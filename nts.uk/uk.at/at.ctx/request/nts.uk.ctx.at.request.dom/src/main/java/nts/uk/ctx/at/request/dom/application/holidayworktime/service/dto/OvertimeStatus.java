package nts.uk.ctx.at.request.dom.application.holidayworktime.service.dto;

import lombok.Data;
import nts.uk.ctx.at.request.dom.application.overtime.AttendanceType;

/**
 * 勤怠時間の超過状態
 * @author huylq
 *Refactor5
 */
@Data
public class OvertimeStatus {

	/**
	 * 事前申請超過
	 */
	private boolean isPreApplicationOvertime;
	
	/**
	 * 勤怠種類
	 */
	private AttendanceType attendanceType;
	
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
}
