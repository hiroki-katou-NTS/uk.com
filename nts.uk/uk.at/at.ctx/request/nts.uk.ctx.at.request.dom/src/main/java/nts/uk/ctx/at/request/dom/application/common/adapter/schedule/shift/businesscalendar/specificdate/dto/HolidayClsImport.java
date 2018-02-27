package nts.uk.ctx.at.request.dom.application.common.adapter.schedule.shift.businesscalendar.specificdate.dto;

public enum HolidayClsImport {
	/* 法定休日 */
    STATUTORY_HOLIDAYS(0),
	/* 法定外休日*/
    NON_STATUTORY_HOLIDAYS(1),
	/* 祝日*/
	PUBLIC_HOLIDAY(2);
	
	public final int value;
	
	private HolidayClsImport(int value) {
		this.value = value;
	}
}
