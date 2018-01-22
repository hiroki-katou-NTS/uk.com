package nts.uk.ctx.at.record.app.find.dailyperform.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemLayout;

/** 控除合計時間 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TotalDeductionTimeDto {

	/** 所定外合計時間 */
	@AttendanceItemLayout(layout = "A", jpPropertyName="法定外合計時間")
	private CalcAttachTimeDto excessOfStatutoryTotalTime;

	/** 所定内合計時間 */
	@AttendanceItemLayout(layout = "B", jpPropertyName="法定内合計時間")
	private CalcAttachTimeDto withinStatutoryTotalTime;

	/** 合計時間 */
	@AttendanceItemLayout(layout = "C", jpPropertyName="合計時間")
	private CalcAttachTimeDto totalTime;
}
