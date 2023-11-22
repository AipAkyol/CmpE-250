import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException { 

        if (args.length != 2) {
            System.err.println("Usage: java Main <input_file> <output_file>");
            System.exit(1);
        }

        String inputFileName = args[0];
        String outputFileName = args[1];
        
        File inputFile = new File(  "./" +inputFileName);
        Scanner input = new Scanner(inputFile);
        String[] boss = input.nextLine().split(" ");

        File outputFile = new File(outputFileName);
        FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
        PrintStream printStream = new PrintStream(fileOutputStream);
        System.setOut(printStream);  // set system out to the file

        AvlTree theFamily = new AvlTree(new AvlNode(boss[0], Double.parseDouble(boss[1])));
        
        do {
            executeInput(theFamily, input.nextLine());
        }
        while (input.hasNextLine());
        
        input.close();

    }

    public static void executeInput(AvlTree tree, String line) {
        String[] input = line.split(" ");
         if (input.length == 1) {
            if(input[0].equals("INTEL_DIVIDE")) {
                tree.divideTree();
            }
        } else {
            if (input[0].equals("MEMBER_IN") ) {
                tree.insert( input[1], Double.parseDouble(input[2]) );
            } else if (input[0].equals("MEMBER_OUT") )  {
                tree.delete(Double.parseDouble(input[2]));
            } else if (input[0].equals("INTEL_TARGET") )  {
                tree.printTarget(Double.parseDouble(input[2]), Double.parseDouble(input[4]));
            } else if (input[0].equals("INTEL_RANK") )  {
                tree.monitorRanks(Double.parseDouble(input[2]));
            }
        }
    }
    
    static class AvlTree {
        //basic avl algorithms and methods(height methods, insert, balance, rotate, delete) are the same with common avl algortihm so(assuming you can easily understand my code) i did not comment these codes
        AvlNode root;

        public AvlTree() {}

        public AvlTree(AvlNode root) {
            this.root = root;
        }

          private void updateHeight(AvlNode node) {
            int leftChildHeight = getHeight(node.left);
            int rightChildHeight = getHeight(node.right);
            node.height = Math.max(leftChildHeight, rightChildHeight) + 1;
        }
        
        private int getHeight(AvlNode node) {
            return node == null ? -1 : node.height;
        }

        private int getBalanceFactor(AvlNode node) {
            return (node == null) ? 0 : getHeight(node.right) - getHeight(node.left);
        }
        
        private AvlNode rightRotation(AvlNode grandparent) {
            AvlNode parent = grandparent.left;
            AvlNode child = parent.right;
            parent.right = grandparent;
            grandparent.left = child;
            updateHeight(grandparent);
            updateHeight(parent);
            return parent;
        }

        private AvlNode leftRotation(AvlNode grandparent) {
            AvlNode parent = grandparent.right;
            AvlNode child = parent.left;
            parent.left = grandparent;
            grandparent.right = child;
            updateHeight(grandparent);
            updateHeight(parent);
            return parent;
        }
        
        private AvlNode balance(AvlNode node) {
            updateHeight(node);

            int balanceFactor = getBalanceFactor(node);
            if (balanceFactor < -1) {
              if (getBalanceFactor(node.left) <= 0) {
                node = rightRotation(node);
              } else {
                node.left = leftRotation(node.left);
                node = rightRotation(node);
              }
            }
            if (balanceFactor > 1) {
              if (getBalanceFactor(node.right) >= 0) {
                node = leftRotation(node);
              } else {
                node.right = rightRotation(node.right);
                node = leftRotation(node);
              }
            }
            return node;
        }

        public void insert(String name, double gmsValue) {
            root = insert(root, name, gmsValue);
        }
        
        private AvlNode insert(AvlNode root, String name, double gmsValue) {
            if (root == null) {
                return new AvlNode(name, gmsValue);
            } else if (gmsValue > root.gmsValue) {
                System.out.println(root.name + " welcomed " + name);
                root.right = insert(root.right, name, gmsValue);
            } else if (gmsValue < root.gmsValue) {
                System.out.println(root.name + " welcomed " + name);                
                root.left = insert(root.left, name, gmsValue);
            }
            updateHeight(root);
            return balance(root);
        }

        public void delete(double gmsValue) {
            root = delete(root, gmsValue, false);
        }

        private AvlNode delete (AvlNode node, double gmsValue, boolean isThirdCaseMet) {
            if (node == null) {
                return node;
            } else if (gmsValue > node.gmsValue) {
                node.right = delete(node.right, gmsValue, isThirdCaseMet);
            } else if (gmsValue < node.gmsValue) {
                node.left = delete(node.left, gmsValue, isThirdCaseMet);
            } else {
                String leavingMemberName = node.name;
                if (node.left == null && node.right == null) {
                    if (!isThirdCaseMet) {
                        System.out.println(leavingMemberName + " left the family, replaced by nobody");
                    }
                    node = null;
                } else if (node.left == null) {
                    String rightChildName = node.right.name;
                    if (!isThirdCaseMet) {
                        System.out.println(leavingMemberName + " left the family, replaced by " + rightChildName);
                    }
                    node = node.right;
                } else if (node.right == null){
                    String leftChildName = node.left.name;
                    if (!isThirdCaseMet) {
                        System.out.println(leavingMemberName + " left the family, replaced by " + leftChildName);
                    }
                    node = node.left;
                } else {
                        AvlNode rightMostLeftChild = getMostLeftChild(node.right);
                        String rightMostLeftChildName = rightMostLeftChild.name;
                        node.gmsValue = rightMostLeftChild.gmsValue;
                        node.name = rightMostLeftChild.name;
                        if (!isThirdCaseMet) {
                            System.out.println(leavingMemberName + " left the family, replaced by " + rightMostLeftChildName);
                        }
                        node.right = delete(node.right, node.gmsValue, true);
                }
            }
            if (node != null) {
                node = balance(node);
            }
            return node;
        }

        private AvlNode getMostLeftChild (AvlNode node) {  // for deletion case 3                 
            AvlNode currentNode = node;
            while (currentNode.left != null) {
                currentNode = currentNode.left;
            }
            return currentNode;
        }

        public AvlNode find(double gmsValue) {   // return a node in the tree
            AvlNode currentNode = root;
            while (currentNode != null) {
                if (currentNode.gmsValue == gmsValue) {
                    break;
                }
                if (gmsValue > currentNode.gmsValue) {
                    currentNode = currentNode.right;
                } else {
                    currentNode = currentNode.left;
                }
            }
            return currentNode;
        }
        
        private int getRank(double gmsValue) {    // get a rank of a node
            AvlNode currentNode = root;
            int rank = 0;
            while (currentNode != null) {
                if (currentNode.gmsValue == gmsValue) {
                    break;
                    
                }
                if (gmsValue > currentNode.gmsValue) {
                    currentNode = currentNode.right;
                } else {
                    currentNode = currentNode.left;
                }
                rank += 1;
            }
            return rank ;
        }

        public void printTarget(double firstGmsValue, double secondGmsValue) {   // method for printing targets
            AvlNode targetResult = target(firstGmsValue, secondGmsValue);
            String targetName = targetResult.name;
            String targetGms = String.format("%.3f", targetResult.gmsValue);;
            
            System.out.println("Target Analysis Result: " + targetName + " " + targetGms);
        }
        
        private AvlNode target(double firstGmsValue, double secondGmsValue) {    // prints the most inferior superior of two nodes 
            AvlNode currentNode = root;
            while (currentNode != null) {
                //AvlNode currentLeft = currentNode.left;
                //if ((currentLeft.gmsValue == firstGmsValue)
                //|| (currentLeft.gmsValue == secondGmsValue)){
                //    if (firstGmsValue ==82.031){
                //        int sad = 0;
                //    }
                //    return currentNode;                  //this was an edge case but...
                //}                                                       
                //AvlNode currentRight = currentNode.right;
                //if ((currentRight.gmsValue == firstGmsValue)
                //|| (currentRight.gmsValue == secondGmsValue)){
                //    if (firstGmsValue ==82.031){
                //        int sad = 0;
                //    }
                //    return currentNode;
                //}
                if ((firstGmsValue > currentNode.gmsValue)    // works exactly like find method until a seperation begins
                && (secondGmsValue > currentNode.gmsValue)) {
                    currentNode = currentNode.right;
                } else if ((firstGmsValue < currentNode.gmsValue) 
                && (secondGmsValue < currentNode.gmsValue)) {
                    currentNode = currentNode.left;
                } else {
                    return currentNode;
                }
            }
            return currentNode;
        }

        public void monitorRanks(double gmsValue) {  // method for printing ranks 
            System.out.print("Rank Analysis Result:");
            int monitoredRank = getRank(gmsValue);
            printRanks(monitoredRank, root);
            System.out.println();
        }
        
        private void printRanks(int monitoredHeight, AvlNode node) {  //prints all the nodes that are equally ranked to given nodes rank left to right
            if (node == null) {
                return;
            }
            int nodeRank = getRank(node.gmsValue);
            if (monitoredHeight >= nodeRank) {  // enter inside the tree until given rank found
                printRanks(monitoredHeight, node.left); // call small gms first
                if (monitoredHeight == nodeRank) {  // prints the node if the ranks are equal
                    String formattedGms = String.format("%.3f", node.gmsValue);
                    System.out.print(" " + node.name + " " + formattedGms );
                }
                printRanks(monitoredHeight, node.right); // call large gms later
            }
        }
        
        public void divideTree() { // method for printing the division result
            MutableInteger count = new MutableInteger(0); // new mutable integer class for changing the value of an integer inside the method
            findMaxDivide(root, count);
            System.out.println("Division Analysis Result: " + count.getValue());
        }


        boolean findMaxDivide(AvlNode node, MutableInteger count) { // true: included in the max false: excluded in the max
            if (node == null) {
                return false;
            }
            //postorder treversal for going to the leafs first (one the max cases always includes all the leafs)
            boolean rightStatus = findMaxDivide(node.right,count);
            boolean leftStatus = findMaxDivide(node.left,count);
            if (rightStatus || leftStatus) {   // if one of the childs are included we can exclude this node
                return false;
            } else { // if both childs are excluded we can include this node
                count.setValue(count.getValue() + 1); // increase the count via reference so we can use outside the function
                return true;
            }
        }
    }

    static class AvlNode {
        String name;
        double gmsValue;
        int height;
        AvlNode right;
        AvlNode left;
        
        public AvlNode() {}
        
        public AvlNode(String name, double gmsValue) {
            this.name = name;
            this.gmsValue = gmsValue;
            this.height = 0;
        }
    }
}

class MutableInteger {
    private int value;

    public MutableInteger(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}


