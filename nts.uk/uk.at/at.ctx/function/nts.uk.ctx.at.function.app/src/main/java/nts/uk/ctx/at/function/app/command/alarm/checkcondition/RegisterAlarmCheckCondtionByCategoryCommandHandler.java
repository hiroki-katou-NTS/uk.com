package nts.uk.ctx.at.function.app.command.alarm.checkcondition;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
//import nts.gul.collection.CollectionUtil;
import nts.gul.text.IdentifierUtil;
import nts.gul.text.StringUtil;
import nts.uk.ctx.at.function.app.command.alarm.checkcondition.agree36.AgreeCondOtCommand;
import nts.uk.ctx.at.function.app.command.alarm.checkcondition.agree36.AgreeConditionErrorCommand;
import nts.uk.ctx.at.function.app.find.alarm.checkcondition.AlarmCheckConditionByCategoryFinder;
import nts.uk.ctx.at.function.app.find.alarm.checkcondition.AppApprovalFixedExtractConditionDto;
import nts.uk.ctx.at.function.app.find.alarm.checkcondition.FixedConditionWorkRecordDto;
import nts.uk.ctx.at.function.app.find.alarm.mastercheck.MasterCheckFixedExtractConditionDto;
import nts.uk.ctx.at.function.dom.adapter.FixedConWorkRecordAdapter;
import nts.uk.ctx.at.function.dom.adapter.FixedConWorkRecordAdapterDto;
import nts.uk.ctx.at.function.dom.adapter.WorkRecordExtraConAdapter;
import nts.uk.ctx.at.function.dom.adapter.WorkRecordExtraConAdapterDto;
import nts.uk.ctx.at.function.dom.adapter.eralworkrecorddto.ErrorAlarmConAdapterDto;
import nts.uk.ctx.at.function.dom.adapter.eralworkrecorddto.ScheMonCondDto;
import nts.uk.ctx.at.function.dom.adapter.eralworkrecorddto.WorkTimeConAdapterDto;
import nts.uk.ctx.at.function.dom.adapter.eralworkrecorddto.WorkTypeConAdapterDto;
import nts.uk.ctx.at.function.dom.adapter.monthlycheckcondition.FixedExtraMonFunAdapter;
import nts.uk.ctx.at.function.dom.adapter.monthlycheckcondition.FixedExtraMonFunImport;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.annual.ScheduleAnnualAlarmCheckCond;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.schedaily.ScheduleDailyAlarmCheckCond;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.schemonthly.ScheduleMonthlyAlarmCheckCond;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.weekly.WeeklyAlarmCheckCond;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.AlarmCheckConditionByCategory;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.AlarmCheckConditionByCategoryRepository;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.AlarmCheckTargetCondition;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.ExtractionCondition;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.agree36.AgreeCondOt;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.agree36.AgreeConditionError;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.agree36.AlarmChkCondAgree36;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.agree36.IAgreeCondOtRepository;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.agree36.IAgreeConditionErrorRepository;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.annualholiday.AlarmCheckConAgr;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.annualholiday.AlarmCheckSubConAgr;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.annualholiday.AnnualHolidayAlarmCondition;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.annualholiday.IAlarmCheckConAgrRepository;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.annualholiday.IAlarmCheckSubConAgrRepository;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.appapproval.AppApprovalAlarmCheckCondition;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.appapproval.AppApprovalFixedCheckItem;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.appapproval.AppApprovalFixedExtractCondition;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.appapproval.AppApprovalFixedExtractConditionRepository;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.appapproval.ErrorAlarmMessage;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.daily.DailyAlarmCondition;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.fourweekfourdayoff.AlarmCheckCondition4W4D;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.master.MasterCheckAlarmCheckCondition;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.monthly.MonAlarmCheckCon;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.monthly.MonAlarmCheckConEvent;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.monthly.dtoevent.ExtraResultMonthlyDomainEventDto;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.multimonth.MulMonAlarmCond;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.multimonth.MulMonAlarmCondEvent;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.multimonth.doevent.MulMonCheckCondDomainEventDto;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.attendanceitem.CompareRange;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.attendanceitem.CompareSingleValue;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums.ConditionType;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.mastercheck.ErrorAlarmMessageMSTCHK;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.mastercheck.MasterCheckFixedCheckItem;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.mastercheck.MasterCheckFixedExtractCondition;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.mastercheck.MasterCheckFixedExtractConditionRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.ContinuousPeriod;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.annual.ExtractionCondScheduleYear;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.annual.ExtractionCondScheduleYearRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.annual.YearCheckItemType;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.daily.CheckTimeType;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.daily.CondContinuousTime;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.daily.CondContinuousTimeZone;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.daily.CondContinuousWrkType;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.daily.CondTime;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.daily.DaiCheckItemType;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.daily.ExtraCondScheDayRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.daily.ExtractionCondScheduleDay;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.daily.FixedExtractSDailyConRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.daily.FixedExtractionSDailyCon;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.daily.RangeToCheck;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.daily.TimeZoneTargetRange;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.monthly.DayCheckCond;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.monthly.ExtractionCondScheduleMonth;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.monthly.ExtractionCondScheduleMonthRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.monthly.FixedExtractionSMonCon;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.monthly.FixedExtractionSMonConRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.monthly.MonCheckItemType;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.monthly.PublicHolidayCheckCond;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.monthly.ScheduleMonRemainCheckCond;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.monthly.TimeCheckCond;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.monthly.TypeOfContrast;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.monthly.TypeOfDays;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.monthly.TypeOfTime;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.monthly.TypeOfVacations;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.weekly.ExtractionCondWeekly;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.weekly.ExtractionCondWeeklyRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.weekly.WeeklyCheckItemType;
import nts.uk.ctx.at.shared.dom.alarmList.AlarmCategory;
import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHolidayCode;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author HungTT
 *
 */

@Stateless
public class RegisterAlarmCheckCondtionByCategoryCommandHandler
		extends CommandHandler<AlarmCheckConditionByCategoryCommand> {

	@Inject
	private AlarmCheckConditionByCategoryRepository conditionRepo;

	@Inject
	private WorkRecordExtraConAdapter workRecordExtraConRepo;

	@Inject
	private FixedConWorkRecordAdapter fixedConWorkRecordRepo;

	@Inject
	private AppApprovalFixedExtractConditionRepository appApprovalFixedExtractConditionRepository;
	// monthly
	@Inject
	private FixedExtraMonFunAdapter fixedExtraMonFunAdapter;

	@Inject
	private IAgreeConditionErrorRepository conErrRep;

	@Inject
	private IAgreeCondOtRepository otRep;

	@Inject
	private AlarmCheckConditionByCategoryFinder alarmCheckConByCategoryFinder;

	@Inject
	private IAlarmCheckSubConAgrRepository alarmCheckSubConAgrRepository;

	@Inject
	private IAlarmCheckConAgrRepository alarmCheckConAgrRepository;
	
	@Inject
	private MasterCheckFixedExtractConditionRepository fixedMasterCheckConditionRepo;
	
	@Inject
	private FixedExtractSDailyConRepository fixedExtractSDailyConRepository;
	
	@Inject
	private ExtraCondScheDayRepository extraCondScheDayRepository;
	
	@Inject
	private FixedExtractionSMonConRepository fixedExtractSMonthlyConRepository;
	
	@Inject
	private ExtractionCondScheduleMonthRepository extraCondScheMonRepository;
	
	@Inject
	private ExtractionCondScheduleYearRepository extraCondScheYearRepository;
	
	@Inject
	private ExtractionCondWeeklyRepository extraCondWeeklyRepository;
	
	@Override
	protected void handle(CommandHandlerContext<AlarmCheckConditionByCategoryCommand> context) {
		AlarmCheckConditionByCategoryCommand command = context.getCommand();
		String companyId = AppContexts.user().companyId();
		String contractCode = AppContexts.user().contractCode();
		
		if (command.getAction() == 0
				&& conditionRepo.isCodeExist(companyId, command.getCategory(), command.getCode())) {
			throw new BusinessException("Msg_3");
		}

		// msg_832 extractcondition > 0

		Optional<AlarmCheckConditionByCategory> domainOpt = conditionRepo.find(companyId, command.getCategory(),
				command.getCode());
		if (domainOpt.isPresent()) {
			// update
			AlarmCheckConditionByCategory domain = domainOpt.get();
			AlarmCheckTargetCondition targetConditionValue = new AlarmCheckTargetCondition("",
					command.getTargetCondition().isFilterByBusinessType(),
					command.getTargetCondition().isFilterByJobTitle(),
					command.getTargetCondition().isFilterByEmployment(),
					command.getTargetCondition().isFilterByClassification(),
					command.getTargetCondition().getTargetBusinessType(),
					command.getTargetCondition().getTargetJobTitle(),
					command.getTargetCondition().getTargetEmployment(),
					command.getTargetCondition().getTargetClassification());

			ExtractionCondition extractionCondition = null;
			AlarmCategory category = AlarmCategory.values()[command.getCategory()];
			switch (category) {
			case DAILY:
				// update WorkRecordExtractCondition
				DailyAlarmCondition dailyAlramCondition = (DailyAlarmCondition) domain.getExtractionCondition();
				List<String> listErrorAlarmId = this.workRecordExtraConRepo.checkUpdateListErAl(
						dailyAlramCondition.getExtractConditionWorkRecord(),
						command.getDailyAlarmCheckCondition().getListExtractConditionWorkRecork());

				extractionCondition = command.getDailyAlarmCheckCondition() == null ? null
						: new DailyAlarmCondition(IdentifierUtil.randomUniqueId(),
								command.getDailyAlarmCheckCondition().getConditionToExtractDaily(),
								command.getDailyAlarmCheckCondition().isAddApplication(), listErrorAlarmId,
								command.getDailyAlarmCheckCondition().getListErrorAlarmCode());

				// update FixedWorkRecordExtractCondition
				for (FixedConWorkRecordAdapterDto fixedConWorkRecordAdapterDto : command.getDailyAlarmCheckCondition()
						.getListFixedExtractConditionWorkRecord()) {
					if (fixedConWorkRecordAdapterDto.getDailyAlarmConID() == null
							|| fixedConWorkRecordAdapterDto.getDailyAlarmConID().equals("")) {
						fixedConWorkRecordAdapterDto.setDailyAlarmConID(dailyAlramCondition.getDailyAlarmConID());
						this.fixedConWorkRecordRepo.addFixedConWorkRecordPub(fixedConWorkRecordAdapterDto);
					} else {
						this.fixedConWorkRecordRepo.updateFixedConWorkRecordPub(fixedConWorkRecordAdapterDto);
					}
				}
				break;
			case APPLICATION_APPROVAL:
				String appAlarmId = IdentifierUtil.randomUniqueId();
				extractionCondition = command.getApprovalAlarmCheckConDto() == null ? null
						: new AppApprovalAlarmCheckCondition(appAlarmId);
				for (AppApprovalFixedExtractConditionDto dto : command.getApprovalAlarmCheckConDto()
						.getListFixedExtractConditionWorkRecord()) {
					if (dto.getAppAlarmConId() == null || dto.getAppAlarmConId().equals("")) {
						dto.setAppAlarmConId(appAlarmId);
						this.appApprovalFixedExtractConditionRepository
						.add(toDomain(dto));
					} else {
						this.appApprovalFixedExtractConditionRepository.update(toDomain(dto));
					}
				}
				break;

			case MONTHLY:
				MonAlarmCheckCon monAlarmCheckCon = (MonAlarmCheckCon) domain.getExtractionCondition();
				// boolean checkErrorFixed = false;
				// for(FixedExtraMonFunImport fixedExtraMonFun :
				// command.getMonAlarmCheckCon().getListFixExtraMon()) {
				// if(fixedExtraMonFun.isUseAtr()) {
				// checkErrorFixed = true;
				// break;
				// }
				// }
				// boolean checkArbExtraCon = false;
				// if(command.getMonAlarmCheckCon().getArbExtraCon().size() >0)
				// {
				// for(ExtraResultMonthlyDomainEventDto dto :
				// command.getMonAlarmCheckCon().getArbExtraCon() ) {
				// if(dto.isUseAtr()) {
				// checkArbExtraCon = true;
				// break;
				// }
				// }
				// }
				//
				//
				// if(checkErrorFixed == false && checkArbExtraCon == false) {
				// throw new BusinessException("Msg_832");
				// }

				// update list mon
				List<String> listEralCheckIDOld = alarmCheckConByCategoryFinder
						.getDataByCode(command.getCategory(), command.getCode()).getMonAlarmCheckConDto()
						.getListEralCheckIDOld();
				for (int i = 0; i < command.getMonAlarmCheckCon().getArbExtraCon().size(); i++) {
					if (command.getMonAlarmCheckCon().getArbExtraCon().get(i).getErrorAlarmCheckID().equals("")) {
						command.getMonAlarmCheckCon().getArbExtraCon().get(i)
								.setErrorAlarmCheckID(IdentifierUtil.randomUniqueId());
					}
				}

				extractionCondition = command.getMonAlarmCheckCon() == null ? null
						: new MonAlarmCheckCon(IdentifierUtil.randomUniqueId(),
								command.getMonAlarmCheckCon().getArbExtraCon().stream()
										.map(c -> c.getErrorAlarmCheckID()).collect(Collectors.toList()));
				MonAlarmCheckConEvent event = new MonAlarmCheckConEvent(monAlarmCheckCon.getMonAlarmCheckConID(), true,
						false, false, command.getMonAlarmCheckCon().getArbExtraCon(), listEralCheckIDOld);
				event.toBePublished();

				// update list fixedExtraMonFun
				for (FixedExtraMonFunImport fixedExtraMonFun : command.getMonAlarmCheckCon().getListFixExtraMon()) {
					fixedExtraMonFun.setMonAlarmCheckID(monAlarmCheckCon.getMonAlarmCheckConID());
					this.fixedExtraMonFunAdapter.persistFixedExtraMon(fixedExtraMonFun);
				}
				break;

			case MULTIPLE_MONTH:
				MulMonAlarmCond mulMonAlarmCond = (MulMonAlarmCond) domain.getExtractionCondition();

				// boolean checkMulMonConds = false;
				List<MulMonCheckCondDomainEventDto> listMulMonCheckConds = command.getMulMonCheckCond()
						.getListMulMonCheckConds();
				// if(!CollectionUtil.isEmpty(listMulMonCheckConds)) {
				// for(MulMonCheckCondDomainEventDto dto : listMulMonCheckConds)
				// {
				// if(dto.isUseAtr()) {
				// checkMulMonConds = true;
				// break;
				// }
				// }
				// }
				//
				//
				// if(checkMulMonConds == false) {
				// throw new BusinessException("Msg_832");
				// }

				// update list multiple month
				List<String> listEralCheckMulIDOld = alarmCheckConByCategoryFinder
						.getDataByCode(command.getCategory(), command.getCode()).getMulMonAlarmCheckConDto()
						.getErrorAlarmCheckIDOlds();
				for (int i = 0; i < listMulMonCheckConds.size(); i++) {
					MulMonCheckCondDomainEventDto dto = listMulMonCheckConds.get(i);
					dto.setCid(companyId);
					if (dto.getErrorAlarmCheckID().equals("")) {
						dto.setErrorAlarmCheckID(IdentifierUtil.randomUniqueId());
					}
				}

				extractionCondition = command.getMulMonCheckCond() == null ? null
						: new MulMonAlarmCond(IdentifierUtil.randomUniqueId(), listMulMonCheckConds.stream()
								.map(c -> c.getErrorAlarmCheckID()).collect(Collectors.toList()));
				MulMonAlarmCondEvent eventUpdate = new MulMonAlarmCondEvent(mulMonAlarmCond.getMulMonAlarmCondID(),
						true, false, false, listMulMonCheckConds, listEralCheckMulIDOld);
				eventUpdate.toBePublished();

				break;

			case SCHEDULE_4WEEK:
				extractionCondition = command.getSchedule4WeekAlarmCheckCondition() == null ? null
						: new AlarmCheckCondition4W4D(IdentifierUtil.randomUniqueId(),
								command.getSchedule4WeekAlarmCheckCondition().getSchedule4WeekCheckCondition());
				break;
			case AGREEMENT:
				// update agree condtion error
				List<AgreeConditionError> listError = new ArrayList<>();
				listError = command.getCondAgree36().getListCondError().stream()
						.map(x -> AgreeConditionError.createFromJavaType(x.getId(), x.getCompanyId(), x.getCategory(),
								x.getCode(), x.getUseAtr(), x.getPeriod(), x.getErrorAlarm(), x.getMessageDisp()))
						.collect(Collectors.toList());
				// List<Integer> useList = listError.stream().map(x ->
				// x.getUseAtr().value).collect(Collectors.toList());
				for (AgreeConditionError item : listError) {
					if (item.getId() != null) {
						Optional<AgreeConditionError> oldOption = conErrRep.findById(item.getId(), item.getCode().v(),
								companyId, item.getCategory().value);
						if (oldOption.isPresent()) {
							conErrRep.update(item);
						} else {
							item.setId(UUID.randomUUID().toString());
							conErrRep.insert(item);
						}
					} else {
						item.setId(UUID.randomUUID().toString());
						conErrRep.insert(item);
					}
				}
				// update agree conditon ot
				List<AgreeCondOt> listOt = new ArrayList<>();
				listOt = command.getCondAgree36().getListCondOt().stream()
						.map(x -> AgreeCondOt.createFromJavaType(x.getId(), x.getCompanyId(), x.getCategory(),
								x.getCode(), x.getNo(), x.getOt36(), x.getExcessNum(), x.getMessageDisp()))
						.collect(Collectors.toList());
				// boolean check = useList.contains(1);
				// if(!check && listOt.isEmpty()){
				// throw new BusinessException("Msg_832");
				// }
				if (listOt.size() > 10) {
					throw new BusinessException("Msg_1242");
				}
				// find 1 list agreeconditionot from DB, if item in DB isn't
				// existed in UI => delete
				List<AgreeCondOt> agreeConditionDbList = otRep.findAll(command.getCode(), command.getCategory());
				for (AgreeCondOt itemDb : agreeConditionDbList) {
					Boolean exists = listOt.stream().anyMatch(x -> x.getId().equals(itemDb.getId()));
					if (!exists) {
						otRep.deleteId(command.getCode(), command.getCategory(), itemDb.getId(), itemDb.getNo());
					}
				}
				// update/insert listOt
				for (AgreeCondOt obj : listOt) {
					if (!StringUtil.isNullOrEmpty(obj.getId(), true)) {
						Optional<AgreeCondOt> oldOption = otRep.findById(obj.getId(), obj.getNo(), obj.getCode().v(),
								companyId, obj.getCategory().value);
						if (oldOption.isPresent()) {
							otRep.update(obj);
						} else {
							obj.setId(UUID.randomUUID().toString());
							otRep.insert(obj);
						}
					} else {
						obj.setId(UUID.randomUUID().toString());
						otRep.insert(obj);
					}
				}
				break;
			case ATTENDANCE_RATE_FOR_HOLIDAY:
				// AnnualHolidayAlarmCondition
				val annualHoliday = command.getAnnualHolidayAlCon();
				AlarmCheckConAgr alarmCheckConAgr = (annualHoliday == null
						|| annualHoliday.getAlarmCheckConAgr() == null)
								? null
								: new AlarmCheckConAgr(annualHoliday.getAlarmCheckConAgr().isDistByPeriod(),
										annualHoliday.getAlarmCheckConAgr().getDisplayMessage(),
										annualHoliday.getAlarmCheckConAgr().getUsageObliDay());
				AlarmCheckSubConAgr alarmCheckSubConAgr = (annualHoliday == null
						|| annualHoliday.getAlarmCheckSubConAgr() == null)
								? null
								: new AlarmCheckSubConAgr(annualHoliday.getAlarmCheckSubConAgr().isNarrowUntilNext(),
										annualHoliday.getAlarmCheckSubConAgr().isNarrowLastDay(),
										annualHoliday.getAlarmCheckSubConAgr().getNumberDayAward(),
										annualHoliday.getAlarmCheckSubConAgr().getPeriodUntilNext());
				if (alarmCheckConAgr != null) {
					val optionalCon = alarmCheckConAgrRepository.findByKey(companyId, category.value,
							command.getCode());
					if (optionalCon.isPresent()) {
						alarmCheckConAgrRepository.update(companyId, category.value, command.getCode(),
								alarmCheckConAgr);
					} else {
						alarmCheckConAgrRepository.insert(companyId, category.value, command.getCode(),
								alarmCheckConAgr);
					}
				}

				if (alarmCheckSubConAgr != null) {
					val optionalSubCon = alarmCheckSubConAgrRepository.findByKey(companyId, category.value,
							command.getCode());
					Integer numberDayAward = null, periodUntilNext = null;
					if (optionalSubCon.isPresent()) {
						if (alarmCheckSubConAgr.isNarrowLastDay()) {
							numberDayAward = alarmCheckSubConAgr.getNumberDayAward().get().v();
						} else {
							numberDayAward = optionalSubCon.get().getNumberDayAward().isPresent()
									? optionalSubCon.get().getNumberDayAward().get().v() : null;
						}

						if (alarmCheckSubConAgr.isNarrowUntilNext()) {
							periodUntilNext = alarmCheckSubConAgr.getPeriodUntilNext().get().v();
						} else {
							periodUntilNext = optionalSubCon.get().getPeriodUntilNext().isPresent()
									? optionalSubCon.get().getPeriodUntilNext().get().v() : null;
						}

						alarmCheckSubConAgr = new AlarmCheckSubConAgr(alarmCheckSubConAgr.isNarrowUntilNext(),
								alarmCheckSubConAgr.isNarrowLastDay(), numberDayAward, periodUntilNext);

						alarmCheckSubConAgrRepository.update(companyId, category.value, command.getCode(),
								alarmCheckSubConAgr);
					} else {
						if (alarmCheckSubConAgr.isNarrowLastDay())
							numberDayAward = alarmCheckSubConAgr.getNumberDayAward().get().v();
						if (alarmCheckSubConAgr.isNarrowUntilNext())
							periodUntilNext = alarmCheckSubConAgr.getPeriodUntilNext().get().v();
						alarmCheckSubConAgr = new AlarmCheckSubConAgr(alarmCheckSubConAgr.isNarrowUntilNext(),
								alarmCheckSubConAgr.isNarrowLastDay(), numberDayAward, periodUntilNext);
						alarmCheckSubConAgrRepository.insert(companyId, category.value, command.getCode(),
								alarmCheckSubConAgr);
					}
				}
				extractionCondition = command.getAnnualHolidayAlCon() == null ? null
						: new AnnualHolidayAlarmCondition(alarmCheckConAgr, alarmCheckSubConAgr);

				break;
				
			case MASTER_CHECK:
				MasterCheckAlarmCheckCondition masterCheckAlarmCheckCondition = (MasterCheckAlarmCheckCondition) domain.getExtractionCondition();
				
				extractionCondition = command.getMasterCheckAlarmCheckCondition() == null ? null
						: new MasterCheckAlarmCheckCondition(IdentifierUtil.randomUniqueId());
					
				List<MasterCheckFixedExtractCondition> lstCondition = new ArrayList<>();
				for(MasterCheckFixedExtractConditionDto fixedMasterCheckExtConDto : command.getMasterCheckAlarmCheckCondition().getListFixedMasterCheckCondition()) {
					if(fixedMasterCheckExtConDto.getErrorAlarmCheckId() == null 
							|| fixedMasterCheckExtConDto.getErrorAlarmCheckId().equals("")) {
						fixedMasterCheckExtConDto.setErrorAlarmCheckId(masterCheckAlarmCheckCondition.getAlarmCheckId());
					}
					MasterCheckFixedExtractCondition fixedMasterCheckExtCon = new MasterCheckFixedExtractCondition(
							fixedMasterCheckExtConDto.getErrorAlarmCheckId(),
							EnumAdaptor.valueOf(fixedMasterCheckExtConDto.getNo(), MasterCheckFixedCheckItem.class),
							Optional.of(new ErrorAlarmMessageMSTCHK(fixedMasterCheckExtConDto.getMessage())),
							fixedMasterCheckExtConDto.isUseAtr()
							);
					lstCondition.add(fixedMasterCheckExtCon);
				}
				fixedMasterCheckConditionRepo.persist(lstCondition);
				break;
			case SCHEDULE_DAILY:
				if (!command.getScheFixCondDay().getSheFixItemDays().isEmpty()) {
					saveScheduleFixCondDay(companyId, command.getScheFixCondDay().getErAlCheckLinkId(), command.getScheFixCondDay().getSheFixItemDays(), false);
				}
				if (!command.getScheAnyCondDay().getScheAnyCondDays().isEmpty()) {
					saveScheduleAnyCondDay(companyId, command.getScheAnyCondDay().getErAlCheckLinkId(), command.getScheAnyCondDay().getScheAnyCondDays());
				} else {
					extraCondScheDayRepository.delete(contractCode, companyId, command.getScheAnyCondDay().getErAlCheckLinkId());
				}
				break;
			case SCHEDULE_MONTHLY:
				if (!command.getScheFixCondDay().getSheFixItemDays().isEmpty()) {
					saveScheduleFixCondMonth(companyId, command.getScheFixCondDay().getErAlCheckLinkId(), command.getScheFixCondDay().getSheFixItemDays(), false);
				}
				if (!command.getScheAnyCondDay().getScheAnyCondDays().isEmpty()) {
					saveScheduleAnyCondMon(companyId, command.getScheAnyCondDay().getErAlCheckLinkId(), command.getScheAnyCondDay().getScheAnyCondDays());
				} else {
					extraCondScheMonRepository.delete(contractCode, companyId, command.getScheAnyCondDay().getErAlCheckLinkId());
				}
				break;
			case SCHEDULE_YEAR:
				if (!command.getScheAnyCondDay().getScheAnyCondDays().isEmpty()) {
					saveScheduleAnyCondYear(companyId, command.getScheAnyCondDay().getErAlCheckLinkId(), command.getScheAnyCondDay().getScheAnyCondDays());
				}
				else {
					extraCondScheYearRepository.delete(contractCode, companyId, command.getScheAnyCondDay().getErAlCheckLinkId());
				}
				break;
			case WEEKLY:
				if (!command.getScheAnyCondDay().getScheAnyCondDays().isEmpty()) {
					saveAnyCondWeekly(companyId, command.getScheAnyCondDay().getErAlCheckLinkId(), command.getScheAnyCondDay().getScheAnyCondDays());
				} else {
					extraCondWeeklyRepository.delete(contractCode, companyId, command.getScheAnyCondDay().getErAlCheckLinkId());
				}
				break;
			default:
				break;
			}
			domain.changeState(command.getName(), command.getAvailableRoles(), targetConditionValue,
					extractionCondition);

			conditionRepo.update(domain);
		} else {
			// add
			ExtractionCondition extractionCondition = null;
			AlarmCategory category = AlarmCategory.values()[command.getCategory()];
			switch (category) {
			case DAILY:
				String dailyAlarmId = IdentifierUtil.randomUniqueId();
				// add WorkRecordExtractCondition
				List<String> listErrorAlarmId = this.workRecordExtraConRepo
						.addNewListErAl(command.getDailyAlarmCheckCondition().getListExtractConditionWorkRecork());

				extractionCondition = command.getDailyAlarmCheckCondition() == null ? null
						: new DailyAlarmCondition(dailyAlarmId,
								command.getDailyAlarmCheckCondition().getConditionToExtractDaily(),
								command.getDailyAlarmCheckCondition().isAddApplication(), listErrorAlarmId,
								command.getDailyAlarmCheckCondition().getListErrorAlarmCode());

				// add FixedWorkRecordExtractCondition
				for (FixedConWorkRecordAdapterDto fixedConWorkRecordAdapterDto : command.getDailyAlarmCheckCondition()
						.getListFixedExtractConditionWorkRecord()) {
					fixedConWorkRecordAdapterDto.setDailyAlarmConID(dailyAlarmId);
					this.fixedConWorkRecordRepo.addFixedConWorkRecordPub(fixedConWorkRecordAdapterDto);
				}
				break;
			case APPLICATION_APPROVAL:
				String appAlarmId = IdentifierUtil.randomUniqueId();
				extractionCondition = command.getApprovalAlarmCheckConDto() == null ? null
						: new AppApprovalAlarmCheckCondition(appAlarmId);
				for (AppApprovalFixedExtractConditionDto dto : command.getApprovalAlarmCheckConDto()
						.getListFixedExtractConditionWorkRecord()) {
					dto.setAppAlarmConId(appAlarmId);
					appApprovalFixedExtractConditionRepository
							.add(toDomain(dto));
				}
				break;

			case MONTHLY:
				// boolean checkErrorFixed = false;
				// for(FixedExtraMonFunImport fixedExtraMonFun :
				// command.getMonAlarmCheckCon().getListFixExtraMon()) {
				// if(fixedExtraMonFun.isUseAtr()) {
				// checkErrorFixed = true;
				// break;
				// }
				// }
				//
				// boolean checkArbExtraCon = false;
				// if(command.getMonAlarmCheckCon().getArbExtraCon().size() >0)
				// {
				// for(ExtraResultMonthlyDomainEventDto dto :
				// command.getMonAlarmCheckCon().getArbExtraCon() ) {
				// if(dto.isUseAtr()) {
				// checkArbExtraCon = true;
				// break;
				// }
				// }
				// }
				// if(checkErrorFixed == false && checkArbExtraCon == false) {
				// throw new BusinessException("Msg_832");
				// }

				String monAlarmCheckConID = IdentifierUtil.randomUniqueId();
				for (ExtraResultMonthlyDomainEventDto item : command.getMonAlarmCheckCon().getArbExtraCon()) {
					item.setErrorAlarmCheckID(IdentifierUtil.randomUniqueId());
				}
				extractionCondition = command.getMonAlarmCheckCon() == null ? null
						: new MonAlarmCheckCon(monAlarmCheckConID, command.getMonAlarmCheckCon().getArbExtraCon()
								.stream().map(c -> c.getErrorAlarmCheckID()).collect(Collectors.toList()));
				// add list mon
				List<String> listEralCheckIDOld = new ArrayList<>();
				MonAlarmCheckConEvent event = new MonAlarmCheckConEvent(monAlarmCheckConID, false, true, false,
						command.getMonAlarmCheckCon().getArbExtraCon(), listEralCheckIDOld);
				event.toBePublished();
				// add list fixedExtraMonFun
				for (FixedExtraMonFunImport fixedExtraMonFun : command.getMonAlarmCheckCon().getListFixExtraMon()) {
					fixedExtraMonFun.setMonAlarmCheckID(monAlarmCheckConID);
					this.fixedExtraMonFunAdapter.persistFixedExtraMon(fixedExtraMonFun);
				}

				break;

			case MULTIPLE_MONTH:
				// boolean checkMulMonConds = false;
				List<MulMonCheckCondDomainEventDto> listMulMonCheckConds = command.getMulMonCheckCond()
						.getListMulMonCheckConds();
				// if(!CollectionUtil.isEmpty(listMulMonCheckConds)) {
				// for(MulMonCheckCondDomainEventDto item :
				// listMulMonCheckConds) {
				// if(item.isUseAtr()) {
				// checkMulMonConds = true;
				// break;
				// }
				// }
				// }
				// if(checkMulMonConds == false) {
				// throw new BusinessException("Msg_832");
				// }

				String mulMonAlarmCondID = IdentifierUtil.randomUniqueId();
				for (MulMonCheckCondDomainEventDto item : listMulMonCheckConds) {
					item.setCid(companyId);
					item.setErrorAlarmCheckID(IdentifierUtil.randomUniqueId());
				}

				extractionCondition = command.getMulMonCheckCond() == null ? null
						: new MulMonAlarmCond(mulMonAlarmCondID, listMulMonCheckConds.stream()
								.map(c -> c.getErrorAlarmCheckID()).collect(Collectors.toList()));

				// add list multiple months
				List<String> listEralCheckMulIDOld = new ArrayList<>();
				MulMonAlarmCondEvent mulMonAlarmCondEvent = new MulMonAlarmCondEvent(mulMonAlarmCondID, false, true,
						false, listMulMonCheckConds, listEralCheckMulIDOld);
				mulMonAlarmCondEvent.toBePublished();

				break;
			case SCHEDULE_4WEEK:
				extractionCondition = command.getSchedule4WeekAlarmCheckCondition() == null ? null
						: new AlarmCheckCondition4W4D(IdentifierUtil.randomUniqueId(),
								command.getSchedule4WeekAlarmCheckCondition().getSchedule4WeekCheckCondition());
				break;

			case AGREEMENT:
				// update agree conditon ot
				List<AgreeCondOtCommand> listOt = new ArrayList<>();
				// List<AgreeConditionErrorCommand> listError = new
				// ArrayList<>();
				// listError = command.getCondAgree36().getListCondError();
				listOt = command.getCondAgree36().getListCondOt();
				// List<Integer> useList = listError.stream().map(x ->
				// x.getUseAtr()).collect(Collectors.toList());
				// boolean check = useList.contains(1);
				// if(!check && listOt.isEmpty()){
				// throw new BusinessException("Msg_832");
				// }
				if (listOt.size() > 10) {
					throw new BusinessException("Msg_1242");
				}
				break;
			case ATTENDANCE_RATE_FOR_HOLIDAY:
				val annualHoliday = command.getAnnualHolidayAlCon();
				AlarmCheckConAgr alarmCheckConAgr = (annualHoliday == null
						|| annualHoliday.getAlarmCheckConAgr() == null)
								? null
								: new AlarmCheckConAgr(annualHoliday.getAlarmCheckConAgr().isDistByPeriod(),
										annualHoliday.getAlarmCheckConAgr().getDisplayMessage(),
										annualHoliday.getAlarmCheckConAgr().getUsageObliDay());
				AlarmCheckSubConAgr alarmCheckSubConAgr = (annualHoliday == null
						|| annualHoliday.getAlarmCheckSubConAgr() == null)
								? null
								: new AlarmCheckSubConAgr(annualHoliday.getAlarmCheckSubConAgr().isNarrowUntilNext(),
										annualHoliday.getAlarmCheckSubConAgr().isNarrowLastDay(),
										annualHoliday.getAlarmCheckSubConAgr().getNumberDayAward(),
										annualHoliday.getAlarmCheckSubConAgr().getPeriodUntilNext());
				Integer numberDayAward = null, periodUntilNext = null;
				if (alarmCheckSubConAgr.isNarrowLastDay())
					numberDayAward = alarmCheckSubConAgr.getNumberDayAward().get().v();
				if (alarmCheckSubConAgr.isNarrowUntilNext())
					periodUntilNext = alarmCheckSubConAgr.getPeriodUntilNext().get().v();
				alarmCheckSubConAgr = new AlarmCheckSubConAgr(alarmCheckSubConAgr.isNarrowUntilNext(),
						alarmCheckSubConAgr.isNarrowLastDay(), numberDayAward, periodUntilNext);
				extractionCondition = new AnnualHolidayAlarmCondition(alarmCheckConAgr, alarmCheckSubConAgr);
				break;
			case MASTER_CHECK:	
				String errorAlarmCheckId = IdentifierUtil.randomUniqueId();
				
				extractionCondition = command.getMasterCheckAlarmCheckCondition() == null ? null
						: new MasterCheckAlarmCheckCondition(errorAlarmCheckId);
				List<MasterCheckFixedExtractCondition> lstMasterCheck = new ArrayList<>();
				for(MasterCheckFixedExtractConditionDto fixedMasterCheckExtConDto : command.getMasterCheckAlarmCheckCondition().getListFixedMasterCheckCondition()) {
						fixedMasterCheckExtConDto.setErrorAlarmCheckId(errorAlarmCheckId);
						MasterCheckFixedExtractCondition fixedMasterCheckExtCon = new MasterCheckFixedExtractCondition(
								fixedMasterCheckExtConDto.getErrorAlarmCheckId(),
								EnumAdaptor.valueOf(fixedMasterCheckExtConDto.getNo(), MasterCheckFixedCheckItem.class),
								Optional.of(new ErrorAlarmMessageMSTCHK(fixedMasterCheckExtConDto.getMessage())),
								fixedMasterCheckExtConDto.isUseAtr()
								);						
						lstMasterCheck.add(fixedMasterCheckExtCon);
				}
				fixedMasterCheckConditionRepo.persist(lstMasterCheck);
				break;
			case SCHEDULE_DAILY:
				String eralCheckIdFixedItem = IdentifierUtil.randomUniqueId();
				String eralCheckIdOptionalItem = IdentifierUtil.randomUniqueId();
				
				saveScheduleFixCondDay(companyId, eralCheckIdFixedItem, command.getScheFixCondDay().getSheFixItemDays(), true);
				saveScheduleAnyCondDay(companyId, eralCheckIdOptionalItem, command.getScheAnyCondDay().getScheAnyCondDays());
				extractionCondition = new ScheduleDailyAlarmCheckCond(eralCheckIdOptionalItem, eralCheckIdFixedItem);
				break;
			case SCHEDULE_MONTHLY:
				String eralCheckIdFixedMonItem = IdentifierUtil.randomUniqueId();
				String eralCheckIdOptionalMonItem = IdentifierUtil.randomUniqueId();
				
				saveScheduleFixCondMonth(companyId, eralCheckIdFixedMonItem, command.getScheFixCondDay().getSheFixItemDays(), true);
				saveScheduleAnyCondMon(companyId, eralCheckIdOptionalMonItem, command.getScheAnyCondDay().getScheAnyCondDays());
				extractionCondition = new ScheduleMonthlyAlarmCheckCond(eralCheckIdOptionalMonItem, eralCheckIdFixedMonItem);
				break;
			case SCHEDULE_YEAR:
				String eralCheckIdOptionalYearItem = IdentifierUtil.randomUniqueId();
				saveScheduleAnyCondYear(companyId, eralCheckIdOptionalYearItem, command.getScheAnyCondDay().getScheAnyCondDays());
				extractionCondition = new ScheduleAnnualAlarmCheckCond(eralCheckIdOptionalYearItem);
				break;
			case WEEKLY:
				String eralCheckIdOptionalWeeklyItem = IdentifierUtil.randomUniqueId();
				saveAnyCondWeekly(companyId, eralCheckIdOptionalWeeklyItem, command.getScheAnyCondDay().getScheAnyCondDays());
				extractionCondition = new WeeklyAlarmCheckCond(eralCheckIdOptionalWeeklyItem);
				break;
			default:
				break;
			}

			AlarmCheckConditionByCategory domain = new AlarmCheckConditionByCategory(companyId, command.getCategory(),
					command.getCode(), command.getName(),
					new AlarmCheckTargetCondition(IdentifierUtil.randomUniqueId(),
							command.getTargetCondition().isFilterByBusinessType(),
							command.getTargetCondition().isFilterByJobTitle(),
							command.getTargetCondition().isFilterByEmployment(),
							command.getTargetCondition().isFilterByClassification(),
							command.getTargetCondition().getTargetBusinessType(),
							command.getTargetCondition().getTargetJobTitle(),
							command.getTargetCondition().getTargetEmployment(),
							command.getTargetCondition().getTargetClassification()),
					command.getAvailableRoles(), extractionCondition,
					new AlarmChkCondAgree36(
							command.getCondAgree36().getListCondError().stream()
									.map(x -> AgreeConditionErrorCommand.toDomain(x)).collect(Collectors.toList()),
							command.getCondAgree36().getListCondOt().stream().map(c -> AgreeCondOtCommand.toDomain(c))
									.collect(Collectors.toList())));

			conditionRepo.add(domain);
		}
	}

	private AppApprovalFixedExtractCondition toDomain(AppApprovalFixedExtractConditionDto dto) {
		return new AppApprovalFixedExtractCondition(dto.getAppAlarmConId(),
				EnumAdaptor.valueOf(dto.getNo(), AppApprovalFixedCheckItem.class),
				Optional.ofNullable(new ErrorAlarmMessage(dto.getDisplayMessage())), 
				dto.isUseAtr());
	}
	
	/**
	 * Schedule Daily with tab3
	 * (process tab 固有のチェック条件)
	 * @param sheFixItemDays
	 */
	private void saveScheduleFixCondDay(String companyId, String eralCheckId, List<FixedConditionWorkRecordDto> sheFixItemDays, boolean isAdd) {
		String contractCode = AppContexts.user().contractCode();
		
		for(FixedConditionWorkRecordDto item: sheFixItemDays) {
			FixedExtractionSDailyCon domain = FixedExtractionSDailyCon.create(
					eralCheckId, item.getFixConWorkRecordNo(), item.getMessage(), item.isUseAtr());
			if (isAdd) {
				domain = FixedExtractionSDailyCon.create(
						eralCheckId, item.getFixConWorkRecordNo(), item.getMessage(), item.isUseAtr());
				fixedExtractSDailyConRepository.add(contractCode, companyId, domain);
			} else {
				fixedExtractSDailyConRepository.update(contractCode, companyId, domain);
			}
		}
	}
	
	/**
	 * Schedule daily with tab2
	 * @param companyId
	 * @param scheAnyCondDays
	 * @return list of error alarm check id
	 */
	private void saveScheduleAnyCondDay(String companyId, String eralCheckId, List<WorkRecordExtraConAdapterDto> scheAnyCondDays) {
		String contractCode = AppContexts.user().contractCode();
		List<ExtractionCondScheduleDay> listOptionalItem = extraCondScheDayRepository.getScheAnyCondDay(contractCode, companyId, eralCheckId);
		
		int alarmNo = 0;
		for(WorkRecordExtraConAdapterDto item: scheAnyCondDays) {
			alarmNo++;
			item.setSortOrderBy(alarmNo);
			DaiCheckItemType dailyCheckItemType = EnumAdaptor.valueOf(item.getCheckItem(), DaiCheckItemType.class);
			RangeToCheck rangeToCheck = RangeToCheck.ALL;
			
			ExtractionCondScheduleDay domain = ExtractionCondScheduleDay.create(
					eralCheckId, item.getSortOrderBy(), item.isUseAtr(), item.getNameWKRecord(),
					item.getErrorAlarmCondition().getDisplayMessage(),
					dailyCheckItemType,
					rangeToCheck);
			
			WorkTypeConAdapterDto workTypeCondition = item.getErrorAlarmCondition().getWorkTypeCondition();
			if (item.getErrorAlarmCondition().getWorkTypeCondition() != null) {
				rangeToCheck = EnumAdaptor.valueOf(workTypeCondition.getComparePlanAndActual(), RangeToCheck.class);
				domain.setTargetWrkType(rangeToCheck);
			}
			
			if (dailyCheckItemType == DaiCheckItemType.TIME) {
				CheckTimeType checkTimeType = EnumAdaptor.valueOf(workTypeCondition.getCheckTimeType(), CheckTimeType.class);
				if (workTypeCondition.getComparisonOperator() > 5) {
					if (workTypeCondition.getCompareStartValue() != null && workTypeCondition.getCompareEndValue() != null) {
						CompareRange checkedCondition = new CompareRange<>(workTypeCondition.getComparisonOperator());
		                ((CompareRange) checkedCondition).setStartValue(workTypeCondition.getCompareStartValue());
		                ((CompareRange) checkedCondition).setEndValue(workTypeCondition.getCompareEndValue());
		                
						CondTime time = new CondTime(checkedCondition, checkTimeType, workTypeCondition.getPlanLstWorkType());
						domain.setScheduleCheckCond(time);
					}
				} else {
					if (workTypeCondition.getCompareStartValue() != null) {
						CompareSingleValue checkedCondition = new CompareSingleValue<>(workTypeCondition.getComparisonOperator(), ConditionType.FIXED_VALUE.value);
						((CompareSingleValue)checkedCondition).setValue(workTypeCondition.getCompareStartValue());
						CondTime time = new CondTime(checkedCondition, checkTimeType, workTypeCondition.getPlanLstWorkType());
						domain.setScheduleCheckCond(time);
					}
				}
			}
			
			if (dailyCheckItemType == DaiCheckItemType.CONTINUOUS_TIME) {
				CheckTimeType checkTimeType = EnumAdaptor.valueOf(workTypeCondition.getCheckTimeType(), CheckTimeType.class);
				if (workTypeCondition.getComparisonOperator() > 5) {
					if (workTypeCondition.getCompareStartValue() != null && workTypeCondition.getCompareEndValue() != null) {
						CompareRange checkedCondition = new CompareRange<>(workTypeCondition.getComparisonOperator());
		                ((CompareRange) checkedCondition).setStartValue(workTypeCondition.getCompareStartValue());
		                ((CompareRange) checkedCondition).setEndValue(workTypeCondition.getCompareEndValue());
		                
		                CondContinuousTime continuousTime = new CondContinuousTime(checkedCondition, checkTimeType,
		                		workTypeCondition.getPlanLstWorkType(),
		                		new ContinuousPeriod(item.getErrorAlarmCondition().getContinuousPeriod()));
						domain.setScheduleCheckCond(continuousTime);
					}
				} else {
					if (workTypeCondition.getCompareStartValue() != null) {
						CompareSingleValue checkedCondition = new CompareSingleValue<>(workTypeCondition.getComparisonOperator(), ConditionType.FIXED_VALUE.value);
						((CompareSingleValue)checkedCondition).setValue(workTypeCondition.getCompareStartValue());
						CondContinuousTime continuousTime = new CondContinuousTime(checkedCondition,
								checkTimeType,
								workTypeCondition.getPlanLstWorkType(),
								new ContinuousPeriod(item.getErrorAlarmCondition().getContinuousPeriod()));
						domain.setScheduleCheckCond(continuousTime);
					}
				}
			}
			
			if (dailyCheckItemType == DaiCheckItemType.CONTINUOUS_TIMEZONE) {
				WorkTimeConAdapterDto workTimeCondition = item.getErrorAlarmCondition().getWorkTimeCondition();
				CondContinuousTimeZone continuousTimeZone = new CondContinuousTimeZone(
						workTypeCondition.getPlanLstWorkType(),
						workTimeCondition.getPlanLstWorkTime(),
						new ContinuousPeriod(item.getErrorAlarmCondition().getContinuousPeriod()));
				domain.setScheduleCheckCond(continuousTimeZone);
				domain.setTimeZoneTargetRange(EnumAdaptor.valueOf(workTimeCondition.getComparePlanAndActual(), TimeZoneTargetRange.class));
			}
			
			if (dailyCheckItemType == DaiCheckItemType.CONTINUOUS_WORK) {
				CondContinuousWrkType continuousWorkType = new CondContinuousWrkType(
						workTypeCondition.getPlanLstWorkType(), 
						new ContinuousPeriod(item.getErrorAlarmCondition().getContinuousPeriod()));
				domain.setScheduleCheckCond(continuousWorkType);
			}
			
			if (!listOptionalItem.stream().anyMatch(x -> x.getErrorAlarmId().equals(eralCheckId) && x.getSortOrder() == item.getSortOrderBy())) {
				extraCondScheDayRepository.add(contractCode, companyId, domain);
			} else {
				extraCondScheDayRepository.update(contractCode, companyId, domain);
			}
		}
		
		// sync again item when user remove in list
		for(ExtractionCondScheduleDay item: listOptionalItem) {
			if (!scheAnyCondDays.stream().anyMatch(x -> item.getErrorAlarmId().equals(eralCheckId) && item.getSortOrder() == x.getSortOrderBy())) {
				extraCondScheDayRepository.delete(contractCode, companyId, eralCheckId, item.getSortOrder());
			}
		}
	}
	
	/**
	 * Schedule Monthly with tab3
	 * (process tab 固有のチェック条件)
	 * @param sheFixItemDays
	 */
	private void saveScheduleFixCondMonth(String companyId, String eralCheckId, List<FixedConditionWorkRecordDto> sheFixItemDays, boolean isAdd) {
		String contractCode = AppContexts.user().contractCode();
		
		for(FixedConditionWorkRecordDto item: sheFixItemDays) {
			FixedExtractionSMonCon domain = FixedExtractionSMonCon.create(
					eralCheckId, item.getFixConWorkRecordNo(), item.getMessage(), item.isUseAtr());
			if (isAdd) {
				fixedExtractSMonthlyConRepository.add(contractCode, companyId, domain);
			} else {
				fixedExtractSMonthlyConRepository.update(contractCode, companyId, domain);
			}
		}
	}
	
	/**
	 * Schedule daily with tab2
	 * @param companyId
	 * @param eralCheckId
	 * @param scheAnyCondDays
	 * @return list of error alarm check id
	 */
	private void saveScheduleAnyCondMon(String companyId, String eralCheckId, List<WorkRecordExtraConAdapterDto> scheAnyCondDays) {
		String contractCode = AppContexts.user().contractCode();
		List<ExtractionCondScheduleMonth> listOptionalItem = extraCondScheMonRepository.getScheAnyCond(contractCode, companyId, eralCheckId);
		
		int alarmNo = 0;
		for(WorkRecordExtraConAdapterDto item: scheAnyCondDays) {
			alarmNo++;
			item.setSortOrderBy(alarmNo);
			MonCheckItemType checkItemType = EnumAdaptor.valueOf(item.getCheckItem(), MonCheckItemType.class);
			ErrorAlarmConAdapterDto errorAlarmCondition = item.getErrorAlarmCondition();
			ScheMonCondDto monthlyCondition = errorAlarmCondition.getMonthlyCondition();
			
			ExtractionCondScheduleMonth domain = ExtractionCondScheduleMonth.create(
					eralCheckId, item.getSortOrderBy(), item.isUseAtr(), item.getNameWKRecord(),
					item.getErrorAlarmCondition().getDisplayMessage(),
					checkItemType);
			
			if (checkItemType == MonCheckItemType.CONTRAST) {
				TypeOfContrast typeOfContrast = EnumAdaptor.valueOf(monthlyCondition.getScheCheckCondition(), TypeOfContrast.class);
				PublicHolidayCheckCond scheCheckedCondition = new PublicHolidayCheckCond(typeOfContrast);
				domain.setScheCheckConditions(scheCheckedCondition);
			}
			
			if (checkItemType == MonCheckItemType.TIME) {
				TypeOfTime typeOfTime = EnumAdaptor.valueOf(monthlyCondition.getScheCheckCondition(), TypeOfTime.class);
				TimeCheckCond scheCheckedCondition = new TimeCheckCond(typeOfTime);
				
				domain.setScheCheckConditions(scheCheckedCondition);
			}
			
			if (checkItemType == MonCheckItemType.NUMBER_DAYS) {
				TypeOfDays typeOfDays = EnumAdaptor.valueOf(monthlyCondition.getScheCheckCondition(), TypeOfDays.class);
				DayCheckCond scheCheckedCondition = new DayCheckCond(typeOfDays);
				domain.setScheCheckConditions(scheCheckedCondition);
			}
			
			if (checkItemType == MonCheckItemType.REMAIN_NUMBER) {
				TypeOfVacations typeOfVacations = EnumAdaptor.valueOf(monthlyCondition.getScheCheckCondition(), TypeOfVacations.class);
				ScheduleMonRemainCheckCond scheCheckedCondition = new ScheduleMonRemainCheckCond(
						typeOfVacations, 
						Optional.of(new SpecialHolidayCode(monthlyCondition.getSpecialHolidayCode())));
				domain.setScheCheckConditions(scheCheckedCondition);
			}
			
			if (monthlyCondition.getComparisonOperator() > 5) {
				if (monthlyCondition.getCompareStartValue() != null && monthlyCondition.getCompareEndValue() != null) {
					CompareRange checkedCondition = new CompareRange<>(monthlyCondition.getComparisonOperator());
	                ((CompareRange) checkedCondition).setStartValue(monthlyCondition.getCompareStartValue());
	                ((CompareRange) checkedCondition).setEndValue(monthlyCondition.getCompareEndValue());
	                domain.setCheckConditions(checkedCondition);
				}
			} else {
				if (monthlyCondition.getCompareStartValue() != null) {
					CompareSingleValue checkedCondition = new CompareSingleValue<>(monthlyCondition.getComparisonOperator(), ConditionType.FIXED_VALUE.value);
					((CompareSingleValue)checkedCondition).setValue(monthlyCondition.getCompareStartValue());
					domain.setCheckConditions(checkedCondition);
				}
			}
			
			if (!listOptionalItem.stream().anyMatch(x -> x.getErrorAlarmId().equals(eralCheckId) && x.getSortOrder() == item.getSortOrderBy())) {
				extraCondScheMonRepository.add(contractCode, companyId, domain);
			} else {
				extraCondScheMonRepository.update(contractCode, companyId, domain);
			}
		}
		
		// sync again item when user remove in list
		for(ExtractionCondScheduleMonth item: listOptionalItem) {
			if (!scheAnyCondDays.stream().anyMatch(x -> item.getErrorAlarmId().equals(eralCheckId) && item.getSortOrder() == x.getSortOrderBy())) {
				extraCondScheMonRepository.delete(contractCode, companyId, eralCheckId, item.getSortOrder());
			}
		}
	}
	
	/**
	 * Schedule year with tab2
	 * @param companyId
	 * @param eralCheckId
	 * @param scheAnyCondDays
	 * @return list of error alarm check id
	 */
	private void saveScheduleAnyCondYear(String companyId, String eralCheckId, List<WorkRecordExtraConAdapterDto> scheAnyCondDays) {
		String contractCode = AppContexts.user().contractCode();
		List<ExtractionCondScheduleYear> listOptionalItem = extraCondScheYearRepository.getScheAnyCond(contractCode, companyId, eralCheckId);
		
		int alarmNo = 0;
		for(WorkRecordExtraConAdapterDto item: scheAnyCondDays) {
			alarmNo++;
			item.setSortOrderBy(alarmNo);
			YearCheckItemType checkItemType = EnumAdaptor.valueOf(item.getCheckItem(), YearCheckItemType.class);
			ErrorAlarmConAdapterDto errorAlarmCondition = item.getErrorAlarmCondition();
			ScheMonCondDto monthlyCondition = errorAlarmCondition.getMonthlyCondition();
			
			ExtractionCondScheduleYear domain = ExtractionCondScheduleYear.create(
					eralCheckId, item.getSortOrderBy(), item.isUseAtr(), item.getNameWKRecord(),
					item.getErrorAlarmCondition().getDisplayMessage(),
					checkItemType);
			
			if (checkItemType == YearCheckItemType.TIME) {
				TypeOfTime typeOfTime = EnumAdaptor.valueOf(monthlyCondition.getScheCheckCondition(), TypeOfTime.class);
				nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.annual.TimeCheckCond scheCheckedCondition = new nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.annual.TimeCheckCond(typeOfTime);
				domain.setScheCheckConditions(scheCheckedCondition);
			}
			
			if (checkItemType == YearCheckItemType.DAY_NUMBER) {
				TypeOfDays typeOfDays = EnumAdaptor.valueOf(monthlyCondition.getScheCheckCondition(), TypeOfDays.class);
				nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.annual.DayCheckCond scheCheckedCondition = new nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.annual.DayCheckCond(typeOfDays);
				domain.setScheCheckConditions(scheCheckedCondition);
			}
			
			if (monthlyCondition.getComparisonOperator() > 5) {
				if (monthlyCondition.getCompareStartValue() != null && monthlyCondition.getCompareEndValue() != null) {
					CompareRange checkedCondition = new CompareRange<>(monthlyCondition.getComparisonOperator());
	                ((CompareRange) checkedCondition).setStartValue(monthlyCondition.getCompareStartValue());
	                ((CompareRange) checkedCondition).setEndValue(monthlyCondition.getCompareEndValue());
	                domain.setCheckConditions(checkedCondition);
				}
			} else {
				if (monthlyCondition.getCompareStartValue() != null) {
					CompareSingleValue checkedCondition = new CompareSingleValue<>(monthlyCondition.getComparisonOperator(), ConditionType.FIXED_VALUE.value);
					((CompareSingleValue)checkedCondition).setValue(monthlyCondition.getCompareStartValue());
					domain.setCheckConditions(checkedCondition);
				}
			}
			
			if (!listOptionalItem.stream().anyMatch(x -> x.getErrorAlarmId().equals(eralCheckId) && x.getSortOrder() == item.getSortOrderBy())) {
				extraCondScheYearRepository.add(contractCode, companyId, domain);
			} else {
				extraCondScheYearRepository.update(contractCode, companyId, domain);
			}
		}
		
		// sync again item when user remove in list
		for(ExtractionCondScheduleYear item: listOptionalItem) {
			if (!scheAnyCondDays.stream().anyMatch(x -> item.getErrorAlarmId().equals(eralCheckId) && item.getSortOrder() == x.getSortOrderBy())) {
				extraCondScheYearRepository.delete(contractCode, companyId, eralCheckId, item.getSortOrder());
			}
		}
	}
	
	
	/**
	 * weekly with tab2
	 * @param companyId
	 * @param eralCheckId
	 * @param scheAnyCondDays
	 * @return list of error alarm check id
	 */
	private void saveAnyCondWeekly(String companyId, String eralCheckId, List<WorkRecordExtraConAdapterDto> scheAnyCondDays) {
		String contractCode = AppContexts.user().contractCode();
		List<ExtractionCondWeekly> listOptionalItem = extraCondWeeklyRepository.getAnyCond(contractCode, companyId, eralCheckId);
		
		int alarmNo = 0;
		for(WorkRecordExtraConAdapterDto item: scheAnyCondDays) {
			alarmNo++;
			item.setSortOrderBy(alarmNo);
			WeeklyCheckItemType checkItemType = WeeklyCheckItemType.TIME;
			if (item.getCheckItem() > 0) {
				checkItemType = EnumAdaptor.valueOf(item.getCheckItem(), WeeklyCheckItemType.class);
			}
			
			ErrorAlarmConAdapterDto errorAlarmCondition = item.getErrorAlarmCondition();
			ScheMonCondDto monthlyCondition = errorAlarmCondition.getMonthlyCondition();

			// TODO:atdItemConditionの移送
			ExtractionCondWeekly domain = ExtractionCondWeekly.create(
					eralCheckId, item.getSortOrderBy(), item.isUseAtr(), item.getNameWKRecord(),
					item.getErrorAlarmCondition().getDisplayMessage(),
					checkItemType,
					errorAlarmCondition.getContinuousPeriod());

			if (!listOptionalItem.stream().anyMatch(x -> x.getErrorAlarmId().equals(eralCheckId) && x.getSortOrder() == item.getSortOrderBy())) {
				extraCondWeeklyRepository.add(contractCode, companyId, domain);
			} else {
				extraCondWeeklyRepository.update(contractCode, companyId, domain);
			}
		}
		
		// sync again item when user remove in list
		for(ExtractionCondWeekly item: listOptionalItem) {
			if (!scheAnyCondDays.stream().anyMatch(x -> item.getErrorAlarmId().equals(eralCheckId) && item.getSortOrder() == x.getSortOrderBy())) {
				extraCondWeeklyRepository.delete(contractCode, companyId, eralCheckId, item.getSortOrder());
			}
		}
	}

}