package nts.uk.ctx.at.record.app.find.actualworkinghours.dto;

import lombok.Data;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemLayout;

/** 控除合計時間 */
@Data
public class TotalDeductionTimeDto {

	/** 所定外合計時間 */
	@AttendanceItemLayout(layout="A")
	private CalcAttachTimeDto excessOfStatutoryTotalTime;
	
	/** 所定内合計時間 */
	@AttendanceItemLayout(layout="B")
	private CalcAttachTimeDto withinStatutoryTotalTime;
	
	/** 合計時間 */
	@AttendanceItemLayout(layout="C")
	private CalcAttachTimeDto totalTime;
}
