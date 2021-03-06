import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Board {
	ArrayList<Sector> SectorList = new ArrayList<Sector>();

	public Board(){
		File file = new File("MapData.txt");
		try {
			Scanner MapData = new Scanner(file);
			String[] FirstLine = (MapData.nextLine()).split(",");
			for(int i = 0; i<FirstLine.length/2;i++){
				Sector oObject = new Sector(FirstLine[i*2]);
				SectorList.add(oObject);
			}
			for (int j = 0; j < SectorList.size();j++){
				MapData.nextLine();
				//System.out.println(MapData.nextLine());
				//System.out.println(SectorList.get(j).getName());
				SectorList.get(j).setGalaxy(MapData.nextLine());
				String strNeighbors = MapData.nextLine();
				//System.out.println(strNeighbors);
				String[] Neighbors = strNeighbors.split(",");
				//System.out.println(Neighbors);
				for(int n = 0;n<Neighbors.length;n++){
					for(int z = 0;z<SectorList.size();z++){
						if (Neighbors[n].equals(SectorList.get(z).getName())){
							SectorList.get(j).addNeighbor(SectorList.get(z));
							break;
						}
					}
				}
			}
			//System.out.println(SectorList);
			MapData.close();
		}
		catch (FileNotFoundException e) {
			System.err.println("FileNotFound!!!");
		}
		
	}
}
