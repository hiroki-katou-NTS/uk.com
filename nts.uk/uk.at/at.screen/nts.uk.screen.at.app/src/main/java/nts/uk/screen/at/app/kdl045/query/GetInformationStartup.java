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
import nts.uk.ctx.at.shared.app.find.workrule.shiftmaster.TargetOrgIdenInforDto;
import nts.uk.ctx.at.shared.app.find.worktime.common.dto.WorkTimezoneCommonSetDto;
import nts.uk.ctx.at.shared.dom.WorkInformation;
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
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingService;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.screen.at.app.kdl045.query.GetMoreInformation.WorkInformationImpl;
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
	
	@Inject
	private FixedWorkSettingRepository fixedWorkSettingRepository;
	
	@Inject
	private FlowWorkSettingRepository flowWorkSettingRepository;
	
	@Inject
	private FlexWorkSettingRepository flexWorkSettingRepository;
	
	@Inject
	private PredetemineTimeSettingRepository predetemineTimeSettingRepository;

	public GetInformationStartupOutput getInformationStartup(String employeeId, GeneralDate baseDate,
			List<TimeVacationAndType> listTimeVacationAndType, String workTimeCode,String workTypeCode,
			TargetOrgIdenInforDto targetOrgIdenInforDto) {
		GetInformationStartupOutput data = new GetInformationStartupOutput();
		String companyId = AppContexts.user().companyId();
		val require = requireService.createRequire();
		
		if(workTimeCode !=null) {
			// 1.1 : 共通設定の取得
			Optional<WorkTimezoneCommonSet> optWorktimezone = GetCommonSet.workTimezoneCommonSet(require, companyId,
					workTimeCode);
			WorkTimezoneCommonSetDto workTimezoneCommonSetDto = new WorkTimezoneCommonSetDto();
			if (optWorktimezone.isPresent()) {
				optWorktimezone.get().saveToMemento(workTimezoneCommonSetDto);
				data.setWorkTimezoneCommonSet(workTimezoneCommonSetDto);
			}
			//1.2 : 会社を指定し就業時間帯を取得する
			Optional<WorkTimeSetting> optWorkTimeSetting = workTimeSettingRepository.findByCode(companyId, workTimeCode);
			if(optWorkTimeSetting.isPresent()) {
				data.setWorkTimeSettingName(new WorkTimeSettingNameDto(
						optWorkTimeSetting.get().getWorkTimeDisplayName().getWorkTimeName().v(),
						optWorkTimeSetting.get().getWorkTimeDisplayName().getWorkTimeAbName().v()));
			}
		}
		
		if (workTypeCode != null) {
			// 2.1 :
			Optional<WorkType> workType = workTypeRepo.findByPK(companyId, workTypeCode);
			data.setWorkTypeSettingName(new WorkTypeSettingNameDto(workType.get().getName().v(), workType.get().getAbbreviationName().v()));
			// 2.2 :
			WorkInformation wi = new WorkInformation(workTypeCode, workTimeCode);
			WorkInformation.Require requireWorkInfo = new WorkInformationImpl(workTypeRepo, workTimeSettingRepository,
					workTimeSettingService, basicScheduleService, fixedWorkSettingRepository, flowWorkSettingRepository,
					flexWorkSettingRepository, predetemineTimeSettingRepository);
			//2.3 : 
			Optional<WorkStyle> workStyle = wi.getWorkStyle(requireWorkInfo, companyId);
			data.setWorkStyle(workStyle.isPresent() ? workStyle.get().value : null);
		}
		// 3 : Map<時間休暇種類, 合計使用時間>
		List<UsageTimeAndType> listUsageTimeAndType = new ArrayList<>();
		// 3.1 : 合計使用時間の計算()
		for (TimeVacationAndType timeVacationAndType : listTimeVacationAndType) {
			int total = timeVacationAndType.getTimeVacation().getUsageTime().totalVacationAddTime();
			listUsageTimeAndType.add(new UsageTimeAndType(timeVacationAndType.getTypeVacation(), total));
		}
		data.setListUsageTimeAndType(listUsageTimeAndType);

		// 4:取得する(Require, 対象組織識別情報)
		GetShiftTableRuleForOrganizationService.Require requireGetShiftTableRule = new RequireImpl(
				shiftTableRuleForOrganizationRepo, shiftTableRuleForCompanyRepo);
		Optional<ShiftTableRule> otpShiftTableRule = GetShiftTableRuleForOrganizationService
				.get(requireGetShiftTableRule, targetOrgIdenInforDto.convertFromDomain());
		data.setShowYourDesire(0);
		//5 : 希望を表示するか==true
		if (otpShiftTableRule.isPresent() && otpShiftTableRule.get().getUseWorkAvailabilityAtr() == NotUseAtr.USE) {
			data.setShowYourDesire(1);
		}
		// 6:
		Optional<WorkAvailabilityOfOneDay> workAvailabilityOfOneDayOpt = workAvailabilityOfOneDayRepo.get(employeeId,
				baseDate);
		if (workAvailabilityOfOneDayOpt.isPresent()) {
			WorkAvailabilityOfOneDay.Require requireWorkAvailabilityOfOneDay = new RequireWorkAvailabilityOfOneDayImpl(
					workTypeRepo, workTimeSettingRepository, workTimeSettingService, basicScheduleService,
					shiftMasterRepo,fixedWorkSettingRepository,flowWorkSettingRepository,flexWorkSettingRepository,predetemineTimeSettingRepository);
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
						domain.getDisplayInfo().getShiftList().values().stream()
						.filter(c -> c.isPresent())
						.map(c -> c.get())
						.collect(Collectors.toList()),
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
		
		@Inject
		private FixedWorkSettingRepository fixedWorkSettingRepository;
		
		@Inject
		private FlowWorkSettingRepository flowWorkSettingRepository;
		
		@Inject
		private FlexWorkSettingRepository flexWorkSettingRepository;
		
		@Inject
		private PredetemineTimeSettingRepository predetemineTimeSettingRepository;


		@Override
		public SetupType checkNeededOfWorkTimeSetting(String workTypeCode) {
			return basicScheduleService.checkNeededOfWorkTimeSetting(workTypeCode);
		}

// fix bug 113211
//		@Override
//		public PredetermineTimeSetForCalc getPredeterminedTimezone(String workTimeCd, String workTypeCd,
//				Integer workNo) {
//			return workTimeSettingService.getPredeterminedTimezone(companyId, workTimeCd, workTypeCd, workNo);
//		}

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

		@Override
		public Optional<WorkType> workType(String companyId, WorkTypeCode workTypeCode) {
			return workTypeRepo.findByPK(companyId, workTypeCode.v());
		}

		@Override
		public Optional<WorkTimeSetting> workTimeSetting(String companyId, WorkTimeCode workTimeCode) {
			return workTimeSettingRepository.findByCode(companyId, workTimeCode.v());
		}

		@Override
		public Optional<FixedWorkSetting> fixedWorkSetting(String companyId, WorkTimeCode workTimeCode) {
			return fixedWorkSettingRepository.findByKey(companyId, workTimeCode.v());
		}

		@Override
		public Optional<FlowWorkSetting> flowWorkSetting(String companyId, WorkTimeCode workTimeCode) {
			return flowWorkSettingRepository.find(companyId, workTimeCode.v());
		}

		@Override
		public Optional<FlexWorkSetting> flexWorkSetting(String companyId, WorkTimeCode workTimeCode) {
			return flexWorkSettingRepository.find(companyId, workTimeCode.v());
		}

		@Override
		public Optional<PredetemineTimeSetting> predetemineTimeSetting(String companyId, WorkTimeCode workTimeCode) {
			return predetemineTimeSettingRepository.findByWorkTimeCode(companyId, workTimeCode.v());
		}

		@Override
		public boolean shiftMasterIsExist(ShiftMasterCode shiftMasterCode) {
			return shiftMasterRepo.checkExistsByCd(companyId, shiftMasterCode.v());
		}
	}

}
