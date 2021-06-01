package nts.uk.ctx.exio.dom.input.validation.condition.user;
/**
 * 値の比較条件
 */
public enum CompareValueCondition {
	/**0: 条件としない	 */
	NOT_COND(0),
	/**1: 条件値1　＜　値	 */
	COND1_LESS_VAL(1),
	/**2: 条件値1　≦　値	 */
	COND1_LESS_EQUAL_VAL(2),
	/**3: 値　＜　条件値1	 */
	VAL_LESS_COND1(3),
	/**4: 値　≦　条件値1	 */
	VAL_LESS_EQUAL_COND1(4),
	/**5: 条件値1　＜　値　かつ　　値　＜　条件値2	 */
	COND1_LESS_VAL_AND_VAL_LESS_COND2(5),
	/**6:条件値1　≦　値　かつ　　値　≦　条件値2	 */
	COND1_LESS_EQUAL_VAL_AND_VAL_LESS_EQUAL_COND2(6),
	/**7: 値　＜　条件値1　または　　条件値2　＜　値	 */
	VAL_LESS_COND1_OR_COND2_LESS_VAL(7),
	/**8: 値　≦　条件値1　または　　条件値2　≦　値	 */
	VAL_LESS_EQUAL_COND1_OR_COND2_LESS_EQUAL_VAL(8),
	/**9: 条件値1　＝　値	 */
	EQUAL(9),
	/**10: 条件値1　≠　　値	 */
	NOT_EQUAL(10);
	
	/** The value. */
	public final int value;

	private CompareValueCondition(int value) {
		this.value = value;
	}
}
