package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.output.BreakTimeZoneSettingOutPut;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.output.StampReflectTimezoneOutput;
import nts.uk.ctx.at.record.dom.workinformation.ScheduleTimeSheet;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.BasicScheduleService;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.WorkStyle;
import nts.uk.ctx.at.shared.dom.workingcondition.SingleDaySchedule;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemService;
import nts.uk.ctx.at.shared.dom.worktime.common.DeductionTime;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixHalfDayWorkTimezone;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixOffdayWorkTimezone;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixRestTimezoneSet;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowRestTimezone;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;

@Stateless
public class ReflectBreakTimeOfDailyDomainServiceImpl implements ReflectBreakTimeOfDailyDomainService {

	@Inject
	private BasicScheduleService basicScheduleService;
	@Inject
	private WorkingConditionItemService workingConditionItemService;
	@Inject
	private WorkTimeSettingRepository workTimeSettingRepo;
	@Inject
	private FixedWorkSettingRepository fixedWorkSettingRepo;
	@Inject
	private FlowWorkSettingRepository flowWorkSettingRep;
	@Inject
	private FlexWorkSettingRepository flexWorkSettingRepo;

	@Override
	public void reflectBreakTime(String companyId, List<StampReflectTimezoneOutput> lstStampReflectTimezone,
			WorkInfoOfDailyPerformance WorkInfo) {
		BreakTimeZoneSettingOutPut breakTimeZoneSettingOutPut = new BreakTimeZoneSettingOutPut();
		// 休憩時間帯設定を確認する
		this.checkBreakTimeSetting(companyId, WorkInfo, breakTimeZoneSettingOutPut);
	}

	// 休憩時間帯設定を確認する
	public boolean checkBreakTimeSetting(String companyId, WorkInfoOfDailyPerformance WorkInfo,
			BreakTimeZoneSettingOutPut breakTimeZoneSettingOutPut) {
		// fixed thieu reset breaktime
		boolean checkReflect = false;
		WorkStyle checkWorkDay = this.basicScheduleService
				.checkWorkDay(WorkInfo.getRecordWorkInformation().getWorkTypeCode().v());
		if (checkWorkDay.value == 0) {
			return false;
		} else {
			// Optional<SingleDaySchedule> holidayWorkScheduleOptional =
			// this.workingConditionItemService.getHolidayWorkSchedule(companyId,
			// WorkInfo.getEmployeeId(), WorkInfo.getYmd(),
			// WorkInfo.getRecordWorkInformation().getWorkTypeCode().v());

			// 1* Lấy 休日出勤時の勤務情報 trả về 勤務情報なし , 公休出勤時 ....
			// (fixed)
			String result = "勤務情報なし";
			// 1*

			String weekdayHolidayClassification = null;
			if ("勤務情報なし".equals(result)) {
				weekdayHolidayClassification = "平日";
			} else {
				weekdayHolidayClassification = "休日";
			}
			Optional<WorkTimeSetting> WorkTimeSettingOptional = this.workTimeSettingRepo.findByCode(companyId,
					WorkInfo.getRecordWorkInformation().getWorkTimeCode().v());
			WorkTimeSetting workTimeSetting = WorkTimeSettingOptional.get();
			// WorkTimeDailyAtr = 通常勤務・変形労働用
			if (workTimeSetting.getWorkTimeDivision().getWorkTimeDailyAtr().value == 0) {

				switch (workTimeSetting.getWorkTimeDivision().getWorkTimeMethodSet().value) {
				case 0:// 固定勤務
					checkReflect = this.CheckBreakTimeFromFixedWorkSetting(companyId, weekdayHolidayClassification,
							WorkInfo.getRecordWorkInformation().getWorkTimeCode().v(), breakTimeZoneSettingOutPut);
					break;
				case 1:// 時差勤務
					checkReflect = ConfirmInterTimezoneStaggeredWorkSetting(companyId, weekdayHolidayClassification,
							WorkInfo, breakTimeZoneSettingOutPut);
					break;
				case 2:// 流動勤務
					checkReflect = this.confirmIntermissionTimeZone(companyId, weekdayHolidayClassification,
							WorkInfo.getRecordWorkInformation().getWorkTimeCode().v(), breakTimeZoneSettingOutPut);

					break;
				default:
					checkReflect = this.CheckBreakTimeFromFixedWorkSetting(companyId, weekdayHolidayClassification,
							WorkInfo.getRecordWorkInformation().getWorkTimeCode().v(), breakTimeZoneSettingOutPut);
					break;
				}

			} else {
				checkReflect = this.confirmInterFlexWorkSetting(companyId,
						weekdayHolidayClassification, WorkInfo.getRecordWorkInformation().getWorkTimeCode().v(),
						breakTimeZoneSettingOutPut);
			}

		}
		
		return checkReflect;
	}

	// フレックス勤務設定から休憩時間帯を確認する
	public boolean confirmInterFlexWorkSetting(String companyId, String weekdayHolidayClassification,
			String workTimeCode, BreakTimeZoneSettingOutPut breakTimeZoneSettingOutPut) {
		Optional<FlexWorkSetting> FlexWorkSettingOptional = this.flexWorkSettingRepo.find(companyId, workTimeCode);
		FlexWorkSetting flexWorkSetting = FlexWorkSettingOptional.get();

		boolean fixRestTime = true;
		if ("平日".equals(weekdayHolidayClassification)) {
			// fixRestTime =
			// flexWorkSetting.getLstHalfDayWorkTimezone().getRestTimezone().isFixRestTime();
			// lstTimezone =
			// flowWorkSetting.getHalfDayWorkTimezone().getRestTimezone().getFixedRestTimezone()
			// .getTimezones();
		} else {
			// fixRestTime =
			// flowWorkSetting.getOffdayWorkTimezone().getRestTimeZone().isFixRestTime();
			// lstTimezone =
			// flowWorkSetting.getOffdayWorkTimezone().getRestTimeZone().getFixedRestTimezone()
			// .getTimezones();
		}
		if (fixRestTime) {
			// breakTimeZoneSettingOutPut.setLstTimezone(lstTimezone);
			// return true;
		}
		// fixed case nay LstTimezone co the null (confirm?)
		return false;

	}

	// 時差勤務設定から休憩時間帯を確認する
	public boolean ConfirmInterTimezoneStaggeredWorkSetting(String companyId, String weekdayHolidayClassification,
			WorkInfoOfDailyPerformance WorkInfo, BreakTimeZoneSettingOutPut breakTimeZoneSettingOutPut) {

		if (WorkInfo != null && WorkInfo.getScheduleTimeSheets() != null
				&& !WorkInfo.getScheduleTimeSheets().isEmpty()) {
			boolean workNoIsOne = false;
			List<ScheduleTimeSheet> scheduleTimeSheets = WorkInfo.getScheduleTimeSheets();
			int size = scheduleTimeSheets.size();
			for (int i = 0; i < size; i++) {
				ScheduleTimeSheet scheduleTimeSheet = scheduleTimeSheets.get(i);
				if (scheduleTimeSheet.getWorkNo().v() == 1) {
					workNoIsOne = true;
					break;
				}
			}
			if (workNoIsOne) {

			}
		}
		return false;
	}

	// 流動勤務設定から休憩時間帯を確認する
	public boolean confirmIntermissionTimeZone(String companyId, String weekdayHolidayClassification,
			String workTimeCode, BreakTimeZoneSettingOutPut breakTimeZoneSettingOutPut) {
		List<DeductionTime> lstTimezone;
		Optional<FlowWorkSetting> FlowWorkSettingoptional = this.flowWorkSettingRep.find(companyId, workTimeCode);
		FlowWorkSetting flowWorkSetting = FlowWorkSettingoptional.get();

		boolean fixRestTime;
		if ("平日".equals(weekdayHolidayClassification)) {
			fixRestTime = flowWorkSetting.getHalfDayWorkTimezone().getRestTimezone().isFixRestTime();
			lstTimezone = flowWorkSetting.getHalfDayWorkTimezone().getRestTimezone().getFixedRestTimezone()
					.getTimezones();
		} else {
			fixRestTime = flowWorkSetting.getOffdayWorkTimezone().getRestTimeZone().isFixRestTime();
			lstTimezone = flowWorkSetting.getOffdayWorkTimezone().getRestTimeZone().getFixedRestTimezone()
					.getTimezones();
		}
		if (fixRestTime) {
			breakTimeZoneSettingOutPut.setLstTimezone(lstTimezone);
			return true;
		}
		// fixed case nay LstTimezone co the null (confirm?)
		return false;
	}

	// 固定勤務設定から休憩時間帯を確認する
	public boolean CheckBreakTimeFromFixedWorkSetting(String companyId, String weekdayHolidayClassification,
			String workTimeCode, BreakTimeZoneSettingOutPut breakTimeZoneSettingOutPut) {

		List<DeductionTime> lstTimezone = new ArrayList<DeductionTime>();
		Optional<FixedWorkSetting> FixedWorkSettingOptional = this.fixedWorkSettingRepo.findByKey(companyId,
				workTimeCode);
		// check null?
		FixedWorkSetting fixedWorkSetting = FixedWorkSettingOptional.get();
		if ("平日".equals(weekdayHolidayClassification)) {
			List<FixHalfDayWorkTimezone> lstHalfDayWorkTimezone = fixedWorkSetting.getLstHalfDayWorkTimezone();
			for (FixHalfDayWorkTimezone fixHalfDayWorkTimezone : lstHalfDayWorkTimezone) {
				List<DeductionTime> timezones = fixHalfDayWorkTimezone.getRestTimezone().getLstTimezone();
				lstTimezone.addAll(timezones);
			}
		} else {
			List<DeductionTime> timezones = fixedWorkSetting.getOffdayWorkTimezone().getRestTimezone().getLstTimezone();
			lstTimezone.addAll(timezones);
		}
		breakTimeZoneSettingOutPut.setLstTimezone(lstTimezone);
		return true;
	}

}
