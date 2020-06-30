package nts.uk.ctx.at.request.dom.setting.workplace;

/**
 * @author loivt
 * 出退勤時刻初期表示区分
 */
public enum AtWorkAtr {
	
	/**
	 * 使用しない
	 */
	NOTDISPLAY(0),
	/**
	 * 使用する
	 * 実績から出退勤を初期表示する
	 */
	DISPLAY(1),
	
	/**
	 * 出勤は始業時刻、退勤は実績の退勤を初期表示する
	 */
	AT_START_WORK_OFF_PERFORMANCE(2),
	/**
	 * 出勤は始業時刻、退勤は終業時刻を初期表示する
	 */
	AT_START_WORK_OFF_ENDWORK(3);
	
	public int value;
	
	AtWorkAtr(int type){
		this.value = type;
	}

}
