package nts.uk.ctx.at.record.app.command.kdp.kdp003.a;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.AsyncCommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDateTime;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.app.command.kdp.kdp001.a.ConfirmUseOfStampInputResult;
import nts.uk.ctx.at.record.dom.adapter.employee.EmployeeDataMngInfoImport;
import nts.uk.ctx.at.record.dom.adapter.employee.EmployeeRecordAdapter;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.output.ExecutionAttr;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.CreateDailyResultDomainService;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.CreateDailyResultDomainServiceImpl.ProcessState;
import nts.uk.ctx.at.record.dom.stamp.application.SettingsUsingEmbossing;
import nts.uk.ctx.at.record.dom.stamp.application.SettingsUsingEmbossingRepository;
import nts.uk.ctx.at.record.dom.stamp.card.stamcardedit.StampCardEditing;
import nts.uk.ctx.at.record.dom.stamp.card.stamcardedit.StampCardEditingRepo;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCard;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCardCreateResult;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCardRepository;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.Stamp;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampDakokuRepository;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampMeans;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampRecord;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampRecordRepository;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.MakeUseJudgmentResults;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.StampFunctionAvailableService;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.ExecutionLog;
import nts.uk.ctx.at.shared.dom.adapter.holidaymanagement.CompanyAdapter;
import nts.uk.ctx.at.shared.dom.adapter.holidaymanagement.CompanyImport622;
import nts.uk.shr.com.context.AppContexts;

/***
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.勤務実績.打刻管理.打刻.App.打刻入力を利用できるかを確認する
 * @author vuongnv
 *
 */
@Stateless
public class ConfirmUseOfStampInputWithEmployeeIdCommandHandler 
extends CommandHandlerWithResult<ConfirmUseOfStampInputCommandWithEmployeeId, ConfirmUseOfStampInputResult> {

	@Inject
	private SettingsUsingEmbossingRepository stampUsageRepo;

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
	private CreateDailyResultDomainService createDailyResultDomainSv;

	@Override
	protected ConfirmUseOfStampInputResult handle(
			CommandHandlerContext<ConfirmUseOfStampInputCommandWithEmployeeId> context) {

		StampFunctionAvailableServiceRequireImpl require = new StampFunctionAvailableServiceRequireImpl(stampUsageRepo,
				stampCardRepo, stampCardEditRepo, companyAdapter, sysEmpPub, stampRecordRepo, stampDakokuRepo,
				createDailyResultDomainSv);

		StampMeans stampMeans = EnumAdaptor.valueOf(context.getCommand().getStampMeans(), StampMeans.class);

		String employeeId = context.getCommand().getEmployeeId();
		// 1. 判断する(@Require, 社員ID, 打刻手段)
		MakeUseJudgmentResults jugResult = StampFunctionAvailableService.decide(require, employeeId, stampMeans);
		// not 打刻カード作成結果 empty
		Optional<StampCardCreateResult> cradResultOpt = jugResult.getCardResult();

		if (cradResultOpt.isPresent()) {

			Optional<AtomTask> atom = cradResultOpt.get().getAtomTask();
			transaction.execute(() -> {
				atom.get().run();
			});
			return new ConfirmUseOfStampInputResult(GeneralDateTime.now(), jugResult.getUsed().value);
		}
		return new ConfirmUseOfStampInputResult(GeneralDateTime.now(), jugResult.getUsed().value);
	}

	@AllArgsConstructor
	private class StampFunctionAvailableServiceRequireImpl implements StampFunctionAvailableService.Require {
		
		@Inject
		private SettingsUsingEmbossingRepository stampUsageRepo;

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
		private CreateDailyResultDomainService createDailyResultDomainSv;

		@Override
		public List<EmployeeDataMngInfoImport> findBySidNotDel(List<String> sids) {
			return this.sysEmpPub.findBySidNotDel(sids);
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
		public void add(StampCard domain) {
			this.stampCardRepo.add(domain);
		}

		@Override
		public List<StampCard> getListStampCard(String sid) {
			return this.stampCardRepo.getListStampCard(sid);
		}

		@Override
		public Optional<SettingsUsingEmbossing> get() {
			return this.stampUsageRepo.get(AppContexts.user().companyId());
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
		@SuppressWarnings("rawtypes")
		public ProcessState createDailyResult(AsyncCommandHandlerContext asyncContext, List<String> emloyeeIds,
				DatePeriod periodTime, ExecutionAttr executionAttr, String companyId, String empCalAndSumExecLogID,
				Optional<ExecutionLog> executionLog) {
			return this.createDailyResultDomainSv.createDailyResult(asyncContext, emloyeeIds, periodTime, executionAttr,
					companyId, empCalAndSumExecLogID, executionLog);
		}

		@Override
		public Optional<StampCard> getByCardNoAndContractCode(String stampNumber, String contractCode) {
			return this.stampCardRepo.getByCardNoAndContractCode(stampNumber, contractCode);
		}

	}
}
