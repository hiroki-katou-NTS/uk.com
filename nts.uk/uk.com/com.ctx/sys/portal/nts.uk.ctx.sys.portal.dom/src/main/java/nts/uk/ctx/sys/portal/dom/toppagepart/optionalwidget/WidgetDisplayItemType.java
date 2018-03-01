package nts.uk.ctx.sys.portal.dom.toppagepart.optionalwidget;

import nts.uk.ctx.sys.portal.dom.enums.TopPagePartType;

//選択ウィジェット種類
public enum WidgetDisplayItemType {

	/** The Overtime Number. */
	OvertimeNumber(0),
	
	/** The Number Of Holiday. */
	NumberOfHoliday(1),
	
	/** The Number Approved. */
	NumberApproved(2),
	
	/** The Unapproved Number. */
	UnapprovedNumber(3),
	
	/** The Number Of Denied. */
	NumberOfDenied(4),
	
	/** The Number Of Remand. */
	NumberOfRemand(5),
	
	/** The Approved Deadline Day. */
	ApprovedDeadlineDay(6),
	
	/** The Error For Daily. */
	ErrorForDaily(7),
	
	/** The Refer To Work Record. */
	ReferToWorkRecord(8),
	
	/** The Overtime Hours. */
	OvertimeHours(9),
	
	/** The Flex Time. */
	FlexTime(10),
	
	/** The Rest Time. */
	RestTime(11),
	
	/** The Late Night Hours. */
	LateNightHours(12),
	
	/** The Late Early Retreat. */
	LateEarlyRetreat(13),
	
	/** The Yearly Holidays. */
	YearlyHolidays(14),
	
	/** The Number Of Reserved. */
	NumberOfReserved(15),
	
	/** The Remaining Alternation Number. */
	RemainingAlternationNumber(16),
	
	/** The Remains Left. */
	RemainsLeft(17),
	
	/** The Number Of Public Holidays. */
	NumberOfPublicHolidays(18),
	
	/** The Child Nursing Vacation. */
	ChildNursingVacation(19),
	
	/** The Number Of Nursing. */
	NumberOfNursing(20),
	
	/** The Special Remaining. */
	SpecialRemaining(21),
	
	/** The 60H Extra Rest. */
	HExtraRest(22);
	
	/** The value. */
	public int value;

	/** The Constant values. */
	private final static WidgetDisplayItemType[] values = WidgetDisplayItemType.values();

	/**
	 * Instantiates a new top page part type.
	 *
	 * @param value the value
	 */
	private WidgetDisplayItemType(int value) {
		this.value = value;
	}

	/**
	 * Value of.
	 *
	 * @param value the value
	 * @return the top page part type
	 */
	public static WidgetDisplayItemType valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (WidgetDisplayItemType val : WidgetDisplayItemType.values) {
			if (val.value == value) {
				return val;
			}
		}
		// Not found.
		return null;
	}
}
