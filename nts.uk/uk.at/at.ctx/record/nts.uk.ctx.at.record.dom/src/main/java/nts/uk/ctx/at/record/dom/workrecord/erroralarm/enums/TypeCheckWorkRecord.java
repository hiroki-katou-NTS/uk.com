package nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums;

/**
 * 勤務実績のチェック項目の種類
 * @author tutk
 *
 */
public enum TypeCheckWorkRecord {

	TIME(0,"時間"),
	
	TIMES(1,"回数"),
	
	AMOUNT_OF_MONEY(2,"金額"),
	
	TIME_OF_DAY(3,"時刻"),
	
	CONTINUOUS_TIME(4,"連続時間"),
	
	CONTINUOUS_WORK(5,"連続勤務"),
	
	CONTINUOUS_TIME_ZONE(6,"連続時間帯"),
	
	CONTINUOUS_CONDITION(7,"複合条件");

	
    /* 時間 */
    //TIME(0, "Enum_TypeCheckWorkRecord_Time"),
    /* 回数 */
    //TIMES(1, "Enum_TypeCheckWorkRecord_Times"),
    
    /* 金額 */
    //AMOUNT_OF_MONEY(2, "Enum_TypeCheckWorkRecord_AmountOfMoney"),
    
        /* 時刻 */
    //TIME_OF_DATE(3, "Enum_TypeCheckWorkRecord_TimeOfDate"),
    /* 連続時間 */
   // CONTINUOUS_TIME(4, "Enum_TypeCheckWorkRecord_ContinuousTime"),
    /* 連続勤務 */
    //CONTINUOUS_WORK(5, "Enum_TypeCheckWorkRecord_ContinuousWork"),
    /* 連続時間帯 */
    //CONTINUOUS_TIME_ZONE(6, "Enum_TypeCheckWorkRecord_ContinuousTimeZone"),
    /* 複合条件 */
   // COMPOUND_CONDITION(7, "Enum_TypeCheckWorkRecord_CompoundCondition");
	
	
	public int value;
	
	public String nameId;
	
	private TypeCheckWorkRecord (int value,String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
}
