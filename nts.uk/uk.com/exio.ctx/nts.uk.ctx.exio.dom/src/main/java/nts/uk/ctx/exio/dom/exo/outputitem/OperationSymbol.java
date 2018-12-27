package nts.uk.ctx.exio.dom.exo.outputitem;

/*
 * 演算符号
 */
public enum OperationSymbol {
	/*
	 * &
	 */
	AND(0),
	/*
	 * +
	 */
	PLUS(1),

	/*
	 * -
	 */
	MINUS(2);

	/* The value. */
	public final int value;

	private OperationSymbol(int value) {
		this.value = value;
	}
}
