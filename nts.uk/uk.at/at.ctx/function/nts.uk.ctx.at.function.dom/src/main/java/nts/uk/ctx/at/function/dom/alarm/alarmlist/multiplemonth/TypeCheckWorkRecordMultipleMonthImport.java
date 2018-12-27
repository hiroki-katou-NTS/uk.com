package nts.uk.ctx.at.function.dom.alarm.alarmlist.multiplemonth;

public enum TypeCheckWorkRecordMultipleMonthImport {
		
	TIME(0,"時間"),
	
	TIMES(1,"回数"),
	
	AMOUNT(2,"金額"),
	
	AVERAGE_TIME(3,"平均時間"),
	
	AVERAGE_TIMES(4,"平均回数"),
	
	AVERAGE_AMOUNT(5,"平均金額"),

	CONTINUOUS_TIME(6,"連続時間"),
	
	CONTINUOUS_TIMES(7,"連続回数"),
	
	CONTINUOUS_AMOUNT(8,"連続金額"),
	
	NUMBER_TIME (9,"該当月数 時間"),
	
	NUMBER_TIMES (10,"該当月数 回数"),
	
	NUMBER_AMOUNT (11,"該当月数 金額"),
	
	DAYS (12,"日数"),
	
	AVERAGE_DAYS (13,"平均日数"),
	
	CONTINUOUS_DAYS (14,"連続日数"),
	
	NUMBER_DAYS (15,"該当月数 日数");

	
	public int value;
	
	public String nameId;
	
	private TypeCheckWorkRecordMultipleMonthImport (int value,String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
}

