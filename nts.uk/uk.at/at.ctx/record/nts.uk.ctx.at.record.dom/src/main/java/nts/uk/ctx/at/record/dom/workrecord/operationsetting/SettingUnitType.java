package nts.uk.ctx.at.record.dom.workrecord.operationsetting;
/**
 * フォーマット種類
 * @author BinhHX-3si
 *
 */
public enum SettingUnitType {
	/**
	 * 権限
	 */
	AUTHORITY(0), 
	/**
	 * 勤務種別
	 */
	BUSINESS_TYPE(1);

	public final int value;

	private SettingUnitType(int value) {
		this.value = value;
	}
}
