package nts.uk.ctx.at.record.app.command.kdp.kdp003.a;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
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
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.AuthcMethod;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.EnterStampForSharedStampService;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.Relieve;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.Stamp;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampDakokuRepository;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampMeans;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampRecord;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampRecordRepository;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.StampDataReflectResult;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.TimeStampInputResult;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.ChangeClockArt;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.StampSetCommunal;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.StampSetCommunalRepository;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmpEmployeeAdapter;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmployeeImport;
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

@Stateless
public class RegisterNameSelectionCommandHandler
		extends CommandHandlerWithResult<RegisterNameSelectionCommand, GeneralDate> {

	@Inject
	private StampSetCommunalRepository stampSetCommunalRepository;

	@Inject
	private StampCardRepository stampCardRepo;

	@Inject
	private StampCardEditingRepo stampCardEditRepo;

	@Inject
	private CompanyAdapter companyAdapter;

	@Inject
	private EmployeeRecordAdapter sysEmpPub;

	@Inject
	private StampRecordRepository stampRecordRepo;

	@Inject
	private StampDakokuRepository stampDakokuRepo;

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
	protected GeneralDate handle(CommandHandlerContext<RegisterNameSelectionCommand> context) {
		RegisterNameSelectionCommand cmd = context.getCommand();

		EnterStampForSharedStampServiceRequireImpl require = new EnterStampForSharedStampServiceRequireImpl(
				stampSetCommunalRepository, stampCardRepo, stampCardEditRepo, companyAdapter, sysEmpPub,
				stampRecordRepo, stampDakokuRepo, createDailyResults,
				temporarilyReflectStampDailyAttd, 
				empInfoTerminalRepository, stampCardRepo, employeeManageRCAdapter, 
				dailyRecordAdUpService, getMngInfoFromEmpIDListAdapter, iGetInfoForLogin, 
				loginUserContextManager, calcService, closureRepo, closureEmploymentRepo, shareEmploymentAdapter,
				attendanceItemConvertFactory, iCorrectionAttendanceRule, interimRemainDataMngRegisterDateChange,
				dailyRecordShareFinder, timeReflectFromWorkinfo,
				closureStatusManagementRepo, actualLockRepo, employmentAdapter, creatingDailyResultsConditionRepo, empEmployeeAdapter);

		// require, 契約コード, 社員ID, なし, 打刻する方法, 打刻日時, 打刻ボタン, 実績への反映内容
		TimeStampInputResult inputResult = EnterStampForSharedStampService.create(require,
				AppContexts.user().companyId(), AppContexts.user().contractCode(), cmd.getEmployeeId(),
				Optional.ofNullable(null), new Relieve(AuthcMethod.ID_AUTHC, StampMeans.NAME_SELECTION),
				cmd.getDateTime(), cmd.getStampButton(), cmd.getRefActualResult().toDomainValue());
		// 2: not empty
		if (inputResult != null && inputResult.at.isPresent()) {
			transaction.execute(() -> {
				inputResult.at.get().run();
			});
		}

		StampDataReflectResult stampRefResult = inputResult.getStampDataReflectResult();

		if (stampRefResult != null && stampRefResult.getAtomTask() != null) {
			transaction.execute(() -> {
				stampRefResult.getAtomTask().run();
			});
		}

		return stampRefResult.getReflectDate().map(x -> x).orElse(null);
	}

	@AllArgsConstructor
	private class EnterStampForSharedStampServiceRequireImpl implements EnterStampForSharedStampService.Require {
		private StampSetCommunalRepository stampSetCommunalRepository;

		private StampCardRepository stampCardRepo;

		private StampCardEditingRepo stampCardEditRepo;

		private CompanyAdapter companyAdapter;

		private EmployeeRecordAdapter sysEmpPub;

		private StampRecordRepository stampRecordRepo;

		private StampDakokuRepository stampDakokuRepo;

		private CreateDailyResults createDailyResults;

		private TemporarilyReflectStampDailyAttd temporarilyReflectStampDailyAttd;

		private EmpInfoTerminalRepository empInfoTerminalRepository;

		private StampCardRepository stampCardRepository;

		private EmployeeManageRCAdapter employeeManageRCAdapter;

		private DailyRecordAdUpService dailyRecordAdUpService;

		private GetMngInfoFromEmpIDListAdapter getMngInfoFromEmpIDListAdapter;

		private IGetInfoForLogin iGetInfoForLogin;

		private LoginUserContextManager loginUserContextManager;

		private CalculateDailyRecordServiceCenter calcService;

		private ClosureRepository closureRepo;

		private ClosureEmploymentRepository closureEmploymentRepo;

		private ShareEmploymentAdapter shareEmploymentAdapter;
		
        private AttendanceItemConvertFactory attendanceItemConvertFactory;
	    
	    private ICorrectionAttendanceRule iCorrectionAttendanceRule;
	    
	    private InterimRemainDataMngRegisterDateChange interimRemainDataMngRegisterDateChange;
	    
	    private DailyRecordShareFinder dailyRecordShareFinder;
		
		private TimeReflectFromWorkinfo timeReflectFromWorkinfo;
		
		protected ClosureStatusManagementRepository closureStatusManagementRepo;
		protected ActualLockRepository actualLockRepo;
		protected EmploymentAdapter employmentAdapter;
		private CreatingDailyResultsConditionRepository creatingDailyResultsConditionRepo;
		protected EmpEmployeeAdapter empEmployeeAdapter;

		@Override
		public List<StampCard> getLstStampCardBySidAndContractCd(String sid) {
			return this.stampCardRepo.getLstStampCardBySidAndContractCd(AppContexts.user().contractCode(), sid);
		}

		@Override
		public List<EmployeeDataMngInfoImport> findBySidNotDel(List<String> sid) {
			return this.sysEmpPub.findBySidNotDel(sid);
		}

		@Override
		public Optional<CompanyImport622> getCompanyNotAbolitionByCid(String cid) {
			return this.companyAdapter.getCompanyNotAbolitionByCid(cid);
		}

		@Override
		public Optional<StampCardEditing> get(String companyId) {
			return Optional.ofNullable(this.stampCardEditRepo.get(companyId));
		}

		@Override
		public Optional<StampCard> getByCardNoAndContractCode(String stampNumber, String contractCode) {
			return this.stampCardRepo.getByCardNoAndContractCode(stampNumber, contractCode);
		}

		@Override
		public void add(StampCard domain) {
			this.stampCardRepo.add(domain);
		}

		@Override
		public void insert(StampRecord stampRecord) {
			this.stampRecordRepo.insert(stampRecord);

		}

		@Override
		public void insert(Stamp stamp) {
			this.stampDakokuRepo.insert(stamp);
		}

		@Override
		public Optional<StampSetCommunal> gets() {
			return this.stampSetCommunalRepository.gets(AppContexts.user().companyId());
		}

		@Override
		public Optional<StampRecord> getStampRecord(ContractCode contractCode, StampNumber stampNumber,
				GeneralDateTime dateTime) {
			return stampRecordRepo.get(contractCode.v(), stampNumber.v(), dateTime);
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
		public void addAllDomain(IntegrationOfDaily domain) {
			dailyRecordAdUpService.addAllDomain(domain);
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
		public boolean existsStamp(ContractCode contractCode, StampNumber stampNumber, GeneralDateTime dateTime,
				ChangeClockArt changeClockArt) {
			return stampDakokuRepo.existsStamp(contractCode, stampNumber, dateTime, changeClockArt);
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
		public Closure findClosureById(int closureId) {
			String companyId = AppContexts.user().companyId();
		return closureRepo.findById(companyId, closureId).get();
		}

		@Override
		public Optional<CreatingDailyResultsCondition> creatingDailyResultsCondition(String cid) {
			return creatingDailyResultsConditionRepo.findByCid(cid);
		}

		@Override
		public EmployeeImport employeeInfo(CacheCarrier cacheCarrier, String empId) {
			return empEmployeeAdapter.findByEmpIdRequire(cacheCarrier, empId);
		}

	}
}