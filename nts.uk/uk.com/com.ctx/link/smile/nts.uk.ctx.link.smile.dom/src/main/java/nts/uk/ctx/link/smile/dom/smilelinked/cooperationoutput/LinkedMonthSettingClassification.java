package nts.uk.ctx.link.smile.dom.smilelinked.cooperationoutput;

/**
 * 連動月の設定区分
 */
public enum LinkedMonthSettingClassification {

	/** 当月の情報を連携する */
	CURRENT_MONTH(0, "当月の情報を連携する"),

	/** 1ヶ月前の情報を連携する */
	ONE_MONTH_AGO(1, "1ヶ月前の情報を連携する");

	public int value;
	public String nameId;

	private LinkedMonthSettingClassification (int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}

	public static LinkedMonthSettingClassification valueOf(Integer value) {
		return value == 0 ? CURRENT_MONTH : ONE_MONTH_AGO;
	}
}
