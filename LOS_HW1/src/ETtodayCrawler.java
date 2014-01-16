import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author chi
 */

public class ETtodayCrawler{
	private List<String> data = new ArrayList<String>();
	public void connecter(){
		String s = "<h3><a href=\".*\">.*</a></h3>$";
		Pattern pattern_title = Pattern.compile(s);
		
		try {
	
			URL url = new URL("http://www.ettoday.net/news/news-list.htm");
		
			HttpURLConnection uc = (HttpURLConnection) url.openConnection();
			uc.connect();
			
			InputStreamReader inputStreamReader = new InputStreamReader(uc.getInputStream(), "UTF8");
		
            BufferedReader br = new BufferedReader(inputStreamReader);
			String str = ""; 
			
			while((str = br.readLine()) != null) {
			
				Matcher match = pattern_title.matcher(str);
				if(match.find() == true){
					data.add(str);
				}
				str = "";
			}
			br.close();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	 
	public void spliter(){
		String[] s;
		String[] t;
		for(int i = 0; i < data.size(); i++){ 
            s = data.get(i).split("<h3><a href=\".*\">");
            t = s[1].split("</a></h3>");
            data.set(i, t[0]);
        }
	}

	public void printer(){
		for(int i = 0; i < data.size(); i++){ 
            System.out.print(data.get(i)); 
            System.out.println();
        }
	}

	public void mySQL_storage(){
		String driver = "org.postgresql.Driver"; 
        String url = "jdbc:postgresql://210.61.10.89/"; 
        String user = "Team7"; 
        String password = "2013postgres";
        Connection conn;
        Statement stmt;
        try { 

			Class.forName(driver).newInstance();

            
            conn = DriverManager.getConnection(url, user, password);
         
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            if(conn != null && !conn.isClosed()) {
          
            
            	for(int i = 0; i < data.size(); i++){ 
            
            		ResultSet result = stmt.executeQuery("SELECT * FROM ettoday WHERE title='" + data.get(i) + "'");
            	
            		if(!result.first()){
            	
            			result = stmt.executeQuery("SELECT * FROM ettoday");
            			result.moveToInsertRow(); 
            			result.updateString("title", data.get(i));
            			result.insertRow();
                	}
    	        }
         
            	
            }
            conn.close();
        } 
        catch(ClassNotFoundException e) { 
            System.out.println("error");
            e.printStackTrace(); 
        } 
        catch(SQLException e) { 
            e.printStackTrace(); 
        }
        catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
        catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
}
