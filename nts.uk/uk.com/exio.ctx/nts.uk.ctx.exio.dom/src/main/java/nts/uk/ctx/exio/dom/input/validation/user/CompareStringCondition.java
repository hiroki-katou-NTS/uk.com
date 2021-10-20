package nts.uk.ctx.exio.dom.input.validation.user;

/**
 * 文字の比較条件
 */
public enum CompareStringCondition {
	/**0: 条件としない	 */
	NOT_COND(0),
	/**1: 条件値　＝　値	 */
	EQUAL(1),
	/**2: 条件値　≠　　値	 */
	NOT_EQUAL(2);
	
	/** The value. */
	public final int value;
	
	private CompareStringCondition(int value) {
		this.value = value;
	}
}
