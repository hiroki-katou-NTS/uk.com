package nts.uk.ctx.pr.core.dom.wageprovision.wagetable;

/**
 * 要素設定
 */
public enum ElementSetting {

	// 一次元
	ONE_DIMENSION(0, "Enum_Element_Setting_One_Dimension"), 
	// 二次元
	TWO_DIMENSION(1, "Enum_Element_Setting_Two_Dimension"), 
	// 三次元
	THREE_DIMENSION(2, "Enum_Element_Setting_Three_Dimension"), 
	// 資格
	QUALIFICATION(3, "Enum_Element_Setting_Qualification"), 
	// 精皆勤
	FINE_WORK(4, "Enum_Element_Setting_Fine_Work");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	private ElementSetting(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
	
}
