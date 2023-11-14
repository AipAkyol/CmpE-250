package W5;
import java.util.Hashtable;
import java.util.HashSet;
import java.util.HashMap;

public class QuadraticProbingHashTable<AnyType> {
	
	private static final int DEFAULT_TABLE_SIZE = 101;
	
	private HashEntry<AnyType> [ ] array;
	private int occupied;
	private int size;
	
	private static class HashEntry<AnyType>{
		public AnyType value;
		public boolean isActive;
		
		HashEntry(AnyType value){
			this(value, true);
		}
		
		HashEntry(AnyType value, boolean isActive){
			this.value = value;
			this.isActive = isActive;
		}
	}
	
	QuadraticProbingHashTable(){
		this(DEFAULT_TABLE_SIZE);
	}
	
	QuadraticProbingHashTable(int size){
		allocateArray(size);
		clear();
	}
	
	private void allocateArray(int arraySize) {
		array = new HashEntry[nextPrime(arraySize)];
	}
	
	public void clear() {
		occupied = 0;
		for(int i = 0; i < array.length; i++) {
			array[i] = null;
		}
	}
	
	public boolean insert(AnyType value) {
		int currentPos = findPos(value);
		if(isActive(currentPos)) {
			return false;
		}
		
		if(array[currentPos] == null) {
			occupied++;
		}
		array[currentPos] = new HashEntry<>(value);
		size++;
		
		if(occupied > array.length / 2) {
			rehash();
		}
		
		return true;
		
	}
	
	public boolean remove(AnyType value) {
		int currentPos = findPos(value);
		if(isActive(currentPos)) {
			array[currentPos].isActive = false;
			size--;
			return true;
		}else {
			return false;
		}
	}
	
	public boolean contains(AnyType value) {
		int currentPos = findPos(value);
		return isActive(currentPos);
	}
	
	private int findPos(AnyType value) {
		int offset = 1;
		int currentPos = hash(value);
		
		while(array[currentPos] != null && !array[currentPos].value.equals(value)) {
			currentPos += offset;
			offset += 2;
			if(currentPos >= array.length) {
				currentPos -= array.length;
			}
		}
		
		return currentPos;
	}
	
	private boolean isActive(int currentPos) {
		return array[currentPos] != null && array[currentPos].isActive;
	}
	
	private int hash(AnyType value) {
		int hashVal = value.hashCode();
		hashVal = hashVal % array.length;
		if(hashVal < 0) {
			hashVal += array.length;
		}
		return hashVal;
	}
	
	private void rehash() {
		HashEntry<AnyType> [] oldArray = array;
		
		allocateArray(2 * oldArray.length);
		occupied = 0;
		size = 0;
		for(HashEntry<AnyType> entry : oldArray) {
			if(entry != null && entry.isActive) {
				insert(entry.value);
			}
		}
	}
	
	private static int nextPrime(int currentPrime) {
		if(currentPrime % 2 == 0) {
			currentPrime++;
		}
		while(!isPrime(currentPrime)) {
			currentPrime += 2;
		}
		return currentPrime;
	}
	
	private static boolean isPrime(int n) {
		if(n == 2 || n == 3) {
			return true;
		}
		if (n == 1 || n % 2 == 0) {
			return false;
		}
		for(int i = 3; i * i <= n; i += 2) {
			if (n % i == 0) {
				return false;
			}
		}
		return true;
		
	}
	public static void main(String[] args) {
		QuadraticProbingHashTable<Integer> quadraticProbingHashTable = new QuadraticProbingHashTable<>();
		for(int i = 0; i < 400; i += 5) {
			quadraticProbingHashTable.insert(i);
		}
		for(int i = 0; i < 30; i++) {
			if(quadraticProbingHashTable.contains(i)) {
				System.out.println("value " + i +" is present");
			}else{
				System.out.println("value " + i +" is not present");
			}
		}
	}

}
