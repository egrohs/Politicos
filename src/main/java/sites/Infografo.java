package sites;

import java.io.IOException;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import modelo.Politico;

public class Infografo extends Site {
	public Infografo() throws Exception {
		super(true);
	}

	// todos desse site tem ocorrencias ruims...
	@Override
	public List<Politico> getData(Document doc, List<Politico> politicos) throws IOException {
		Elements sels = doc.select("div.alvo > strong");
		for (Element src : sels) {
			System.out.println(src.text());
		}
		return politicos;
	}

	@Override
	public String getUrl() {
		return "http://infograficos.oglobo.globo.com/brasil/politicos-lava-jato.html";
	}
}
