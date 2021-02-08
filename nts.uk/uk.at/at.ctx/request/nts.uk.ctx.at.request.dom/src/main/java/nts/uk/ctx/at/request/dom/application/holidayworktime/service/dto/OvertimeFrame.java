package nts.uk.ctx.at.request.dom.application.holidayworktime.service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;

/**
 * 残業時間枠
 * @author huylq
 *Refactor5
 */
@Data
@NoArgsConstructor
public class OvertimeFrame {

	/**
	 * 枠No
	 */
	private int frameNo;
	
	/**
	 * 枠名
	 */
	private String frameName;
	
	/**
	 * 時間
	 */
	private AttendanceTime time;
	
	/**
	 * 勤怠種類
	 */
	private int attendanceType;
}
