/*
 * TwoPlayerSetUp and PlayerSetUp methods by Taylor Scafe
 * Battle and Battle Execute method by Taylor Scafe.
 * DialogBox and OptionBox and QuitPrompt methods by Taylor Scafe.
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import javax.swing.JOptionPane;

public class Method {
	public static ArrayList<Player> PlayerList = new ArrayList<Player>();
	public static Player CurrentPlayer;
	public static int intCurrentPlayer;
	public static boolean gotSector = false;
	public static boolean TwoPlayer = false;
	public static Player winner;
/////////////////////////////////GAME SET UP METHODS///////////////////////////////////////////	
	public static void TwoPlayerSetUp(){
		TwoPlayer = true;
		ArrayList<Sector> SectorList = Board.SectorList;
		PlayerList.add(new Player(DialogBox("Player 1: Enter Name", "Enter Name", "")));
		PlayerList.add(new Player(DialogBox("Player 2: Enter Name", "Enter Name", "")));
		CurrentPlayer = PlayerList.get(1);
		for(int i = 0;i<23;i++){
			
		}
	}
	public static void PlayerSetUp(int iPlayerCount){
		ArrayList<Sector> SectorList = Board.SectorList;
		for(int i=0;i<iPlayerCount;i++){
			PlayerList.add(new Player(DialogBox("Player "+(i+1)+": Enter Name", "Enter Name", "")));
		}
		CurrentPlayer = PlayerList.get(1);
	}
/////////////////////////////////PLACE TROOP METHODS///////////////////////////////////////////
	
/////////////////////////////////BATTLE METHODS///////////////////////////////////////////	
	public static void Battle(Sector attack, Sector defend){//Will take 2 sectors
		int attackTroops = 0;
		int defendTroops = 0;
		attackTroops = NumberRangeBox(1, attack.getTroops()-1, "Select Number of Troops to Deploy", "Attack");
		defendTroops = defend.getTroops();
		Method.BattleExecute(attackTroops, defendTroops, attack, defend);
	}
	private static void BattleExecute(int attackTroops, int defendTroops, Sector attack, Sector defend){//Will take 2 sectors as input
		Random die = new Random();
		System.out.println(attackTroops);
		ArrayList<Integer> attackDice = new ArrayList<Integer>();
		ArrayList<Integer> defendDice = new ArrayList<Integer>();
		int moveTroops = attackTroops;
		if (defendTroops >=2){
			defendTroops = 2;
		}
		for(int i=0; i!=attackTroops;i++){
			attackDice.add((die.nextInt(5)+1));
		}
		Collections.sort(attackDice);
		for(int i=0; i!=defendTroops;i++){
			defendDice.add(die.nextInt(5)+1);
		}
		Collections.sort(defendDice);
		for(int i=0;i!=defendTroops;i++){
			int attackDie = (int) attackDice.get(attackTroops-i-1);
			int defendDie = (int) defendDice.get(defendTroops-i-1);
			if(attackDie <= defendDie){
				System.out.println("Defender "+ (i+1) + " was Sucessful");
				moveTroops --;
				attack.subTroop();
			}
			else{
				System.out.println("Attacker "+ (i+1) + " was Sucessful");
				defend.subTroop();
			}
		}
		if(defend.getTroops() == 0){
			System.out.println("Attackers overwhelmed the opposition");
			gotSector = true;
			defend.setPlayer(attack.getPlayer());
			defend.setTroops(moveTroops);
			attack.subTroops(moveTroops);
			if(attack.getTroops()>1){
				moveTroops = NumberRangeBox(1,attack.getTroops()-1,"Select number of troops to move","Move Troops");
				defend.addTroops((moveTroops));
				attack.subTroops((moveTroops));
			}
		}
	}
/////////////////////////////////MANEUVER METHODS///////////////////////////////////////////
	
/////////////////////////////////DRAW CARD METHODS///////////////////////////////////////////
	public static void DrawCard(){
		if (gotSector){
			CurrentPlayer.addCard(Deck.draw());
			gotSector = false;
		}
	}
/////////////////////////////////CHECK WIN METHOD//////////////////////////////////////////
	public static boolean CheckWin(){
		if(TwoPlayer){
			Player player1 = PlayerList.get(0);
			Player player2 = PlayerList.get(1);
			if(player1.getSectorList().size()== 0 || player2.getSectorList().size()== 0){
				if(player1.getSectorList().size()== 0){
					winner = player1;
					return true;
				}
				else{
					winner = player2;
					return true;
				}
			}
			else{
				return false;
			}
		}
		else{
			for(int i = 0;i<PlayerList.size();i++){
				Player checkPlayer = PlayerList.get(i);
				if(checkPlayer.getSectorList().size() == Board.SectorList.size()){
					winner = checkPlayer;
					return true;
				}
			}
		return false;
		}
	}
/////////////////////////////////NEXT PLAYER METHOD////////////////////////////////////////
	public static void nextPlayer(){
		if (intCurrentPlayer == PlayerList.size()){
			intCurrentPlayer = 0;
			CurrentPlayer = PlayerList.get(intCurrentPlayer);
		}
		else{
			intCurrentPlayer++;
			CurrentPlayer = PlayerList.get(intCurrentPlayer);
		}
	}
/////////////////////////////////OBJECT METHODS///////////////////////////////////////////
	private static Object[] appendValue(Object[] obj, Object newObj) {//Taken from the Internet. Give append method to an Object[]

		ArrayList<Object> temp = new ArrayList<Object>(Arrays.asList(obj));
		temp.add(newObj);
		return temp.toArray();
	}
	public static Object[] ConvertSectorListToObject(ArrayList<Sector> input){
		Object[] output = new Object[0];
		for(int i=0;i<input.size();i++){
			Sector sectorInput = input.get(i);
			appendValue(output,sectorInput.getName());
		}
		return output;
	}
/////////////////////////////////USER INTERFACE METHODS///////////////////////////////////////////	
	public static String DialogBox(String text,String title,String defaultOption){
		boolean notDone = true;
		while(notDone){
			String sInput = (String)JOptionPane.showInputDialog(null, text, title, JOptionPane.PLAIN_MESSAGE, null, null, defaultOption);
			//System.out.println(playerCount);
			if (sInput == null){
				QuitPrompt();
			}
			else{
				notDone = false;
				return sInput;
			}
		}
		return "";
	}
	public static String OptionBox(String text,String title,Object[] possabllities,String defaultOption){
		boolean notDone = true;
		while(notDone){
			String output = (String)JOptionPane.showInputDialog(null, text, title, JOptionPane.PLAIN_MESSAGE, null, possabllities, defaultOption);
			//System.out.println(playerCount);
			if (output == null){
				QuitPrompt();
			}
			else{
				notDone = false;
				return (output);
				
			}
		}
		return "";
	}
	public static int NumberRangeBox(int start, int end, String text,String title){
		Object[] possabilities = new Object[0];
		boolean notDone = true;
		String output = "";
		for(int i = start; i <= end ;i++){
			possabilities = appendValue(possabilities, ""+i);
		}
		while(notDone){
			output = (String)JOptionPane.showInputDialog(null, text, title, JOptionPane.PLAIN_MESSAGE, null, possabilities, ""+start);
			if (output == null){
				QuitPrompt();
			}
			else{
				notDone = false;
			}
		}
		return Integer.parseInt(output);
	}
	public static void QuitPrompt(){ // Will prompt for player to quit Y/N. 
		int choice = JOptionPane.showConfirmDialog(null, "Would you like to quit?","Quit?",JOptionPane.YES_NO_OPTION);
		if (choice == 0){
			System.exit(0);
		}
		else if (choice == 1){
			return;
		}
		else{
			System.out.println("Error. Incorrect option chosen. Terminating :(");
			System.exit(0);
		}
	}
}////////////////////////////////END OF METHOD.JAVA///////////////////////////////////////////////