package nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums;

public enum TypeCheckWorkRecordMultipleMonth {
	
//	TIME(0,"時間"),
//	
//	TIMES(1,"回数"),
//	
//	AMOUNT_OF_MONEY(2,"金額"),
//	
//	CONTINUOUS_TIME(3,"連続時間"),
//	
//	CONTINUOUS_TIMES(4,"連続回数"),
//	
//	CONTINUOUS_AMOUNT(5,"連続金額"),
//	
//	AVERAGE_TIME(6,"連続時間帯"),
//	
//	AVERAGE_TIMES(7,"平均回数"),
//	
//	AVERAGE_PRICE(8,"平均金額"),
//	
//	CORRESPONDING_NUMBER_OF_HOURS (9,"該当月数時間"),
//	
//	CORRESPONDING_NUMBER_OF_MONTHS (10,"該当月数回数"),
//	
//	CORRESPONDING_NUMBER_OF_Monthly (11,"該当月数金額");
	
	
	TIME(0,"時間"),
	
	TIMES(1,"回数"),
	
	AMOUNT(2,"金額"),
	
	CONTINUOUS_TIME(3,"平均時間"),
	
	CONTINUOUS_DAYS(4,"平均日数"),
	
	CONTINUOUS_AMOUNT(5,"平均金額"),
	
	TIME_AVERAGE(6,"連続時間"),
	
	TIMES_AVERAGE(7,"連続日数"),
	
	AMOUNT_AVERAGE(8,"連続金額"),
	
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

