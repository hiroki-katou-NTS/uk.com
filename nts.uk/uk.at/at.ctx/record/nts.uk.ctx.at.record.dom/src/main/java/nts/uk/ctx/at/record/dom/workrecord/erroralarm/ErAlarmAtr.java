package nts.uk.ctx.at.record.dom.workrecord.erroralarm;

public enum ErAlarmAtr {
	ERROR(0, "エラー"),
	ALARM(1, "アラーム"),
	OTHER(2, "その他"),;
	
	public final int value;
	public final String description;
	
	ErAlarmAtr(int val, String des){
		this.value = val;
		this.description = des;
	}
}
