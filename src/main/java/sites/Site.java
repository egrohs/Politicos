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
	protected Document doc;
	protected String url;
	protected List<Politico> politicos = new ArrayList<Politico>();

	public abstract String getUrl();

	public abstract List<Politico> getData() throws IOException;

	private WebDriver startDriver() {
		// Selenium
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
			driver.get(getUrl());
			String html = driver.getPageSource();
			doc = Jsoup.parse(html);
		} else {
			doc = Jsoup.connect(getUrl()).get();
		}
		politicos = getData();
		if (driver != null) {
			driver.close();
		}
	}
}
