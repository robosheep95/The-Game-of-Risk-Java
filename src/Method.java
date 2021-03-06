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
	private ArrayList<Player> PlayerList = new ArrayList<Player>();
	private Deck CardDeck = new Deck();
	private Board GameBoard = new Board();
	private Player CurrentPlayer;
	private int intCurrentPlayer;
	private boolean gotSector = false;
	private boolean TwoPlayer = false;
	public Player winner = null;
/////////////////////////////////GAME SET UP METHODS///////////////////////////////////////////	
	public void StartGame() {
		CardDeck.setDeck();
		int choice = NumberRangeBox(2,5,"Select Number of Players","Players");
		if(choice == 2){
			TwoPlayerSetUp();
		}
		else{;
			PlayerSetUp(choice);
		}
	}
	public void TwoPlayerSetUp(){
		TwoPlayer = true;
		ArrayList<Sector> SectorList = GameBoard.SectorList;
		PlayerList.add(new Player(DialogBox("Player 1: Enter Name", "Enter Name", "")));
		PlayerList.add(new Player(DialogBox("Player 2: Enter Name", "Enter Name", "")));
		CurrentPlayer = PlayerList.get(0);
		for(int i = 0;i<24;i++){
			String choice = OptionBox(CurrentPlayer.getPlayerName() +", Please Choose a Sector", "Sector Select", SectorList.toArray(), SectorList.get(0).getName());
			for(int j = 0; j< SectorList.size();j++){
				Sector checkSector = SectorList.get(j);
				if(checkSector.getName()==choice){
					CurrentPlayer.addSector(SectorList.get(j));
					SectorList.remove(j);
				}
			}
			nextPlayer();
		}
		PlayerList.add(new Player("Neutral",true,CardDeck.draw(),CardDeck.draw(),CardDeck.draw(),CardDeck.draw(),CardDeck.draw(),CardDeck.draw()));
		while(!(SectorList.isEmpty())){
			SectorList.get(0).setTroops(2);
			PlayerList.get(2).addSector(SectorList.get(0));
			SectorList.remove(0);
		}
	}
	public void PlayerSetUp(int iPlayerCount){
		ArrayList<Sector> SectorList = GameBoard.SectorList;
		for(int i=0;i<iPlayerCount;i++){
			PlayerList.add(new Player(DialogBox("Player "+(i+1)+": Enter Name", "Enter Name", "")));
		}
		CurrentPlayer = PlayerList.get(0);
		while(!(SectorList.isEmpty())){
			String choice = OptionBox(CurrentPlayer.getPlayerName() +", Please Choose a Sector", "Sector Select", SectorList.toArray(), SectorList.get(0).getName());
			for(int j = 0; j< SectorList.size();j++){
				Sector checkSector = SectorList.get(j);
				if(checkSector.getName()==choice){
					CurrentPlayer.addSector(SectorList.get(j));
					SectorList.remove(j);
				}
			}
			nextPlayer();
		}
	}
/////////////////////////////////PLACE TROOP METHODS///////////////////////////////////////////
	
/////////////////////////////////BATTLE METHODS///////////////////////////////////////////	
	public void Battle(){
		ArrayList<Sector> CanAttackFrom = new ArrayList<Sector>();
		for (int i = 0;i<CurrentPlayer.getSectorList().size();i++){
			if (CurrentPlayer.getSectorList().get(i).getTroops()>1){
				CanAttackFrom.add(CurrentPlayer.getSectorList().get(i));
			}
		}
		if(CanAttackFrom.isEmpty()){
			System.out.println("No sectors to attack from");
			return;
		}
		Sector attack = ChooseSector(CurrentPlayer.getPlayerName()+". Choose a sector to attack from", "Attack", CurrentPlayer.getSectorList());
		ArrayList<Sector> attackable = CreateAttackable(attack);
		if(attackable.isEmpty()){
			System.out.println("No enemy sectors to attack from "+attack.getName());
		}
		else{
			Sector defend = ChooseSector(CurrentPlayer.getPlayerName()+". Choose a sector to attack", "Attack",attackable);
			int attackTroops = 0;
			int defendTroops = 0;
			attackTroops = NumberRangeBox(1, attack.getTroops()-1, "Select Number of Troops to Deploy", "Attack");
			defendTroops = defend.getTroops();
			BattleExecute(attackTroops, defendTroops, attack, defend);
		}
	}
	private ArrayList<Sector> CreateAttackable(Sector attack){
		ArrayList<Sector> Attackable = new ArrayList<Sector>();
		Attackable.addAll(attack.getNeighbors());
		System.out.println(Attackable);
		for (int i = 0;i<Attackable.size();i++){
			ArrayList<Sector> Owned = CurrentPlayer.getSectorList();
			for (int j = 0;j<Owned.size();j++){
				Sector Check1 = Attackable.get(i);
				Sector Check2 = Owned.get(j);
				if(Check1.getName()==Check2.getName()){
					Attackable.remove(i);
					i--;
					break;
				}
			}
		}
		return Attackable;
	}
	private void BattleExecute(int attackTroops, int defendTroops, Sector attack, Sector defend){//Will take 2 sectors as input
		Random die = new Random();
		System.out.println(attackTroops);
		ArrayList<Integer> attackDice = new ArrayList<Integer>();
		ArrayList<Integer> defendDice = new ArrayList<Integer>();
		int moveTroops = attackTroops;
		if (defendTroops >=2){
			defendTroops = 2;
		}
		for(int i=0; i<attackTroops;i++){
			attackDice.add((die.nextInt(5)+1));
			System.out.println("AttackDie "+attackDice.get(i));
		}
		Collections.sort(attackDice);
		for(int i=0; i<defendTroops;i++){
			defendDice.add(die.nextInt(5)+1);
		}
		Collections.sort(defendDice);
		for(int i=0;i<defendTroops && i<attackTroops;i++){
			int attackDie = attackDice.get(attackTroops-i-1);
			int defendDie = defendDice.get(defendTroops-i-1);
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
	public void DrawCard(){
		if (gotSector){
			if(!(CardDeck.isEmpty())){
				CurrentPlayer.addCard(CardDeck.draw());
				gotSector = false;
			}
		}
	}
/////////////////////////////////CHECK WIN METHOD//////////////////////////////////////////
	public boolean CheckWin(){
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
				if(checkPlayer.getSectorList().size() == GameBoard.SectorList.size()){
					winner = checkPlayer;
					return true;
				}
			}
		return false;
		}
	}
/////////////////////////////////NEXT PLAYER METHOD////////////////////////////////////////
	public void nextPlayer(){
		if (intCurrentPlayer == PlayerList.size()-1 || PlayerList.get(intCurrentPlayer+1).isNeutral()){
			intCurrentPlayer = 0;
			CurrentPlayer = PlayerList.get(intCurrentPlayer);
		}
		else{
			intCurrentPlayer++;
			CurrentPlayer = PlayerList.get(intCurrentPlayer);
		}
	}
/////////////////////////////////OBJECT METHODS///////////////////////////////////////////
	private Object[] appendValue(Object[] obj, Object newObj) {//Taken from the Internet. Give append method to an Object[]

		ArrayList<Object> temp = new ArrayList<Object>(Arrays.asList(obj));
		temp.add(newObj);
		return temp.toArray();
	}
/////////////////////////////////USER INTERFACE METHODS///////////////////////////////////////////
	public Sector ChooseSector(String text,String title,ArrayList<Sector> SectorList){
		String choice = OptionBox(text, title, SectorList.toArray(), SectorList.get(0).getName());
		for(int j = 0; j< SectorList.size();j++){
			Sector checkSector = SectorList.get(j);
			if(checkSector.getName()==choice){
				return checkSector;
			}
		}
		return null;
	}
	
	public String DialogBox(String text,String title,String defaultOption){
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
	public String OptionBox(String text,String title,Object[] possabllities,String defaultOption){
		boolean notDone = true;
		while(notDone){
			Object output = JOptionPane.showInputDialog(null, text, title, JOptionPane.PLAIN_MESSAGE, null, possabllities, defaultOption);
			//System.out.println(playerCount);
			if (output == null){
				QuitPrompt();
			}
			else{
				notDone = false;
				return (output.toString());
			}
		}
		return "";
	}
	public int NumberRangeBox(int start, int end, String text,String title){
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
	public boolean YesNoPrompt(String text,String title){
		int n = JOptionPane.showConfirmDialog(null,text,title,JOptionPane.YES_NO_OPTION);
		//System.out.println(n);
		if(n == 0){
			return true;
		}
		else{
			return false;
		}
	}
	public void QuitPrompt(){ // Will prompt for player to quit Y/N. 
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
////////////////////////////////END OF METHOD.JAVA///////////////////////////////////////////////
	public void Present(){
		System.out.println(CurrentPlayer.getPlayerName());
		System.out.println(CurrentPlayer.getSectorList());
		ArrayList<Sector> temp = CurrentPlayer.getSectorList();
		System.out.println(temp.get(0));
		System.out.println(temp.get(0).getTroops());
	}
}