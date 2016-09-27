package sites;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import modelo.Politico;

public class Senado extends Site {
	public Senado() throws Exception {
		super(true);
	}

	@Override
	public String getUrl() {
		return "http://www25.senado.leg.br/web/senadores/legislaturas-anteriores";
	}

	@Override
	public List<Politico> getData(Document doc, List<Politico> politicos) throws IOException {
		// WebElement nome = driver.findElement(By.id("nome"));
		// nome.sendKeys(n);
		WebElement leg = driver.findElement(By.id("select-legislatura"));
		// leg.sendKeys("Qualquer Legislatura...");
		leg.sendKeys("54ª Legislatura (2011 - 2015)");
//		WebElement pesq = driver.findElement(By.id("Pesquisa2"));
//		pesq.click();

		parseData();
		// // for (Element e : es) {
		// // System.out.println(e.text());
		// // }
		// //span
		return politicos;
	}

	private void parseData() throws IOException {
		Document doc;
		doc = lePagina();
		Elements epols = doc.select("td > a");
		List<String> urls = new ArrayList<String>();
		for (Element p : epols) {
			System.out.println(p.attr("href"));
			urls.add(p.attr("href"));
		}
		for (String url1 : urls) {
			doc = navega(url1);
			String nome = doc.select("dt:matchesOwn(Nome civil:)").first().nextElementSibling().text();
			// Politico politico = new Politico(id, nome, codinome, estado, par,
			// profissoes, legis, url1);
			// politicos.add(politico);
			System.out.println(nome);
		}
	}
	// JavaBeanToCsv.toCSV(politicos);
}
