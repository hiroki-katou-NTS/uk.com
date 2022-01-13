package nts.uk.ctx.at.schedule.app.command.workrequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.schedule.dom.shift.management.shifttable.ShiftTableRuleForCompany;
import nts.uk.ctx.at.schedule.dom.shift.management.shifttable.ShiftTableRuleForCompanyRepo;
import nts.uk.ctx.at.schedule.dom.shift.management.shifttable.ShiftTableRuleForOrganization;
import nts.uk.ctx.at.schedule.dom.shift.management.shifttable.ShiftTableRuleForOrganizationRepo;
import nts.uk.ctx.at.schedule.dom.shift.management.workavailability.AssignmentMethod;
import nts.uk.ctx.at.schedule.dom.shift.management.workavailability.RegisterWorkAvailability;
import nts.uk.ctx.at.schedule.dom.shift.management.workavailability.WorkAvailabilityMemo;
import nts.uk.ctx.at.schedule.dom.shift.management.workavailability.WorkAvailabilityOfOneDay;
import nts.uk.ctx.at.schedule.dom.shift.management.workavailability.WorkAvailabilityOfOneDayRepository;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.BasicScheduleService;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.SetupType;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.adapter.EmpAffiliationInforAdapter;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.adapter.EmpOrganizationImport;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMaster;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMasterCode;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMasterRepository;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
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
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * «Command» 勤務希望を提出する
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.シフト管理.シフト勤務.勤務希望.App.勤務希望を提出する
 * @author tutk
 *
 */

@Stateless
public class SubmitWorkRequest extends CommandHandler<SubmitWorkRequestCmd> {

	@Inject
	private WorkTypeRepository workTypeRepo;

	@Inject
	private WorkTimeSettingRepository workTimeSettingRepository;

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

	//
	@Inject
	private ShiftTableRuleForOrganizationRepo shiftTableRuleForOrganizationRepo;

	@Inject
	private ShiftTableRuleForCompanyRepo shiftTableRuleForCompanyRepo;

	@Inject
	private EmpAffiliationInforAdapter empAffiliationInforAdapter;

	@Inject
	private WorkAvailabilityOfOneDayRepository workAvailabilityOfOneDayRepo;

	@Override
	protected void handle(CommandHandlerContext<SubmitWorkRequestCmd> context) {

		SubmitWorkRequestCmd command = context.getCommand();
		String companyId = AppContexts.user().companyId();
		String employeeId = AppContexts.user().employeeId();
		WorkAvailabilityOfOneDay.Require requireWorkAvailabilityOfOneDay = new RequireWorkAvailabilityOfOneDayImpl(
				workTypeRepo, workTimeSettingRepository, basicScheduleService, shiftMasterRepo,
				fixedWorkSettingRepository, flowWorkSettingRepository, flexWorkSettingRepository,
				predetemineTimeSettingRepository);
		// 1:
		List<WorkAvailabilityOfOneDay> listDomain = new ArrayList<>();
		for (DataOneDateScreenKsuS02 oneDay : command.getListData()) {
			List<ShiftMasterCode> listShift = oneDay
					.getNameList().stream().map(c -> new ShiftMasterCode(c)).collect(Collectors
							.toList());
			if(!(oneDay.getAssignmentMethod()==AssignmentMethod.SHIFT.value && listShift.isEmpty())) {
				WorkAvailabilityOfOneDay domain = WorkAvailabilityOfOneDay
						.create(requireWorkAvailabilityOfOneDay, employeeId,
								GeneralDate.fromString(oneDay.getDate(), "yyyy/MM/dd"),
								new WorkAvailabilityMemo(oneDay.getMemo()),
								AssignmentMethod.of(oneDay.getAssignmentMethod()), 
								listShift,
								oneDay.getTimeZoneList().stream()
										.map(c -> new TimeSpanForCalc(new TimeWithDayAttr(c.getStartTime()),
												new TimeWithDayAttr(c.getEndTime())))
										.collect(Collectors.toList()));
				listDomain.add(domain);
			}
		}
		RegisterWorkAvailability.Require requireRegisterWorkAvailability = new RequireRegisterWorkAvailabilityImpl(
				shiftTableRuleForOrganizationRepo, shiftTableRuleForCompanyRepo, empAffiliationInforAdapter,
				workAvailabilityOfOneDayRepo, workTimeSettingRepository, workTypeRepo, basicScheduleService,
				shiftMasterRepo, fixedWorkSettingRepository, flowWorkSettingRepository, flexWorkSettingRepository,
				predetemineTimeSettingRepository);
		// 2:
		AtomTask persist = RegisterWorkAvailability.register(requireRegisterWorkAvailability,companyId,employeeId,
				new DatePeriod(GeneralDate.fromString(command.getStartPeriod(), "yyyy/MM/dd"),
						GeneralDate.fromString(command.getEndPeriod(), "yyyy/MM/dd")), listDomain);

		// 3:
		transaction.execute(() -> {
			persist.run();
		});

	}

	@AllArgsConstructor
	private static class RequireWorkAvailabilityOfOneDayImpl implements WorkAvailabilityOfOneDay.Require {

		private final String companyId = AppContexts.user().companyId();

		@Inject
		private WorkTypeRepository workTypeRepo;

		@Inject
		private WorkTimeSettingRepository workTimeSettingRepository;

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

	@AllArgsConstructor
	private static class RequireRegisterWorkAvailabilityImpl implements RegisterWorkAvailability.Require {

		private final String companyId = AppContexts.user().companyId();

		@Inject
		private ShiftTableRuleForOrganizationRepo shiftTableRuleForOrganizationRepo;

		@Inject
		private ShiftTableRuleForCompanyRepo shiftTableRuleForCompanyRepo;

		@Inject
		private EmpAffiliationInforAdapter empAffiliationInforAdapter;

		@Inject
		private WorkAvailabilityOfOneDayRepository workAvailabilityOfOneDayRepo;
		
		@Inject
		private WorkTimeSettingRepository workTimeSettingRepository;
		
		@Inject
		private WorkTypeRepository workTypeRepo;
		
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
		public List<EmpOrganizationImport> getEmpOrganization(GeneralDate referenceDate, List<String> listEmpId) {
			return empAffiliationInforAdapter.getEmpOrganization(referenceDate, listEmpId);
		}

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

		@Override
		public void insertAllWorkAvailabilityOfOneDay(List<WorkAvailabilityOfOneDay> workOneDays) {
			workAvailabilityOfOneDayRepo.insertAll(workOneDays);

		}

		@Override
		public void deleteAllWorkAvailabilityOfOneDay(String sid, DatePeriod datePeriod) {
			workAvailabilityOfOneDayRepo.deleteAll(sid, datePeriod);

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
		public SetupType checkNeededOfWorkTimeSetting(String workTypeCode) {
			return basicScheduleService.checkNeededOfWorkTimeSetting(workTypeCode);
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
		public Optional<ShiftMaster> getShiftMasterByWorkInformation(WorkTypeCode workTypeCode,
				WorkTimeCode workTimeCode) {
			return shiftMasterRepo.getByWorkTypeAndWorkTime(companyId, workTypeCode.v(),
					workTimeCode.v());
		}

		@Override
		public List<ShiftMaster> getShiftMaster(List<ShiftMasterCode> shiftMasterCodeList) {
			return shiftMasterRepo.getByListShiftMaterCd2(companyId,
					shiftMasterCodeList.stream().map(c -> c.v()).collect(Collectors.toList()));
		}

		@Override
		public boolean shiftMasterIsExist(ShiftMasterCode shiftMasterCode) {
			return shiftMasterRepo.checkExistsByCd(companyId, shiftMasterCode.v());
		}

		@Override
		public boolean existWorkAvailabilityOfOneDay(String sid, GeneralDate date) {
			return workAvailabilityOfOneDayRepo.exists(sid, date);
		}

	}

}
