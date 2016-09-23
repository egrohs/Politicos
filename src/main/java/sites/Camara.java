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
			"Andr� Gustavo Pereira Corr�a Da Silva", "Francisco Oswaldo Neves Dornelles", "Jos� Camilo Zito dos Santos",
			"S�rgio Sveiter", "Luiz Fernando De Souza", "G. Alves", "Geraldo Julio de Mello Filho", "J. Agripino",
			"Severino Branquinho", "Cleiber Santana", "Dudu Rosalva", "Abelardo Leopoldo", "Cacau Gomes", "Dinha",
			"Jarbas Vasconcelos Filho", "Jorge Felipe", "Jorge Picciani", "M�rcio Della Valle Biolchi", "M�rio Kertesz",
			"Nilson Bonome", "Pedro Godinho", "S�rgio Paulo Perrucci De Aquino", "Ademar Delgado", "Antonio Donato",
			"Carmem Gandarella", "Donizete Braga", "Fernando Haddad", "Francisco Daniel Celeguin De Moraes",
			"Humberto Carballal", "Jacques Pena", "Jeferson Andrade", "Jo�o Paulo Rillo", "Jorge Miguel Samek",
			"Jussara M�rcia", "Luis Tavares", "Luiz Marinho", "Nelson Vicente Portela Pelegrino", "Paulo Garcia",
			"Rodrigo Neves", "Ronaldo Miro Z�lke", "Jos� Wellington Barroso de Ara�jo Dias", "V�nia Galv�o",
			"Ant�nio Carlos Magalh�es Neto", "Jefferson Morais", "Jos� Carlos Aleluia Costa", "Leonardo Prates",
			"Milton Leite", "Rodney Miranda", "Agnaldo", "Alberto Pereira Castro", "Diogo Paz Bier", "Fischinha",
			"Paulinho Fiorella", "Heitor Schulk", "Jos� Pavan Junior", "Aidan Ant�nio Ravin", "Atila Jacomussi",
			"C�sar Souza Junior", "Chiquinho Do Zaira", "Diversos Vereadores", "Geraldo Junior", "Leonice Da Paz",
			"Marcelo Esvim", "Marquinhos Bola", "Maur�cio Bacelar", "Pedro Serafin", "Firmino Filho",
			"Sonia Francine Gaspar Marmo", "Cristina Carrara", "Isaac", "Jaison Cardoso",
			"Oswaldo Baptista Duarte Filho", "Randolfo Pacciardi", "Roberto Carlos De Sousa",
			"Geraldo Jos� Rodrigues Alckmin Filho", "Luiz S�rgio N�brega de Oliveira", "Mazinho",
			"Ant�nio Lomanto J�nior", "Bruno Cavalcanti", "Champagne", "Despota", "Her�clito de Sousa Fortes",
			"Marcelo Sereno", "Neto Richa", "Paulo C�sar de Melo S�", "Luiz Henrique Madndetta", "Orlando Tolentino",
			"Waldomiro Diniz", "Simone Reis Lobo De Vasconcelos", "Rodolpho Tourinho Neto", "Carlos Henrique Sobral",
			"Vander Luiz dos Santos Loubet", "Eduardo Cosentino da Cunha", "A�cio Neves Da Cunha", "Vin�cius Samarane",
			"Rog�rio Lanza Tolentino", "S�lvio Jos� Pereira", "Zilmar Fernandes Silveira", "Ademir Prates",
			"�ngela Moraes Guadagnin", "Ant�nio Ceron", "Antonio Palocci Filho", "Benedito Dias de Carvalho",
			"Ben�cio Tavares", "Bernardo Guimar�es Ribas Carli", "Breno Fischberg", "Carlos Alberto Grana",
			"Carlos Alberto Quaglia", "Sebasti�o Bocalom Rodrigues", "Carlos Magno Ramos", "C�ssio Taniguchi",
			"Alexandre de Moraes", "Anderson Adauto Pereira", "Anita Leoc�dia Pereira da Costa",
			"Del�bio Soares de Castro", "Ant�nio Anastasia", "Ant�nio De P�dua De Souza Lamas",
			"Edir Pedro de Oliveira", "Edison Adri�o Andrino de Oliveira", "Edison Lob�o Filho", "Asp�sia Camargo",
			"Ayanna Ten�rio T�rres De Jesus", "Caiadinho", "Candidato Porto Velho", "C�cero Lucena Filho",
			"Cristiano De Mello Paz", "Dr. Paulinho", "Eder Moreira Brambilla", "Francisco Vieira Sampaio",
			"Garibaldi Alves", "Edvaldo Pereira De Brito", "Emerson Eloy Palmieri",
			"Expedito Gon�alves Ferreira Junior", "Heleno Augusto de Lima", "F�bio De Oliveira Branco",
			"Henrique Pizzolato", "Ildeu Ara�jo", "Inoc�ncio Gomes de Oliveira", "Irapuan Teixeira", "Iris Sim�es",
			"Jo�o Cl�udio De Carvalho Genu", "Jo�o Luiz Correia Arg�lo dos Santos", "Fernando Ant�nio Falc�o Soares",
			"Jo�o Vaccari Neto", "Jos� Eduardo Cavalcanti De Mendon�a", "Jos� Fuscaldi Ces�lio", "Jos� Geno�no Neto",
			"Jos� Geraldo Riva", "Fernando Bezerra Coelho Filho", "Jos� Linhares Ponte", "Jos� Luiz Alves",
			"Geiza Dias Dos Santos", "Jos� Milit�o Costa", "Jos� Nobre Guimar�es", "Jos� Ol�mpio Silveira Moraes",
			"Jos� Roberto Salgado", "Jos� Viana Povoa Camelo", "J�lio C�sar", "J�lio Lopes", "K�tia Rabello",
			"Helder Barbalho", "L�cia V�nia Abr�o Costa", "Luiz Ant�nio Fleury Filho", "Jacinto De Souza Lamas",
			"Jo�o Rodrigo Pinho de Almeida", "Leonardo Piciani", "Marcelo Pirilo Teixeira", "Luciano Gen�sio",
			"Marcos Val�rio Fernandes De Souza", "Marcos Pereira", "Paulo Jos� Gouveia",
			"Paulo Roberto Galv�o da Rocha", "Mauricio Quintella", "Roberto Cavalcanti Ribeiro",
			"Paulo C�sar Hartung Gomes", "Ramon Hollerbach Cardoso", "Teot�nio Brand�o Vilela", "Valmir Amaral",
			"Agostinho Valente", "Alfredo Nascimento", "Amazonino Mendes", "Anderson Adauto",
			"Antonio Carlos Magalh�es", "Antonio Palocci", "Ideli Salvatti", "Carlos Abicalil", "Carlos Bezerra",
			"Carlos Lupi", "Celcita Pinheiro", "Chic�o Br�gido", "Cl�sio Andrade", "Cleuber Carneiro",
			"Colbert Martins", "Jos� Airton", "Divaldo Suruagy", "Emerson Kapaz", "Epit�cio Cafeteira", "Eurides Brito",
			"F�tima Pelaes", "Fernando Henrique Cardoso", "Gilberto Carvalho", "Gilberto Miranda", "Narciso Mendes",
			"Heleno Silva", "Ibsen Pinheiro", "Jaqueline Roriz", "Jilmar Tatto", "Rubens Otoni", "Jo�o Lyra",
			"Jo�o Magalh�es", "Jo�o Maia", "Joaquim Roriz", "An�bal Teixeira", "Benedito Domingos", "Eduardo Cunha",
			"Efraim Morais", "Luiz Estev�o", "Magno Malta", "Marcondes Gadelha", "Nair Xavier Lobo", "Orlando Silva",
			"Osmir Lima", "Paulo Oct�vio", "Paulo Renato Souza", "Paulo Rocha", "Pedro Novais", "Luiz Arg�lo",
			"Renan Filho", "Ricardo Berzoini", "Luiz In�cio Lula da Silva", "Ricardo Rique", "Ronivon Santiago",
			"Rui Falc�o", "Serys Slhessarenko", "Ti�o Viana", "Stepan Nercessian", "Wagner Rossi", "Zila Bezerra",
			"Paulo C�sar Farias" };

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
