package nts.uk.ctx.at.record.app.command.kdp.kdp002.a;

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
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.auth.dom.adapter.login.IGetInfoForLogin;
import nts.uk.ctx.at.record.dom.adapter.employee.EmployeeDataMngInfoImport;
import nts.uk.ctx.at.record.dom.adapter.employee.EmployeeRecordAdapter;
import nts.uk.ctx.at.record.dom.adapter.employeemanage.EmployeeManageRCAdapter;
import nts.uk.ctx.at.record.dom.adapter.employmentinfoterminal.infoterminal.EmpDataImport;
import nts.uk.ctx.at.record.dom.adapter.employmentinfoterminal.infoterminal.GetMngInfoFromEmpIDListAdapter;
import nts.uk.ctx.at.record.dom.daily.DailyRecordAdUpService;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.output.ExecutionAttr;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.ExecutionTypeDaily;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.createdailyoneday.EmbossingExecutionFlag;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.createdailyoneday.createdailyresults.CreateDailyResults;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.createdailyresults.CreateDailyResultDomainServiceNew;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.createdailyresults.OutputCreateDailyOneDay;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.createdailyresults.OutputCreateDailyResult;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.CalculateDailyRecordServiceCenter;
import nts.uk.ctx.at.record.dom.dailyresultcreationprocess.creationprocess.creationclass.dailywork.TemporarilyReflectStampDailyAttd;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminal;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.log.TopPageAlarmEmpInfoTer;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.log.TopPgAlTrRepository;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.repo.EmpInfoTerminalRepository;
import nts.uk.ctx.at.record.dom.stamp.card.stamcardedit.StampCardEditing;
import nts.uk.ctx.at.record.dom.stamp.card.stamcardedit.StampCardEditingRepo;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCard;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCardRepository;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampNumber;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.Stamp;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampDakokuRepository;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampRecord;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampRecordRepository;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.CreateStampDataForEmployeesService;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.TimeStampInputResult;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.ChangeClockArt;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.EmpCalAndSumExeLog;
import nts.uk.ctx.at.shared.dom.adapter.employment.BsEmploymentHistoryImport;
import nts.uk.ctx.at.shared.dom.adapter.employment.ShareEmploymentAdapter;
import nts.uk.ctx.at.shared.dom.adapter.holidaymanagement.CompanyAdapter;
import nts.uk.ctx.at.shared.dom.adapter.holidaymanagement.CompanyImport622;
import nts.uk.ctx.at.shared.dom.adapter.holidaymanagement.CompanyInfo;
import nts.uk.ctx.at.shared.dom.dailyattdcal.converter.DailyRecordShareFinder;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.function.algorithm.ChangeDailyAttendance;
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
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.loginuser.LoginUserContextManager;

/**
 * UKDesign.UniversalK.就業.KDP_打刻.KDP002_打刻入力(個人打刻).A:打刻入力(個人).メニュー別OCD.打刻入力(個人)を登録する
 * đây là class sample để tham khảo
 * @author lamvt
 *
 */
@Stateless
public class RegisterStampIndividualSampleCommandHandler
		extends CommandHandlerWithResult<RegisterStampIndividualSampleCommand, RegisterStampIndividualSampleResult> {

	@Inject
	private StampCardRepository stampCardRepository;

	@Inject
	private StampDakokuRepository stampDakokuRepo;

	@Inject
	private StampRecordRepository stampRecordRepo;

	@Inject
	private CreateDailyResultDomainServiceNew createDailyResultDomainServiceNew;

	@Inject
	private StampCardEditingRepo stampCardEditRepo;

	@Inject
	private EmployeeRecordAdapter sysEmpPub;

	@Inject
	private CompanyAdapter companyAdapter;

	@Inject
	private CreateDailyResults createDailyResults;

	@Inject
	private TimeReflectFromWorkinfo timeReflectFromWorkinfo;

	@Inject
	private TemporarilyReflectStampDailyAttd temporarilyReflectStampDailyAttd;

	@Inject
	private DailyRecordShareFinder dailyRecordShareFinder;

	@Inject
	private EmpInfoTerminalRepository empInfoTerminalRepository;

	@Inject
	private EmployeeManageRCAdapter employeeManageRCAdapter;

	@Inject
	private TopPgAlTrRepository executionLog;

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

	@Override
	protected RegisterStampIndividualSampleResult handle(CommandHandlerContext<RegisterStampIndividualSampleCommand> context) {
		
		CreateStampDataForEmployeesRequiredImpl required = new CreateStampDataForEmployeesRequiredImpl(
				stampCardRepository, stampDakokuRepo, stampRecordRepo, createDailyResultDomainServiceNew, stampCardEditRepo,
				sysEmpPub, companyAdapter, createDailyResults, timeReflectFromWorkinfo, temporarilyReflectStampDailyAttd,
				dailyRecordShareFinder,empInfoTerminalRepository, stampRecordRepo, employeeManageRCAdapter, executionLog, 
				dailyRecordAdUpService, getMngInfoFromEmpIDListAdapter, iGetInfoForLogin, loginUserContextManager, 
				calcService, closureRepo, closureEmploymentRepo, shareEmploymentAdapter);

		RegisterStampIndividualSampleCommand cmd = context.getCommand();
		String employeeId = AppContexts.user().employeeId();
		
		/** 
		 * 1. 作成する(@Require, 契約コード, 社員ID, 打刻カード番号, 日時, 打刻する方法, ボタン種類, 実績への反映内容, 打刻場所情報) */
		TimeStampInputResult result = CreateStampDataForEmployeesService.create(required, AppContexts.user().companyId(),
			new ContractCode(AppContexts.user().contractCode()), employeeId, Optional.empty(), cmd.retriveDateTime(), cmd.toRelieve(),
				cmd.toButtonType(), null, Optional.empty());
		
		/**
		 * 2.  */
		Optional<AtomTask> atomOpt = result.getAt();
		
		if (atomOpt.isPresent()) {
			transaction.execute(() -> {
				atomOpt.get().run();
			});
		}
		return new RegisterStampIndividualSampleResult(result.getStampDataReflectResult().getReflectDate());
	}

	@AllArgsConstructor
	private class CreateStampDataForEmployeesRequiredImpl implements CreateStampDataForEmployeesService.Require {

		@Inject
		private StampCardRepository stampCardRepository;

		@Inject
		private StampDakokuRepository stampDakokuRepo;

		@Inject
		private StampRecordRepository stampRecordRepo;

		@Inject
		private CreateDailyResultDomainServiceNew createDailyResultDomainServiceNew;

		@Inject
		private StampCardEditingRepo stampCardEditRepo;

		@Inject
		private EmployeeRecordAdapter sysEmpPub;

		@Inject
		private CompanyAdapter companyAdapter;

		@Inject
		private CreateDailyResults createDailyResults;

		@Inject
		private TimeReflectFromWorkinfo timeReflectFromWorkinfo;

		@Inject
		private TemporarilyReflectStampDailyAttd temporarilyReflectStampDailyAttd;

		@Inject
		private DailyRecordShareFinder dailyRecordShareFinder;

		@Inject
		private final EmpInfoTerminalRepository empInfoTerminalRepository;

		@Inject
		private final StampRecordRepository stampRecordRepository;

		@Inject
		private final EmployeeManageRCAdapter employeeManageRCAdapter;

		@Inject
		private final TopPgAlTrRepository executionLog;

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

		@Override
		public void insert(StampRecord stampRecord) {
			stampRecordRepo.insert(stampRecord);

		}

		@Override
		public void insert(Stamp stamp) {
			stampDakokuRepo.insert(stamp);
		}

		@Override
		public OutputCreateDailyResult createDataNewNotAsync(String employeeId, DatePeriod periodTime,
				ExecutionAttr executionAttr, String companyId, ExecutionTypeDaily executionType,
				Optional<EmpCalAndSumExeLog> empCalAndSumExeLog, Optional<Boolean> checkLock) {
			return createDailyResultDomainServiceNew.createDataNewNotAsync(employeeId, periodTime, executionAttr,
					companyId, executionType, empCalAndSumExeLog, checkLock);
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
		public OutputCreateDailyOneDay createDailyResult(String cid, String employeeId, GeneralDate ymd,
				ExecutionTypeDaily executionType, EmbossingExecutionFlag flag, 
				IntegrationOfDaily integrationOfDaily) {
			return this.createDailyResults.createDailyResult(cid, employeeId, ymd, executionType,
					integrationOfDaily);
		}

		@Override
		public OutputTimeReflectForWorkinfo get(String companyId, String employeeId, GeneralDate ymd,
				WorkInfoOfDailyAttendance workInformation) {
			return this.timeReflectFromWorkinfo.get(companyId, employeeId, ymd, workInformation);
		}

		@Override
		public List<ErrorMessageInfo> reflectStamp(String companyId, Stamp stamp,
				StampReflectRangeOutput stampReflectRangeOutput, IntegrationOfDaily integrationOfDaily,
				ChangeDailyAttendance changeDailyAtt) {
			return this.temporarilyReflectStampDailyAttd.reflectStamp(companyId, stamp, stampReflectRangeOutput,
					integrationOfDaily, changeDailyAtt);
		}

		@Override
		public Optional<IntegrationOfDaily> findDaily(String employeeId, GeneralDate date) {
			return dailyRecordShareFinder.find(employeeId, date);
		}

		@Override
		public Optional<EmpInfoTerminal> getEmpInfoTerminal(EmpInfoTerminalCode empInfoTerCode,
				ContractCode contractCode) {
			return empInfoTerminalRepository.getEmpInfoTerminal(empInfoTerCode, contractCode);
		}

		@Override
		public Optional<StampRecord> getStampRecord(ContractCode contractCode, StampNumber stampNumber,
				GeneralDateTime dateTime) {
			return stampRecordRepository.get(contractCode.v(), stampNumber.v(), dateTime);
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
		public void insertLogAll(TopPageAlarmEmpInfoTer alEmpInfo) {
			executionLog.insertLogAll(alEmpInfo);

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
			loginUserContextManager.loggedInAsEmployee(userId, personId, contractCode, companyId, companyCode,
					employeeId, employeeCode);
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
	}
}