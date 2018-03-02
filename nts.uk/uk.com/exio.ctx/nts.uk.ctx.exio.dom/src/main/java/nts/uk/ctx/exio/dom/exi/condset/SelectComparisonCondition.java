package nts.uk.ctx.exio.dom.exi.condset;

public enum SelectComparisonCondition {
	NO_CONDITION(0, "条件としない"),
	COND1_LESS_THAN_VAL(1,"条件値1　＜　値"),
	COND1_LESS_THAN_OR_EQUAL_VAL(2,"条件値1　≦　値"),
	VAL_LESS_THAN_COND1(3,"値　＜　条件値1"),
	VAL_LESS_THAN_OR_EQUAL_COND1(4,"値　≦　条件値1"),
	VAL_MORE_COND1_AND_LESS_COND2(5,"条件値1　＜　値　かつ　　値　＜　条件値2"),
	VAL_MORE_OR_EQUAL_COND1_AND_LESS_COND2(6,"条件値1　≦　値　かつ　　値　≦　条件値2"),
	VAL_MORE_COND1_OR_LESS_COND2(7,"値　＜　条件値1　または　　条件値2　＜　値"),
	VAL_MORE_OR_EQUAL__COND1_OR_LESS_COND2(8,"値　≦　条件値1　または　　条件値2　≦　値"),
	COND1_EQUAL_VAL(9,"条件値1　＝　値"),
	COND1_NOT_EQUAL_VAL(10,"条件値1　≠　　値")
	;
	
	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	private SelectComparisonCondition(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
}
