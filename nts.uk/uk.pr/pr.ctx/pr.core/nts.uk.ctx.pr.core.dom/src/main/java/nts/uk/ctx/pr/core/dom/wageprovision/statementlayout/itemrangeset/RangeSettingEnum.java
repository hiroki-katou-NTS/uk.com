package nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.itemrangeset;

/**
 * 範囲設定区分
 */
public enum RangeSettingEnum {
	NOT_USE(0, "Enum_Range_Setting_NOT_USE"),
	USE(1, "Enum_Range_Setting_USE"),;
	
	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	private RangeSettingEnum(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
}
