/**
 * Chess with AI 
 * 
 * WHITE --> CPU  player = 1 ***   BLACK --> HUMAN  player = 2
 * Indexed from the top.
 * @author rajiv
 * 
 * 
 * 
 * 
 * TO THE INFINITY AND BEYOND ...
 */

package GUI;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import java.io.File;
import java.lang.reflect.Array;

import DataStructure.Piece;
import DataStructure.PointsAndScores;
import DataStructure.Square;

import java.util.PriorityQueue;
import java.util.Random;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import GUI.GameState; 
import Moves.Move ; 
import Moves.Move;
import Algorithms.MiniMax;
import DataStructure.Transition;

public class Mother {
	int[][] array = new int[8][8];
	final static int HEIGHT = 800;
	final static int WIDTH = 1400;
	final static int BOARD_WIDTH = 700;
	final static int BOARD_HEIGHT = 700;
	public static JFrame frame;
	public static JPanel board;
	public static JPanel status;
	public static Pos clickedPos;
	public static ArrayList<Pos> fromTo;
	public static Control[][] buttonArray;
	public static int flag;
	public static int clicked = 0;
	public static int player = 1;
	public static Square[][] boardState;
	//Pieces id . Final. 
	public static int b_pa = 1;
	public static int b_ro = 2;
	public static int b_kn = 3;
	public static int b_bi = 4;
	public static int b_ki = 5;
	public static int b_qu = 6;
	
	public static int w_pa = 7;
	public static int w_ro = 8;
	public static int w_kn = 9;
	public static int w_bi = 10;
	public static int w_ki = 11;
	public static int w_qu = 12;

	public static int empty = 0 ; 
	
	final static int WHITE = 1;
	final static int BLACK = 2;
	public static String imgDir;
	public static ArrayList<Pos> possibleMoves;
	
	public static int[][] gameState ; 
	
	public static Move move ; 
	
	public static int selectPiece ;   // Contains the id of the selected piece.
	static Thread t1;

	Transition nextMove ; 
	static int last_from_id ; 
	static int last_to_id ;  
	
	static PriorityQueue<PointsAndScores> rootsChildrenScores = new PriorityQueue<>() ;
	private static int black_checked = 0 ; 
	
	
	public static void main(String[] args) {
	/* Define a new JFrame as the main container */
		frame = new JFrame("Chess");
		frame.setSize(WIDTH, HEIGHT);
		frame.setLayout(null);
	/* Board that holds the squares and the pieces */
		board = new JPanel();
	/* Introduce the Buttons 8*8 */
		createBoard();
		boardState = new Square[8][8];
	/* Initially with no pieces and position of each square saved */
		initializeBoard();

		String currDir = new File("").getAbsoluteFile().toString();
		imgDir = currDir + "/src/Image/";

		fillBlackArmyInBoardState();
		fillWhiteArmyInBoardState();
		realizeBoard();
		
	/*Buffer array to store the clicked Positions */
		fromTo = new ArrayList<Pos>();

		gameState = new int[8][8] ; 
		gameState = GameState.boardInitialize() ; 
		
		/*printGameState();
		minimax(0,WHITE);
		makeMove(rootsChildrenScores.peek().t.from,rootsChildrenScores.peek().t.to);
		printGameState() ; 
		makeMove(new Pos(6,0),new Pos(3,0));
		printGameState();
		*/
		
		
		
		frame.add(board);
		frame.setVisible(true);
		while (true){
			
			Play();
		}
		//System.out.println("Player : "+player);
		
	}

	public static void printGameState() {
		int i , j ; 
		for ( i = 0 ; i < 8 ; i++){
			for ( j = 0 ; j < 8 ; j++){
				System.out.print(gameState[i][j] + " ");
			}
			System.out.println();
		}
		
	}

	public static void Play() {
		
		if (player == WHITE){
			whitesMove() ;
		}
		
		while(player == BLACK){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	
		return ;
	}
	public static void whitesMove(){
		
		MiniMax m = new MiniMax() ;
		Transition te = m.getBestTransition(gameState);
		makeMove(te.from , te.to) ;
		
		
		return ;
	}
	

	public static void realizeBoard() {
		int i, j;
		for (i = 0; i < 8; i++) {
			for (j = 0; j < 8; j++) {
				drawOnBoard(i, j, boardState[i][j].getId());
			}
		}
	}

	/* Represent the state of the board using the button array */
	public static void drawOnBoard(int i, int j, int id) {
		if (id == b_pa) {
			buttonArray[i][j].button.setIcon(new ImageIcon(imgDir
					+ "b_pawn.png"));
		}
		if (id == b_ro) {
			buttonArray[i][j].button.setIcon(new ImageIcon(imgDir
					+ "b_rook.png"));
		}
		if (id == b_kn) {
			buttonArray[i][j].button.setIcon(new ImageIcon(imgDir
					+ "b_knight.png"));
		}
		if (id == b_bi) {
			buttonArray[i][j].button.setIcon(new ImageIcon(imgDir
					+ "b_bishop.png"));
		}
		if (id == b_ki) {
			buttonArray[i][j].button.setIcon(new ImageIcon(imgDir
					+ "b_king.png"));
		}
		if (id == b_qu) {
			buttonArray[i][j].button.setIcon(new ImageIcon(imgDir
					+ "b_queen.png"));
		}
		if (id == w_pa) {
			buttonArray[i][j].button.setIcon(new ImageIcon(imgDir
					+ "w_pawn.png"));
		}
		if (id == w_ro) {
			buttonArray[i][j].button.setIcon(new ImageIcon(imgDir
					+ "w_rook.png"));
		}
		if (id == w_kn) {
			buttonArray[i][j].button.setIcon(new ImageIcon(imgDir
					+ "w_knight.png"));
		}
		if (id == w_bi) {
			buttonArray[i][j].button.setIcon(new ImageIcon(imgDir
					+ "w_bishop.png"));
		}
		if (id == w_ki) {
			buttonArray[i][j].button.setIcon(new ImageIcon(imgDir
					+ "w_king.png"));
		}
		if (id == w_qu) {
			buttonArray[i][j].button.setIcon(new ImageIcon(imgDir
					+ "w_queen.png"));
		}
	}

	public static void fillBlackArmyInBoardState() {
		int i = 6, j;
		for (j = 0; j < 8; j++) { // Pawns
			boardState[i][j] = new Square(b_pa);
		}
		i = 7;
		boardState[i][0] = new Square(b_ro); // Rook
		boardState[i][7] = new Square(b_ro);
		boardState[i][1] = new Square(b_kn); // Knight
		boardState[i][6] = new Square(b_kn);
		boardState[i][2] = new Square(b_bi); // Bishop
		boardState[i][5] = new Square(b_bi);
		boardState[i][3] = new Square(b_ki); // King
		boardState[i][4] = new Square(b_qu); // Queen
	}

	public static void fillWhiteArmyInBoardState() {
		int i = 1;
		int j;
		for (j = 0; j < 8; j++) { // Pawns
			boardState[i][j] = new Square(w_pa);
		}
		i = 0;
		boardState[i][0] = new Square(w_ro); // Rook
		boardState[i][7] = new Square(w_ro);
		boardState[i][1] = new Square(w_kn); // Knight
		boardState[i][6] = new Square(w_kn);
		boardState[i][2] = new Square(w_bi); // Bishop
		boardState[i][5] = new Square(w_bi);
		boardState[i][3] = new Square(w_ki); // King
		boardState[i][4] = new Square(w_qu); // Queen
	}

	/*
	 * Initialize the board with no pieces i.e .., id = 0 --> No piece in that
	 * square
	 */
	public static void initializeBoard() {
		int i, j;
		for (i = 0; i < 8; i++) {
			for (j = 0; j < 8; j++) {
				boardState[i][j] = new Square(0);
			}
		}
	}

	public static void createBoard() {
		status = new JPanel();
		status.setSize(400, 400);
		status.setLayout(new FlowLayout());
		board = new JPanel();
		board.setSize(BOARD_WIDTH, BOARD_HEIGHT);
		board.setLayout(new GridLayout(8, 8));
		fillWithButtons();
		 
	}

	public static void fillWithButtons() {
		/* 2-D button array. To identify a position. */
		buttonArray = new Control[8][8];
		int i;
		int j;
		for (i = 0; i < 8; i++) {
			for (j = 0; j < 8; j++) {
				final JButton but = new JButton();
				final Control jb = new Control(i, j, but);
				jb.button.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						disableAllHighlights();
						jb.button.setBackground(Color.yellow);
						
						clickedPos = new Pos(jb.getRow(), jb.getCol());
						if (player == BLACK) {
							Icon ic = buttonArray[clickedPos.x][clickedPos.y].button.getIcon();
							
							if (ic != null){
								String imgPath = ic.toString() ;
								clicked += 1 ;
								readButtonClick(imgPath) ;
							}
							else if (clicked == 1){
								clicked += 1 ; 
								readButtonClick(null) ; 
							}
						}
						
					}

				});
				buttonArray[i][j] = jb;
				if (i % 2 == j % 2)
					buttonArray[i][j].button.setBackground(Color.white);
				else
					buttonArray[i][j].button.setBackground(Color.cyan);
				board.add(buttonArray[i][j].button);
			}
		}
	}

	public static void disableAllHighlights() {
		int i, j;
		for (i = 0; i < 8; i++) {
			for (j = 0; j < 8; j++) {
				if (i % 2 == j % 2)
					buttonArray[i][j].button.setBackground(Color.white);
				else
					buttonArray[i][j].button.setBackground(Color.cyan);
			}
		}
	}
	public static void printPos(Pos f){
		System.out.println(f.x + " "+f.y );
	}

	public static void makeMove(Pos from, Pos to) {
		
		ArrayList<Pos> posMoves = null ;
		generatePossibleMoves(from) ; 
		posMoves = possibleMoves ;
		
		disableAllHighlights() ; 
		if ( buttonArray[from.x][from.y].button.getIcon() == null){
			//System.out.println(from.x + " "+from.y);
			System.out.println("Abruptly terminated");
			return ;
		}
		
		
		String fromImg = buttonArray[from.x][from.y].button.getIcon().toString();
		
		Icon toImg = buttonArray[to.x][to.y].button.getIcon();
		if (player == WHITE){
			if (true){
				
				int temp  = gameState[from.x][from.y] ; 
				gameState[from.x][from.y] = 0 ; 
				gameState[to.x][to.y] = temp ; 
				buttonArray[from.x][from.y].button.setBackground(Color.orange) ;  
				if (toImg == null) {
					buttonArray[from.x][from.y].button.setIcon(null);
					buttonArray[to.x][to.y].button.setIcon(new ImageIcon(fromImg));
				} 
				else {
					buttonArray[to.x][to.y].button.setIcon(null);
					buttonArray[from.x][from.y].button.setIcon(null);
					buttonArray[to.x][to.y].button.setIcon(new ImageIcon(fromImg));
				}
				
				//White Pawn Transition. 
				int pawnPos = -1 ; pawnPos = whitePawnInFirstRow() ; 
				if (pawnPos != -1){
					gameState[7][pawnPos] = w_qu ; 
					buttonArray[7][pawnPos].button.setIcon(new ImageIcon(imgDir+"w_queen.png")) ; 
				}
				buttonArray[to.x][to.y].button.setBackground(Color.orange) ;  
				boolean b = MiniMax.checkForCheck(gameState,BLACK) ;
				try {
					Thread.sleep(300) ;
				} catch (InterruptedException e) {
					e.printStackTrace();
				} 
				disableAllHighlights() ; 
				if (b){
					black_checked = 1 ; 
					if (checkGameOver(gameState,BLACK)){
						JOptionPane.showMessageDialog(frame, "White Wins!") ; 
					}
				}
				if (player == BLACK){
					player = WHITE ;
					return ;
				}
				else {
					player = BLACK ; 
					return ;
				}
				
				
			}
		}
		if (player == BLACK){
			for (int i = 0 ; i < posMoves.size() ; i++){
				if (black_checked == 0){
					int fromId = gameState[from.x][from.y] ; int toId = gameState[to.x][to.y] ; 
					gameState[from.x][from.y]= 0 ;
					gameState[to.x][to.y] = fromId ;
					if (MiniMax.checkForCheck(gameState, BLACK)){
						posMoves.remove(i);
					}
					gameState[to.x][to.y] = toId ;
					gameState[from.x][from.y] = fromId ;
				}
			}
			if (black_checked == 1){
				for (int i = 0 ; i < posMoves.size() ; i++){
					//System.out.println("Before: "+posMoves.size());
					int prevId = gameState[from.x][from.y] ; int nowId = gameState[to.x][to.y] ; 
					gameState[from.x][from.y]= 0 ;
					gameState[to.x][to.y] = prevId ;
					if (MiniMax.checkForCheck(gameState, BLACK)){
						posMoves.remove(i);
					}
					//System.out.println("After: "+posMoves.size());
					gameState[to.x][to.y] = nowId ;
					gameState[from.x][from.y] = prevId ;
				}
			}
			
			
			if (validate(to,posMoves)){
				
				int temp  = gameState[from.x][from.y] ; 
				int tid = gameState[to.x][to.y] ; 
				int fid = gameState[from.x][from.y] ; 
				gameState[from.x][from.y] = 0 ; 
				gameState[to.x][to.y] = temp ; 
				// This is to ensure that the King won't make a wrong step and get f****d in the a** 
				if (MiniMax.checkForCheck(gameState, BLACK)){
					gameState[from.x][from.y] = fid ; 
					gameState[to.x][to.y] = tid ; 
					clicked = 0 ; 
					fromTo.removeAll(fromTo) ; 
					clickedPos = null ; 
					return ; 
				}
				if (toImg == null) {
					buttonArray[from.x][from.y].button.setIcon(null);
					buttonArray[to.x][to.y].button.setIcon(new ImageIcon(fromImg));
				} 
				else {
					buttonArray[to.x][to.y].button.setIcon(null);
					buttonArray[from.x][from.y].button.setIcon(null);
					buttonArray[to.x][to.y].button.setIcon(new ImageIcon(fromImg));
				}
				//Black Pawn Transition. 
				int pawnPos = -1 ; pawnPos = blackPawnInFirstRow() ; 
				if (pawnPos != -1){
					gameState[0][pawnPos] = b_qu ; 
					buttonArray[0][pawnPos].button.setIcon(new ImageIcon(imgDir+"b_queen.png")) ; 
				}
				
				black_checked = 0 ;
				boolean b = MiniMax.checkForCheck(gameState, WHITE) ; 
				if (b){
					if (checkGameOver(gameState,WHITE)){
//						System.out.println("White Game Over!");
						JOptionPane.showMessageDialog(frame, "White Wins!") ; 
						return ; 
					}
				}
				if (player == BLACK){
					player = WHITE ;
					return ;
				}
				else {
					player = BLACK ; 
					return ;
				}
			}
		}
		return ;
	}
	
	private static boolean checkGameOver(int[][] gameState, int color) {
		if (color == BLACK){
			ArrayList<Transition> poMoves = MiniMax.getAllPossibleMoves(color, gameState) ; 
			boolean b = true ; 
			for (int i = 0 ; i < poMoves.size() ; i++){
				Pos frm = poMoves.get(i).from ; 
				Pos to = poMoves.get(i).to ; 
				int fid = gameState[frm.x][frm.y] ; 
				int tid = gameState[to.x][to.y] ; 
				
				gameState[frm.x][frm.y] = empty ; gameState[to.x][to.y] = fid ; 
				b = MiniMax.checkForCheck(gameState, color) ; 
				gameState[frm.x][frm.y] = fid ; gameState[to.x][to.y] = tid ; 
				if (!b){
					return false ; 
				}
			}
			return true ; 
		}
		if (color == WHITE){
			ArrayList<Transition> poMoves = MiniMax.getAllPossibleMoves(color, gameState) ; 
			boolean b = true ; 
			for (int i = 0 ; i < poMoves.size() ; i++){
				Pos frm = poMoves.get(i).from ; 
				Pos to = poMoves.get(i).to ; 
				int fid = gameState[frm.x][frm.y] ; 
				int tid = gameState[to.x][to.y] ; 
				
				gameState[frm.x][frm.y] = empty ; gameState[to.x][to.y] = fid ; 
				b = MiniMax.checkForCheck(gameState, color) ; 
				gameState[frm.x][frm.y] = fid ; gameState[to.x][to.y] = tid ; 
				
				if (!b){
					return false ; 
				}
			}
			return true ;
		}
		return false ; 
	}

	/* Returns if a move is valid or not... id of the piece being moved
	 * is in the selectedPiece variable. This function uses that.
	 */
	public static boolean validate(Pos to ,ArrayList<Pos>posMoves) throws NullPointerException{
		
		//System.out.println("Enter validate");
		int i ; 
		
		for ( i = 0 ; i < posMoves.size() ;i++){
			
			if (to.x == posMoves.get(i).x && to.y == posMoves.get(i).y)
				return true ; 
		}
		
		return false ;
		
		//return true ;
	}

	public static void generatePossibleMoves(Pos from) throws NullPointerException{
		possibleMoves = new ArrayList<>() ; 
		//System.out.println(possibleMoves.size());
		
		
		if (selectPiece == b_pa){
			possibleMoves = Move.movesForPawn(from,b_pa,gameState) ; 
		}
		else if (selectPiece == b_bi){
			possibleMoves = Move.movesForBishop(from,b_bi,gameState) ; 
		}
		else if (selectPiece == b_kn){
			possibleMoves = Move.movesForKnight(from,b_kn,gameState) ; 
		}
		else if (selectPiece == b_ro){
			possibleMoves = Move.movesForRook(from,b_ro,gameState) ; 
		}
		else if (selectPiece == b_ki){
			possibleMoves = Move.movesForKing(from,b_ki,gameState) ; 
		}
		else if (selectPiece == b_qu){
			possibleMoves = Move.movesForQueen(from,b_qu,gameState) ; 
		}
		else {
			possibleMoves = Move.movesForAll(from, gameState[from.x][from.y], gameState) ;
		}
		
	}

	
	/* Reads and gives attributes to a button click...validates it */
	public static void readButtonClick(String imgPath) {
		if (clicked == 1){
			if (imgPath == null){
				clicked = 0 ; 
				fromTo.removeAll(fromTo) ; 
				return ;
			}
			if (imgPath.contains("b_")){
				//System.out.println("Selected");
				selectPiece = getIdFromImgPath(imgPath);
				fromTo.add(clickedPos) ;
				//disableAllHighlights() ; 
				highlightPossibleMoves() ; 
				return ;
			}
			if (imgPath.contains("w_")){
				clicked = 0 ; 
				fromTo.removeAll(fromTo) ; 
			}
			return ;
		}
		if (clicked == 2){
			if (imgPath == null || !(imgPath.contains("b_")) && gameState[clickedPos.x][clickedPos.y]!=11){
				fromTo.add(clickedPos) ;	
					//System.out.println("Make move");
					makeMove(fromTo.get(0),fromTo.get(1));
					clicked = 0 ; 
					fromTo.removeAll(fromTo);
					return ; 
                
			}
			if (gameState[clickedPos.x][clickedPos.y]==11){
				clicked = 1 ; 
				 
			}
			if (imgPath.contains("b_")){
				fromTo.set(0, clickedPos) ; 
				//System.out.println("Selection changed");
				selectPiece = getIdFromImgPath(imgPath); 
				highlightPossibleMoves() ; 
				clicked = 1 ; 
				return ; 
			}
			
			else{
				return ;  	
			}
		}
	
	
	}
	
	public static int blackPawnInFirstRow() {
		for (int i = 0 ; i < 8 ; i++){
			if (gameState[0][i] == b_pa){
				return i ; 
			}
		}
		return -1;
	}
	
	public static int whitePawnInFirstRow(){
		for(int i = 0 ; i < 8 ; i++){
			if (gameState[7][i] == w_pa){
				return i ; 
			}
		}
		return -1 ; 
	}
	
	public static void highlightPossibleMoves() {
		int i ; 
		//System.out.println("Yes, Came to highlight possible moves!");
		generatePossibleMoves(clickedPos) ; 
		for (i = 0 ; i < possibleMoves.size() ; i++){
			if (Move.checkLimits(possibleMoves.get(i).x , possibleMoves.get(i).y) && gameState[possibleMoves.get(i).x][ possibleMoves.get(i).y]!=11)
				
				buttonArray[possibleMoves.get(i).x][possibleMoves.get(i).y].button.setBackground(Color.GREEN);
		}
		//possibleMoves.removeAll(possibleMoves);
	}

	public static int getIdFromImgPath(String imgPath){
		if (imgPath == null)
			return -1 ; 
		if (imgPath.contains("b_pawn"))
			return b_pa ;
		if (imgPath.contains("b_bishop"))
			return b_bi ;
		if (imgPath.contains("b_knight"))
			return b_kn ;
		if (imgPath.contains("b_rook"))
			return b_ro ;
		if (imgPath.contains("b_king"))
			return b_ki ;
		if (imgPath.contains("b_queen"))
			return b_qu ;
		if (imgPath.contains("w_pawn"))
			return w_pa ;
		if (imgPath.contains("w_bishop"))
			return w_bi ;
		if (imgPath.contains("w_knight"))
			return w_kn ;
		if (imgPath.contains("w_rook"))
			return w_ro ;
		if (imgPath.contains("w_king"))
			return w_ki ;
		if (imgPath.contains("w_queen"))
			return w_qu ;
		return -1 ; 
	}

	public static void highlightPossMoves(ArrayList<Pos> pomo) {
		int i ; 
		for (i = 0 ; i < pomo.size() ; i++){
			if (Move.checkLimits(pomo.get(i).x , pomo.get(i).y) && gameState[pomo.get(i).x][ pomo.get(i).y]!=11)
				buttonArray[pomo.get(i).x][pomo.get(i).y].button.setBackground(Color.GREEN);
		}
		
	}
	
	
	
}