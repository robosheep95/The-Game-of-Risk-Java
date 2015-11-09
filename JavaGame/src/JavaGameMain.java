import javax.swing.JOptionPane;

public class JavaGameMain {

	public static void main(String[] args) {
		//Start by setting up board with txt input using board init
		//Prompt for number of players 2-5
		Object[] possabllities = {"2","3","4","5"};
		boolean notStarted = true;
		while(notStarted){
			String playerCount = (String)JOptionPane.showInputDialog(null, "Select Number of Players", "Players", JOptionPane.PLAIN_MESSAGE, null, possabllities, "2");
			//System.out.println(playerCount);
			if (playerCount == null){
				QuitPrompt();
			}
			else if(Integer.parseInt(playerCount) == 2){
				TwoPlayerSetUp();
				notStarted = false;
			}
			else{
				PlayerSetUp(Integer.parseInt(playerCount));
				notStarted = false;
			}
			
		}
		
	}
	private static void TwoPlayerSetUp(){
		
	}
	private static void PlayerSetUp(int iPlayerCount){
		
	}
	public static void QuitPrompt(){ // Will prompt for player to quit Y/N. 
		int choice = JOptionPane.showConfirmDialog(
			    null, "Would you like to quit?",
			    "Quit?",
			    JOptionPane.YES_NO_OPTION);
		System.out.println(choice);
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
}
