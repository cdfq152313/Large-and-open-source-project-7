import junit.framework.TestCase;
/**
 * 印出數據以驗證parse是否正確
 * 結果會出現在console
 */
public class CrawlerTest extends TestCase {
    private ETtodayCrawler crawler;
   
    @Override
    protected void setUp() {
    	crawler = new ETtodayCrawler();
    }

    @Override
    protected void tearDown() {
    	crawler = null;
    }

    public void testconnecter() {
    	crawler.connecter();
    	crawler.printer();
    }
   
    public void testspliter() {
    	crawler.connecter();
    	crawler.spliter();
    	crawler.printer();
    }
	
	public void testmySQL_storage() {
        fail("單元測試尚未撰寫");
    }
} 