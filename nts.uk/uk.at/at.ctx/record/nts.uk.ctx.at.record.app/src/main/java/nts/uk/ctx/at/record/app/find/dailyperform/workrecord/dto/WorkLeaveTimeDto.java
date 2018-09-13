package nts.uk.ctx.at.record.app.find.dailyperform.workrecord.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.app.find.dailyperform.common.WithActualTimeStampDto;
import nts.uk.ctx.at.shared.dom.attendance.util.ItemConst;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;

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
}
