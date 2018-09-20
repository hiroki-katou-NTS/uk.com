package nts.uk.ctx.at.record.app.find.dailyperform.workrecord.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.app.find.dailyperform.common.WithActualTimeStampDto;
import nts.uk.ctx.at.record.dom.worktime.TimeActualStamp;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingWork;
import nts.uk.ctx.at.shared.dom.attendance.util.ItemConst;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkNo;

@Data
/** 出退勤時刻 */
@AllArgsConstructor
@NoArgsConstructor
public class WorkLeaveTimeDto implements ItemConst {

	private Integer no;

	@AttendanceItemLayout(layout = LAYOUT_A, jpPropertyName = ATTENDANCE)
	private WithActualTimeStampDto working;
	
	@AttendanceItemLayout(layout = LAYOUT_B, jpPropertyName = LEAVE)
	private WithActualTimeStampDto leave;
	
	@Override
	public WorkLeaveTimeDto clone() {
		return new WorkLeaveTimeDto(no, working == null ? null : working.clone(), leave == null ? null : leave .clone());
	}
	
	public static TimeLeavingWork toDomain(WorkLeaveTimeDto c) {
		return c == null ? new TimeLeavingWork(new WorkNo(1), null, null) 
				: new TimeLeavingWork(new WorkNo(c.getNo()), toTimeActualStamp(c.getWorking()),
															toTimeActualStamp(c.getLeave()));
	}

	private static TimeActualStamp toTimeActualStamp(WithActualTimeStampDto c) {
		return c == null ? null : c.toDomain();
	}
}
