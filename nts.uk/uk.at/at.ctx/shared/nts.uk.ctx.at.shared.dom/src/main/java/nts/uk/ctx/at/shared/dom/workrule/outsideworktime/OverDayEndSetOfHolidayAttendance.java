package nts.uk.ctx.at.shared.dom.workrule.outsideworktime;

import lombok.Value;

/**
 * 休日出勤の0時跨ぎ設定
 * @author keisuke_hoshina
 *
 */
@Value
public class OverDayEndSetOfHolidayAttendance {
	private int TransFerFrameNoOfOverWork;
	private int HolidayWorkFrameNo;
}
