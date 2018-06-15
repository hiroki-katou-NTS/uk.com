package nts.uk.ctx.at.schedule.dom.schedule.algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.logging.log4j.util.Strings;

import nts.uk.ctx.at.shared.dom.worktime.common.DeductionTime;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeDeductTimezone;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeHalfDayWorkTimezone;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeWorkSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixRestTimezoneSet;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeDailyAtr;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktype.DailyWork;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;

@Stateless
public class CreScheWithBusinessDayCalService {
	@Inject
	WorkTypeRepository workTypeRepository;

	@Inject
	WorkTimeSettingRepository workTimeSettingRepository;

	@Inject
	FixedWorkSettingRepository fixedWorkSettingRepository;

	@Inject
	FlowWorkSettingRepository flowWorkSettingRepository;

	@Inject
	DiffTimeWorkSettingRepository diffTimeWorkSettingRepository;

	/**
	 * 休憩予定時間帯を取得する
	 * 
	 * @param companyId
	 * @param workTypeCode
	 * @param workTimeCode
	 */
	public BusinessDayCal getScheduleBreakTime(String companyId, String workTypeCode, String workTimeCode,
			List<WorkType> listWorkType, List<WorkTimeSetting> listWorkTimeSetting) {
		// 入力パラメータ「就業時間帯コード」をチェック
		if (Strings.isBlank(workTimeCode) || "000".equals(workTimeCode)) {
			return null;
		}

		// ドメインモデル「勤務種類」を取得する
		// Optional<WorkType> workTypeOpt =
		// workTypeRepository.findByPK(companyId, workTypeCode);
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
		// Optional<WorkTimeSetting> workTimeSettingOpt = workTimeSettingRepository.findByCode(companyId, workTimeCode);
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
						workType.getDailyWork());
			}
		} else {
			// 就業時間帯の設定
			// 取得したドメインモデルの「就業時間帯勤務区分. 勤務形態区分」を判断
			if (workTimeSetting.getWorkTimeDivision().getWorkTimeDailyAtr() == WorkTimeDailyAtr.FLEX_WORK) {
				// ［フレックス勤務用］
				// TODO:
			} else {
				return determineSetWorkingHours(workTimeSetting, companyId, workTimeCode, false,
						workType.getDailyWork());
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
	 */
	public BusinessDayCal determineSetWorkingHours(WorkTimeSetting workTimeSetting, String companyId,
			String workTimeCode, boolean isHoliday, DailyWork dailyWork) {
		BusinessDayCal data = new BusinessDayCal();

		// 「就業時間帯勤務区分. 就業時間帯の設定方法」を判断
		switch (workTimeSetting.getWorkTimeDivision().getWorkTimeMethodSet()) {
		// ［固定勤務設定］
		case FIXED_WORK:
			Optional<FixedWorkSetting> fixedWorkSetting = fixedWorkSettingRepository.findByKey(companyId, workTimeCode);
			if (!fixedWorkSetting.isPresent()) {
				return null;
			}

			if (isHoliday) {
				// 固定勤務設定. 休日勤務時間帯. 休憩時間帯
				data.setTimezones(fixedWorkSetting.get().getOffdayWorkTimezone().getRestTimezone().getLstTimezone());
			} else {
				// 「固定勤務設定. 平日勤務時間帯. 休憩時間帯」
				List<FixRestTimezoneSet> lstTimezone = fixedWorkSetting.get().getLstHalfDayWorkTimezone().stream()
						.map(x -> x.getRestTimezone()).collect(Collectors.toList());
				List<DeductionTime> timezones = new ArrayList<>();
				lstTimezone.forEach(item -> {
					timezones.addAll(item.getLstTimezone());
				});
				data.setTimezones(timezones);
			}
			break;
		// ［流動勤務設定］
		case FLOW_WORK:
			Optional<FlowWorkSetting> flowWorkSetting = flowWorkSettingRepository.find(companyId, workTimeCode);
			if (!flowWorkSetting.isPresent()) {
				return null;
			}

			if (isHoliday) {
				// 流動勤務設定. 休日勤務時間帯. 休憩時間帯. 固定休憩時間帯
				data.setTimezones(flowWorkSetting.get().getOffdayWorkTimezone().getRestTimeZone().getFixedRestTimezone()
						.getTimezones());
			} else {
				// 流動勤務設定. 平日勤務時間帯. 休憩時間帯. 固定休憩時間帯」
				data.setTimezones(flowWorkSetting.get().getHalfDayWorkTimezone().getRestTimezone()
						.getFixedRestTimezone().getTimezones());
			}

			break;
		// ［時差勤務設定］
		case DIFFTIME_WORK:
			Optional<DiffTimeWorkSetting> diffTimeWorkSetting = diffTimeWorkSettingRepository.find(companyId,
					workTimeCode);

			if (!diffTimeWorkSetting.isPresent()) {
				return null;
			}

			if (isHoliday) {
				// 「時差勤務設定. 休日勤務時間帯. 休憩時間帯」
				List<DiffTimeDeductTimezone> lsDiffTimeDeductTimezone = diffTimeWorkSetting.get()
						.getDayoffWorkTimezone().getRestTimezone().getRestTimezones();
				List<DeductionTime> lsDeductionTime = new ArrayList<DeductionTime>(lsDiffTimeDeductTimezone);
				data.setTimezones(lsDeductionTime);
			} else {
				// 「時差勤務設定. 平日勤務時間帯. 休憩時間帯」
				List<DiffTimeHalfDayWorkTimezone> lsDiffTimeDeductTimezone = diffTimeWorkSetting.get()
						.getHalfDayWorkTimezones();

				List<DeductionTime> timezones = new ArrayList<>();
				lsDiffTimeDeductTimezone.forEach(item -> {
					timezones.addAll(item.getRestTimezone().getRestTimezones());
				});

				data.setTimezones(timezones);
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