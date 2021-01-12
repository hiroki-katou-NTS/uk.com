package nts.uk.ctx.at.request.app.find.application.holidaywork.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.dom.application.holidayworktime.service.dto.OvertimeStatus;

/**
 * Refactor5
 * @author huylq
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OvertimeStatusDto {

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
	
	public static OvertimeStatusDto fromDomain(OvertimeStatus domain) {
		if (domain == null) return null;
		return new OvertimeStatusDto(domain.isPreApplicationOvertime(), domain.getAttendanceType().value, 
				domain.isActualOvertime(), domain.getFrameNo(), domain.isInputCalculationDiff());
	}
}
