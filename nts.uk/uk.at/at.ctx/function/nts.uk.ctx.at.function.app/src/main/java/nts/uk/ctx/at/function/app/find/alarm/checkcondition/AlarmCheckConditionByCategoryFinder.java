package nts.uk.ctx.at.function.app.find.alarm.checkcondition;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

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
import nts.uk.ctx.at.function.dom.adapter.monthlycheckcondition.ExtraResultMonthlyFunAdapter;
import nts.uk.ctx.at.function.dom.adapter.monthlycheckcondition.FixedExtraItemMonFunAdapter;
import nts.uk.ctx.at.function.dom.adapter.monthlycheckcondition.FixedExtraItemMonFunImport;
import nts.uk.ctx.at.function.dom.adapter.monthlycheckcondition.FixedExtraMonFunAdapter;
import nts.uk.ctx.at.function.dom.adapter.multimonth.MultiMonthFucAdapter;
import nts.uk.ctx.at.function.dom.alarm.AlarmCategory;
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
import nts.uk.ctx.at.function.dom.alarm.checkcondition.appapproval.AppApprovalFixedExtractCondition;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.appapproval.AppApprovalFixedExtractConditionRepository;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.appapproval.AppApprovalFixedExtractItemRepository;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.daily.ConExtractedDaily;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.daily.DailyAlarmCondition;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.fourweekfourdayoff.AlarmCheckCondition4W4D;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.mastercheck.MasterCheckAlarmCheckCondition;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.mastercheck.MasterCheckFixedExtractConditionRepository;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.mastercheck.MasterCheckFixedExtractItemRepository;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.monthly.MonAlarmCheckCon;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.monthly.dtoevent.ExtraResultMonthlyDomainEventDto;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.multimonth.MulMonAlarmCond;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.multimonth.doevent.MulMonCheckCondDomainEventDto;
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
		List<AppFixedConditionWorkRecordDto> listAppAlCheckConDto = new ArrayList();
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
		List<MasterCheckFixedExtractConditionDto> listMasterCheckFixedCon = new ArrayList<>();
		
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

		if (domain.getCategory() == AlarmCategory.APPLICATION_APPROVAL && domain.getExtractionCondition() != null) {
			Optional<AppApprovalAlarmCheckCondition> approvalAlCheckCon = appApprovalFixedExtractConditionRepository
					.findByCodeAndCategory(domain.getCompanyId(), domain.getCode().v(), domain.getCategory().value);

			List<AppApprovalFixedExtractConditionDto> listAppConDto = appApprovalFixedExtractConditionRepository
					.findAll().stream().map(e -> new AppApprovalFixedExtractConditionDto(e))
					.collect(Collectors.toList());

			List<AppApprovalFixedExtractItemDto> listAppItem = appApprovalFixedExtractItemRepository.findAll().stream()
					.map(e -> new AppApprovalFixedExtractItemDto(e)).collect(Collectors.toList());

			if (approvalAlCheckCon.isPresent()) {
				List<Integer> listNo = listAppConDto.stream()
						.filter(e -> e.getAppAlarmCheckId().equals(approvalAlCheckCon.get().getApprovalAlarmConID()))
						.map(a -> a.getNo()).collect(Collectors.toList());

				listAppItem.stream().forEach(e -> {
					int check = listNo.indexOf(e.getNo());
					if (check > 0) {
						AppFixedConditionWorkRecordDto dto = new AppFixedConditionWorkRecordDto(listAppConDto.get(check).getAppAlarmCheckId(), e.getNo(), e.getName(),
								listAppConDto.get(check).getMessage(), listAppConDto.get(check).isUseAtr(),
								e.getErAlAtr());
						listAppAlCheckConDto.add(dto);
					}
//					ApprovalAlarmCheckConDto dto = new ApprovalAlarmCheckConDto(e.getNo(), e.getName(),
//							listAppConDto.get(check).getMessage(), listAppConDto.get(check).isUseAtr(),
//							e.getErAlAtr()); con mot doan
				});
				;

			}

		}

		// monthly
		if (domain.getCategory() == AlarmCategory.MONTHLY && domain.getExtractionCondition() != null) {
			monAlarmCheckCon = (MonAlarmCheckCon) domain.getExtractionCondition();

			// get arbExtraCon
			arbExtraCon = extraResultMonthly.getListExtraResultMonByListEralID(monAlarmCheckCon.getArbExtraCon());
			listEralCheckIDOld = monAlarmCheckCon.getArbExtraCon();
			// get fixExtra monthly
			List<FixedExtraItemMonFunImport> dataFixedExtraMon = fixExtraItemMon.getAllFixedExtraItemMon();
			listFixedExtraMonFun = fixExtraMon.getByEralCheckID(monAlarmCheckCon.getMonAlarmCheckConID()).stream()
					.map(c -> FixedExtraMonFunDto.convertToImport(c)).collect(Collectors.toList());
			for (FixedExtraMonFunDto fixedExtraMonFunDto : listFixedExtraMonFun) {
				for (FixedExtraItemMonFunImport fixedExtraItemMonFunImport : dataFixedExtraMon) {
					if (fixedExtraMonFunDto.getFixedExtraItemMonNo() == fixedExtraItemMonFunImport
							.getFixedExtraItemMonNo()) {
						fixedExtraMonFunDto.setMonAlarmCheckName(fixedExtraItemMonFunImport.getFixedExtraItemMonName());
						break;
					}
				}
			} // end for
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
				new ApprovalAlarmCheckConDto(listAppAlCheckConDto),
				new MonAlarmCheckConDto(listFixedExtraMonFun, arbExtraCon, listEralCheckIDOld),
				new AlarmChkCondAgree36Dto(listCondError, listCondOt),
				new MulMonAlarmCheckConDto(mulMonCheckCondDomainEventDtos, listEralCheckMulMonIDOld),
				annualHolidayAlConDto,
				new MasterCheckAlarmCheckConditionDto(listMasterCheckFixedCon));
	}

	private AlarmCheckConditionByCategoryDto minValueFromDomain(AlarmCheckConditionByCategory domain) {
		return new AlarmCheckConditionByCategoryDto(domain.getCode().v(), domain.getName().v(),
				domain.getCategory().value, null, null, 0, null, null, null, null, null, null, null);
	}
}
