package modelo;

public class Politico {
	// partidoAtual, cargos, excelenciasorg, veja, politicosorg, wiki,
	// curriculo, camaraFoto, outros
	private String camaraPk, senadoId, nome, uf, partidoAtual, codinomes, outrosPartidos, profissoes, cargo,
			legislaturas, foto, urlCamara;
	// Set<String> urls, codinomes, partidos, cargos, profissoes, legislaturas;
	// Set<Integer> legislaturas;

	public Politico() {
	}

	// TODO fazer validacao semantica dos dados sendo criados.
	public Politico(String camaraPk, String senadoId, String nome, String codinome, String uf, String partidoAtual,
			String outrosPartidos, String profissoes, String cargo, String legislaturas, String foto,
			String urlCamara) {
		this.camaraPk = camaraPk;
		this.senadoId = senadoId;
		this.nome = nome;
		this.codinomes = codinome;
		this.uf = uf;
		this.partidoAtual = partidoAtual;
		this.outrosPartidos = outrosPartidos;
		this.profissoes = profissoes;
		this.legislaturas = legislaturas;
		this.foto = foto;
		this.urlCamara = urlCamara;
	}

	@Override
	public String toString() {
		return senadoId + "\t" + nome + "\t" + uf + "\t" + outrosPartidos + "\t" + urlCamara;
	}

	public String getSenadoId() {
		return senadoId;
	}

	public void setSenadoId(String id) {
		this.senadoId = id;
	}

	public String getCamaraPk() {
		return camaraPk;
	}

	public void setCamaraPk(String camaraPk) {
		this.camaraPk = camaraPk;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String estado) {
		this.uf = estado;
	}

	public String getUrls() {
		return urlCamara;
	}

	public void setUrls(String urls) {
		this.urlCamara = urls;
	}

	public String getCodinomes() {
		return codinomes;
	}

	public void setCodinomes(String codinomes) {
		this.codinomes = codinomes;
	}

	public String getOutrosPartidos() {
		return outrosPartidos;
	}

	public void setOutrosPartidos(String partidos) {
		this.outrosPartidos = partidos;
	}

	public String getProfissoes() {
		return profissoes;
	}

	public void setProfissoes(String profissoes) {
		this.profissoes = profissoes;
	}

	public String getLegislaturas() {
		return legislaturas;
	}

	public void setLegislaturas(String legislaturas) {
		this.legislaturas = legislaturas;
	}
	
	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}
}