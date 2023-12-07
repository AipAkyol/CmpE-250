import java.io.File;
import java.util.Scanner;
import java.util.LinkedList;

import java.io.FileNotFoundException;

public class Project3 {
    public static void main(String[] args) throws FileNotFoundException {
        
        File songFile = new File("C:\\BOUN\\CmpE250\\CmpE-250\\Project-3\\sample-test-cases\\songs.txt");
        Scanner songScanner = new Scanner(songFile);
        
        Song[] songDatabase = new Song[Integer.parseInt(songScanner.nextLine()) + 1];
        
        while (songScanner.hasNextLine()) {
            String[] songInfo = songScanner.nextLine().split(" ");
            int id = Integer.parseInt(songInfo[0]);
            String name = songInfo[1];
            int playCount = Integer.parseInt(songInfo[2]);
            int[] scores = new int[3];
            for (int i = 0; i < 3; i++) {
                scores[i] = Integer.parseInt(songInfo[3 + i]);
            }
            songDatabase[id] = new Song(id, name, playCount, scores);
        }

        songScanner.close();

        // TODO: check starting sizes
        MaxHeap heartacheHeap = new MaxHeap(100, 0);
        MaxHeap roadtripHeap = new MaxHeap(100, 1);
        MaxHeap blissfulHeap = new MaxHeap(100, 2);

        File inputFile = new File("C:\\BOUN\\CmpE250\\CmpE-250\\Project-3\\sample-test-cases\\inputs\\sample_0.txt");
        Scanner inputScanner = new Scanner(inputFile);

        String[] limits = inputScanner.nextLine().split(" ");
        final int LIMIT_PLAYLIST = Integer.parseInt(limits[0]);
        final int LIMIT_HEARTACHE = Integer.parseInt(limits[1]);
        final int LIMIT_ROADTRIP = Integer.parseInt(limits[2]);
        final int LIMIT_BLISSFUL = Integer.parseInt(limits[3]);

        int[] playlistContribitionCounts = new int[Integer.parseInt(inputScanner.nextLine()) + 1];

        //TODO: playlist sizeları dırektolarak atladım
        for (int i = 1; i < playlistContribitionCounts.length; i++) {
            inputScanner.nextLine();
            // playlistContribitionCounts[i] = 0;
            String[] playlistSongs = inputScanner.nextLine().split(" ");
            for (int j = 0; j < playlistSongs.length; j++) {
                int songId = Integer.parseInt(playlistSongs[j]);
                songDatabase[songId].playlistId = i;
                heartacheHeap.insert(songDatabase[songId]);
                roadtripHeap.insert(songDatabase[songId]);
                blissfulHeap.insert(songDatabase[songId]);
            }
        }

        //build the initial epic blend
        EpicBlend epicBlend = new EpicBlend();
        
        // heartache part
        for (int i = 0; i < LIMIT_HEARTACHE; i++) {
            Song song = heartacheHeap.remove();
            epicBlend.blend.add(song.id);
            epicBlend.heartacheCount += song.scores[0];
            epicBlend.minHeartache += song.scores[0];
            epicBlend.roadtripCount += song.scores[1];
            epicBlend.minRoadtrip += song.scores[1];
            epicBlend.blissfulCount += song.scores[2];
            epicBlend.minBlissful += song.scores[2];
            playlistContribitionCounts[song.playlistId]++;
        }


        System.out.println("Heartache Heap");

        
         
        // test if sort runs well in more than 2 duplicates
        Song[] test = new Song[6];
        test[0] = new Song(0, "a", 0, new int[]{0, 0, 0}, 0);
        test[1] = new Song(1, "a", 0, new int[]{0, 0, 0}, 0);
        test[2] = new Song(2, "d", 2, new int[]{0, 0, 0}, 0);
        test[3] = new Song(3, "c", 0, new int[]{0, 0, 0}, 0);
        test[4] = new Song(4, "a", 0, new int[]{0, 0, 0}, 0);
        test[5] = new Song(5, "a", 0, new int[]{0, 0, 0}, 0);
        MergeSort.sort(test);
        for (int i = 0; i < test.length; i++) {
            if (i < test.length - 1) {
                if (test[i+1].name == test[i].name) {
                    continue;
                }
            }
            System.out.println(test[i].name);
        }
        


        
        /*MaxHeap heap = new MaxHeap();
        heap.insert(new Song(1, "", 0, new int[]{1, 0, 0}, 0));
        heap.insert(new Song(2, "", 0, new int[]{2, 0, 0}, 0));
        heap.insert(new Song(3, "", 0, new int[]{3, 0, 0}, 0));
        heap.insert(new Song(4, "", 0, new int[]{4, 0, 0}, 0));
        for(int i = 0; i < 5; i++){
            if (heap.size == 1) {
                throw new RuntimeException("Heap is empty");
            }
            System.out.println(heap.remove().scores[0]);
        }*/

    }

    //TODO: checklater
    public Song[] hashtableToSongArray(Hashtable hashtable, Song[] songDatabase){
        Song[] array = new Song[hashtable.size];
        int index = 0;
        for (int i = 0; i < hashtable.table.length; i++) {
            if (hashtable.table[i] != null) {
                for (int j = 0; j < hashtable.table[i].size(); j++) {
                    int songID = hashtable.table[i].get(j);
                    array[index] = songDatabase[songID];
                    index++;
                }
            }
        }
        return array;
    }

    public static class EpicBlend {
        Hashtable blend;
        int heartacheCount;
        int roadtripCount;
        int blissfulCount;
        int minHeartache;
        int minRoadtrip;
        int minBlissful;

        public EpicBlend(){
            this.blend = new Hashtable();
            this.heartacheCount = 0;
            this.roadtripCount = 0;
            this.blissfulCount = 0;
            this.minHeartache = 0;
            this.minRoadtrip = 0;
            this.minBlissful = 0;
        }
            
    }

    //TODO: nameler uniqmi
    public static class MergeSort { // use songs playcounts in descending order of play counts. 
                                    //In the case of tied counts, the song with lexicographically smaller names is comes first
        public static void sort(Song[] array, int low, int high){
            if(low < high){
                int mid = (low + high) / 2;
                sort(array, low, mid);
                sort(array, mid + 1, high);
                merge(array, low, mid, high);
            }
        }

        public static void merge(Song[] array, int low, int mid, int high){
            int left = low;
            int right = mid + 1;
            int index = low;
            Song[] temp = new Song[array.length];
            for(int i = low; i <= high; i++){
                temp[i] = array[i];
            }
            while(left <= mid && right <= high){
                if(temp[left].playCount > temp[right].playCount){
                    array[index] = temp[left];
                    left++;
                }else if(temp[left].playCount < temp[right].playCount){
                    array[index] = temp[right];
                    right++;
                }else{ 
                    if(temp[left].name.compareTo(temp[right].name) < 0){
                        array[index] = temp[left];
                        left++;
                    }else {
                        array[index] = temp[right];
                        right++;
                    } 
                }
                index++;
            }
            while(left <= mid){
                array[index] = temp[left];
                left++;
                index++;
            }
            while(right <= high){
                array[index] = temp[right];
                right++;
                index++;
            }
        }

        public static void sort(Song[] array){
            sort(array, 0, array.length - 1);
        }
    }

    //TODO: voide don
    public static class Hashtable { //for storing integers, will use seperate hashing
        //TODO integer kullanma
        LinkedList<Integer>[] table;
        int size;

        //TODO: check the number
        public Hashtable(){
            this.table = new LinkedList[9973];
            this.size = 0;
        }

        public int add(int key){
            int hash = key % table.length;
            if(table[hash] == null){
                table[hash] = new LinkedList();
                table[hash].add(key);
                size++;
                if (size > table.length / 2) {
                    rehash();
                }
            } else if (!table[hash].contains(key)) {
                table[hash].add(key);
                size++;
                if (size > table.length / 2) {
                    rehash();
                }
            }
            return key;   
        }

        public int remove(int key){
            int hash = key % table.length;
            if(table[hash] != null){
                for (int i = 0; i < table[hash].size(); i++) {
                    if (table[hash].get(i) == key) {
                        table[hash].remove(i);
                        size--;
                        return key;
                    }
                }
            }
            return -1;
        }

        public int[] toArray(){
            int[] array = new int[size];
            int index = 0;
            for (int i = 0; i < table.length; i++) {
                if (table[i] != null) {
                    for (int j = 0; j < table[i].size(); j++) {
                        array[index] = table[i].get(j);
                        index++;
                    }
                }
            }
            return array;
        }

        public void rehash(){
            LinkedList<Integer>[] newTable;
            newTable = new LinkedList[nextPrime(table.length * 2 + 1)];
            size = 0;
            for (int i = 0; i < table.length; i++) {
                if (table[i] != null) {
                    for (int j = 0; j < table[i].size(); j++) {
                        add(table[i].get(j));
                    }
                }
            }
        }

        public boolean contains(int key){
            int hash = key % table.length;
            if(table[hash] != null){
                for (int i = 0; i < table[hash].size(); i++) {
                    if (table[hash].get(i) == key) {
                        return true;
                    }
                }
            }
            return false;
        }

        public int nextPrime(int input) {
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
    
    public static class MaxHeap{
        int scoreIndex; // for song scores
        Song[] heap;
        int size;

        public MaxHeap(){
            this.heap = new Song[100];
            this.size = 0;
            this.scoreIndex = 0;
            this.insert(new Song(0, "", 0, new int[]{0, 0, 0}, 0)); // dummy node
        }

        public MaxHeap(int size){
            this.heap = new Song[size];
            this.size = 0;
            this.scoreIndex = 0;
            this.insert(new Song(0, "", 0, new int[]{0, 0, 0}, 0)); // dummy node
        }

        public MaxHeap(int size, int scoreIndex){
            this.heap = new Song[size];
            this.size = 0;
            this.scoreIndex = scoreIndex;
            this.insert(new Song(0, "", 0, new int[]{0, 0, 0}, 0)); // dummy node
        }

        public void insert(Song song){
            if(size == heap.length){
                Song[] newHeap = new Song[heap.length * 2];
                for(int i = 0; i < heap.length; i++){
                    newHeap[i] = heap[i];
                }
                heap = newHeap;
            }
            heap[size] = song;
            size++;
            int current = size - 1;
            while(current > 1 && heap[current].scores[scoreIndex] > heap[parent(current)].scores[scoreIndex]){
                swap(current, parent(current));
                current = parent(current);
            }
        }

        public void swap(int a, int b){
            Song temp = heap[a];
            heap[a] = heap[b];
            heap[b] = temp;
        }

        public int parent(int pos){
            return pos / 2;
        }

        public int leftChild(int pos){
            return 2 * pos;
        }

        public int rightChild(int pos){
            return (2 * pos) + 1;
        }

        public void maxHeapify(int pos){
            if (pos * 2 >= size) { // leaf node
                return;
            }
            int left = leftChild(pos);
            int right = rightChild(pos);
            if(right < size){ // Check if right child exists
                if(heap[pos].scores[scoreIndex] < heap[left].scores[scoreIndex] || heap[pos].scores[scoreIndex] < heap[right].scores[scoreIndex]){
                    if(heap[left].scores[scoreIndex] > heap[right].scores[scoreIndex]){
                        swap(pos, left);
                        maxHeapify(left);
                    }else{
                        swap(pos, right);
                        maxHeapify(right);
                    }
                }
            }else if(left < size){ // Check if left child exists
                if(heap[pos].scores[scoreIndex] < heap[left].scores[scoreIndex]){
                    swap(pos, left);
                    maxHeapify(left);
                }
            }
        }

        public Song remove(){
            Song popped = heap[1];
            heap[1] = heap[size - 1];
            heap[size - 1] = null;
            size--;
            maxHeapify(1);
            return popped;
        }

    }

    public static class Song{
        int id;
        String name;
        int playCount;
        int[] scores; // [heartache, roadtrip, blissful]
        int playlistId;

        public Song(){
            this.id = 0;
            this.scores = new int[3];
            this.playlistId = 0;
            this.playCount = 0;
        }

        public Song(int id, String name, int playCount, int[] scores){
            this.id = id;
            this.name = name;
            this.playCount = playCount;
            this.scores = scores;
            this.playlistId = 0;
        }

        public Song(int id, String name, int playCount, int[] scores, int playlistId){
            this.id = id;
            this.name = name;
            this.playCount = playCount;
            this.scores = scores;
            this.playlistId = playlistId;
        }
    }
}
