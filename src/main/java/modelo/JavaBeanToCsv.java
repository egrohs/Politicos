package modelo;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.bean.BeanToCsv;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;

public class JavaBeanToCsv {
	public static void toCSV(Collection<Politico> politicos) {
		CSVWriter csvWriter = null;
		try {
			// Create CSVWriter for writing to Employee.csv
			csvWriter = new CSVWriter(new OutputStreamWriter(new FileOutputStream("saida.csv"), "UTF-8"));
			BeanToCsv bc = new BeanToCsv();
			// mapping of columns with their positions
			ColumnPositionMappingStrategy mappingStrategy = new ColumnPositionMappingStrategy();
			// Set mappingStrategy type to Employee Type
			mappingStrategy.setType(Politico.class);
			// String nome, UF, formacao;
			// Set<String> urls, codinomes, partidos, cargos, profissoes;
			// Set<Integer> legislaturas;
			String[] columns = new String[] { "id", "nome", "UF", "codinomes", "partidos", "profissoes", "legislaturas",
					"urls" };
			// Setting the colums for mappingStrategy
			mappingStrategy.setColumnMapping(columns);
			// Writing empList to csv file
			List<Politico> l = new ArrayList<Politico>();
			l.addAll(politicos);
			bc.write(mappingStrategy, csvWriter, l);
			System.out.println("CSV File written successfully!!!");
		} catch (Exception ee) {
			ee.printStackTrace();
		} finally {
			try {
				// closing the writer
				csvWriter.close();
			} catch (Exception ee) {
				ee.printStackTrace();
			}
		}
	}

	public static void main(String[] args) throws Exception {
		// new Camara();
		// String url =
		// "http://www2.camara.leg.br/deputados/pesquisa/layouts_deputados_biografia?pk=193117&tipo=0";
		// System.out.println(url.split("pk=")[1].split("&")[0]);
		read();
	}

	public static List<Politico> read() throws Exception {
		CsvToBean csv = new CsvToBean();
		CSVReader csvReader = new CSVReader(new InputStreamReader(new FileInputStream("saida.csv"), "UTF-8"));
		// Set column mapping strategy
		List<Politico> list = csv.parse(setColumMapping(), csvReader);
		System.out.println(list.size() + " politicos lidos.");
		for (Object object : list) {
			Politico employee = (Politico) object;
			//System.out.println(employee);
		}
		return list;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static ColumnPositionMappingStrategy setColumMapping() {
		ColumnPositionMappingStrategy strategy = new ColumnPositionMappingStrategy();
		strategy.setType(Politico.class);
		// id,nome,UF,codinomes,partidos,profissoes,legislaturas,urls
		String[] columns = new String[] { "id", "nome", "UF", "codinomes", "partidos", "profissoes", "legislaturas",
				"urls" };
		strategy.setColumnMapping(columns);
		return strategy;
	}
}