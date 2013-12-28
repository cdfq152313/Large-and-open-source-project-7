
/**
 * @author chi
 * �\��:
 * 1.�NETtoday�W���s�D���D�^���X
 * 2.�x�s��mySQL
 * 
 * Pattern:
 * Simple Factory�A�ϥΪ̥i�H���XPTT�άOETtoday�ӷ�������
 * ���X�s�D��������󶰤��bNews_factory
 */
public class HW1 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ETtodayCrawler ET_news = News_factory.get_ETtoday();
		PttCrawler Ptt_news = News_factory.get_Ptt();
		
	}
	/**
	 * �s�D������󲣥�
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

