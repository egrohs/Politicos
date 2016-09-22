package modelo;

import java.io.IOException;
import java.util.List;

public interface Site {
	public List<Politico> getData() throws IOException;
}
