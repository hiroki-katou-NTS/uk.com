package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.createdailyresults;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.creationprocess.CreatingDailyResultsCondition;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.creationprocess.CreatingDailyResultsConditionRepository;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.creationprocess.getperiodcanprocesse.AchievementAtr;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.creationprocess.getperiodcanprocesse.GetPeriodCanProcesse;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.creationprocess.getperiodcanprocesse.IgnoreFlagDuringLock;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.ExecutionTypeDaily;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.createdailyresults.reflectedperiod.ReflectedAtr;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.createdailyresults.reflectedperiod.ReflectedPeriod;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.createdailyresults.reflectedperiod.ReflectedPeriodOutput;
import nts.uk.ctx.at.record.dom.organization.EmploymentHistoryImported;
import nts.uk.ctx.at.record.dom.organization.adapter.EmploymentAdapter;
import nts.uk.ctx.at.record.dom.workrecord.actuallock.ActualLock;
import nts.uk.ctx.at.record.dom.workrecord.actuallock.ActualLockRepository;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.EmpCalAndSumExeLog;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmpEmployeeAdapter;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmployeeImport;
import nts.uk.ctx.at.shared.dom.adapter.generalinfo.dtoimport.EmployeeGeneralInfoImport;
import nts.uk.ctx.at.shared.dom.dailyperformanceprocessing.ErrMessageResource;
import nts.uk.ctx.at.shared.dom.dailyperformanceprocessing.output.PeriodInMasterList;
import nts.uk.ctx.at.shared.dom.scherec.closurestatus.ClosureStatusManagement;
import nts.uk.ctx.at.shared.dom.scherec.closurestatus.ClosureStatusManagementRepository;
import nts.uk.ctx.at.shared.dom.workrecord.workperfor.dailymonthlyprocessing.ErrMessageContent;
import nts.uk.ctx.at.shared.dom.workrecord.workperfor.dailymonthlyprocessing.ErrorMessageInfo;
import nts.uk.ctx.at.shared.dom.workrecord.workperfor.dailymonthlyprocessing.enums.ExecutionContent;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmployment;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmploymentRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.i18n.TextResource;

@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@Stateless
public class CreateDailyResultEmployeeDomainServiceNewImpl implements CreateDailyResultEmployeeDomainServiceNew {

	@Inject
	private EmploymentAdapter employmentAdapter;

	@Inject
	private ClosureStatusManagementRepository closureStatusManagementRepo;

	@Inject
	private ClosureEmploymentRepository closureEmploymentRepo;

	@Inject
	private ClosureRepository closureRepository;

	@Inject
	private ActualLockRepository actualLockRepository;

	@Inject
	private CreateDailyEmployeesByPeriod createDailyEmployeesByPeriod;

	@Inject
	private ReflectedPeriod reflectedPeriod;
	
	@Inject
	private RegisterDailyAchievements registerDailyAchievements; 
	@Inject
	private CreatingDailyResultsConditionRepository creatingDailyResultsConditionRepo;
	@Inject
	private EmpEmployeeAdapter employeeAdapter;

	@Override
	public OutputCreateDailyResult createDailyResultEmployee(String employeeId,
			DatePeriod periodTimes, String companyId, Optional<EmpCalAndSumExeLog> empCalAndSumExeLog,
			EmployeeGeneralInfoImport employeeGeneralInfoImport, PeriodInMasterList periodInMasterList,
			ExecutionTypeDaily executionType, Optional<Boolean> checkLock) {
		// ??????????????????????????????
		List<ErrorMessageInfo> listErrorMessageInfo = new ArrayList<>();

		// ???????????????????????????????????????????????????????????????
		GeneralDate processingDate = periodTimes.start();

		// Imported???????????????????????????????????????????????????
		List<EmploymentHistoryImported> listEmploymentHis = this.employmentAdapter.getEmpHistBySid(companyId,
				employeeId);
		if (listEmploymentHis.isEmpty()) {
			// ????????????Imported(?????????????????????)??????????????????????????????????????????????????????
			listErrorMessageInfo
					.add(new ErrorMessageInfo(companyId, employeeId, processingDate, ExecutionContent.DAILY_CREATION,
							new ErrMessageResource("010"), new ErrMessageContent(TextResource.localize("Msg_426"))));

			return new OutputCreateDailyResult(ProcessState.SUCCESS, listErrorMessageInfo);
		}

		GetPeriodCanProcesseRequireImpl require = new GetPeriodCanProcesseRequireImpl(closureStatusManagementRepo,
				closureEmploymentRepo, closureRepository, actualLockRepository, employmentAdapter, 
				creatingDailyResultsConditionRepo, employeeAdapter);
		IgnoreFlagDuringLock ignoreFlagDuringLock = checkLock.isPresent() && checkLock.get().booleanValue()
				? IgnoreFlagDuringLock.CAN_CAL_LOCK
				: IgnoreFlagDuringLock.CANNOT_CAL_LOCK;
		// ???????????????????????????????????????
		List<DatePeriod> listPeriod = GetPeriodCanProcesse.get(require, companyId, employeeId, periodTimes, listEmploymentHis,
				ignoreFlagDuringLock, AchievementAtr.DAILY);

		// ?????????????????????????????????????????????
		for (DatePeriod period : listPeriod) {
			Optional<String> empCalAndSumExecLogId = empCalAndSumExeLog.isPresent()
					? Optional.of(empCalAndSumExeLog.get().getEmpCalAndSumExecLogID())
					: Optional.empty();
			// ?????????????????????????????????????????????
			CreateDailyOuput createDailyOuput = createDailyEmployeesByPeriod.create(period, employeeId, executionType,
					empCalAndSumExecLogId, employeeGeneralInfoImport, periodInMasterList);
			listErrorMessageInfo.addAll(createDailyOuput.getListError());
			if (createDailyOuput.getReflectedPeriod() == ReflectedAtr.SUSPENDED) {
				new OutputCreateDailyResult(ProcessState.INTERRUPTION, listErrorMessageInfo);
			}
			//?????????????????????
			ReflectedPeriodOutput reflectedPeriodOutput = reflectedPeriod.reflect(employeeId, period, executionType, createDailyOuput.getListIntegrationOfDaily(),
					listErrorMessageInfo, createDailyOuput.getChangedDailyAttendance(), empCalAndSumExecLogId);
			
			if(reflectedPeriodOutput.getReflectedAtr() == ReflectedAtr.SUSPENDED ) {
				new OutputCreateDailyResult(ProcessState.INTERRUPTION, listErrorMessageInfo); 
			}
			
			//???????????????????????????
			registerDailyAchievements.register(createDailyOuput.getListIntegrationOfDaily(),
					createDailyOuput.getListError(), reflectedPeriodOutput.getListStamp(), empCalAndSumExecLogId);
		}

		return new OutputCreateDailyResult(ProcessState.SUCCESS, listErrorMessageInfo);
	}

	@AllArgsConstructor
	private class GetPeriodCanProcesseRequireImpl implements GetPeriodCanProcesse.Require {
		private ClosureStatusManagementRepository closureStatusManagementRepo;
		private ClosureEmploymentRepository closureEmploymentRepo;
		private ClosureRepository closureRepository;
		private ActualLockRepository actualLockRepository;
		private EmploymentAdapter employmentAdapter;
		private CreatingDailyResultsConditionRepository creatingDailyResultsConditionRepo;
		private EmpEmployeeAdapter employeeAdapter;

		@Override
		public DatePeriod getClosurePeriod(int closureId, YearMonth processYm) {
			// ??????????????????????????????????????????
			DatePeriod datePeriodClosure = ClosureService.getClosurePeriod(
					ClosureService.createRequireM1(closureRepository, closureEmploymentRepo), closureId, processYm);
			return datePeriodClosure;
		}

		@Override
		public List<ClosureStatusManagement> getAllByEmpId(String employeeId) {
			return closureStatusManagementRepo.getAllByEmpId(employeeId);
		}

		@Override
		public Optional<ClosureEmployment> findByEmploymentCD(String employmentCode) {
			String companyId = AppContexts.user().companyId();
			return closureEmploymentRepo.findByEmploymentCD(companyId, employmentCode);
		}

		@Override
		public Optional<ActualLock> findById(int closureId) {
			String companyId = AppContexts.user().companyId();
			return actualLockRepository.findById(companyId, closureId);
		}

		@Override
		public Optional<Closure> findClosureById(int closureId) {
			String companyId = AppContexts.user().companyId();
			return closureRepository.findById(companyId, closureId);
		}

		@Override
		public Optional<CreatingDailyResultsCondition> creatingDailyResultsCondition(String cid) {
			return creatingDailyResultsConditionRepo.findByCid(cid);
		}

		@Override
		public EmployeeImport employeeInfo(CacheCarrier cacheCarrier, String empId) {
			return employeeAdapter.findByEmpIdRequire(cacheCarrier, empId);
		}

		@Override
		public List<EmploymentHistoryImported> getEmpHistBySid(String companyId, String employeeId) {
			return employmentAdapter.getEmpHistBySid(companyId, employeeId);
		}

	}

}
