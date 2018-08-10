package Algorithms;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Random;
import Moves.Move;
import GUI.GameState;
import GUI.Mother;
import GUI.Pos ; 
import DataStructure.*;
public class MiniMax extends Mother{
	
	Transition nextMove ; 
	static int last_from_id ; 
	static int last_to_id ;  
	public static int WHITE = 1 ;
	public static int BLACK = 2 ;
	public static int w_checked = 0 ; 
	public static int b_checked = 0 ; 
	static PriorityQueue<PointsAndScores> rootsChildrenScores = new PriorityQueue<>() ;
	
	public static int kingWt = 100000 ; 
	public static int queenWt = 914 ; 
	public static int rookWt = 543 ; 
	public static int bishopWt = 333 ; 
	public static int knightWt = 317 ; 
	public static int pawnWt = 121 ; 
	
	public static int qu_depth = 5 ; 
	public static int ro_depth = 4 ;
	public static int kn_depth = 5 ;
	public static int bi_depth = 4 ;
	public static int pa_depth = 2 ;
	public static int ki_depth = 2 ;
	
	public static int D = 4  ; 
	public static Mother m = new Mother() ;
	
	
	public static int alphaBetaMinimax (int depth , int maxDepth ,int playing ,int alpha , int beta , int[][]gameState, long startTime){
		
		if (alpha >= beta){
			if (playing == WHITE){
				return Integer.MAX_VALUE ;
			}
			if (playing == BLACK){
				return Integer.MIN_VALUE ;
			}
			
		}
		long currentTime = System.currentTimeMillis() ;
//		if (currentTime > startTime+15000){
//			return 0 ; 
//		}
		if (depth == maxDepth){
			int[][] dupState = new int [8][8] ;
			dupState = duplicate(gameState) ; 
			int result = Evaluate(dupState) +15-depth;
			return result ; 
		}
		ArrayList<Transition> moves = new ArrayList<>() ; 
		moves = getAllPossibleMoves(playing, gameState);
			
		if (moves.isEmpty()){
			
			return 0 ; 
		}
		int j ; 
		
		
		ArrayList<Integer> scores = new ArrayList<>() ; 

		int i ; 
		for ( i = 0 ; i < moves.size() ; i++){
				Pos tt = moves.get(i).to ; 
			// Condition eliminates the inclusion of Killing the King moves in the evaluation. 
			//if (gameState[tt.x][tt.y] != Mother.w_ki && gameState[tt.x][tt.y] != Mother.b_ki){
				if (playing == WHITE){
					Pos from = moves.get(i).from ; 
					Pos to = moves.get(i).to ;
					int fid = gameState[from.x][from.y];
					int tid = gameState[to.x][to.y] ;
				
					if (checkForCheck(gameState, WHITE)){
						w_checked = 1 ;
					}
					
					gameState[from.x][from.y] = 0 ;
					gameState[to.x][to.y] = fid ;
					
					
					int currentScore ;
					
					if (checkForCheck(gameState, WHITE)){
						w_checked = 1 ;
						currentScore = -100000 ;    // Changed here .. :/
					}
					else{
						if (w_checked == 1){
							w_checked = 0 ;
						}
						maxDepth = max(maxDepth,updateMaxDepth(fid)) ;
						currentScore = alphaBetaMinimax(depth + 1 ,maxDepth,BLACK,alpha,beta,gameState,startTime);
					}
					alpha = Math.max(alpha, currentScore) ; 
					
					scores.add(currentScore);
					
					/*
					 * As a last resort the king is being
					 * moved to a square which has check
					 * Check Why such steps are being placed. 
					 * 
					 * */
					if (depth == 0){
						/*Pos fr1 = moves.get(i).from ; Pos to1 = moves.get(i).to ;
						int fid1 = gameState[fr1.x][fr1.y] ; int tid1 = gameState[to1.x][to1.y];
						gameState[fr1.x][fr1.y] = 0 ; gameState[to1.x][to1.y]=  fid1 ; */
						if (!checkForCheck(gameState, WHITE))
							rootsChildrenScores.add(new PointsAndScores(moves.get(i), currentScore,System.currentTimeMillis()-startTime));
						/*gameState[fr1.x][fr1.y] = fid1 ; gameState[to1.x][to1.y]=  tid1 ; */
						
					}
					gameState[from.x][from.y] = fid ;
					gameState[to.x][to.y] = tid ; 
				}
				
				if (playing == BLACK){
					Pos from = moves.get(i).from ; 
					Pos to = moves.get(i).to ;
					int fid = gameState[from.x][from.y];
					int tid = gameState[to.x][to.y] ;
					
					if (b_checked == 0 && checkForCheck(gameState, BLACK)){
						b_checked = 1 ; 
					}
					
					
					gameState[from.x][from.y] = 0 ;
					gameState[to.x][to.y] = fid ;
					int currentScore ; 
					
					if (checkForCheck(gameState, BLACK)){
						currentScore = 100000 ;    // Changed here... :/ 
						b_checked = 1 ;
					}
					else{
						if (b_checked == 1){
							b_checked = 0 ;
						}
						maxDepth = max(maxDepth,updateMaxDepth(fid)) ;
						currentScore = alphaBetaMinimax(depth + 1,maxDepth,WHITE,alpha,beta,gameState,startTime) ;  
					}
					
					scores.add(currentScore);
					beta = Math.min(beta, currentScore);
					gameState[from.x][from.y] = fid ;
					gameState[to.x][to.y] = tid ; 
				}
			}
		//}
		if (playing == WHITE){
			return returnMax(scores);
		}
		if (playing == BLACK){
			return returnMin(scores);
		}
		
		
		return 0 ; 
	}
	
	
	public static int updateMaxDepth(int fid) {
		int retVal = 0 ;
		//System.out.println(fid);
		if (fid == m.w_qu || fid == m.b_qu){
			retVal = qu_depth ; 
		}
		if (fid == m.w_ro || fid == m.b_ro){
			retVal = ro_depth ; 
		}
		if (fid == m.w_kn || fid == m.b_kn){
			retVal = kn_depth ; 
		}
		if (fid == m.w_bi || fid == m.b_bi){
			retVal = bi_depth ; 
		}
		if (fid == m.w_ki || fid == m.b_ki){
			retVal = ki_depth ; 
		}
		
		return retVal ; 
	}

	public static int max(int a , int b){
		if (a > b )
			return a ; 
		return b ; 
	}
	public static int Evaluate(int[][] gameState) {
		int score = 0 ; 
		
		int pieceDifference = getPieceDifference(gameState) ; 
		score += pieceDifference ;
		
		score += pieceSquareTables(gameState) ; 
		
		return score ; 
		
	}



	public static int pieceSquareTables(int[][] gameState) {
		int retVal = 0 ; 
		int whiteRet = whitePosAdv(gameState) ; 
		int blackRet = blackPosAdv(gameState) ; 
		retVal = whiteRet - blackRet ; 
		return retVal ;
	}


	public static int blackPosAdv(int[][] gameState) {
		int ret = 0  ; 
		for(int i = 0 ; i < 8 ; i++){
			for (int j = 0 ; j < 8 ; j++){
				if (gameState[i][j] >= 1 && gameState[i][j] <= 6 && gameState[i][j] != 5){
					 
					int pst = PieceSquareTable.getPST(gameState[i][j],i,j) ; 
					ret += pst ; 
				}
			}
		}
		return ret ; 
	}


	public static int whitePosAdv(int[][] gameState) {
		int ret = 0  ; 
		for(int i = 0 ; i < 8 ; i++){
			for (int j = 0 ; j < 8 ; j++){
				if (gameState[i][j] >= w_pa && gameState[i][j] <= w_qu && gameState[i][j] != w_ki){
					//if (gameState[i][j]-6 >= b_pa && gameState[i][j]-6 <= b_qu)
					int pst = PieceSquareTable.getPST(gameState[i][j]-6,7-i,j) ; 
					ret += pst ; 
				}
			}
		}
		return ret ; 
	}


	public static int getControlDifference(int[][] gameState) {
		int controlScoreBlack = getContolBlack(gameState) ; 
		int controlScoreWhite = getControlWhite(gameState) ; 
		
		return controlScoreWhite - controlScoreBlack ;
	}
	
	public static int getContolBlack(int[][] gameState) {
		int retVal = 0 ;
		for (int i = 0 ; i < 8 ; i++){
			for (int j = 0 ; j < 8 ; j++){
				if (gameState[i][j] >= 1 && gameState[i][j] <= 6){
					if (gameState[i][j] == b_pa){
						retVal += movesSize(new Pos(i,j),b_pa,gameState) ; 
					}
					if (gameState[i][j] == b_bi){
						retVal += movesSize(new Pos(i,j),b_bi,gameState) ; 
					}
					if (gameState[i][j] == b_kn){
						retVal += movesSize(new Pos(i,j),b_kn,gameState) ; 
					}
					if (gameState[i][j] == b_ro){
						retVal += movesSize(new Pos(i,j),b_ro,gameState) ; 
					}
					if (gameState[i][j] == b_qu){
						retVal += movesSize(new Pos(i,j),b_pa,gameState) ; 
					}
					if (gameState[i][j] == b_ki){
						retVal += movesSize(new Pos(i,j),b_ki,gameState) ; 
					}
				}
			}
		}
		return retVal ; 			
		
		
	}


	public static int getControlWhite(int[][] gameState) {
		int retVal = 0 ;
		
		for (int i = 0 ; i < 8 ; i++){
			for (int j = 0 ; j < 8 ; j++){
				if (gameState[i][j] >= 7 && gameState[i][j] <= 12){
					if (gameState[i][j] == w_pa){
						retVal += movesSize(new Pos(i,j),w_pa,gameState) ; 
					}
					if (gameState[i][j] == w_bi){
						retVal += movesSize(new Pos(i,j),w_bi,gameState) ; 
					}
					if (gameState[i][j] == w_kn){
						retVal += movesSize(new Pos(i,j),w_kn,gameState) ; 
					}
					if (gameState[i][j] == w_ro){
						retVal += movesSize(new Pos(i,j),w_ro,gameState) ; 
					}
					if (gameState[i][j] == w_qu){
						retVal += movesSize(new Pos(i,j),w_pa,gameState) ; 
					}
					if (gameState[i][j] == w_ki){
						retVal += movesSize(new Pos(i,j),w_ki,gameState) ; 
					}
				}
			}
		}
		return retVal ; 			
					
	}
	
	public static int movesSize(Pos p , int id , int[][] gameState){
		ArrayList<Pos> moves = new ArrayList<Pos>() ; 
		moves = Moves.Move.movesForAll(p, id, gameState) ; 
		int ret = moves.size() ; 
		moves.clear();
		return ret ; 
	}

	private static int getPieceDifference(int[][] gameState) {
		int result = 0 ; 
		HashMap<Integer,Integer> pieceWeight = new HashMap<Integer,Integer>() ; 
		fillPieceWeights(pieceWeight) ; 
		for (int i = 0 ; i < 8 ; i++){
			for (int j = 0 ; j < 8 ; j++){
				if (gameState[i][j] <= m.b_qu && gameState[i][j] >= m.b_pa){
					result -= pieceWeight.get(gameState[i][j]) ; 
				}
				else if (gameState[i][j] <= m.w_qu && gameState[i][j] >= m.w_pa){
					result += pieceWeight.get(gameState[i][j]) ; 
				}
			}
		}
		return result;
	}


	public static void fillPieceWeights(HashMap<Integer, Integer> pieceWeight) {
		 
		pieceWeight.put(m.w_ki, kingWt) ;pieceWeight.put(m.b_ki, kingWt) ;
		pieceWeight.put(m.w_qu, queenWt) ;pieceWeight.put(m.b_qu, queenWt) ;
		pieceWeight.put(m.w_ro, rookWt) ;pieceWeight.put(m.b_ro, rookWt) ;
		pieceWeight.put(m.w_bi, bishopWt) ;pieceWeight.put(m.b_bi, bishopWt) ;
		pieceWeight.put(m.w_kn, knightWt) ;pieceWeight.put(m.b_kn, knightWt) ;
		pieceWeight.put(m.w_pa, pawnWt) ;pieceWeight.put(m.b_pa, pawnWt) ;
		
		
	}


	public static boolean checkForCheck(int[][] gameState , int player){
		int i , j , k ; long l = 0 ; 
		if (player == WHITE){
			ArrayList<Transition> poMo = new ArrayList<Transition>() ;
			poMo = getAllPossibleMoves(BLACK, gameState) ; 
			for (i = 0 ; i < 8 ; i++){
				for (j = 0 ; j < 8 ; j++){
					if (gameState[i][j] == Mother.w_ki){
						l = BitBoard.getBitBoard(new Pos(i,j));
						long temp = 0 ;
						for (k = 0 ; k < poMo.size() ; k++){
							temp = temp | BitBoard.getBitBoard(poMo.get(k).to);
						}
						
						if ((l&temp) != l){
							return false ;
						}
						//System.out.println("Checked the white king!!");
						
						return true ;
					}
				}
			}
			
		}
		else if (player == BLACK){
			ArrayList<Transition> poMo = new ArrayList<Transition>() ;
			poMo = getAllPossibleMoves(WHITE, gameState) ; 
			 
			
			for (i = 0 ; i < 8 ; i++){
				for (j = 0 ; j < 8 ; j++){
					if (gameState[i][j] == Mother.b_ki){
						l = BitBoard.getBitBoard(new Pos(i,j));
						long temp = 0 ;
						// TODO : You can optimize here... Just stop when a match is received 
						//        between l and BitBoard.get...
						for (k = 0 ; k < poMo.size() ; k++){
							temp = temp | BitBoard.getBitBoard(poMo.get(k).to);
						}
						
						if ((l&temp) != l){
							return false ;
						}
						//System.out.println("Black king Checked!!");
						return true ;
					}
				}
			}
		}
		return false ;
	}
	
	


	private static int[][] duplicate(int[][] gameState) {
		int [][] temp = new int[8][8] ; 
		int i , j ; 
		for (i = 0 ; i < 8 ; i++){
			for (j = 0 ; j < 8 ; j++){
				temp[i][j] = gameState[i][j] ;
			}
		}
		return temp ; 
		
	}




	public static int returnMax(ArrayList<Integer> scores) {
		int maxi = Integer.MIN_VALUE ; 
		int i = 0 ; 
		for ( i = 0 ; i < scores.size() ; i++){
			if (maxi < scores.get(i)){
				maxi = scores.get(i) ; 
			}
		}
		return maxi;
	}
	
	public static int returnMin(ArrayList<Integer> scores) {
		int mini = Integer.MAX_VALUE ; 
		int i = 0 ; 
		for ( i = 0 ; i< scores.size() ; i++){
			if (mini > scores.get(i)){
				mini = scores.get(i) ;
			}
		}
		return mini;
	}
	
	public static void undoMove(Transition t,int[][]gameState) {
		Pos from = t.from ; 
		Pos to = t.to ; 
		gameState[from.x][from.y] = last_from_id ; 
		gameState[to.x][to.y] = last_to_id ;
		
		return ;
		
		
	}
	
	public static void placeAMove(Transition t, int[][] gameState) {
		Pos from = t.from ; 
		Pos to = t.to ; 
		last_from_id = gameState[from.x][from.y] ; 
		last_to_id   = gameState[to.x][to.y] ; 
		gameState[from.x][from.y] = 0 ; 
		gameState[to.x][to.y] = last_from_id ;
		return ; 
		
	}
	
	public static boolean isWhite(int id){
		if (id >= 7 && id <= 12){
			return true ; 
		}
		return false ; 
	}
	
	public static boolean isBlack(int id){
		if (id >= 1 && id <= 6){
			return true ; 
		}
		return false ; 
	}
	
	public static ArrayList<Transition> getAllPossibleMoves(int player, int[][] gameState) {
		//System.out.println(w_checked +" "+b_checked);
		ArrayList<Transition> movesDupe = new ArrayList<>() ; 
		int i , j ; 
		ArrayList<Pos> possible = new ArrayList<>() ; 
		if (player == WHITE){
			for ( i = 0 ; i < 8 ; i++){
				for ( j = 0 ; j < 8 ; j++){
					if (isWhite(gameState[i][j])){
						Pos from = new Pos(i,j) ; 
						possible = Move.movesForAll(from ,gameState[i][j],gameState) ; 
						int k ; 
						for ( k = 0 ; k < possible.size(); k++){
							movesDupe.add(new Transition(from, possible.get(k))); 
						}
						
						possible.removeAll(possible);
					}
				}
			}
		}
		
		if (player == BLACK){
			for ( i = 0 ; i < 8 ; i++){
				for ( j = 0 ; j < 8 ; j++){
					if (isBlack(gameState[i][j])){
						Pos from = new Pos(i,j) ; 
						
						possible = Move.movesForAll(from ,gameState[i][j],gameState) ; 
						int k ; 
						for ( k = 0 ; k < possible.size(); k++){
							movesDupe.add(new Transition(from, possible.get(k))); 
						}
						//possible.removeAll(possible);
					}
				}
			}
		}
		return movesDupe ; 
	}
	
	
	
	public static int evaluate(){
		
		Random rand = new Random();
		return rand.nextInt(100);
	}
	
	
	public static Transition getBestTransition(int[][]gameState) {
		rootsChildrenScores.removeAll(rootsChildrenScores);
//		System.out.println("Enter minimax");
		int maxDepth = 3; 
		long startTime = System.currentTimeMillis()  ; 
		alphaBetaMinimax (0 ,maxDepth, WHITE,Integer.MIN_VALUE,Integer.MAX_VALUE,gameState,startTime) ; 
//		System.out.println("Left minimax");
		
		Transition t = rootsChildrenScores.peek().t ;
		System.out.println(rootsChildrenScores.peek().score);
		while (!rootsChildrenScores.isEmpty()){
			PointsAndScores ppp = rootsChildrenScores.poll() ; 
			System.out.print(ppp.score+","+ppp.time+" ");
		}
		System.out.println();
		return t ; 
		
	}

	
}