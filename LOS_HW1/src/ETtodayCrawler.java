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
	 * �s��ETtoday���s�D����
	 * �çQ��regular expression��o�ӭ��t���s�D���D��html�X
	 * @param s �t���s�D���D��regular expression
	 */
	public void connecter(){
		String s = "<h3><a href=\".*\">.*</a></h3>$";
		Pattern pattern_title = Pattern.compile(s);
		
		try {
			//�إ�HTTP�s�u���ت��a
			URL url = new URL("http://www.ettoday.net/news/news-list.htm");
			//�Q��URL��openConnection()�ӫإ߳s�u
			HttpURLConnection uc = (HttpURLConnection) url.openConnection();
			uc.connect();
			//�N�s�u��o���^����J��@��InputStream���A�åB�OUTF8�s�X
			InputStreamReader inputStreamReader = new InputStreamReader(uc.getInputStream(), "UTF8");
			//���зǿ�J��y�إ߽w�İϪ���
            BufferedReader br = new BufferedReader(inputStreamReader);
			String str = ""; 
			//Ū��w�İϪ���A�ϥ�readline��k
			while((str = br.readLine()) != null) {
				//�ŦX�s�D���D����
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
	 * �Nhtml���һP�s�D���D���}
	 * @param s �x�s�Ĥ@���Nhtml���Ҥ��}�P�s�D���D(�e�q)
	 * @param t �x�s�ĤG���Nhtml���Ҥ��}�P�s�D���D(��q)
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
	 * �L�Xdata����ƥH�K�ˬd 
	 */
	public void printer(){
		for(int i = 0; i < data.size(); i++){ 
            System.out.print(data.get(i)); 
            System.out.println();
        }
	}
	/**
	 * �N��쪺�s�D���D�x�s��mySQL
	 * @param driver ��J�P��UJDBC�X�ʵ{��
	 * @param url ����JDBC URL
	 * @param user mySQL�b��
	 * @param password mySQL�K�X
	 */
	public void mySQL_storage(){
		String driver = "org.postgresql.Driver"; 
        String url = "jdbc:postgresql://210.61.10.89/"; 
        String user = "Team7"; 
        String password = "2013postgres";
        Connection conn;
        Statement stmt;
        try { 
        	//��J�P��UJDBC�X�ʵ{��
			Class.forName(driver).newInstance();

            //�qDriverManager��oConnection
            conn = DriverManager.getConnection(url, user, password);
            //��o Statement����A����SQL�ԭz�è�o���椧�᪺���G
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            if(conn != null && !conn.isClosed()) {
                //System.out.println("��Ʈw�s�u��զ��\�I");
            	//�s�W���
            	for(int i = 0; i < data.size(); i++){ 
            		//�M���ƬO�_�w�s�b
            		//ResultSet����A�N���ܧ�άd�ߪ����G
            		ResultSet result = stmt.executeQuery("SELECT * FROM ettoday WHERE title='" + data.get(i) + "'");
            		//�Y���s�b
            		if(!result.first()){
            			//�ϥ�moveToInsertRow()���ܷs�W��ƳB
            			result = stmt.executeQuery("SELECT * FROM ettoday");
            			result.moveToInsertRow(); 
            			result.updateString("title", data.get(i));
            			result.insertRow();
                	}
    	        }
            	//�����P��Ʈw���s�u
            	
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