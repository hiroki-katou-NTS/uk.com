package nts.uk.screen.at.app.kdw013.a;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.ScheduleTimeSheet;
import nts.uk.ctx.at.shared.dom.worktime.predset.WorkNo;
import nts.uk.shr.com.time.TimeWithDayAttr;

@AllArgsConstructor
@Getter
public class ScheduleTimeSheetCommand {
	private int workNo;

	private Integer attendance;

	private Integer leaveWork;

	public static ScheduleTimeSheet toDomain(ScheduleTimeSheetCommand st) {

		return new ScheduleTimeSheet(new WorkNo(st.getWorkNo()),
				EnumAdaptor.valueOf(st.getAttendance(), TimeWithDayAttr.class),
				EnumAdaptor.valueOf(st.getLeaveWork(), TimeWithDayAttr.class));
	}
}
