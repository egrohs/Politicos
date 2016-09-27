package sites;

import java.io.IOException;
import java.util.List;

import org.jsoup.nodes.Document;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import modelo.Politico;

public class Excelencias extends Site {
	public Excelencias() throws Exception {
		super(true);
	}

	@Override
	public List<Politico> getData(Document doc, List<Politico> politicos) throws IOException {
		// Ao clicar buscar sem informar nada, ele lista todos? politos do site?
		WebElement txt = driver.findElement(By.name("busca"));
		txt.clear();
		WebElement procurar = driver.findElement(By.name("btBusca"));
		procurar.click();
		WebElement lista = driver.findElement(By.id("contem_busca"));
		System.out.println(lista.getText());
		return null;
	}

	@Override
	public String getUrl() {
		return "http://www.excelencias.org.br/";
	}
}