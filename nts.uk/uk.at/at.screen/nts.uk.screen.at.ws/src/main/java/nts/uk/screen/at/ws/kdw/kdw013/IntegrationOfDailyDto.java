package nts.uk.screen.at.ws.kdw.kdw013;

import nts.arc.time.GeneralDate;

/**
 * 
 * @author tutt
 *
 */
public class IntegrationOfDailyDto {
	// 社員ID
	private String employeeId;

	// 年月日
	private GeneralDate ymd;
	
	// 勤務情報: 日別勤怠の勤務情報
	private WorkInfoOfDailyAttendanceDto workInformationDto;
}
