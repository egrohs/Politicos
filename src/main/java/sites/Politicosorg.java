package sites;

import java.io.IOException;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import modelo.Politico;

public class Politicosorg extends Site {
	public Politicosorg() throws Exception {
		super(true);
	}

	@Override
	public List<Politico> getData(Document doc, List<Politico> politicos) throws IOException {
		while (true) {
			WebElement next = driver.findElement(By.xpath("a[text()='»']"));
			Elements sels = doc.select("div > ul > li > ul > li > div > span");
			for (Element src : sels) {// .get(1).children()) {
				// a[Processos judiciais]
				System.out.println(src);
				// if (src.tagName().equals("option")) {
				// if (name.equals(src.text())) {
				// System.out.println(src);
				// break;
				// }
				// }
			}
			if (next != null) {
				next.click();
				doc = lePagina();
			} else {
				break;
			}
		}
		return politicos;
	}

	@Override
	public String getUrl() {
		return "http://www.politicos.org.br/";
	}
}
