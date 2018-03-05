package nts.uk.ctx.exio.dom.exi.condset;
/**
 * 
 * @author DatLH 比較条件選択
 *
 */
public enum SelectComparisonCondition {
	NOT_COND(0, "Enum_SelectComparisonCondition_DO_NOT_COND"),
	COND1_LESS_VAL(1, "Enum_SelectComparisonCondition_COND1_LESS_VAL"),
	COND1_LESS_EQUAL_VAL(2, "Enum_SelectComparisonCondition_COND1_LESS_EQUAL_VAL"),
	VAL_LESS_COND1(3, "Enum_SelectComparisonCondition_VAL_LESS_COND1"),
	VAL_LESS_EQUAL_COND1(4, "Enum_SelectComparisonCondition_VAL_LESS_EQUAL_COND1"),
	COND1_LESS_VAL_AND_VAL_LESS_COND2(5, "Enum_SelectComparisonCondition_COND1_LESS_VAL_AND_VAL_LESS_COND2"),
	COND1_LESS_EQUAL_VAL_AND_VAL_LESS_EQUAL_COND2(6, "Enum_SelectComparisonCondition_COND1_LESS_EQUAL_VAL_AND_VAL_LESS_EQUAL_COND2"),
	VAL_LESS_COND1_OR_COND2_LESS_VAL(7, "Enum_SelectComparisonCondition_VAL_LESS_COND1_OR_COND2_LESS_VAL"),
	VAL_LESS_EQUAL_COND1_OR_COND2_LESS_EQUAL_VAL(8, "Enum_SelectComparisonCondition_VAL_LESS_EQUAL_COND1_OR_COND2_LESS_EQUAL_VAL"),
	COND1_EQUAL_VAL(9, "Enum_SelectComparisonCondition_COND1_EQUAL_VAL"),
	COND1_NOT_EQUAL_VAL(10, "Enum_SelectComparisonCondition_COND1_NOT_EQUAL_VAL");
	
	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	private SelectComparisonCondition(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
}
