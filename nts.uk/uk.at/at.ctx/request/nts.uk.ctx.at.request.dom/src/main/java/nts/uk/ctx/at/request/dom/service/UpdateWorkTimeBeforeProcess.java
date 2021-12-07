package nts.uk.ctx.at.request.dom.service;

import java.util.Optional;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.adapter.schedule.schedule.basicschedule.ScBasicScheduleImport;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.WorkInfoOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.worktype.AttendanceDayAttr;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;

/**
 * @author thanh_nx
 *
 *         処理前に就業時間帯を更新する
 */
public class UpdateWorkTimeBeforeProcess {

	// 就業時間帯を更新する
	public static Optional<String> getWorkInfoFromSetting(Require require, String companyId, String employeeId,
			GeneralDate date, Optional<String> workTypeCode, Optional<String> workTimeCode) {

		Optional<String> workTypeCodeProcess = workTypeCode;
		// 申請から就業時間帯コードを反映するかどうか
		if (workTimeCode.isPresent()) {
			return workTimeCode;
		}

		Optional<WorkInfoOfDailyAttendance> workInfoOfDailyRecord = require.getWorkInfoOfDailyAttendance(employeeId,
				date);

		if (workInfoOfDailyRecord.isPresent()) {
			return workInfoOfDailyRecord.get().getRecordInfo().getWorkTimeCodeNotNull().map(x -> x.v());
		}

		workTypeCodeProcess = updateWorkTypeCode(workTypeCodeProcess,
				workInfoOfDailyRecord.flatMap(x -> x.getRecordInfo().getWorkTimeCodeNotNull().map(y -> y.v())));

		val schedule = require.findByIDRefactor(employeeId, date);
		if (schedule.isPresent()) {
			return schedule.get().getWorkTimeCode();
		}

		workTypeCodeProcess = updateWorkTypeCode(workTypeCodeProcess, schedule.map(x -> x.getWorkTypeCode()));

		return getWorkInfoFromSetting(require, companyId, employeeId, date, workTypeCodeProcess);
	}

	private static Optional<String> updateWorkTypeCode(Optional<String> workTypeCodeBefore,
			Optional<String> workTypeCodeAfter) {
		if (workTypeCodeBefore.isPresent()) {
			return workTypeCodeBefore;
		}

		return workTypeCodeAfter;
	}

	// 個人勤務設定から就業時間帯コードを取得する
	public static Optional<String> getWorkInfoFromSetting(Require require, String companyId, String employeeId,
			GeneralDate date, Optional<String> workTypeCode) {
		if (!workTypeCode.isPresent()) {
			return Optional.empty();
		}
		val workType = require.findByPK(companyId, workTypeCode.get());
		if (!workType.isPresent()) {
			return Optional.empty();
		}

		val workCondition = require.getWorkingConditionItemByEmpIDAndDate(companyId, date, employeeId);
		if (!workCondition.isPresent()) {
			return Optional.empty();
		}

		// 1日半日出勤・1日休日系の判定（休出判定あり）
		AttendanceDayAttr attAtr = workType.get().chechAttendanceDay();
		if (attAtr == AttendanceDayAttr.HOLIDAY) {
			return Optional.empty();
		}

		if (attAtr == AttendanceDayAttr.HOLIDAY_WORK) {
			// 休出時の勤務情報を取得する
			WorkInformation workInfo = workCondition.get().getWorkCategory().getWorkinfoOnVacation(workType.get());
			if (workInfo.getWorkTimeCodeNotNull().isPresent()) {
				return workInfo.getWorkTimeCodeNotNull().map(x -> x.v());
			}

			// 勤務情報を作成
			return workCondition.get().getWorkCategory().getWorkTime().getHolidayWork().getWorkTimeCode()
					.map(x -> x.v());
		}

		WorkInformation workInfo = workCondition.get().getWorkCategory().getWorkInformationDayOfTheWeek(date);
		if (workInfo.getWorkTimeCodeNotNull().isPresent()) {
			return workInfo.getWorkTimeCodeNotNull().map(x -> x.v());
		}

		return workCondition.get().getWorkCategory().getWorkInformationWorkDay().getWorkTimeCodeNotNull()
				.map(x -> x.v());

	}

	public static interface Require {

		// 勤務予定を取得する
		public Optional<ScBasicScheduleImport> findByIDRefactor(String employeeID, GeneralDate date);

		// WorkScheWorkInforSharedAdapter
		// 日別勤怠(Work)を取得する
		public Optional<WorkInfoOfDailyAttendance> getWorkInfoOfDailyAttendance(String employeeId, GeneralDate ymd);

		public Optional<WorkType> findByPK(String companyId, String workTypeCd);

		public Optional<WorkingConditionItem> getWorkingConditionItemByEmpIDAndDate(String companyID, GeneralDate ymd,
				String empID);

	}
}
