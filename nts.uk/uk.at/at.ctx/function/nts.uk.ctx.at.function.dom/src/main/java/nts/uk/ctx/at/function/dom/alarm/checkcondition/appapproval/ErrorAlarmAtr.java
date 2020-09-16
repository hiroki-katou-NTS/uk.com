package nts.uk.ctx.at.function.dom.alarm.checkcondition.appapproval;

/**
 * ER/AL区分
 */
public enum ErrorAlarmAtr {

	ER(0, "エラー"),
	AL(1, "アラーム");
	
	public int value;
	
	public String name;
	
	private ErrorAlarmAtr(int value, String name) {
		this.value = value;
		this.name = name;
	}
}
