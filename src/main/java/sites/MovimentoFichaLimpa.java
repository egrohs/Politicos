package sites;

import java.io.IOException;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import modelo.Politico;

public class MovimentoFichaLimpa extends Site {
	public MovimentoFichaLimpa() throws IOException {
		super(false);
	}

	@Override
	public String getUrl() {
		return "http://www.movimentofichalimpa.com.br/lista-de-politicos/";
	}

	@Override
	public List<Politico> getData(Document doc) throws IOException {
		Elements sels = doc.select("div > a > div");
		for (Element src : sels) {
			System.out.println(src.text());
		}
		return null;
	}
}
