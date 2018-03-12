package nts.uk.ctx.at.schedule.pub.shift.management;

public enum HolidayClsExport {
	/* 法定休日 */
    STATUTORY_HOLIDAYS(0),
	/* 法定外休日*/
    NON_STATUTORY_HOLIDAYS(1),
	/* 祝日*/
	PUBLIC_HOLIDAY(2);
	
	public final int value;
	
	private HolidayClsExport(int value) {
		this.value = value;
	}
}
