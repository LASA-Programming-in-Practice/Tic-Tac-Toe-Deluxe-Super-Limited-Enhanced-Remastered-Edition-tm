package source;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main {
	static ArrayList<ArrayList<String>> board = new ArrayList<ArrayList<String>>();
	static ArrayList<TileButton> allButtons = new ArrayList<TileButton>();
	static Scanner pl = new Scanner(System.in);
	static int size;
	static int win;
	static int winSize = 600;
	static int isai;
	static int currentPlayer = 0;
	static boolean useConsole = false;
	

	public static void main(String[] args) {
		System.out.println("Size of board");
		size = pl.nextInt();
		System.out.println("Number in a row needed to win");
		win = pl.nextInt();
		System.out.println("AI difficulty (0 for pvp)");
		isai = pl.nextInt();
		
		if(useConsole == false) {
			JFrame frame = new JFrame("Tic-Tac-Toe Deluxe Super Limited Enhanced Remastered Edition™");
			frame.setSize(winSize, winSize + 24);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			JPanel panel = new JPanel(null);
			panel.setSize(winSize, winSize);
			
			
			for(int y = 0; y < size; y++) {
				for(int x = 0; x < size; x++) {
					allButtons.add(new TileButton((y * size) + x));
					panel.add(allButtons.get((y * size) + x));
				}
			}
			frame.add(panel);
			frame.validate();
			frame.setVisible(true);
		}
		if(win > size) {
			System.out.println("You are aware this is impossible to win, right?");
		}
		if(win < 3 | size > 20) { //limits are artificial, size and win condition could go to infinity
			System.out.println("Bad numbers. NO GAME FOR YOU!");
			System.exit(0);
		}
		for(int i = 0; i < size; i++) {{
			board.add(new ArrayList<String>());
		}
			for (int j = 0; j < size; j++) {
				board.get(i).add(new String("-"));
			}
		}

		if(useConsole == true) {
			runConsoleGame();
		}

	}
	protected static void setValue(int loc, String s) {
		int x = (loc % size);
		int y = loc / size;
		board.get(y).set(x, s);
	}

	protected static String getValue(int loc) {
		int x = (loc % size);
		int y = loc / size;
		return board.get(y).get(x);
	}

	private static void display() {
		for (ArrayList<String> row : board) {
			for (String s : row) {
				System.out.print(s + " ");
			}
			System.out.println();
		}
	}
	
	protected static void buttonPressed(int id) throws Exception {

		int[] taboo = new int[size * size];
		
			if(currentPlayer == 0) {
				System.out.println("Player 1 Input: ");

				int within1 = id;
				if(taboo[within1] == -1) {
					System.out.println("That tile is occupied!");
					//throw new Exception("Please select an unoccupied tile");
				} else {
					//System.out.println("This tile should not be occupied");
					setValue(within1,"x");
					allButtons.get(within1).setImageState(1);
					//display();
					checkwin();
					taboo[within1] = -1;
					if(isai == 0) {currentPlayer = 1;}
						
				}
				
			}

			if(isai == 0 && currentPlayer == 1) {
				System.out.println("Player 2 Input: ");
				int within2 = id;
				if(taboo[within2] == -1) {
					System.out.println("That tile is occupied!");
					//throw new Exception("Please select an unoccupied tile");
				} else {
					//System.out.println("This tile should not be occupied");
					setValue(within2,"o");
					allButtons.get(within2).setImageState(2);
					//display();
					checkwin();
					taboo[within2] = -1;
					currentPlayer = 0;
					
				}
			} else {
				System.out.println("AI Move: ");
				checkwin();
				int ernie = generate();
				setValue(ernie, "o");
				allButtons.get(ernie).setImageState(2);
				//display();
				checkwin();
				taboo[ernie] = -1;
			}
		}
	
	
	private static void runConsoleGame() {
		int cookiemonster = 0;
		int[] taboo = new int[size * size];
		
		while(true) {
			Scanner sc = new Scanner(System.in);
			if(cookiemonster == 0) {
				System.out.println("Player 1 Input: ");
				String p1 = sc.nextLine();
				int within1 = Integer.parseInt(p1);
				if(within1 >= size * size || 0 - 1 >= within1) {
					System.out.println("Failure to select reasonable tile.");
					return;
				} else {
					if(taboo[within1] == -1) {
						System.out.println("That tile is occupied!");
						return;
					} else {
						setValue(within1,"x");
						//allButtons.get(within1).setImageState(1);
						display();
						checkwin();
						taboo[within1] = -1;
						
					}
				}
			}
			cookiemonster++;
			if(isai == 0) {
				System.out.println("Player 2 Input: ");
				String p2 = sc.nextLine();
				int within2 = Integer.parseInt(p2);
				if(within2 >= size * size || 0 - 1 >= within2) {
					System.out.println("Failure to select reasonable tile.");
					continue;
				} else {
					if(taboo[within2] == -1) {
						System.out.println("That tile is occupied!");
						continue;
					} else {
						setValue(within2,"o");
						//allButtons.get(within2).setImageState(2);
						display();
						checkwin();
						taboo[within2] = -1;
						cookiemonster = 0;
					}
				}
			} else {
				System.out.println("AI Move: ");
				checkwin();
				int ernie = generate();
				setValue(ernie, "o");
				//allButtons.get(ernie).setImageState(2);
				display();
				checkwin();
				taboo[ernie] = -1;
				cookiemonster = 0;
			}
		}
	}
	
	private static void checkwin() {
		String[] check = new String[size * size];
		int i = 0;
		for (ArrayList<String> row : board) {
			int k = 0;
			for (String s : row) {
				check[i * size + k] = s;
				k++;
			}
			i++;
		}
		String[] againstx = new String[win];
		String[] againsto = new String[win];
		for (int l = 0; l < win; l++) {
			againstx[l] = "x";
			againsto[l] = "o";
		}
		String st1 = String.join("", check);
		String agsx = String.join("", againstx);
		String agso = String.join("", againsto);
		for (int p = 0; p < size * size; p = p + size) {
			String row = st1.substring(p, p + size); // row
			String[] columns = new String[size * size]; // column
			for (int h = 0; h < size; h++) {
				for (int r = 0; r < size; r++) {
					columns[h * size + r] = check[h + size * r];
				}
			}
			String colstr = String.join("", columns);
			String col = colstr.substring(p, p + size);
			if (row.contains(agsx) | col.contains(agsx)) { // check rows + columns
				System.out.println("Player 1 wins!");
				System.exit(0);
			}
			if (row.contains(agso) | col.contains(agso)) {
				System.out.println("Player 2 wins!");
				System.exit(0);
			}
		}
		String[] beaker = new String[size * size]; // diagonals
		String[] beaker2 = new String[size * size];
		for (ArrayList<String> row : board) {
			for (String s : row) {
				int m = 0;
				int n = 0;
				if (s == "x" || s == "o") {
					int elmo = board.indexOf(row);
					int bigbird = row.indexOf(s);
					for (int w = 1; w < win; w++) {
						if (elmo - w >= 0 && bigbird - w >= 0
								&& board.get(elmo - w).get(bigbird - w) == board.get(elmo).get(bigbird)) {
							beaker[m] = board.get(elmo).get(bigbird);
							m++;
						}
						if (elmo + w < size && bigbird - w >= 0
								&& board.get(elmo + w).get(bigbird - w) == board.get(elmo).get(bigbird)) {
							beaker2[n] = board.get(elmo).get(bigbird);
							n++;
						}
					}
					String beak = String.join("", beaker);
					String beak2 = String.join("", beaker2);
					String agssx = agsx.substring(0, win - 1);
					String agsso = agso.substring(0, win - 1);
					if (beak.contains(agssx) | beak2.contains(agssx)) { // check diagonals
						System.out.println("Player 1 wins!");
						System.exit(0);
					}
					if (beak.contains(agsso) | beak2.contains(agsso)) {
						System.out.println("Player 2 wins!");
						System.exit(0);
					}
				}
			}
		}
		int v = 0;
		while (v < check.length) { // check for draw
			if (check[v] == "o" || check[v] == "x") {
				v++;
				if (v == check.length) {
					System.out.println("Draw!");
					System.exit(0);
				}
			} else {
				break;
			}
		}
	}

	private static int generate() {
		int bestrow = 0;	//if row is under threat
		int standard = size;
		int up = 0;
		int[] candidates = new int[size];
		for (ArrayList<String> row : board) {
			int linecount = 0;
			for (String s : row) {
				if (s == "x") {
					linecount++;
				}
			}
			if (win - linecount < standard) {
				for (int q = 0; q < size; q++) {
					candidates[q] = -1;
				}
				up = 0;
				candidates[up - up] = board.indexOf(row);
				standard--;
			}
			if (win - linecount == standard) {
				candidates[up] = board.indexOf(row);
			}
			up++;
		}
		int marker = 0;
		for (int u = 0; u < size; u++) {
			for (int p = 0; p < size; p++) {
				if (candidates[u] == p) {
					marker++;
				}
			}
		}
		Random rand = new Random();
		int rowselect = rand.nextInt(marker); 
		bestrow = candidates[rowselect];
		
		String[] check = new String[size * size];
		int i = 0;
		for (ArrayList<String> row : board) {
			int k = 0;
			for (String s : row) {
				check[i * size + k] = s;
				k++;
			}
			i++;
		}
		
		int output = 0;	//if column is about to be taken
		int bestcol = 0;
		int blockcol = 0;
		int pind = 0;
		int colstrind = 0;
		Random rand2 = new Random();
		for (int p = 0; p < size * size; p = p + size) {
			String[] columns = new String[size * size];
			for (int h = 0; h < size; h++) {
				for (int r = 0; r < size; r++) {
					columns[h * size + r] = check[h + size * r];
				}
			}
			String colstr = String.join("", columns);
			String col = colstr.substring(p, p + size);
			int snap = 0;
			for (int k = 0; k < size; k++) {
				String colsub = col.substring(k, k + 1);
				if(colsub.contains("x")) {
					snap++;
				}
			}
			System.out.println("snap= " + snap);
			if (snap == win - 1 && col.contains("-")) {
				blockcol = 1;
				pind = p / size;
				colstrind = col.indexOf("-");
			}
		}
		if (blockcol != 0) {
			bestcol = pind;
			bestrow = colstrind;
		} else {
			System.out.println("random time");
			while (true) {
				bestrow = candidates[rowselect];
				Random rand3 = new Random();
				bestcol = rand3.nextInt(size);
				if(check[bestrow * size + bestcol] != "x" && check[bestrow * size + bestcol] != "o") {
					System.out.println("hi " + check[bestrow * size + bestcol]);
					break;
				} else {
					System.out.println("ok");
					break;
				}
			}
		}
		System.out.println(bestrow + " " + bestcol);

		while (true) {
			String[] pop = new String[size];
			for (int e = 0; e < size; e++) {
				pop[e] = check[bestrow * size + e];
			}
			String popo = String.join("", pop);
			if (popo.contains("-")) {
				if (blockcol != 0) {
					output = bestrow * size + bestcol;
				} else {
					while (true) {
						if (pop[bestcol] != "x" && pop[bestcol] != "o") {
							output = bestrow * size + bestcol;
							break;
						} else {
							bestcol = rand.nextInt(size);
						}
					}
				}
			} else {
				bestrow = rand2.nextInt(size);
				output = bestrow * size + bestcol;
			}
			if (check[output] == "x" || check[output] == "o") {
				continue;
			}
			return (output);
		}
	}
}