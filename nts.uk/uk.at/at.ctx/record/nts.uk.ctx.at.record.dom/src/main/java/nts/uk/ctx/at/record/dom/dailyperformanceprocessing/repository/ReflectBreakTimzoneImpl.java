package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.output.BreakTimeZoneSettingOutPut;
import nts.uk.ctx.at.shared.dom.employmentrules.employmenttimezone.BreakTimeZoneService;
import nts.uk.ctx.at.shared.dom.employmentrules.employmenttimezone.BreakTimeZoneSharedOutPut;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.WorkStyle;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;

@Stateless
public class ReflectBreakTimzoneImpl implements BreakTimeZoneService {
	
	@Inject
	private ReflectBreakTimeOfDailyDomainService reflectBreakTimeOfDailyDomainService;

	@Inject
	private WorkTimeSettingRepository workTimeSettingRepo;

	@Override
	public BreakTimeZoneSharedOutPut getBreakTimeZone(String companyId, String workTimeCode,
			int weekdayHolidayClassification, WorkStyle checkWorkDay) {

		BreakTimeZoneSharedOutPut breakTimeZoneSharedOutPut = new BreakTimeZoneSharedOutPut();

		BreakTimeZoneSettingOutPut breakTimeZoneSettingOutPut = new BreakTimeZoneSettingOutPut();

		Optional<WorkTimeSetting> WorkTimeSettingOptional = this.workTimeSettingRepo.findByCode(companyId,
				workTimeCode);
		WorkTimeSetting workTimeSetting = WorkTimeSettingOptional.orElse(null);

		// weekdayHolidayClassification :
		// 平日 : 0 , 休日 : 1

		boolean checkReflect = false;
		if (workTimeSetting != null && workTimeSetting.getWorkTimeDivision().getWorkTimeDailyAtr().value == 0) {

			switch (workTimeSetting.getWorkTimeDivision().getWorkTimeMethodSet().value) {
			case 0:// 固定勤務
				checkReflect = reflectBreakTimeOfDailyDomainService.CheckBreakTimeFromFixedWorkSetting(companyId,
						weekdayHolidayClassification, workTimeCode, breakTimeZoneSettingOutPut, checkWorkDay);
				break;
			case 2:// 流動勤務
				checkReflect = reflectBreakTimeOfDailyDomainService.confirmIntermissionTimeZone(companyId,
						weekdayHolidayClassification, workTimeCode, breakTimeZoneSettingOutPut);

				break;
			case 1:// 時差勤務
//				checkReflect = reflectBreakTimeOfDailyDomainServiceImpl.ConfirmInterTimezoneStaggeredWorkSetting(
//						companyId, employeeID, processingDate, empCalAndSumExecLogID, weekdayHolidayClassification,
//						null, // WorkInfo ,
				// breakTimeZoneSettingOutPut, checkWorkDay);
				break;

			default:
				checkReflect = reflectBreakTimeOfDailyDomainService.CheckBreakTimeFromFixedWorkSetting(companyId,
						weekdayHolidayClassification, workTimeCode, breakTimeZoneSettingOutPut, checkWorkDay);
				break;
			}

		} else {
			checkReflect = reflectBreakTimeOfDailyDomainService.confirmInterFlexWorkSetting(companyId,
					weekdayHolidayClassification, workTimeCode, breakTimeZoneSettingOutPut, checkWorkDay);
		}

		breakTimeZoneSharedOutPut.setLstTimezone(breakTimeZoneSettingOutPut.getLstTimezone());

		return breakTimeZoneSharedOutPut;
	}

}
