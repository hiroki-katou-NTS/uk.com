package nts.uk.ctx.at.record.app.command.stamp.card.stampcard.management.personalengraving;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;

import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;

import nts.uk.ctx.at.auth.dom.adapter.login.IGetInfoForLogin;
import nts.uk.ctx.at.record.dom.adapter.employee.EmployeeDataMngInfoImport;
import nts.uk.ctx.at.record.dom.adapter.employee.EmployeeRecordAdapter;
import nts.uk.ctx.at.record.dom.adapter.employeemanage.EmployeeManageRCAdapter;
import nts.uk.ctx.at.record.dom.adapter.employmentinfoterminal.infoterminal.EmpDataImport;
import nts.uk.ctx.at.record.dom.adapter.employmentinfoterminal.infoterminal.GetMngInfoFromEmpIDListAdapter;
import nts.uk.ctx.at.record.dom.daily.DailyRecordAdUpService;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.creationprocess.CreatingDailyResultsCondition;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.creationprocess.CreatingDailyResultsConditionRepository;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.ExecutionTypeDaily;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.createdailyoneday.createdailyresults.CreateDailyResults;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.createdailyresults.OutputCreateDailyOneDay;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.CalculateDailyRecordServiceCenter;
import nts.uk.ctx.at.record.dom.dailyresultcreationprocess.creationprocess.creationclass.dailywork.TemporarilyReflectStampDailyAttd;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminal;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.repo.EmpInfoTerminalRepository;
import nts.uk.ctx.at.record.dom.organization.EmploymentHistoryImported;
import nts.uk.ctx.at.record.dom.organization.adapter.EmploymentAdapter;
import nts.uk.ctx.at.record.dom.stamp.card.stamcardedit.StampCardEditing;
import nts.uk.ctx.at.record.dom.stamp.card.stamcardedit.StampCardEditingRepo;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCard;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCardRepository;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampNumber;
import nts.uk.ctx.at.record.dom.workrecord.actuallock.ActualLock;
import nts.uk.ctx.at.record.dom.workrecord.actuallock.ActualLockRepository;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.Stamp;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampDakokuRepository;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.CreateStampDataForEmployeesService;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.TimeStampInputResult;

import nts.uk.ctx.at.shared.dom.adapter.employee.EmpEmployeeAdapter;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmployeeImport;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.ChangeClockAtr;

import nts.uk.ctx.at.shared.dom.adapter.employment.BsEmploymentHistoryImport;
import nts.uk.ctx.at.shared.dom.adapter.employment.ShareEmploymentAdapter;
import nts.uk.ctx.at.shared.dom.adapter.holidaymanagement.CompanyAdapter;
import nts.uk.ctx.at.shared.dom.adapter.holidaymanagement.CompanyImport622;
import nts.uk.ctx.at.shared.dom.adapter.holidaymanagement.CompanyInfo;
import nts.uk.ctx.at.shared.dom.dailyattdcal.converter.DailyRecordShareFinder;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.InterimRemainDataMngRegisterDateChange;
import nts.uk.ctx.at.shared.dom.scherec.attendanceitem.converter.service.AttendanceItemConvertFactory;
import nts.uk.ctx.at.shared.dom.scherec.closurestatus.ClosureStatusManagement;
import nts.uk.ctx.at.shared.dom.scherec.closurestatus.ClosureStatusManagementRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.DailyRecordToAttendanceItemConverter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.function.algorithm.ChangeDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.function.algorithm.ICorrectionAttendanceRule;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.WorkInfoOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.algorithmdailyper.OutputTimeReflectForWorkinfo;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.algorithmdailyper.StampReflectRangeOutput;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.algorithmdailyper.TimeReflectFromWorkinfo;
import nts.uk.ctx.at.shared.dom.workrecord.workperfor.dailymonthlyprocessing.ErrorMessageInfo;
import nts.uk.ctx.at.shared.dom.workrecord.workperfor.dailymonthlyprocessing.enums.ExecutionType;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmployment;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmploymentRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.loginuser.LoginUserContextManager;

/**
 * @author anhdt ????????????(??????)???????????????
 */
@Stateless
public class RegisterStampDataCommandHandler extends CommandHandlerWithResult<RegisterStampDataCommand, RegisterStampDataResult> {

	@Inject
	private StampCardRepository stampCardRepository;

	@Inject
	private StampDakokuRepository stampDakokuRepo;

	@Inject
	private StampCardEditingRepo stampCardEditRepo;
	
	@Inject
	private EmployeeRecordAdapter sysEmpPub;

	@Inject
	private CompanyAdapter companyAdapter;
	
	@Inject
	private CreateDailyResults createDailyResults;
	
	@Inject
	private TemporarilyReflectStampDailyAttd temporarilyReflectStampDailyAttd;
	
	@Inject
	private EmpInfoTerminalRepository empInfoTerminalRepository;

	@Inject
	private EmployeeManageRCAdapter employeeManageRCAdapter;

	@Inject
	private DailyRecordAdUpService dailyRecordAdUpService;
	
	@Inject
	private GetMngInfoFromEmpIDListAdapter getMngInfoFromEmpIDListAdapter;
	
	@Inject
	private IGetInfoForLogin iGetInfoForLogin;
	
	@Inject
	private LoginUserContextManager loginUserContextManager;
	
    @Inject
    private CalculateDailyRecordServiceCenter calcService;
    
    @Inject
    private ClosureRepository closureRepo;

    @Inject
	 private ClosureEmploymentRepository closureEmploymentRepo;

    @Inject
    private ShareEmploymentAdapter shareEmploymentAdapter;
    
    @Inject
	private AttendanceItemConvertFactory attendanceItemConvertFactory;
    
	@Inject
    private ICorrectionAttendanceRule iCorrectionAttendanceRule;
    
	@Inject
	private InterimRemainDataMngRegisterDateChange interimRemainDataMngRegisterDateChange;

	@Inject
	private DailyRecordShareFinder dailyRecordShareFinder;

	@Inject
	private TimeReflectFromWorkinfo timeReflectFromWorkinfo;

    @Inject
    private ClosureStatusManagementRepository closureStatusManagementRepo;
    @Inject
    private ActualLockRepository actualLockRepo;
    @Inject
    private EmploymentAdapter employmentAdapter;
    @Inject
	private CreatingDailyResultsConditionRepository creatingDailyResultsConditionRepo;
    @Inject
    private EmpEmployeeAdapter empEmployeeAdapter;

	@Override
	protected RegisterStampDataResult handle(CommandHandlerContext<RegisterStampDataCommand> context) {
		CreateStampDataForEmployeesRequiredImpl required = new CreateStampDataForEmployeesRequiredImpl(stampCardRepository,
				stampDakokuRepo, stampCardEditRepo, sysEmpPub, companyAdapter,
				empInfoTerminalRepository, employeeManageRCAdapter, dailyRecordAdUpService,
				getMngInfoFromEmpIDListAdapter, iGetInfoForLogin, loginUserContextManager, calcService, closureRepo,
				closureEmploymentRepo, shareEmploymentAdapter, dailyRecordShareFinder, timeReflectFromWorkinfo,
				closureStatusManagementRepo, actualLockRepo, employmentAdapter, creatingDailyResultsConditionRepo, empEmployeeAdapter);

		RegisterStampDataCommand cmd = context.getCommand();
		String employeeId = AppContexts.user().employeeId();
		TimeStampInputResult result = CreateStampDataForEmployeesService.create(required,
				AppContexts.user().companyId(),
				new ContractCode(AppContexts.user().contractCode()), employeeId, Optional.empty(),
				cmd.retriveDateTime(), cmd.toRelieve(), cmd.toStampType(),
				cmd.toRefectActualResult(), Optional.ofNullable(cmd.toGeoCoordinate()));
		
		Optional<AtomTask> atomOpt = result.getAt();
		
		if (atomOpt.isPresent()) {
			transaction.execute(() -> {
				atomOpt.get().run();
			});
		}
		
		AtomTask ReflectResultAtomOpt = result.getStampDataReflectResult().getAtomTask();
		
		if (ReflectResultAtomOpt != null) {
			transaction.execute(() -> {
				ReflectResultAtomOpt.run();
			});
		}

		return new RegisterStampDataResult(employeeId, result.getStampDataReflectResult().getReflectDate());
	}

	@AllArgsConstructor
	private class CreateStampDataForEmployeesRequiredImpl implements CreateStampDataForEmployeesService.Require {

		@Inject
		private StampCardRepository stampCardRepository;

		@Inject
		private StampDakokuRepository stampDakokuRepo;

		@Inject
		private StampCardEditingRepo stampCardEditRepo;
		
		@Inject
		private EmployeeRecordAdapter sysEmpPub;

		@Inject
		private CompanyAdapter companyAdapter;
		
		private EmpInfoTerminalRepository empInfoTerminalRepository;

		private EmployeeManageRCAdapter employeeManageRCAdapter;

		private DailyRecordAdUpService dailyRecordAdUpService;
		
		private GetMngInfoFromEmpIDListAdapter getMngInfoFromEmpIDListAdapter;
		
		private IGetInfoForLogin iGetInfoForLogin;
		
		private LoginUserContextManager loginUserContextManager;
		
		 private CalculateDailyRecordServiceCenter calcService;
		 
		 private ClosureRepository closureRepo;

		 private ClosureEmploymentRepository closureEmploymentRepo;
		 
		 private ShareEmploymentAdapter shareEmploymentAdapter;
		 
		 private DailyRecordShareFinder dailyRecordShareFinder;
		    
		 private TimeReflectFromWorkinfo timeReflectFromWorkinfo;
			
		protected ClosureStatusManagementRepository closureStatusManagementRepo;
		protected ActualLockRepository actualLockRepo;
		protected EmploymentAdapter employmentAdapter;
		private CreatingDailyResultsConditionRepository creatingDailyResultsConditionRepo;
		protected EmpEmployeeAdapter empEmployeeAdapter;

		@Override
		public void insert(Stamp stamp) {
			stampDakokuRepo.insert(stamp);
		}

		@Override
		public List<StampCard> getLstStampCardBySidAndContractCd(String sid) {
			return this.stampCardRepository.getLstStampCardBySidAndContractCd(AppContexts.user().contractCode(), sid);
		}

		@Override
		public List<EmployeeDataMngInfoImport> findBySidNotDel(List<String> sids) {
			return this.sysEmpPub.findBySidNotDel(sids);
		}

		@Override
		public Optional<CompanyImport622> getCompanyNotAbolitionByCid(String cid) {
			return this.companyAdapter.getCompanyNotAbolitionByCid(cid);
		}

		@Override
		public void add(StampCard domain) {
			this.stampCardRepository.add(domain);
		}

		@Override
		public Optional<StampCardEditing> get(String companyId) {
			return Optional.ofNullable(this.stampCardEditRepo.get(companyId));

		}
		
		@Override
		public Optional<StampCard> getByCardNoAndContractCode(String stampNumber, String contractCode) {
			return this.stampCardRepository.getByCardNoAndContractCode(stampNumber, contractCode);
		}
		
		@Override
		public Optional<EmpInfoTerminal> getEmpInfoTerminal(EmpInfoTerminalCode empInfoTerCode,
				ContractCode contractCode) {
			return empInfoTerminalRepository.getEmpInfoTerminal(empInfoTerCode, contractCode);
		}

		@Override
		public Optional<StampCard> getByCardNoAndContractCode(ContractCode contractCode, StampNumber stampNumber) {
			return stampCardRepository.getByCardNoAndContractCode(stampNumber.v(), contractCode.v());
		}

		@Override
		public List<String> getListEmpID(String companyID, GeneralDate referenceDate) {
			return employeeManageRCAdapter.getListEmpID(companyID, referenceDate);
		}

		@Override
		public List<EmpDataImport> getEmpData(List<String> empIDList) {
			return getMngInfoFromEmpIDListAdapter.getEmpData(empIDList);
		}

		@Override
		public CompanyInfo getCompanyInfoById(String companyId) {
			return companyAdapter.getCompanyInfoById(companyId);
		}

		@Override
		public Optional<String> getUserIdFromLoginId(String perId) {
			return iGetInfoForLogin.getUserIdFromLoginId(perId);
		}

		@Override
		public void loggedInAsEmployee(String userId, String personId, String contractCode, String companyId,
				String companyCode, String employeeId, String employeeCode) {
			loginUserContextManager.loggedInAsEmployee(userId, personId, contractCode, companyId, companyCode, employeeId, employeeCode);
		}

		@Override
		public List<IntegrationOfDaily> calculatePassCompanySetting(String cid,
				List<IntegrationOfDaily> integrationOfDaily, ExecutionType reCalcAtr) {
			return calcService.calculatePassCompanySetting(integrationOfDaily, Optional.empty(), reCalcAtr);
		}

		@Override
		public void loggedOut() {
			loginUserContextManager.loggedOut();
		}

		@Override
		public Optional<BsEmploymentHistoryImport> employmentHistory(CacheCarrier cacheCarrier, String companyId,
				String employeeId, GeneralDate baseDate) {
			return shareEmploymentAdapter.findEmploymentHistoryRequire(cacheCarrier, companyId, employeeId, baseDate);
		}

		@Override
		public Optional<ClosureEmployment> employmentClosure(String companyID, String employmentCD) {
			return closureEmploymentRepo.findByEmploymentCD(companyID, employmentCD);
		}

		@Override
		public Optional<Closure> closure(String companyId, int closureId) {
			return closureRepo.findById(companyId, closureId);
		}

		@Override
		public void addAllDomain(IntegrationOfDaily domain) {
			dailyRecordAdUpService.addAllDomain(domain);
		}

		@Override
		public Map<String, BsEmploymentHistoryImport> employmentHistoryClones(String companyId, List<String> employeeId,
				GeneralDate baseDate) {
			return shareEmploymentAdapter.findEmpHistoryVer2(companyId, employeeId, baseDate);
		}

		@Override
		public List<ClosureEmployment> employmentClosureClones(String companyID, List<String> employmentCD) {
			return closureEmploymentRepo.findListEmployment(companyID, employmentCD);
		}

		@Override
		public List<Closure> closureClones(String companyId, List<Integer> closureId) {
			return closureRepo.findByListId(companyId, closureId);
		}

		@Override
		public DailyRecordToAttendanceItemConverter createDailyConverter() {
			return attendanceItemConvertFactory.createDailyConverter();
		}
		
		@Override
		public IntegrationOfDaily restoreData(DailyRecordToAttendanceItemConverter converter,
				IntegrationOfDaily integrationOfDaily, List<ItemValue> listItemValue) {
			return createDailyResults.restoreData(converter, integrationOfDaily, listItemValue);
		}
		
		@Override
		public IntegrationOfDaily process(IntegrationOfDaily domainDaily, ChangeDailyAttendance changeAtt) {
			return iCorrectionAttendanceRule.process(domainDaily, changeAtt);
		}
		
		@Override
		public void registerDateChange(String cid, String sid, List<GeneralDate> lstDate) {
			interimRemainDataMngRegisterDateChange.registerDateChange(cid, sid, lstDate);
		}

		@Override
		public Optional<IntegrationOfDaily> find(String employeeId, GeneralDate date) {
			return dailyRecordShareFinder.find(employeeId, date);
		}

		@Override
		public Optional<OutputCreateDailyOneDay> createDailyResult(String companyId, String employeeId, GeneralDate ymd,
				ExecutionTypeDaily executionType) {
			return createDailyResults.createDailyResult(companyId, employeeId, ymd, executionType);
		}

		@Override
		public OutputTimeReflectForWorkinfo get(String companyId, String employeeId, GeneralDate ymd,
				WorkInfoOfDailyAttendance workInformation) {
			return timeReflectFromWorkinfo.get(companyId, employeeId, ymd, workInformation);
		}

		@Override
		public List<ErrorMessageInfo> reflectStamp(String companyId, Stamp stamp,
				StampReflectRangeOutput stampReflectRangeOutput, IntegrationOfDaily integrationOfDaily,
				ChangeDailyAttendance changeDailyAtt) {
			return temporarilyReflectStampDailyAttd.reflectStamp(companyId, stamp, stampReflectRangeOutput, integrationOfDaily, changeDailyAtt);
		}

		@Override
		public List<ClosureStatusManagement> getAllByEmpId(String employeeId) {
			return closureStatusManagementRepo.getAllByEmpId(employeeId);
		}

		@Override
		public Optional<ClosureEmployment> findByEmploymentCD(String employmentCode) {
			return closureEmploymentRepo.findByEmploymentCD(AppContexts.user().companyId(), employmentCode);
		}

		@Override
		public Optional<ActualLock> findById(int closureId) {
			return actualLockRepo.findById(AppContexts.user().companyId(), closureId);
		}

		@Override
		public List<EmploymentHistoryImported> getEmpHistBySid(String companyId, String employeeId) {
			return employmentAdapter.getEmpHistBySid(companyId, employeeId);
		}

		@Override
		public DatePeriod getClosurePeriod(int closureId, YearMonth processYm) {
			DatePeriod datePeriodClosure = ClosureService.getClosurePeriod(
					ClosureService.createRequireM1(closureRepo, closureEmploymentRepo), closureId, processYm);
			return datePeriodClosure;

		}

		@Override
		public Optional<Closure> findClosureById(int closureId) {
			String companyId = AppContexts.user().companyId();
		return closureRepo.findById(companyId, closureId);
		}

		@Override
		public Optional<CreatingDailyResultsCondition> creatingDailyResultsCondition(String cid) {
			return creatingDailyResultsConditionRepo.findByCid(cid);
		}

		@Override
		public EmployeeImport employeeInfo(CacheCarrier cacheCarrier, String empId) {
			return empEmployeeAdapter.findByEmpIdRequire(cacheCarrier, empId);
		}
		
		@Override
		public boolean existsStamp(ContractCode contractCode, StampNumber stampNumber, GeneralDateTime dateTime,
				ChangeClockAtr changeClockArt) {
			return stampDakokuRepo.existsStamp(contractCode, stampNumber, dateTime, changeClockArt);
		}
	}
}