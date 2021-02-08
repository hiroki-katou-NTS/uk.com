package nts.uk.ctx.at.request.dom.application.overtime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 
 * @author hoangnd
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
// 申請時間詳細
public class OvertimeApplicationSetting {
	// frameNo
	private FrameNo frameNo;
	// type
	private AttendanceType_Update attendanceType;
	// 申請時間
	private TimeWithDayAttr applicationTime;
	
	public OvertimeApplicationSetting(Integer frameNo, AttendanceType_Update attendanceType, Integer applicationTime) {
		this.frameNo = new FrameNo(frameNo);
		this.attendanceType = attendanceType;
		this.applicationTime = new TimeWithDayAttr(applicationTime);
	}
}
