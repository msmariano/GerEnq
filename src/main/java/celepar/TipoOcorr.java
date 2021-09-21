package celepar;

public class TipoOcorr {
	
	
	

	public TipoOcorr(String string) {
		this.descrOcorrencia = string;
	}


	public String getDescrOcorrencia() {
		return descrOcorrencia;
	}

	public void setDescrOcorrencia(String descrOcorrencia) {
		this.descrOcorrencia = descrOcorrencia;
	}


	private Integer codGrupoOcor;
	private String descrOcorrencia;

	public Object makeObj() {
		return new Object() {
			public String toString() {
				return descrOcorrencia;
			}

		};
	}

	public Integer getCodGrupoOcor() {
		return codGrupoOcor;
	}


	public void setCodGrupoOcor(Integer codGrupoOcor) {
		this.codGrupoOcor = codGrupoOcor;
	}
	
}
