package modelo;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Veja {
	static String name = "Zilmar Fernandes";
	static String url = "http://veja.abril.com.br/infograficos/rede-escandalos/";
	static String s = "perfil/$.shtml' + '?scrollto=conteudo-rede";

	public static void main(String[] args) throws IOException {
		Document doc = Jsoup.connect(url).get();
		Elements sels = doc.select("select");
		for (Element src : sels.get(1).children()) {
			if (src.tagName().equals("option")) {
				if (name.equals(src.text())) {
					System.out.println(src);
					break;
				}
				// System.out.println(src.attr("value"));
				// Document doc1 = Jsoup.connect(url+s.replaceFirst("\\$",
				// )).get();
			}
		}
	}
}
