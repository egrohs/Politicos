package modelo;

import java.io.IOException;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Politicosorg implements Site {
	static String name = "Zilmar Fernandes";
	static String url = "http://www.politicos.org.br/";
	static String s = "perfil/$.shtml' + '?scrollto=conteudo-rede";

	// a pagina esconde o codigo da tabela?
	public List<Politico> getData() throws IOException {
		Document doc = Jsoup.connect(url).get();
		Elements sels = doc.select("div > ul");
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
		return null;
	}
}
