public class JavaGameMain {
	
	public static void main(String[] args) {
		
		//Start by setting up board with txt input using board init
		//Prompt for number of players 2-5
		//Sector attack = new Sector();
		//attack.setTroops(5);
		//Sector defend = new Sector();
		//defend.setTroops(1);
		//Method.Battle(attack, defend);
		
		Board.setBoard();
		Deck.setDeck();
		int choice = Method.NumberRangeBox(2,5,"Select Number of Players","Players");
		if(choice == 2){
			Method.TwoPlayerSetUp();
		}
		else{;
			Method.PlayerSetUp(choice);
		}
	}
	
	
/*
 *if(someone has won){
 * output that they won. terminate game
 * go to next persons turn
 * Start Place phase
 * Then start attack phase
 * then move troops phase
 * then draw cards if possible
 * start next turn
 */
}
