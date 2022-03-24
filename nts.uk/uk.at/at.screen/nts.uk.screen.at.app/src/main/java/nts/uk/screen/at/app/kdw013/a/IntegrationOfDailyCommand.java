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
	private String ymd;
	// 休憩時間帯: 日別勤怠の休憩時間帯
	private BreakTimeOfDailyAttdCommand breakTime;

	//private SnapshotDto snapshot;

	public static IntegrationOfDaily toDomain(IntegrationOfDailyCommand id) {
		IntegrationOfDaily result = new IntegrationOfDaily();
		
		result.setEmployeeId(id.getEmployeeId());
		result.setYmd(GeneralDate.fromString(id.getYmd(), "yyyy/MM/dd") );
		result.setBreakTime(id.getBreakTime().toDomain());
		return result;
	}
}
