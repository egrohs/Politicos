package sites;

import java.io.IOException;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import modelo.Politico;

public class ContraCorrupcao extends Site {
	public ContraCorrupcao() throws Exception{
		super(false);
	}

	@Override
	public String getUrl() {
		return "http://www.contracorrupcao.org/2013/04/lista-da-corrupcao.html";
	}

	@Override
	public List<Politico> getData(Document doc, List<Politico> politicos) throws IOException {
		Elements sels = doc.select("td > div > span");
		for (Element src : sels) {
			System.out.println(src.text());
		}
		return null;
	}
}
