package nts.uk.ctx.at.function.app.find.alarm.checkcondition;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import nts.uk.ctx.at.function.app.find.alarm.checkcondition.agree36.AgreeCondOtDto;
import nts.uk.ctx.at.function.app.find.alarm.checkcondition.agree36.AgreeConditionErrorDto;
import nts.uk.ctx.at.function.app.find.alarm.checkcondition.agree36.AlarmChkCondAgree36Dto;
import nts.uk.ctx.at.function.app.find.alarm.checkcondition.annualholiday.AlarmCheckConAgrDto;
import nts.uk.ctx.at.function.app.find.alarm.checkcondition.annualholiday.AlarmCheckSubConAgrDto;
import nts.uk.ctx.at.function.app.find.alarm.checkcondition.annualholiday.AnnualHolidayAlarmConditionDto;
import nts.uk.ctx.at.function.app.find.alarm.mastercheck.MasterCheckFixedExtractConditionDto;
import nts.uk.ctx.at.function.app.find.alarm.mastercheck.MasterCheckFixedExtractItemDto;
import nts.uk.ctx.at.function.dom.adapter.ErrorAlarmWorkRecordAdapter;
import nts.uk.ctx.at.function.dom.adapter.FixedConWorkRecordAdapter;
import nts.uk.ctx.at.function.dom.adapter.FixedConWorkRecordAdapterDto;
import nts.uk.ctx.at.function.dom.adapter.FixedConditionDataAdapter;
import nts.uk.ctx.at.function.dom.adapter.FixedConditionDataAdapterDto;
import nts.uk.ctx.at.function.dom.adapter.PublicHolidaySettingAdapter;
import nts.uk.ctx.at.function.dom.adapter.WorkRecordExtraConAdapter;
import nts.uk.ctx.at.function.dom.adapter.WorkRecordExtraConAdapterDto;
import nts.uk.ctx.at.function.dom.adapter.eralworkrecorddto.ErrorAlarmConAdapterDto;
import nts.uk.ctx.at.function.dom.adapter.eralworkrecorddto.ScheMonCondDto;
import nts.uk.ctx.at.function.dom.adapter.eralworkrecorddto.WorkTimeConAdapterDto;
import nts.uk.ctx.at.function.dom.adapter.eralworkrecorddto.WorkTypeConAdapterDto;
import nts.uk.ctx.at.function.dom.adapter.monthlycheckcondition.ExtraResultMonthlyFunAdapter;
import nts.uk.ctx.at.function.dom.adapter.monthlycheckcondition.FixedExtraItemMonFunAdapter;
import nts.uk.ctx.at.function.dom.adapter.monthlycheckcondition.FixedExtraItemMonFunImport;
import nts.uk.ctx.at.function.dom.adapter.monthlycheckcondition.FixedExtraMonFunAdapter;
import nts.uk.ctx.at.function.dom.adapter.monthlycheckcondition.FixedExtraMonFunImport;
import nts.uk.ctx.at.function.dom.adapter.multimonth.MultiMonthFucAdapter;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.annual.ScheduleAnnualAlarmCheckCond;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.schedaily.ScheduleDailyAlarmCheckCond;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.schemonthly.ScheduleMonthlyAlarmCheckCond;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.weekly.WeeklyAlarmCheckCond;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.AlarmCheckConditionByCategory;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.AlarmCheckConditionByCategoryRepository;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.agree36.AgreeCondOt;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.agree36.AgreeConditionError;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.agree36.AgreeNameError;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.agree36.ErrorAlarm;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.agree36.IAgreeCondOtRepository;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.agree36.IAgreeConditionErrorRepository;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.agree36.IAgreeNameErrorRepository;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.agree36.Period;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.annualholiday.AlarmCheckConAgr;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.annualholiday.AlarmCheckSubConAgr;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.annualholiday.AnnualHolidayAlarmCondition;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.appapproval.AppApprovalAlarmCheckCondition;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.appapproval.AppApprovalFixedExtractConditionRepository;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.appapproval.AppApprovalFixedExtractItemRepository;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.daily.ConExtractedDaily;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.daily.DailyAlarmCondition;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.fourweekfourdayoff.AlarmCheckCondition4W4D;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.master.MasterCheckAlarmCheckCondition;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.monthly.MonAlarmCheckCon;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.monthly.dtoevent.ExtraResultMonthlyDomainEventDto;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.multimonth.MulMonAlarmCond;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.multimonth.doevent.MulMonCheckCondDomainEventDto;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.attendanceitem.CompareRange;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.attendanceitem.CompareSingleValue;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.attendanceitem.CountableTarget;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.mastercheck.MasterCheckFixedExtractConditionRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.mastercheck.MasterCheckFixedExtractItemRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.annual.ExtractionCondScheduleYear;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.annual.ExtractionCondScheduleYearRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.daily.CondContinuousTime;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.daily.CondContinuousTimeZone;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.daily.CondContinuousWrkType;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.daily.CondTime;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.daily.ExtraCondScheDayRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.daily.ExtractionCondScheduleDay;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.daily.FixedExtractSDailyConRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.daily.FixedExtractionSDailyCon;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.monthly.DayCheckCond;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.monthly.ExtractionCondScheduleMonth;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.monthly.ExtractionCondScheduleMonthRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.monthly.FixedExtractionSMonCon;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.monthly.FixedExtractionSMonConRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.monthly.PublicHolidayCheckCond;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.monthly.ScheduleMonRemainCheckCond;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.monthly.TimeCheckCond;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.weekly.ExtractionCondWeekly;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.weekly.ExtractionCondScheduleWeeklyRepository;
import nts.uk.ctx.at.shared.dom.alarmList.AlarmCategory;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author HungTT
 *
 */
@Stateless
public class AlarmCheckConditionByCategoryFinder {

	@Inject
	private AlarmCheckConditionByCategoryRepository conditionRepo;

	@Inject
	private FixedConWorkRecordAdapter fixedConditionAdapter;

	@Inject
	private FixedConditionDataAdapter fixCondDataAdapter;

	@Inject
	private WorkRecordExtraConAdapter workRecordExtractConditionAdapter;

	@Inject
	private ErrorAlarmWorkRecordAdapter errorAlarmWkRcAdapter;

	@Inject
	private PublicHolidaySettingAdapter publicHolidaySettingAdapter;

	@Inject
	private IAgreeConditionErrorRepository errorRep;

	@Inject
	private IAgreeNameErrorRepository nameRep;

	// monthly
	@Inject
	private FixedExtraMonFunAdapter fixExtraMon;

	@Inject
	private FixedExtraItemMonFunAdapter fixExtraItemMon;

	@Inject
	private ExtraResultMonthlyFunAdapter extraResultMonthly;

	@Inject
	private MultiMonthFucAdapter multiMonthCond;

	@Inject
	private IAgreeCondOtRepository condOtRep;

	@Inject
	private IAgreeNameErrorRepository iAgreeNameErrorRepository;

	@Inject
	private IAgreeConditionErrorRepository iAgreeConditionErrorRepository;

	@Inject
	private AppApprovalFixedExtractConditionRepository appApprovalFixedExtractConditionRepository;

	@Inject
	private AppApprovalFixedExtractItemRepository appApprovalFixedExtractItemRepository;
	
	@Inject
	private MasterCheckFixedExtractConditionRepository fixedMasterCheckConditionRepo;
	
	@Inject
	private MasterCheckFixedExtractItemRepository fixedMasterCheckItemRepo;
	
	@Inject
	private FixedExtractSDailyConRepository scheFixedCondDayRepo;
	
	@Inject
	private ExtraCondScheDayRepository extraCondScheDayRepository;
	
	@Inject
	private FixedExtractionSMonConRepository scheFixedCondMonRepo;
	
	@Inject
	private ExtractionCondScheduleMonthRepository extraCondScheMonRepository;
	
	@Inject
	private ExtractionCondScheduleYearRepository extraCondScheYearRepository;
	
	@Inject
	private ExtractionCondScheduleWeeklyRepository extraCondScheWeeklyRepository;
	
	public List<AlarmCheckConditionByCategoryDto> getAllData(int category) {
		String companyId = AppContexts.user().companyId();
		return conditionRepo.findByCategory(companyId, category).stream().map(item -> minValueFromDomain(item))
				.collect(Collectors.toList());
	}

	public boolean checkManager() {
		if (publicHolidaySettingAdapter.findPublicHolidaySetting().getIsManageComPublicHd() == 1)
			return true;
		return false;

	}

	public AlarmCheckConditionByCategoryDto getDataByCode(int category, String code) {
		String companyId = AppContexts.user().companyId();
		if (category == AlarmCategory.AGREEMENT.value) {
			List<AgreeNameError> itemDefault = iAgreeNameErrorRepository.findAll();
			List<AgreeConditionError> agreeCondition = iAgreeConditionErrorRepository.findAll(code, category);
			if (itemDefault.size() != agreeCondition.size() && agreeCondition.size() != 0) {
				// add default new Item 2: 上限規制(Upper)
				iAgreeConditionErrorRepository
						.insert(AgreeConditionError.createFromJavaType(UUID.randomUUID().toString(), companyId,
								category, code, 0, Period.Months_Average.value, ErrorAlarm.Upper.value, ""));
				iAgreeConditionErrorRepository
						.insert(AgreeConditionError.createFromJavaType(UUID.randomUUID().toString(), companyId,
								category, code, 0, Period.One_Month.value, ErrorAlarm.Upper.value, ""));

			}
		} 
		Optional<AlarmCheckConditionByCategory> opt = conditionRepo.find(companyId, category, code);
		if (opt.isPresent()) {
			return fromDomain(opt.get());
		} else {
			throw new RuntimeException("Object not exist!");
		}
	}

	public AlarmCheckConditionByCategoryDto getDataByCodeAndInsertDefault(int category, String code) {
		String companyId = AppContexts.user().companyId();
		Optional<AlarmCheckConditionByCategory> opt = conditionRepo.find(companyId, category, code);
		if (opt.isPresent()) {
			return fromDomain(opt.get());
		} else {
			throw new RuntimeException("Object not exist!");
		}
	}

	public List<DailyErrorAlarmCheckDto> getDailyErrorAlarmCheck() {
		return errorAlarmWkRcAdapter.getAllErrorAlarmWorkRecord(AppContexts.user().companyId()).stream()
				.map(item -> new DailyErrorAlarmCheckDto(item.getCode(), item.getName(), item.getTypeAtr(),
						item.getDisplayMessage()))
				.collect(Collectors.toList());
	}

	private AlarmCheckConditionByCategoryDto fromDomain(AlarmCheckConditionByCategory domain) {
		int schedule4WCondition = 0;
		DailyAlarmCondition dailyAlarmCondition = new DailyAlarmCondition("", ConExtractedDaily.ALL.value, false,
				Collections.emptyList(), Collections.emptyList());
		List<FixedConditionWorkRecordDto> listFixedConditionWkRecord = new ArrayList<>();
		List<WorkRecordExtraConAdapterDto> lstWorkRecordExtraCon = new ArrayList<>();
		// monthly
		MonAlarmCheckCon monAlarmCheckCon = new MonAlarmCheckCon("", Collections.emptyList());
		List<FixedExtraMonFunDto> listFixedExtraMonFun = new ArrayList<>();
		List<ExtraResultMonthlyDomainEventDto> arbExtraCon = new ArrayList<>();
		List<String> listEralCheckIDOld = new ArrayList<>();
		// multiple month
		List<MulMonCheckCondDomainEventDto> mulMonCheckCondDomainEventDtos = new ArrayList<>();
		List<String> listEralCheckMulMonIDOld = new ArrayList<>();
		AnnualHolidayAlarmConditionDto annualHolidayAlConDto = new AnnualHolidayAlarmConditionDto();
		//master check
		MasterCheckAlarmCheckCondition masterCheckCon = new MasterCheckAlarmCheckCondition();
		AppApprovalAlarmCheckCondition approvalCondition = new AppApprovalAlarmCheckCondition();
		List<MasterCheckFixedExtractConditionDto> listMasterCheckFixedCon = new ArrayList<>();
		List<AppApprovalFixedExtractConditionDto> listApprovalFixedCon = new ArrayList<>();
		
		// AgreeConditionErrorFinder
		List<AgreeConditionError> listConError = errorRep.findAll(domain.getCode().v(), domain.getCategory().value);
		List<AgreeNameError> listAgreeNameError = this.nameRep.findAll();
		List<AgreeConditionErrorDto> listCondError = new ArrayList<>();
		listCondError = listConError.stream().map(item -> {
			String agreementNameErr = listAgreeNameError.stream()
					.filter(x -> (x.getPeriod() == item.getPeriod() && x.getErrorAlarm() == item.getErrorAlarm()))
					.findFirst().get().getName().v();
			return new AgreeConditionErrorDto(item.getCategory().value, item.getId(), item.getCode().v(),
					item.getUseAtr().value, item.getPeriod().value, item.getErrorAlarm().value,
					item.getMessageDisp().v(), agreementNameErr);
		}).collect(Collectors.toList());
		// AgreeCondOtFinder
		List<AgreeCondOtDto> listCondOt = new ArrayList<>();
		List<AgreeCondOt> result = condOtRep.findAll(domain.getCode().v(), domain.getCategory().value);
		listCondOt = result.stream().map(x -> {
			return new AgreeCondOtDto(x.getCategory().value, x.getId(), x.getNo(), x.getCode().v(), x.getOt36().v(),
					x.getExcessNum().v(), x.getMessageDisp().v());
		}).collect(Collectors.toList());

		if (domain.getCategory() == AlarmCategory.SCHEDULE_4WEEK && domain.getExtractionCondition() != null) {
			AlarmCheckCondition4W4D schedule4WeekCondition = (AlarmCheckCondition4W4D) domain.getExtractionCondition();
			schedule4WCondition = schedule4WeekCondition.getFourW4DCheckCond().value;
		}
		if (domain.getCategory() == AlarmCategory.DAILY && domain.getExtractionCondition() != null) {
			dailyAlarmCondition = (DailyAlarmCondition) domain.getExtractionCondition();
			String dailyID = dailyAlarmCondition.getDailyAlarmConID();
			List<FixedConWorkRecordAdapterDto> listFixedConditionWorkRecord = fixedConditionAdapter
					.getAllFixedConWorkRecordByID(dailyID);
			List<FixedConditionDataAdapterDto> listFixedConditionData = fixCondDataAdapter
					.getAllFixedConditionDataPub();
			for (FixedConditionDataAdapterDto i : listFixedConditionData) {
				boolean check = true;
				if (listFixedConditionWorkRecord != null && !listFixedConditionWorkRecord.isEmpty()) {
					for (FixedConWorkRecordAdapterDto e : listFixedConditionWorkRecord) {
						if (e.getFixConWorkRecordNo() == i.getFixConWorkRecordNo()) {
							FixedConditionWorkRecordDto dto = new FixedConditionWorkRecordDto(e.getDailyAlarmConID(),
									i.getFixConWorkRecordName(), i.getFixConWorkRecordNo(), e.getMessage(),
									e.isUseAtr(), i.getEralarmAtr());
							listFixedConditionWkRecord.add(dto);
							check = false;
							break;
						}
					}
				}
				if (check) {
					FixedConditionWorkRecordDto dto = new FixedConditionWorkRecordDto("", i.getFixConWorkRecordName(),
							i.getFixConWorkRecordNo(), i.getMessage(), false, i.getEralarmAtr());
					listFixedConditionWkRecord.add(dto);
				}
			}
			lstWorkRecordExtraCon = workRecordExtractConditionAdapter
					.getAllWorkRecordExtraConByListID(dailyAlarmCondition.getExtractConditionWorkRecord());
		}



		// monthly
		if (domain.getCategory() == AlarmCategory.MONTHLY && domain.getExtractionCondition() != null) {
			monAlarmCheckCon = (MonAlarmCheckCon) domain.getExtractionCondition();

			// get arbExtraCon
			arbExtraCon = extraResultMonthly.getListExtraResultMonByListEralID(monAlarmCheckCon.getArbExtraCon());
			listEralCheckIDOld = monAlarmCheckCon.getArbExtraCon();
			// get fixExtra monthly
			List<FixedExtraItemMonFunImport> dataFixedExtraMon = fixExtraItemMon.getAllFixedExtraItemMon();
			List<FixedExtraMonFunImport> lstFixedExtraMonFunImport = fixExtraMon.getByEralCheckID(monAlarmCheckCon.getMonAlarmCheckConID());
			listFixedExtraMonFun = dataFixedExtraMon.stream().map(importData -> new FixedExtraMonFunDto(
					"",
					importData.getFixedExtraItemMonName(),
					importData.getFixedExtraItemMonNo(),
					 lstFixedExtraMonFunImport.stream().filter(x -> x.getFixedExtraItemMonNo() == importData.getFixedExtraItemMonNo()).findFirst().isPresent() ? 
							 lstFixedExtraMonFunImport.stream().filter(x -> x.getFixedExtraItemMonNo() == importData.getFixedExtraItemMonNo()).findFirst().get().isUseAtr() : false,
					lstFixedExtraMonFunImport.stream().filter(x -> x.getFixedExtraItemMonNo() == importData.getFixedExtraItemMonNo()).findFirst().map(FixedExtraMonFunImport::getMessage).orElse(importData.getMessage())
					)).collect(Collectors.toList());
			/*
			 * listFixedExtraMonFun =
			 * fixExtraMon.getByEralCheckID(monAlarmCheckCon.getMonAlarmCheckConID()).stream
			 * () .map(c ->
			 * FixedExtraMonFunDto.convertToImport(c)).collect(Collectors.toList()); for
			 * (FixedExtraMonFunDto fixedExtraMonFunDto : listFixedExtraMonFun) { for
			 * (FixedExtraItemMonFunImport fixedExtraItemMonFunImport : dataFixedExtraMon) {
			 * if (fixedExtraMonFunDto.getFixedExtraItemMonNo() ==
			 * fixedExtraItemMonFunImport .getFixedExtraItemMonNo()) {
			 * fixedExtraMonFunDto.setMonAlarmCheckName(fixedExtraItemMonFunImport.
			 * getFixedExtraItemMonName()); break; } } } // end for
			 */		
		}

		if (domain.getCategory() == AlarmCategory.MULTIPLE_MONTH && domain.getExtractionCondition() != null) {
			MulMonAlarmCond mulMonAlarmCond = (MulMonAlarmCond) domain.getExtractionCondition();

			// get arbExtraCon
			mulMonCheckCondDomainEventDtos = multiMonthCond
					.getListMultiMonCondByListEralID(mulMonAlarmCond.getErrorAlarmCondIds());
			listEralCheckIDOld = mulMonAlarmCond.getErrorAlarmCondIds();
		}

		if (domain.getCategory() == AlarmCategory.ATTENDANCE_RATE_FOR_HOLIDAY
				&& domain.getExtractionCondition() != null) {
			AnnualHolidayAlarmCondition annualHolidayAlCon = (AnnualHolidayAlarmCondition) domain
					.getExtractionCondition();
			AlarmCheckConAgr alarmCheckConAgr = annualHolidayAlCon.getAlarmCheckConAgr();
			AlarmCheckSubConAgr alSubConAgr = annualHolidayAlCon.getAlarmCheckSubConAgr();
			annualHolidayAlConDto.setAlCheckConAgrDto(alarmCheckConAgr == null ? null
					: new AlarmCheckConAgrDto(alarmCheckConAgr.isDistByPeriod(),
							alarmCheckConAgr.getDisplayMessage().isPresent()
									? alarmCheckConAgr.getDisplayMessage().get().v() : null,
							alarmCheckConAgr.getUsageObliDay().v()));
			annualHolidayAlConDto.setAlCheckSubConAgrDto(alSubConAgr == null ? null
					: new AlarmCheckSubConAgrDto(alSubConAgr.isNarrowUntilNext(), alSubConAgr.isNarrowLastDay(),
							alSubConAgr.getNumberDayAward().isPresent() ? alSubConAgr.getNumberDayAward().get().v()
									: null,
							alSubConAgr.getPeriodUntilNext().isPresent() ? alSubConAgr.getPeriodUntilNext().get().v()
									: null));
		}
		
		if (domain.getCategory() == AlarmCategory.MASTER_CHECK && domain.getExtractionCondition() != null) {
			masterCheckCon = (MasterCheckAlarmCheckCondition) domain.getExtractionCondition();
			String alarmCheckId = masterCheckCon.getAlarmCheckId();
			List<MasterCheckFixedExtractItemDto> listFixedMasterCheckItem = 
					fixedMasterCheckItemRepo.getAllFixedMasterCheckItem().stream()
					.map(c->MasterCheckFixedExtractItemDto.fromDomain(c)).collect(Collectors.toList());
			List<MasterCheckFixedExtractConditionDto> listFixedMasterCheckCon = 
					fixedMasterCheckConditionRepo.getAllFixedMasterCheckConById(alarmCheckId).stream()
					.map(c->MasterCheckFixedExtractConditionDto.fromDomain(c)).collect(Collectors.toList());
			for(MasterCheckFixedExtractItemDto i : listFixedMasterCheckItem) {
				boolean check = true;
				if(listFixedMasterCheckCon != null && !listFixedMasterCheckCon.isEmpty()) {
					for (MasterCheckFixedExtractConditionDto e : listFixedMasterCheckCon) {
						if(e.getNo() == i.getNo()) {
							MasterCheckFixedExtractConditionDto dto = new MasterCheckFixedExtractConditionDto(e.getErrorAlarmCheckId(), i.getName(), i.getNo(), e.getMessage(), e.isUseAtr(), i.getErAlAtr());
							listMasterCheckFixedCon.add(dto);
							check = false;
							break;
						}
					}
				}
				if(check) {
					MasterCheckFixedExtractConditionDto dto = new MasterCheckFixedExtractConditionDto("", i.getName(), i.getNo(), i.getMessage(), false, i.getErAlAtr());
					listMasterCheckFixedCon.add(dto);
				}
			}
		}
		
		if (domain.getCategory() == AlarmCategory.APPLICATION_APPROVAL && domain.getExtractionCondition() != null) {
			approvalCondition = (AppApprovalAlarmCheckCondition) domain.getExtractionCondition();
			String alarmCheckId = approvalCondition.getApprovalAlarmConID();
			List<AppApprovalFixedExtractItemDto> listApprovalItem = appApprovalFixedExtractItemRepository.findAll().stream()
			.map(e -> new AppApprovalFixedExtractItemDto(e)).collect(Collectors.toList());
			List<AppApprovalFixedExtractConditionDto> listAppCheckConDto = appApprovalFixedExtractConditionRepository
					.findById(alarmCheckId).stream().map(e -> AppApprovalFixedExtractConditionDto.fromDomain(e)).collect(Collectors.toList());
	
			for (AppApprovalFixedExtractItemDto a : listApprovalItem) {
				boolean check = true;
				if (listAppCheckConDto != null && !listAppCheckConDto.isEmpty()) {
					for (AppApprovalFixedExtractConditionDto e : listAppCheckConDto) {
						if (e.getNo() == a.getNo()) {
							AppApprovalFixedExtractConditionDto dto = new AppApprovalFixedExtractConditionDto(e.getAppAlarmConId(), a.getName(), a.getNo(), e.getDisplayMessage(), e.isUseAtr(), a.getErAlAtr());
							listApprovalFixedCon.add(dto);
							check = false;
							break;
						}
					}
				}
				if (check) {
					AppApprovalFixedExtractConditionDto dto = new AppApprovalFixedExtractConditionDto("", a.getName(), a.getNo(), a.getDisplayMessage(), false, a.getErAlAtr());
					listApprovalFixedCon.add(dto);
				}
			}
		}
		
		// schedule daily
		ScheFixCondDayDto scheFixConditionDay = null;
		ScheAnyCondDayDto scheAnyConditionDay = null;
		if (domain.getCategory() == AlarmCategory.SCHEDULE_DAILY && domain.getExtractionCondition() != null) {
			ScheduleDailyAlarmCheckCond condition = (ScheduleDailyAlarmCheckCond) domain.getExtractionCondition();
			String listFixedItem = condition.getListFixedItem();
			String contractCode = AppContexts.user().contractCode();
			String companyId = AppContexts.user().companyId();
			List<FixedConditionWorkRecordDto> scheFixCondDays = new ArrayList<>();
			// get list KscdtScheFixCondDay by listFixedItemId
			if (StringUtils.isNotEmpty(listFixedItem)) {
				scheFixCondDays = scheFixedCondDayRepo.getScheFixCondDay(contractCode, companyId, listFixedItem)
						.stream().map(item -> schedFixCondDayToDto(item)).collect(Collectors.toList());
			}
			
			List<WorkRecordExtraConAdapterDto> scheAnyCondDays = new ArrayList<>();
			String listOptionalItem = condition.getListOptionalItem();
			if (listOptionalItem != null && StringUtils.isNotEmpty(listOptionalItem)) {
				scheAnyCondDays = extraCondScheDayRepository.getScheAnyCondDay(contractCode, companyId, listOptionalItem)
						.stream().map(item -> schedAnyCondDayToDto(item)).collect(Collectors.toList());
			}
			 
			scheFixConditionDay = new ScheFixCondDayDto(listFixedItem, scheFixCondDays);
			scheAnyConditionDay = new ScheAnyCondDayDto(listOptionalItem, scheAnyCondDays);
		}
		
		// schedule monthly
		if (domain.getCategory() == AlarmCategory.SCHEDULE_MONTHLY && domain.getExtractionCondition() != null) {
			ScheduleMonthlyAlarmCheckCond condition = (ScheduleMonthlyAlarmCheckCond) domain.getExtractionCondition();
			String listFixedItem = condition.getListFixedItem();
			String contractCode = AppContexts.user().contractCode();
			String companyId = AppContexts.user().companyId();
			List<FixedConditionWorkRecordDto> scheFixCondDays = new ArrayList<>();
			// get list KscdtScheFixCondDay by listFixedItemId
			if (StringUtils.isNotEmpty(listFixedItem)) {
				scheFixCondDays = scheFixedCondMonRepo.getScheFixCond(contractCode, companyId, listFixedItem)
						.stream().map(item -> schedFixCondMonToDto(item)).collect(Collectors.toList());
			}
			
			List<WorkRecordExtraConAdapterDto> scheAnyCondDays = new ArrayList<>();
			String listOptionalItem = condition.getListOptionalItem();
			if (listOptionalItem != null && StringUtils.isNotEmpty(listOptionalItem)) {
				scheAnyCondDays = extraCondScheMonRepository.getScheAnyCond(contractCode, companyId, listOptionalItem)
						.stream().map(item -> schedAnyCondMonToDto(item)).collect(Collectors.toList());
			}
			 
			scheFixConditionDay = new ScheFixCondDayDto(listFixedItem, scheFixCondDays);
			scheAnyConditionDay = new ScheAnyCondDayDto(listOptionalItem, scheAnyCondDays);
		}
		
		// schedule year
		if (domain.getCategory() == AlarmCategory.SCHEDULE_YEAR && domain.getExtractionCondition() != null) {
			ScheduleAnnualAlarmCheckCond condition = (ScheduleAnnualAlarmCheckCond) domain.getExtractionCondition();
			String contractCode = AppContexts.user().contractCode();
			String companyId = AppContexts.user().companyId();
			
			List<WorkRecordExtraConAdapterDto> scheAnyCondDays = new ArrayList<>();
			String listOptionalItem = condition.getListOptionalItem();
			if (listOptionalItem != null && StringUtils.isNotEmpty(listOptionalItem)) {
				scheAnyCondDays = extraCondScheYearRepository.getScheAnyCond(contractCode, companyId, listOptionalItem)
						.stream().map(item -> schedAnyCondYearToDto(item)).collect(Collectors.toList());
			}
			scheAnyConditionDay = new ScheAnyCondDayDto(listOptionalItem, scheAnyCondDays);
		}
		
		// weekly
		if (domain.getCategory() == AlarmCategory.WEEKLY && domain.getExtractionCondition() != null) {
			WeeklyAlarmCheckCond condition = (WeeklyAlarmCheckCond) domain.getExtractionCondition();
			String contractCode = AppContexts.user().contractCode();
			String companyId = AppContexts.user().companyId();
			
			List<WorkRecordExtraConAdapterDto> scheAnyCondDays = new ArrayList<>();
			String listOptionalItem = condition.getListOptionalItem();
			if (listOptionalItem != null && StringUtils.isNotEmpty(listOptionalItem)) {
				scheAnyCondDays = extraCondScheWeeklyRepository.getAnyCond(contractCode, companyId, listOptionalItem)
						.stream().map(item -> schedAnyCondWeeklyToDto(item)).collect(Collectors.toList());
			}
			scheAnyConditionDay = new ScheAnyCondDayDto(listOptionalItem, scheAnyCondDays);
		}

		return new AlarmCheckConditionByCategoryDto(domain.getCode().v(), domain.getName().v(),
				domain.getCategory().value,
				new AlarmCheckTargetConditionDto(domain.getExtractTargetCondition().isFilterByEmployment(),
						domain.getExtractTargetCondition().isFilterByClassification(),
						domain.getExtractTargetCondition().isFilterByJobTitle(),
						domain.getExtractTargetCondition().isFilterByBusinessType(),
						domain.getExtractTargetCondition().getLstEmploymentCode(),
						domain.getExtractTargetCondition().getLstClassificationCode(),
						domain.getExtractTargetCondition().getLstJobTitleId(),
						domain.getExtractTargetCondition().getLstBusinessTypeCode()),
				domain.getListRoleId(), schedule4WCondition,
				new DailyAlarmCheckConditionDto(dailyAlarmCondition.isAddApplication(),
						dailyAlarmCondition.getConExtractedDaily().value, dailyAlarmCondition.getErrorAlarmCode(),
						lstWorkRecordExtraCon.stream().sorted((x, y) -> x.getSortOrderBy() - y.getSortOrderBy())
								.collect(Collectors.toList()),
						listFixedConditionWkRecord),
				new ApprovalAlarmCheckConDto(listApprovalFixedCon),
				new MonAlarmCheckConDto(listFixedExtraMonFun, arbExtraCon, listEralCheckIDOld),
				new AlarmChkCondAgree36Dto(listCondError, listCondOt),
				new MulMonAlarmCheckConDto(mulMonCheckCondDomainEventDtos, listEralCheckMulMonIDOld),
				annualHolidayAlConDto,
				new MasterCheckAlarmCheckConditionDto(listMasterCheckFixedCon),
				scheFixConditionDay,
				scheAnyConditionDay);
	}

	private AlarmCheckConditionByCategoryDto minValueFromDomain(AlarmCheckConditionByCategory domain) {
		return new AlarmCheckConditionByCategoryDto(domain.getCode().v(), domain.getName().v(),
				domain.getCategory().value, null, null, 0, null, null, null, null, null, null, null, null, null);
	}
	
	/**
	 * Convert FixedExtractionSDailyCon domain to DTO
	 * @param domain
	 * @return
	 */
	private FixedConditionWorkRecordDto schedFixCondDayToDto(FixedExtractionSDailyCon domain) {
		FixedConditionWorkRecordDto dto = new FixedConditionWorkRecordDto();
		dto.setDailyAlarmConID(domain.getErrorAlarmWorkplaceId());
		dto.setFixConWorkRecordNo(domain.getFixedCheckDayItems().value);
		dto.setMessage(domain.getMessageDisp().get().v());
		dto.setUseAtr(domain.isUseAtr());
		return dto;
	}
	
	/**
	 * Convert ExtractionCondScheduleDay domain to DTO
	 * @param domain
	 * @return
	 */
	private WorkRecordExtraConAdapterDto schedAnyCondDayToDto(ExtractionCondScheduleDay domain) {
		ErrorAlarmConAdapterDto errorAlarmCondition = ErrorAlarmConAdapterDto.builder()
				.displayMessage(domain.getErrorAlarmMessage() != null && domain.getErrorAlarmMessage().isPresent() ? domain.getErrorAlarmMessage().get().v() : "")
				.build();
		WorkTypeConAdapterDto workTypeCondition = new WorkTypeConAdapterDto(false, 0, false, new ArrayList<>(), false, new ArrayList<>());
		WorkTimeConAdapterDto workTimeCondition = new WorkTimeConAdapterDto(false, 0, false, new ArrayList<>(), false, new ArrayList<>());
		
		if (domain.getScheduleCheckCond() != null) {
			switch (domain.getCheckItemType()) {
			 	case TIME:			 		
			 		CondTime time = (CondTime)domain.getScheduleCheckCond();
			 		if (time.getCheckedCondition() == null) {
			 			break;
			 		}
			 		
			 		if (time.getCheckedCondition() instanceof CompareRange) {
			 			CompareRange compareRange = (CompareRange)time.getCheckedCondition();
			 			workTypeCondition.setComparePlanAndActual(domain.getTargetWrkType().value);
			 			workTypeCondition.setPlanLstWorkType(time.getWrkTypeCds());
			 			workTypeCondition.setCheckTimeType(time.getCheckTimeType().value);
			 			workTypeCondition.setComparisonOperator(compareRange.getCompareOperator().value);
			 			workTypeCondition.setCompareStartValue((Double)compareRange.getStartValue());
			 			workTypeCondition.setCompareEndValue((Double)compareRange.getEndValue());
			 		} else {
			 			CompareSingleValue compareRange = (CompareSingleValue)time.getCheckedCondition();
			 			workTypeCondition.setComparePlanAndActual(domain.getTargetWrkType().value);
			 			workTypeCondition.setPlanLstWorkType(time.getWrkTypeCds());
			 			workTypeCondition.setCheckTimeType(time.getCheckTimeType().value);
			 			workTypeCondition.setComparisonOperator(compareRange.getCompareOpertor().value);
			 			workTypeCondition.setCompareStartValue((Double)compareRange.getValue());
			 		}
			 		break;
			 	case CONTINUOUS_TIME:
			 		CondContinuousTime continuousTime = (CondContinuousTime)domain.getScheduleCheckCond();
			 		if (continuousTime.getCheckedCondition() == null) {
			 			break;
			 		}
			 		
			 		errorAlarmCondition.setContinuousPeriod(continuousTime.getPeriod().v());
			 		if (continuousTime.getCheckedCondition() instanceof CompareRange) {
			 			workTypeCondition.setPlanLstWorkType(continuousTime.getWrkTypeCds());
			 			CompareRange compareRange = (CompareRange)continuousTime.getCheckedCondition();
			 			workTypeCondition.setComparePlanAndActual(domain.getTargetWrkType().value);
			 			workTypeCondition.setCheckTimeType(continuousTime.getCheckTimeType().value);
			 			workTypeCondition.setComparisonOperator(compareRange.getCompareOperator().value);
			 			workTypeCondition.setCompareStartValue((Double)compareRange.getStartValue());
			 			workTypeCondition.setCompareEndValue((Double)compareRange.getEndValue());
			 		} else {
			 			workTypeCondition.setPlanLstWorkType(continuousTime.getWrkTypeCds());
			 			CompareSingleValue compareRange = (CompareSingleValue)continuousTime.getCheckedCondition();
			 			workTypeCondition.setComparePlanAndActual(domain.getTargetWrkType().value);
			 			workTypeCondition.setCheckTimeType(continuousTime.getCheckTimeType().value);
			 			workTypeCondition.setComparisonOperator(compareRange.getCompareOpertor().value);
			 			workTypeCondition.setCompareStartValue((Double)compareRange.getValue());
			 		}
			 		errorAlarmCondition.setContinuousPeriod(continuousTime.getPeriod().v());
			 		break;
			 	case CONTINUOUS_TIMEZONE:
			 		CondContinuousTimeZone continuousTimeZone = (CondContinuousTimeZone)domain.getScheduleCheckCond();
			 		workTypeCondition.setComparePlanAndActual(domain.getTargetWrkType().value);
		 			workTypeCondition.setPlanLstWorkType(continuousTimeZone.getWrkTypeCds());
		 			
		 			workTimeCondition.setPlanLstWorkTime(continuousTimeZone.getWrkTimeCds());
		 			workTimeCondition.setComparePlanAndActual(domain.getTimeZoneTargetRange().value);
			 		
			 		errorAlarmCondition.setContinuousPeriod(continuousTimeZone.getPeriod().v());
			 		break;
			 	case CONTINUOUS_WORK:
			 		CondContinuousWrkType continuousWorkType = (CondContinuousWrkType)domain.getScheduleCheckCond();
			 		workTypeCondition.setComparePlanAndActual(domain.getTargetWrkType().value);
		 			workTypeCondition.setPlanLstWorkType(continuousWorkType.getWrkTypeCds());
		 			errorAlarmCondition.setContinuousPeriod(continuousWorkType.getPeriod().v());
			 		break;
			 	default:
			 		break;
			 }
			 
			 errorAlarmCondition.setWorkTypeCondition(workTypeCondition);
			 errorAlarmCondition.setWorkTimeCondition(workTimeCondition);
		}
		
		 return WorkRecordExtraConAdapterDto.builder()
				.errorAlarmCheckID(domain.getErrorAlarmId())
				.sortOrderBy(domain.getSortOrder())
				.useAtr(domain.isUse())
				.nameWKRecord(domain.getName().v())
				.errorAlarmCondition(errorAlarmCondition)
				.checkItem(domain.getCheckItemType().value)
				.build();
	}
	
	/**
	 * Convert FixedExtractionSDailyCon domain to DTO
	 * @param domain
	 * @return
	 */
	private FixedConditionWorkRecordDto schedFixCondMonToDto(FixedExtractionSMonCon domain) {
		FixedConditionWorkRecordDto dto = new FixedConditionWorkRecordDto();
		dto.setDailyAlarmConID(domain.getErrorAlarmWorkplaceId());
		dto.setFixConWorkRecordNo(domain.getFixedCheckSMonItems().value);
		dto.setMessage(domain.getMessageDisp().get().v());
		dto.setUseAtr(domain.isUseAtr());
		return dto;
	}
	
	/**
	 * Convert ExtractionCondScheduleDay domain to DTO
	 * @param domain
	 * @return
	 */
	private WorkRecordExtraConAdapterDto schedAnyCondMonToDto(ExtractionCondScheduleMonth domain) {
		ScheMonCondDto monthlyCondition = new ScheMonCondDto();
		
		if (domain.getScheCheckConditions() != null) {
			switch (domain.getCheckItemType()) {
			 	case CONTRAST:
			 		PublicHolidayCheckCond holidayCheckCond = (PublicHolidayCheckCond)domain.getScheCheckConditions();
			 		monthlyCondition.setScheCheckCondition(holidayCheckCond.getTypeOfContrast().value);
			 		break;
			 	case TIME:
			 		TimeCheckCond timeCheckCond = (TimeCheckCond)domain.getScheCheckConditions();
			 		monthlyCondition.setScheCheckCondition(timeCheckCond.getTypeOfTime().value);
			 		break;
			 	case NUMBER_DAYS:
			 		DayCheckCond dayCheckCond = (DayCheckCond)domain.getScheCheckConditions();
			 		monthlyCondition.setScheCheckCondition(dayCheckCond.getTypeOfDays().value);
			 		break;
			 	case REMAIN_NUMBER:
			 		ScheduleMonRemainCheckCond scheduleMonRemainCheckCond = (ScheduleMonRemainCheckCond)domain.getScheCheckConditions();
			 		monthlyCondition.setScheCheckCondition(scheduleMonRemainCheckCond.getTypeOfVacations().value);
			 		if (scheduleMonRemainCheckCond.getSpecialHolidayCode().isPresent()) {
			 			monthlyCondition.setSpecialHolidayCode(scheduleMonRemainCheckCond.getSpecialHolidayCode().get().v());
			 		}
			 		break;
			 	default:
			 		break;
			}
		}
		
		if (domain.getCheckConditions() != null) {
			if (domain.getCheckConditions() instanceof CompareRange) {
				CompareRange checkedCondition = (CompareRange)domain.getCheckConditions();
				monthlyCondition.setComparisonOperator(checkedCondition.getCompareOperator().value);
				monthlyCondition.setCompareStartValue((Double)checkedCondition.getStartValue());
				monthlyCondition.setCompareEndValue((Double)checkedCondition.getEndValue());
			} else {
				CompareSingleValue checkedCondition = (CompareSingleValue)domain.getCheckConditions();
				monthlyCondition.setComparisonOperator(checkedCondition.getCompareOpertor().value);
				monthlyCondition.setCompareStartValue((Double)checkedCondition.getValue());
			}
		}
		
		ErrorAlarmConAdapterDto errorAlarmCondition = ErrorAlarmConAdapterDto.builder()
				.displayMessage(domain.getErrorAlarmMessage() != null && domain.getErrorAlarmMessage().isPresent() ? domain.getErrorAlarmMessage().get().v() : "")
				.monthlyCondition(monthlyCondition)
				.build();
		
		return WorkRecordExtraConAdapterDto.builder()
				.errorAlarmCheckID(domain.getErrorAlarmId())
				.sortOrderBy(domain.getSortOrder())
				.useAtr(domain.isUse())
				.nameWKRecord(domain.getName().v())
				.errorAlarmCondition(errorAlarmCondition)
				.checkItem(domain.getCheckItemType().value)
				.build();
	}
	
	
	/**
	 * Convert ExtractionCondScheduleYear domain to DTO
	 * @param domain
	 * @return
	 */
	private WorkRecordExtraConAdapterDto schedAnyCondYearToDto(ExtractionCondScheduleYear domain) {
		ScheMonCondDto monthlyCondition = new ScheMonCondDto();
		
		switch (domain.getCheckItemType()) {
		 	case TIME:
		 		nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.annual.TimeCheckCond holidayCheckCond = (nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.annual.TimeCheckCond)domain.getScheCheckConditions();
		 		monthlyCondition.setScheCheckCondition(holidayCheckCond.getTypeOfTime().value);
		 		break;
		 	case DAY_NUMBER:
		 		nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.annual.DayCheckCond timeCheckCond = (nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.annual.DayCheckCond)domain.getScheCheckConditions();
		 		monthlyCondition.setScheCheckCondition(timeCheckCond.getTypeOfDays().value);
		 		break;
		 	default:
		 		break;
		}
		
		if (domain.getCheckConditions() != null) {
			if (domain.getCheckConditions() instanceof CompareRange) {
				CompareRange checkedCondition = (CompareRange)domain.getCheckConditions();
				monthlyCondition.setComparisonOperator(checkedCondition.getCompareOperator().value);
				monthlyCondition.setCompareStartValue((Double)checkedCondition.getStartValue());
				monthlyCondition.setCompareEndValue((Double)checkedCondition.getEndValue());
			} else {
				CompareSingleValue checkedCondition = (CompareSingleValue)domain.getCheckConditions();
				monthlyCondition.setComparisonOperator(checkedCondition.getCompareOpertor().value);
				monthlyCondition.setCompareStartValue((Double)checkedCondition.getValue());
			}
		}
		
		ErrorAlarmConAdapterDto errorAlarmCondition = ErrorAlarmConAdapterDto.builder()
				.displayMessage(domain.getErrorAlarmMessage() != null && domain.getErrorAlarmMessage().isPresent() ? domain.getErrorAlarmMessage().get().v() : "")
				.monthlyCondition(monthlyCondition)
				.build();
		
		return WorkRecordExtraConAdapterDto.builder()
				.errorAlarmCheckID(domain.getErrorAlarmId())
				.sortOrderBy(domain.getSortOrder())
				.useAtr(domain.isUse())
				.nameWKRecord(domain.getName().v())
				.errorAlarmCondition(errorAlarmCondition)
				.checkItem(domain.getCheckItemType().value)
				.build();
	}
	
	/**
	 * Convert ExtractionCondScheduleWeekly domain to DTO
	 * @param domain
	 * @return
	 */
	private WorkRecordExtraConAdapterDto schedAnyCondWeeklyToDto(ExtractionCondWeekly domain) {
		ScheMonCondDto monthlyCondition = new ScheMonCondDto();
		
		//TODO:atdItemCondition移送
		
		ErrorAlarmConAdapterDto errorAlarmCondition = ErrorAlarmConAdapterDto.builder()
				.displayMessage(domain.getErrorAlarmMessage() != null && domain.getErrorAlarmMessage().isPresent() ? domain.getErrorAlarmMessage().get().v() : "")
				.continuousPeriod(domain.getContinuousPeriod() != null && domain.getContinuousPeriod().isPresent() ? domain.getContinuousPeriod().get().v() : 0)
				.monthlyCondition(monthlyCondition)
				.build();
		
		return WorkRecordExtraConAdapterDto.builder()
				.errorAlarmCheckID(domain.getErrorAlarmId())
				.sortOrderBy(domain.getSortOrder())
				.useAtr(domain.isUse())
				.nameWKRecord(domain.getName().v())
				.errorAlarmCondition(errorAlarmCondition)
				.checkItem(domain.getCheckItemType().value)
				.build();
	}
}
