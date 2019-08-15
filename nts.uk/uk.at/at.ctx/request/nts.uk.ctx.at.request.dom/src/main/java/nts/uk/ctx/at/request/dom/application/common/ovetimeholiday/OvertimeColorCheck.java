package nts.uk.ctx.at.request.dom.application.common.ovetimeholiday;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.dom.application.common.adapter.frame.OvertimeInputCaculation;
import nts.uk.ctx.at.request.dom.application.overtime.service.CaculationTime;

@AllArgsConstructor
@NoArgsConstructor
public class OvertimeColorCheck {
	
	public Integer attendanceID;
	
	public Integer frameNo;
	
	public Integer appTime;
	
	public Integer calcTime;
	
	public boolean calcError;
	
	public Integer preAppTime;
	
	public boolean preAppError;
	
	public Integer actualTime;
	
	public boolean actualError;
	
	public static OvertimeColorCheck createActual(Integer attendanceID, Integer frameNo, Integer actualTime){
		OvertimeColorCheck overtimeColorCheck = new OvertimeColorCheck();
		overtimeColorCheck.attendanceID = attendanceID;
		overtimeColorCheck.frameNo = frameNo;
		overtimeColorCheck.actualTime = actualTime;
		return overtimeColorCheck;
	}
	
	public static OvertimeColorCheck createApp(Integer attendanceID, Integer frameNo, Integer appTime){
		OvertimeColorCheck overtimeColorCheck = new OvertimeColorCheck();
		overtimeColorCheck.attendanceID = attendanceID;
		overtimeColorCheck.frameNo = frameNo;
		overtimeColorCheck.appTime = appTime;
		return overtimeColorCheck;
	}
	
	public static OvertimeColorCheck createFromCalc(OvertimeInputCaculation overtimeInputCaculation){
		OvertimeColorCheck overtimeColorCheck = new OvertimeColorCheck();
		overtimeColorCheck.attendanceID = overtimeInputCaculation.getAttendanceID();
		overtimeColorCheck.frameNo = overtimeInputCaculation.getFrameNo();
		overtimeColorCheck.calcTime = overtimeInputCaculation.getResultCaculation();
		return overtimeColorCheck;
	}
	
	public static OvertimeColorCheck createFromOverTimeInput(CaculationTime overtimeHour){
		OvertimeColorCheck overtimeColorCheck = new OvertimeColorCheck();
		overtimeColorCheck.attendanceID = overtimeHour.getAttendanceID();
		overtimeColorCheck.frameNo = overtimeHour.getFrameNo();
		overtimeColorCheck.appTime = overtimeHour.getApplicationTime();
		overtimeColorCheck.preAppTime = null == overtimeHour.getPreAppTime() ? null: Integer.parseInt(overtimeHour.getPreAppTime());
		overtimeColorCheck.actualTime = null == overtimeHour.getCaculationTime() ? null: Integer.parseInt(overtimeHour.getCaculationTime());
		return overtimeColorCheck;
	}
	
}
