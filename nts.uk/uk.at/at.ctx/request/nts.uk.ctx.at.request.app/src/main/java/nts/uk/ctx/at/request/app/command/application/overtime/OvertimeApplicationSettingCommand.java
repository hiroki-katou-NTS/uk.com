package nts.uk.ctx.at.request.app.command.application.overtime;

import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.request.dom.application.overtime.AttendanceType_Update;
import nts.uk.ctx.at.request.dom.application.overtime.FrameNo;
import nts.uk.ctx.at.request.dom.application.overtime.OvertimeApplicationSetting;
import nts.uk.shr.com.time.TimeWithDayAttr;

public class OvertimeApplicationSettingCommand {
	// frameNo
	public Integer frameNo;
	// type
	public Integer attendanceType;
	// 申請時間
	public Integer applicationTime;
	
	public OvertimeApplicationSetting toDomain() {
		return new OvertimeApplicationSetting(
				new FrameNo(frameNo),
				EnumAdaptor.valueOf(attendanceType, AttendanceType_Update.class),
				new TimeWithDayAttr(applicationTime));
	}
}
