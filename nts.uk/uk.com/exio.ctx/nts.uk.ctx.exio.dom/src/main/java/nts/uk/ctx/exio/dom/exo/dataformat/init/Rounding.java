package nts.uk.ctx.exio.dom.exo.dataformat.init;

public enum Rounding {
	
	//四捨五入
	DOWN_4_UP_5(0, "Enum_Rounding_DOWN_4_UP_5"),
	//切り捨て
	TRUNCATION(1, "Enum_Rounding_TRUNCATION"),
	//切り上げ
	ROUND_UP(2, "Enum_Rounding_ROUND_UP");
	
	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	private Rounding(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
}
