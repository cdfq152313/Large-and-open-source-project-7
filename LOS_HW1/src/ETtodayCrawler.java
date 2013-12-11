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
	/**
	 * 連接ETtoday的新聞網頁
	 * 並利用regular expression取得該頁含有新聞標題的html碼
	 * @param s 含有新聞標題的regular expression
	 */
	public void connecter(){
		String s = "<h3><a href=\".*\">.*</a></h3>$";
		Pattern pattern_title = Pattern.compile(s);
		
		try {
			//建立HTTP連線的目的地
			URL url = new URL("http://www.ettoday.net/news/news-list.htm");
			//利用URL的openConnection()來建立連線
			HttpURLConnection uc = (HttpURLConnection) url.openConnection();
			uc.connect();
			//將連線取得的回應載入到一個InputStream中，並且是UTF8編碼
			InputStreamReader inputStreamReader = new InputStreamReader(uc.getInputStream(), "UTF8");
			//為標準輸入串流建立緩衝區物件
            BufferedReader br = new BufferedReader(inputStreamReader);
			String str = ""; 
			//讀取緩衝區物件，使用readline方法
			while((str = br.readLine()) != null) {
				//符合新聞標題的行
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
	/**
	 * 將html標籤與新聞標題分開
	 * @param s 儲存第一次將html標籤分開與新聞標題(前段)
	 * @param t 儲存第二次將html標籤分開與新聞標題(後段)
	 */
	public void spliter(){
		String[] s;
		String[] t;
		for(int i = 0; i < data.size(); i++){ 
            s = data.get(i).split("<h3><a href=\".*\">");
            t = s[1].split("</a></h3>");
            data.set(i, t[0]);
        }
	}
	/**
	 * 印出data內資料以便檢查 
	 */
	public void printer(){
		for(int i = 0; i < data.size(); i++){ 
            System.out.print(data.get(i)); 
            System.out.println();
        }
	}
	/**
	 * 將找到的新聞標題儲存至mySQL
	 * @param driver 載入與註冊JDBC驅動程式
	 * @param url 提供JDBC URL
	 * @param user mySQL帳號
	 * @param password mySQL密碼
	 */
	public void mySQL_storage(){
		String driver = "com.mysql.jdbc.Driver"; 
		//jdbc:mysql://主機名稱:連接埠/資料庫名稱?參數1=值1&參數2=值2
        String url = "jdbc:mysql://localhost/chi"; 
        String user = "root"; 
        String password = "10719293";
        Connection conn;
        Statement stmt;
        try { 
        	//載入與註冊JDBC驅動程式
            Class.forName(driver); 
            //從DriverManager取得Connection
            conn = DriverManager.getConnection(url, user, password);
            //取得 Statement物件，執行SQL敘述並取得執行之後的結果
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            if(conn != null && !conn.isClosed()) {
                //System.out.println("資料庫連線測試成功！");
            	//新增資料
            	for(int i = 0; i < data.size(); i++){ 
            		//尋找資料是否已存在
            		//ResultSet物件，代表變更或查詢的結果
            		ResultSet result = stmt.executeQuery("SELECT * FROM ettoday WHERE title='" + data.get(i) + "'");
            		//若不存在
            		if(!result.first()){
            			//使用moveToInsertRow()移至新增資料處
            			result = stmt.executeQuery("SELECT * FROM ettoday");
            			result.moveToInsertRow(); 
            			result.updateString("title", data.get(i));
            			result.insertRow();
                	}
    	        }
            	//關閉與資料庫的連線
                conn.close();
            }
        } 
        catch(ClassNotFoundException e) { 
            System.out.println("找不到驅動程式類別"); 
            e.printStackTrace(); 
        } 
        catch(SQLException e) { 
            e.printStackTrace(); 
        }
	}
}