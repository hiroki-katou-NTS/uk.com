package nts.uk.ctx.exio.dom.exo.outitem;

/*
 * 演算符号
 */
public enum OperationSymbol {
	/*
	 * &
	 */
	AND(0, "Enum_OperationSymbol_AND"),
	/*
	 * +
	 */
	PLUS(1, "Enum_OperationSymbol_PLUS"),

	/*
	 * -
	 */
	MINUS(2, "Enum_OperationSymbol_MINUS");

	/* The value. */
	public final int value;
	/* The name id. */
	public final String nameId;

	private OperationSymbol(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
}
