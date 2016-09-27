package modelo;

import sites.Senado;

public class Main {
	static String url = "http://www2.camara.gov.br/deputados/pesquisa/layouts_deputados_biografia?pk=$&tipo=0";

	public static void main(String[] args) throws Exception {
		new Senado();
		// new Camara();
		// fotos();
	}

	// private static void fotos() {
	// System.setProperty("webdriver.chrome.driver",
	// "drivers/chromedriver.exe");
	// WebDriver driver = new ChromeDriver();
	// for (String pk : pks) {
	// String u = url.replaceFirst("\\$", pk);
	// driver.get(u);
	// try {
	// WebElement f =
	// driver.findElement(By.xpath("//div[@class='bioFoto']/img"));
	// System.out.println(pk + "\t" + f.getAttribute("src"));
	// } catch (Exception e) {
	// // TODO: handle exception
	// }
	// }
	// driver.close();
	// }
}