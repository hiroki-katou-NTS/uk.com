package nts.uk.screen.at.app.command.kdp.kdp001.a;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.arc.layer.app.command.AsyncCommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.function.dom.adapter.employeemanage.EmployeeManageAdapter;
import nts.uk.ctx.at.record.dom.adapter.employee.EmployeeDataMngInfoImport;
import nts.uk.ctx.at.record.dom.adapter.employee.EmployeeRecordAdapter;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.output.ExecutionAttr;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.CreateDailyResultDomainService;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.CreateDailyResultDomainServiceImpl.ProcessState;
import nts.uk.ctx.at.record.dom.stamp.card.stamcardedit.StampCardEditing;
import nts.uk.ctx.at.record.dom.stamp.card.stamcardedit.StampCardEditingRepo;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCard;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCardRepository;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.RefectActualResult;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.Stamp;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampDakokuRepository;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampRecord;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampRecordRepository;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.CreateDailyResultsStamps;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.EnterStampFromPortalService;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.TimeStampInputResult;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.ButtonPositionNo;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.PortalStampSettings;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.PortalStampSettingsRepository;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.toppagealarm.TopPageAlarmStamping;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.toppagealarm.TopPageAlarmStampingRepository;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.EmpCalAndSumExeLog;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.ErrorMessageInfo;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.ExecutionLog;
import nts.uk.ctx.at.shared.dom.adapter.holidaymanagement.CompanyAdapter;
import nts.uk.ctx.at.shared.dom.adapter.holidaymanagement.CompanyImport622;
import nts.uk.ctx.at.shared.dom.dailyperformanceprocessing.ErrMessageResource;
import nts.uk.ctx.at.shared.dom.workrecord.workperfor.dailymonthlyprocessing.ErrMessageContent;
import nts.uk.ctx.at.shared.dom.workrecord.workperfor.dailymonthlyprocessing.enums.ExecutionContent;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author sonnlb
 *         UKDesign.UniversalK.就業.KDP_打刻.KDP001_打刻入力(ポータル).A:打刻入力(ポータル).メニュー別OCD.打刻入力(ポータル)を登録する
 *
 */
@Stateless
public class RegisterStampInputCommandHandler
		extends CommandHandlerWithResult<RegisterStampInputCommand, RegisterStampInputResult> {
	

	@Inject
	private PortalStampSettingsRepository settingRepo;

	@Inject
	private StampCardRepository stampCardRepo;

	@Inject
	private EmployeeRecordAdapter sysEmpPub;

	@Inject
	private CompanyAdapter companyAdapter;

	@Inject
	private StampRecordRepository stampRecordRepo;

	@Inject
	private StampDakokuRepository stampDakokuRepo;

	@Inject
	private CreateDailyResultDomainService createDailyResultDomainSv;

	@Inject
	private StampCardEditingRepo stampCardEditRepo;
	
	@Inject
	private TopPageAlarmStampingRepository topPageRepo;
	
	@Inject
	private EmployeeManageAdapter empManaAdapter;

	@Override
	protected RegisterStampInputResult handle(CommandHandlerContext<RegisterStampInputCommand> context) {
		RegisterStampInputCommand cmd = context.getCommand();

		EnterStampFromPortalServiceRequireImpl require = new EnterStampFromPortalServiceRequireImpl(settingRepo,
				stampCardRepo, sysEmpPub, companyAdapter, stampRecordRepo, stampDakokuRepo, createDailyResultDomainSv,
				stampCardEditRepo);

		ContractCode contractCode = new ContractCode(AppContexts.user().contractCode());

		String employeeID = AppContexts.user().employeeId();

		GeneralDateTime datetime = cmd.getDatetime();

		ButtonPositionNo buttonPositionNo = new ButtonPositionNo(cmd.getButtonPositionNo());

		RefectActualResult refActualResults = cmd.getRefActualResults().toDomainValue();

		// 作成する(@Require, 契約コード, 社員ID, 日時, 打刻ボタン位置NO, 実績への反映内容)

		TimeStampInputResult inputResult = EnterStampFromPortalService.create(require, contractCode, employeeID, datetime,
				buttonPositionNo, refActualResults);

		if (inputResult == null) {
			return null;
		}

		// not 打刻入力結果 empty
		Optional<AtomTask> atomInput = inputResult.getAt();

		if (atomInput.isPresent()) {
			transaction.execute(() -> {
				atomInput.get().run();
			});
		}

		if (inputResult.getStampDataReflectResult() == null) {
			return null;
		}

		// not 打刻データ反映処理結果 empty
		AtomTask atomResult = inputResult.getStampDataReflectResult().getAtomTask();

		transaction.execute(() -> {
			atomResult.run();
		});

		Optional<GeneralDate> refDateOpt = inputResult.getStampDataReflectResult().getReflectDate();
		
		//打刻入力から日別実績を作成する (Tạo thực tế hàng ngày từ input check tay)
		CreateDailyResultsStampsRequireImpl resultStampRequire = new CreateDailyResultsStampsRequireImpl(topPageRepo,
				empManaAdapter);

		CreateDailyResultsStamps.create(resultStampRequire, AppContexts.user().companyId(), employeeID,
				Optional.ofNullable(refDateOpt.isPresent() ? refDateOpt.get() : null));
		
		
		return new RegisterStampInputResult(refDateOpt.isPresent() ? refDateOpt.get() : null);
	}
	
	
	
	@AllArgsConstructor
	private class CreateDailyResultsStampsRequireImpl implements CreateDailyResultsStamps.Require {
		
		@Inject
		private TopPageAlarmStampingRepository topPageRepo;
		
		@Inject
		private EmployeeManageAdapter empManaAdapter;

		@Override
		public List<ErrorMessageInfo> getListError(String companyID, String employeeId, DatePeriod period,
				int reCreateAtr, int i, EmpCalAndSumExeLog empCalAndSumExeLog, int i1) {
			return Arrays.asList(new ErrorMessageInfo(companyID, employeeId, period.start(),
					ExecutionContent.DAILY_CALCULATION, new ErrMessageResource("Test"), new ErrMessageContent("Test")));
		}

		@Override
		public void insert(TopPageAlarmStamping domain) {
			this.topPageRepo.insert(domain);
		}

		@Override
		public List<String> getListEmpID(String companyID, GeneralDate referenceDate) {
			return this.empManaAdapter.getListEmpID(companyID, referenceDate);
		}

	}

	@AllArgsConstructor
	private class EnterStampFromPortalServiceRequireImpl implements EnterStampFromPortalService.Require {
		@Inject
		private PortalStampSettingsRepository settingRepo;

		@Inject
		private StampCardRepository stampCardRepo;

		@Inject
		private EmployeeRecordAdapter sysEmpPub;

		@Inject
		private CompanyAdapter companyAdapter;

		@Inject
		private StampRecordRepository stampRecordRepo;

		@Inject
		private StampDakokuRepository stampDakokuRepo;

		@Inject
		private CreateDailyResultDomainService createDailyResultDomainSv;

		@Inject
		private StampCardEditingRepo stampCardEditRepo;

		@Override
		public List<StampCard> getLstStampCardBySidAndContractCd(String sid) {
			return this.stampCardRepo.getLstStampCardBySidAndContractCd(AppContexts.user().contractCode(), sid);
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

		@SuppressWarnings("rawtypes")
		@Override
		public ProcessState createDailyResult(AsyncCommandHandlerContext asyncContext, List<String> emloyeeIds,
				DatePeriod periodTime, ExecutionAttr executionAttr, String companyId, String empCalAndSumExecLogID,
				Optional<ExecutionLog> executionLog) {
			return this.createDailyResultDomainSv.createDailyResult(asyncContext, emloyeeIds, periodTime, executionAttr,
					companyId, empCalAndSumExecLogID, executionLog);
		}

		@Override
		public Optional<PortalStampSettings> getPortalStampSetting() {
			return this.settingRepo.get(AppContexts.user().companyId());
		}

		@Override
		public Optional<StampCard> getByCardNoAndContractCode(String stampNumber, String contractCode) {
			return this.stampCardRepo.getByCardNoAndContractCode(stampNumber, contractCode);
		}

		@Override
		public Optional<StampCardEditing> get(String companyId) {

			StampCardEditing stampCardEdit = this.stampCardEditRepo.get(companyId);
			return Optional.ofNullable(stampCardEdit);
		}

	}

}
