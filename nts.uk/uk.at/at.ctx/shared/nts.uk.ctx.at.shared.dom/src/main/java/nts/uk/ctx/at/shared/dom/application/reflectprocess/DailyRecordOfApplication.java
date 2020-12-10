package nts.uk.ctx.at.shared.dom.application.reflectprocess;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.cancellation.AttendanceBeforeApplicationReflect;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;

/**
 * @author thanh_nx
 *
 *         日別勤怠(申請反映用Work)
 */
@Getter
@Setter
public class DailyRecordOfApplication extends IntegrationOfDaily {

	// 反映前の勤怠
	private List<AttendanceBeforeApplicationReflect> attendanceBeforeReflect;

	// 予定実績区分
	private ScheduleRecordClassifi classification;

	public DailyRecordOfApplication(List<AttendanceBeforeApplicationReflect> attendanceBeforeReflect,
			ScheduleRecordClassifi classification, IntegrationOfDaily domainDaily) {
		super(domainDaily);
		this.attendanceBeforeReflect = attendanceBeforeReflect;
		this.classification = classification;
	}
	
}
