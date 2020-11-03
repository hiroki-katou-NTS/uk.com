package nts.uk.ctx.sys.assist.dom.storage;

/**
 * 調査用設定区分
 */
public enum SurveySettingCategory {
	
	/**
	 * 調査用保存しない
	 */
	DONT_SAVE_FOR_RESEARCH(0, "Enum_SurveySettingCategory_DONT_SAVE_FOR_RESEARCH"),
	
	/**
	 * 調査用保存する
	 */
	SAVE_FOR_RESEARCH(1, "Enum_SurveySettingCategory_SAVE_FOR_RESEARCH");
	
	public final int value;
	public final String nameId;
	
	private SurveySettingCategory(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
}
