package nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition.algorithm;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.YearMonthPeriod;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.dom.monthly.TimeOfMonthlyRepository;
import nts.uk.ctx.at.record.dom.monthly.mergetable.RemainMergeRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.attendanceitem.AttendanceItemCondition;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.attendanceitem.ErAlAttendanceItemCondition;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums.CompareOperatorText;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums.ConditionAtr;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums.ConvertCompareTypeToText;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums.ErrorAlarmConditionType;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums.LogicalOperator;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.mastercheck.algorithm.WorkPlaceHistImportAl;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition.ExtraResultMonthly;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition.ExtraResultMonthlyRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition.FixedExtraItemMon;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition.FixedExtraItemMonRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition.FixedExtraMon;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition.FixedExtraMonRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition.PerTimeMonActualResultService;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition.TypeCheckVacation;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition.TypeMonCheckItem;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition.checkremainnumber.CheckRemainNumberMon;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition.checkremainnumber.CheckRemainNumberMonRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycondition.MonthlyCorrectConditionRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycondition.TimeItemCheckMonthly;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.CheckedTimeDuration;
import nts.uk.ctx.at.record.dom.workrecord.export.WorkRecordExport;
import nts.uk.ctx.at.record.dom.workrecord.export.dto.EmpAffInfoExport;
import nts.uk.ctx.at.shared.dom.adapter.attendanceitemname.AttendanceItemNameAdapter;
import nts.uk.ctx.at.shared.dom.adapter.attendanceitemname.MonthlyAttendanceItemNameDto;
import nts.uk.ctx.at.shared.dom.alarmList.extractionResult.AlarmListCheckInfor;
import nts.uk.ctx.at.shared.dom.alarmList.extractionResult.AlarmListCheckType;
import nts.uk.ctx.at.shared.dom.alarmList.extractionResult.ExtractionAlarmPeriodDate;
import nts.uk.ctx.at.shared.dom.alarmList.extractionResult.ExtractionResultDetail;
import nts.uk.ctx.at.shared.dom.alarmList.extractionResult.ResultOfEachCondition;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.daynumber.AnnualLeaveRemainingDayNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.daynumber.AnnualLeaveUsedDayNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.RemainingMinutes;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.UsedMinutes;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.service.AttendanceItemConvertFactory;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.anyitem.AnyItemOfMonthlyRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.annualleave.AnnLeaRemNumEachMonth;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.annualleave.AnnLeaRemNumEachMonthRepository;
import nts.uk.shr.com.i18n.TextResource;
@Stateless
public class MonthlyExtractCheckServiceImpl implements MonthlyExtractCheckService{
	@Inject
	private WorkRecordExport workRecordEx;
	@Inject
	private ExtraResultMonthlyRepository extCondMonRepo;
	@Inject
	private MonthlyCorrectConditionRepository correctCondRepo;
	@Inject
	private FixedExtraMonRepository fixCondRepo;
	@Inject
	private FixedExtraItemMonRepository fixItemCondRepo;
	@Inject
	private AttendanceItemNameAdapter attendanceAdapter;
	@Inject
	private AnnLeaRemNumEachMonthRepository annLeaRepos;
	@Inject
	private CheckRemainNumberMonRepository remainNumberRepos;
	@Inject
	private TimeOfMonthlyRepository timeRepo;
	/** 月別実績の任意項目 */
	@Inject
	private AnyItemOfMonthlyRepository anyItemRepo;
	@Inject
	private RemainMergeRepository remainRepo;
	/** 勤怠項目変換 */
	@Inject
	private AttendanceItemConvertFactory attendanceItemConverterFact;
	@Inject
	private PerTimeMonActualResultService perTimeService;
	@Inject
	private ConvertCompareTypeToText convertComparaToText;
	
	@Override
	public void extractMonthlyAlarm(String cid, List<String> lstSid, YearMonthPeriod mPeriod, String fixConId,
			List<String> lstAnyConID, List<WorkPlaceHistImportAl> getWplByListSidAndPeriod,
			List<ResultOfEachCondition> lstResultCondition, List<AlarmListCheckInfor> lstCheckType) {
		DataCheck data = new DataCheck(cid, lstSid, mPeriod, fixConId, lstAnyConID);
		// 任意抽出条件をチェック
		data.lstAnyCondMon.stream().forEach(anyCond -> {
			lstCheckType.add(new AlarmListCheckInfor(String.valueOf(anyCond.getSortBy()), AlarmListCheckType.FreeCheck));
			this.extractAnyCondAlarm(lstSid, mPeriod, getWplByListSidAndPeriod, anyCond, lstResultCondition, data);
		});
		
	}
	private void extractAnyCondAlarm(List<String> lstSid, YearMonthPeriod mPeriod, List<WorkPlaceHistImportAl> getWplByListSidAndPeriod,
			ExtraResultMonthly anyCond,
			List<ResultOfEachCondition> lstResultCondition, DataCheck data) {
		
		List<ExtractionResultDetail> listValueExtractAlarm = new ArrayList<>(); 
		//
		
		//残数チェック
		Optional<CheckRemainNumberMon> optRemainCond = remainNumberRepos.getByEralCheckID(anyCond.getErrorAlarmCheckID());
		Optional<AttendanceItemCondition> optCheckConMonthly = anyCond.getCheckConMonthly();

		AttendanceItemCondition checkConMonthly = optCheckConMonthly.get();
		CheckRemainNumberMon remainCond = null;
		TypeCheckVacation checkVacation = TypeCheckVacation.ANNUAL_PAID_LEAVE;
		if(optRemainCond.isPresent()) {
			remainCond = optRemainCond.get();
			
			//CheckOperatorType checkOperatorType = remainCond.getCheckOperatorType(); //単一　OR　範囲
			checkVacation = remainCond.getCheckVacation(); //チェックする休暇
			if(checkVacation == TypeCheckVacation.ANNUAL_PAID_LEAVE) {
				//社員の月毎の確定済み年休を取得する
				data.lstAnnLeaveData = getAnnLeaRemainData(lstSid, mPeriod);
			}
		}
		GeneralDate enDate = GeneralDate.ymd(mPeriod.end().year(), mPeriod.end().month(), 30);
		GeneralDate startDate = GeneralDate.ymd(mPeriod.start().year(), mPeriod.end().month(), 1);
		for(String sid : lstSid) {
			String workplaceId = getWplByListSidAndPeriod.stream().filter(x -> x.getEmployeeId().equals(sid))
					.collect(Collectors.toList())
					.get(0).getLstWkpIdAndPeriod().stream().filter(y -> y.getDatePeriod().start().beforeOrEquals(enDate) && y.getDatePeriod().end().afterOrEquals(startDate))
					.collect(Collectors.toList()).get(0).getWorkplaceId();
			
			if(remainCond != null) {//残数チェック
				switch (checkVacation) {
				case ANNUAL_PAID_LEAVE:
					for(AnnualLeaveUsageDto annaData : data.lstAnnLeaveData) {
						boolean check = false;
						String alarmMessage = "";
						double remainingDays = annaData.getRemainingDays().v();
						if(optCheckConMonthly.isPresent()) {
							ErAlAttendanceItemCondition<?> erAlAtdItemCon = checkConMonthly.getGroup1()
									.getLstErAlAtdItemCon().get(0);
							boolean checkTarget = false;
							checkTarget = erAlAtdItemCon.checkTarget(item -> {
								return Arrays.asList(remainingDays);
							});
														
						}
					}
					break;
				}
			}
			Map<String, AttendanceItemCondition> condition = new HashMap<>();
			condition.put(anyCond.getErrorAlarmCheckID(), anyCond.getCheckConMonthly().get());
			if(anyCond.getTypeCheckItem().value > 3) { //チェック種類：時間、日数、回数、金額、複合条件
				Map<String, Map<YearMonth, Map<String,String>>> resultsData = new HashMap<>();
				Map<String, Map<YearMonth, Map<String, Integer>>> checkPerTimeMonActualResult = perTimeService.checkPerTimeMonActualResult(mPeriod, 
						lstSid,
						condition, 
						resultsData);
				for (YearMonth yearMonth : mPeriod.yearMonthsBetween()) {
					if(isError(checkPerTimeMonActualResult, anyCond.getErrorAlarmCheckID(), sid, yearMonth)) {
						if(anyCond.getTypeCheckItem() == TypeMonCheckItem.COMPOUND_CON) {
							extractCompoun(lstResultCondition, anyCond, sid, yearMonth, resultsData, data, workplaceId);
						} else {
							extractTimeDayTimesMoney(lstResultCondition, anyCond, sid, yearMonth, resultsData, data, workplaceId);
						}
					}
				}
				
			}
		}		
		
	}
	private void extractCompoun(List<ResultOfEachCondition> lstResultCondition, ExtraResultMonthly anyCond,
			String sid, YearMonth yearMonth, Map<String, Map<YearMonth, Map<String,String>>> resultsData, DataCheck data
			, String workplaceId) {
		String checkedValue = resultsData.get(sid).get(yearMonth).get(anyCond.getErrorAlarmCheckID());
		String alarmDescription2 = "";
		List<ErAlAttendanceItemCondition<?> > listErAlAtdItemCon = anyCond.getCheckConMonthly().get().getGroup1().getLstErAlAtdItemCon();
		
		//group 1 
		String alarmDescription1 = getDesGroup(anyCond, listErAlAtdItemCon, data);
		if(anyCond.getCheckConMonthly().get().isUseGroup2()) {
			List<ErAlAttendanceItemCondition<?> > listErAlAtdItemCon2 = anyCond.getCheckConMonthly().get().getGroup2().getLstErAlAtdItemCon();
			//group 2 
			alarmDescription2 = getDesGroup(anyCond, listErAlAtdItemCon2, data);
		}
		String alarmDescriptionValue= "";
		if(anyCond.getCheckConMonthly().get().getOperatorBetweenGroups() == LogicalOperator.AND) {//AND
			if(!alarmDescription2.equals("")) {
				alarmDescriptionValue = "("+alarmDescription1+") AND ("+alarmDescription2+")";
			}else {
				alarmDescriptionValue = alarmDescription1;
			}
		}else{
			if(!alarmDescription2.equals("")) {
				alarmDescriptionValue = "("+alarmDescription1+") OR ("+alarmDescription2+")";
			}else {
				alarmDescriptionValue = alarmDescription1;
			}
		}
		ExtractionAlarmPeriodDate periodDate = new ExtractionAlarmPeriodDate();
		periodDate.setStartDate(Optional.ofNullable(GeneralDate.ymd(yearMonth.year(), yearMonth.month(), 1)));
		periodDate.setEndDate(Optional.empty());
		ExtractionResultDetail detail = new ExtractionResultDetail(sid,
				periodDate,
				anyCond.getNameAlarmExtraCon().v(),
				alarmDescriptionValue,
				GeneralDateTime.now(),
				Optional.ofNullable(workplaceId),
				anyCond.getDisplayMessage().isPresent() ? Optional.ofNullable(anyCond.getDisplayMessage().get().v()) : Optional.empty(),
				Optional.ofNullable(checkedValue));
		List<ResultOfEachCondition> result = lstResultCondition.stream()
				.filter(x -> x.getCheckType() == AlarmListCheckType.FreeCheck && x.getNo().equals(String.valueOf(anyCond.getSortBy())))
				.collect(Collectors.toList());
		if(result.isEmpty()) {
			ResultOfEachCondition resultCon = new ResultOfEachCondition(AlarmListCheckType.FreeCheck,
					String.valueOf(anyCond.getSortBy()),
					new ArrayList<>());
			resultCon.getLstResultDetail().add(detail);
		} else {
			ResultOfEachCondition ex = result.get(0);
			lstResultCondition.remove(ex);
			ex.getLstResultDetail().add(detail);
			lstResultCondition.add(ex);
		}
	}
	private String getDesGroup(ExtraResultMonthly anyCond, List<ErAlAttendanceItemCondition<?>> listErAlAtdItemConG1, DataCheck data) {
		String alarmDescription = "";
		for(ErAlAttendanceItemCondition<?> erAlAtdItemCon : listErAlAtdItemConG1 ) {
			int compare =  erAlAtdItemCon.getCompareSingleValue() != null ? erAlAtdItemCon.getCompareSingleValue().getCompareOpertor().value
					: erAlAtdItemCon.getCompareRange().getCompareOperator().value;
		    String startValue ="";
			String endValue= "";
			String nameErrorAlarm = "";	
			List<MonthlyAttendanceItemNameDto> listAttdNameAddCompare = new ArrayList<>();
			//get name attdanceName 										
			if(erAlAtdItemCon.getType() == ErrorAlarmConditionType.FIXED_VALUE){
				 startValue = String.valueOf(erAlAtdItemCon.getCompareRange() != null ?
							new BigDecimal(((CheckedTimeDuration) erAlAtdItemCon.getCompareRange().getStartValue()).v())
							: erAlAtdItemCon.getCompareSingleValue().getValue());											
				//if type = time
				if(erAlAtdItemCon.getConditionAtr() == ConditionAtr.TIME_DURATION) {
					startValue =this.timeToString(erAlAtdItemCon.getCompareRange() != null ?
							Integer.valueOf(erAlAtdItemCon.getCompareRange().getStartValue().toString())
							: Integer.valueOf(erAlAtdItemCon.getCompareSingleValue().getValue().toString()));
				}	
			}else{
				//Integer singleAtdItem[] = {Integer.valueOf(erAlAtdItemCon.getCountableTarget().getAddSubAttendanceItems()) };												
				List<Integer> singleAtdItemList= erAlAtdItemCon.getCountableTarget().getAddSubAttendanceItems().getAdditionAttendanceItems();
				listAttdNameAddCompare = data.lstItemMond.stream().filter(ati -> singleAtdItemList
						.contains(ati.getAttendanceItemId())).collect(Collectors.toList());
				startValue = getNameErrorAlarm(listAttdNameAddCompare,0,nameErrorAlarm);										 
			}
		
			List<MonthlyAttendanceItemNameDto> listAttdNameAdd =  data.lstItemMond.stream()
					.filter(ati -> erAlAtdItemCon.getCountableTarget().getAddSubAttendanceItems().getAdditionAttendanceItems()
							.contains(ati.getAttendanceItemId())).collect(Collectors.toList());
			nameErrorAlarm = getNameErrorAlarm(listAttdNameAdd,0,nameErrorAlarm);//0 add atd item
			List<MonthlyAttendanceItemNameDto> listAttdNameSub =  data.lstItemMond.stream()
					.filter(ati -> erAlAtdItemCon.getCountableTarget().getAddSubAttendanceItems().getSubstractionAttendanceItems()
							.contains(ati.getAttendanceItemId())).collect(Collectors.toList());
			nameErrorAlarm = getNameErrorAlarm(listAttdNameSub,1,nameErrorAlarm);//1 sub atd item

			CompareOperatorText compareOperatorText = convertComparaToText.convertCompareType(compare);
			//0 : AND, 1 : OR
			String compareAndOr = "";
			if(anyCond.getCheckConMonthly().get().getGroup1().getConditionOperator() == LogicalOperator.AND) {
				compareAndOr = "AND";
			}else {
				compareAndOr = "OR";
			}
			if(!alarmDescription.equals("")) {
				alarmDescription += compareAndOr +" ";
			}
			if(compare<=5) {
					alarmDescription +=  nameErrorAlarm + " " + compareOperatorText.getCompareLeft()+" "+ startValue+" ";											
																										
			}else {
				endValue = String.valueOf(erAlAtdItemCon.getCompareRange().getEndValue());
				if(erAlAtdItemCon.getConditionAtr() == ConditionAtr.TIME_DURATION) {
					endValue =  this.timeToString(Integer.valueOf(erAlAtdItemCon.getCompareRange().getStartValue().toString())); 
				}
				if(compare>5 && compare<=7) {
					alarmDescription += startValue +" "+
							compareOperatorText.getCompareLeft()+ " "+
							nameErrorAlarm+ " "+
							compareOperatorText.getCompareright()+ " "+
							endValue+ " ";	
				}else {
					alarmDescription += nameErrorAlarm + " "+
							compareOperatorText.getCompareLeft()+ " "+
							startValue + ","+endValue+ " "+
							compareOperatorText.getCompareright()+ " "+
							nameErrorAlarm+ " " ;
				}
			}

					
		}
		return alarmDescription;
	}
	/**
	 * 時間、日数、回数、金額
	 * @param lstResultCondition
	 * @param anyCond
	 * @param sid
	 * @param yearMonth
	 * @param resultsData
	 * @param data
	 * @param workplaceId
	 */
	private void extractTimeDayTimesMoney(List<ResultOfEachCondition> lstResultCondition, ExtraResultMonthly anyCond,
			String sid, YearMonth yearMonth, Map<String, Map<YearMonth, Map<String,String>>> resultsData, DataCheck data
			, String workplaceId) {
		String checkedValue = resultsData.get(sid).get(yearMonth).get(anyCond.getErrorAlarmCheckID());
		ErAlAttendanceItemCondition<?> erAlAtdItemConAdapterDto = anyCond.getCheckConMonthly().get().getGroup1().getLstErAlAtdItemCon().get(0);
		int compare = erAlAtdItemConAdapterDto.getCompareSingleValue() != null ? erAlAtdItemConAdapterDto.getCompareSingleValue().getCompareOpertor().value
				: erAlAtdItemConAdapterDto.getCompareRange().getCompareOperator().value;
		
		BigDecimal startValue = erAlAtdItemConAdapterDto.getCompareRange() != null ?
				new BigDecimal(((CheckedTimeDuration) erAlAtdItemConAdapterDto.getCompareRange().getStartValue()).v())
				: null;
		BigDecimal endValue = erAlAtdItemConAdapterDto.getCompareRange() != null ? 
				new BigDecimal(((CheckedTimeDuration) erAlAtdItemConAdapterDto.getCompareRange().getEndValue()).v())
				: null;
		CompareOperatorText compareOperatorText = convertComparaToText.convertCompareType(compare);
		String nameErrorAlarm = "";
		//0 is monthly,1 is dayly
		List<MonthlyAttendanceItemNameDto> listAttdNameAdd =  data.lstItemMond.stream()
				.filter(c -> erAlAtdItemConAdapterDto.getCountableTarget().getAddSubAttendanceItems().getAdditionAttendanceItems()
						.contains(c.getAttendanceItemId())).collect(Collectors.toList());
		nameErrorAlarm = getNameErrorAlarm(listAttdNameAdd,0,nameErrorAlarm);//0 add atd item
		List<MonthlyAttendanceItemNameDto> listAttdNameSub =  data.lstItemMond.stream()
				.filter(c -> erAlAtdItemConAdapterDto.getCountableTarget().getAddSubAttendanceItems().getSubstractionAttendanceItems()
						.contains(c.getAttendanceItemId())).collect(Collectors.toList());
		nameErrorAlarm = getNameErrorAlarm(listAttdNameSub,1,nameErrorAlarm);//1 sub atd item
		String alarmDescription = "";
		switch (anyCond.getTypeCheckItem()) {
			case TIME:
				String startValueTime = this.timeToString(startValue.intValue());
				String endValueTime = "";
				if(compare<=5) {
					alarmDescription = TextResource.localize("KAL010_276",
							nameErrorAlarm,
							compareOperatorText.getCompareLeft(),
							startValueTime);
				}else {
					endValueTime = this.timeToString(endValue.intValue());
					if(compare>5 && compare<=7) {
						alarmDescription = TextResource.localize("KAL010_277",startValueTime,
								compareOperatorText.getCompareLeft(),
								nameErrorAlarm,
								compareOperatorText.getCompareright()+
								endValueTime
								);	
					}else {
						alarmDescription = TextResource.localize("KAL010_277",
								nameErrorAlarm,
								compareOperatorText.getCompareLeft(),
								startValueTime + ","+endValueTime,
								compareOperatorText.getCompareright()+
								nameErrorAlarm
								);
					}
				}
				checkedValue = this.timeToString(Double.valueOf(checkedValue).intValue());
				break;
			case DAYS:
				String startValueDays = String.valueOf(startValue.intValue());
				String endValueDays = "";
				if(compare<=5) {
					alarmDescription = TextResource.localize("KAL010_276",nameErrorAlarm,compareOperatorText.getCompareLeft(),startValueDays);
				}else {
					endValueDays = String.valueOf(endValue.intValue());
					if(compare>5 && compare<=7) {
						alarmDescription = TextResource.localize("KAL010_277",startValueDays,
								compareOperatorText.getCompareLeft(),
								nameErrorAlarm,
								compareOperatorText.getCompareright(),
								endValueDays
								);	
					}else {
						alarmDescription = TextResource.localize("KAL010_277",
								nameErrorAlarm,
								compareOperatorText.getCompareLeft(),
								startValueDays + "," + endValueDays,
								compareOperatorText.getCompareright(),
								nameErrorAlarm
								);
					}
				}
				break;
			case TIMES:
				String startValueTimes = String.valueOf(startValue.intValue());
				String endValueTimes = "";
				if(compare<=5) {
					alarmDescription = TextResource.localize("KAL010_276",nameErrorAlarm,compareOperatorText.getCompareLeft(),startValueTimes);
				}else {
					endValueTimes = String.valueOf(endValue.intValue());
					if(compare>5 && compare<=7) {
						alarmDescription = TextResource.localize("KAL010_277",startValueTimes,
								compareOperatorText.getCompareLeft(),
								nameErrorAlarm,
								compareOperatorText.getCompareright()+
								endValueTimes
								);	
					}else {
						alarmDescription = TextResource.localize("KAL010_277",
								nameErrorAlarm,
								compareOperatorText.getCompareLeft(),
								startValueTimes + "," + endValueTimes,
								compareOperatorText.getCompareright()+
								nameErrorAlarm
								);
					}
				}
				break;
			case AMOUNT_OF_MONEY:
				String startValueMoney = String.valueOf(startValue.intValue());
				String endValueMoney = "";
				if(compare<=5) {
					alarmDescription = TextResource.localize("KAL010_276",nameErrorAlarm,compareOperatorText.getCompareLeft(),startValueMoney);
				}else {
					endValueMoney = String.valueOf(endValue.intValue());
					if(compare>5 && compare<=7) {
						alarmDescription = TextResource.localize("KAL010_277",startValueMoney,
								compareOperatorText.getCompareLeft(),
								nameErrorAlarm,
								compareOperatorText.getCompareright()+
								endValueMoney
								);	
					}else {
						alarmDescription = TextResource.localize("KAL010_277",
								nameErrorAlarm,
								compareOperatorText.getCompareLeft(),
								startValueMoney + "," + endValueMoney,
								compareOperatorText.getCompareright()+
								nameErrorAlarm
								);
					}
				}
				break;
			default:
				break;
		}
		ExtractionAlarmPeriodDate periodDate = new ExtractionAlarmPeriodDate();
		periodDate.setStartDate(Optional.ofNullable(GeneralDate.ymd(yearMonth.year(), yearMonth.month(), 1)));
		periodDate.setEndDate(Optional.empty());
		ExtractionResultDetail detail = new ExtractionResultDetail(sid,
				periodDate,
				anyCond.getNameAlarmExtraCon().v(),
				alarmDescription,
				GeneralDateTime.now(),
				Optional.ofNullable(workplaceId),
				anyCond.getDisplayMessage().isPresent() ? Optional.ofNullable(anyCond.getDisplayMessage().get().v()) : Optional.empty(),
				Optional.ofNullable(checkedValue));
		List<ResultOfEachCondition> result = lstResultCondition.stream()
				.filter(x -> x.getCheckType() == AlarmListCheckType.FreeCheck && x.getNo().equals(String.valueOf(anyCond.getSortBy())))
				.collect(Collectors.toList());
		if(result.isEmpty()) {
			ResultOfEachCondition resultCon = new ResultOfEachCondition(AlarmListCheckType.FreeCheck,
					String.valueOf(anyCond.getSortBy()),
					new ArrayList<>());
			resultCon.getLstResultDetail().add(detail);
		} else {
			ResultOfEachCondition ex = result.get(0);
			lstResultCondition.remove(ex);
			ex.getLstResultDetail().add(detail);
			lstResultCondition.add(ex);
		}
	}
	private String getNameErrorAlarm(List<MonthlyAttendanceItemNameDto> attendanceItemNames ,int type,String nameErrorAlarm){
		if(!CollectionUtil.isEmpty(attendanceItemNames)) {
			for(int i=0; i< attendanceItemNames.size(); i++) {
				String beforeOperator = "";
				String operator = (i == (attendanceItemNames.size() - 1)) ? "" : type == 1 ? " - " : " + ";
				
				if (!"".equals(nameErrorAlarm) || type == 1) {
					beforeOperator = (i == 0) ? type == 1 ? " - " : " + " : "";
				}
                nameErrorAlarm += beforeOperator + attendanceItemNames.get(i).getAttendanceItemName() + operator;
			}
		}		
		return nameErrorAlarm;
	}
	private String timeToString(int value ){
		if(value%60<10){
			return  String.valueOf(value/60)+":0"+  String.valueOf(value%60);
		}
		return String.valueOf(value/60)+":"+  String.valueOf(value%60);
	}
	
	private String yearmonthToString(YearMonth yearMonth){
		if(yearMonth.month()<10){
			return  String.valueOf(yearMonth.year())+"/0"+  String.valueOf(yearMonth.month());
		}
		return String.valueOf(yearMonth.year())+"/"+  String.valueOf(yearMonth.month());
	}
	private boolean isError(Map<String, Map<YearMonth, Map<String, Integer>>> checkPerTimeMonActualResults,
			String eralId, String empId, YearMonth yearMonth) {
		if(checkPerTimeMonActualResults.containsKey(empId)){
			if(checkPerTimeMonActualResults.get(empId).containsKey(yearMonth)) {
				if(checkPerTimeMonActualResults.get(empId).get(yearMonth).containsKey(eralId)) {
					return checkPerTimeMonActualResults.get(empId).get(yearMonth).get(eralId) == 1;
				}
			}
		}
		return false;
	}
	/**
	 * 年休月別残数データ
	 * @param lstSid
	 * @param mPeriod
	 * @return
	 */
	private List<AnnualLeaveUsageDto> getAnnLeaRemainData(List<String> lstSid, YearMonthPeriod mPeriod) {
		List<AnnLeaRemNumEachMonth> findBySidsAndYearMonths = annLeaRepos.findBySidsAndYearMonths(lstSid, mPeriod.yearMonthsBetween());
		Map<YearMonth, AnnualLeaveUsageDto> results = new HashMap<>();
		Map<YearMonth, GeneralDate> saveDates = new HashMap<>();
		for (val data : findBySidsAndYearMonths){
			val yearMonth = data.getYearMonth();
			val annualLeave = data.getAnnualLeave();
			val usedNumber = annualLeave.getUsedNumber();
			val remNumber = annualLeave.getRemainingNumber();
			AnnualLeaveUsedDayNumber usedDays =
					new AnnualLeaveUsedDayNumber(usedNumber.getUsedDays().getUsedDays().v());
			UsedMinutes usedTime = null;
			if (usedNumber.getUsedTime().isPresent()){
				usedTime = new UsedMinutes(usedNumber.getUsedTime().get().getUsedTime().v());
			}
			AnnualLeaveRemainingDayNumber remainingDays =
					new AnnualLeaveRemainingDayNumber(remNumber.getTotalRemainingDays().v());
			RemainingMinutes remainingTime = null;
			if (remNumber.getTotalRemainingTime().isPresent()){
				remainingTime = new RemainingMinutes(remNumber.getTotalRemainingTime().get().v());
			}
			// 同じ年月が複数ある時、合算する
			if (results.containsKey(yearMonth)){
				val oldResult = results.get(yearMonth);
				val oldDate = saveDates.get(yearMonth);
				
				usedDays = new AnnualLeaveUsedDayNumber(usedDays.v() + oldResult.getUsedDays().v());
				if (oldResult.getUsedTime().isPresent()){
					if (usedTime == null){
						usedTime = new UsedMinutes(oldResult.getUsedTime().get().v());
					}
					else {
						usedTime = new UsedMinutes(usedTime.v() + oldResult.getUsedTime().get().v());
					}
				}
				
				// 年休残数に限り、締め期間．終了日の遅い方を保持する
				if (data.getClosurePeriod().end().before(oldDate)){
					remainingDays = new AnnualLeaveRemainingDayNumber(oldResult.getRemainingDays().v());
					remainingTime = null;
					if (oldResult.getRemainingTime().isPresent()){
						remainingTime = new RemainingMinutes(oldResult.getRemainingTime().get().v());
					}
				}
				else {
					saveDates.put(yearMonth, data.getClosurePeriod().end());
				}
			}
			results.put(yearMonth, new AnnualLeaveUsageDto(
					yearMonth,
					usedDays,
					Optional.ofNullable(usedTime),
					remainingDays,
					Optional.ofNullable(remainingTime)));
			saveDates.putIfAbsent(yearMonth, data.getClosurePeriod().end());
		}
		// 年休月別残数データリストを返す
		val resultList = results.values().stream().collect(Collectors.toList());
		resultList.sort((a, b) -> a.getYearMonth().compareTo(b.getYearMonth()));
		return resultList;
	}
	
	public class DataCheck {
		/**所属状況(年月)(AffiliationStatus)*/
		private EmpAffInfoExport empAffInfo;
		/**	月別実績の抽出条件	 */
		private List<ExtraResultMonthly> lstAnyCondMon;
		/**月別実績の勤怠項目チェック */
		private List<TimeItemCheckMonthly> lstTimeItem;
		/**	月別実績の固定抽出条件	 */
		private List<FixedExtraMon> lstFixCond; 
		/**月別実績の固定抽出項目 */
		private List<FixedExtraItemMon> lstFixItemCond;
		/**	月次の勤怠項目 */
		private List<MonthlyAttendanceItemNameDto> lstItemMond;
		/**年休月別残数データ		 */
		private List<AnnualLeaveUsageDto> lstAnnLeaveData;
		/**月別実績データ		 */
		private Map<String, List<MonthlyRecordValuesDto>> mapMonthData;
		
		public DataCheck(String cid, List<String> lstSid, YearMonthPeriod mPeriod, String fixConId,
			List<String> lstAnyConID) {
			//社員の指定期間中の所属期間を取得する（年月）
			this.empAffInfo = workRecordEx.getAffiliationPeriod(lstSid, mPeriod, GeneralDate.today());
			//ドメインモデル「月別実績の抽出条件」を取得
			this.lstAnyCondMon = extCondMonRepo.getAnyItemBySidAndUseAtr(lstAnyConID, true);
			//TODO ドメインモデル「月別実績の勤怠項目チェック」を取得
			
			//ドメインモデル「月別実績の固定抽出条件」を取得する
			this.lstFixCond = fixCondRepo.getFixedItem(fixConId, true);
			if(!this.lstFixCond.isEmpty()) {
				this.lstFixItemCond = fixItemCondRepo.getAllFixedExtraItemMon();
			}
			
			//社員ID（List）、期間を設定して月別実績を取得する
			
			//月次の勤怠項目を取得する
			this.lstItemMond = attendanceAdapter.getMonthlyAttendanceItem(2);
			//月別実績データを取得する
			this.mapMonthData = getMonthData(lstSid, mPeriod);
			
		}
		/**
		 * 月別実績データを取得する
		 * @param lstSid
		 * @param mPeriod
		 */
		private Map<String, List<MonthlyRecordValuesDto>> getMonthData(List<String> lstSid, YearMonthPeriod mPeriod) {
			Map<String, List<MonthlyRecordValuesDto>> results = new HashMap<>();
			// 検索キーを準備する
			val yearMonths = mPeriod.yearMonthsBetween();
			// 月別実績の所属情報を取得する
			val times = timeRepo.findBySidsAndYearMonths(lstSid, yearMonths);
			val remains = remainRepo.findBySidsAndYearMonths(lstSid, yearMonths);
			// 月別実績の任意項目を取得する
			List<Integer> itemIds = Arrays.asList(202,203,204,205,206);
			val anyItems = anyItemRepo.findBySidsAndMonths(lstSid, yearMonths);
			for (val attendanceTime : times){
				val employeeId = attendanceTime.getEmployeeId();
				
				val currentRemain = remains.stream().filter(c -> {
					return c.getMonthMergeKey().getEmployeeId().equals(employeeId)
							&& c.getMonthMergeKey().getClosureDate().equals(attendanceTime.getClosureDate())
							&& c.getMonthMergeKey().getClosureId().equals(attendanceTime.getClosureId())
							&& c.getMonthMergeKey().getYearMonth().equals(attendanceTime.getYearMonth());
				}).findFirst();
				val currentAnyItems = anyItems.stream().filter(c -> {
					return c.getEmployeeId().equals(employeeId)
							&& c.getClosureDate().equals(attendanceTime.getClosureDate())
							&& c.getClosureId().equals(attendanceTime.getClosureId())
							&& c.getYearMonth().equals(attendanceTime.getYearMonth());
				}).collect(Collectors.toList());
				
				// 勤怠項目値リストに変換する準備をする
				val monthlyConverter = attendanceItemConverterFact.createMonthlyConverter();
				
				attendanceTime.getAffiliation().ifPresent(af -> {
					monthlyConverter.withAffiliation(af);
				});
				attendanceTime.getAttendanceTime().ifPresent(at -> {
					monthlyConverter.withAttendanceTime(at);
				});
				monthlyConverter.withAnyItem(currentAnyItems);
				
				currentRemain.ifPresent(remain -> {
					monthlyConverter.withAnnLeave(remain.getAnnLeaRemNumEachMonth());
					monthlyConverter.withRsvLeave(remain.getRsvLeaRemNumEachMonth());
					monthlyConverter.withAbsenceLeave(remain.getAbsenceLeaveRemainData());
					monthlyConverter.withDayOff(remain.getMonthlyDayoffRemainData());
					monthlyConverter.withSpecialLeave(remain.getSpecialHolidayRemainData());
					monthlyConverter.withMonCareHd(remain.getMonCareHdRemain());
					monthlyConverter.withMonChildHd(remain.getMonChildHdRemain());
				});
				
				// 月別実績データ値リストに追加する
				results.putIfAbsent(employeeId, new ArrayList<>());
				results.get(employeeId).add(MonthlyRecordValuesDto.of(
						attendanceTime.getYearMonth(),
						attendanceTime.getClosureId(),
						attendanceTime.getClosureDate(),
						monthlyConverter.convert(itemIds)));
			}
			return results;
		}
		
		
	}

}
