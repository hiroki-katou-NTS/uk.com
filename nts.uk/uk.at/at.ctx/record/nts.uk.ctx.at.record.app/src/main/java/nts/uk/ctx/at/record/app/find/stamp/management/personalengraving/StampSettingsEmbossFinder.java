package nts.uk.ctx.at.record.app.find.stamp.management.personalengraving;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.app.find.stamp.management.personalengraving.dto.KDP002AStartPageOutput;
import nts.uk.ctx.at.record.dom.stamp.application.StampResultDisplay;
import nts.uk.ctx.at.record.dom.stamp.application.StampResultDisplayRepository;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCard;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCardRepository;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampNumber;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.Stamp;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampDakokuRepository;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampMeans;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampRecord;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampRecordRepository;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.EmployeeStampInfo;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.GetListStampEmployeeService;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.GetStampTypeToSuppressService;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.GetTimeCardService;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.StampFunctionAvailableService;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.StampToSuppress;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.TimeCard;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.StampSetPerRepository;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.StampSettingPerson;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingOfDailyPerformance;
import nts.uk.ctx.at.record.dom.worktime.repository.TimeLeavingOfDailyPerformanceRepository;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingCondition;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemRepository;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionRepository;
import nts.uk.ctx.at.shared.dom.workingcondition.service.WorkingConditionService;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSettingRepository;
import nts.uk.shr.com.context.AppContexts;

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
	protected PredetemineTimeSettingRepository predetemineTimeSettingRepo;
	
	@Inject
	protected WorkingConditionRepository workingConditionRepo;
	
	@Inject
	protected WorkingConditionItemRepository workingConditionItemRepo;

	// 
	public KDP002AStartPageOutput getSettings() {

		String companyId = AppContexts.user().companyId();
		String employeeId = AppContexts.user().employeeId();
		
		StampFunctionAvailableRequiredImpl checkFuncRq = new StampFunctionAvailableRequiredImpl();
		
		boolean isAvailable = StampFunctionAvailableService.decide(checkFuncRq, employeeId);
		
		if(!isAvailable) {
			throw new BusinessException("Msg_1619");
		}
		
		// 1
		Optional<StampSettingPerson> stampSetting = stampSetPerRepo.getStampSetting(companyId);
		
		if(!stampSetting.isPresent()) {
			throw new BusinessException("Msg_1644");
		}
		
		if(CollectionUtil.isEmpty(stampSetting.get().getLstStampPageLayout()) ) {		
			throw new BusinessException("Msg_1645","KDP002_1");
		}
		
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
		EmpStampDataRequiredImpl empStampDataR = new EmpStampDataRequiredImpl();
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
		StampTypeToSuppressRequiredImpl stampTypeToSuppressR = new StampTypeToSuppressRequiredImpl();
		
		return GetStampTypeToSuppressService.get(stampTypeToSuppressR, employeeId, StampMeans.INDIVITION);
	} 
	
	public TimeCard getTimeCard(String employeeId, GeneralDate date) {
		TimeCardRequiredImpl required = new TimeCardRequiredImpl();
		return GetTimeCardService.getTimeCard(required, employeeId, date.yearMonth());
	}

	@AllArgsConstructor
	private class TimeCardRequiredImpl implements GetTimeCardService.Require {

		@Override
		public List<TimeLeavingOfDailyPerformance> findbyPeriodOrderByYmd(String employeeId, DatePeriod datePeriod) {
			return timeLeavingOfDailyPerformanceRepository.findbyPeriodOrderByYmd(employeeId, datePeriod);
		}

	}

	@AllArgsConstructor
	private class EmpStampDataRequiredImpl implements GetListStampEmployeeService.Require {
//	private class EmpStampDataRequiredImpl implements GetEmpStampDataService.Require {

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
			implements GetStampTypeToSuppressService.Require, WorkingConditionService.RequireM1 {

		@Override
		public Optional<StampSettingPerson> getStampSet() {
			return stampSetPerRepo.getStampSet(AppContexts.user().companyId());
		}

		@Override
		public Optional<WorkingConditionItem> findWorkConditionByEmployee(String employeeId, GeneralDate baseDate) {
			return WorkingConditionService.findWorkConditionByEmployee(this, employeeId, baseDate);
		}

		@Override
		public Optional<PredetemineTimeSetting> findByWorkTimeCode(String workTimeCode) {
			return predetemineTimeSettingRepo.findByWorkTimeCode(AppContexts.user().companyId(), workTimeCode);
		}

		@Override
		public Optional<WorkingCondition> workingCondition(String companyId, String employeeId, GeneralDate baseDate) {
			return workingConditionRepo.getBySidAndStandardDate(companyId, employeeId, baseDate);
		}

		@Override
		public Optional<WorkingConditionItem> workingConditionItem(String historyId) {
			return workingConditionItemRepo.getByHistoryId(historyId);
		}

	}
	
	@AllArgsConstructor
	private class StampFunctionAvailableRequiredImpl implements StampFunctionAvailableService.Require {
		
		@Override
		public List<StampCard> getListStampCard(String sid) {
			return stampCardRepo.getListStampCard(sid);
		}
		
	}

}
