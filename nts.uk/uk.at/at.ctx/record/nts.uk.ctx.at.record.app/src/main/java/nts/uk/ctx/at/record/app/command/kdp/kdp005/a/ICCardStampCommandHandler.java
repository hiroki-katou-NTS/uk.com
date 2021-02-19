package nts.uk.ctx.at.record.app.command.kdp.kdp005.a;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.arc.layer.app.command.AsyncCommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
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
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.Stamp;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampDakokuRepository;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampRecord;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampRecordRepository;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.EnterStampFromICCardService;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.StampDataReflectResult;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.StampingResultEmployeeId;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.StampSetCommunal;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.StampSetCommunalRepository;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.ExecutionLog;
import nts.uk.ctx.at.shared.dom.adapter.holidaymanagement.CompanyAdapter;
import nts.uk.ctx.at.shared.dom.adapter.holidaymanagement.CompanyImport622;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author thanhpv
 *
 */
@Stateless
public class ICCardStampCommandHandler extends CommandHandlerWithResult<ICCardStampCommand,GeneralDate> {

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
	private CreateDailyResultDomainService createDailyResultDomainSv;

	@Override
	protected GeneralDate handle(CommandHandlerContext<ICCardStampCommand> context) {
		String contractCode = AppContexts.user().contractCode(); 
		
		ICCardStampCommand cmd = context.getCommand();

		EnterStampFromICCardServiceRequireImpl require = new EnterStampFromICCardServiceRequireImpl(stampSetCommunalRepository, stampCardRepo, stampCardEditRepo, companyAdapter, sysEmpPub, stampRecordRepo, stampDakokuRepo, createDailyResultDomainSv);

		//作成する(@Require, 契約コード, 日時, 打刻カード番号, 打刻ボタン, 実績への反映内容)
		StampingResultEmployeeId stampingResultEmployeeId = EnterStampFromICCardService.create(require, new ContractCode(contractCode), cmd.getStampNumber(), cmd.getStampDatetime(), cmd.getStampButton(), cmd.getRefActualResult().toDomainValue());
		
		//2: not empty
		if (stampingResultEmployeeId.inputResult.at.isPresent()) {

			transaction.execute(() -> {
				stampingResultEmployeeId.inputResult.at.get().run();
			});
		}

		StampDataReflectResult stampRefResult = stampingResultEmployeeId.inputResult.getStampDataReflectResult();

		if (stampRefResult.getAtomTask() != null) {

			transaction.execute(() -> {
				stampRefResult.getAtomTask().run();
			});
		}
		return stampRefResult.getReflectDate().isPresent() ? stampRefResult.getReflectDate().get() : null;
	}

	@AllArgsConstructor
	private class EnterStampFromICCardServiceRequireImpl implements EnterStampFromICCardService.Require {
		
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
		private CreateDailyResultDomainService createDailyResultDomainSv;

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
		public ProcessState createDailyResult(AsyncCommandHandlerContext asyncContext, List<String> emloyeeIds,
				DatePeriod periodTime, ExecutionAttr executionAttr, String companyId, String empCalAndSumExecLogID,
				Optional<ExecutionLog> executionLog) {
			return this.createDailyResultDomainSv.createDailyResult(asyncContext, emloyeeIds, periodTime, executionAttr,
					companyId, empCalAndSumExecLogID, executionLog);
		}

		@Override
		public Optional<StampSetCommunal> gets() {
			return this.stampSetCommunalRepository.gets(AppContexts.user().companyId());
		}

	}

}
