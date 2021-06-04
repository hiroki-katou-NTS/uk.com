package nts.uk.ctx.exio.dom.exo.dataformat.init;

public enum FixedValueOperationSymbol {
	//+
	PLUS(0, "Enum_FixedValueOperationSymbol_PLUS"),
	//-
	MINUS(1, "Enum_FixedValueOperationSymbol_MINUS");
	public final int value;

	/** The name id. */
	public final String nameId;

	private FixedValueOperationSymbol(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
}
