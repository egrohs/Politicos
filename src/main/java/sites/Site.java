package sites;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import modelo.JavaBeanToCsv;
import modelo.Politico;

public abstract class Site {
	protected WebDriver driver;
	// private Document doc;
	protected String siteURL;
	protected List<Politico> politicos = new ArrayList<Politico>();

	public abstract String getUrl();

	public abstract List<Politico> getData(Document doc, List<Politico> politicos) throws IOException;

	public Site(boolean needDriver) throws Exception {
		List<Politico> politicos = JavaBeanToCsv.read();
		if (needDriver) {
			// FirefoxProfile profile = new FirefoxProfile();
			System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");
			// WebDriver driver = new FirefoxDriver();
			// final WebDriver driver = new HtmlUnitDriver();
			// ((HtmlUnitDriver)driver).setJavascriptEnabled(true);
			driver = new ChromeDriver();
		}
		politicos = getData(navega(getUrl()), politicos);
		if (driver != null) {
			driver.close();
		}
	}

	protected Document navega(String url) {
		if (driver != null) {
			driver.get(url);
		}
		return lePagina();
	}

	protected Document lePagina() {
		if (driver != null) {
			return Jsoup.parse(driver.getPageSource());
		}
		try {
			return Jsoup.connect(getUrl())
					.userAgent(
							"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.152 Safari/537.36")
					.get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
