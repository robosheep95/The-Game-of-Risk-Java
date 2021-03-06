import java.util.ArrayList;

public class Sector {
	private String strName;
	private String strGalaxy;
	private int intTroops;
	private Player player;
	private ArrayList<Sector> Neighbors = new ArrayList<Sector>();
///////////////////////CONSTRUCTORS/////////////////////////////////
	public Sector(){
	setName("");
	setGalaxy("");
	setTroops(0);
	}
	public Sector(String name) {
		setName(name);
		setTroops(3);//CHANGE THIS NUMBER TO 1 AFTER PRESENTATION
	}
///////////////////////MUTATORS/////////////////////////////////
	public void setName(String input){
		strName = input;
	}
	public void setGalaxy(String input){
		strGalaxy = input;
	}
	public void setTroops(int input){
		intTroops = input;
	}
	public void addTroops(int input){
		intTroops = intTroops + input;
	}
	public void subTroops(int input){
		intTroops = intTroops - input;
	}
	public void addTroop(){
		intTroops++;
	}
	public void subTroop(){
		intTroops--;
	}
	public void setPlayer(Player input){
		player = input;
	}
	public void addNeighbor(Sector input){
		Neighbors.add(input);
	}
///////////////////////ACCESSORS/////////////////////////////////	
	public String getName(){
		return strName;
	}
	public String getGalaxy(){
		return strGalaxy;
	}
	public int getTroops(){
		return intTroops;
	}
	public Player getPlayer(){
		return player;
	}
	public ArrayList<Sector> getNeighbors(){
		return Neighbors;
	}
	public String toString(){
		return strName;
	}
}//////////////////////END OF SECTOR.JAVA/////////////////////////////
