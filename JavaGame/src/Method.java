import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import javax.swing.JOptionPane;


public class Method {
	
	public static void BattleSetUp(){//Will take 2 sectors
		Sector attack = new Sector();//Temporary, Will take sector as input
		Sector defend = new Sector();
		attack.setTroops(5);
		defend.setTroops(2);
		int attackTroops = 0;
		int defendTroops = 0;
		int maxTroops = 1 - attack.getTroops();
		Object[] troopOptions = {};
		for(int i = 1; i <= attack.getTroops()-1;i++){
			troopOptions = appendValue(troopOptions, ""+i);
		}
		Object[] possibilities = troopOptions;
		String sAttackTroops = (String)JOptionPane.showInputDialog(null, "Select Number of Troops to Deploy", "Attack", JOptionPane.PLAIN_MESSAGE, null, possibilities, "1");
		if (sAttackTroops == "null"){
			return;
		}
		else{
			attackTroops = Integer.parseInt(sAttackTroops);
			defendTroops = defend.getTroops();
			System.out.println(attackTroops);
			Method.battle(attackTroops, defendTroops, attack, defend);
		}
		
	}
	
	public static void battle(int attackTroops, int defendTroops, Sector attack, Sector defend){//Will take 2 sectors as input
		//Sector attack = new Sector();//Temporary, Will take sector as input
		//Sector defend = new Sector();
		Random die = new Random();
		//int attack1 = die.nextInt(5)+1;
		//int attack2 = die.nextInt(5)+1;
		//int attack3 = die.nextInt(5)+1;
		//int defend1 = die.nextInt(5)+1;
		//int defend2 = die.nextInt(5)+1;
		ArrayList attackDice = new ArrayList<Object>();
		ArrayList defendDice = new ArrayList<Object>();
		int moveTroops = attackTroops;
		if (attackTroops >=3){
			attackTroops = 3;
		}
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
				attack.subTroop();
			}
			else{
				System.out.println("Attacker "+ (i+1) + " was Sucessful");
				defend.subTroop();
			}
		}
		//System.out.println(attackDice);
		//System.out.println(defendDice);
		//System.out.println(attack.getTroops());
		//System.out.println(defend.getTroops());
			
		if(defend.getTroops() == 0){
			System.out.println("Attackers overwhelmed the opposition");
			defend.setPlayer(attack.getPlayer());   //!! For later use when players are added
			defend.setTroops(moveTroops);
		}
	
	}
	private static Object[] appendValue(Object[] obj, Object newObj) {//Taken from the internet. Give append to an Object[]

		ArrayList<Object> temp = new ArrayList<Object>(Arrays.asList(obj));
		temp.add(newObj);
		return temp.toArray();
	}
}