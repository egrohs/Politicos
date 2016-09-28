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

import modelo.Politico;

public class Camara extends Site {
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
		leg.sendKeys("Qualquer Legislatura...");
		WebElement pesq = driver.findElement(By.id("Pesquisa2"));
		pesq.click();

		// monta map
		Map<String, Politico> pols = new HashMap<String, Politico>();
		for (Politico politico : politicos) {
			pols.put(politico.getCamaraPk(), politico);
		}
		pegaUrlsNavega(pols);
		return politicos;
	}

	private void pegaUrlsNavega(Map<String, Politico> politicos) {
		Document doc = lePagina();
		List<String> urls = new ArrayList<String>();
		try {
			urls.add(doc.select("div#content > ul > li > a").first().attr("href"));
		} catch (Exception e) {
			System.err.println("SEM URL");
		}
		for (String camaraUrl : urls) {
			navega(camaraUrl);
			System.out.println(camaraUrl);
			// http://www2.camara.leg.br/deputados/pesquisa/layouts_deputados_biografia?pk=5830756
			// http://www2.camara.leg.br/deputados/pesquisa/layouts_deputados_biografia?pk=178957

			try {
				WebElement biolink = driver.findElement(By.xpath("//a[text()='Biografia']"));
				biolink.click();
			} catch (Exception e) {
				System.out.println("Sem link biografia!");
			}

			scrapDataCriaPolitico(politicos, camaraUrl);
		}
	}

	private void scrapDataCriaPolitico(Map<String, Politico> politicos, String camaraUrl) {
		Document doc = lePagina();
		String camaraPk = driver.getCurrentUrl().split("pk=")[1].replaceFirst("\\D.+", "");
		// System.out.println(id);
		if (!politicos.keySet().contains(camaraPk)) {
			String codinome = "";
			try {
				String cab = doc.select("div.bioNomParlamentrPartido").first().text();
				String txt[] = cab.split(" - ");
				codinome = txt[0];
				txt = txt[1].split("/");
				String partidoAtual = txt[0];
				String nome = "";
				String uf = txt[1];
				String profissoes = "";
				try {
					Element bio = doc.select("div.bioDetalhes").first();
					nome = bio.select("strong").first().text();
					try {
						profissoes = bio.select("span:matchesOwn(Profissões)").first().nextElementSibling().text();
					} catch (Exception e) {
						try {
							profissoes = bio.select("span:matchesOwn(Escolaridade)").first().child(0).text();
						} catch (Exception e1) {
							System.out.println("Não achou Escolaridade: " + camaraUrl);
						}
					}
				} catch (Exception e1) {
					System.err.println("Não achou div.bioDetalhes ou Nome: " + camaraUrl);
				}
				Elements legs = null;
				try {
					legs = doc.select("span:matchesOwn(Legislaturas)").first().siblingElements().select("a > span");
				} catch (Exception e) {
					System.out.println("Não achou div.bioOutros ou Legislaturas: " + camaraUrl);
				}
				String legislaturas = "";
				if (legs != null) {
					StringBuffer sb = new StringBuffer();
					for (Element el : legs) {
						sb.append(el.text() + ", ");
					}
					legislaturas = sb.toString();// legislaturas
				}

				String camaraFoto = "";
				try {
					camaraFoto = doc.select("div.bioFoto > img").attr("src");
				} catch (Exception e) {
					System.out.println("Não achou div.bioFoto : " + camaraUrl);
				}

				String outrosPartidos = "";
				try {
					outrosPartidos = doc.select("div:matchesOwn(Filiações Partidárias)").first().nextElementSibling()
							.text();
				} catch (Exception e) {
					try {
						outrosPartidos = doc.select("span:matchesOwn(Atividades Partidárias)").first()
								.nextElementSibling().text();
					} catch (Exception e1) {
						System.out.println("Não achou div.bioOutrosTitulo ou Atividades Partidárias: " + camaraUrl);
					}
				}
				Politico politico = new Politico(camaraPk, "", nome, codinome, uf, partidoAtual, outrosPartidos,
						profissoes, "Câmara", legislaturas, camaraFoto, camaraUrl);
				politicos.put(camaraUrl, politico);
				System.out.println(politico);
			} catch (Exception e2) {
				System.err.println("Não achou div.bioNomParlamentrPartido");
			}
		}
	}
}
