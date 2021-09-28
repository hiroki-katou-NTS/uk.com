package nts.uk.cnv.core.dom.conversiontable;

public enum ConversionCodeType {
	INSERT(""),
	UPDATE("dest");

	private String alias;

	private ConversionCodeType(String alias) {
		this.alias = alias;
	}

	public String getTagetAlias() {
		return alias;
	}
}
