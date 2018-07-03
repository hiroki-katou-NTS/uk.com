package nts.uk.ctx.at.schedule.dom.schedule.algorithm;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.ejb.Stateless;

import org.apache.logging.log4j.util.Strings;

import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeDailyAtr;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktype.DailyWork;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;

@Stateless
public class CreScheWithBusinessDayCalService {

	/**
	 * 休憩予定時間帯を取得する
	 * 
	 * @param companyId
	 * @param workTypeCode
	 * @param workTimeCode
	 */
	public BusinessDayCal getScheduleBreakTime(String companyId, String workTypeCode, String workTimeCode,
			List<WorkType> listWorkType, List<WorkTimeSetting> listWorkTimeSetting,
			Map<String, WorkRestTimeZoneDto> mapFixedWorkSetting, Map<String, WorkRestTimeZoneDto> mapFlowWorkSetting,
			Map<String, WorkRestTimeZoneDto> mapDiffTimeWorkSetting) {
		// 入力パラメータ「就業時間帯コード」をチェック
		if (Strings.isBlank(workTimeCode)) {
			return null;
		}

		// ドメインモデル「勤務種類」を取得する
		// EA No2018
		// 勤務種類一覧から入力パラメータ.勤務種類コードと一致する情報を取得する
		Optional<WorkType> workTypeOpt = listWorkType.stream()
				.filter(x -> (x.getCompanyId().equals(companyId) && x.getWorkTypeCode().toString().equals(workTypeCode)))
				.findFirst();
		if (!workTypeOpt.isPresent()) {
			throw new RuntimeException("Work Type Not Found:" + workTypeCode);
		}

		WorkType workType = workTypeOpt.get();

		// 取得したドメインモデルの「勤務種類. 1日の勤務. 勤務種類の分類」を判断
		DailyWork dailyWork = workType.getDailyWork();

		// ドメインモデル「就業時間帯の設定」を取得する
		// EA No2018
		// 就業時間帯一覧から入力パラメータ.就業時間帯コードと一致する情報を取得する
		Optional<WorkTimeSetting> workTimeSettingOpt = listWorkTimeSetting.stream()
				.filter(x -> (x.getCompanyId().equals(companyId) && x.getWorktimeCode().toString().equals(workTimeCode)))
				.findFirst();
		if (!workTimeSettingOpt.isPresent()) {
			return null;
		}

		WorkTimeSetting workTimeSetting = workTimeSettingOpt.get();

		// 休日出勤 or 休日出勤 以外
		if (dailyWork.isHolidayWork()) {
			// 取得したドメインモデルの「就業時間帯勤務区分. 勤務形態区分」を判断
			if (workTimeSetting.getWorkTimeDivision().getWorkTimeDailyAtr() == WorkTimeDailyAtr.FLEX_WORK) {
				// 今回対象外
				// TODO:
			} else {
				return determineSetWorkingHours(workTimeSetting, companyId, workTimeCode, true,
						workType.getDailyWork(), mapFixedWorkSetting, mapFlowWorkSetting, mapDiffTimeWorkSetting);
			}
		} else {
			// 就業時間帯の設定
			// 取得したドメインモデルの「就業時間帯勤務区分. 勤務形態区分」を判断
			if (workTimeSetting.getWorkTimeDivision().getWorkTimeDailyAtr() == WorkTimeDailyAtr.FLEX_WORK) {
				// ［フレックス勤務用］
				// TODO:
			} else {
				return determineSetWorkingHours(workTimeSetting, companyId, workTimeCode, false,
						workType.getDailyWork(), mapFixedWorkSetting, mapFlowWorkSetting, mapDiffTimeWorkSetting);
			}
		}

		return null;
	}

	/**
	 * 「就業時間帯勤務区分. 就業時間帯の設定方法」を判断
	 * 
	 * @param workTimeSetting
	 * @param companyId
	 * @param workTimeCode
	 * @param isHoliday
	 * @param dailyWork
	 * @param listFixedWorkSetting
	 * @param listFlowWorkSetting
	 * @param listDiffTimeWorkSetting
	 * @return
	 */
	public BusinessDayCal determineSetWorkingHours(WorkTimeSetting workTimeSetting, String companyId,
			String workTimeCode, boolean isHoliday, DailyWork dailyWork,
			Map<String, WorkRestTimeZoneDto> mapFixedWorkSetting, Map<String, WorkRestTimeZoneDto> mapFlowWorkSetting,
			Map<String, WorkRestTimeZoneDto> mapDiffTimeWorkSetting) {
		BusinessDayCal data = new BusinessDayCal();

		// 「就業時間帯勤務区分. 就業時間帯の設定方法」を判断
		// EA修正履歴　No2104
		switch (workTimeSetting.getWorkTimeDivision().getWorkTimeMethodSet()) {
		// ［固定勤務設定］
		case FIXED_WORK:
			WorkRestTimeZoneDto fixedWorkSettingDto = mapFixedWorkSetting.get(workTimeCode);
			
			if (fixedWorkSettingDto ==null) {
				return null;
			}

			if (isHoliday) {
				// 固定勤務設定. 休日勤務時間帯. 休憩時間帯
				data.setTimezones(fixedWorkSettingDto.getListOffdayWorkTimezone());
			} else {
				// 「固定勤務設定. 平日勤務時間帯. 休憩時間帯」
				data.setTimezones(fixedWorkSettingDto.getListHalfDayWorkTimezone());
			}
			break;
		// ［流動勤務設定］
		case FLOW_WORK:
			WorkRestTimeZoneDto flowWorkSettingDto = mapFlowWorkSetting.get(workTimeCode);
			if (flowWorkSettingDto == null) {
				return null;
			}

			if (isHoliday) {
				// 流動勤務設定. 休日勤務時間帯. 休憩時間帯. 固定休憩時間帯
				data.setTimezones(flowWorkSettingDto.getListOffdayWorkTimezone());
			} else {
				// 流動勤務設定. 平日勤務時間帯. 休憩時間帯. 固定休憩時間帯」
				data.setTimezones(flowWorkSettingDto.getListHalfDayWorkTimezone());
			}

			break;
		// ［時差勤務設定］
		case DIFFTIME_WORK:
			WorkRestTimeZoneDto diffTimeWorkSettingDto = mapDiffTimeWorkSetting.get(workTimeCode);

			if (diffTimeWorkSettingDto == null) {
				return null;
			}

			if (isHoliday) {
				// 「時差勤務設定. 休日勤務時間帯. 休憩時間帯」
				data.setTimezones(diffTimeWorkSettingDto.getListOffdayWorkTimezone());
			} else {
				// 「時差勤務設定. 平日勤務時間帯. 休憩時間帯」
				data.setTimezones(diffTimeWorkSettingDto.getListHalfDayWorkTimezone());
			}

			break;
		}

		return data;
	}

	/**
	 * Make Break Times Fixed Work Setting
	 * 
	 * @param dailyWork
	 */
	public void makeBreakTimesFixedWorkSetting(DailyWork dailyWork) {
		if (dailyWork.IsLeaveForMorning()) {

		} else if (dailyWork.IsLeaveForAfternoon()) {

		}
	}

	/**
	 * Make Flow Work Setting
	 * 
	 * @param dailyWork
	 */
	public void makeFlowWorkSetting(DailyWork dailyWork) {
		if (dailyWork.IsLeaveForMorning()) {

		} else if (dailyWork.IsLeaveForAfternoon()) {

		}
	}

	/**
	 * Make Diff Time Work Setting
	 * 
	 * @param dailyWork
	 */
	public void makeDiffTimeWorkSetting(DailyWork dailyWork) {
		if (dailyWork.IsLeaveForMorning()) {

		} else if (dailyWork.IsLeaveForAfternoon()) {

		}
	}
}