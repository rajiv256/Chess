package Moves;

import java.awt.Color;
import java.util.ArrayList;

import GUI.GameState;
import GUI.Pos;
import GUI.Mother;
import GUI.Mother;
import GUI.GameState;

public class Move {
	
	public static Mother m = new Mother();
	
	public static ArrayList<Pos> movesForAll(Pos from , int id , int[][]gameState){
		//System.out.println("enter movesForAll");
		//Mother.printGameState();
		if (id == 6 || id == 12){
			return movesForQueen(from, id, gameState) ; 
		}
		if (id == 5 || id == 11){
			return movesForKing(from, id, gameState);
		}
		if (id == 4 || id == 10){
			return movesForBishop(from, id, gameState) ; 
		}
		if (id == 3 || id == 9){
			return movesForKnight(from, id, gameState) ; 
		}
		if (id == 2 || id == 8){
			return movesForRook(from, id, gameState) ; 
		}
		if (id == 1 || id == 7){
			return movesForPawn(from, id, gameState) ; 
		}		
		return null;
	}
	
	
	
	public static ArrayList<Pos> movesForQueen(Pos from, int qu,int[][] gameState) {
		ArrayList<Pos> moves = new ArrayList<>();
		if (qu == m.b_qu){
			moves.addAll(movesForRook(from,m.b_ro,gameState)) ; 
			moves.addAll(movesForBishop(from, m.b_bi, gameState)) ; 
		}
		if (qu == m.w_qu){
			moves.addAll(movesForRook(from,m.w_ro,gameState)) ; 
			moves.addAll(movesForBishop(from, m.w_bi, gameState)) ; 
		}
		return moves;

	}

	public static ArrayList<Pos> movesForKing(Pos from, int ki,int[][] gameState) {
		ArrayList<Pos> moves = new ArrayList<>();
		int x = from.x ; int y = from.y ;
		int temp_x ; int temp_y ; 
		int[]inc_x = new int[]{0,0,1,1,1,-1,-1,-1} ; 
		int[]inc_y = new int[]{1,-1,0,1,-1,0,1,-1} ; 
		int i ; 
	
		if (ki == m.b_ki){
			for (i = 0 ; i < 8 ; i++){
				temp_x = x ; temp_y = y ; 
				if (checkLimits(x+inc_x[i], y+inc_y[i]) && checkAdjacentToKing(m.w_ki,x+inc_x[i],y+inc_y[i],inc_x,inc_y,gameState)){
					if (gameState[x+inc_x[i]][y+inc_y[i]] == 0){
						moves.add(new Pos(x+inc_x[i] , y +inc_y[i])) ;
					}
					if (gameState[x+inc_x[i]][y+inc_y[i]] >= 7 && gameState[x+inc_x[i]][y+inc_y[i]]<= 12 
							&& gameState[x+inc_x[i]][y+inc_y[i]] != 11){
						moves.add(new Pos(x+inc_x[i], y+inc_y[i])) ; 
					}
					
				}
			}
			
		}
		
		if (ki == m.w_ki){
			for (i = 0 ; i < 8 ; i++){
				temp_x = x ; temp_y = y ; 
				if (checkLimits(x+inc_x[i], y+inc_y[i]) && checkAdjacentToKing(m.b_ki,x+inc_x[i],y+inc_y[i],inc_x,inc_y,gameState)){
					if (gameState[x+inc_x[i]][y+inc_y[i]] == 0){
						moves.add(new Pos(x+inc_x[i] , y +inc_y[i])) ;
					}
					if (gameState[x+inc_x[i]][y+inc_y[i]] >= 1 && gameState[x+inc_x[i]][y+inc_y[i]]<= 6
							/*&& gameState[x+inc_x[i]][y+inc_y[i]] != 5*/){
						moves.add(new Pos(x+inc_x[i], y+inc_y[i])) ; 
					}
					
				}
			}
		}
		return moves;

	}

	private static boolean checkAdjacentToKing(int id, int cur_x, int cur_y, int[] inc_x,int[] inc_y,int[][] gameState) {
		
		int i ; 
		for (i = 0 ; i < 8 ; i++){
			if (checkLimits(cur_x+inc_x[i], cur_y+inc_y[i])){
				if (gameState[cur_x+inc_x[i]][cur_y+inc_y[i]] == id){
					return false ;
				}
			}
		}
		return true ;
	}



	public static ArrayList<Pos> movesForRook(Pos from ,int ro, int[][] gameState) {
		ArrayList<Pos> moves = new ArrayList<>();
		int x = from.x ; 
		int y = from.y ; 
		int temp_x ; int temp_y ;
		
		if (ro == m.b_ro){
			temp_x = x+1 ; temp_y = y ;  
			while ( checkLimits(temp_x, temp_y)){
				if ( gameState[temp_x][temp_y]==0 ){
					moves.add(new Pos(temp_x , temp_y)) ; 
					temp_x += 1 ; 
				}
				else 
					break ;
			}
			if (checkLimits(temp_x, temp_y)){
				Pos p = termStep(temp_x, temp_y, 1, gameState) ;
				if (p !=null){
					moves.add(p) ; 
				}
			}
			temp_x = x - 1 ; temp_y = y ;  
			while ( checkLimits(temp_x, temp_y)){
				if ( gameState[temp_x][temp_y]==0 ){	
					moves.add(new Pos(temp_x , temp_y)) ; 
					temp_x -= 1 ; 
				}
				else
					break ;
			}
			if (checkLimits(temp_x, temp_y)){
				Pos p = termStep(temp_x, temp_y, 1, gameState) ;
				if (p !=null){
					moves.add(p) ; 
				}
			}
			temp_x = x ; temp_y = y - 1 ;  
			while (checkLimits(temp_x, temp_y)){
				if ( gameState[temp_x][temp_y]==0 ){
					moves.add(new Pos(temp_x , temp_y)) ; 
					temp_y -= 1 ; 
				}
				else
					break ;
			}
			if (checkLimits(temp_x, temp_y)){
				Pos p = termStep(temp_x, temp_y, 1, gameState) ;
				if (p !=null){
					moves.add(p) ; 
				}
			}
			temp_x = x ; temp_y = y + 1;  
			while (checkLimits(temp_x, temp_y)){
				if ( gameState[temp_x][temp_y]==0 ){
					moves.add(new Pos(temp_x , temp_y)) ; 
					temp_y += 1 ; 
				}
				else 
					break ;
			}
			if (checkLimits(temp_x, temp_y)){
				Pos p = termStep(temp_x, temp_y, 1, gameState) ;
				if (p !=null){
					moves.add(p) ; 
				}
			}
		}
		if (ro == m.w_ro){
			//System.out.println("Print all the rook moves at : "+x + " "+y);
			temp_x = x+1 ; temp_y = y ;  
			while (checkLimits(temp_x, temp_y)){
				if (gameState[temp_x][temp_y]==0){
					//Mother.printPos(new Pos(temp_x,temp_y));
					moves.add(new Pos(temp_x , temp_y)) ; 
					temp_x += 1 ;
				}
				else 
					break ;
			}
			if (checkLimits(temp_x, temp_y)){
				Pos p = termStep(temp_x, temp_y, 0, gameState) ;
				if (p !=null){
					//Mother.printPos(new Pos(temp_x,temp_y));
					moves.add(p) ; 
				}
			}
			temp_x = x - 1 ; temp_y = y ;  
			while (checkLimits(temp_x, temp_y)){
				if ( gameState[temp_x][temp_y]==0 ){
					moves.add(new Pos(temp_x , temp_y)) ; 
					temp_x -= 1 ; 
				}
				else 
					break ;
			}
			if (checkLimits(temp_x, temp_y)){
				Pos p = termStep(temp_x, temp_y, 0, gameState) ;
				if (p !=null){
					//Mother.printPos(new Pos(temp_x,temp_y));
					moves.add(p) ; 
				}
			}
			temp_x = x ; temp_y = y - 1 ;  
			while (checkLimits(temp_x, temp_y) ){
				if ( gameState[temp_x][temp_y]==0 ){
					//Mother.printPos(new Pos(temp_x,temp_y));
					moves.add(new Pos(temp_x , temp_y)) ; 
					temp_y -= 1 ; 
				}
				else 
					break ;
			}
			if (checkLimits(temp_x, temp_y)){
				Pos p = termStep(temp_x, temp_y, 0, gameState) ;
				if (p !=null){
					//Mother.printPos(new Pos(temp_x,temp_y));
					moves.add(p) ; 
				}
			}
			temp_x = x ; temp_y = y + 1;  
			while (checkLimits(temp_x, temp_y)){
				if ( gameState[temp_x][temp_y]==0 ){
					//Mother.printPos(new Pos(temp_x,temp_y));
					moves.add(new Pos(temp_x , temp_y)) ; 
					temp_y += 1 ; 
				}
				else 
					break ;
			}
			if (checkLimits(temp_x, temp_y)){
				Pos p = termStep(temp_x, temp_y, 0, gameState) ;
				if (p !=null){
					//Mother.printPos(new Pos(temp_x,temp_y));
					moves.add(p) ; 
				}
			}

			
		}
		
		return moves;
	}

	public static ArrayList<Pos> movesForKnight(Pos from, int kn,int[][] gameState) {
		//System.out.println("came to Moves Knight");
		ArrayList<Pos> moves = new ArrayList<>();
		int[] inc_x = new int[] { 2, 1, 1, 2, -2, -1, -2, -1 };
		int[] inc_y = new int[] { 1, 2, -2, -1, 1, 2, -1, -2 };

		int x = from.x;
		int y = from.y;
		int i;
		if (kn == m.b_kn) {
			for (i = 0; i < 8; i++) {
				if (checkLimits(x + inc_x[i], y + inc_y[i])){
					if (gameState[x+inc_x[i]][y+inc_y[i]] == 0){
						moves.add(new Pos(x+inc_x[i],y+inc_y[i])) ;
					}
					else{
						Pos p = termStep(x + inc_x[i], y + inc_y[i], 1, gameState) ; 
						if (p != null){
							moves.add(p) ; 
						}
					}
				}
			}
		}
		if (kn == m.w_kn) {
			
			for (i = 0; i < 8; i++) {
				if (checkLimits(x + inc_x[i], y + inc_y[i]))
					if (gameState[x+inc_x[i]][y+inc_y[i]] == 0)
						moves.add(new Pos(x+inc_x[i],y+inc_y[i])) ; 
					else{
						Pos p = termStep(x + inc_x[i], y + inc_y[i], 0, gameState) ; 
						if (p != null){
							moves.add(p) ; 
						}
					}
			}
		}

		return moves;

	}

	public static ArrayList<Pos> movesForBishop(Pos from, int bi,int[][] gameState) {
		ArrayList<Pos> moves = new ArrayList<>();
		int x = from.x;
		int y = from.y;
		int temp_x;
		int temp_y;
		/* Moves for a black bishop. */
		if (bi == m.b_bi) {
			temp_x = x + 1;
			temp_y = y + 1;
			while (checkLimits(temp_x, temp_y)) {
				if ( gameState[temp_x][temp_y]==0 ){
					moves.add(new Pos(temp_x, temp_y));
					temp_x += 1;
					temp_y += 1;
				}
				else 
					break ;
			}
			if (checkLimits(temp_x, temp_y)){
				Pos p = termStep(temp_x, temp_y, 1, gameState) ;
				if (p !=null){
					moves.add(p) ; 
				}
			}

			temp_x = x + 1;
			temp_y = y - 1;
			while (checkLimits(temp_x, temp_y)) {
				if ( gameState[temp_x][temp_y]==0 ){
					moves.add(new Pos(temp_x, temp_y));
					temp_x += 1;
					temp_y -= 1;
				}
				else
					break ;
			}
			if (checkLimits(temp_x, temp_y)){
				Pos p = termStep(temp_x, temp_y, 1, gameState) ;
				if (p !=null){
					moves.add(p) ; 
				}
			}
				
			temp_x = x - 1;
			temp_y = y + 1;
			while (checkLimits(temp_x, temp_y)) {
				if ( gameState[temp_x][temp_y]==0 ){
					moves.add(new Pos(temp_x, temp_y));
					temp_x -= 1;
					temp_y += 1;
				}
				else
					break ;
			}
			if (checkLimits(temp_x, temp_y)){
				Pos p = termStep(temp_x, temp_y, 1, gameState) ;
				if (p !=null){
					moves.add(p) ; 
				}
			}
				
			temp_x = x - 1;
			temp_y = y - 1;
			while (checkLimits(temp_x, temp_y)) {
				if (gameState[temp_x][temp_y] == 0){
					moves.add(new Pos(temp_x, temp_y));
					temp_x -= 1;
					temp_y -= 1;
				}
				else
					break ;
			}
			if (checkLimits(temp_x, temp_y)){
				Pos p = termStep(temp_x, temp_y, 1, gameState) ;
				if (p !=null){
					moves.add(p) ; 
				}
			}
		}
		/* Moves for white bishop */
		if (bi == m.w_bi) {
		
			temp_x = x + 1;
			temp_y = y + 1;
			while (checkLimits(temp_x, temp_y)) {
				if (gameState[temp_x][temp_y] == 0){
					moves.add(new Pos(temp_x, temp_y));
					temp_x += 1;
					temp_y += 1;
				}
				else
					break ;
			}
			if (checkLimits(temp_x, temp_y)){
				Pos p = termStep(temp_x, temp_y, 0, gameState) ;
				if (p !=null){
					moves.add(p) ; 
				}
			}
			temp_x = x + 1;
			temp_y = y - 1;
			while (checkLimits(temp_x, temp_y) ) {
				if (gameState[temp_x][temp_y] == 0){	
					moves.add(new Pos(temp_x, temp_y));
					temp_x += 1;
					temp_y -= 1;
				}
				else
					break ;
			}
			if (checkLimits(temp_x, temp_y)){
				Pos p = termStep(temp_x, temp_y, 0, gameState) ;
				if (p !=null){
					moves.add(p) ; 
				}
			}
			temp_x = x - 1;
			temp_y = y + 1;
			while (checkLimits(temp_x, temp_y)) {
				if (gameState[temp_x][temp_y] == 0){
					moves.add(new Pos(temp_x, temp_y));
					temp_x -= 1;
					temp_y += 1;
				}
				else
					break ;
			}
			if (checkLimits(temp_x, temp_y)){
				Pos p = termStep(temp_x, temp_y, 0, gameState) ;
				if (p !=null){
					moves.add(p) ; 
				}
			}
			temp_x = x - 1;
			temp_y = y - 1;
			while (checkLimits(temp_x, temp_y)) {
				if (gameState[temp_x][temp_y] == 0){
					moves.add(new Pos(temp_x, temp_y));
					temp_x -= 1;
					temp_y -= 1;
				}
				else
					break ;
			}
			if (checkLimits(temp_x, temp_y)){
				Pos p = termStep(temp_x, temp_y, 0, gameState) ;
				if (p !=null){
					moves.add(p) ; 
				}
			}
		}
		return moves;
	}

	public static Pos termStep(int temp_x, int temp_y, int color,
			int[][] gameState) {
		//ArrayList<Pos> moves = new ArrayList<>();
		if (color == 1) { // If black
			if (gameState[temp_x][temp_y] >= 7
					&& gameState[temp_x][temp_y] <= 12
					/*&& gameState[temp_x][temp_y] != 11*/) {
				
				Pos p = new Pos(temp_x, temp_y);
				return p;
			}
			else {
				//System.out.println("Didn't go in!");
				return null ;
			}
		}
		if (color == 0) { // If white
			if (gameState[temp_x][temp_y] >= 1
					&& gameState[temp_x][temp_y] <= 6
					/*&& gameState[temp_x][temp_y] != 5*/) {
				Pos p = new Pos(temp_x, temp_y);
				return p;
			}
		}
		return null;
	}

	public static boolean checkLimits(int x, int y) {
		if (x >= 0 && x <= 7 && y >= 0 && y <= 7)
			return true;
		return false;
	}

	public static ArrayList<Pos> movesForPawn(Pos from, int pa,int[][] gameState) {
		ArrayList<Pos> moves = new ArrayList<>();
		int x = from.x;
		int y = from.y;
		moves.removeAll(moves);
		/* Moves for black pawn */
		if (pa == m.b_pa) {
			if (checkLimits(x-1, y))
				if(gameState[x - 1][y] == 0 )
					moves.add(new Pos(x - 1, y));
			if (checkLimits(x-2, y))
				if (gameState[x - 2][y] == 0 && x == 6 && gameState[x-1][y] == 0)
					moves.add(new Pos(x - 2, y));
			if (checkLimits(x-1, y-1))
				if(gameState[x - 1][y - 1] >= 7 && gameState[x-1][y-1] <=12/*&& gameState[x - 1][y - 1] != 11*/)
					moves.add(new Pos(x - 1, y - 1));
			if (checkLimits(x-1, y+1))
				if (gameState[x - 1][y + 1] >= 7 && gameState[x-1][y+1]<=12 /*&& gameState[x - 1][y + 1] != 11*/ )
					moves.add(new Pos(x - 1, y + 1));
			
		}
		/* Moves for white pawn */
		if (pa == m.w_pa) {
			if (checkLimits(x+1, y))
				if (gameState[x + 1][y] == 0 )
					moves.add(new Pos(x + 1, y));
			if ( checkLimits(x+2, y))
				if (gameState[x + 2][y] == 0 && x == 1 && gameState[x+1][y] == 0)
					moves.add(new Pos(x + 2, y));
			if ( checkLimits(x+1, y-1))
				if (gameState[x + 1][y - 1] >=1 && gameState[x+1][y-1]<=6 /*&& gameState[x+1][y-1] != 5*/)
					moves.add(new Pos(x + 1, y - 1));
			if ( checkLimits(x+1, y+1))
				if (gameState[x + 1][y +1] >=1 && gameState[x+1][y+1]<=6 /*&& gameState[x+1][y+1] != 5*/)
					moves.add(new Pos(x + 1, y + 1));
			
		}
		
		return moves;

	}

}