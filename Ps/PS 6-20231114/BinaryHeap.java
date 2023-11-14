import java.util.ArrayList;
import java.util.Arrays;

public class BinaryHeap<AnyType extends Comparable<? super AnyType>> {
	private ArrayList<AnyType> array;
	private int size;
	
	
	BinaryHeap(){
		size = 0;
		array = new ArrayList<AnyType>();
		array.add(null);
	}
	
	BinaryHeap(AnyType[] items){
		array = new ArrayList<AnyType>();
		array.add(null);
		for (AnyType item : items) {
			array.add(item);
			size++;
		}
		buildHeap();
	}
	
	public void insert(AnyType value) {
		size++;
		array.add(value);
		int hole = size;
		while(hole > 1 && value.compareTo(array.get(hole/2)) < 0) {
			AnyType parent = array.get(hole/2);
			array.set(hole / 2, value);
			array.set(hole, parent);
			hole = hole / 2;
		}
	}
	
	public AnyType peek() {
		return array.get(1);
	}
	
	public AnyType pop() {
		AnyType minItem = peek();
		array.set(1, array.get(size));
		size--;
		percolateDown(1);
		
		return minItem;
	}
	
	private void buildHeap(){
		for(int i = size / 2; i > 0; i--) {
			percolateDown(i);
		}
	}
	
	public int size() {
		return size;
	}
	
	private void percolateDown(int hole) {
		int child;
		AnyType temp = array.get(hole);
		
		while(hole * 2 <= size) {
			child = hole * 2;
			if(child != size && array.get(child + 1).compareTo(array.get(child)) < 0) {
				child++;
			}
			if(array.get(child).compareTo(temp) < 0) {
				array.set(hole, array.get(child));
			}else {
				break;
			}
			
			hole = child;
		}
		array.set(hole, temp);
	}
	
	public static void main( String [ ] args )
    {
		Integer[] nums = {4, 5, 41,24, 15, 64,313,5, 64,35,12};
        BinaryHeap<Integer> binaryHeap = new BinaryHeap<>(nums);
        
        for(int i = 20; i > 0; i--) {
        	binaryHeap.insert(i);
        }
        while(binaryHeap.size() > 0) {
        	System.out.println(binaryHeap.pop());
        }
    }

}
