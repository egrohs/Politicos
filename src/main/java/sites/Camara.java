package sites;

import java.io.IOException;
import java.util.List;

import modelo.Politico;

public class Camara extends Site {
	public Camara() throws IOException {
		super(true);
	}

	@Override
	public String getUrl() {
		return "http://www2.camara.leg.br/deputados/pesquisa";
	}

	@Override
	public List<Politico> getData() throws IOException {
		return null;
	}
}
