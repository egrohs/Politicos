package sites;

import java.io.IOException;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import modelo.Politico;

public class Veja extends Site {
	public Veja() throws IOException {
		super(false);
	}

	String name = "Zilmar Fernandes";
	String s = "perfil/$.shtml' + '?scrollto=conteudo-rede";

	// todos desse site tem ocorrencias ruims...
	public List<Politico> getData(Document doc) throws IOException {
		Elements sels = doc.select("select");
		for (Element src : sels.get(1).children()) {
			if (src.tagName().equals("option")) {
//				if (name.equals(src.text())) {
//					System.out.println(src);
//					break;
//				}
				System.out.println(src.attr("value"));
				// Document doc1 = Jsoup.connect(url+s.replaceFirst("\\$",
				// )).get();
			}
		}
		return politicos;
	}

	@Override
	public String getUrl() {
		return "http://veja.abril.com.br/infograficos/rede-escandalos/";
	}
}
