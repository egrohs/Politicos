package modelo;

public class Politico {
	String id, nome, UF, codinomes, partidos, profissoes, legislaturas, urls;
	// Set<String> urls, codinomes, partidos, cargos, profissoes, legislaturas;
	// Set<Integer> legislaturas;

	public Politico() {
	}

	// public Politico(String nome, String uf, String codinome, String part,
	// String profs, String legs, String url) {
	// this.nome = nome;
	// UF = uf;
	// codinomes = new HashSet<String>();
	// codinomes.add(codinome);
	// partidos = new HashSet<String>();
	// // partidos.addAll(Arrays.asList(parts));
	// partidos.add(part);
	// profissoes = new HashSet<String>();
	// profissoes.add(profs);
	// legislaturas = new HashSet<String>();
	// legislaturas.add(legs);
	// urls = new HashSet<String>();
	// urls.add(url);
	// }
	public Politico(String id, String nome, String uf, String codinome, String part, String profs, String legs,
			String url) {
		this.id = id;
		this.nome = nome;
		UF = uf;
		codinomes = codinome;
		partidos = part;
		profissoes = profs;
		legislaturas = legs;
		urls = url;
	}

	@Override
	public String toString() {
		return id + "\t" + nome + "\t" + UF + "\t" + partidos + "\t" + urls;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getUF() {
		return UF;
	}

	public void setUF(String estado) {
		this.UF = estado;
	}

	public String getUrls() {
		return urls;
	}

	public void setUrls(String urls) {
		this.urls = urls;
	}

	public String getCodinomes() {
		return codinomes;
	}

	public void setCodinomes(String codinomes) {
		this.codinomes = codinomes;
	}

	public String getPartidos() {
		return partidos;
	}

	public void setPartidos(String partidos) {
		this.partidos = partidos;
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
}