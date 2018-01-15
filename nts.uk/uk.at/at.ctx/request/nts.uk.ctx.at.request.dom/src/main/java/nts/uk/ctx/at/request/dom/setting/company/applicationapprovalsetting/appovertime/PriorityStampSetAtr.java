package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.appovertime;

/**
 * @author loivt
 * 実績超過打刻優先設定
 */
public enum PriorityStampSetAtr {
	/**
	 * 0: 1日の範囲内であっても実績に退勤打刻があれば退勤時刻でチェックする
	 */
	TIMETOLEAVEWORK(0),
	/**
	 * 1: 1日の範囲内であれば現在の時刻でチェックする
	 */
	CURRENTTIME(1);
	
	public final int value;
	
	PriorityStampSetAtr(int value){
		this.value = value;
	}
	

}
