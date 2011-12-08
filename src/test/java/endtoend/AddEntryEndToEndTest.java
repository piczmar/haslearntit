package endtoend;

import org.junit.*;
import org.junit.runner.*;
import org.mortbay.jetty.*;
import org.mortbay.jetty.bio.*;
import org.mortbay.jetty.webapp.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.*;
import org.springframework.test.context.*;
import org.springframework.test.context.junit4.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/ioc/testContext.xml", "classpath:/ioc/backend/applicationContext.xml" })
public class AddEntryEndToEndTest {

	private static final int PORT = 6345;

	@Test
	@Ignore
	public void shouldAddEntry() throws Exception {
		Server server = createServer();
		addContextToServer(server);
		server.start();

		WebDriver driver = new FirefoxDriver();

		driver.navigate().to("http://localhost:" + PORT + "/hello");

	}

	private static Server createServer() {
		Server server = new Server();
		SocketConnector connector = new SocketConnector();

		// Set some timeout options to make debugging easier.
		connector.setMaxIdleTime(1000 * 60 * 60);
		connector.setSoLingerTime(-1);
		connector.setPort(PORT);
		server.setConnectors(new Connector[] { connector });
		return server;
	}

	private static void addContextToServer(Server server) {
		WebAppContext bb = new WebAppContext();
		bb.setServer(server);
		bb.setContextPath("/");
		bb.setWar("src/main/webapp");
		server.addHandler(bb);
	}
}
