package GUI;
import GUI.Pos; 

public class GameState {
	
	//TODO : May be use BitBoards to recognize 
	//       the possible moves and to represent all the moves
	//   	 of the powers. 
	
	public static int[][] boardInitialize() {
		int i , j ; 
		
		int[][] boardState = new int[8][8]; 
		Mother mother = new Mother() ; 
	/* init -> Black Army */	
		// Setup pawns 
		i = 6 ; 
		for ( j = 0 ; j < 8 ; j++){
			boardState[i][j] = Mother.b_pa ; 
		}
		i = 7 ; 
		//Setup rooks
		boardState[i][0] = mother.b_ro ;
		boardState[i][7] = mother.b_ro ;
		//Setup knight
		boardState[i][1] = mother.b_kn ;
		boardState[i][6] = mother.b_kn ;
		//Setup bishop
		boardState[i][2] = mother.b_bi ; 
		boardState[i][5] = mother.b_bi ; 
		//Setup King 
		boardState[i][3] = mother.b_ki ; 
		//Setup queen 
		boardState[i][4] = mother.b_qu ; 
	/* -----------------------------------*/
	
	/* init -> Black Army */
		i = 1 ; 
		for ( j = 0 ; j < 8 ; j++){
			boardState[i][j] = Mother.w_pa ; 
		}
		i = 0 ; 
		//Setup rooks
		boardState[i][0] = mother.w_ro ;
		boardState[i][7] = mother.w_ro ;
		//Setup knight
		boardState[i][1] = mother.w_kn ;
		boardState[i][6] = mother.w_kn ;
		//Setup bishop
		boardState[i][2] = mother.w_bi ; 
		boardState[i][5] = mother.w_bi ; 
		//Setup King 
		boardState[i][3] = mother.w_ki ; 
		//Setup queen 
		boardState[i][4] = mother.w_qu ; 
	/* -------------------------------------*/
		return boardState ; 
		
	}
	
	
}
