package celepar;

public class TipoOcorrDetalhamento {
	
	private Integer codGrupoOcor;
	private Integer codOcorrencia;
	private Integer ordemExibicao;
	private String descrOcorrencia;
	private Integer codCadInf;
	private Integer artigo;
	private String itemParagArtigo;
	private String descEnq;
	private String descrOcorrVis;
	private String ativo;
	
	public TipoOcorrDetalhamento(String string) {
		descrOcorrencia = string;
	}
	
	public Object makeObj() {
		return new Object() {
			public String toString() {
				return descrOcorrVis;
			}

		};
	}

	public Integer getCodGrupoOcor() {
		return codGrupoOcor;
	}

	public void setCodGrupoOcor(Integer codGrupoOcor) {
		this.codGrupoOcor = codGrupoOcor;
	}

	public Integer getCodOcorrencia() {
		return codOcorrencia;
	}

	public void setCodOcorrencia(Integer codOcorrencia) {
		this.codOcorrencia = codOcorrencia;
	}

	public Integer getOrdemExibicao() {
		return ordemExibicao;
	}

	public void setOrdemExibicao(Integer ordemExibicao) {
		this.ordemExibicao = ordemExibicao;
	}

	public String getDescrOcorrencia() {
		return descrOcorrencia;
	}

	public void setDescrOcorrencia(String descrOcorrencia) {
		this.descrOcorrencia = descrOcorrencia;
	}

	public Integer getCodCadInf() {
		return codCadInf;
	}

	public void setCodCadInf(Integer codCadInf) {
		this.codCadInf = codCadInf;
	}

	public Integer getArtigo() {
		return artigo;
	}

	public void setArtigo(Integer artigo) {
		this.artigo = artigo;
	}

	public String getItemParagArtigo() {
		return itemParagArtigo;
	}

	public void setItemParagArtigo(String itemParagArtigo) {
		this.itemParagArtigo = itemParagArtigo;
	}

	public String getDescEnq() {
		return descEnq;
	}

	public void setDescEnq(String descEnq) {
		this.descEnq = descEnq;
	}

	public String getDescrOcorrVis() {
		return descrOcorrVis;
	}

	public void setDescrOcorrVis(String descrOcorrVis) {
		this.descrOcorrVis = descrOcorrVis;
	}

	public String getAtivo() {
		return ativo;
	}

	public void setAtivo(String ativo) {
		this.ativo = ativo;
	}


}
