package songboard;


import java.io.*;
import java.util.Random;


/** Class for storing song rankings, initially read from CSV */
public class SongBoard implements SongBoardInterface {


	public int numEntries;
	private String songBoardTitle;
	private SongEntry[] songArray;


	/**
	 * Constructs a SongBoard with the given capacity for storing entries.
	 */
	public SongBoard() {
		numEntries = 200;
		songArray = new SongEntry[numEntries];
		songBoardTitle = "Shazam Top 200 Songs";
		String filePath;

		filePath = "./input.csv";
		int count = 0;
		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			String line;
			while ((line = br.readLine()) != null) {
				String[] values = line.split(",");

				values[0] = values[0].replaceAll("[^\\d]", "");
				SongEntry thisSongEntry = new SongEntry(Integer.parseInt(values[0]), values[1], values[2]);
				songArray[count] = thisSongEntry;
				count++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public void printTopTen() {
		int rank;
		String artist;
		String songname;
		System.out.println(songBoardTitle);
		System.out.println("The Top Ten Are:");
		for (int i = 0; i < 10; i++) {
			rank = songArray[i].GetRank();
			artist = songArray[i].GetArtist();
			songname = songArray[i].GetSongName();
			System.out.println(rank + ", " + artist + ", " + songname);
		}
	}

	public void scrambleBoard() {

		Random rand = new Random();
		rand.setSeed(System.currentTimeMillis());
		double rand0to1;
		int lowerrandom, upperrandom, randomrange, randlowertoupper;
		int j;
		long mylong = System.currentTimeMillis();
		System.out.println("\n\nCurrent time in milliseconds since 1970 is: " + Long.toString(mylong));
		rand.setSeed(mylong);
		SongEntry temp;
		for (int i = 0; i < numEntries; i++) {
			lowerrandom = i;
			upperrandom = numEntries - 1;
			randomrange = upperrandom - lowerrandom;
			rand0to1 = rand.nextDouble();
			randlowertoupper = (int) (lowerrandom + rand0to1 * randomrange);
			j = randlowertoupper;

			temp = songArray[i];
			songArray[i] = songArray[j];
			songArray[j] = temp;
		}
	}

	public int[] insertionSortBoard() {


		// This code for test only
		int[] returnArray = new int[numEntries];
		for (int count = 0; count < numEntries; count++) {
			returnArray[count] = 0;
		}


		int n = numEntries;
		for (int k=1; k < n; k++) {
			SongEntry cur = songArray[k];
			int j = k;
			while (j > 0 && songArray[j-1].GetRank() > cur.GetRank()) {
				songArray[j] = songArray[j-1];
				j--;
			}
			songArray[j] = cur;
		}

		for (int count = 0; count < numEntries; count++) {
			returnArray[count] = songArray[count].rank;
		}
		return returnArray;}}