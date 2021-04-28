package nts.uk.ctx.at.shared.dom.scherec.application.overtime;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;

/**
 * 
 * @author thanhnx
 *
 *         申請時間詳細(反映用)
 */
@AllArgsConstructor
@Data
public class OvertimeApplicationSettingShare {
	// 枠NO
	private int frameNo;
	//  勤怠種類
	private AttendanceTypeShare attendanceType;
	// 申請時間
	private AttendanceTime applicationTime;

	public OvertimeApplicationSettingShare(Integer frameNo, AttendanceTypeShare attendanceType,
			Integer applicationTime) {
		this.frameNo = frameNo;
		this.attendanceType = attendanceType;
		this.applicationTime = new AttendanceTime(applicationTime);
	}
}
