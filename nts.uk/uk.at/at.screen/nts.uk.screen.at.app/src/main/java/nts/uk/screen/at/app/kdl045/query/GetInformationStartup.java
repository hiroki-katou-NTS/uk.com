package nts.uk.screen.at.app.kdl045.query;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.app.find.dailyperform.dto.TimeSpanForCalcDto;
import nts.uk.ctx.at.record.dom.require.RecordDomRequireService;
import nts.uk.ctx.at.schedule.dom.shift.management.shifttable.GetShiftTableRuleForOrganizationService;
import nts.uk.ctx.at.schedule.dom.shift.management.shifttable.ShiftTableRule;
//import nts.uk.ctx.at.schedule.dom.shift.management.shifttable.GetShiftTableRuleForOrganizationService.Require;
import nts.uk.ctx.at.schedule.dom.shift.management.shifttable.ShiftTableRuleForCompany;
import nts.uk.ctx.at.schedule.dom.shift.management.shifttable.ShiftTableRuleForCompanyRepo;
import nts.uk.ctx.at.schedule.dom.shift.management.shifttable.ShiftTableRuleForOrganization;
import nts.uk.ctx.at.schedule.dom.shift.management.shifttable.ShiftTableRuleForOrganizationRepo;
import nts.uk.ctx.at.schedule.dom.shift.management.workavailability.WorkAvailabilityDisplayInfoOfOneDay;
import nts.uk.ctx.at.schedule.dom.shift.management.workavailability.WorkAvailabilityOfOneDay;
import nts.uk.ctx.at.schedule.dom.shift.management.workavailability.WorkAvailabilityOfOneDayRepository;
import nts.uk.ctx.at.shared.app.find.worktime.common.dto.WorkTimezoneCommonSetDto;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.BasicScheduleService;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.SetupType;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.WorkStyle;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMaster;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMasterCode;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMasterRepository;
import nts.uk.ctx.at.shared.dom.worktime.algorithm.getcommonset.GetCommonSet;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSet;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingService;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.internal.PredetermineTimeSetForCalc;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.screen.at.app.ksu001.getinfoofInitstartup.TargetOrgIdenInforDto;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * «ScreenQuery» 初期起動情報を取得する
 * UKDesign.UniversalK.就業.KDL_ダイアログ.KDL045_詳細設定ダイアログ.メニュー別OCD.初期起動情報を取得する
 * 
 * @author tutk
 *
 */
@Stateless
public class GetInformationStartup {

	@Inject
	private RecordDomRequireService requireService;
	//
	@Inject
	private ShiftTableRuleForOrganizationRepo shiftTableRuleForOrganizationRepo;

	@Inject
	private ShiftTableRuleForCompanyRepo shiftTableRuleForCompanyRepo;

	@Inject
	private WorkAvailabilityOfOneDayRepository workAvailabilityOfOneDayRepo;

	@Inject
	private WorkTypeRepository workTypeRepo;

	@Inject
	private WorkTimeSettingRepository workTimeSettingRepository;

	@Inject
	private WorkTimeSettingService workTimeSettingService;

	@Inject
	private BasicScheduleService basicScheduleService;

	@Inject
	private ShiftMasterRepository shiftMasterRepo;

	public GetInformationStartupOutput getInformationStartup(String employeeId, GeneralDate baseDate,
			List<TimeVacationAndType> listTimeVacationAndType, String workTimeCode,
			TargetOrgIdenInforDto targetOrgIdenInforDto) {
		GetInformationStartupOutput data = new GetInformationStartupOutput();
		String companyId = AppContexts.user().companyId();
		val require = requireService.createRequire();

		// 1 : 共通設定の取得
		Optional<WorkTimezoneCommonSet> optWorktimezone = GetCommonSet.workTimezoneCommonSet(require, companyId,
				workTimeCode);
		WorkTimezoneCommonSetDto workTimezoneCommonSetDto = new WorkTimezoneCommonSetDto();
		if (!optWorktimezone.isPresent()) {
			data.setWorkTimezoneCommonSet(null);
		} else {
			optWorktimezone.get().saveToMemento(workTimezoneCommonSetDto);
			data.setWorkTimezoneCommonSet(workTimezoneCommonSetDto);
		}

		List<UsageTimeAndType> listUsageTimeAndType = new ArrayList<>();
		// 2 : 合計使用時間()
		for (TimeVacationAndType timeVacationAndType : listTimeVacationAndType) {
			int total = 0;
			for (DailyAttdTimeVacationDto dailyAttdTimeVacationDto : timeVacationAndType.getTimeVacation()
					.getUsageTime()) {
				total = total + dailyAttdTimeVacationDto.getTimeAbbyakLeave().intValue()
						+ dailyAttdTimeVacationDto.getTimeOff().intValue()
						+ dailyAttdTimeVacationDto.getExcessPaidHoliday().intValue()
						+ dailyAttdTimeVacationDto.getSpecialHoliday().intValue()
						+ dailyAttdTimeVacationDto.getChildNursingLeave().intValue()
						+ dailyAttdTimeVacationDto.getNursingCareLeave().intValue();
			}
			listUsageTimeAndType.add(new UsageTimeAndType(timeVacationAndType.getTypeVacation(), total));
		}
		data.setListUsageTimeAndType(listUsageTimeAndType);

		// 3:取得する(Require, 対象組織識別情報)
		GetShiftTableRuleForOrganizationService.Require requireGetShiftTableRule = new RequireImpl(
				shiftTableRuleForOrganizationRepo, shiftTableRuleForCompanyRepo);
		Optional<ShiftTableRule> otpShiftTableRule = GetShiftTableRuleForOrganizationService
				.get(requireGetShiftTableRule, targetOrgIdenInforDto.convertFromDomain());
		data.setShowYourDesire(0);
		if (otpShiftTableRule.isPresent() && otpShiftTableRule.get().getUseWorkAvailabilityAtr() == NotUseAtr.USE) {
			data.setShowYourDesire(1);
		}
		// 4:
		Optional<WorkAvailabilityOfOneDay> workAvailabilityOfOneDayOpt = workAvailabilityOfOneDayRepo.get(employeeId,
				baseDate);
		if (workAvailabilityOfOneDayOpt.isPresent()) {
			WorkAvailabilityOfOneDay.Require requireWorkAvailabilityOfOneDay = new RequireWorkAvailabilityOfOneDayImpl(
					workTypeRepo, workTimeSettingRepository, workTimeSettingService, basicScheduleService,
					shiftMasterRepo);
			WorkAvailabilityDisplayInfoOfOneDay WorkAvailabilityDisplayInfoOfOneDay = workAvailabilityOfOneDayOpt.get()
					.getDisplayInformation(requireWorkAvailabilityOfOneDay);
			data.setWorkAvaiOfOneDayDto(convertToDomain(WorkAvailabilityDisplayInfoOfOneDay));
		} else {
			data.setWorkAvaiOfOneDayDto(null);
		}
		return data;
	}

	private WorkAvailabilityOfOneDayDto convertToDomain(WorkAvailabilityDisplayInfoOfOneDay domain) {
		return new WorkAvailabilityOfOneDayDto(domain.getEmployeeId(), domain.getAvailabilityDate(),
				domain.getMemo().v(),
				new WorkAvailabilityDisplayInfoDto(domain.getDisplayInfo().getMethod().value,
						domain.getDisplayInfo().getNameList(),
						domain.getDisplayInfo().getTimeZoneList().stream()
								.map(c -> new TimeSpanForCalcDto(c.getStart().v(), c.getEnd().v()))
								.collect(Collectors.toList())));
	}

	@AllArgsConstructor
	private static class RequireImpl implements GetShiftTableRuleForOrganizationService.Require {
		private final String companyId = AppContexts.user().companyId();

		@Inject
		private ShiftTableRuleForOrganizationRepo shiftTableRuleForOrganizationRepo;

		@Inject
		private ShiftTableRuleForCompanyRepo shiftTableRuleForCompanyRepo;

		@Override
		public Optional<ShiftTableRuleForOrganization> getOrganizationShiftTable(TargetOrgIdenInfor targetOrg) {
			Optional<ShiftTableRuleForOrganization> data = shiftTableRuleForOrganizationRepo.get(companyId, targetOrg);
			return data;
		}

		@Override
		public Optional<ShiftTableRuleForCompany> getCompanyShiftTable() {
			Optional<ShiftTableRuleForCompany> data = shiftTableRuleForCompanyRepo.get(companyId);
			return data;
		}
	}

	@AllArgsConstructor
	private static class RequireWorkAvailabilityOfOneDayImpl implements WorkAvailabilityOfOneDay.Require {

		private final String companyId = AppContexts.user().companyId();

		@Inject
		private WorkTypeRepository workTypeRepo;

		@Inject
		private WorkTimeSettingRepository workTimeSettingRepository;

		@Inject
		private WorkTimeSettingService workTimeSettingService;

		@Inject
		private BasicScheduleService basicScheduleService;

		@Inject
		private ShiftMasterRepository shiftMasterRepo;

		@Override
		public Optional<WorkType> findByPK(String workTypeCd) {
			return workTypeRepo.findByPK(companyId, workTypeCd);
		}

		@Override
		public Optional<WorkTimeSetting> findByCode(String workTimeCode) {
			return workTimeSettingRepository.findByCode(companyId, workTimeCode);
		}

		@Override
		public SetupType checkNeededOfWorkTimeSetting(String workTypeCode) {
			return basicScheduleService.checkNeededOfWorkTimeSetting(workTypeCode);
		}

		@Override
		public PredetermineTimeSetForCalc getPredeterminedTimezone(String workTimeCd, String workTypeCd,
				Integer workNo) {
			return workTimeSettingService.getPredeterminedTimezone(companyId, workTimeCd, workTypeCd, workNo);
		}

		@Override
		public WorkStyle checkWorkDay(String workTypeCode) {
			return basicScheduleService.checkWorkDay(workTypeCode);
		}

		@Override
		public Optional<ShiftMaster> getShiftMasterByWorkInformation(WorkTypeCode workTypeCode,
				WorkTimeCode workTimeCode) {
			Optional<ShiftMaster> data = shiftMasterRepo.getByWorkTypeAndWorkTime(companyId, workTypeCode.v(),
					workTimeCode.v());
			return data;
		}

		@Override
		public List<ShiftMaster> getShiftMaster(List<ShiftMasterCode> shiftMasterCodeList) {
			List<ShiftMaster> data = shiftMasterRepo.getByListShiftMaterCd2(companyId,
					shiftMasterCodeList.stream().map(c -> c.v()).collect(Collectors.toList()));
			return data;
		}
	}

}
