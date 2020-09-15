package nts.uk.ctx.at.schedule.dom.schedule.alarm.simultaneousattendance.ban;
/**
 * 適用する時間帯
 * @author lan_lt
 *
 */
public enum ApplicableTimeZoneCls {
	/** 0 - 全日帯 **/
	ALLDAY(0),
	/** 1 - 夜勤時間帯**/
	NIGHTSHIFT(1);

	public final int value;

	private ApplicableTimeZoneCls(int value) {
		this.value = value;
	}
}
