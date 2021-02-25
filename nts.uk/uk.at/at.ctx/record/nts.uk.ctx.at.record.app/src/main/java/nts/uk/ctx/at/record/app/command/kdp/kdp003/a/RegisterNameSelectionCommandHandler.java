package nts.uk.ctx.at.record.app.command.kdp.kdp003.a;

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
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCard;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCardRepository;
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
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.StampSetCommunal;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.StampSetCommunalRepository;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.ExecutionLog;
import nts.uk.ctx.at.shared.dom.adapter.holidaymanagement.CompanyAdapter;
import nts.uk.ctx.at.shared.dom.adapter.holidaymanagement.CompanyImport622;
import nts.uk.shr.com.context.AppContexts;

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
	private CreateDailyResultDomainService createDailyResultDomainSv;

	@Override
	protected GeneralDate handle(CommandHandlerContext<RegisterNameSelectionCommand> context) {
		RegisterNameSelectionCommand cmd = context.getCommand();

		EnterStampForSharedStampServiceRequireImpl require = new EnterStampForSharedStampServiceRequireImpl(
																												stampSetCommunalRepository
																												, stampCardRepo
																												, stampCardEditRepo
																												, companyAdapter
																												, sysEmpPub
																												, stampRecordRepo
																												, stampDakokuRepo
																												, createDailyResultDomainSv);

		//require, 契約コード, 社員ID, なし, 打刻する方法, 打刻日時, 打刻ボタン, 実績への反映内容
		TimeStampInputResult inputResult = EnterStampForSharedStampService.create(
																						require
																						, AppContexts.user().contractCode()
																						, cmd.getEmployeeId()
																						, Optional.ofNullable(null)
																						, new Relieve(AuthcMethod.ID_AUTHC, StampMeans.NAME_SELECTION)
																						, cmd.getDateTime()
																						, cmd.getStampButton()
																						, cmd.getRefActualResult().toDomainValue());
		//2: not empty
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
		
		return stampRefResult.getReflectDate().map(x-> x).orElse(null);
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

		@SuppressWarnings("rawtypes")
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
