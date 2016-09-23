package sites;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import modelo.Politico;

public abstract class Site {
	protected WebDriver driver;
	// private Document doc;
	protected String url;
	protected List<Politico> politicos = new ArrayList<Politico>();

	public abstract String getUrl();

	public abstract List<Politico> getData(Document doc) throws IOException;

	private WebDriver startDriver() {
		// FirefoxProfile profile = new FirefoxProfile();
		System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
		// WebDriver driver = new FirefoxDriver();
		// final WebDriver driver = new HtmlUnitDriver();
		// ((HtmlUnitDriver)driver).setJavascriptEnabled(true);
		return new ChromeDriver();
	}

	public Site(boolean needDriver) throws IOException {
		if (needDriver) {
			driver = startDriver();
		}
		politicos = getData(navega(getUrl()));
		if (driver != null) {
			driver.close();
		}
	}

	protected Document navega(String url) throws IOException {
		if (driver != null) {
			driver.get(url);
		}
		return lePagina();
	}

	protected Document lePagina() throws IOException {
		if (driver != null) {
			return Jsoup.parse(driver.getPageSource());
		}
		return Jsoup.connect(getUrl())
				.userAgent(
						"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.152 Safari/537.36")
				.get();
	}
}
