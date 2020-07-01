package nts.uk.ctx.at.record.app.find.stamp.management.personalengraving;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.AsyncCommandHandlerContext;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.app.find.stamp.management.personalengraving.dto.KDP002AStartPageOutput;
import nts.uk.ctx.at.record.dom.adapter.employee.EmployeeDataMngInfoImport;
import nts.uk.ctx.at.record.dom.adapter.employee.EmployeeRecordAdapter;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.output.ExecutionAttr;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.CreateDailyResultDomainService;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.CreateDailyResultDomainServiceImpl.ProcessState;
import nts.uk.ctx.at.record.dom.stamp.application.SettingsUsingEmbossing;
import nts.uk.ctx.at.record.dom.stamp.application.SettingsUsingEmbossingRepository;
import nts.uk.ctx.at.record.dom.stamp.application.StampResultDisplay;
import nts.uk.ctx.at.record.dom.stamp.application.StampResultDisplayRepository;
import nts.uk.ctx.at.record.dom.stamp.card.stamcardedit.StampCardEditing;
import nts.uk.ctx.at.record.dom.stamp.card.stamcardedit.StampCardEditingRepo;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCard;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCardRepository;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampNumber;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.Stamp;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampDakokuRepository;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampMeans;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampRecord;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampRecordRepository;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.CanEngravingUsed;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.EmployeeStampInfo;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.GetListStampEmployeeService;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.GetStampTypeToSuppressService;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.GetTimeCardService;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.MakeUseJudgmentResults;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.StampFunctionAvailableService;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.StampToSuppress;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.TimeCard;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.PortalStampSettings;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.PortalStampSettingsRepository;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.SettingsSmartphoneStamp;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.SettingsSmartphoneStampRepository;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.StampSetPerRepository;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.StampSettingPerson;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.ExecutionLog;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingOfDailyPerformance;
import nts.uk.ctx.at.record.dom.worktime.repository.TimeLeavingOfDailyPerformanceRepository;
import nts.uk.ctx.at.shared.dom.adapter.holidaymanagement.CompanyAdapter;
import nts.uk.ctx.at.shared.dom.adapter.holidaymanagement.CompanyImport622;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workingcondition.service.WorkingConditionService;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSettingRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.i18n.TextResource;

/**
 * @author anhdt
 * 打刻入力(個人)の設定を取得する
 */
@Stateless
public class StampSettingsEmbossFinder {

	@Inject
	private StampSetPerRepository stampSetPerRepo;

	@Inject
	private StampResultDisplayRepository stampResultDisplayRepository;

	@Inject
	private TimeLeavingOfDailyPerformanceRepository timeLeavingOfDailyPerformanceRepository;

	@Inject
	private StampCardRepository stampCardRepo;

	@Inject
	private StampRecordRepository stampRecordRepo;

	@Inject
	private StampDakokuRepository stampDakokuRepo;

	@Inject
	protected WorkingConditionService workingConditionService;

	@Inject
	protected PredetemineTimeSettingRepository predetemineTimeSettingRepo;
	
	@Inject
	private SettingsSmartphoneStampRepository settingsSmartphoneStampRepo;

	@Inject
	private PortalStampSettingsRepository portalStampSettingsrepo;
	
	@Inject
	private SettingsUsingEmbossingRepository stampUsageRepo;

	@Inject
	private CreateDailyResultDomainService createDailyResultDomainSv;
	
	@Inject
	private StampCardEditingRepo stampCardEditRepo;
	
	@Inject
	private EmployeeRecordAdapter sysEmpPub;

	@Inject
	private CompanyAdapter companyAdapter;

	// 
	public KDP002AStartPageOutput getSettings() {

		String companyId = AppContexts.user().companyId();
		String employeeId = AppContexts.user().employeeId();
		
		StampFunctionAvailableRequiredImpl checkFuncRq = new StampFunctionAvailableRequiredImpl(stampUsageRepo,
				stampCardRepo, stampRecordRepo, stampDakokuRepo, createDailyResultDomainSv, stampCardEditRepo,
				sysEmpPub, companyAdapter);
		
		MakeUseJudgmentResults judgmentResults = StampFunctionAvailableService.decide(checkFuncRq, employeeId,StampMeans.INDIVITION);
		CanEngravingUsed used = judgmentResults.getUsed();
		
		if (used.equals(CanEngravingUsed.NOT_PURCHASED_STAMPING_OPTION)) {
			throw new BusinessException("Msg_1644");
		}

		if (used.equals(CanEngravingUsed.ENGTAVING_FUNCTION_CANNOT_USED)) {
			throw new BusinessException("Msg_1645", TextResource.localize("KDP002_1"));
		}

		if (used.equals(CanEngravingUsed.UNREGISTERED_STAMP_CARD)) {
			throw new BusinessException("Msg_1619");
		}
		
		
//		Optional<AtomTask> atomOpt = judgmentResults.getCardResult().get().getAtomTask();

//		if (atomOpt.isPresent()) {
//			transaction.execute(() -> {
//				atomOpt.get().run();
//			});
	//	}
		
		// 1
		Optional<StampSettingPerson> stampSetting = stampSetPerRepo.getStampSetting(companyId);
		
		// 2
		Optional<StampResultDisplay> stampResultDisplay = stampResultDisplayRepository.getStampSet(companyId);

		// 3 DS: タイムカードを取得する
		TimeCard timeCard = getTimeCard(employeeId, GeneralDate.today());

		// 4  DS 社員の打刻一覧を取得する
		DatePeriod period = new DatePeriod(GeneralDate.today().addDays(-3), GeneralDate.today());
		List<EmployeeStampInfo> employeeStampDatas = getEmployeeStampDatas(period, employeeId);

		// 5 抑制する打刻種類を取得する
		StampToSuppress stampToSuppress = getStampToSuppress(employeeId);

		return new KDP002AStartPageOutput(stampSetting, stampResultDisplay, timeCard, employeeStampDatas, stampToSuppress);
	}
	
	public List<EmployeeStampInfo> getEmployeeStampDatas(DatePeriod period, String employeeId) {
		List<EmployeeStampInfo> employeeStampDatas = new ArrayList<>();
		EmpStampDataRequiredImpl empStampDataR = new EmpStampDataRequiredImpl(stampCardRepo, stampRecordRepo,
				stampDakokuRepo);
		List<GeneralDate> betweens = period.datesBetween();
		betweens.sort((d1, d2) -> d2.compareTo(d1));
		for (GeneralDate date : betweens) {
			// 4  DS 社員の打刻一覧を取得する
			Optional<EmployeeStampInfo> employeeStampData = GetListStampEmployeeService.get(empStampDataR, employeeId,
					date);
			if (employeeStampData.isPresent()) {
				employeeStampDatas.add(employeeStampData.get());
			}
		}
		
		return employeeStampDatas;
	}
	
	public StampToSuppress getStampToSuppress(String employeeId) {
		StampTypeToSuppressRequiredImpl stampTypeToSuppressR = new StampTypeToSuppressRequiredImpl(stampCardRepo,
				stampRecordRepo, stampDakokuRepo, stampSetPerRepo, workingConditionService, predetemineTimeSettingRepo,
				settingsSmartphoneStampRepo, portalStampSettingsrepo);
		
		return GetStampTypeToSuppressService.get(stampTypeToSuppressR, employeeId, StampMeans.INDIVITION);
	} 
	
	public TimeCard getTimeCard(String employeeId, GeneralDate date) {
		TimeCardRequiredImpl required = new TimeCardRequiredImpl(timeLeavingOfDailyPerformanceRepository);
		return GetTimeCardService.getTimeCard(required, employeeId, date.yearMonth());
	}

	@AllArgsConstructor
	private class TimeCardRequiredImpl implements GetTimeCardService.Require {

		@Inject
		private TimeLeavingOfDailyPerformanceRepository timeLeavingOfDailyPerformanceRepository;

		@Override
		public List<TimeLeavingOfDailyPerformance> findbyPeriodOrderByYmd(String employeeId, DatePeriod datePeriod) {
			return timeLeavingOfDailyPerformanceRepository.findbyPeriodOrderByYmd(employeeId, datePeriod);
		}

	}

	@AllArgsConstructor
	private class EmpStampDataRequiredImpl implements GetListStampEmployeeService.Require {

		@Inject
		protected StampCardRepository stampCardRepo;

		@Inject
		protected StampRecordRepository stampRecordRepo;

		@Inject
		protected StampDakokuRepository stampDakokuRepo;

		@Override
		public List<StampCard> getListStampCard(String sid) {
			return stampCardRepo.getListStampCard(sid);
		}

		@Override
		public List<StampRecord> getStampRecord(List<StampNumber> stampNumbers, GeneralDate date) {
			return stampRecordRepo.get(stampNumbers, date);
		}

		@Override
		public List<Stamp> getStamp(List<StampNumber> stampNumbers, GeneralDate date) {
			return stampDakokuRepo.get(AppContexts.user().contractCode(),stampNumbers, date);
		}

	}

	private class StampTypeToSuppressRequiredImpl extends EmpStampDataRequiredImpl
			implements GetStampTypeToSuppressService.Require {

		@Inject
		protected StampSetPerRepository stampSetPerRepo;

		@Inject
		protected WorkingConditionService workingConditionService;

		@Inject
		protected PredetemineTimeSettingRepository predetemineTimeSettingRepo;
		
		@Inject
		private SettingsSmartphoneStampRepository settingsSmartphoneStampRepo;

		@Inject
		private PortalStampSettingsRepository portalStampSettingsrepo;

		public StampTypeToSuppressRequiredImpl(StampCardRepository stampCardRepo, StampRecordRepository stampRecordRepo,
				StampDakokuRepository stampDakokuRepo, StampSetPerRepository stampSetPerRepo,
				WorkingConditionService workingConditionService,
				PredetemineTimeSettingRepository predetemineTimeSettingRepo,
				SettingsSmartphoneStampRepository settingsSmartphoneStampRepo,
				PortalStampSettingsRepository portalStampSettingsrepo) {
			super(stampCardRepo, stampRecordRepo, stampDakokuRepo);
			this.stampSetPerRepo = stampSetPerRepo;
			this.workingConditionService = workingConditionService;
			this.predetemineTimeSettingRepo = predetemineTimeSettingRepo;
			this.settingsSmartphoneStampRepo = settingsSmartphoneStampRepo;
			this.portalStampSettingsrepo = portalStampSettingsrepo;
		}

		@Override
		public Optional<WorkingConditionItem> findWorkConditionByEmployee(String employeeId, GeneralDate baseDate) {
			return this.workingConditionService.findWorkConditionByEmployee(employeeId, baseDate);
		}

		@Override
		public Optional<PredetemineTimeSetting> findByWorkTimeCode(String workTimeCode) {
			String companyId = AppContexts.user().companyId();
			return this.predetemineTimeSettingRepo.findByWorkTimeCode(companyId, workTimeCode);
		}

		@Override
		public Optional<StampSettingPerson> getStampSet(String companyId) {
			return this.stampSetPerRepo.getStampSet(companyId);
		}

		@Override
		public Optional<SettingsSmartphoneStamp> getSettingsSmartphone(String companyId) {
			return this.settingsSmartphoneStampRepo.get(companyId);
		}

		@Override
		public Optional<PortalStampSettings> getPotalSettings(String comppanyID) {
			return this.portalStampSettingsrepo.get(comppanyID);
		}



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
		private CreateDailyResultDomainService createDailyResultDomainSv;
		
		@Inject
		private StampCardEditingRepo stampCardEditRepo;
		
		@Inject
		private EmployeeRecordAdapter sysEmpPub;

		@Inject
		private CompanyAdapter companyAdapter;
		
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
		public ProcessState createDailyResult(AsyncCommandHandlerContext asyncContext, List<String> emloyeeIds,
				DatePeriod periodTime, ExecutionAttr executionAttr, String companyId, String empCalAndSumExecLogID,
				Optional<ExecutionLog> executionLog) {
			return this.createDailyResultDomainSv.createDailyResult(asyncContext, emloyeeIds, periodTime, executionAttr,
					companyId, empCalAndSumExecLogID, executionLog);
		}

		@Override
		public Optional<Stamp> get(String contractCode, String stampNumber) {
			return this.stampDakokuRepo.get(contractCode, new StampNumber(stampNumber));
		}
		
	}

}
