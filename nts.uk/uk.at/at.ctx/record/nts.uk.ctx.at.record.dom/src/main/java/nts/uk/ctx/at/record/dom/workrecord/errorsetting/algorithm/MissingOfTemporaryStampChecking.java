package nts.uk.ctx.at.record.dom.workrecord.errorsetting.algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.worktime.TemporaryTimeOfDailyPerformance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.StampLeakStateEachWork;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.erroralarm.EmployeeDailyPerError;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.erroralarm.ErrorAlarmWorkRecordCode;

/*
 * 臨時系打刻漏れをチェックする
 */
@Stateless
public class MissingOfTemporaryStampChecking {

	/**
	 * 臨時系打刻漏れ
	 * @param companyID 会社ID
	 * @param employeeID 社員ID
	 * @param processingDate 処理日
	 * @param temporaryTimeOfDailyPerformance 日別勤怠の臨時出退勤
	 * @return 社員の日別実績エラー一覧
	 */
	public Optional<EmployeeDailyPerError> missingOfTemporaryStampChecking(
			String companyID,
			String employeeID,
			GeneralDate processingDate,
			TemporaryTimeOfDailyPerformance temporaryTimeOfDailyPerformance) {

		EmployeeDailyPerError employeeDailyPerError = null;
		if (temporaryTimeOfDailyPerformance == null) return Optional.empty();

		// 勤怠項目ID　（添字が勤務NOと対応）
		List<Integer> attdId = Arrays.asList(0, 51, 59, 67, 2738, 2746, 2754, 2762, 2770, 2778, 2786);
		List<Integer> leaveId = Arrays.asList(0, 53, 61, 69, 2740, 2748, 2756, 2764, 2772, 2780, 2788);
		
		// 打刻漏れ状態チェック
		List<StampLeakStateEachWork> stampLeakStateList = new ArrayList<>();
		if (temporaryTimeOfDailyPerformance.getAttendance() != null) {
			stampLeakStateList =
					temporaryTimeOfDailyPerformance.getAttendance().checkStampLeakState();
		}
		
		List<Integer> attendanceItemIDList = new ArrayList<>();
		for (StampLeakStateEachWork stampLeakState : stampLeakStateList) {
			switch (stampLeakState.getStampLeakState()) {
			case NO_ATTENDANCE:
				attendanceItemIDList.add(attdId.get(stampLeakState.getWorkNo().v()));
				break;
			case NO_LEAVE:
				attendanceItemIDList.add(leaveId.get(stampLeakState.getWorkNo().v()));
				break;
			case NOT_EXIST:
				attendanceItemIDList.add(attdId.get(stampLeakState.getWorkNo().v()));
				attendanceItemIDList.add(leaveId.get(stampLeakState.getWorkNo().v()));
				break;
			default:
				break;
			}
		}
		
		if (attendanceItemIDList.size() > 0) {
			// 社員の日別実績のエラーを作成する
			employeeDailyPerError = new EmployeeDailyPerError(companyID, employeeID, processingDate,
					new ErrorAlarmWorkRecordCode("S001"), attendanceItemIDList);
		}
		return Optional.ofNullable(employeeDailyPerError);
	}
}
