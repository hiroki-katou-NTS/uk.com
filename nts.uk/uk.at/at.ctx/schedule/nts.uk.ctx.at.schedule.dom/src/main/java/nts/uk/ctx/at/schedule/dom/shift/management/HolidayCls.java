package nts.uk.ctx.at.schedule.dom.shift.management;

/**
 * 休日区分
 * @author tanlv
 *
 */
public enum HolidayCls {
	/* 法定休日 */
    STATUTORY_HOLIDAYS(0),
	/* 法定外休日*/
    NON_STATUTORY_HOLIDAYS(1),
	/* 祝日*/
	PUBLIC_HOLIDAY(2);
	
	public final int value;
	
	private HolidayCls(int value) {
		this.value = value;
	}
}
