package nts.uk.ctx.exio.dom.exi.condset;

/**
 * 文字の比較条件
 */
public enum CompareStringCondition {
	/**0: 条件としない	 */
	NOT_COND(0),
	/**1: 条件値　＝　値	 */
	COND1_EQUAL_VAL(1),
	/**2: 条件値　≠　　値	 */
	COND1_NOT_EQUAL_VAL(2);
	
	/** The value. */
	public final int value;
	
	private CompareStringCondition(int value) {
		this.value = value;
	}
}
