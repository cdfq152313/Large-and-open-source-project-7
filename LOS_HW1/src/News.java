import java.util.ArrayList;
import java.util.HashMap;


public class News {
	    private String title;
	    private String content = null;
	    private String date;
	    
	    private ArrayList<String> words;
	    private HashMap<String, Boolean> dictionary;

	    public News(String title, String date, String content){
	        this.title = title;
	        this.date = date;
	        this.content = content;
	        this.setDictionary(generateDict(2, 4));
	    }
	    
	    

	    /*
	     * generate a dictionary for words in this news
	     * 
	     *  start -> n gram start 
	     *  end -> n gram end 
	     *  
	     *  ex:
	     *       HashMap<String, Boolean> d = generateDict(2, 4);
	     * 
	     *  this will compute 2-gram to 3-gram words for this news
	     *  and generate a dictionary  
	     *     
	     */
	    public HashMap<String, Boolean> generateDict(int start, int end){
	    	HashMap<String, Boolean> d = new HashMap<String, Boolean>();
	    	for(int i = start; i < end; ++i){
	    	    ArrayList<String> l = nGram(i, this.title);

	    	    for(int j = 0; j < l.size(); ++j){
	    	    	d.put(l.get(j), true);
	    	    }

	    	}
	    	return d;
	    }

	    
		public ArrayList<String> nGram(int n, String str){
		    ArrayList<String> result = new ArrayList<String>();
		    String[] strs = str.split(" ");
		    for(int i = 0; i < strs.length; ++i){
		    	//System.out.println(strs[i]);
		    	for(int j = 0; j < strs[i].length()-n; ++j){
		    		 result.add(strs[i].substring(j, j+n));
		    	}
		    }
			return result;
		}


		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getDate() {
			return date;
		}
		public void setDate(String date) {
			this.date = date;
		}
		
		public HashMap<String, Boolean> getDictionary() {
			return dictionary;
		}

		public void setDictionary(HashMap<String, Boolean> dictionary) {
			this.dictionary = dictionary;
		}

}
