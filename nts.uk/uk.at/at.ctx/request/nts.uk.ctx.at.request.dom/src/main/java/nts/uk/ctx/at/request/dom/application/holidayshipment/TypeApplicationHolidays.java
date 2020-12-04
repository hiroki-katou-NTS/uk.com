package nts.uk.ctx.at.request.dom.application.holidayshipment;

/**
 * 振休振出申請種類
 * 
 * @author ThanhPV
 */
public enum TypeApplicationHolidays {
	/**
	 * 振出申請
	 */
	Rec(0, "振出申請"),
	/**
	 * 振休申請
	 */
	Abs(1, "振休申請");

	public final int value;
	public final String nameId;

	private TypeApplicationHolidays(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
}
