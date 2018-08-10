package DataStructure;

import GUI.Pos;
import java.util.ArrayList ;
import java.lang.Math ;

public class BitBoard {
	
	public static long getBitBoard(Pos p){
		long l = 0 ; 
		l = l | 1 ;
		l = l << (8*p.x + p.y) ; 
		//l = l | (long)(Math.pow((double)2, (double)(8*p.x + p.y))) ; 
		//System.out.println(l +"    WTF is happenning??\n");
		return l ; 
		
	}
	
	
	
}
