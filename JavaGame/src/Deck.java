import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Deck {
	static ArrayList<Card> Deck = new ArrayList<Card>();

	public static boolean setDeck(){
		File file = new File("MapData.txt");
		try {
			Scanner MapData = new Scanner(file);
			String[] FirstLine = (MapData.nextLine()).split(",");
			for(int i = 0; i<FirstLine.length/2;i++){
				Card oObject = new Card(FirstLine[i*2],Integer.parseInt(FirstLine[i*2+1]));
				Deck.add(oObject);
				//SectorList.add(new Sector(FirstLine[i]));
				//System.out.println(FirstLine[i]);
				//System.out.println(Deck.get(i));
				//System.out.println(Deck);
			}
				System.out.println(Deck);
			MapData.close();
			return true;
		}
		catch (FileNotFoundException e) {
			return false;
		}
		
	}

	public static void add(Card input) {
		// TODO Auto-generated method stub
		
	}

	public static Object[] toObject() {
		// TODO Auto-generated method stub
		return new Object[0];//TEMP REPLACE WITH ACCUAL METHOD!!!
	}

	public static void remove(Card input) {
		// TODO Auto-generated method stub
		
	}

	public static Card draw() {//PLEASE CREATE THIS METHOD
		// TODO Auto-generated method stub
		return null;
	}
	
}
