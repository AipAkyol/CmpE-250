import java.io.BufferedReader;
import java.io.File;
import java.util.Scanner;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Random;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;

public class Project3 {
    public static void main(String[] args) throws FileNotFoundException, IOException {

        long initialAllTime = System.currentTimeMillis();
        
        File songFile = new File("C:\\BOUN\\CmpE250\\CmpE-250\\Project-3\\test-cases\\songs.txt");
        BufferedReader songScanner = new BufferedReader(new FileReader(songFile));
        
        Song[] songDatabase = new Song[Integer.parseInt(songScanner.readLine())];

        while (songScanner.ready()) {
            String[] songInfo = songScanner.readLine().split(" ");
            int id = Integer.parseInt(songInfo[0]);
            String name = songInfo[1];
            int playCount = Integer.parseInt(songInfo[2]);
            int[] scores = new int[3];
            for (int i = 0; i < 3; i++) {
                scores[i] = Integer.parseInt(songInfo[3 + i]);
            }
            songDatabase[id - 1] = new Song(id, name, playCount, scores); // id starts from 1 but index starts from 0 so use id-1
        }
        songScanner.close();
        long initialTime = System.currentTimeMillis();
        InfoDatabase infoDatabase = new InfoDatabase(songDatabase.length);
        /*Song[] sortedSongsbyPlayCount = new Song[songDatabase.length];
        Song[] sortedSongsbyHeartache = new Song[songDatabase.length];
        Song[] sortedSongsbyRoadTrip = new Song[songDatabase.length];
        Song[] sortedSongsbyBlissful = new Song[songDatabase.length]; */
        System.arraycopy(songDatabase, 0, infoDatabase.sortedSongsbyPlayCount, 0, songDatabase.length);
        MergeSort.radixSortByName(infoDatabase.sortedSongsbyPlayCount, MergeSort.findLongestNameLength(songDatabase)); //TODO: be careful
        System.arraycopy(infoDatabase.sortedSongsbyPlayCount, 0, infoDatabase.sortedSongsbyHeartache, 0, songDatabase.length); //namely sorted
        System.arraycopy(infoDatabase.sortedSongsbyPlayCount, 0, infoDatabase.sortedSongsbyRoadTrip, 0, songDatabase.length); //namely sorted
        System.arraycopy(infoDatabase.sortedSongsbyPlayCount, 0, infoDatabase.sortedSongsbyBlissful, 0, songDatabase.length); //namely sorted
        MergeSort.countingSortByPlayCount(infoDatabase.sortedSongsbyPlayCount, 10000);
        MergeSort.countingSortByHeartache(infoDatabase.sortedSongsbyHeartache, 100);
        MergeSort.countingSortByRoadTrip(infoDatabase.sortedSongsbyRoadTrip, 100);
        MergeSort.countingSortByBlissful(infoDatabase.sortedSongsbyBlissful, 100);
        long finalTime = System.currentTimeMillis();
        System.out.println("Merge Sort: " + (finalTime - initialTime) + " ms");
        Song dummySong = new Song(0, "DummySong", 0, new int[]{0, 0, 0});
        dummySong.heartacheAIP = -1;
        dummySong.roadtripAIP = -1;
        dummySong.blissfulAIP = -1;

        Song[] temp = new Song[songDatabase.length + 1];
        System.arraycopy(songDatabase, 0, temp, 1, songDatabase.length);
        temp[0] = dummySong;
        songDatabase = temp;

        for (int i = 0; i < infoDatabase.sortedSongsbyBlissful.length; i++) {
            infoDatabase.sortedSongsbyHeartache[i].heartacheAIP = i;
            infoDatabase.sortedSongsbyRoadTrip[i].roadtripAIP = i;
            infoDatabase.sortedSongsbyBlissful[i].blissfulAIP = i;
        }
        //testSortSongs();

        /*for (int i = 0; i < songDatabase.length; i++) {
            //if (i%2 == 0) {
                songDatabase[i].playlistId = 1;
            //}
        }
        int a;
        long initialTime2 = System.nanoTime();
        for(int i = 0; i < 1000000; i++){
            for(int j = 0; j < songDatabase.length; j++){
                if (songDatabase[j].playlistId > 0) {
                    a=1;
                }
            }
        }
        long finalTime2 = System.nanoTime();
        System.out.println("Linear Search: " + (finalTime2 - initialTime2) + " ns");*/

        /* 
        // TODO: check starting sizes
        MaxHeap heartacheHeap = new MaxHeap(100, 0);
        MaxHeap roadtripHeap = new MaxHeap(100, 1);
        MaxHeap blissfulHeap = new MaxHeap(100, 2);
        */
        
        File inputFile = new File("C:\\BOUN\\CmpE250\\CmpE-250\\Project-3\\test-cases\\inputs\\tiny_playlists_large.txt");
        BufferedReader inputScanner = new BufferedReader(new FileReader(inputFile));

        String[] limits = inputScanner.readLine().split(" ");
        infoDatabase.limitPlaylist = Integer.parseInt(limits[0]);
        infoDatabase.limitHeartache = Integer.parseInt(limits[1]);
        infoDatabase.limitRoadtrip = Integer.parseInt(limits[2]);
        infoDatabase.limitBlissful = Integer.parseInt(limits[3]);

        final int PLAYLIST_COUNT = Integer.parseInt(inputScanner.readLine());
        //int[] playlistContribitionCounts = new int[PLAYLIST_COUNT + 1];

        Playlist[] playlists = new Playlist[PLAYLIST_COUNT + 1]; //note that playlist id starts from 1 and index 0 is null

        int[][] initialState = new int[PLAYLIST_COUNT + 1][];

        int totalSongCount = 0;

        //TODO: playlist sizeları dırektolarak atladım
        for (int i = 1; i < PLAYLIST_COUNT + 1; i++) {
            playlists[i] = new Playlist();
            String[] playlistInfo = inputScanner.readLine().split(" ");
            initialState[i] = new int[Integer.parseInt(playlistInfo[1])];
            // playlistContribitionCounts[i] = 0;
            String[] playlistSongs = inputScanner.readLine().split(" ");
            if (playlistSongs[0].equals("")) {
                continue;
            }
            for (int j = 0; j < playlistSongs.length; j++) {
                int songId = Integer.parseInt(playlistSongs[j]);
                songDatabase[songId].playlistId = i; 
                initialState[i][j] = songId;
                totalSongCount++;
                //addSong(songId, songDatabase, playlists, infoDatabase, false);
                
                
                /*playlists[i].notInBlendHeartache.insert(songDatabase[songId]);
                playlists[i].notInBlendRoadtrip.insert(songDatabase[songId]);
                playlists[i].notInBlendBlissful.insert(songDatabase[songId]);
                heartacheHeap.insert(songDatabase[songId]);
                roadtripHeap.insert(songDatabase[songId]);
                blissfulHeap.insert(songDatabase[songId]);*/
            }
        }


        long initialRandomTime = System.currentTimeMillis();
        /* 
        int[] initialSongIds = new int[totalSongCount];
        
        int index = 0;
        for (int i = 1; i < initialState.length; i++) {
            for (int j = 0; j < initialState[i].length; j++) {
                initialSongIds[index] = initialState[i][j];
                index++;
            }
        }


        Random rand = new Random();

        for (int i = 0; i < initialSongIds.length; i++) {
            int randomIndexToSwap = rand.nextInt(initialSongIds.length);
            int temp2 = initialSongIds[randomIndexToSwap];
            initialSongIds[randomIndexToSwap] = initialSongIds[i];
            initialSongIds[i] = temp2;
        }
        */

        int[] initialSongIds = concatenateArrays(initialState);

        long finalRandomTime = System.currentTimeMillis();
        System.out.println("Randomize: " + (finalRandomTime - initialRandomTime) + " ms");

        for (int i = 0; i < initialSongIds.length; i++) {
            addSong(initialSongIds[i], songDatabase, playlists, infoDatabase, false);
        }

        //TODO: remove this
        PrintStream console = System.out; // save console

        File outputFile = new File("C:\\BOUN\\CmpE250\\CmpE-250\\Project-3\\output.txt");
        FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
        PrintStream printStream = new PrintStream(fileOutputStream);
        System.setOut(printStream);  // set system out to the file


        final int EVENT_COUNT = Integer.parseInt(inputScanner.readLine());
        for (int i = 0; i < EVENT_COUNT; i++) {
            String[] event = inputScanner.readLine().split(" ");
            switch (event[0]) {
                case "ADD":
                    int songId = Integer.parseInt(event[1]);
                    songDatabase[songId].playlistId = Integer.parseInt(event[2]);
                    addSong(songId, songDatabase, playlists, infoDatabase, true);
                    break;
                case "REM":
                    int songId2 = Integer.parseInt(event[1]);
                    removeSong(songId2, songDatabase, playlists, infoDatabase);
                break;
                case "ASK": // print all the songs ids in blend in playcount order
                //TODO: why ters itarate?
                    StringBuilder sb = new StringBuilder();
                    for (int j = infoDatabase.sortedSongsbyPlayCount.length - 1; j >= 0; j--) {
                        if (infoDatabase.sortedSongsbyPlayCount[j].inBlendByHeartache
                                || infoDatabase.sortedSongsbyPlayCount[j].inBlendByRoadtrip
                                || infoDatabase.sortedSongsbyPlayCount[j].inBlendByBlissful
                                ) {
                            sb.append(infoDatabase.sortedSongsbyPlayCount[j].id).append(" ");
                        }
                    }
                    String result = sb.toString().trim();
                    System.out.println(result);
                default:
                    //System.out.println("Invalid event type");    
                    break;
            }
        }
        
        System.setOut(console); // set system out to console again


        inputScanner.close();

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

        long endAllTime = System.currentTimeMillis();
        System.out.println("Total Time: " + (endAllTime - initialAllTime) + " ms");

    }

    private static int[] concatenateArrays(int[][] array2D) {
        // Find the maximum row length
        int maxRowLength = 0;
        for (int[] row : array2D) {
            if (row != null) {
                maxRowLength = Math.max(maxRowLength, row.length);
            }
        }

        // Initialize the 1D array with the calculated length
        int[] resultArray = new int[array2D.length * maxRowLength];

        // Index to keep track of the current position in the result array
        int currentIndex = 0;

        // Iterate through each column
        for (int col = 0; col < maxRowLength; col++) {
            // Iterate through each row
            for (int[] row : array2D) {
                // Check if the current row is not null and has elements in the current column
                if (row != null && col < row.length) {
                    // Copy the element to the result array
                    resultArray[currentIndex++] = row[col];
                }
            }
        }

        // Trim the result array to remove trailing zeros
        return Arrays.copyOf(resultArray, currentIndex);
    }


    //TODO: yavas olursa cift itarasyon teke cevir
    //TODO: yavas olursa info database iptalet
    public static void addSong(int songId, Song[] songDatabase, Playlist[] playlists, InfoDatabase infoDatabase, boolean isEvent) {
        //if (songId == 4) {
        //    System.out.println("sad");
        //}
        Song song = songDatabase[songId]; // because song id starts from 1 but index starts from 0
        Playlist playlist = playlists[song.playlistId];
        
        /* HEARTACHE PART */
        int heartacheIn = 0;
        int heartacheOut = 0;
        if (infoDatabase.heartacheCountInBlend < infoDatabase.limitHeartache) {  // blend is not full
            if (infoDatabase.heartacheCountInBlend == 0) { //blend is empty
                infoDatabase.minHeartacheInBlend = song.id;
                infoDatabase.heartacheCountInBlend += 1;
                song.inBlendByHeartache = true;
                heartacheIn = songId;
                playlist.inBlendHeartacheMin = song.id;
                playlist.heartacheContributionCount += 1;
            } else { // blend is not empty but not full
                if (playlist.heartacheContributionCount < infoDatabase.limitPlaylist) { //playlist did not reach its limit
                    if (playlist.heartacheContributionCount == 0) { // playlist in blend is empty 
                        playlist.inBlendHeartacheMin = song.id;
                        playlist.heartacheContributionCount += 1;
                        infoDatabase.heartacheCountInBlend += 1;
                        song.inBlendByHeartache = true;
                        heartacheIn = songId;
                        if (song.heartacheAIP < songDatabase[infoDatabase.minHeartacheInBlend].heartacheAIP) {
                            infoDatabase.minHeartacheInBlend = song.id;
                        }
                    } else { // playlist in blend is not empty but not full
                        playlist.heartacheContributionCount += 1;
                        infoDatabase.heartacheCountInBlend += 1;
                        song.inBlendByHeartache = true;
                        heartacheIn = songId;
                        if (song.heartacheAIP < songDatabase[infoDatabase.minHeartacheInBlend].heartacheAIP) {
                            infoDatabase.minHeartacheInBlend = song.id;
                        }
                        if (song.heartacheAIP < songDatabase[playlist.inBlendHeartacheMin].heartacheAIP) {
                            playlist.inBlendHeartacheMin = song.id;
                        }
                    }
                } else { // playlist reached its limit
                    if (song.heartacheAIP > songDatabase[playlist.inBlendHeartacheMin].heartacheAIP) { // bigger than min so remove min from blend
                        song.inBlendByHeartache = true;
                        heartacheIn = songId;
                        Song minInBlendfromPlaylist = songDatabase[playlist.inBlendHeartacheMin];
                        minInBlendfromPlaylist.inBlendByHeartache = false;
                        heartacheOut = minInBlendfromPlaylist.id;
                        //itarate tru heartachesort start from min and find new min in that playlist
                        for (int i = minInBlendfromPlaylist.heartacheAIP + 1; i < infoDatabase.sortedSongsbyHeartache.length; i++) {
                            if (infoDatabase.sortedSongsbyHeartache[i].playlistId == song.playlistId
                                    && infoDatabase.sortedSongsbyHeartache[i].inBlendByHeartache) {
                                playlist.inBlendHeartacheMin = infoDatabase.sortedSongsbyHeartache[i].id;
                                break;
                            }
                        }
                        if (minInBlendfromPlaylist.id == infoDatabase.minHeartacheInBlend) {
                            //itarate tru heartachesort start from minbleend and find new min in that playlist
                            for (int i = minInBlendfromPlaylist.heartacheAIP + 1; i < infoDatabase.sortedSongsbyHeartache.length; i++) {
                                if (infoDatabase.sortedSongsbyHeartache[i].inBlendByHeartache) {
                                    infoDatabase.minHeartacheInBlend = infoDatabase.sortedSongsbyHeartache[i].id;
                                    break;
                                }
                            }
                        }
                    } else {
                        // sad?*
                    }
                }
            }
        } else { // blend is full
            if (song.heartacheAIP > songDatabase[infoDatabase.minHeartacheInBlend].heartacheAIP) { // song is eligable
                Song minInBlend = songDatabase[infoDatabase.minHeartacheInBlend];
                // burada kaldım
                if (playlist.heartacheContributionCount < infoDatabase.limitPlaylist) { // playlist did not reach its limit
                    if (playlist.heartacheContributionCount == 0) { // playlist in blend is empty 
                        playlist.inBlendHeartacheMin = song.id;
                        playlist.heartacheContributionCount += 1;
                        //infoDatabase.heartacheCountInBlend += 1;  because blend count is not changed
                        song.inBlendByHeartache = true;
                        heartacheIn = songId;
                        // remove min from blend
                        minInBlend.inBlendByHeartache = false;
                        heartacheOut = minInBlend.id;
                        playlists[minInBlend.playlistId].heartacheContributionCount -= 1;
                        //itarate tru heartachesort start from old min and find new min in that blend for playlist of oldmin
                        for (int i = minInBlend.heartacheAIP + 1; i < infoDatabase.sortedSongsbyHeartache.length; i++) {
                            if (infoDatabase.sortedSongsbyHeartache[i].playlistId == minInBlend.playlistId
                                    && infoDatabase.sortedSongsbyHeartache[i].inBlendByHeartache) {
                                playlists[minInBlend.playlistId].inBlendHeartacheMin = infoDatabase.sortedSongsbyHeartache[i].id;
                                break;
                            }
                        }
                        //itarate tru heartachesort start from old min and find new min in that blend
                        for (int i = minInBlend.heartacheAIP + 1; i < infoDatabase.sortedSongsbyHeartache.length; i++) {
                            if (infoDatabase.sortedSongsbyHeartache[i].inBlendByHeartache) {
                                infoDatabase.minHeartacheInBlend = infoDatabase.sortedSongsbyHeartache[i].id;
                                break;
                            }
                        }
                    } else { // playlist in blend is not empty but not full
                        playlist.heartacheContributionCount += 1;
                        //infoDatabase.heartacheCountInBlend += 1;
                        song.inBlendByHeartache = true;
                        heartacheIn = songId;
                        // remove min from blend
                        minInBlend.inBlendByHeartache = false;
                        heartacheOut = minInBlend.id;
                        playlists[minInBlend.playlistId].heartacheContributionCount -= 1;
                        //itarate tru heartachesort start from old min and find new min in that blend for playlist of oldmin
                        for (int i = minInBlend.heartacheAIP + 1; i < infoDatabase.sortedSongsbyHeartache.length; i++) {
                            if (infoDatabase.sortedSongsbyHeartache[i].playlistId == minInBlend.playlistId
                                    && infoDatabase.sortedSongsbyHeartache[i].inBlendByHeartache) {
                                playlists[minInBlend.playlistId].inBlendHeartacheMin = infoDatabase.sortedSongsbyHeartache[i].id;
                                break;
                            }
                        }
                        //itarate tru heartachesort start from old min and find new min in that blend
                        for (int i = minInBlend.heartacheAIP + 1; i < infoDatabase.sortedSongsbyHeartache.length; i++) {
                            if (infoDatabase.sortedSongsbyHeartache[i].inBlendByHeartache) {
                                infoDatabase.minHeartacheInBlend = infoDatabase.sortedSongsbyHeartache[i].id;
                                break;
                            }
                        }
                        if (song.heartacheAIP < songDatabase[playlist.inBlendHeartacheMin].heartacheAIP) {
                            playlist.inBlendHeartacheMin = song.id;
                        }
                    }
                } else { // playlist reached its limit
                    if (song.heartacheAIP > songDatabase[playlist.inBlendHeartacheMin].heartacheAIP) { // bigger than min so remove min from blend
                        song.inBlendByHeartache = true;
                        heartacheIn = songId;
                        Song minInBlendfromPlaylist = songDatabase[playlist.inBlendHeartacheMin];
                        minInBlendfromPlaylist.inBlendByHeartache = false;
                        heartacheOut = minInBlendfromPlaylist.id;
                        //itarate tru heartachesort start from min and find new min in that playlist
                        for (int i = minInBlendfromPlaylist.heartacheAIP + 1; i < infoDatabase.sortedSongsbyHeartache.length; i++) {
                            if (infoDatabase.sortedSongsbyHeartache[i].playlistId == song.playlistId
                                    && infoDatabase.sortedSongsbyHeartache[i].inBlendByHeartache) {
                                playlist.inBlendHeartacheMin = infoDatabase.sortedSongsbyHeartache[i].id;
                                break;
                            }
                        }
                        if (minInBlendfromPlaylist.id == infoDatabase.minHeartacheInBlend) {
                            //itarate tru heartachesort start from minbleend and find new min for bledn
                            for (int i = minInBlendfromPlaylist.heartacheAIP + 1; i < infoDatabase.sortedSongsbyHeartache.length; i++) {
                                if (infoDatabase.sortedSongsbyHeartache[i].inBlendByHeartache) {
                                    infoDatabase.minHeartacheInBlend = infoDatabase.sortedSongsbyHeartache[i].id;
                                    break;
                                }
                            }
                        }
                    } else {
                        // sad?*
                    }
                } 
            }  else { // song is not eligable
            // sad ?
            }
        }
        
        /* HEARTACHE PART */

        /* ROADTRIP PART */
        int roadtripIn = 0;
        int roadtripOut = 0;
        if (infoDatabase.roadtripCountInBlend < infoDatabase.limitRoadtrip) {  // blend is not full
            if (infoDatabase.roadtripCountInBlend == 0) { //blend is empty
                infoDatabase.minRoadtripInBlend = song.id;
                infoDatabase.roadtripCountInBlend += 1;
                song.inBlendByRoadtrip = true;
                roadtripIn = songId;
                playlist.inBlendRoadtripMin = song.id;
                playlist.roadtripContributionCount += 1;
            } else { // blend is not empty but not full
                if (playlist.roadtripContributionCount < infoDatabase.limitPlaylist) { //playlist did not reach its limit
                    if (playlist.roadtripContributionCount == 0) { // playlist in blend is empty 
                        playlist.inBlendRoadtripMin = song.id;
                        playlist.roadtripContributionCount += 1;
                        infoDatabase.roadtripCountInBlend += 1;
                        song.inBlendByRoadtrip = true;
                        roadtripIn = songId;
                        if (song.roadtripAIP < songDatabase[infoDatabase.minRoadtripInBlend].roadtripAIP) {
                            infoDatabase.minRoadtripInBlend = song.id;
                        }
                    } else { // playlist in blend is not empty but not full
                        playlist.roadtripContributionCount += 1;
                        infoDatabase.roadtripCountInBlend += 1;
                        song.inBlendByRoadtrip = true;
                        roadtripIn = songId;
                        if (song.roadtripAIP < songDatabase[infoDatabase.minRoadtripInBlend].roadtripAIP) {
                            infoDatabase.minRoadtripInBlend = song.id;
                        }
                        if (song.roadtripAIP < songDatabase[playlist.inBlendRoadtripMin].roadtripAIP) {
                            playlist.inBlendRoadtripMin = song.id;
                        }
                    }
                } else { // playlist reached its limit
                    if (song.roadtripAIP > songDatabase[playlist.inBlendRoadtripMin].roadtripAIP) { // bigger than min so remove min from blend
                        song.inBlendByRoadtrip = true;
                        roadtripIn = songId;
                        Song minInBlendfromPlaylist = songDatabase[playlist.inBlendRoadtripMin];
                        minInBlendfromPlaylist.inBlendByRoadtrip = false;
                        roadtripOut = minInBlendfromPlaylist.id;
                        //itarate tru roadtripsort start from min and find new min in that playlist
                        for (int i = minInBlendfromPlaylist.roadtripAIP + 1; i < infoDatabase.sortedSongsbyRoadTrip.length; i++) {
                            if (infoDatabase.sortedSongsbyRoadTrip[i].playlistId == song.playlistId
                                    && infoDatabase.sortedSongsbyRoadTrip[i].inBlendByRoadtrip) {
                                playlist.inBlendRoadtripMin = infoDatabase.sortedSongsbyRoadTrip[i].id;
                                break;
                            }
                        }
                        if (minInBlendfromPlaylist.id == infoDatabase.minRoadtripInBlend) {
                            //itarate tru roadtripsort start from minbleend and find new min for blend
                            for (int i = minInBlendfromPlaylist.roadtripAIP + 1; i < infoDatabase.sortedSongsbyRoadTrip.length; i++) {
                                if (infoDatabase.sortedSongsbyRoadTrip[i].inBlendByRoadtrip) {
                                    infoDatabase.minRoadtripInBlend = infoDatabase.sortedSongsbyRoadTrip[i].id;
                                    break;
                                }
                            }
                        }
                    } else {
                        // sad?*
                    }
                }
            }
        } else { // blend is full
            if (song.roadtripAIP > songDatabase[infoDatabase.minRoadtripInBlend].roadtripAIP) { // song is eligable
                Song minInBlend = songDatabase[infoDatabase.minRoadtripInBlend];
                // burada kaldım
                if (playlist.roadtripContributionCount < infoDatabase.limitPlaylist) { // playlist did not reach its limit
                    if (playlist.roadtripContributionCount == 0) { // playlist in blend is empty 
                        playlist.inBlendRoadtripMin = song.id;
                        playlist.roadtripContributionCount += 1;
                        //infoDatabase.roadtripCountInBlend += 1;  because blend count is not changed
                        song.inBlendByRoadtrip = true;
                        roadtripIn = songId;
                        // remove min from blend
                        minInBlend.inBlendByRoadtrip = false;
                        roadtripOut = minInBlend.id;
                        playlists[minInBlend.playlistId].roadtripContributionCount -= 1;
                        //itarate tru roadtripsort start from old min and find new min in that blend for playlist of oldmin
                        for (int i = minInBlend.roadtripAIP + 1; i < infoDatabase.sortedSongsbyRoadTrip.length; i++) {
                            if (infoDatabase.sortedSongsbyRoadTrip[i].playlistId == minInBlend.playlistId
                                    && infoDatabase.sortedSongsbyRoadTrip[i].inBlendByRoadtrip) {
                                playlists[minInBlend.playlistId].inBlendRoadtripMin = infoDatabase.sortedSongsbyRoadTrip[i].id;
                                break;
                            }
                        }
                        //itarate tru roadtripsort start from old min and find new min in that blend
                        for (int i = minInBlend.roadtripAIP + 1; i < infoDatabase.sortedSongsbyRoadTrip.length; i++) {
                            if (infoDatabase.sortedSongsbyRoadTrip[i].inBlendByRoadtrip) {
                                infoDatabase.minRoadtripInBlend = infoDatabase.sortedSongsbyRoadTrip[i].id;
                                break;
                            }
                        }
                    } else { // playlist in blend is not empty but not full
                        playlist.roadtripContributionCount += 1;
                        //infoDatabase.roadtripCountInBlend += 1;
                        song.inBlendByRoadtrip = true;
                        roadtripIn = songId;
                        // remove min from blend
                        minInBlend.inBlendByRoadtrip = false;
                        roadtripOut = minInBlend.id;
                        playlists[minInBlend.playlistId].roadtripContributionCount -= 1;
                        //itarate tru roadtripsort start from old min and find new min in that blend for playlist of oldmin
                        for (int i = minInBlend.roadtripAIP + 1; i < infoDatabase.sortedSongsbyRoadTrip.length; i++) {
                            if (infoDatabase.sortedSongsbyRoadTrip[i].playlistId == minInBlend.playlistId
                                    && infoDatabase.sortedSongsbyRoadTrip[i].inBlendByRoadtrip) {
                                playlists[minInBlend.playlistId].inBlendRoadtripMin = infoDatabase.sortedSongsbyRoadTrip[i].id;
                                break;
                            }
                        }
                        //itarate tru roadtripsort start from old min and find new min in that blend
                        for (int i = minInBlend.roadtripAIP + 1; i < infoDatabase.sortedSongsbyRoadTrip.length; i++) {
                            if (infoDatabase.sortedSongsbyRoadTrip[i].inBlendByRoadtrip) {
                                infoDatabase.minRoadtripInBlend = infoDatabase.sortedSongsbyRoadTrip[i].id;
                                break;
                            }
                        }
                        if (song.roadtripAIP < songDatabase[playlist.inBlendRoadtripMin].roadtripAIP) {
                            playlist.inBlendRoadtripMin = song.id;
                        }
                    }
                } else { // playlist reached its limit
                    if (song.roadtripAIP > songDatabase[playlist.inBlendRoadtripMin].roadtripAIP) { // bigger than min so remove min from blend
                        song.inBlendByRoadtrip = true;
                        roadtripIn = songId;
                        Song minInBlendfromPlaylist = songDatabase[playlist.inBlendRoadtripMin];
                        minInBlendfromPlaylist.inBlendByRoadtrip = false;
                        roadtripOut = minInBlendfromPlaylist.id;
                        //itarate tru roadtripsort start from min and find new min in that playlist
                        for (int i = minInBlendfromPlaylist.roadtripAIP + 1; i < infoDatabase.sortedSongsbyRoadTrip.length; i++) {
                            if (infoDatabase.sortedSongsbyRoadTrip[i].playlistId == song.playlistId
                                    && infoDatabase.sortedSongsbyRoadTrip[i].inBlendByRoadtrip) {
                                playlist.inBlendRoadtripMin = infoDatabase.sortedSongsbyRoadTrip[i].id;
                                break;
                            }
                        }
                        if (minInBlendfromPlaylist.id == infoDatabase.minRoadtripInBlend) {
                            //itarate tru roadtripsort start from minbleend and find new min for blend
                            for (int i = minInBlendfromPlaylist.roadtripAIP + 1; i < infoDatabase.sortedSongsbyRoadTrip.length; i++) {
                                if (infoDatabase.sortedSongsbyRoadTrip[i].inBlendByRoadtrip) {
                                    infoDatabase.minRoadtripInBlend = infoDatabase.sortedSongsbyRoadTrip[i].id;
                                    break;
                                }
                            }
                        }
                    } else {
                        // sad?*
                    }
                } 
            }  else { // song is not eligable
            // sad ?
            }
        }
        /* ROADTRIP PART */

        /* BLISSFUL PART */
        int blissfulIn = 0;
        int blissfulOut = 0;
        if (infoDatabase.blissfulCountInBlend < infoDatabase.limitBlissful) {  // blend is not full
            if (infoDatabase.blissfulCountInBlend == 0) { //blend is empty
                infoDatabase.minBlissfulInBlend = song.id;
                infoDatabase.blissfulCountInBlend += 1;
                song.inBlendByBlissful = true;
                blissfulIn = songId;
                playlist.inBlendBlissfulMin = song.id;
                playlist.blissfulContributionCount += 1;
            } else { // blend is not empty but not full
                if (playlist.blissfulContributionCount < infoDatabase.limitPlaylist) { //playlist did not reach its limit
                    if (playlist.blissfulContributionCount == 0) { // playlist in blend is empty
                        playlist.inBlendBlissfulMin = song.id;
                        playlist.blissfulContributionCount += 1;
                        infoDatabase.blissfulCountInBlend += 1;
                        song.inBlendByBlissful = true;
                        blissfulIn = songId;
                        if (song.blissfulAIP < songDatabase[infoDatabase.minBlissfulInBlend].blissfulAIP) {
                            infoDatabase.minBlissfulInBlend = song.id;
                        }
                    } else { // playlist in blend is not empty but not full
                        playlist.blissfulContributionCount += 1;
                        infoDatabase.blissfulCountInBlend += 1;
                        song.inBlendByBlissful = true;
                        blissfulIn = songId;
                        if (song.blissfulAIP < songDatabase[infoDatabase.minBlissfulInBlend].blissfulAIP) {
                            infoDatabase.minBlissfulInBlend = song.id;
                        }
                        if (song.blissfulAIP < songDatabase[playlist.inBlendBlissfulMin].blissfulAIP) {
                            playlist.inBlendBlissfulMin = song.id;
                        }
                    }
                } else { // playlist reached its limit
                    if (song.blissfulAIP > songDatabase[playlist.inBlendBlissfulMin].blissfulAIP) { // bigger than min so remove min from blend
                        song.inBlendByBlissful = true;
                        blissfulIn = songId;
                        Song minInBlendfromPlaylist = songDatabase[playlist.inBlendBlissfulMin];
                        minInBlendfromPlaylist.inBlendByBlissful = false;
                        blissfulOut = minInBlendfromPlaylist.id;
                        //itarate tru blissfulsort start from min and find new min in that playlist
                        for (int i = minInBlendfromPlaylist.blissfulAIP + 1; i < infoDatabase.sortedSongsbyBlissful.length; i++) {
                            if (infoDatabase.sortedSongsbyBlissful[i].playlistId == song.playlistId
                                    && infoDatabase.sortedSongsbyBlissful[i].inBlendByBlissful) {
                                playlist.inBlendBlissfulMin = infoDatabase.sortedSongsbyBlissful[i].id;
                                break;
                            }
                        }
                        if (minInBlendfromPlaylist.id == infoDatabase.minBlissfulInBlend) {
                            //itarate tru blissfulsort start from minbleend and find new min for blend
                            for (int i = minInBlendfromPlaylist.blissfulAIP + 1; i < infoDatabase.sortedSongsbyBlissful.length; i++) {
                                if (infoDatabase.sortedSongsbyBlissful[i].inBlendByBlissful) {
                                    infoDatabase.minBlissfulInBlend = infoDatabase.sortedSongsbyBlissful[i].id;
                                    break;
                                }
                            }
                        }
                    } else {
                        // sad?*
                    }
                }
            }
        } else { // blend is full
            if (song.blissfulAIP > songDatabase[infoDatabase.minBlissfulInBlend].blissfulAIP) { // song is eligable
                Song minInBlend = songDatabase[infoDatabase.minBlissfulInBlend];
                // burada kaldım
                if (playlist.blissfulContributionCount < infoDatabase.limitPlaylist) { // playlist did not reach its limit
                    if (playlist.blissfulContributionCount == 0) { // playlist in blend is empty
                        playlist.inBlendBlissfulMin = song.id;
                        playlist.blissfulContributionCount += 1;
                        //infoDatabase.blissfulCountInBlend += 1;  because blend count is not changed
                        song.inBlendByBlissful = true;
                        blissfulIn = songId;
                        // remove min from blend
                        minInBlend.inBlendByBlissful = false;
                        blissfulOut = minInBlend.id;
                        playlists[minInBlend.playlistId].blissfulContributionCount -= 1;
                        //itarate tru blissfulsort start from old min and find new min in that blend for playlist of oldmin
                        for (int i = minInBlend.blissfulAIP + 1; i < infoDatabase.sortedSongsbyBlissful.length; i++) {
                            if (infoDatabase.sortedSongsbyBlissful[i].playlistId == minInBlend.playlistId
                                    && infoDatabase.sortedSongsbyBlissful[i].inBlendByBlissful) {
                                playlists[minInBlend.playlistId].inBlendBlissfulMin = infoDatabase.sortedSongsbyBlissful[i].id;
                                break;
                            }
                        }
                        //itarate tru blissfulsort start from old min and find new min in that blend
                        for (int i = minInBlend.blissfulAIP + 1; i < infoDatabase.sortedSongsbyBlissful.length; i++) {
                            if (infoDatabase.sortedSongsbyBlissful[i].inBlendByBlissful) {
                                infoDatabase.minBlissfulInBlend = infoDatabase.sortedSongsbyBlissful[i].id;
                                break;
                            }
                        }
                    } else { // playlist in blend is not empty but not full
                        playlist.blissfulContributionCount += 1;
                        //infoDatabase.blissfulCountInBlend += 1;
                        song.inBlendByBlissful = true;
                        blissfulIn = songId;
                        // remove min from blend
                        minInBlend.inBlendByBlissful = false;
                        blissfulOut = minInBlend.id;
                        playlists[minInBlend.playlistId].blissfulContributionCount -= 1;
                        //itarate tru blissfulsort start from old min and find new min in that blend for playlist of oldmin
                        for (int i = minInBlend.blissfulAIP + 1; i < infoDatabase.sortedSongsbyBlissful.length; i++) {
                            if (infoDatabase.sortedSongsbyBlissful[i].playlistId == minInBlend.playlistId
                                    && infoDatabase.sortedSongsbyBlissful[i].inBlendByBlissful) {
                                playlists[minInBlend.playlistId].inBlendBlissfulMin = infoDatabase.sortedSongsbyBlissful[i].id;
                                break;
                            }
                        }
                        //itarate tru blissfulsort start from old min and find new min in that blend
                        for (int i = minInBlend.blissfulAIP + 1; i < infoDatabase.sortedSongsbyBlissful.length; i++) {
                            if (infoDatabase.sortedSongsbyBlissful[i].inBlendByBlissful) {
                                infoDatabase.minBlissfulInBlend = infoDatabase.sortedSongsbyBlissful[i].id;
                                break;
                            }
                        }
                        if (song.blissfulAIP < songDatabase[playlist.inBlendBlissfulMin].blissfulAIP) {
                            playlist.inBlendBlissfulMin = song.id;
                        }
                    }
                } else { // playlist reached its limit
                    if (song.blissfulAIP > songDatabase[playlist.inBlendBlissfulMin].blissfulAIP) { // bigger than min so remove min from blend
                        song.inBlendByBlissful = true;
                        blissfulIn = songId;
                        Song minInBlendfromPlaylist = songDatabase[playlist.inBlendBlissfulMin];
                        minInBlendfromPlaylist.inBlendByBlissful = false;
                        blissfulOut = minInBlendfromPlaylist.id;
                        //itarate tru blissfulsort start from min and find new min in that playlist
                        for (int i = minInBlendfromPlaylist.blissfulAIP + 1; i < infoDatabase.sortedSongsbyBlissful.length; i++) {
                            if (infoDatabase.sortedSongsbyBlissful[i].playlistId == song.playlistId
                                    && infoDatabase.sortedSongsbyBlissful[i].inBlendByBlissful) {
                                playlist.inBlendBlissfulMin = infoDatabase.sortedSongsbyBlissful[i].id;
                                break;
                            }
                        }
                        if (minInBlendfromPlaylist.id == infoDatabase.minBlissfulInBlend) {
                            //itarate tru blissfulsort start from minbleend and find new min for blend
                            for (int i = minInBlendfromPlaylist.blissfulAIP + 1; i < infoDatabase.sortedSongsbyBlissful.length; i++) {
                                if (infoDatabase.sortedSongsbyBlissful[i].inBlendByBlissful) {
                                    infoDatabase.minBlissfulInBlend = infoDatabase.sortedSongsbyBlissful[i].id;
                                    break;
                                }
                            }
                        }
                    } else {
                        // sad?*
                    }
                } 
            }  else { // song is not eligable
             // sad ?
            }
        } 
        /* BLISSFUL PART */
        
        if (isEvent) {
            System.out.println(heartacheIn + " " + roadtripIn + " " + blissfulIn);
            System.out.println(heartacheOut + " " + roadtripOut + " " + blissfulOut);
        }
    }

    //TODO: remove logic errors for efficiency
    public static void removeSong(int songId, Song[] songDatabase, Playlist[] playlists, InfoDatabase infoDatabase) {

        Song oldSong = songDatabase[songId];
        Playlist oldPlaylist = playlists[oldSong.playlistId];
        
        int heartacheIn = 0;
        int heartacheOut = 0;
        int roadtripIn = 0;
        int roadtripOut = 0;
        int blissfulIn = 0;
        int blissfulOut = 0;
        
        if (oldSong.inBlendByHeartache) {
            Song minInBlend = songDatabase[infoDatabase.minHeartacheInBlend];
            if(songId == minInBlend.id) { // also old = playlist.minInblend
                Song newSong = null;
                // itarate tru heartachesort start from old=blendmin=playlistmin go down find new song for blend
                for (int i = minInBlend.heartacheAIP - 1; i >= 0; i--) {
                    Song tempSong = infoDatabase.sortedSongsbyHeartache[i];
                    if (tempSong.playlistId > 0 
                    && ((playlists[tempSong.playlistId].heartacheContributionCount < infoDatabase.limitPlaylist && tempSong.playlistId != oldSong.playlistId) 
                    || (playlists[tempSong.playlistId].heartacheContributionCount <= infoDatabase.limitPlaylist && tempSong.playlistId == oldSong.playlistId)) 
                    ) {
                        newSong = infoDatabase.sortedSongsbyHeartache[i];
                        break;
                    }
                }
                if (newSong != null) { // new song found
                    newSong.inBlendByHeartache = true;
                    heartacheIn = newSong.id;
                    if (newSong.playlistId == minInBlend.playlistId) { // new song is in same playlist}
                        infoDatabase.minHeartacheInBlend = newSong.id;
                        playlists[newSong.playlistId].inBlendHeartacheMin = newSong.id;
                        // PL h contribution count does not change
                        // h count in blend does not change
                    } else { // new s is in diff playlist
                        infoDatabase.minHeartacheInBlend = newSong.id;
                        playlists[newSong.playlistId].inBlendHeartacheMin = newSong.id;
                        playlists[newSong.playlistId].heartacheContributionCount += 1;
                        playlists[minInBlend.playlistId].heartacheContributionCount -= 1;
                        // h count in blend does not change
                        // itarate tru heartachesort start from old and find new min for PL in blend
                        // but first check if PL h count is 0 if it is 0 assign 0 to min
                        if (playlists[minInBlend.playlistId].heartacheContributionCount == 0) {
                            playlists[minInBlend.playlistId].inBlendHeartacheMin = 0;
                        } else {
                            // itarate tru heartachesort start from old and find new min for PL in blend
                            for (int i = minInBlend.heartacheAIP + 1; i < infoDatabase.sortedSongsbyHeartache.length; i++) {
                                if (infoDatabase.sortedSongsbyHeartache[i].playlistId == minInBlend.playlistId) {
                                    playlists[minInBlend.playlistId].inBlendHeartacheMin = infoDatabase.sortedSongsbyHeartache[i].id;
                                    break;
                                } else if (i == infoDatabase.sortedSongsbyHeartache.length - 1) {
                                    System.out.println("LOGIC ERR 1 H SONGID: " + songId);
                                }
                            }
                        }
                    } 
                } else { // new song not found
                    playlists[minInBlend.playlistId].heartacheContributionCount -= 1;
                    infoDatabase.heartacheCountInBlend -= 1;
                    // itarate tru heartachesort start from old and find new min for PL in blend also find new min for blend
                    // but first check if h count is 0 if it is 0 assign both to 0
                    if (infoDatabase.heartacheCountInBlend == 0) {
                        playlists[minInBlend.playlistId].inBlendHeartacheMin = 0;
                        infoDatabase.minHeartacheInBlend = 0;
                    } else {
                        // itarate tru heartachesort start from old and find new min for PL in blend also new min for blend
                        boolean newBlendMinNotFound = true;
                        for (int i = minInBlend.heartacheAIP + 1; i < infoDatabase.sortedSongsbyHeartache.length; i++) {
                            if (infoDatabase.sortedSongsbyHeartache[i].inBlendByHeartache && newBlendMinNotFound) {
                                infoDatabase.minHeartacheInBlend = infoDatabase.sortedSongsbyHeartache[i].id;
                                newBlendMinNotFound = false;
                            }
                            if (infoDatabase.sortedSongsbyHeartache[i].playlistId == minInBlend.playlistId) {
                                playlists[minInBlend.playlistId].inBlendHeartacheMin = infoDatabase.sortedSongsbyHeartache[i].id;
                                break;
                            } else if (i == infoDatabase.sortedSongsbyHeartache.length - 1) {
                                playlists[minInBlend.playlistId].inBlendHeartacheMin = 0;
                            }
                        }
                    }    
                }       
            } else if (oldSong.heartacheAIP > minInBlend.heartacheAIP) {
                Song playlistMinSong = songDatabase[oldPlaylist.inBlendHeartacheMin];
                if (oldSong.id == playlistMinSong.id) { // old = playmin
                    //first check if any song between playmin and blendmin that is in same playlist with old, itarate back
                    Song newSongBetween = null;
                    if (oldPlaylist.heartacheContributionCount == infoDatabase.limitPlaylist) { // no need to check in between
                        for (int i = playlistMinSong.heartacheAIP - 1; i > minInBlend.heartacheAIP; i--) {
                            if (infoDatabase.sortedSongsbyHeartache[i].playlistId == oldSong.playlistId
                            && !infoDatabase.sortedSongsbyHeartache[i].inBlendByHeartache) {
                                newSongBetween = infoDatabase.sortedSongsbyHeartache[i];
                                break;
                            }
                        }
                    }
                    if (newSongBetween != null) { // song found in between
                        newSongBetween.inBlendByHeartache = true;
                        heartacheIn = newSongBetween.id;
                        playlists[newSongBetween.playlistId].inBlendHeartacheMin = newSongBetween.id;
                        //min blend does not change 
                        // PL h contribution count does not change
                        // h count in blend does not change
                    } else { // nothing in between
                        Song newSong = null;
                        // itarate tru heartachesort start from blendmin= go down find new song for blend
                        for (int i = minInBlend.heartacheAIP - 1; i >= 0; i--) {
                            Song tempSong = infoDatabase.sortedSongsbyHeartache[i];
                            if (tempSong.playlistId > 0
                            && ((playlists[tempSong.playlistId].heartacheContributionCount < infoDatabase.limitPlaylist && tempSong.playlistId != oldSong.playlistId)
                            || (playlists[tempSong.playlistId].heartacheContributionCount <= infoDatabase.limitPlaylist && tempSong.playlistId == oldSong.playlistId))
                            ) {
                                newSong = infoDatabase.sortedSongsbyHeartache[i];
                                break;
                            }
                        }
                        if (newSong != null) { // new song found
                            newSong.inBlendByHeartache = true;
                            heartacheIn = newSong.id;
                            if (newSong.playlistId == oldSong.playlistId) { // new song is in the same playlist 
                                infoDatabase.minHeartacheInBlend = newSong.id;
                                playlists[newSong.playlistId].inBlendHeartacheMin = newSong.id;
                                // PL h contribution count does not change
                                // h count in blend does not change
                            } else { // new song is in diff playlist
                                infoDatabase.minHeartacheInBlend = newSong.id;
                                playlists[newSong.playlistId].inBlendHeartacheMin = newSong.id;
                                playlists[newSong.playlistId].heartacheContributionCount += 1;
                                playlists[oldSong.playlistId].heartacheContributionCount -= 1;
                                // h count in blend does not change
                                // itarate tru heartachesort start from old and find new min for PL in blend
                                // but first check if PL h count is 0 if it is 0 assign 0 to min
                                if (playlists[oldSong.playlistId].heartacheContributionCount == 0) {
                                    playlists[oldSong.playlistId].inBlendHeartacheMin = 0;
                                } else {
                                    // itarate tru heartachesort start from old and find new min for PL in blend
                                    for (int i = oldSong.heartacheAIP + 1; i < infoDatabase.sortedSongsbyHeartache.length; i++) {
                                        if (infoDatabase.sortedSongsbyHeartache[i].playlistId == oldSong.playlistId) {
                                            playlists[oldSong.playlistId].inBlendHeartacheMin = infoDatabase.sortedSongsbyHeartache[i].id;
                                            break;
                                        } else if (i == infoDatabase.sortedSongsbyHeartache.length - 1) {
                                            System.out.println("LOGIC ERR 1 H SONGID: " + songId);
                                        }
                                    }
                                }
                            }
                        } else { // new song is not found
                            playlists[oldSong.playlistId].heartacheContributionCount -= 1;
                            infoDatabase.heartacheCountInBlend -= 1;
                            // itarate tru heartachesort start from old and find new min for PL in blend blendmin should not change
                            // but first check if PL h count is 0 if it is 0 assign 0 to min
                            if (playlists[oldSong.playlistId].heartacheContributionCount == 0) {
                                playlists[oldSong.playlistId].inBlendHeartacheMin = 0;
                            } else {
                                // itarate tru heartachesort start from old and find new min for PL in blend
                                for (int i = oldSong.heartacheAIP + 1; i < infoDatabase.sortedSongsbyHeartache.length; i++) {
                                    if (infoDatabase.sortedSongsbyHeartache[i].playlistId == oldSong.playlistId) {
                                        playlists[oldSong.playlistId].inBlendHeartacheMin = infoDatabase.sortedSongsbyHeartache[i].id;
                                        break;
                                    } else if (i == infoDatabase.sortedSongsbyHeartache.length - 1) {
                                        System.out.println("LOGIC ERR 1 H SONGID: " + songId);
                                    }
                                }
                            }
                        }
                    }
                } else if (oldSong.heartacheAIP > playlistMinSong.heartacheAIP) { // old > playmin
                    //first check if any song between playmin and blendmin that is in same playlist with old, itarate back
                    Song newSongBetween = null;
                    if (oldPlaylist.heartacheContributionCount == infoDatabase.limitPlaylist) { // no need to check in between
                        for (int i = playlistMinSong.heartacheAIP - 1; i > minInBlend.heartacheAIP; i--) {
                            if (infoDatabase.sortedSongsbyHeartache[i].playlistId == oldSong.playlistId
                            && !infoDatabase.sortedSongsbyHeartache[i].inBlendByHeartache) {
                                newSongBetween = infoDatabase.sortedSongsbyHeartache[i];
                                break;
                            }
                        }
                    }
                    if (newSongBetween != null) { // song found in between
                        newSongBetween.inBlendByHeartache = true;
                        heartacheIn = newSongBetween.id;
                        playlists[newSongBetween.playlistId].inBlendHeartacheMin = newSongBetween.id;
                        //min blend does not change 
                        // PL h contribution count does not change
                        // h count in blend does not change
                    } else { // nothing in between
                        Song newSong = null;
                        // itarate tru heartachesort start from blendmin= go down find new song for blend
                        for (int i = minInBlend.heartacheAIP - 1; i >= 0; i--) {
                            Song tempSong = infoDatabase.sortedSongsbyHeartache[i];
                            if (tempSong.playlistId > 0
                            && ((playlists[tempSong.playlistId].heartacheContributionCount < infoDatabase.limitPlaylist && tempSong.playlistId != oldSong.playlistId)
                            || (playlists[tempSong.playlistId].heartacheContributionCount <= infoDatabase.limitPlaylist && tempSong.playlistId == oldSong.playlistId))
                            ) {
                                newSong = infoDatabase.sortedSongsbyHeartache[i];
                                break;
                            }
                        }
                        if (newSong != null) { // new song found
                            newSong.inBlendByHeartache = true;
                            heartacheIn = newSong.id;
                            if (newSong.playlistId == oldSong.playlistId) { // new song is in the same playlist 
                                infoDatabase.minHeartacheInBlend = newSong.id;
                                playlists[newSong.playlistId].inBlendHeartacheMin = newSong.id;
                                // PL h contribution count does not change
                                // h count in blend does not change                            
                            } else { // new song is in diff playlist
                                infoDatabase.minHeartacheInBlend = newSong.id;
                                playlists[newSong.playlistId].inBlendHeartacheMin = newSong.id;
                                playlists[newSong.playlistId].heartacheContributionCount += 1;
                                playlists[oldSong.playlistId].heartacheContributionCount -= 1;
                                // h count in blend does not change
                                // no need to find new min for PL in blend because old is not PL min
                            }
                        } else { // new song is not found
                            playlists[oldSong.playlistId].heartacheContributionCount -= 1;
                            infoDatabase.heartacheCountInBlend -= 1;
                            // no need to find new min for PL in blend because old is not PL min
                            // also no need to find new min for blend because old is not blend min
                        }
                    }
                } else { // old < playmin, impossible
                    System.out.println("ERR REM H 2, songId: " + songId);
                }
            } else { // oldAIP < minInBlendAIP, impossible
               System.out.println("ERR REM H 1, songId: " + songId);
            }
            songDatabase[songId].inBlendByHeartache = false;
            heartacheOut = songId;
        }
        if (oldSong.inBlendByRoadtrip) {
            Song minInBlend = songDatabase[infoDatabase.minRoadtripInBlend];
            if(songId == minInBlend.id) { // also old = playlist.minInblend
                Song newSong = null;
                // itarate tru roadtripsort start from old=blendmin=playlistmin go down find new song for blend
                for (int i = minInBlend.roadtripAIP - 1; i >= 0; i--) {
                    Song tempSong = infoDatabase.sortedSongsbyRoadTrip[i];
                    if (tempSong.playlistId > 0
                    && ((playlists[tempSong.playlistId].roadtripContributionCount < infoDatabase.limitPlaylist && tempSong.playlistId != oldSong.playlistId)
                    || (playlists[tempSong.playlistId].roadtripContributionCount <= infoDatabase.limitPlaylist && tempSong.playlistId == oldSong.playlistId))
                    ) {
                        newSong = infoDatabase.sortedSongsbyRoadTrip[i];
                        break;
                    }
                }
                if (newSong != null) { // new song found
                    newSong.inBlendByRoadtrip = true;
                    roadtripIn = newSong.id;
                    if (newSong.playlistId == minInBlend.playlistId) { // new song is in same playlist}
                        infoDatabase.minRoadtripInBlend = newSong.id;
                        playlists[newSong.playlistId].inBlendRoadtripMin = newSong.id;
                        // PL h contribution count does not change
                        // h count in blend does not change
                    } else { // new s is in diff playlist
                        infoDatabase.minRoadtripInBlend = newSong.id;
                        playlists[newSong.playlistId].inBlendRoadtripMin = newSong.id;
                        playlists[newSong.playlistId].roadtripContributionCount += 1;
                        playlists[minInBlend.playlistId].roadtripContributionCount -= 1;
                        // h count in blend does not change
                        // itarate tru roadtripsort start from old and find new min for PL in blend
                        // but first check if PL h count is 0 if it is 0 assign 0 to min
                        if (playlists[minInBlend.playlistId].roadtripContributionCount == 0) {
                            playlists[minInBlend.playlistId].inBlendRoadtripMin = 0;
                        } else {
                            // itarate tru roadtripsort start from old and find new min for PL in blend
                            for (int i = minInBlend.roadtripAIP + 1; i < infoDatabase.sortedSongsbyRoadTrip.length; i++) {
                                if (infoDatabase.sortedSongsbyRoadTrip[i].playlistId == minInBlend.playlistId) {
                                    playlists[minInBlend.playlistId].inBlendRoadtripMin = infoDatabase.sortedSongsbyRoadTrip[i].id;
                                    break;
                                } else if (i == infoDatabase.sortedSongsbyRoadTrip.length - 1) {
                                    System.out.println("LOGIC ERR 1 R SONGID: " + songId);
                                }
                            }
                        }
                    } 
                } else { // new song not found
                    playlists[minInBlend.playlistId].roadtripContributionCount -= 1;
                    infoDatabase.roadtripCountInBlend -= 1;
                    // itarate tru roadtripsort start from old and find new min for PL in blend also find new min for blend
                    // but first check if h count is 0 if it is 0 assign both to 0
                    if (infoDatabase.roadtripCountInBlend == 0) {
                        playlists[minInBlend.playlistId].inBlendRoadtripMin = 0;
                        infoDatabase.minRoadtripInBlend = 0;
                    } else {
                        // itarate tru roadtripsort start from old and find new min for PL in blend also new min for blend
                        boolean newBlendMinNotFound = true;
                        for (int i = minInBlend.roadtripAIP + 1; i < infoDatabase.sortedSongsbyRoadTrip.length; i++) {
                            if (infoDatabase.sortedSongsbyRoadTrip[i].inBlendByRoadtrip && newBlendMinNotFound) {
                                infoDatabase.minRoadtripInBlend = infoDatabase.sortedSongsbyRoadTrip[i].id;
                                newBlendMinNotFound = false;
                            }
                            if (infoDatabase.sortedSongsbyRoadTrip[i].playlistId == minInBlend.playlistId) {
                                playlists[minInBlend.playlistId].inBlendRoadtripMin = infoDatabase.sortedSongsbyRoadTrip[i].id;
                                break;
                            } else if (i == infoDatabase.sortedSongsbyRoadTrip.length - 1) {
                                playlists[minInBlend.playlistId].inBlendRoadtripMin = 0;
                            }
                        }
                    }    
                }        
            } else if (oldSong.roadtripAIP > minInBlend.roadtripAIP) {
                Song playlistMinSong = songDatabase[oldPlaylist.inBlendRoadtripMin];
                if (oldSong.id == playlistMinSong.id) { // old = playmin
                    //first check if any song between playmin and blendmin that is in same playlist with old, itarate back
                    Song newSongBetween = null;
                    if (oldPlaylist.roadtripContributionCount == infoDatabase.limitPlaylist) { // no need to check in between
                        for (int i = playlistMinSong.roadtripAIP - 1; i > minInBlend.roadtripAIP; i--) {
                            if (infoDatabase.sortedSongsbyRoadTrip[i].playlistId == oldSong.playlistId
                            && !infoDatabase.sortedSongsbyRoadTrip[i].inBlendByRoadtrip) {
                                newSongBetween = infoDatabase.sortedSongsbyRoadTrip[i];
                                break;
                            }
                        }
                    }
                    if (newSongBetween != null) { // song found in between
                        newSongBetween.inBlendByRoadtrip = true;
                        roadtripIn = newSongBetween.id;
                        playlists[newSongBetween.playlistId].inBlendRoadtripMin = newSongBetween.id;
                        //min blend does not change 
                        // PL r contribution count does not change
                        // r count in blend does not change
                    } else { // nothing in between
                        Song newSong = null;
                        // itarate tru roadtripsort start from blendmin= go down find new song for blend
                        for (int i = minInBlend.roadtripAIP - 1; i >= 0; i--) {
                            Song tempSong = infoDatabase.sortedSongsbyRoadTrip[i];
                            if (tempSong.playlistId > 0
                            && ((playlists[tempSong.playlistId].roadtripContributionCount < infoDatabase.limitPlaylist && tempSong.playlistId != oldSong.playlistId)
                            || (playlists[tempSong.playlistId].roadtripContributionCount <= infoDatabase.limitPlaylist && tempSong.playlistId == oldSong.playlistId))
                            ) {
                                newSong = infoDatabase.sortedSongsbyRoadTrip[i];
                                break;
                            }
                        }
                        if (newSong != null) { // new song found
                            newSong.inBlendByRoadtrip = true;
                            roadtripIn = newSong.id;
                            if (newSong.playlistId == oldSong.playlistId) { // new song is in the same playlist 
                                infoDatabase.minRoadtripInBlend = newSong.id;
                                playlists[newSong.playlistId].inBlendRoadtripMin = newSong.id;
                                // PL h contribution count does not change
                                // h count in blend does not change
                            } else { // new song is in diff playlist
                                infoDatabase.minRoadtripInBlend = newSong.id;
                                playlists[newSong.playlistId].inBlendRoadtripMin = newSong.id;
                                playlists[newSong.playlistId].roadtripContributionCount += 1;
                                playlists[oldSong.playlistId].roadtripContributionCount -= 1;
                                // h count in blend does not change
                                // itarate tru roadtripsort start from old and find new min for PL in blend
                                // but first check if PL h count is 0 if it is 0 assign 0 to min
                                if (playlists[oldSong.playlistId].roadtripContributionCount == 0) {
                                    playlists[oldSong.playlistId].inBlendRoadtripMin = 0;
                                } else {
                                    // itarate tru roadtripsort start from old and find new min for PL in blend
                                    for (int i = oldSong.roadtripAIP + 1; i < infoDatabase.sortedSongsbyRoadTrip.length; i++) {
                                        if (infoDatabase.sortedSongsbyRoadTrip[i].playlistId == oldSong.playlistId) {
                                            playlists[oldSong.playlistId].inBlendRoadtripMin = infoDatabase.sortedSongsbyRoadTrip[i].id;
                                            break;
                                        } else if (i == infoDatabase.sortedSongsbyRoadTrip.length - 1) {
                                            System.out.println("LOGIC ERR 1 R SONGID: " + songId);
                                        }
                                    }
                                }
                            }
                        } else { // new song is not found
                            playlists[oldSong.playlistId].roadtripContributionCount -= 1;
                            infoDatabase.roadtripCountInBlend -= 1;
                            // itarate tru roadtripsort start from old and find new min for PL in blend blendmin should not change
                            // but first check if PL h count is 0 if it is 0 assign 0 to min
                            if (playlists[oldSong.playlistId].roadtripContributionCount == 0) {
                                playlists[oldSong.playlistId].inBlendRoadtripMin = 0;
                            } else {
                                // itarate tru roadtripsort start from old and find new min for PL in blend
                                for (int i = oldSong.roadtripAIP + 1; i < infoDatabase.sortedSongsbyRoadTrip.length; i++) {
                                    if (infoDatabase.sortedSongsbyRoadTrip[i].playlistId == oldSong.playlistId) {
                                        playlists[oldSong.playlistId].inBlendRoadtripMin = infoDatabase.sortedSongsbyRoadTrip[i].id;
                                        break;
                                    } else if (i == infoDatabase.sortedSongsbyRoadTrip.length - 1) {
                                        System.out.println("LOGIC ERR 1 R SONGID: " + songId);
                                    }
                                }
                            }
                        }
                    }
                } else if (oldSong.roadtripAIP > playlistMinSong.roadtripAIP) { // old > playmin
                    //first check if any song between playmin and blendmin that is in same playlist with old, itarate back
                    Song newSongBetween = null;
                    if (oldPlaylist.roadtripContributionCount == infoDatabase.limitPlaylist) { // no need to check in between
                        for (int i = playlistMinSong.roadtripAIP - 1; i > minInBlend.roadtripAIP; i--) {
                            if (infoDatabase.sortedSongsbyRoadTrip[i].playlistId == oldSong.playlistId
                            && !infoDatabase.sortedSongsbyRoadTrip[i].inBlendByRoadtrip) {
                                newSongBetween = infoDatabase.sortedSongsbyRoadTrip[i];
                                break;
                            }
                        }
                    }
                    if (newSongBetween != null) { // song found in between
                        newSongBetween.inBlendByRoadtrip = true;
                        roadtripIn = newSongBetween.id;
                        playlists[newSongBetween.playlistId].inBlendRoadtripMin = newSongBetween.id;
                        //min blend does not change 
                        // PL r contribution count does not change
                        // r count in blend does not change
                    } else { // nothing in between
                        Song newSong = null;
                        // itarate tru roadtripsort start from blendmin= go down find new song for blend
                        for (int i = minInBlend.roadtripAIP - 1; i >= 0; i--) {
                            Song tempSong = infoDatabase.sortedSongsbyRoadTrip[i];
                            if (tempSong.playlistId > 0
                            && ((playlists[tempSong.playlistId].roadtripContributionCount < infoDatabase.limitPlaylist && tempSong.playlistId != oldSong.playlistId)
                            || (playlists[tempSong.playlistId].roadtripContributionCount <= infoDatabase.limitPlaylist && tempSong.playlistId == oldSong.playlistId))
                            ) {
                                newSong = infoDatabase.sortedSongsbyRoadTrip[i];
                                break;
                            }
                        }
                        if (newSong != null) { // new song found
                            newSong.inBlendByRoadtrip = true;
                            roadtripIn = newSong.id;
                            if (newSong.playlistId == oldSong.playlistId) { // new song is in the same playlist 
                                infoDatabase.minRoadtripInBlend = newSong.id;
                                playlists[newSong.playlistId].inBlendRoadtripMin = newSong.id;
                                // PL h contribution count does not change
                                // h count in blend does not change                            
                            } else { // new song is in diff playlist
                                infoDatabase.minRoadtripInBlend = newSong.id;
                                playlists[newSong.playlistId].inBlendRoadtripMin = newSong.id;
                                playlists[newSong.playlistId].roadtripContributionCount += 1;
                                playlists[oldSong.playlistId].roadtripContributionCount -= 1;
                                // h count in blend does not change
                                // no need to find new min for PL in blend because old is not PL min
                            }
                        } else { // new song is not found
                            playlists[oldSong.playlistId].roadtripContributionCount -= 1;
                            infoDatabase.roadtripCountInBlend -= 1;
                            // no need to find new min for PL in blend because old is not PL min
                            // also no need to find new min for blend because old is not blend min
                        }
                    }
                } else { // old < playmin, impossible
                    System.out.println("ERR REM R 2, songId: " + songId);
                }
            } else { // oldAIP < minInBlendAIP, impossible
                System.out.println("ERR REM R 1, songId: " + songId);
                }
            songDatabase[songId].inBlendByRoadtrip = false;
            roadtripOut = songId;
        }
        if (oldSong.inBlendByBlissful) {
            Song minInBlend = songDatabase[infoDatabase.minBlissfulInBlend];
            if(songId == minInBlend.id) { // also old = playlist.minInblend
                Song newSong = null;
                // itarate tru blissfulsort start from old=blendmin=playlistmin go down find new song for blend
                for (int i = minInBlend.blissfulAIP - 1; i >= 0; i--) {
                    Song tempSong = infoDatabase.sortedSongsbyBlissful[i];
                    if (tempSong.playlistId > 0 
                    && ((playlists[tempSong.playlistId].blissfulContributionCount < infoDatabase.limitPlaylist && tempSong.playlistId != oldSong.playlistId) 
                    || (playlists[tempSong.playlistId].blissfulContributionCount <= infoDatabase.limitPlaylist && tempSong.playlistId == oldSong.playlistId)) 
                    ) {
                        newSong = infoDatabase.sortedSongsbyBlissful[i];
                        break;
                    } 
                }
                if (newSong != null) { // new song found
                    newSong.inBlendByBlissful = true;
                    blissfulIn = newSong.id;
                    if (newSong.playlistId == minInBlend.playlistId) { // new song is in same playlist}
                        infoDatabase.minBlissfulInBlend = newSong.id;
                        playlists[newSong.playlistId].inBlendBlissfulMin = newSong.id;
                        // PL h contribution count does not change
                        // h count in blend does not change
                    } else { // new s is in diff playlist
                        infoDatabase.minBlissfulInBlend = newSong.id;
                        playlists[newSong.playlistId].inBlendBlissfulMin = newSong.id;
                        playlists[newSong.playlistId].blissfulContributionCount += 1;
                        playlists[minInBlend.playlistId].blissfulContributionCount -= 1;
                        // h count in blend does not change
                        // itarate tru blissfulsort start from old and find new min for PL in blend
                        // but first check if PL h count is 0 if it is 0 assign 0 to min
                        if (playlists[minInBlend.playlistId].blissfulContributionCount == 0) {
                            playlists[minInBlend.playlistId].inBlendBlissfulMin = 0;
                        } else {
                            // itarate tru blissfulsort start from old and find new min for PL in blend
                            for (int i = minInBlend.blissfulAIP + 1; i < infoDatabase.sortedSongsbyBlissful.length; i++) {
                                if (infoDatabase.sortedSongsbyBlissful[i].playlistId == minInBlend.playlistId) {
                                    playlists[minInBlend.playlistId].inBlendBlissfulMin = infoDatabase.sortedSongsbyBlissful[i].id;
                                    break;
                                } else if (i == infoDatabase.sortedSongsbyBlissful.length - 1) {
                                    System.out.println("LOGIC ERR 1 B SONGID: " + songId);
                                }
                            }
                        }
                    }
                } else { // new song not found
                    playlists[minInBlend.playlistId].blissfulContributionCount -= 1;
                    infoDatabase.blissfulCountInBlend -= 1;
                    // itarate tru blissfulsort start from old and find new min for PL in blend also find new min for blend
                    // but first check if h count is 0 if it is 0 assign both to 0
                    if (infoDatabase.blissfulCountInBlend == 0) {
                        playlists[minInBlend.playlistId].inBlendBlissfulMin = 0;
                        infoDatabase.minBlissfulInBlend = 0;
                    } else {
                        // itarate tru blissfulsort start from old and find new min for PL in blend also new min for blend
                        boolean newBlendMinNotFound = true;
                        for (int i = minInBlend.blissfulAIP + 1; i < infoDatabase.sortedSongsbyBlissful.length; i++) {
                            if (infoDatabase.sortedSongsbyBlissful[i].inBlendByBlissful && newBlendMinNotFound) {
                                infoDatabase.minBlissfulInBlend = infoDatabase.sortedSongsbyBlissful[i].id;
                                newBlendMinNotFound = false;
                            }
                            if (infoDatabase.sortedSongsbyBlissful[i].playlistId == minInBlend.playlistId) {
                                playlists[minInBlend.playlistId].inBlendBlissfulMin = infoDatabase.sortedSongsbyBlissful[i].id;
                                break;
                            } else if (i == infoDatabase.sortedSongsbyBlissful.length - 1) {
                                playlists[minInBlend.playlistId].inBlendBlissfulMin = 0;
                            }
                        }
                    }    
                }
            } else if (oldSong.blissfulAIP > minInBlend.blissfulAIP) {
                Song playlistMinSong = songDatabase[oldPlaylist.inBlendBlissfulMin];
                if (oldSong.id == playlistMinSong.id) { // old = playmin
                    //first check if any song between playmin and blendmin that is in same playlist with old, itarate back
                    Song newSongBetween = null;
                    if (oldPlaylist.blissfulContributionCount == infoDatabase.limitPlaylist) { // no need to check in between
                        for (int i = playlistMinSong.blissfulAIP - 1; i > minInBlend.blissfulAIP; i--) {
                            if (infoDatabase.sortedSongsbyBlissful[i].playlistId == oldSong.playlistId
                            && !infoDatabase.sortedSongsbyBlissful[i].inBlendByBlissful) {
                                newSongBetween = infoDatabase.sortedSongsbyBlissful[i];
                                break;
                            }
                        }
                    }
                    if (newSongBetween != null) { // song found in between
                        newSongBetween.inBlendByBlissful = true;
                        blissfulIn = newSongBetween.id;
                        playlists[newSongBetween.playlistId].inBlendBlissfulMin = newSongBetween.id;
                        //min blend does not change 
                        // PL b contribution count does not change
                        // b count in blend does not change
                    } else { // nothing in between
                        Song newSong = null;
                        // itarate tru blissfulsort start from blendmin= go down find new song for blend
                        for (int i = minInBlend.blissfulAIP - 1; i >= 0; i--) {
                            Song tempSong = infoDatabase.sortedSongsbyBlissful[i];
                            if (tempSong.playlistId > 0 
                            && ((playlists[tempSong.playlistId].blissfulContributionCount < infoDatabase.limitPlaylist && tempSong.playlistId != oldSong.playlistId) 
                            || (playlists[tempSong.playlistId].blissfulContributionCount <= infoDatabase.limitPlaylist && tempSong.playlistId == oldSong.playlistId)) 
                            ) {
                                newSong = infoDatabase.sortedSongsbyBlissful[i];
                                break;
                            }
                        }
                        if (newSong != null) { // new song found
                            newSong.inBlendByBlissful = true;
                            blissfulIn = newSong.id;
                            if (newSong.playlistId == oldSong.playlistId) { // new song is in the same playlist 
                                infoDatabase.minBlissfulInBlend = newSong.id;
                                playlists[newSong.playlistId].inBlendBlissfulMin = newSong.id;
                                // PL h contribution count does not change
                                // h count in blend does not change
                            } else { // new song is in diff playlist
                                infoDatabase.minBlissfulInBlend = newSong.id;
                                playlists[newSong.playlistId].inBlendBlissfulMin = newSong.id;
                                playlists[newSong.playlistId].blissfulContributionCount += 1;
                                playlists[oldSong.playlistId].blissfulContributionCount -= 1;
                                // h count in blend does not change
                                // itarate tru blissfulsort start from old and find new min for PL in blend
                                // but first check if PL h count is 0 if it is 0 assign 0 to min
                                if (playlists[oldSong.playlistId].blissfulContributionCount == 0) {
                                    playlists[oldSong.playlistId].inBlendBlissfulMin = 0;
                                } else {
                                    // itarate tru blissfulsort start from old and find new min for PL in blend
                                    for (int i = oldSong.blissfulAIP + 1; i < infoDatabase.sortedSongsbyBlissful.length; i++) {
                                        if (infoDatabase.sortedSongsbyBlissful[i].playlistId == oldSong.playlistId) {
                                            playlists[oldSong.playlistId].inBlendBlissfulMin = infoDatabase.sortedSongsbyBlissful[i].id;
                                            break;
                                        } else if (i == infoDatabase.sortedSongsbyBlissful.length - 1) {
                                            System.out.println("LOGIC ERR 1 B SONGID: " + songId);
                                        }    
                                    }
                                }
                            }
                        } else { // new song is not found
                            playlists[oldSong.playlistId].blissfulContributionCount -= 1;
                            infoDatabase.blissfulCountInBlend -= 1;
                            // itarate tru blissfulsort start from old and find new min for PL in blend blendmin should not change
                            // but first check if PL h count is 0 if it is 0 assign 0 to min
                            if (playlists[oldSong.playlistId].blissfulContributionCount == 0) {
                                playlists[oldSong.playlistId].inBlendBlissfulMin = 0;
                            } else {
                                // itarate tru blissfulsort start from old and find new min for PL in blend
                                for (int i = oldSong.blissfulAIP + 1; i < infoDatabase.sortedSongsbyBlissful.length; i++) {
                                    if (infoDatabase.sortedSongsbyBlissful[i].playlistId == oldSong.playlistId) {
                                        playlists[oldSong.playlistId].inBlendBlissfulMin = infoDatabase.sortedSongsbyBlissful[i].id;
                                        break;
                                    } else if (i == infoDatabase.sortedSongsbyBlissful.length - 1) {
                                        System.out.println("LOGIC ERR 1 B SONGID: " + songId);
                                    }
                                }
                            }
                        }
                    }
                } else if (oldSong.blissfulAIP > playlistMinSong.blissfulAIP) { // old > playmin
                    //first check if any song between playmin= and blendmin that is in same playlist with old, itarate back
                    Song newSongBetween = null;
                    if (oldPlaylist.blissfulContributionCount == infoDatabase.limitPlaylist) { // no need to check in between
                        for (int i = playlistMinSong.blissfulAIP - 1; i > minInBlend.blissfulAIP; i--) {
                            if (infoDatabase.sortedSongsbyBlissful[i].playlistId == oldSong.playlistId
                            && !infoDatabase.sortedSongsbyBlissful[i].inBlendByBlissful) {
                                newSongBetween = infoDatabase.sortedSongsbyBlissful[i];
                                break;
                            }
                        }
                    }
                    if (newSongBetween != null) { // song found in between
                        newSongBetween.inBlendByBlissful = true;
                        blissfulIn = newSongBetween.id;
                        playlists[newSongBetween.playlistId].inBlendBlissfulMin = newSongBetween.id;
                        //min blend does not change 
                        // PL b contribution count does not change
                        // b count in blend does not change
                    } else { // nothing in between
                        Song newSong = null;
                        // itarate tru blissfulsort start from blendmin= go down find new song for blend
                        for (int i = minInBlend.blissfulAIP - 1; i >= 0; i--) {
                            Song tempSong = infoDatabase.sortedSongsbyBlissful[i];
                            if (tempSong.playlistId > 0
                            && ((playlists[tempSong.playlistId].blissfulContributionCount < infoDatabase.limitPlaylist && tempSong.playlistId != oldSong.playlistId)
                            || (playlists[tempSong.playlistId].blissfulContributionCount <= infoDatabase.limitPlaylist && tempSong.playlistId == oldSong.playlistId))
                            ) {
                                newSong = infoDatabase.sortedSongsbyBlissful[i];
                                break;
                            }
                        }
                        if (newSong != null) { // new song found
                            newSong.inBlendByBlissful = true;
                            blissfulIn = newSong.id;
                            if (newSong.playlistId == oldSong.playlistId) { // new song is in the same playlist 
                                infoDatabase.minBlissfulInBlend = newSong.id;
                                playlists[newSong.playlistId].inBlendBlissfulMin = newSong.id;
                                // PL h contribution count does not change
                                // h count in blend does not change                            
                            } else { // new song is in diff playlist
                                infoDatabase.minBlissfulInBlend = newSong.id;
                                playlists[newSong.playlistId].inBlendBlissfulMin = newSong.id;
                                playlists[newSong.playlistId].blissfulContributionCount += 1;
                                playlists[oldSong.playlistId].blissfulContributionCount -= 1;
                                // h count in blend does not change
                                // no need to find new min for PL in blend because old is not PL min
                            }
                        } else { // new song is not found
                            playlists[oldSong.playlistId].blissfulContributionCount -= 1;
                            infoDatabase.blissfulCountInBlend -= 1;
                            // no need to find new min for PL in blend because old is not PL min
                            // also no need to find new min for blend because old is not blend min
                        }
                    }
                } else { // old < playmin, impossible
                    System.out.println("ERR REM B 2, songId: " + songId);
                }
            } else { // oldAIP < minInBlendAIP, impossible
                System.out.println("ERR REM B 1, songId: " + songId);
            }
        songDatabase[songId].inBlendByBlissful = false;
        blissfulOut = songId;
    }
        //TODO: checklater    
        /*if (!oldSong.inBlendByBlissful && !oldSong.inBlendByHeartache && !oldSong.inBlendByRoadtrip) { // not in blend
            
        }*/
        songDatabase[songId].playlistId = 0; 
        System.out.println(heartacheIn + " " + roadtripIn + " " + blissfulIn);
        System.out.println(heartacheOut + " " + roadtripOut + " " + blissfulOut);
    }

    /* 
    // TODO: remove
    public static void testSortSongs() {
        // Create an array of songs
        Song[] songs = new Song[] {
            new Song(0,"abcde", 1000, new int[]{0, 0, 0}, 0, false),
            new Song(1,"abcda", 1000, new int[]{0, 0, 0}, 0, false),
            new Song(2,"abc", 2000, new int[]{0, 0, 0}, 0, false),
            new Song(3,"abcdaasd", 2000, new int[]{0, 0, 0}, 0, false),
            new Song(4,"abcde", 4000, new int[]{0, 0, 0}, 0, false),
            new Song(5,"abcdb", 5000, new int[]{0, 0, 0}, 0, false),
            new Song(6,"abcde", 2000, new int[]{0, 0, 0}, 0, false),
            new Song(7,"abcdb", 5000, new int[]{0, 0, 0}, 0, false),
            new Song(8,"abcde", 4000, new int[]{0, 0, 0}, 0, false),
            new Song(9,"abcdb", 5000, new int[]{0, 0, 0}, 0, false),
            new Song(10,"abcde", 4000, new int[]{0, 0, 0}, 0, false),
            new Song(11,"abcdb", 5000, new int[]{0, 0, 0}, 0, false),

        };
    
        // Sort the songs
        MergeSort.sortSongs(songs);
    
        // Check that the songs are sorted by playCount
        for (int i = 0; i < songs.length - 1; i++) {
            assert songs[i].playCount <= songs[i + 1].playCount : "Songs are not sorted by playCount";
        }
    
        // Check that songs with the same playCount are sorted by name
        for (int i = 0; i < songs.length - 1; i++) {
            if (songs[i].playCount == songs[i + 1].playCount) {
                assert songs[i].name.compareTo(songs[i + 1].name) <= 0 : "Songs with the same playCount are not sorted by name";
            }
        }
    
        System.out.println("All tests passed!");
    }
*/
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

    public static class InfoDatabase {
        public Song[] sortedSongsbyPlayCount;
        public Song[] sortedSongsbyHeartache;
        public Song[] sortedSongsbyRoadTrip;
        public Song[] sortedSongsbyBlissful;

        int limitPlaylist;
        int limitHeartache;
        int limitRoadtrip;
        int limitBlissful;

        int minHeartacheInBlend;
        int minRoadtripInBlend;
        int minBlissfulInBlend;

        int heartacheCountInBlend;
        int roadtripCountInBlend;
        int blissfulCountInBlend;

        public InfoDatabase(int songDatabaseSize) {
            sortedSongsbyPlayCount = new Song[songDatabaseSize];
            sortedSongsbyHeartache = new Song[songDatabaseSize];
            sortedSongsbyRoadTrip = new Song[songDatabaseSize];
            sortedSongsbyBlissful = new Song[songDatabaseSize];
            limitPlaylist = 0;
            limitHeartache = 0;
            limitRoadtrip = 0;
            limitBlissful = 0;
            heartacheCountInBlend = 0;
            roadtripCountInBlend = 0;
            blissfulCountInBlend = 0;
            // 0: does not exist, else: song id
            minHeartacheInBlend = 0;
            minRoadtripInBlend = 0;
            minBlissfulInBlend = 0;
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
/* 
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
*/
        /* 
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
*/
        /* 
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
*/
        public static void sort(Song[] array){
            sort(array, 0, array.length - 1);
        }

        /*public static void sortSongs(Song[] array){
            radixSortByName(array, findLongestNameLength(array));
            //countingSortByPlayCount(array, 10000);
            countingSortByHeartache(array, 100);
            //countingSortByRoadTrip(array, 100);
            //countingSortByBlissful(array, 100);
        }*/
        
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

        public static void countingSortByHeartache(Song[] array, int maxHeartache){
            Song[] output = new Song[array.length];
            int[] count = new int[maxHeartache + 1];
            for(Song song : array){
                count[song.scores[0]]++;
            }
            for(int i = 1; i <= maxHeartache; i++){
                count[i] += count[i - 1];
            }
            for(int i = array.length - 1; i >= 0; i--){
                output[--count[array[i].scores[0]]] = array[i];
            }
            System.arraycopy(output, 0, array, 0, array.length);
        }
        
        public static void countingSortByRoadTrip(Song[] array, int maxRoadTrip){
            Song[] output = new Song[array.length];
            int[] count = new int[maxRoadTrip + 1];
            for(Song song : array){
                count[song.scores[1]]++;
            }
            for(int i = 1; i <= maxRoadTrip; i++){
                count[i] += count[i - 1];
            }
            for(int i = array.length - 1; i >= 0; i--){
                output[--count[array[i].scores[1]]] = array[i];
            }
            System.arraycopy(output, 0, array, 0, array.length);
        }
        
        public static void countingSortByBlissful(Song[] array, int maxBlissful){
            Song[] output = new Song[array.length];
            int[] count = new int[maxBlissful + 1];
            for(Song song : array){
                count[song.scores[2]]++;
            }
            for(int i = 1; i <= maxBlissful; i++){
                count[i] += count[i - 1];
            }
            for(int i = array.length - 1; i >= 0; i--){
                output[--count[array[i].scores[2]]] = array[i];
            }
            System.arraycopy(output, 0, array, 0, array.length);
        }
        
        
        /* 
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
        }*/

        public static int findLongestNameLength(Song[] array){
            int maxLength = 0;
            for(Song song : array){
                if(song.name.length() > maxLength){
                    maxLength = song.name.length();
                }
            }
            return maxLength;
        }

        public static void radixSortByName(Song[] array, int nameLength){
            for(int exp = nameLength - 1; exp >= 0; exp--){
                countingSortByName(array, exp);
            }
        }
        
        public static void countingSortByName(Song[] array, int exp){
            Song[] output = new Song[array.length];
            int[] count = new int[53]; // 26 lowercase + 26 uppercase + 1 for shorter names
            for(Song song : array){
                int index;
                if(song.name.length() > exp){
                    char c = song.name.charAt(exp);
                    index = (c >= 'a' && c <= 'z') ? c - 'a' + 1 : c - 'A' + 27;
                } else {
                    index = 0; // Use 0 for songs with names shorter than exp
                }
                count[index]++;
            }
            for(int i = 1; i < 53; i++){
                count[i] += count[i - 1];
            }
            for(int i = 0; i < array.length; i++){
                int index;
                if(array[i].name.length() > exp){
                    char c = array[i].name.charAt(exp);
                    index = (c >= 'a' && c <= 'z') ? c - 'a' + 1 : c - 'A' + 27;
                } else {
                    index = 0;
                }
                output[array.length - count[index]] = array[i];
                count[index]--;
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
            this.insert(new Song(0, "", 0, new int[]{0, 0, 0}, 0)); // dummy node
        }

        public MinHeap(int size){
            this.heap = new Song[size];
            this.size = 0;
            this.scoreIndex = 0;
            this.insert(new Song(0, "", 0, new int[]{0, 0, 0}, 0)); // dummy node
        }

        public MinHeap(int size, int scoreIndex){
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
        /*MaxHeap notInBlendHeartache;
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
        */

        //??
        int notInBlendHeartacheMax;
        int notInBlendRoadtripMax;
        int notInBlendBlissfulMax;
        
        int inBlendHeartacheMin;
        int inBlendRoadtripMin;
        int inBlendBlissfulMin;
        int heartacheContributionCount;
        int roadtripContributionCount;
        int blissfulContributionCount;

        public Playlist(){
            // 0: does not exist, else: song id
            this.notInBlendHeartacheMax = 0;
            this.notInBlendRoadtripMax = 0;
            this.notInBlendBlissfulMax = 0;
            this.inBlendHeartacheMin = 0;
            this.inBlendRoadtripMin = 0;
            this.inBlendBlissfulMin = 0;
            this.heartacheContributionCount = 0;
            this.roadtripContributionCount = 0;
            this.blissfulContributionCount = 0;
        }
    }

    public static class Song{
        int id;
        String name;
        int playCount;
        int[] scores; // [heartache, roadtrip, blissful]
        int playlistId;
        boolean inBlendByHeartache;
        boolean inBlendByRoadtrip;
        boolean inBlendByBlissful;
        //AIP = Advanced Index of Priority
        int heartacheAIP;
        int roadtripAIP;
        int blissfulAIP;
        //int nameHash;

        public Song(){
            this.id = 0;
            this.scores = new int[3];
            this.playlistId = 0;
            this.playCount = 0;
            this.name = "";
            this.inBlendByHeartache = false;
            this.inBlendByRoadtrip = false;
            this.inBlendByBlissful = false;
            //this.nameHash = 0;
        }

        public Song(int id, String name, int playCount, int[] scores){
            this.id = id;
            this.name = name;
            this.playCount = playCount;
            this.scores = scores;
            this.playlistId = 0;
            this.inBlendByHeartache = false;
            this.inBlendByRoadtrip = false;
            this.inBlendByBlissful = false;
            //this.nameHash = computeValue(name);
        }

        public Song(int id, String name, int playCount, int[] scores, int playlistId){
            this.id = id;
            this.name = name;
            this.playCount = playCount;
            this.scores = scores;
            this.playlistId = playlistId;
            this.inBlendByHeartache = false;
            this.inBlendByRoadtrip = false;
            this.inBlendByBlissful = false;
            //this.nameHash = computeValue(name);
        }

        /*public static int computeValue(String s) {
            int value = 0;
            for (char c : s.toCharArray()) {
                value = value * 26 + (c - 'a' + 1);
            }
            return value;
        }*/

        
    }

    public static class SongPlaylistIdTuple{
        int songId;
        int playlistId;

        public SongPlaylistIdTuple(int songId, int playlistId){
            this.songId = songId;
            this.playlistId = playlistId;
        }
    }

    /*public static class StringComparator {
        public static boolean fastStringCompare(String str1, String str2) {
            for (int i = 0; i < 5; i++) {
                if (str1.charAt(i) < str2.charAt(i)) {
                    return false;
                }
            }
            return true;
        }
    }*/
}
