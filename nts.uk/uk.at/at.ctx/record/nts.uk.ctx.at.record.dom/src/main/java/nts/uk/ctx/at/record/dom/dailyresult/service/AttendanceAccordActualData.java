package nts.uk.ctx.at.record.dom.dailyresult.service;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AttendanceAccordActualData {
	// 在席状態
	private StatusClassfication attendanceState;

	// 勤務が出勤ですか
	private boolean workingNow;
}
