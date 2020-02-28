package nts.uk.ctx.pr.core.dom.wageprovision.wagetable;

/**
 * 要素種類
 */
public enum ElementType {

	// 雇用
	EMPLOYMENT("M001", "Enum_Element_Type_M001"), 
	// 部門
	DEPARTMENT("M002", "Enum_Element_Type_M002"), 
	// 分類
	CLASSIFICATION("M003", "Enum_Element_Type_M003"), 
	// 職位
	JOB_TITLE("M004", "Enum_Element_Type_M004"), 
	// 給与分類
	SALARY_CLASSIFICATION("M005", "Enum_Element_Type_M005"), 
	// 資格
	QUALIFICATION("M006", "Enum_Element_Type_M006"), 
	// 精皆勤レベル
	FINE_WORK("M007", "Enum_Element_Type_M007"), 
	// 年齢
	AGE("N001", "Enum_Element_Type_N001"), 
	// 勤続年数
	SERVICE_YEAR("N002", "Enum_Element_Type_N002"), 
	// 家族人数
	FAMILY_MEMBER("N003", "Enum_Element_Type_N003");

	/** The value. */
	public final String value;

	/** The name id. */
	public final String nameId;

	private ElementType(String value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}

}
