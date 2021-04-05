package nts.uk.ctx.office.dom.status.service;

import java.util.Optional;

import lombok.Builder;
import lombok.Data;
import nts.uk.ctx.office.dom.status.StatusClassfication;

@Builder
@Data
public class AttendanceAccordActualData {
	// 在席状態
	private StatusClassfication attendanceState;

	// 勤務が出勤ですか
	private Optional<Boolean> workingNow;
}
