package nts.uk.ctx.at.shared.dom.scherec.application.overtime;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.holidaywork.StaturoryAtrOfHolidayWork;

/**
 * @author thanh_nx
 *
 *         申請休出深夜時間(反映用)
 */
@AllArgsConstructor
@Data
public class HolidayMidNightTimeShare {
	// 時間
	private AttendanceTime attendanceTime;
	// 法定区分
	private StaturoryAtrOfHolidayWork legalClf;

	public HolidayMidNightTimeShare(Integer attendanceTime, StaturoryAtrOfHolidayWork legalClf) {
		this.attendanceTime = new AttendanceTime(attendanceTime);
		this.legalClf = legalClf;
	}
}
