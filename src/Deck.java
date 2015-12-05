import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Deck {
	private ArrayList<Card> MainDeck = new ArrayList<Card>();

	public void setDeck(){
		File file = new File("MapData.txt");
		try {
			Scanner MapData = new Scanner(file);
			String[] FirstLine = (MapData.nextLine()).split(",");
			for(int i = 0; i<FirstLine.length/2;i++){
				Card oObject = new Card(FirstLine[i*2],Integer.parseInt(FirstLine[i*2+1]));
				MainDeck.add(oObject);
			}
			//System.out.println(MainDeck);
			MapData.close();
		}
		catch (FileNotFoundException e) {
			System.err.println("FileNotFound!!!");
		}
		
	}

	public void add(Card input) {
		MainDeck.add(input);
	}

	public Object[] toObject() {
		// TODO Auto-generated method stub
		return new Object[0];//TEMP REPLACE WITH ACCUAL METHOD!!!
	}

	public void remove(Card input) {
		// TODO Auto-generated method stub
		
	}

	public Card draw() {
		Card output = MainDeck.get(0);
		MainDeck.remove(0);
		return output;
	}
	public boolean isEmpty(){
		return MainDeck.isEmpty();
	}
}
