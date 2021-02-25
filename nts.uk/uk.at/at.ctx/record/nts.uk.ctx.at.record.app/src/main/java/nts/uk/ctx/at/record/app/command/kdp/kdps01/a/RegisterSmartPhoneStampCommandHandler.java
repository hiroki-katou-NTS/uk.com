package nts.uk.ctx.at.record.app.command.kdp.kdps01.a;

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
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.EnterStampFromSmartPhoneService;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.StampDataReflectResult;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.TimeStampInputResult;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.SettingsSmartphoneStamp;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.SettingsSmartphoneStampRepository;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.ExecutionLog;
import nts.uk.ctx.at.shared.dom.adapter.holidaymanagement.CompanyAdapter;
import nts.uk.ctx.at.shared.dom.adapter.holidaymanagement.CompanyImport622;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author sonnlb
 *
 *         UKDesign.UniversalK.就業.KDP_打刻.KDPS01_打刻入力(スマホ).A:打刻入力(スマホ).メニュー別OCD.打刻入力(スマホ)を登録する
 */
@Stateless
public class RegisterSmartPhoneStampCommandHandler
		extends CommandHandlerWithResult<RegisterSmartPhoneStampCommand, GeneralDate> {

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

	@Inject
	private SettingsSmartphoneStampRepository getSettingRepo;

	@Override
	protected GeneralDate handle(CommandHandlerContext<RegisterSmartPhoneStampCommand> context) {
		RegisterSmartPhoneStampCommand cmd = context.getCommand();

		// 1:require, 契約コード, ログイン社員ID, 打刻日時, 打刻ボタン, 地理座標, 実績への反映内容

		EnterStampFromSmartPhoneServiceImpl require = new EnterStampFromSmartPhoneServiceImpl(stampCardRepo,
				stampCardEditRepo, companyAdapter, sysEmpPub, stampRecordRepo, stampDakokuRepo,
				createDailyResultDomainSv, getSettingRepo);

		TimeStampInputResult stampRes = EnterStampFromSmartPhoneService.create(require,
				new ContractCode(AppContexts.user().contractCode()), AppContexts.user().employeeId(),
				cmd.getStampDatetime(), cmd.getStampButton().toDomainValue(),
				Optional.ofNullable(cmd.getGeoCoordinate().toDomainValue()), cmd.getRefActualResult().toDomainValue());
		// 2.1:not 打刻入力結果 empty

		if (stampRes != null) {

			stampRes.getAt().ifPresent(x -> {
				transaction.execute(() -> {
					x.run();
				});
			});

			// 2.2:not 打刻データ反映処理結果 empty
			StampDataReflectResult stampRef = stampRes.getStampDataReflectResult();
			if (stampRef != null) {
				transaction.execute(() -> {
					stampRef.getAtomTask().run();
				});

				return stampRef.getReflectDate().map(x -> x).orElse(null);
			}
		}

		return null;
	}

	@AllArgsConstructor
	private class EnterStampFromSmartPhoneServiceImpl implements EnterStampFromSmartPhoneService.Require {

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

		@Inject
		private SettingsSmartphoneStampRepository getSettingRepo;

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
		public ProcessState createDailyResult(@SuppressWarnings("rawtypes") AsyncCommandHandlerContext asyncContext,
				List<String> emloyeeIds, DatePeriod periodTime, ExecutionAttr executionAttr, String companyId,
				String empCalAndSumExecLogID, Optional<ExecutionLog> executionLog) {
			return this.createDailyResultDomainSv.createDailyResult(asyncContext, emloyeeIds, periodTime, executionAttr,
					companyId, empCalAndSumExecLogID, executionLog);
		}

		@Override
		public Optional<SettingsSmartphoneStamp> getSmartphoneStampSetting() {
			return this.getSettingRepo.get(AppContexts.user().companyId());
		}

	}

}
