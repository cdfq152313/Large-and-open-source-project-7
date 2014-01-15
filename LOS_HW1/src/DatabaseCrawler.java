import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class DatabaseCrawler {
	private ArrayList<News> NewsList = new ArrayList<News>(); 
	public DatabaseCrawler(String news_database){
		String driver = "org.postgresql.Driver"; 
		//jdbc:mysql://�D���W��:�s����/��Ʈw�W��?�Ѽ�1=��1&�Ѽ�2=��2
        String url = "jdbc:postgresql://210.61.10.89/"; 
        String user = "Team7"; 
        String password = "2013postgres";
        Connection conn;
        Statement stmt;
        
        
        
        try { 
        	//��J�P��UJDBC�X�ʵ{��
            Class.forName(driver); 
            //�qDriverManager��oConnection
            conn = DriverManager.getConnection(url, user, password);
            //��o Statement����A����SQL�ԭz�è�o���椧�᪺���G
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            if(conn != null && !conn.isClosed()) {
            	ResultSet result = stmt.executeQuery("SELECT title FROM " + news_database);
            	while(result.next()){
            		News news = new News(result.getString("title"), new SimpleDateFormat("yyyy/MM/dd hh:mm:ss").format(new Date()), "");
            		NewsList.add(news);
            	}
                
            }
            conn.close();
        } 
        catch(ClassNotFoundException e) { 
            System.out.println("�䤣���X�ʵ{�����O"); 
            e.printStackTrace(); 
        } 
        catch(SQLException e) { 
            e.printStackTrace(); 
        }
	}
	
	public ArrayList<News> GetNews(){

		
		return NewsList;
	}
    
}
