package sites;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
		WebElement leg = driver.findElement(By.name("Legislatura"));
		// leg.sendKeys("Qualquer Legislatura...");
		leg.sendKeys("54º - 2011 a 2015");
		WebElement pesq = driver.findElement(By.id("Pesquisa2"));
		pesq.click();

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
		Elements epols = doc.select("div#content > ul > li > a");
		List<String> urls = new ArrayList<String>();
		for (Element p : epols) {
			urls.add(p.attr("href"));
			// System.out.println(p.text() + "\t" + p.attr("href"));
			// String[] s = p.text().split(" - ");
			// String[] s1 = s[1].split("/");
			// Politico politico = new
			// Politico(WordUtils.capitalize(s[0].toLowerCase()), s1[1], s1[0],
			// p.attr("href"));
			// politicos.add(politico);
			// System.out.println(politico);
		}
		for (String url1 : urls) {
			doc = navega(url1);

			WebElement ecodinome = driver.findElement(By.xpath("//div[@class='bioNomParlamentrPartido']"));
			String codinome = ecodinome.getText();

			List<WebElement> bio = driver.findElements(By.xpath("//div[@class='bioDetalhes']/strong"));
			String nome = bio.get(0).getText();// nome
			String estado = bio.get(2).getText();// estado
			String profissoes = bio.get(3).getText();// profissoes

			List<WebElement> legs = driver
					.findElements(By.xpath("(//span[@class='bioOutrosTitulo'])[1]/following-sibling::a/span"));
			StringBuffer sb = new StringBuffer();
			for (WebElement el : legs) {
				sb.append(el.getText() + ", ");
			}
			String legis = sb.toString();// legislaturas
			// TODO tratar quando nao acha algum nodo.
			WebElement parts = driver.findElement(By.xpath(
					"//div[@class='bioOutrosTitulo' and text()='Filiações Partidárias: ']/following-sibling::div"));
			String par = parts.getText();// partidos
			Politico politico = new Politico(id, nome, codinome, estado, par, profissoes, legis, url1);
			politicos.add(politico);
			System.out.println(politico);
			// break;
		}
		JavaBeanToCsv.toCSV(politicos);
	}
}
