package nts.uk.ctx.at.record.app.command.kdp.kdp002.a;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.record.dom.adapter.employee.EmployeeDataMngInfoImport;
import nts.uk.ctx.at.record.dom.adapter.employee.EmployeeRecordAdapter;
import nts.uk.ctx.at.record.dom.adapter.employmentinfoterminal.infoterminal.EmpDataImport;
import nts.uk.ctx.at.record.dom.adapter.employmentinfoterminal.infoterminal.GetMngInfoFromEmpIDListAdapter;
import nts.uk.ctx.at.record.dom.dailyresultcreationprocess.creationprocess.creationclass.dailywork.ReflectStampDailyAttdOutput;
import nts.uk.ctx.at.record.dom.dailyresultcreationprocess.creationprocess.creationclass.dailywork.TemporarilyReflectStampDailyAttd;
import nts.uk.ctx.at.record.dom.stamp.application.SettingsUsingEmbossing;
import nts.uk.ctx.at.record.dom.stamp.application.SettingsUsingEmbossingRepository;
import nts.uk.ctx.at.record.dom.stamp.card.stamcardedit.StampCardEditing;
import nts.uk.ctx.at.record.dom.stamp.card.stamcardedit.StampCardEditingRepo;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCard;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCardRepository;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampNumber;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.Stamp;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampDakokuRepository;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampMeans;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampRecord;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampRecordRepository;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.CanEngravingUsed;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.MakeUseJudgmentResults;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.StampFunctionAvailableService;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.ChangeClockArt;
import nts.uk.ctx.at.shared.dom.adapter.holidaymanagement.CompanyAdapter;
import nts.uk.ctx.at.shared.dom.adapter.holidaymanagement.CompanyImport622;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSettingRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.i18n.TextResource;

@Stateless
public class ConfirmUseOfStampEmbossCommandHandler extends CommandHandler<ConfirmUseOfStampEmbossCommand> {

	@Inject
	private StampCardRepository stampCardRepo;

	@Inject
	private StampRecordRepository stampRecordRepo;

	@Inject
	private StampDakokuRepository stampDakokuRepo;

//	@Inject
//	protected WorkingConditionService workingConditionService;

	@Inject
	protected PredetemineTimeSettingRepository predetemineTimeSettingRepo;

	@Inject
	private SettingsUsingEmbossingRepository stampUsageRepo;

	@Inject
	private StampCardEditingRepo stampCardEditRepo;

	@Inject
	private EmployeeRecordAdapter sysEmpPub;

	@Inject
	private CompanyAdapter companyAdapter;
	
	@Inject
	private TemporarilyReflectStampDailyAttd temporarilyReflectStampDailyAttd;
	
	@Inject
	private StampCardRepository stampCardRepository;
	
	@Inject
	private GetMngInfoFromEmpIDListAdapter getMngInfoFromEmpIDListAdapter;
	
	@Override
	protected void handle(CommandHandlerContext<ConfirmUseOfStampEmbossCommand> context) {
		String employeeId = AppContexts.user().employeeId();

		StampFunctionAvailableRequiredImpl checkFuncRq = new StampFunctionAvailableRequiredImpl(stampUsageRepo,
				stampCardRepo, stampRecordRepo, stampDakokuRepo, stampCardEditRepo,
				sysEmpPub, companyAdapter, temporarilyReflectStampDailyAttd, stampCardRepository,
				getMngInfoFromEmpIDListAdapter);

		MakeUseJudgmentResults judgmentResults = StampFunctionAvailableService.decide(checkFuncRq, employeeId,
				StampMeans.INDIVITION);
		CanEngravingUsed used = judgmentResults.getUsed();

		if (used.equals(CanEngravingUsed.NOT_PURCHASED_STAMPING_OPTION)) {
			throw new BusinessException("Msg_1644");
		}

		if (used.equals(CanEngravingUsed.ENGTAVING_FUNCTION_CANNOT_USED)) {
			throw new BusinessException("Msg_1644", TextResource.localize("KDP002_1"));
		}

		if (used.equals(CanEngravingUsed.UNREGISTERED_STAMP_CARD)) {
			throw new BusinessException("Msg_1619");
		}

		Optional<AtomTask> atomOpt = judgmentResults.getCardResult().isPresent()
				? judgmentResults.getCardResult().get().getAtomTask()
				: Optional.ofNullable(null);

		atomOpt.ifPresent(atom -> {
			transaction.execute(() -> {
				atom.run();
			});
		});
	}

	@AllArgsConstructor
	private class StampFunctionAvailableRequiredImpl implements StampFunctionAvailableService.Require {

		@Inject
		private SettingsUsingEmbossingRepository stampUsageRepo;

		@Inject
		private StampCardRepository stampCardRepo;

		@Inject
		private StampRecordRepository stampRecordRepo;

		@Inject
		private StampDakokuRepository stampDakokuRepo;

		@Inject
		private StampCardEditingRepo stampCardEditRepo;

		@Inject
		private EmployeeRecordAdapter sysEmpPub;

		@Inject
		private CompanyAdapter companyAdapter;

		private TemporarilyReflectStampDailyAttd temporarilyReflectStampDailyAttd;
		
		private StampCardRepository stampCardRepository;
		
		private GetMngInfoFromEmpIDListAdapter getMngInfoFromEmpIDListAdapter;
		
		@Override
		public List<StampCard> getListStampCard(String sid) {
			return stampCardRepo.getListStampCard(sid);
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
		public Optional<StampCardEditing> get(String companyId) {
			return Optional.ofNullable(this.stampCardEditRepo.get(companyId));
		}

		@Override
		public void add(StampCard domain) {
			this.stampCardRepo.add(domain);
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
		public Optional<StampCard> getByCardNoAndContractCode(String stampNumber, String contractCode) {
			return this.stampCardRepo.getByCardNoAndContractCode(stampNumber, contractCode);
		}

		
		@Override
		public boolean existsStamp(ContractCode contractCode, StampNumber stampNumber, GeneralDateTime dateTime,
				ChangeClockArt changeClockArt) {
			return stampDakokuRepo.existsStamp(contractCode, stampNumber, dateTime, changeClockArt);
		}
		
		@Override
		public Optional<StampCard> getByCardNoAndContractCode(ContractCode contractCode, StampNumber stampNumber) {
			return stampCardRepository.getByCardNoAndContractCode(stampNumber.v(), contractCode.v());
		}

		@Override
		public Optional<ReflectStampDailyAttdOutput> createDailyDomAndReflectStamp(String cid, String employeeId,
				GeneralDate date, Stamp stamp) {
			return temporarilyReflectStampDailyAttd.createDailyDomAndReflectStamp(cid, employeeId, date, stamp);
		}

		@Override
		public List<EmpDataImport> getEmpData(List<String> empIDList) {
			return getMngInfoFromEmpIDListAdapter.getEmpData(empIDList);
		}
	}

}
