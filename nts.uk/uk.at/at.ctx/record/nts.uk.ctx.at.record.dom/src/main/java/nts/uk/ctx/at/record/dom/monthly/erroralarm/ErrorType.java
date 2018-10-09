package nts.uk.ctx.at.record.dom.monthly.erroralarm;

/**
 * @author dungdt
 * 月別実績のエラー種類
 */
public enum ErrorType {
	
	FLEX(0, "フレックス不足補填"),
	
	FLEX_SUPP(1, "フレックス不足"),

	YEARLY_HOLIDAY(2, "年休残数"),
	
	NUMBER_OF_MISSED_PIT(3,"積休残数"),
	
	REMAINING_ALTERNATION_NUMBER(4,"代休残数"),
	
	REMAIN_LEFT(5,"振休残数"),
	
	SPECIAL_REMAIN_HOLIDAY_NUMBER(6,"特休残数"),
	
	PUBLIC_HOLIDAY(7,"公休"),
	
	H60_SUPER_HOLIDAY(8,"60H超休");
	
	
	public int value;

	public String nameId;

	private ErrorType(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
}
