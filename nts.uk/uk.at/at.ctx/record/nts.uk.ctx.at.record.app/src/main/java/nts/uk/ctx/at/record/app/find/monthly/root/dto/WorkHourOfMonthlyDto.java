package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;

@Data
@NoArgsConstructor
@AllArgsConstructor
/** 月別実績の勤務時刻 */
public class WorkHourOfMonthlyDto {

	/** PCログオン情報: 月別実績のPCログオン情報 */
	@AttendanceItemLayout(jpPropertyName = "PCログオン情報", layout = "A")
	private PCLogOnInfoOfMonthlyDto pcLogOnInfo;

	/** 終業時刻: 月別実績の終業時刻 */
	@AttendanceItemLayout(jpPropertyName = "終業時刻", layout = "B")
	private EndWorkHourOfMonthlyDto endWorkHours;
}
