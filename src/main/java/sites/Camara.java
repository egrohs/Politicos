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

public class Camara extends Site {
	// TODO foto http://www.camara.gov.br/internet/deputado/bandep/169553.jpg
	public Camara() throws Exception {
		super(true);
	}

	@Override
	public String getUrl() {
		return "http://www2.camara.leg.br/deputados/pesquisa";
	}

	@Override
	public List<Politico> getData(Document doc, List<Politico> politicos) throws IOException {
		// WebElement nome = driver.findElement(By.id("nome"));
		// nome.sendKeys(n);
		WebElement leg = driver.findElement(By.name("Legislatura"));
		// leg.sendKeys("Qualquer Legislatura...");
		leg.sendKeys("50º - 1995 a 1999");
		WebElement pesq = driver.findElement(By.id("Pesquisa2"));
		pesq.click();

		Map<String, Politico> pols = new HashMap<String, Politico>();
		// monta map
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

	private void parseData(Map<String, Politico> politicos) {
		Document doc;
		List<String> urls = novosPoliticos(politicos);
		for (String polURL : urls) {
			doc = navega(polURL);
			System.out.println(polURL);
			// http://www2.camara.leg.br/deputados/pesquisa/layouts_deputados_biografia?pk=5830756
			// http://www2.camara.leg.br/deputados/pesquisa/layouts_deputados_biografia?pk=178957

			try {
				WebElement biolink = driver.findElement(By.xpath("//a[text()='Biografia']"));
				biolink.click();
			} catch (Exception e) {
				System.out.println("Sem link biografia!");
			}

			doc = lePagina();
			String id = driver.getCurrentUrl().split("pk=")[1].replaceFirst("\\D.+", "");
			// System.out.println(id);
			if (!politicos.keySet().contains(id)) {
				String codinome = "";
				try {
					codinome = doc.select("div.bioNomParlamentrPartido").first().text();
					String nome = "";
					String estado = "";
					String profissoes = "";
					try {
						Element bio = doc.select("div.bioDetalhes").first();
						nome = bio.select("strong").first().text();
						try {
							estado = bio.select("span:matchesOwn(Naturalidade)").first().nextElementSibling().text();
						} catch (Exception e) {
							System.out.println("Não achou Naturalidade: " + polURL);
						}
						try {
							profissoes = bio.select("span:matchesOwn(Profissões)").first().nextElementSibling().text();
						} catch (Exception e) {
							try {
								profissoes = bio.select("span:matchesOwn(Escolaridade)").first().child(0).text();
							} catch (Exception e1) {
								System.out.println("Não achou Escolaridade: " + polURL);
							}
						}
					} catch (Exception e1) {
						System.err.println("Não achou div.bioDetalhes ou Nome: " + polURL);
					}
					Elements legs = null;
					try {
						//legs = doc.select("div.bioOutros").first().select("a > span");
						legs = doc.select("span:matchesOwn(Legislaturas)").first().siblingElements().select("a > span");
					} catch (Exception e) {
						System.out.println("Não achou div.bioOutros ou Legislaturas: " + polURL);
					}
					String legis = "";
					if (legs != null) {
						StringBuffer sb = new StringBuffer();
						for (Element el : legs) {
							sb.append(el.text() + ", ");
						}
						legis = sb.toString();// legislaturas
					}
					//WebElement parts = null;
					//Element parts=null;
					String par = "";
					try {
						//parts = driver.findElement(By.xpath("//div[@class='bioOutrosTitulo' and text()='Filiações Partidárias: ']/following-sibling::div"));
						par = doc.select("div:matchesOwn(Filiações Partidárias)").first().nextElementSibling().text();
					} catch (Exception e) {
						try {
							//parts = driver.findElement(By.xpath("//div[@class='bioOutrosTitulo' and text()='Atividades Partidárias:']/following-sibling::div"));
							par = doc.select("span:matchesOwn(Atividades Partidárias)").first().nextElementSibling().text();
						} catch (Exception e1) {
							System.out.println("Não achou div.bioOutrosTitulo ou Atividades Partidárias: " + polURL);
						}
					}
//					if (parts != null) {
//						par = parts.text();// partidos
//					}
					Politico politico = new Politico(id, nome, estado, codinome, par, profissoes, legis, polURL);
					politicos.put(polURL, politico);
					System.out.println(politico);
				} catch (Exception e2) {
					System.err.println("Não achou div.bioNomParlamentrPartido");
				}
			}
		}
		JavaBeanToCsv.toCSV(politicos.values());
	}

	private List<String> novosPoliticos(Map<String, Politico> politicos) {
		Document doc;
		doc = lePagina();
		Elements epols = doc.select("div#content > ul > li > a");
		List<String> urls = new ArrayList<String>();
		for (Element p : epols) {
			String url = p.attr("href");
			String pk = url.split("pk=")[1];
			if (pk.contains("&")) {
				pk = pk.split("&")[0];
			}
			// só busca politicos novos
			if (politicos.get(pk) == null) {
				// http://www2.camara.gov.br/deputados/pesquisa/layouts_deputados_biografia?pk=139355&tipo=0
				// http://www2.camara.leg.br/deputados/pesquisa/layouts_deputados_biografia?pk=178957
				// http://www.camara.leg.br/internet/deputado/Dep_Detalhe.asp?id=
				urls.add(url);
			}
		}
		return urls;
	}
}
