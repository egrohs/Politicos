package sites;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import modelo.JavaBeanToCsv;
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
		// WebElement pesq = driver.findElement(By.id("Pesquisa2"));
		// pesq.click();

		Map<String, Politico> pols = new HashMap<String, Politico>();
		for (Politico politico : politicos) {
			pols.put(politico.getId(), politico);
		}
		parseData(pols);
		// // for (Element e : es) {
		// // System.out.println(e.text());
		// // }
		// //span
		return politicos;
	}

	private void parseData(Map<String, Politico> politicos1) throws IOException {
		Document doc;
		doc = lePagina();
		Elements lines = doc.select("tr");
		List<String> urls = new ArrayList<String>();
		for (Element line : lines) {
			//System.out.println(p.attr("href"));
			String url1 = line.child(0).child(0).attr("href");
			String sp[] = url1.split("/");
			String idsen = sp[sp.length - 1];
			//if(nao existe){
			String partido = line.child(1).text();
			String uf = line.child(2).text();
			//urls.add();
			
			Politico politico = new Politico(idsen, partido, uf, url1);
			politicos1.put(id, politico);
		}
		for (String url1 : urls) {
			doc = navega(url1);
			
			String nome = doc.select("dt:matchesOwn(Nome civil:)").first().nextElementSibling().text();
			String codinome = doc.select("h1 > span").first().text();
			String foto = doc.select("div > img").first().attr("src");
			System.out.println(politico);
		}
		JavaBeanToCsv.toCSV(politicos1.values());
	}
}
