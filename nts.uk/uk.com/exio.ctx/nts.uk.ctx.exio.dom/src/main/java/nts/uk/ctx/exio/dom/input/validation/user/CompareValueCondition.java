package nts.uk.ctx.exio.dom.input.validation.user;
/**
 * 値の比較条件
 */
public enum CompareValueCondition {
	/**0: 条件としない	 */
	NOT_COND(0),
	/**1: 条件値1　＜　値	 */
	COND1_LESS_TARGET(1),
	/**2: 条件値1　≦　値	 */
	COND1_LESS_EQUAL_TARGET(2),
	/**3: 値　＜　条件値1	 */
	TARGET_LESS_COND1(3),
	/**4: 値　≦　条件値1	 */
	TARGET_LESS_EQUAL_COND1(4),
	/**5: 条件値の間（境界値を含まない）
	 *       条件値1　＜　値　かつ　　値　＜　条件値2	 */
	TARGET_BETWEEN_OPEN_COND(5),
	/**6: 条件値の間（境界値を含む）
	 *       条件値1　≦　値　かつ　　値　≦　条件値2	 */
	TARGET_BETWEEN_CLOSE_COND(6),
	/**7: 条件値の外（境界値を含まない）
	 * 	  値　＜　条件値1　または　　条件値2　＜　値	 */
	TARGET_OUTSIDE_OPEN_COND(7),
	/**8: 条件値の外（境界値を含む）
	 * 　　値　≦　条件値1　または　　条件値2　≦　値	 */
	TARGET_OUTSIDE_CLOSED_COND(8),
	/**9: 条件値1　＝　値	 */
	TARGET_EQUAL_COND1(9),
	/**10: 条件値1　≠　　値	 */
	TARGET_NOT_EQUAL_COND1(10);
	
	/** The value. */
	public final int value;

	private CompareValueCondition(int value) {
		this.value = value;
	}
}
