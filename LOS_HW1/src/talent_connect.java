import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;


public class talent_connect {
	String host;
	int portNum;
	public talent_connect() {
		host = "ptt.cc";
		portNum = 23;
		try {
			Socket s = new Socket(host,portNum);
			/*new Pipe(s.getInputStream(),System.out).start();
			new Pipe(System.in,s.getOutputStream()).start();
			new Pipe(new ByteArrayInputStream("chihuai".getBytes()),s.getOutputStream()).start();
			new Pipe(new ByteArrayInputStream("ch350006".getBytes()),s.getOutputStream()).start();*/
			new Pipe_2(s.getInputStream(), System.out);
			new Pipe_2(System.in,s.getInputStream(),s.getOutputStream());
			new Pipe_2(new ByteArrayInputStream("chihuai\r".getBytes()),s.getInputStream(),s.getOutputStream());
			new Pipe_2(new ByteArrayInputStream("ch350006\r".getBytes()),s.getInputStream(),s.getOutputStream());
		}
		catch (IOException e) {
			e.printStackTrace();
			System.out.println("Connection fail!");
			return;
		}
		System.out.println("Connection OK!");
	}
}

/*class Pipe extends Thread{
	InputStreamReader InS;
	PrintStream OutS;
	 
	public Pipe(InputStream InS,OutputStream OutS){
	    try {
			this.InS = new InputStreamReader(InS, "Big5");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    this.OutS = new PrintStream(OutS);
	}
	public void run(){
		BufferedReader br = new BufferedReader(InS);
		String line;
		try {
			while((line = br.readLine()) != null){
				OutS.print(line);
				OutS.print("\r\n");
				OutS.flush();
			}
		}
		catch (IOException e) {
			throw new RuntimeException(e.getMessage());
		}
	}
}*/

class Pipe_2{
	public Pipe_2(InputStream InS, InputStream socket_ins, OutputStream OutS){
		new input(InS, OutS).start();
		new output(socket_ins).start();
	}
	public Pipe_2(InputStream socket_ins, OutputStream OutS){
		new output(socket_ins).start();
	}
	class input extends Thread{
		PrintStream OutS;
		BufferedReader br;
		public input(InputStream InS, OutputStream OutS){
			br = new BufferedReader(new InputStreamReader(InS));
			this.OutS = new PrintStream(OutS);
		}
		
		public void run(){
			String line;
			try {
				while((line = br.readLine())!=null){
					OutS.println(line);
					OutS.flush();
				}
			}
			catch (IOException e) {
				throw new RuntimeException(e.getMessage());
			}
		}
	} 
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
				while((line = br.readLine())!=null){
					System.out.println(line);
				}
			}
			catch (IOException e) {
				throw new RuntimeException(e.getMessage());
			}
		}
	}
}
