package nts.uk.ctx.at.request.app.command.application.holidaywork;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.request.app.command.application.overtime.BreakTimeZoneSettingCommand;
import nts.uk.ctx.at.request.app.command.application.overtime.WorkHoursCommand;
import nts.uk.ctx.at.request.dom.application.holidayworktime.service.dto.OvertimeStatus;
import nts.uk.ctx.at.request.dom.application.overtime.AttendanceType;
import nts.uk.ctx.at.shared.app.command.worktype.WorkTypeCommandBase;

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
