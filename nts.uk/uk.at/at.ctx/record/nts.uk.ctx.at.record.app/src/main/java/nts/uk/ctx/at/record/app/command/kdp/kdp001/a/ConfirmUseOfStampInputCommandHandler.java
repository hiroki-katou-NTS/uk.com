package nts.uk.ctx.at.record.app.command.kdp.kdp001.a;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
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
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCardCreateResult;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCardRepository;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampNumber;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.Stamp;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampDakokuRepository;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampMeans;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampRecord;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampRecordRepository;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.MakeUseJudgmentResults;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.StampFunctionAvailableService;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.ChangeClockArt;
import nts.uk.ctx.at.shared.dom.adapter.holidaymanagement.CompanyAdapter;
import nts.uk.ctx.at.shared.dom.adapter.holidaymanagement.CompanyImport622;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author sonnlb
 * 
 *         UKDesign.UniversalK.就業.KDP_打刻.KDP001_打刻入力(ポータル).A:打刻入力(ポータル).メニュー別OCD.打刻入力(ポータル)の利用確認を行う
 *
 */
@Stateless
public class ConfirmUseOfStampInputCommandHandler
		extends CommandHandlerWithResult<ConfirmUseOfStampInputCommand, ConfirmUseOfStampInputResult> {

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
	private TemporarilyReflectStampDailyAttd temporarilyReflectStampDailyAttd;
	
	@Inject
	private GetMngInfoFromEmpIDListAdapter getMngInfoFromEmpIDListAdapter;
	
	@Override
	protected ConfirmUseOfStampInputResult handle(CommandHandlerContext<ConfirmUseOfStampInputCommand> context) {

		StampFunctionAvailableServiceRequireImpl require = new StampFunctionAvailableServiceRequireImpl(stampUsageRepo,
				stampCardRepo, stampCardEditRepo, companyAdapter, sysEmpPub, stampRecordRepo, stampDakokuRepo,
				temporarilyReflectStampDailyAttd, getMngInfoFromEmpIDListAdapter);

		StampMeans stampMeans = EnumAdaptor.valueOf(context.getCommand().getStampMeans(), StampMeans.class);

		String employeeId = AppContexts.user().employeeId();
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
		
		private TemporarilyReflectStampDailyAttd temporarilyReflectStampDailyAttd;
		
		private GetMngInfoFromEmpIDListAdapter getMngInfoFromEmpIDListAdapter;
		
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
			return stampCardRepo.getByCardNoAndContractCode(stampNumber.v(), contractCode.v());
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
