package smilelinked.cooperationoutput;

/**
 * 連動月の設定区分
 */
public enum LinkedMonthSettingClassification {

	/** 当月の情報を連携する */
	CURRENT_MONTH(0),

	/** 1ヶ月前の情報を連携する */
	ONE_MONTH_AGO(1);
	
	public int value;
	
	private LinkedMonthSettingClassification (int value) {
		this.value = value;
	}
}
