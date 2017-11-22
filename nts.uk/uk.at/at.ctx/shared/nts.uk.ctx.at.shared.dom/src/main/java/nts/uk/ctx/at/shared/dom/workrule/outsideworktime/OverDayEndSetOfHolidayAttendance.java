package nts.uk.ctx.at.shared.dom.workrule.outsideworktime;

import lombok.Value;

import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.holidaywork.HolidayWorkFrameNo;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.overtimework.overtimeworkframe.OverTimeWorkFrameNo;
/**
 * 休日出勤の0時跨ぎ設定
 * @author keisuke_hoshina
 *
 */

@Value
public class OverDayEndSetOfHolidayAttendance {
	/*変更後の残業枠No*/
	private OverTimeWorkFrameNo TransferFrameNoOfOverWork;
	/*変更前の休出枠No*/
	private HolidayWorkFrameNo holidayWorkFrameNo;
}
