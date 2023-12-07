import java.io.File;
import java.util.Scanner;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;

import java.io.FileNotFoundException;

public class Project3 {
    public static void main(String[] args) throws FileNotFoundException {
        
        File songFile = new File("C:\\BOUN\\CmpE250\\CmpE-250\\Project-3\\test-cases\\songs.txt");
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
            songDatabase[id] = new Song(id, name, playCount, scores, false);
        }
        songDatabase[0] = new Song(0, "aaaaa", 0, new int[]{0, 0, 0}, false); // dummy node
        songScanner.close();
        long initialTime = System.currentTimeMillis();
        MergeSort.sortSongs(songDatabase);
        long finalTime = System.currentTimeMillis();
        System.out.println("Merge Sort: " + (finalTime - initialTime) + " ms");

        /* 
        // TODO: check starting sizes
        MaxHeap heartacheHeap = new MaxHeap(100, 0);
        MaxHeap roadtripHeap = new MaxHeap(100, 1);
        MaxHeap blissfulHeap = new MaxHeap(100, 2);
        */
        /* 
        File inputFile = new File("C:\\BOUN\\CmpE250\\CmpE-250\\Project-3\\sample-test-cases\\inputs\\sample_0.txt");
        Scanner inputScanner = new Scanner(inputFile);

        String[] limits = inputScanner.nextLine().split(" ");
        final int LIMIT_PLAYLIST = Integer.parseInt(limits[0]);
        final int LIMIT_HEARTACHE = Integer.parseInt(limits[1]);
        final int LIMIT_ROADTRIP = Integer.parseInt(limits[2]);
        final int LIMIT_BLISSFUL = Integer.parseInt(limits[3]);

        final int PLAYLIST_COUNT = Integer.parseInt(inputScanner.nextLine());
        //int[] playlistContribitionCounts = new int[PLAYLIST_COUNT + 1];

        Playlist[] playlists = new Playlist[PLAYLIST_COUNT + 1];

        
        
        //TODO: playlist sizeları dırektolarak atladım
        for (int i = 1; i < PLAYLIST_COUNT + 1; i++) {
            inputScanner.nextLine();
            // playlistContribitionCounts[i] = 0;
            String[] playlistSongs = inputScanner.nextLine().split(" ");
            for (int j = 0; j < playlistSongs.length; j++) {
                int songId = Integer.parseInt(playlistSongs[j]);
                songDatabase[songId].playlistId = i;
                playlists[i].notInBlendHeartache.insert(songDatabase[songId]);
                playlists[i].notInBlendRoadtrip.insert(songDatabase[songId]);
                playlists[i].notInBlendBlissful.insert(songDatabase[songId]);
                heartacheHeap.insert(songDatabase[songId]);
                roadtripHeap.insert(songDatabase[songId]);
                blissfulHeap.insert(songDatabase[songId]);
            }
        }
        */

        /* 
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
        */

        System.out.println("Heartache Heap");

        
        /*  
        // test if sort runs well in more than 2 duplicates
        Song[] test = new Song[6];
        test[0] = new Song(0, "a", 0, new int[]{0, 0, 0}, 0, false);
        test[1] = new Song(1, "a", 0, new int[]{0, 0, 0}, 0, false);
        test[2] = new Song(2, "d", 2, new int[]{0, 0, 0}, 0, false);
        test[3] = new Song(3, "c", 0, new int[]{0, 0, 0}, 0, false);
        test[4] = new Song(4, "a", 0, new int[]{0, 0, 0}, 0, false);
        test[5] = new Song(5, "a", 0, new int[]{0, 0, 0}, 0, false);
        MergeSort.sort(test);
        for (int i = 0; i < test.length; i++) {
            if (i < test.length - 1) {
                if (test[i+1].name == test[i].name) {
                    continue;
                }
            }
            System.out.println(test[i].name);
        }
        */


        
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
        MinHeap heartacheSongs;
        MinHeap roadtripSongs;
        MinHeap blissfulSongs;

        public EpicBlend(){
            this.heartacheSongs = new MinHeap(100, 0);
            this.roadtripSongs = new MinHeap(100, 1);
            this.blissfulSongs = new MinHeap(100, 2);
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

        //chech namehash in case of equal playcounts
        public static void radixSort(Song[] array){
            // First sort by nameHash using binary radix sort
            binaryRadixSort(array, 0, array.length - 1, 0);
            // Then perform counting sort for each digit. As max is 10000, we limit to 5 passes.
            for(int exp = 1; exp <= 10000; exp *= 10){
                countSort(array, exp);
            }
        }
        
        public static void countSort(Song[] array, int exp){
            Song[] output = new Song[array.length];
            int[] count = new int[10];
            Arrays.fill(count, 0);
            for(int i = 0; i < array.length; i++){
                count[(array[i].playCount / exp) % 10]++;
            }
            for(int i = 1; i < 10; i++){
                count[i] += count[i - 1];
            }
            for(int i = array.length - 1; i >= 0; i--){
                output[--count[(array[i].playCount / exp) % 10]] = array[i];
            }
            for(int i = 0; i < array.length; i++){
                array[i] = output[i];
            }
        }

        public static void binaryRadixSort(Song[] array, int left, int right, int bit){
            if(right <= left || bit < 0){
                return;
            }
            int i = left, j = right;
            while(i != j){
                while((array[i].nameHash & (1 << bit)) == 0 && i < j){
                    i++;
                }
                while((array[j].nameHash & (1 << bit)) != 0 && i < j){
                    j--;
                }
                Song temp = array[i];
                array[i] = array[j];
                array[j] = temp;
            }
            if((array[i].nameHash & (1 << bit)) == 0){
                i++;
            }
            binaryRadixSort(array, left, i - 1, bit - 1);
            binaryRadixSort(array, i, right, bit - 1);
        }

        public static void mergeSortbyName(Song[] array, int low, int high){
            if(low < high){
                int mid = (low + high) / 2;
                mergeSortbyName(array, low, mid);
                mergeSortbyName(array, mid + 1, high);
                mergebyName(array, low, mid, high);
            }
        }

        public static void mergebyName(Song[] array, int low, int mid, int high){
            int left = low;
            int right = mid + 1;
            int index = low;
            Song[] temp = new Song[array.length];
            for(int i = low; i <= high; i++){
                temp[i] = array[i];
            }
            while(left <= mid && right <= high){
                if(StringComparator.fastStringCompare(temp[left].name, temp[right].name)){
                    array[index] = temp[left];
                    left++;
                }else {
                    array[index] = temp[right];
                    right++;
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

        public static void sortSongs(Song[] array){
            radixSortByName(array, 5);
            countingSortByPlayCount(array, 10000);
            
        }
        
        public static void countingSortByPlayCount(Song[] array, int maxPlayCount){
            Song[] output = new Song[array.length];
            int[] count = new int[maxPlayCount + 1];
            for(Song song : array){
                count[song.playCount]++;
            }
            for(int i = 1; i <= maxPlayCount; i++){
                count[i] += count[i - 1];
            }
            for(int i = array.length - 1; i >= 0; i--){
                output[--count[array[i].playCount]] = array[i];
            }
            System.arraycopy(output, 0, array, 0, array.length);
        }
        
        public static void radixSortByName(Song[] array, int nameLength){
            for(int exp = nameLength - 1; exp >= 0; exp--){
                countingSortByName(array, exp);
            }
        }
        
        public static void countingSortByName(Song[] array, int exp){
            Song[] output = new Song[array.length];
            int[] count = new int[27]; // Assuming names are lowercase English letters
            for(Song song : array){
                int index = song.name.charAt(exp) - 'a' + 1;
                count[index]++;
            }
            for(int i = 1; i < 27; i++){
                count[i] += count[i - 1];
            }
            for(int i = array.length - 1; i >= 0; i--){
                int index = array[i].name.charAt(exp) - 'a' + 1;
                output[--count[index]] = array[i];
            }
            System.arraycopy(output, 0, array, 0, array.length);
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
            this.insert(new Song(0, "", 0, new int[]{0, 0, 0}, 0, false)); // dummy node
        }

        public MaxHeap(int size){
            this.heap = new Song[size];
            this.size = 0;
            this.scoreIndex = 0;
            this.insert(new Song(0, "", 0, new int[]{0, 0, 0}, 0, false)); // dummy node
        }

        public MaxHeap(int size, int scoreIndex){
            this.heap = new Song[size];
            this.size = 0;
            this.scoreIndex = scoreIndex;
            this.insert(new Song(0, "", 0, new int[]{0, 0, 0}, 0, false)); // dummy node
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

        public Song peek(){
            return heap[1];
        }

    }

    public static class MinHeap {
        int scoreIndex; // for song scores
        Song[] heap;
        int size;

        public MinHeap(){
            this.heap = new Song[100];
            this.size = 0;
            this.scoreIndex = 0;
            this.insert(new Song(0, "", 0, new int[]{0, 0, 0}, 0, false)); // dummy node
        }

        public MinHeap(int size){
            this.heap = new Song[size];
            this.size = 0;
            this.scoreIndex = 0;
            this.insert(new Song(0, "", 0, new int[]{0, 0, 0}, 0, false)); // dummy node
        }

        public MinHeap(int size, int scoreIndex){
            this.heap = new Song[size];
            this.size = 0;
            this.scoreIndex = scoreIndex;
            this.insert(new Song(0, "", 0, new int[]{0, 0, 0}, 0, false)); // dummy node
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
            while(current > 1 && heap[current].scores[scoreIndex] < heap[parent(current)].scores[scoreIndex]){
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

        public void minHeapify(int pos){
            if (pos * 2 >= size) { // leaf node
                return;
            }
            int left = leftChild(pos);
            int right = rightChild(pos);
            if(right < size){ // Check if right child exists
                if(heap[pos].scores[scoreIndex] > heap[left].scores[scoreIndex] || heap[pos].scores[scoreIndex] > heap[right].scores[scoreIndex]){
                    if(heap[left].scores[scoreIndex] < heap[right].scores[scoreIndex]){
                        swap(pos, left);
                        minHeapify(left);
                    }else{
                        swap(pos, right);
                        minHeapify(right);
                    }
                }
            }else if(left < size){ // Check if left child exists
                if(heap[pos].scores[scoreIndex] > heap[left].scores[scoreIndex]){
                    swap(pos, left);
                    minHeapify(left);
                }
            }
        }

        public Song remove(){
            Song popped = heap[1];
            heap[1] = heap[size - 1];
            heap[size - 1] = null;
            size--;
            minHeapify(1);
            return popped;
        }

        public Song peek(){
            return heap[1];
        }
    }

    public static class Playlist {
        MaxHeap notInBlendHeartache;
        MinHeap inBlendHeartache;
        MaxHeap notInBlendRoadtrip;
        MinHeap inBlendRoadtrip;
        MaxHeap notInBlendBlissful;
        MinHeap inBlendBlissful;
        int songContributionCount;

        public Playlist(){
            this.notInBlendHeartache = new MaxHeap(100, 0);
            this.inBlendHeartache = new MinHeap(100, 0);
            this.notInBlendRoadtrip = new MaxHeap(100, 1);
            this.inBlendRoadtrip = new MinHeap(100, 1);
            this.notInBlendBlissful = new MaxHeap(100, 2);
            this.inBlendBlissful = new MinHeap(100, 2);
            this.songContributionCount = 0;
        }


    }

    public static class Song{
        int id;
        String name;
        int playCount;
        int[] scores; // [heartache, roadtrip, blissful]
        int playlistId;
        boolean inBlend;
        int nameHash;

        public Song(){
            this.id = 0;
            this.scores = new int[3];
            this.playlistId = 0;
            this.playCount = 0;
            this.name = "";
            this.inBlend = false;
            this.nameHash = 0;
        }

        public Song(int id, String name, int playCount, int[] scores, boolean inBlend){
            this.id = id;
            this.name = name;
            this.playCount = playCount;
            this.scores = scores;
            this.playlistId = 0;
            this.inBlend = inBlend;
            this.nameHash = computeValue(name);
        }

        public Song(int id, String name, int playCount, int[] scores, int playlistId, boolean inBlend){
            this.id = id;
            this.name = name;
            this.playCount = playCount;
            this.scores = scores;
            this.playlistId = playlistId;
            this.inBlend = inBlend;
            this.nameHash = computeValue(name);
        }

        public static int computeValue(String s) {
            int value = 0;
            for (char c : s.toCharArray()) {
                value = value * 26 + (c - 'a' + 1);
            }
            return value;
        }

        
    }

    public static class StringComparator {
        public static boolean fastStringCompare(String str1, String str2) {
            for (int i = 0; i < 5; i++) {
                if (str1.charAt(i) < str2.charAt(i)) {
                    return false;
                }
            }
            return true;
        }
    }
}
