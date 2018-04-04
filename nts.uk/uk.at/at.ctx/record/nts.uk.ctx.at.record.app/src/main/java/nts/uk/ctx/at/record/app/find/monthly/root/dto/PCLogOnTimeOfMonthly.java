package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;

@Data
@NoArgsConstructor
@AllArgsConstructor
/** 月別実績のPCログオン時刻 + 月別実績のPCログオン乖離 */
public class PCLogOnTimeOfMonthly {

	@AttendanceItemLayout(jpPropertyName = "PCログオフ時刻", layout = "A")
	/** PCログオフ時刻: 集計PCログオン時刻 + PCログオフ乖離: 集計PCログオン乖離 */
	private TotalPcLogon logOff;

	@AttendanceItemLayout(jpPropertyName = "PCログオン時刻", layout = "B")
	/** PCログオン時刻: 集計PCログオン時刻 + PCログオン乖離: 集計PCログオン乖離 */
	private TotalPcLogon logOn;
}
