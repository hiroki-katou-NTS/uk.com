package nts.uk.ctx.exio.dom.exo.outcnddetail;

/**
 * 
 * 
 * 条件記号
 *
 */

public enum ConditionSymbol {

	// 含む
	CONTAIN(0, "Enum_ConditionSymbol_CONTAIN"),

	// 範囲内
	BETWEEN(1, "Enum_ConditionSymbol_BETWEEN"),

	// 同じ
	IS(2, "Enum_ConditionSymbol_IS"),
	
	// 同じでない
	IS_NOT(3, "Enum_ConditionSymbol_IS_NOT"),
	
	// より大きい
	GREATER(4, "Enum_ConditionSymbol_GREATER"),
	
	//より小さい
	LESS(5, "Enum_ConditionSymbol_LESS"),
	
	//以上
	GREATER_OR_EQUAL(6, "Enum_ConditionSymbol_GREATER_OR_EQUAL"),
	
	//以下
	LESS_OR_EQUAL(7, "Enum_ConditionSymbol_LESS_OR_EQUAL"),
	
	//同じ(複数)
	IN(8, "Enum_ConditionSymbol_IN"),

	//同じでない(複数)
	NOT_IN(9, "Enum_ConditionSymbol_NOT_IN");
	

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	private ConditionSymbol(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
}
