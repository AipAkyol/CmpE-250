import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

//TODO:!!!!! write the input and output format in the assignment escpicxally do not use ./

public class project2 {

    public static void main(String[] args) throws FileNotFoundException {
        
        //if (args.length != 3) {
        //    System.err.println("Usage: java project2 <inputfile1> <inputfile2> <outputfile>");
        //    System.exit(1);
        //}
        
        Hashtable<Branch> branches = new Hashtable<Branch>();
        
        //String inputFile1Name = args[0];
        //String inputFile2Name = args[1];
        //String outputFileName = args[2];
        
        File outputFile = new File("C:\\BOUN\\CmpE250\\CmpE-250\\Project-2/output.txt");
        FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
        PrintStream printStream = new PrintStream(fileOutputStream);
        System.setOut(printStream);  // set system out to the file
        
        File inputFile1 = new File("C:\\BOUN\\CmpE250\\CmpE-250\\Project-2\\small_cases\\inputs/initial4.txt");
        Scanner input1 = new Scanner(inputFile1);

        do {
            String line = input1.nextLine();
            executeinput1(branches, line);
        } while (input1.hasNextLine());
        
        input1.close();
        
        File inputFile2 = new File("C:\\BOUN\\CmpE250\\CmpE-250\\Project-2\\small_cases\\inputs/input4.txt");
        Scanner input2 = new Scanner(inputFile2);
        
        input2.nextLine(); // skip first line
        do {
            String line = input2.nextLine();
            if (line.equals("")) {  // Month end
                resetMonthlyBonuses(branches);
                if (input2.hasNextLine()) {
                    input2.nextLine();
                }
            } else {
                executeinput2(branches, line);
            }
        } while (input2.hasNextLine());

        input2.close();
    }

    //TODO: rewrite sout of employee already exists
    public static void executeinput1(Hashtable<Branch> branches, String line) {
        String[] arr = line.split(", ");
        if (employeeAlreadyExists(branches, arr[0], arr[1], arr[2], arr[3])) {
            System.out.println("Employee " + arr[2] + " already exists in branch " + arr[1] + ".");
            return;
        }
        branches.add(new Branch(arr[0], arr[1])).addEmployee((new Employee(arr[2], arr[3])));
    }

    //TODO: CHECK CHANGES
    public static void executeinput2(Hashtable<Branch> branches, String line) {
        String[] order = line.split(": ");
        String[] arr = order[1].split(", ");
        if (order[0].equals("PERFORMANCE_UPDATE")) {
            if (!employeeAlreadyExists(branches, arr[0], arr[1], arr[2], "ARBITRARY_PROFESSION")) {
                System.out.println("There is no such employee.");
                return;
            }
            branches.find(arr[0] + arr[1]).updatePerformance(branches.find(arr[0] + arr[1]).employees.find(arr[2]), Integer.parseInt(arr[3]));
            branches.find(arr[0] + arr[1]).checkChanges();
        } else if (order[0].equals("ADD")) {
            if (employeeAlreadyExists(branches, arr[0], arr[1], arr[2], arr[3])) {
                System.out.println("Employee " + arr[2] + " already exists in branch " + arr[1] + ".");
                return;
            }
            branches.find(arr[0] + arr[1]).addEmployee(new Employee(arr[2], arr[3]));
            branches.find(arr[0] + arr[1]).checkChanges();
        } else if (order[0].equals("LEAVE")) {
            if (!employeeAlreadyExists(branches, arr[0], arr[1], arr[2], "ARBITRARY_PROFESSION")) {
                System.out.println("There is no such employee.");
                return;
            }
            branches.find(arr[0] + arr[1]).removeEmployee(branches.find(arr[0] + arr[1]).employees.find(arr[2]), false, false);
            branches.find(arr[0] + arr[1]).checkChanges();
        } else if (order[0].equals("PRINT_MONTHLY_BONUSES")) {
            branches.find(arr[0] + arr[1]).printMonthlyBonuses();
        } else if (order[0].equals("PRINT_OVERALL_BONUSES")) {
            branches.find(arr[0] + arr[1]).printOverallBonuses();
        } else if (order[0].equals("PRINT_MANAGER")) {
            branches.find(arr[0] + arr[1]).printManager();
        } else {
            throw new RuntimeException("Error in executeinput2 method");
        }   
    }
    
    public static boolean employeeAlreadyExists(Hashtable<Branch> branches, String city, String district, String name, String profession) {
        // checks if employee exists in branch
        if (branches.add(new Branch(city, district)).containsEmployee(new Employee(name, profession))) {
            return true;
        }
        return false;
    }

    public static void resetMonthlyBonuses(Hashtable<Branch> branches) {
        // resets monthly bonuses of all branches
        for (int i = 0; i < branches.table.length; i++) {
            if (branches.table[i] != null) {
                for (int j = 0; j < branches.table[i].size(); j++) {
                    branches.table[i].get(j).monthlyBonuses = 0;
                }
            }
        }
    }

    public static class Branch {
        public String city;
        public String district;
        public Hashtable<Employee> employees;
        public int numEmployees = 0;
        public int numCooks = 0;
        public int numCashiers = 0;
        public int numCouriers = 0;
        public Employee manager = null;
        public Queue<Employee> cookPromotion;
        public Queue<Employee> cashierPromotion;
        public Queue<Employee> blacklist;
        public int monthlyBonuses = 0;
        public int overallBonuses = 0;
        
        public Branch(String city, String district) {
            this.city = city;
            this.district = district;
            employees = new Hashtable<Employee>();
            cookPromotion = new LinkedList<Employee>();
            cashierPromotion = new LinkedList<Employee>();
            blacklist = new LinkedList<Employee>();
        }

        @Override
        public boolean equals(Object obj) {
            // checks if two branches are equal
            if (obj == null) {
                return false;
            }
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof Branch)) {
                return false;
            }
            Branch branch = (Branch) obj;
            return (this.city.equals(branch.city) && this.district.equals(branch.district));
        }

        public void updatePerformance(Employee employee, int points) {
            // updates the performance of employee
            int promotionPoints = points / 200;
            int excessPoints = points % 200;
            employee.promotionPoints += promotionPoints;
            if (points > 0) {
                monthlyBonuses += excessPoints;
                overallBonuses += excessPoints;
            }
            if (employee.promotionPoints <= -5) {
                // first check if employee already in blacklist
                if (blacklist.contains(employee)) {
                    return;
                } 
                removeEmployee(employee, true, false);
            } else {
                checkPromotion(employee);
            }
        }

        //TODO: write souts
        private void checkPromotion(Employee employee) {
            // checks if employee can be promoted
            if (employee.profession.equals("COOK")) {
                if (employee.promotionPoints >= 10) {
                    // first check if employee already in cookPromotion
                    if (cookPromotion.contains(employee)) {
                        return;
                    }
                    cookPromotion.add(employee);
                }
            } else if (employee.profession.equals("CASHIER")) {
                if (employee.promotionPoints >= 3) {
                    if (numCashiers > 1) {
                        promoteToCook(employee);
                    } else {
                        // first check if employee already in cashierPromotion
                        if (cashierPromotion.contains(employee)) {
                            return;
                        }
                        cashierPromotion.add(employee);
                    }                  
                }
            }
        }

        //TODO: write souts
        public void removeEmployee(Employee employee, boolean fired, boolean alreadyInBlacklist) {
            // removes employee from branch if possible       
            if (employee.profession.equals("MANAGER")) {
                if (cookPromotion.size() > 0 && numCooks > 1) {
                        employees.remove(employee);
                        if (alreadyInBlacklist) {
                            blacklist.remove(employee);
                        }
                        if (!fired) {
                            System.out.println(employee.name + " is leaving from branch: " + this.district + ".");
                        } else {
                            System.out.println(employee.name + " is dismissed from branch: " + this.district + ".");
                        }
                        numEmployees--;
                        promoteToManager(cookPromotion.remove());
                } else { // cannot remove manager
                    if (alreadyInBlacklist) {
                        return;
                    } else if (fired) {
                        blacklist.add(employee);
                    } else {
                        overallBonuses += 200;
                        monthlyBonuses += 200;
                    }
                } 
            } else if (employee.profession.equals("COOK")) {
                if (numCooks > 1) {
                    if (alreadyInBlacklist) {
                        blacklist.remove(employee);
                    }
                    employees.remove(employee);
                    if (!fired) {
                        System.out.println(employee.name + " is leaving from branch: " + this.district + ".");
                    } else {
                        System.out.println(employee.name + " is dismissed from branch: " + this.district + ".");
                    }                    
                    numEmployees--;
                    numCooks--;
                } else { // cannot remove cook
                    if (alreadyInBlacklist) {
                        return;
                    } else if (fired) {
                        blacklist.add(employee);
                    } else {
                        overallBonuses += 200;
                        monthlyBonuses += 200;
                    }
                }
            } else if (employee.profession.equals("CASHIER")) {
                if (numCashiers > 1) {
                    if (alreadyInBlacklist) {
                        blacklist.remove(employee);
                    }                    
                    employees.remove(employee);
                    if (!fired) {
                        System.out.println(employee.name + " is leaving from branch: " + this.district + ".");
                    } else {
                        System.out.println(employee.name + " is dismissed from branch: " + this.district + ".");
                    }                      
                    numEmployees--;
                    numCashiers--;
                } else { // cannot remove cashier
                    if (alreadyInBlacklist) {
                        return;
                    } else if (fired) {
                        blacklist.add(employee);
                    } else {
                        overallBonuses += 200;
                        monthlyBonuses += 200;
                    }
                }
            } else if (employee.profession.equals("COURIER")) {
                if (numCouriers > 1) {
                    employees.remove(employee);
                    if (alreadyInBlacklist) {
                        blacklist.remove(employee);
                    }                    
                    if (!fired) {
                        System.out.println(employee.name + " is leaving from branch: " + this.district + ".");
                    } else {
                        System.out.println(employee.name + " is dismissed from branch: " + this.district + ".");
                    }                      
                    numEmployees--;
                    numCouriers--;
                } else { // cannot remove courier
                    if (alreadyInBlacklist) {
                        return;
                    } else if (fired) {
                        blacklist.add(employee);
                    } else {
                        overallBonuses += 200;
                        monthlyBonuses += 200;
                    }
                }
            }
        }

        private void promoteToManager(Employee employee) {
            // promotes cook to manager
            employee.profession = "MANAGER";
            System.out.println(employee.name + " is promoted from Cook to Manager.");
            employee.promotionPoints -= 10;
            manager = employee;
            numCooks--;
        }
        
        private void promoteToCook(Employee employee) {
            // promotes cashier to cook
            employee.profession = "COOK";
            System.out.println(employee.name + " is promoted from Cashier to Cook.");
            employee.promotionPoints -= 3;
            numCashiers--;
            numCooks++;
            cashierPromotion.remove(employee);
        }
        
        public void checkChanges() {
            // itarates through cashierPromotion and checks if any cashier can be promoted or lost their promotion
            List<Employee> cashiersToRemove = new ArrayList<>();
            cashierPromotion.forEach((employee) -> {
                if (employee.promotionPoints < 3) {
                    cashiersToRemove.add(employee);
                } else if (numCashiers > 1) {
                    promoteToCook(employee);
                }
            });
            cashierPromotion.removeAll(cashiersToRemove);
            // itarates through cookPromotion and checks if any cook can be promoted or lost their promotion
            List<Employee> cooksToRemove = new ArrayList<>();
            cookPromotion.forEach((employee) -> {
                if (employee.promotionPoints < 10) {
                    cooksToRemove.add(employee);
                }
            });
            cookPromotion.removeAll(cooksToRemove);
            // itarates through blacklist and removes employees from branch if possible
            blacklist.forEach((employee) -> {
                removeEmployee(employee, true, true);
            });
        }

        public void addEmployee(Employee employee) {
            employees.add(employee);
            numEmployees++;
            if (employee.profession.equals("MANAGER")) {
                manager = employee;
            } else if (employee.profession.equals("COOK")) {
                numCooks++;
            } else if (employee.profession.equals("CASHIER")) {
                numCashiers++;
            } else if (employee.profession.equals("COURIER")) {
                numCouriers++;
            }
        }

        public void printMonthlyBonuses() {
            System.out.println("Total bonuses for the " + this.district + " branch this month are: " + this.monthlyBonuses);
        }

        public void printOverallBonuses() {
            System.out.println("Total bonuses for the " + this.district + " branch are: " + this.overallBonuses);
        }

        public void printManager() {
            System.out.println("Manager of the " + this.district + " branch is " + this.manager + ".");
        }

        public String toString() {
            return city + district;
        }

        public boolean containsEmployee(Employee employee) {
            return employees.contains(employee);
        }
    }

    public static class Employee {
        public String name;
        public String profession; // 4 possible values: MANAGER, COOK, CASHIER, COURIER
        public int promotionPoints;
        

        public Employee(String name, String profession) {
            this.name = name;
            this.profession = profession;
            this.promotionPoints = 0;
        }

        public String toString() {
            return name;
        }

        @Override
        public boolean equals(Object obj) {
            // checks if two employees are equal
            if (obj == null) {
                return false;
            }
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof Employee)) {
                return false;
            }
            Employee employee = (Employee) obj;
            return (this.name.equals(employee.name));
        }
    }

    public static class Hashtable<E> {
        // hashtable class using seperate chaining and rehashing all the linked lists if load factor is greater than 0.5
        // will be used for storing employees in branches and branches in company
        LinkedList<E>[] table; // array of linked lists
        private int size;

        public Hashtable() {
            table = new LinkedList[53];
            size = 0;
        }


        //TODO: containss ile cift itarate optimizsasyonu yap gerekirse , override gerekir mi
        //TODO: fixed, but keep an eye on it
        public E add(E input) {
            // adds input to hashtable
            int index = hash(input.toString()) % table.length;
            if (index < 0) {
                index += table.length;
            }
            if (table[index] == null) {
                table[index] = new LinkedList<E>();
                table[index].add(input);
                size++;
                if (size > table.length / 2) {
                    rehash();
                }
            } else if (table[index].contains(input)){
                return table[index].get(table[index].indexOf(input));
            } else if (!table[index].contains(input)) {
                table[index].add(input);
                size++;
                if (size > table.length / 2) {
                    rehash();
                }
            } else {
                throw new RuntimeException("Error in hashtable add method");
            }
            return input;
        }

        public E remove(E input) {
            // removes input from hashtable
            int index = hash(input.toString()) % table.length;
            if (table[index] != null) {
                for (int i = 0; i < table[index].size(); i++) {
                    if (table[index].get(i).toString().equals(input.toString())) {
                        size--;
                        return table[index].remove(i);
                    }
                }
            }
            return null;
        }

        //TODO: check bugs
        public void rehash() {
            // rehashes all the linked lists in the hashtable
            LinkedList<E>[] temp = table;
            table = new LinkedList[nextPrime(table.length * 2 + 1)];
            size = 0;
            for (int i = 0; i < temp.length; i++) {
                if (temp[i] != null) {
                    for (int j = 0; j < temp[i].size(); j++) {
                        add(temp[i].get(j));
                    }
                }
            }
        }

        public E find(String name) {
            // returns the object with the name name
            int index = hash(name) % table.length;
            if (table[index] != null) {
                for (int i = 0; i < table[index].size(); i++) {
                    if (table[index].get(i).toString().equals(name)) {
                        return table[index].get(i);
                    }
                }
            }
            return null;
        }

        public boolean contains(E input) {
            int index = hash(input.toString()) % table.length;
            if (table[index] != null) {
                for (int i = 0; i < table[index].size(); i++) {
                    if (table[index].get(i).toString().equals(input.toString())) {
                        return true;
                    }
                }
            }
            return false;
        }

        public int getSize() {
            // returns size of hashtable
            return size;
        }

        //TODO: son 3 ilk 3
        private int hash(String input) {
            int hash = 0;
            for (int i = 0; i < input.length(); i++) {
                hash = (hash * 31 + input.charAt(i));
            }
            int index = hash % table.length;
            if (index < 0) {
                index += table.length;
            }
            return index;
        }

        public  int nextPrime(int input) {
            // returns the next prime number after input
            input += 2;
            while (!isPrime(input)) {
                input += 2;
            }
            return input;
        }

        public boolean isPrime(int input) {
            // returns true if input is prime, false otherwise
            if (input <= 1) {
                return false;
            }
            for (int i = 3; i <= Math.sqrt(input); i += 2) {
                if (input % i == 0) {
                    return false;
                }
            }
            return true;
        }
    }        
}