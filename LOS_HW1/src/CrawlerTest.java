import junit.framework.TestCase;
/**
 * �L�X�ƾڥH����parse�O�_���T
 * ���G�|�X�{�bconsole
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
        fail("�椸���թ|�����g");
    }
} 