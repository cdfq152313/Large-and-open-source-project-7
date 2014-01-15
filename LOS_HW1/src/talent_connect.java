import java.awt.event.KeyEvent;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class talent_connect {
	//最多同時執行2執行緒
	ExecutorService pool = Executors.newFixedThreadPool(2);
	Boolean flag = true;
	String host = "ptt.cc";
	int portNum = 23;
	Socket s;
	public List<String> data = new ArrayList<String>();
	/**
	 * set socket and connect to the ptt.cc, use two thread to sent command and read data
	 * command to enter the Gossping
	 */
	public talent_connect() {
		
		try {
			s = new Socket(host,portNum);
			new Pipe_2(s.getInputStream());
			String[] cmd = {"chihuai","ch350006"," ","sGossiping\r ","P","P"};
			new Pipe_2(cmd, s.getOutputStream());
		}
		catch (IOException e) {
			e.printStackTrace();
			System.out.println("Connection fail!");
			return;
		}
		/*try {
			Thread.sleep(30000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		flag = false;
		pool.shutdownNow();*/
		System.out.println(pool.isTerminated());
		System.out.println("Connection OK!");
	}
	/**
	 * 
	 * @param str the title without spliter
	 * @return the ptt news title
	 */
	public String spliter(String str){
		String[] s;
		String[] t;
        s = str.split("]");
        //System.out.println(s[1]);
        t = s[1].split("");
		return t[0];
        
	}
	/**
	 * 
	 * @param str the news title sent to the database
	 */
	public void mySQL_storage(String str){
		String driver = "org.postgresql.Driver"; 
        String url = "jdbc:postgresql://210.61.10.89/"; 
        String user = "Team7"; 
        String password = "2013postgres";
        Connection conn;
        Statement stmt;
        try { 
            Class.forName(driver); 
            conn = DriverManager.getConnection(url, user, password);
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            if(conn != null && !conn.isClosed()) {

        		ResultSet result = stmt.executeQuery("SELECT * FROM ptt WHERE title='" + str + "'");
        		if(!result.first()){
        			result = stmt.executeQuery("SELECT * FROM ptt");
        			result.moveToInsertRow(); 
        			result.updateString("title", str);
        			result.insertRow();
        			result.close();
            	}
        		
            }
            stmt.close();
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
	/**
	 * 
	 * @author chi
	 * the pipe that send command to socket and get data from socket
	 */
	class Pipe_2{
		/**
		 * 
		 * @param cmd the command send to socket
		 * @param OutS send cmd to the outputstream
		 * constructor which start a thread sned cmd to socket
		 */
		public Pipe_2(String[] cmd, OutputStream OutS){
			//建立新執行緒
			pool.submit(new input_array(cmd, OutS));

		}
		/**
		 * 
		 * @param socket_ins socket_ins socket puts the data to the inputstream
		 * constructor which start a thread that socket send data to outputstream 
		 */
		public Pipe_2(InputStream socket_ins){
			//建立新執行緒
			pool.submit(new output(socket_ins));
		}
		/**
		 * 
		 * @author chi
		 * use bufferedwriter write the cmd to the socket
		 */
		class input_array extends Thread{
			BufferedReader br;
			BufferedWriter bw;
			String[] cmd;
			public input_array(String[] cmd, OutputStream OutS){
				
				this.cmd = cmd;
				bw = new BufferedWriter(new OutputStreamWriter(OutS));
			}
			
			public void run(){
				for(int i = 0; i < cmd.length; i++){
					InputStream InS = new ByteArrayInputStream(cmd[i].getBytes());

					br = new BufferedReader(new InputStreamReader(InS));
					
					String str;
					try {
						while((str = br.readLine())!=null){
							bw.write(str);
							if(i < 4)
								bw.write('\r');
							bw.flush();
						}
					}
					catch (IOException e) {
						throw new RuntimeException(e.getMessage());
					}
					if(i == 2){
						try {
							Thread.sleep(4000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					else{
						try {
							Thread.sleep(200);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		} 
		/**
		 * 
		 * @author chi
		 * use bufferedreader read the data which the socket send to the inputstream
		 */
		class output extends Thread{
			InputStreamReader InS;
			
			public output(InputStream InS){
				try {
					this.InS = new InputStreamReader(InS, "Big5");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			public void run(){
				BufferedReader br = new BufferedReader(InS);
				String line;
				try {
					while((line = br.readLine())!=null && flag){
						String pattern = "新聞|爆掛|問卦";
						Pattern pattern_title = Pattern.compile(pattern);
						Matcher match = pattern_title.matcher(line);
						if(match.find() == true){
							String temp = spliter(line);
							data.add(temp);
							mySQL_storage(temp);
							//System.out.println(line);
							//System.out.flush();
						}
						line = "";
						//System.out.flush();
					}
				}
				catch (IOException e) {
					throw new RuntimeException(e.getMessage());
				}
			}
		}
	}

}
