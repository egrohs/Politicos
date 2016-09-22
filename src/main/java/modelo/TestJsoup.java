package modelo;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class TestJsoup {

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		Document doc = Jsoup.parse(loadContentFromClasspath(), "UTF8", "");
		Elements es = doc.getElementsByClass("tr_normal");
		System.out.println("load from file= " + es.size());

		doc = Jsoup.parse(loadContentByHttpClient(), "UTF8", "");
		es = doc.getElementsByClass("tr_normal");
		System.out.println("load from http client= " + es.size());

		String url = "http://www.hkex.com.hk/eng/market/sec_tradinfo" + "/stockcode/eisdeqty_pf.htm";
		doc = Jsoup.connect(url).get();
		es = doc.getElementsByClass("tr_normal");
		System.out.println("load from jsoup connect= " + es.size());

		int maxBodySize = 2048000;// 2MB (default is 1MB) 0 for unlimited size
		doc = Jsoup.connect(url).maxBodySize(maxBodySize).get();
		es = doc.getElementsByClass("tr_normal");
		System.out.println("load from jsoup connect using maxBodySize= " + es.size());
	}

	public static InputStream loadContentByHttpClient() throws ClientProtocolException, IOException {
		String url = "http://www.hkex.com.hk/eng/market/sec_tradinfo" + "/stockcode/eisdeqty_pf.htm";
		HttpClient client = HttpClientBuilder.create().build();
		HttpGet request = new HttpGet(url);
		HttpResponse response = client.execute(request);
		return response.getEntity().getContent();
	}

	public static InputStream loadContentFromClasspath() throws ClientProtocolException, IOException {
		return TestJsoup.class.getClassLoader().getResourceAsStream("eisdeqty_pf.htm");
	}

}