package nts.uk.ctx.at.record.dom.workrecord.erroralarm.mastercheck;

/**
 * ER/AL区分
 */
public enum ErrorAlarmAtr {

	ER(0, "エラー"),
	AL(1, "アラーム"),
	OTH(2, "その他");
	
	public int value;
	
	public String name;
	
	private ErrorAlarmAtr(int value, String name) {
		this.value = value;
		this.name = name;
	}
}
