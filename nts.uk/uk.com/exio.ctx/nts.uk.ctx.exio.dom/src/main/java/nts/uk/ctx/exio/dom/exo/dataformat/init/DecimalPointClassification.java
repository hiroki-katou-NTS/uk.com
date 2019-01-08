package nts.uk.ctx.exio.dom.exo.dataformat.init;

public enum DecimalPointClassification {
	
	//小数点を出力しない
	NOT_OUT_PUT(0, "Enum_DecimalPointClassification_NOT_OUT_PUT"),
	//小数点を出力する
	OUT_PUT(1, "Enum_DecimalPointClassification_OUT_PUT");
		
	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	private DecimalPointClassification(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
}
