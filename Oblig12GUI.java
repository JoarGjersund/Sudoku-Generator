import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.border.BevelBorder;
import javax.swing.plaf.SliderUI;

public class Oblig12GUI extends JFrame implements ActionListener {

	
	//__________________MENU AND STATUS PANEL_____________________
	
	JMenuBar menubar	= new JMenuBar();
	JMenu menu			= new JMenu("Fil");
	JMenuItem menu2		= new JMenuItem("Sjekk løsning");
	JMenu menuitem1		= new JMenu("Vanskelighetsgrad");
	JMenuItem menu3		= new JMenuItem("Generer nytt brett");
	JMenuItem menuitem2 = new JMenuItem("Lett");
	JMenuItem menuitem3 = new JMenuItem("Middels");
	JMenuItem menuitem4 = new JMenuItem("Vanskelig");
	
	JPanel timebar = new JPanel();
	JLabel time = new JLabel();
	
	//__________________NUMBER LISTS_____________________________
	
	ArrayList<JTextField> sudokubrett	= new ArrayList<>();	
	ArrayList<Integer> sudokutall		= new ArrayList<>();
	int[][] map							= new int[9][9];
	int[][] mapz						= new int[9][9];
	
	
	//________________GAME STATS & SETTINGS_____________________
	int lvl		= 1;						// LEVEL VARIABLE (1: easy, 2: medio, 3: hardcore)
	Timer t		= new Timer(1000, this);	// TIMER for playerclock: 1 sec.
	int tid		= 0;						// STARTTIME for playerclock: 0
	int forsok	= 1;						// NUMBER OF FAILED ATTAMPTS BY PLAYER TO SOLVE PUZZLE
	int point	= 0;						// NUMBER OF INVALID GAME TABLES GENERATED (FAKE IT, TIL U MAKE IT!)
	

	
	//=====================[ MAIN GAME WINDOW ]=======================
	Oblig12GUI() {
		this.setTitle("Sudoku");						// GAME WINDOW NAME
		GridLayout l = new GridLayout(0, 9);			// 9x9 GRID LAYOUT
		this.setLayout(l);								// SET LAYOUT
		this.setSize(500, 500);							// WINDOW SIZE ON STARTUP
		this.setLocationRelativeTo(null);				// SET WINDOW IN CENTER
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);	// EXIT ON CLOSE
		t.addActionListener(this);						// LISTEN TO TIMER

		//_________________ADDS MENU AND STATUS PANEL________________
		menubar.add(menu); // FIL
		this.setJMenuBar(menubar);
		
			menu.add(menuitem1);	// FIL>VANSKLIGHETSGRAD
								menuitem1.add(menuitem2); // EASY
								menuitem2.addActionListener(this);
									
								menuitem1.add(menuitem3); // MEDIO
								menuitem3.addActionListener(this);
									
								menuitem1.add(menuitem4); // HARDCORE
								menuitem4.addActionListener(this);
									
		
			menu.add(menu2);		// FIL>SJEKK LØSNING
			menu2.addActionListener(this);
				
			menu.add(menu3);		// FIL>GENERER NYTT BRETT
			menu3.addActionListener(this);

		
			timebar.add(time);		// STATUSBAR
			timebar.setAlignmentX(CENTER_ALIGNMENT);
			this.add(timebar);
			menubar.add(timebar);
		
			
		// SETS EVERYTHING VISIBLE!
		this.setVisible(true);
		
		//  GENERATE SUDOKUBOARD (level, recycle last board?)
		writeSudoku(lvl, false);
	
		
	}

	//=====================[ JOARS AMAZING SUDOKU BOARD GENERATOR ]====================
	
	public void writeSudoku(int level, boolean reuse) {
		
		
		t.stop();											// STOP TIMER WHILE GENERATING BOARD

		//__________GENERATE NEW NUMBERS IF RECYCLE IS FALSE______________________
		if (!reuse){
			while (distributeNumbers()){
				point = point+1;
				time.setText("LASTER BRETT: "+point+" ugyldige brett kastet...");
			this.setVisible(true);
			}
		//__________REMOVE OLD BOARD______________________________________________

		}		
		for (int i = 0; i < sudokubrett.size(); i++) {
			this.remove(sudokubrett.get(i));
		}


		//______________GENERATE COLOR PATTERN AND DIFCIFULTY__________________
		Random rand = new Random();
		int counter1 = -1;

		//	GENERATE TWO RANDOM COLORS
		float a =1f;
		float b =1f;
		
		while (a==b){
			a = rand.nextFloat();
			b = rand.nextFloat();
		}
		

		//___________DEALING TEXTFIELDS_______________
		for (int i = 0; i < 81; i++) {

			JTextField box = new JTextField();
			JTextField boxNumber = sudokubrett.get(i);
			if (reuse){
				System.out.println(boxNumber.getName()+""+boxNumber.getText());

			}
			box.setName(boxNumber.getName());
			box.setText("");

			box.setEditable(true);
			if (level == 3) {

				if (rand.nextBoolean()) {
					box.setText(boxNumber.getName());
					box.setEditable(false);

				}

			} else if (level == 2) {

				if (rand.nextBoolean() || rand.nextBoolean()) {
					box.setText(boxNumber.getName());
					box.setEditable(false);
				}

			} else if (level == 1) {
				if (rand.nextBoolean() || rand.nextBoolean()
						|| rand.nextBoolean()) {
					box.setText(boxNumber.getName());
					box.setEditable(false);
				}

			}

			//________PAINTING AMAZING COLOR PATTERNS___________
			if (counter1 > 7) {
				counter1 = 0;
			} else {
				counter1++;
			}

			if (i < 27 || i > 53 && i < 81) {
				if (Math.cos((counter1 / 3) * Math.PI) == 1) {
					box.setBackground(Color.GRAY);
				} else {
					box.setBackground(Color.lightGray);
				}

			} else if (i > 26 && i < 54) {
				if (Math.cos((counter1 / 3) * Math.PI) == 1) {
					box.setBackground(Color.lightGray);
				} else {

					box.setBackground(Color.gray);
				}
			}
			if (Math.cos(i * Math.PI) == 1) {

				if (box.getBackground().equals(Color.lightGray)) {

					box.setBackground(Color.getHSBColor(a, 0.2f, 0.97f)); // dark-light
				} else {
					box.setBackground(Color.getHSBColor(b, 0.3f, 0.98f)); // light-dark
				}

			} else {

				if (box.getBackground().equals(Color.gray)) {
					box.setBackground(Color.getHSBColor(b, 0.2f, 0.99f)); // light-light
				} else {
					box.setBackground(Color.getHSBColor(a, 0.3f, 0.96f)); // dark-dark
				}

			}

			if (box.isEditable()) {
				box.setBackground(Color.white);
			}
			sudokubrett.set(i, box);
			this.setVisible(true);
		}
	
		//
		// this paints the new board

		for (int i = 0; i < sudokubrett.size(); i++) {
			this.add(sudokubrett.get(i));
		}
		this.setVisible(true);
		// start timer
		t.start();

	}

	public boolean distributeNumbers() {
		
		for (int i = 0; i < sudokubrett.size(); i++) {
			this.remove(sudokubrett.get(i));
		}

		// clear map
		for (int l = 0; l < map.length; l++) {
			for (int m = 0; m < map.length; m++) {
				map[l][m] = 0;
				mapz[l][m] = 0;
			}

		}

		sudokubrett.clear();
	//	sudokutall.clear();

		// generate random number
		Random rand = new Random();

		// game number counting algorithm variables:
		boolean fail = false;
		int x = -1; // row
		int y = -1; // column

		// generate 9x9 numbers to distribute
		ArrayList<Integer> realnumber = new ArrayList<>();

		for (int h = 0; h < 9; h++) {

			for (int g = 1; g < 10; g++) {
				realnumber.add(g);
			}

		}
		//
		for (int i = 0; i < 81; i++) {

			JTextField box = new JTextField();
			
			
			

			// generate number position variables (x: row, y: coloumn)
			if (i % 9 == 0) {
				x++;
				y = 0;
			} else {
				y++;
			}
			  int majorRow = (x) / 3;  // zero based majorRow
			  int majorCol = (y) / 3;  // zero based majorCol
			  int z = majorCol + majorRow * 3;

			
			
			
			// pick random number from number array
			int index = rand.nextInt(realnumber.size());
			int number = realnumber.get(index);
			
			// count number of trials.
			int error = 0;
			// Create array for numbers tried.
			ArrayList<Integer> oldnumber = new ArrayList<>();
			
			// checking for duplicates in row and column.
			while (checkDuplicates(x, y, z, number)) {
				index = rand.nextInt(realnumber.size());
				number = realnumber.get(index);
				error++;
				
				// if random number is not used yet, add number to array
				if (!oldnumber.contains(number)) {
					oldnumber.add(number);
				}
				
				// if all numbers have been tried, retry.
				if (oldnumber.size() == 9) {

					box.setBackground(Color.black);
					fail = true;
					break;
				}
	/*			if (error > 100-i) {
					box.setBackground(Color.red);
					fail = true;
					break;
				}
	*/
			}
			oldnumber.clear();
			realnumber.remove(index);
			box.setName("" + number);
			sudokubrett.add(i, box);
			
		//	this.add(sudokubrett.get(i));
			
			if (fail){
				return fail;
			}

		}
		return fail;
	}

	public boolean checkDuplicates(int x, int y, int z, int num) {
		// check x
		for (int j = 0; j < map[x].length; j++) {
			if (map[x][j] == num) {
				return true;
			}
		}
		
		// check y
		for (int k = 0; k < map.length; k++) {
			if (map[k][y] == num) {
				return true;
			}
		}
		
		// check square
			if (mapz[z][num-1]==num) {
				return true;
		}
		
		mapz[z][num-1] = num;
		map[x][y] = num;
		return false;
	}
	
	public void solve(){
		boolean win = true;		
		for (int i = 0; i < sudokubrett.size(); i++) {

			JTextField box = sudokubrett.get(i);
			if (box.getText().equals(box.getName())){
				if (box.isEditable()){
					box.setBackground(Color.LIGHT_GRAY);
					box.setEditable(false);
				}
			}else{
				win = false;

			}
			this.setVisible(true);
		}
		if (win){
			
		JOptionPane.showMessageDialog(this, "Gratulerer du hadde alle rett! Du brukte bare "+forsok+" forsøk.");
		int nytt = JOptionPane.showConfirmDialog(this, "Nytt spill?");
		if (nytt == JOptionPane.YES_OPTION){
			forsok=1;
			writeSudoku(lvl, true);
		}else{
			dispose();
		}
		}else{
			forsok++;
			JOptionPane.showMessageDialog(this, "Her var det noen feil ja... Prøv igjen.");	
		}
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(menuitem2)) {
			lvl=1;
			tid=0;
			this.writeSudoku(lvl, true);

		} else if (e.getSource().equals(menuitem3)) {
			lvl=2;
			tid=0;
			writeSudoku(lvl, true);

		} else if (e.getSource().equals(menuitem4)) {
			lvl=3;
			tid=0;
			writeSudoku(lvl, true);
		}
		if (e.getSource().equals(menu2)){
			solve();
		}

		if (e.getSource().equals(t)){
			tid++;
			//System.out.print("tid: "+tid);
		}
		if (e.getSource().equals(menu3)){
			tid=0;
			this.setVisible(false);
			writeSudoku(lvl, false);
			//System.out.print("tid: "+tid);
		}
		
		time.setText("  Tid: "+tid+" | Forsøk: "+forsok);
	}

}
