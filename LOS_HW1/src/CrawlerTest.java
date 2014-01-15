import junit.framework.TestCase;
/**
 * 
 *
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
        fail("testmySQL_storage");
    }
} 
