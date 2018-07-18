package nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums;

public enum TypeCheckWorkRecordMultipleMonth {
		
	TIME(0,"時間"),
	
	TIMES(1,"回数"),
	
	AMOUNT(2,"金額"),
	
	TIME_AVERAGE(3,"連続時間"),
	
	TIMES_AVERAGE(4,"連続日数"),
	
	AMOUNT_AVERAGE(5,"連続金額"),
	
	CONTINUOUS_TIME(6,"平均時間"),
	
	CONTINUOUS_DAYS(7,"平均日数"),
	
	CONTINUOUS_AMOUNT(8,"平均金額"),
	
	NUMBER_TIME (9,"該当月数　時間"),
	
	NUMBER_TIMES (10,"該当月数　日数"),
	
	NUMBER_AMOUNT (11,"該当月数　金額");
	
	

	
	public int value;
	
	public String nameId;
	
	private TypeCheckWorkRecordMultipleMonth (int value,String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
}

