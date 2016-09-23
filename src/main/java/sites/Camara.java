package sites;

import java.io.IOException;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import modelo.Politico;

public class Camara extends Site {
	static String[] nomes = { "Silval da Cunha Barbosa", "Guilherme Maluf", "Paulo Alexandre Barbosa",
			"André Gustavo Pereira Corrêa Da Silva", "Francisco Oswaldo Neves Dornelles", "José Camilo Zito dos Santos",
			"Sérgio Sveiter", "Luiz Fernando De Souza", "G. Alves", "Geraldo Julio de Mello Filho", "J. Agripino",
			"Severino Branquinho", "Cleiber Santana", "Dudu Rosalva", "Abelardo Leopoldo", "Cacau Gomes", "Dinha",
			"Jarbas Vasconcelos Filho", "Jorge Felipe", "Jorge Picciani", "Márcio Della Valle Biolchi", "Mário Kertesz",
			"Nilson Bonome", "Pedro Godinho", "Sérgio Paulo Perrucci De Aquino", "Ademar Delgado", "Antonio Donato",
			"Carmem Gandarella", "Donizete Braga", "Fernando Haddad", "Francisco Daniel Celeguin De Moraes",
			"Humberto Carballal", "Jacques Pena", "Jeferson Andrade", "João Paulo Rillo", "Jorge Miguel Samek",
			"Jussara Márcia", "Luis Tavares", "Luiz Marinho", "Nelson Vicente Portela Pelegrino", "Paulo Garcia",
			"Rodrigo Neves", "Ronaldo Miro Zülke", "José Wellington Barroso de Araújo Dias", "Vânia Galvão",
			"Antônio Carlos Magalhães Neto", "Jefferson Morais", "José Carlos Aleluia Costa", "Leonardo Prates",
			"Milton Leite", "Rodney Miranda", "Agnaldo", "Alberto Pereira Castro", "Diogo Paz Bier", "Fischinha",
			"Paulinho Fiorella", "Heitor Schulk", "José Pavan Junior", "Aidan Antônio Ravin", "Atila Jacomussi",
			"César Souza Junior", "Chiquinho Do Zaira", "Diversos Vereadores", "Geraldo Junior", "Leonice Da Paz",
			"Marcelo Esvim", "Marquinhos Bola", "Maurício Bacelar", "Pedro Serafin", "Firmino Filho",
			"Sonia Francine Gaspar Marmo", "Cristina Carrara", "Isaac", "Jaison Cardoso",
			"Oswaldo Baptista Duarte Filho", "Randolfo Pacciardi", "Roberto Carlos De Sousa",
			"Geraldo José Rodrigues Alckmin Filho", "Luiz Sérgio Nóbrega de Oliveira", "Mazinho",
			"Antônio Lomanto Júnior", "Bruno Cavalcanti", "Champagne", "Despota", "Heráclito de Sousa Fortes",
			"Marcelo Sereno", "Neto Richa", "Paulo César de Melo Sá", "Luiz Henrique Madndetta", "Orlando Tolentino",
			"Waldomiro Diniz", "Simone Reis Lobo De Vasconcelos", "Rodolpho Tourinho Neto", "Carlos Henrique Sobral",
			"Vander Luiz dos Santos Loubet", "Eduardo Cosentino da Cunha", "Aécio Neves Da Cunha", "Vinícius Samarane",
			"Rogério Lanza Tolentino", "Sílvio José Pereira", "Zilmar Fernandes Silveira", "Ademir Prates",
			"Ângela Moraes Guadagnin", "Antônio Ceron", "Antonio Palocci Filho", "Benedito Dias de Carvalho",
			"Benício Tavares", "Bernardo Guimarães Ribas Carli", "Breno Fischberg", "Carlos Alberto Grana",
			"Carlos Alberto Quaglia", "Sebastião Bocalom Rodrigues", "Carlos Magno Ramos", "Cássio Taniguchi",
			"Alexandre de Moraes", "Anderson Adauto Pereira", "Anita Leocádia Pereira da Costa",
			"Delúbio Soares de Castro", "Antônio Anastasia", "Antônio De Pádua De Souza Lamas",
			"Edir Pedro de Oliveira", "Edison Adrião Andrino de Oliveira", "Edison Lobão Filho", "Aspásia Camargo",
			"Ayanna Tenório Tôrres De Jesus", "Caiadinho", "Candidato Porto Velho", "Cícero Lucena Filho",
			"Cristiano De Mello Paz", "Dr. Paulinho", "Eder Moreira Brambilla", "Francisco Vieira Sampaio",
			"Garibaldi Alves", "Edvaldo Pereira De Brito", "Emerson Eloy Palmieri",
			"Expedito Gonçalves Ferreira Junior", "Heleno Augusto de Lima", "Fábio De Oliveira Branco",
			"Henrique Pizzolato", "Ildeu Araújo", "Inocêncio Gomes de Oliveira", "Irapuan Teixeira", "Iris Simões",
			"João Cláudio De Carvalho Genu", "João Luiz Correia Argôlo dos Santos", "Fernando Antônio Falcão Soares",
			"João Vaccari Neto", "José Eduardo Cavalcanti De Mendonça", "José Fuscaldi Cesílio", "José Genoíno Neto",
			"José Geraldo Riva", "Fernando Bezerra Coelho Filho", "José Linhares Ponte", "José Luiz Alves",
			"Geiza Dias Dos Santos", "José Militão Costa", "José Nobre Guimarães", "José Olímpio Silveira Moraes",
			"José Roberto Salgado", "José Viana Povoa Camelo", "Júlio César", "Júlio Lopes", "Kátia Rabello",
			"Helder Barbalho", "Lúcia Vânia Abrão Costa", "Luiz Antônio Fleury Filho", "Jacinto De Souza Lamas",
			"João Rodrigo Pinho de Almeida", "Leonardo Piciani", "Marcelo Pirilo Teixeira", "Luciano Genésio",
			"Marcos Valério Fernandes De Souza", "Marcos Pereira", "Paulo José Gouveia",
			"Paulo Roberto Galvão da Rocha", "Mauricio Quintella", "Roberto Cavalcanti Ribeiro",
			"Paulo César Hartung Gomes", "Ramon Hollerbach Cardoso", "Teotônio Brandão Vilela", "Valmir Amaral",
			"Agostinho Valente", "Alfredo Nascimento", "Amazonino Mendes", "Anderson Adauto",
			"Antonio Carlos Magalhães", "Antonio Palocci", "Ideli Salvatti", "Carlos Abicalil", "Carlos Bezerra",
			"Carlos Lupi", "Celcita Pinheiro", "Chicão Brígido", "Clésio Andrade", "Cleuber Carneiro",
			"Colbert Martins", "José Airton", "Divaldo Suruagy", "Emerson Kapaz", "Epitácio Cafeteira", "Eurides Brito",
			"Fátima Pelaes", "Fernando Henrique Cardoso", "Gilberto Carvalho", "Gilberto Miranda", "Narciso Mendes",
			"Heleno Silva", "Ibsen Pinheiro", "Jaqueline Roriz", "Jilmar Tatto", "Rubens Otoni", "João Lyra",
			"João Magalhães", "João Maia", "Joaquim Roriz", "Aníbal Teixeira", "Benedito Domingos", "Eduardo Cunha",
			"Efraim Morais", "Luiz Estevão", "Magno Malta", "Marcondes Gadelha", "Nair Xavier Lobo", "Orlando Silva",
			"Osmir Lima", "Paulo Octávio", "Paulo Renato Souza", "Paulo Rocha", "Pedro Novais", "Luiz Argôlo",
			"Renan Filho", "Ricardo Berzoini", "Luiz Inácio Lula da Silva", "Ricardo Rique", "Ronivon Santiago",
			"Rui Falcão", "Serys Slhessarenko", "Tião Viana", "Stepan Nercessian", "Wagner Rossi", "Zila Bezerra",
			"Paulo César Farias" };

	public Camara() throws IOException {
		super(true);
	}

	@Override
	public String getUrl() {
		return "http://www2.camara.leg.br/deputados/pesquisa";
	}

	@Override
	public List<Politico> getData(Document doc) throws IOException {
		// nome ainda nao foi inicializado
		for (String n : nomes) {
			navega(getUrl());

			WebElement nome = driver.findElement(By.id("nome"));
			nome.sendKeys(n);
			WebElement leg = driver.findElement(By.name("Legislatura"));
			leg.sendKeys("Qualquer Legislatura...");
			WebElement pesq = driver.findElement(By.id("Pesquisa2"));
			pesq.click();

			doc = lePagina();
			Elements sels = doc.select("div#content > ul > li > a");
			if (sels != null && sels.size() > 0) {
				doc = navega(sels.get(0).attr("href"));
				Elements es = doc.select("div.bioDetalhes > strong");
				System.out.println(es.get(0).text() + "\t" + es.get(2).text().replaceFirst(".+\\, ", ""));
				// for (Element e : es) {
				// System.out.println(e.text());
				// }
			}
		}
		// "Naturalidade: "//span
		return null;
	}
}
