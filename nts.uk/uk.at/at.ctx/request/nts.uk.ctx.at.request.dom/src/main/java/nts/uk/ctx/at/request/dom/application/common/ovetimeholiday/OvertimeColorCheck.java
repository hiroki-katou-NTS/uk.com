package nts.uk.ctx.at.request.dom.application.common.ovetimeholiday;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class OvertimeColorCheck {
	
	public Integer attendanceID;
	
	public Integer frameNo;
	
	public Integer appTime;
	
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
	
}
