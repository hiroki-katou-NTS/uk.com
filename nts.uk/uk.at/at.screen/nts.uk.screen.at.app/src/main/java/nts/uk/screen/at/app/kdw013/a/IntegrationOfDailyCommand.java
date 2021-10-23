package nts.uk.screen.at.app.kdw013.a;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;

@Getter
@AllArgsConstructor
public class IntegrationOfDailyCommand {
	// 社員ID
	private String employeeId;

	// 年月日
	private GeneralDate ymd;

	// 勤務情報: 日別勤怠の勤務情報
	private WorkInfoOfDailyAttendanceCommand workInformationDto;

	public static IntegrationOfDaily toDomain(IntegrationOfDailyCommand id) {
		IntegrationOfDaily result = new IntegrationOfDaily();
		
		result.setEmployeeId(id.getEmployeeId());
		result.setYmd(id.getYmd());
		result.setWorkInformation(id.getWorkInformationDto().toDomain());
		
		return result;
	}
}
