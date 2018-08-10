package DataStructure;
import java.awt.Image;

import javax.swing.ImageIcon;

/*
 * Class to represent a piece on the board.
 */
public class Piece {
	int id ; 
	boolean alive ; 
	int color ; 
	ImageIcon img ; 
	
	public Piece( int id, int c  ){
		this.id = id ;
		this.color = c ;
		this.img = new ImageIcon("/home/rajiv/CodingIsFun/Chess/src/Image"
				+ assignImage(id , c )) ; 
		
	}

	private String assignImage(int id2, int c) {
		if (c == 1){
			if (id2 >= 1 && id2 <= 8){
				return "b_pawn.png" ; 
			}
			else if (id2 == 9 || id2 == 16){
				return "b_rook.png" ; 
			}
			else if (id2 == 10 || id2 == 15){
				return "b_knight.png" ; 
			}
			else if (id2 == 11 || id2 == 14){
				return "b_bishop.png" ; 
			}
			else if (id2 == 12){
				return "b_king.png" ;
			}
			else if (id2 == 13){
				return "b_queen.png" ;
			}
		}
		if (c == 0){
			if (id2 >= 1 && id2 <= 8){
				return "w_pawn.png" ; 
			}
			else if (id2 == 9 || id2 == 16){
				return "w_rook.png" ; 
			}
			else if (id2 == 10 || id2 == 15){
				return "w_knight.png" ; 
			}
			else if (id2 == 11 || id2 == 14){
				return "w_bishop.png" ; 
			}
			else if (id2 == 13){
				return "w_king.png" ;
			}
			else if (id2 == 12){
				return "w_queen.png" ;
			}
		}
		return null;
	}
	
	
}
