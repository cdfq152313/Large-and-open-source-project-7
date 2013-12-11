

/**
 * @author chi
 * 功能:
 * 1.將ETtoday上的新聞標題擷取出
 * 2.儲存至mySQL
 * 
 * Pattern:
 * Simple Factory，使用者可以產出PTT或是ETtoday來源的物件
 * 產出新聞抓取的物件集中在News_factory
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
	 * 新聞抓取物件產生
	 */
	public static class News_factory{
		/**
		 * 產生抓取ETtoday新聞的物件
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
		 * 產生抓取Ptt新聞的物件
		 */
		public static PttCrawler get_Ptt(){
			PttCrawler crawler = new PttCrawler();
			crawler.connecter();
			return crawler;
		}
	}
}

