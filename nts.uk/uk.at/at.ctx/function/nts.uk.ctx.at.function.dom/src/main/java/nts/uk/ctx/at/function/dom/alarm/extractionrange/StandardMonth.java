package nts.uk.ctx.at.function.dom.alarm.extractionrange;

/**
 * 
 * 実績の基準月
 * @author dxthuong
 *
 */
public enum StandardMonth {

	/**当月*/
	CURRENT_MONTH(0, "当月"),
	
	/** 1ヶ月前 */
	ONE_MONTH_AGO(1, "1ヶ月前"),

	/** 2ヶ月前*/
	TWO_MONTH_AGO(2, "2ヶ月前"),
	
	/** 3ヶ月前*/
	THREE_MONTH_AGO(3, "3ヶ月前"),
	
	/** 4ヶ月前*/
	FOUR_MONTH_AGO(4, "4ヶ月前"),
	
	/** 5ヶ月前*/
	FIVE_MONTH_AGO(5, "5ヶ月前"),
	
	/** 6ヶ月前*/
	SIX_MONTH_AGO(6, "6ヶ月前"),
	
	/** 7ヶ月前*/
	SEVEN_MONTH_AGO(7, "7ヶ月前"),
	
	/** 8ヶ月*/
//	EIGHT_MONTH(8, "8ヶ月"),
	
	/** 9ヶ月*/
	//NINE_MONTH(9, "9ヶ月"),
	
	/** 10ヶ月*/
//	TEN_MONTH(10, "10ヶ月"),
	
	/** 11ヶ月*/
//	ELEVEN_MONTH(11, "11ヶ月"),
	
	/** 12ヶ月*/
//	TWELVE_MONTH(12, "12ヶ月");
	EIGHT_MONTH_AGO(8, "8ヶ月前"),
	NINE_MONTH_AGO(9, "9ヶ月前"),
	TEN_MONTH_AGO(10, "10ヶ月前"),
	ELEVEN_MONTH_AGO(11, "11ヶ月前"),
	TWELVE_MONTH_AGO(12, "12ヶ月前");
	
	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;
	
	private StandardMonth(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
}
