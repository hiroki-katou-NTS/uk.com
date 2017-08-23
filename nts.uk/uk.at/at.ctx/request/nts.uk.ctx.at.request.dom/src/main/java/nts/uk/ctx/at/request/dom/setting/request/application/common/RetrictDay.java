package nts.uk.ctx.at.request.dom.setting.request.application.common;

/** 申請受付制限日数 */
public enum RetrictDay {
	/* 0:当日 */
	THATDAY(0),
	/* 1:1日前 */
	ONEDAYAGO(1),
	/* 2:2日前 */
	TWODAYAGO(2),
	/* 3:3日前 */
	THREEDAYAGO(3),
	/* 4:4日前 */
	FOURDAYAGO(4),
	/* 5:5日前 */
	FIVEDAYAGO(5),
	/* 6:6日前 */
	SIXDAYAGO(6),
	/* 7:7日前 */
	SEVENDAYAGO(7);

	public final int value;

	RetrictDay(int value) {
		this.value = value;
	}

}
