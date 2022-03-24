package nts.uk.ctx.at.record.dom.workrecord.errorsetting.algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingOfDailyPerformance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.StampLeakStateEachWork;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.erroralarm.EmployeeDailyPerError;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.erroralarm.ErrorAlarmWorkRecordCode;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.algorithm.GetWorkTypeServiceShare;

/*
 * 打刻漏れ - (出退勤打刻漏れ)
 */
@Stateless
public class LackOfStampingAlgorithm {

	@Inject
	private GetWorkTypeServiceShare  getWorkTypeService;
	
	@Inject
	private PredetemineTimeSettingRepository predetemineTimeSettingRepository;

	/**
	 * 出退勤打刻漏れ
	 * @param companyID 会社ID
	 * @param employeeID 社員ID
	 * @param processingDate 処理日
	 * @param workInfoOfDailyPerformance 日別勤怠の勤務情報
	 * @param timeLeavingOfDailyPerformance 日別勤怠の出退勤
	 * @return 社員の日別実績エラー一覧
	 */
	public Optional<EmployeeDailyPerError> lackOfStamping(
			String companyID,
			String employeeID,
			GeneralDate processingDate,
			WorkInfoOfDailyPerformance workInfoOfDailyPerformance,
			TimeLeavingOfDailyPerformance timeLeavingOfDailyPerformance) {

		EmployeeDailyPerError employeeDailyPerError = null;
		
		// 勤怠項目ID　（添字が勤務NOと対応）
		List<Integer> attdId = Arrays.asList(0, 31, 41);
		List<Integer> leaveId = Arrays.asList(0, 34, 44);

		// 勤務種類を取得する
		Optional<WorkType> workType = this.getWorkTypeService.getWorkType(
				workInfoOfDailyPerformance.getWorkInformation().getRecordInfo().getWorkTypeCode().v());
		// 所定時間設定を取得する
		Optional<PredetemineTimeSetting> predetemineTimeSet = Optional.empty();
		Optional<WorkTimeCode> workTimeCode = workInfoOfDailyPerformance.getWorkInformation()
				.getRecordInfo().getWorkTimeCodeNotNull();
		if (workTimeCode.isPresent()) {
			predetemineTimeSet = this.predetemineTimeSettingRepository.findByWorkTimeCode(
					companyID, workTimeCode.get().v());
		}
		// 打刻漏れ状態チェック
		List<StampLeakStateEachWork> stampLeakStateList = new ArrayList<>();
		if (timeLeavingOfDailyPerformance.getAttendance() != null) {
			stampLeakStateList =
					timeLeavingOfDailyPerformance.getAttendance().checkStampLeakState(workType, predetemineTimeSet);
		}

		List<Integer> attendanceItemIDList = new ArrayList<>();
		if (stampLeakStateList.size() > 0) {
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
		}
		
		if (attendanceItemIDList.size() > 0) {
			// 社員の日別実績のエラーを作成する
			employeeDailyPerError = new EmployeeDailyPerError(companyID, employeeID, processingDate,
					new ErrorAlarmWorkRecordCode("S001"), attendanceItemIDList);
		}
		return Optional.ofNullable(employeeDailyPerError);
	}

}
