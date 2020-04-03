package nts.uk.ctx.at.record.app.find.stamp.management.personalengraving;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.app.find.stamp.management.personalengraving.dto.KDP002AStartPageSettingDto;
import nts.uk.ctx.at.record.dom.stamp.application.StampResultDisplay;
import nts.uk.ctx.at.record.dom.stamp.application.StampResultDisplayRepository;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCard;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCardRepository;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampNumber;
import nts.uk.ctx.at.record.dom.stamp.management.StampSetPerRepository;
import nts.uk.ctx.at.record.dom.stamp.management.StampSettingPerson;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.Stamp;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampDakokuRepository;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampMeans;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampRecord;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampRecordRepository;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.GetEmpStampDataService;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.GetStampTypeToSuppressService;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.GetTimeCardService;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.StampDataOfEmployees;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.StampToSuppress;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.TimeCard;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingOfDailyPerformance;
import nts.uk.ctx.at.record.dom.worktime.repository.TimeLeavingOfDailyPerformanceRepository;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workingcondition.service.WorkingConditionService;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSettingRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author anhdt
 * 
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

	// 打刻入力(個人)の設定を取得する
	public KDP002AStartPageSettingDto getSettings() {

		String companyId = AppContexts.user().companyId();
		String employeeId = AppContexts.user().employeeId();
		// 1
		Optional<StampSettingPerson> stampSetting = stampSetPerRepo.getStampSetting(companyId);

		// 2
		Optional<StampResultDisplay> stampResultDisplay = stampResultDisplayRepository.getStampSet(companyId);

		// 3
		TimeCardRequiredImpl required = new TimeCardRequiredImpl(timeLeavingOfDailyPerformanceRepository);
		TimeCard timeCard = GetTimeCardService.getTimeCard(required, employeeId, GeneralDate.today().yearMonth());

		// 4
		DatePeriod period = new DatePeriod(GeneralDate.today().addDays(-3), GeneralDate.today());
		List<StampDataOfEmployees> employeeStampDatas = new ArrayList<>();
		EmpStampDataRequiredImpl empStampDataR = new EmpStampDataRequiredImpl(stampCardRepo, stampRecordRepo,
				stampDakokuRepo);
		for (GeneralDate date : period.datesBetween()) {
			Optional<StampDataOfEmployees> employeeStampData = GetEmpStampDataService.get(empStampDataR, employeeId,
					date);
			if (employeeStampData.isPresent()) {
				employeeStampDatas.add(employeeStampData.get());
			}
		}

		// 5
		StampTypeToSuppressRequiredImpl stampTypeToSuppressR = new StampTypeToSuppressRequiredImpl(stampCardRepo,
				stampRecordRepo, stampDakokuRepo, stampSetPerRepo, workingConditionService, predetemineTimeSettingRepo);
		
		StampToSuppress stampToSuppress = GetStampTypeToSuppressService.get(stampTypeToSuppressR, employeeId, StampMeans.INDIVITION);

		return new KDP002AStartPageSettingDto(stampSetting, stampResultDisplay, timeCard);

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
	private class EmpStampDataRequiredImpl implements GetEmpStampDataService.Require {

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
			return stampDakokuRepo.get(stampNumbers, date);
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

		public StampTypeToSuppressRequiredImpl(StampCardRepository stampCardRepo, StampRecordRepository stampRecordRepo,
				StampDakokuRepository stampDakokuRepo, StampSetPerRepository stampSetPerRepo,
				WorkingConditionService workingConditionService,
				PredetemineTimeSettingRepository predetemineTimeSettingRepo) {
			super(stampCardRepo, stampRecordRepo, stampDakokuRepo);
			this.stampSetPerRepo = stampSetPerRepo;
			this.workingConditionService = workingConditionService;
			this.predetemineTimeSettingRepo = predetemineTimeSettingRepo;
		}

		@Override
		public Optional<StampSettingPerson> getStampSet() {
			return stampSetPerRepo.getStampSet(AppContexts.user().companyId());
		}

		@Override
		public Optional<WorkingConditionItem> findWorkConditionByEmployee(String employeeId, GeneralDate baseDate) {
			return workingConditionService.findWorkConditionByEmployee(employeeId, baseDate);
		}

		@Override
		public Optional<PredetemineTimeSetting> findByWorkTimeCode(String workTimeCode) {
			return predetemineTimeSettingRepo.findByWorkTimeCode(AppContexts.user().companyId(), workTimeCode);
		}

	}

}
