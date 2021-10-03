package celepar;

public enum CodInfoCadEnum {
	FLORESTA(1,"Floresta"),
	SERES_VIVOS(2,"Seres vivos"),
	ARMA(3,"Arma"),
	AREA(4,"Area"),
	GR_AP(5,"Grupo residuo/Agente poluente"),
	AREA_SM(6,"Area/Substancia Mineral"),
	CLASSE_CARGA(7,"Classe Carga"),
	MATERIAL(8,"Material"),
	QUANTIDADE(9,"Quantidade"),
	NENHUM(10,"Nenhum"),
	ITEM_11(11,"ITEM_11"),
	ITEM_12(12,"ITEM_12"),
	ITEM_13(13,"ITEM_13"),
	ITEM_14(14,"ITEM_14");
	
	
	
	private final Integer id;
	private final String descr;
	
	CodInfoCadEnum(Integer id,String descr){
		this.id = id;
		this.descr = descr;
	}

	public Integer getId() {
		return id;
	}

	public String getDescr() {
		return descr;
	}
	
	public String toString() {
		return descr;
		
	}
	
	public Integer retornaMaxSizeText() {
		Integer max = 0;
		
		
		
		return max;
	}
	
	
	public static CodInfoCadEnum getEnumerator(Integer id) {
		if (id != null) {
			for (CodInfoCadEnum exg : values()) {
				if (exg.getId().intValue() == id.intValue()) {
					return exg;
				}
			}
		}
		return null;
	}

}
