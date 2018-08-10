package DataStructure;

public class PointsAndScores implements Comparable<PointsAndScores>{
	public Transition t ; 
	public int score ; 
	public long time ; 
	public PointsAndScores (Transition t , int score, long time){
		this.t = t ; 
		this.score = score ; 
		this.time = time ; 
	}
	@Override
	public int compareTo(PointsAndScores pas) {
		if (this.score == pas.score){
			return (int) (this.time - pas.time) ; 
		}
		return -(this.score - pas.score) ; 
	}
}
