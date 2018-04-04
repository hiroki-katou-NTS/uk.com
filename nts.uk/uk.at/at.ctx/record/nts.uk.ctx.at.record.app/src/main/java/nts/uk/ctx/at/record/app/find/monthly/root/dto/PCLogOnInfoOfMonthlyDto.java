package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;

@Data
@NoArgsConstructor
@AllArgsConstructor
/** 月別実績のPCログオン情報 */
public class PCLogOnInfoOfMonthlyDto {

	@AttendanceItemLayout(jpPropertyName = "PCログオン時刻", layout = "A")
	/** PCログオン時刻: 月別実績のPCログオン時刻 */
	private PCLogOnTimeOfMonthly pcLogOnTime;

	@AttendanceItemLayout(jpPropertyName = "PCログオン乖離", layout = "A")
	/** PCログオン乖離: 月別実績のPCログオン乖離 */
	private PCLogOnTimeOfMonthly pcLogOnDivergence;
}
