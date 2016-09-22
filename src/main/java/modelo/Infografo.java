package modelo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class Infografo implements Site {
	static String name = "Zilmar Fernandes";
	static String url = "http://infograficos.oglobo.globo.com/brasil/politicos-lava-jato.html";
	static String s = "perfil/$.shtml' + '?scrollto=conteudo-rede";

	public static void main(String[] args) throws IOException {
		new Infografo().getData();
	}

	// If you do use HTMLUnit don't forget to enable JavaScript when you
	// instantiate it).
	// todos desse site tem ocorrencias ruims...
	public List<Politico> getData() throws IOException {
		List<Politico> politicos = new ArrayList<Politico>();
		// Selenium
		// FirefoxProfile profile = new FirefoxProfile();
		System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
		// WebDriver driver = new FirefoxDriver();
		//WebDriver driver = new ChromeDriver();
		
		final WebDriver driver = new HtmlUnitDriver();
	    ((HtmlUnitDriver)driver).setJavascriptEnabled(true);
	    
		driver.get(url);
		String html_content = driver.getPageSource();
		driver.close();

		// Jsoup makes DOM here by parsing HTML content
		Document doc = Jsoup.parse(html_content);
		System.out.println(doc.html());
		// Elements sels = doc.select("div");
		// for (Element src : sels) {
		// System.out.println(src);
		// }
		return politicos;
	}
}
