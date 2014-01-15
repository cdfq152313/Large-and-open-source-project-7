import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Properties;
import java.sql.PreparedStatement;

/**
 * @author chi
 * �\��:
 * 1.�NETtoday�W���s�D���D�^��X
 * 2.�x�s��mySQL
 * 
 * Pattern:
 * Simple Factory�A�ϥΪ̥i�H���XPTT�άOETtoday�ӷ�������
 * ���X�s�D����󶰤��bNews_factory
 */
public class HW1 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ETtodayCrawler ET_news = News_factory.get_ETtoday();
		//System.out.println("ptt-------------");
		
		ttCrawler Ptt_news = News_factory.get_Ptt();
		//System.out.println("end");
		
		
		DatabaseCrawler pttDb = new DatabaseCrawler("ptt");
		DatabaseCrawler ettodayDb = new DatabaseCrawler("ettoday");
		ArrayList<News> pttnews = pttDb.GetNews();
		ArrayList<News> ettodaynews = ettodayDb.GetNews();

		for(int i = 0; i < pttnews.size(); ++i){
		    //System.out.println(pttnews.get(i).getTitle());	
		//System.out.println("-----------------------");
		}
		//System.out.println("-----------------------");
		for(int i = 0; i < ettodaynews.size(); ++i){
		    //System.out.println(ettodaynews.get(i).getTitle());	
		//System.out.println("-----------------------");
		}
		//System.out.println(""+ pttnews.size());
		//System.out.println(""+ ettodaynews.size());
		Similarity s = new Similarity(pttnews, ettodaynews);
		ArrayList<Pair> result = s.getSimPoints();
		Collections.sort(result, new Comparator<Pair>(){

			@Override
			public int compare(Pair o1, Pair o2) {
				// TODO Auto-generated method stub
				return o2.point - o1.point;
			}
			
		});
		for(int i = 0; i < result.size(); ++i){
			if(result.get(i).point > 0){
				System.out.println("("+result.get(i).index1 + "," + result.get(i).index2 + "):" + result.get(i).point);
				System.out.println(result.get(i).News1.getTitle() + ","+result.get(i).News2.getTitle());
			}

		}
		
		return;
	}

	/**
	 * �s�D���󲣥�
	 */
	public static class News_factory{
		/**
		 * ���ͧ��ETtoday�s�D������
		 */
		public static ETtodayCrawler get_ETtoday(){
			ETtodayCrawler crawler = new ETtodayCrawler();
			crawler.connecter();
			crawler.spliter();
			//crawler.printer();
			crawler.mySQL_storage();
			
			return crawler;
		}
		/**
		 * ���ͧ��Ptt�s�D������
		 */
		public static PttCrawler get_Ptt(){
			PttCrawler crawler = new PttCrawler();
			crawler.connecter();
			return crawler;
		}
	}
}

