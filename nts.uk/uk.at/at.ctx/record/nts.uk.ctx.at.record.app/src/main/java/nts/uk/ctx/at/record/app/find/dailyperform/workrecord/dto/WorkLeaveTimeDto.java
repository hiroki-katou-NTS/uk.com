package nts.uk.ctx.at.record.app.find.dailyperform.workrecord.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.app.find.dailyperform.common.WithActualTimeStampDto;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;

@Data
/** 出退勤時刻 */
@AllArgsConstructor
@NoArgsConstructor
public class WorkLeaveTimeDto {

	private Integer workNo;

	@AttendanceItemLayout(layout = "A", jpPropertyName = "出勤")
	private WithActualTimeStampDto working;
	
	@AttendanceItemLayout(layout = "B", jpPropertyName = "退勤")
	private WithActualTimeStampDto leave;
}
