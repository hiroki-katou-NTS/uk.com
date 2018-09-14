package nts.uk.ctx.at.schedule.dom.schedule.algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.logging.log4j.util.Strings;

import nts.uk.ctx.at.shared.dom.schedule.basicschedule.BasicScheduleService;
import nts.uk.ctx.at.shared.dom.worktime.common.AmPmAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.DeductionTime;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeDailyAtr;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktype.DailyWork;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;

@Stateless
public class CreScheWithBusinessDayCalService {
	
	@Inject
	private BasicScheduleService basicScheduleService;

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
			Map<String, WorkRestTimeZoneDto> mapDiffTimeWorkSetting, List<DeductionTime> listScheTimeZones) {
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
		boolean isHolidayWork = dailyWork.isHolidayWork();
		if (isHolidayWork) {
			// 取得したドメインモデルの「就業時間帯勤務区分. 勤務形態区分」を判断
			if (workTimeSetting.getWorkTimeDivision().getWorkTimeDailyAtr() == WorkTimeDailyAtr.FLEX_WORK) {
				// 今回対象外
				// TODO:
			} else {
				return determineSetWorkingHours(workTimeSetting, companyId, workTimeCode, isHolidayWork, 
						workTypeCode, listWorkType, mapFixedWorkSetting, mapFlowWorkSetting, mapDiffTimeWorkSetting, listScheTimeZones);
			}
		} else {
			// 就業時間帯の設定
			// 取得したドメインモデルの「就業時間帯勤務区分. 勤務形態区分」を判断
			if (workTimeSetting.getWorkTimeDivision().getWorkTimeDailyAtr() == WorkTimeDailyAtr.FLEX_WORK) {
				// ［フレックス勤務用］
				// TODO:
			} else {
				return determineSetWorkingHours(workTimeSetting, companyId, workTimeCode, isHolidayWork,
						workTypeCode, listWorkType, mapFixedWorkSetting, mapFlowWorkSetting, mapDiffTimeWorkSetting, listScheTimeZones);
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
	private BusinessDayCal determineSetWorkingHours(WorkTimeSetting workTimeSetting, String companyId,
			String workTimeCode, boolean isHoliday, String workTypeCode, List<WorkType> listWorkType,
			Map<String, WorkRestTimeZoneDto> mapFixedWorkSetting, Map<String, WorkRestTimeZoneDto> mapFlowWorkSetting,
			Map<String, WorkRestTimeZoneDto> mapDiffTimeWorkSetting, List<DeductionTime> listScheTimeZones) {
		
		final BusinessDayCal data;

		// 「就業時間帯勤務区分. 就業時間帯の設定方法」を判断
		// EA修正履歴　No2104
		switch (workTimeSetting.getWorkTimeDivision().getWorkTimeMethodSet()) {
		// ［固定勤務設定］
		case FIXED_WORK:
			WorkRestTimeZoneDto fixedWorkSettingDto = mapFixedWorkSetting.get(workTimeCode);
			
			if (fixedWorkSettingDto == null) {
				return null;
			}
			
			if (isHoliday) {
				// 固定勤務設定. 休日勤務時間帯. 休憩時間帯
				data = new BusinessDayCal(fixedWorkSettingDto.getListOffdayWorkTimezone().stream()
						.map(x -> new DeductionTime(x.getStart(), x.getEnd())).collect(Collectors.toList()));
			} else {
				// EA修正履歴　No2283
				// 「固定勤務設定. 平日勤務時間帯. 休憩時間帯」
				switch (this.basicScheduleService.checkWorkDayByList(workTypeCode, listWorkType)) {
				case MORNING_WORK:
					data = new BusinessDayCal(fixedWorkSettingDto.getListHalfDayWorkTimezone()
							.stream().filter(x -> x.getAmPmAtr() == AmPmAtr.AM)
							.map(y -> new DeductionTime(y.getStart(), y.getEnd())).collect(Collectors.toList()));
					break;
				case AFTERNOON_WORK:
					data = new BusinessDayCal(fixedWorkSettingDto.getListHalfDayWorkTimezone()
							.stream().filter(x -> x.getAmPmAtr() == AmPmAtr.PM)
							.map(y -> new DeductionTime(y.getStart(), y.getEnd())).collect(Collectors.toList()));
					break;
				case ONE_DAY_WORK:
					data = new BusinessDayCal(fixedWorkSettingDto.getListHalfDayWorkTimezone()
							.stream().filter(x -> x.getAmPmAtr() == AmPmAtr.ONE_DAY)
							.map(y -> new DeductionTime(y.getStart(), y.getEnd())).collect(Collectors.toList()));
					break;
				default:
					data = new BusinessDayCal(Collections.emptyList());
					break;
				}
				
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
				data = new BusinessDayCal(flowWorkSettingDto.getListOffdayWorkTimezone().stream()
						.map(x -> new DeductionTime(x.getStart(), x.getEnd())).collect(Collectors.toList()));
			} else {
				// EA修正履歴　No2283
				// 流動勤務設定. 平日勤務時間帯. 休憩時間帯. 固定休憩時間帯」
				data = new BusinessDayCal(flowWorkSettingDto.getListHalfDayWorkTimezone().stream()
						.map(x -> new DeductionTime(x.getStart(), x.getEnd())).collect(Collectors.toList()));
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
				data = new BusinessDayCal(diffTimeWorkSettingDto.getListOffdayWorkTimezone().stream()
						.map(x -> new DeductionTime(x.getStart(), x.getEnd())).collect(Collectors.toList()));
			} else {
				// EA修正履歴　No2283
				// 「時差勤務設定. 平日勤務時間帯. 休憩時間帯」
				switch (this.basicScheduleService.checkWorkDayByList(workTypeCode, listWorkType)) {
				case MORNING_WORK:
					data = new BusinessDayCal(diffTimeWorkSettingDto.getListHalfDayWorkTimezone()
							.stream().filter(x -> x.getAmPmAtr() == AmPmAtr.AM)
							.map(y -> new DeductionTime(y.getStart(), y.getEnd())).collect(Collectors.toList()));
					break;
				case AFTERNOON_WORK:
					data = new BusinessDayCal(diffTimeWorkSettingDto.getListHalfDayWorkTimezone()
							.stream().filter(x -> x.getAmPmAtr() == AmPmAtr.PM)
							.map(y -> new DeductionTime(y.getStart(), y.getEnd())).collect(Collectors.toList()));
					break;
				case ONE_DAY_WORK:
					data = new BusinessDayCal(diffTimeWorkSettingDto.getListHalfDayWorkTimezone()
							.stream().filter(x -> x.getAmPmAtr() == AmPmAtr.ONE_DAY)
							.map(y -> new DeductionTime(y.getStart(), y.getEnd())).collect(Collectors.toList()));
					break;
				default:
					data = new BusinessDayCal(Collections.emptyList());
					break;
				}
			}

			break;
			
		default:
			throw new RuntimeException("unknown value: " + workTimeSetting.getWorkTimeDivision().getWorkTimeMethodSet());
		}
		
		// 午前出勤系、午後出勤系の場合に、休憩時間帯の補正を行う
		DeductionTime timezoneK1 = listScheTimeZones.get(0);
		Optional<DeductionTime> timezoneK2 = (listScheTimeZones.size() == 2 ? Optional.of(listScheTimeZones.get(1)) : Optional.empty());

		List<DeductionTime> listDeductionTime =  new ArrayList<DeductionTime>();
		data.getTimezones().forEach(deductionTime -> {
			if(timezoneK2.isPresent()){
				this.setDataForListTimeZone(timezoneK2.get(), deductionTime, listDeductionTime);
			}
			this.setDataForListTimeZone(timezoneK1, deductionTime, listDeductionTime);
		});
		
		return new BusinessDayCal(listDeductionTime);
	}
	
	private void setDataForListTimeZone(DeductionTime timezone, DeductionTime deductionTime, List<DeductionTime> listDeductionTime){
		if(deductionTime.isBetweenOrEqual(timezone)){
			listDeductionTime.add(deductionTime);
		} else if(timezone.isBetweenOrEqual(deductionTime)){
			listDeductionTime.add(timezone);
		} else if(!timezone.contains(deductionTime.getStart()) && timezone.contains(deductionTime.getEnd())){
			listDeductionTime.add(new DeductionTime(timezone.getStart(), deductionTime.getEnd()));
		}  else if(timezone.contains(deductionTime.getStart()) && !timezone.contains(deductionTime.getEnd())){
			listDeductionTime.add(new DeductionTime(deductionTime.getStart(), timezone.getEnd()));
		}
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