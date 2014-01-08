import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;


public class Similarity {
	
	private ArrayList<News> News1;
	private ArrayList<News> News2;


	private ArrayList<Pair> simPoints;

	public Similarity(ArrayList<News> n1, ArrayList<News> n2){
		this.News1 = n1;
		this.News2 = n2;
	    this.setSimPoints(countPoints());
	}
			
	public ArrayList<Pair> countPoints(){
		ArrayList<Pair> points = new ArrayList<Pair>();
		for(int i = 0; i < News1.size(); ++i){
		    for(int j = 0; j < News2.size(); ++j){
		         points.add(generatePair(i, j, News1.get(i), News2.get(j)));	
		    }
		}
		
		return points;
	}
	
	public Pair generatePair(int index1, int index2, News n1, News n2){
		Pair p = new Pair();
		p.index1 = index1;
		p.index2 = index2;
		p.News1 = n1; 
		p.News2= n2; 

		p.point = sim(index1, index2);
		return p;
	}
	
	
	public int sim(int index1, int index2){
		HashMap<String, Boolean> d1 = News1.get(index1).getDictionary();
		HashMap<String, Boolean> d2 = News2.get(index2).getDictionary();
		Object[] keyset1 = d1.keySet().toArray();
		int point = 0;
	    for(int i = 0; i < keyset1.length; ++i){
	        if(d2.containsKey(keyset1[i])){
	        	point += 1;
	        }
	    }
	    return point;
	}

	
	

	public ArrayList<Pair> getSimPoints() {
		return simPoints;
	}

	public void setSimPoints(ArrayList<Pair> simPoints) {
		this.simPoints = simPoints;
	}
	
	

}
