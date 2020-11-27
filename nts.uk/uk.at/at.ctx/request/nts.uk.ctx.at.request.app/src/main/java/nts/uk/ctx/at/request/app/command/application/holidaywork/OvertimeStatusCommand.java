package nts.uk.ctx.at.request.app.command.application.holidaywork;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.request.dom.application.holidayworktime.service.dto.OvertimeStatus;
import nts.uk.ctx.at.request.dom.application.overtime.AttendanceType;

/**
 * Refactor5
 * @author huylq
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class OvertimeStatusCommand {
	
	/**
	 * 事前申請超過
	 */
	private boolean isPreApplicationOvertime;
	
	/**
	 * 勤怠種類
	 */
	private int attendanceType;

	/**
	 * 実績超過
	 */
	private boolean isActualOvertime;
	
	/**
	 * 枠No
	 */
	private int frameNo;
	
	/**
	 * 計算入力差異
	 */
	private boolean isInputCalculationDiff;
	
	public OvertimeStatus toDomain() {
		return new OvertimeStatus(
				this.isPreApplicationOvertime,
				EnumAdaptor.valueOf(this.attendanceType, AttendanceType.class),
				this.isActualOvertime,
				this.frameNo,
				this.isInputCalculationDiff);
	}
}
