package nts.uk.ctx.at.function.dom.alarm.alarmlist.multiplemonth;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.function.dom.adapter.ResponseImprovementAdapter;
import nts.uk.ctx.at.function.dom.adapter.actualmultiplemonth.ActualMultipleMonthAdapter;
import nts.uk.ctx.at.function.dom.adapter.actualmultiplemonth.MonthlyRecordValueImport;
import nts.uk.ctx.at.function.dom.adapter.eralworkrecorddto.ErAlAtdItemConAdapterDto;
import nts.uk.ctx.at.function.dom.adapter.multimonth.MultiMonthFucAdapter;
import nts.uk.ctx.at.function.dom.alarm.AlarmCategory;
import nts.uk.ctx.at.function.dom.alarm.alarmdata.ValueExtractAlarm;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.EmployeeSearchDto;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.monthly.CompareOperatorText;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.AlarmCheckConditionByCategory;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.AlarmCheckConditionByCategoryRepository;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.multimonth.MulMonAlarmCond;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.multimonth.doevent.MulMonCheckCondDomainEventDto;
import nts.uk.ctx.at.function.dom.attendanceitemname.AttendanceItemName;
import nts.uk.ctx.at.function.dom.attendanceitemname.service.AttendanceItemNameDomainService;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ItemValue;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.com.time.calendar.period.DatePeriod;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;

@Stateless
public class MultipleMonthAggregateProcessService {

	@Inject
	private AlarmCheckConditionByCategoryRepository alCheckConByCategoryRepo;

	@Inject
	private ResponseImprovementAdapter responseImprovementAdapter; 

	@Inject
	private ActualMultipleMonthAdapter actualMultipleMonthAdapter;

	@Inject
	private MultiMonthFucAdapter multiMonthFucAdapter;

	@Inject
	private AttendanceItemNameDomainService attdItemNameDomainService;

	public List<ValueExtractAlarm> multimonthAggregateProcess(String companyID, String checkConditionCode,
			DatePeriod period, List<EmployeeSearchDto> employees, List<Integer> listCategory) {

		List<String> employeeIds = employees.stream().map(e -> e.getId()).collect(Collectors.toList());

		List<ValueExtractAlarm> listValueExtractAlarm = new ArrayList<>();

		Optional<AlarmCheckConditionByCategory> alCheckConByCategory = alCheckConByCategoryRepo.find(companyID,
				AlarmCategory.MULTIPLE_MONTH.value, checkConditionCode);
		if (!alCheckConByCategory.isPresent()) {
			return Collections.emptyList();
		}
		// list alarmPartem
		MulMonAlarmCond mulMonAlarmCond = (MulMonAlarmCond) alCheckConByCategory.get().getExtractionCondition();
		List<MulMonCheckCondDomainEventDto> listExtra = multiMonthFucAdapter
				.getListMultiMonCondByListEralID(mulMonAlarmCond.getErrorAlarmCondIds());
		
		// 対象者を絞り込む
		DatePeriod endDatePerior = new DatePeriod(period.end(), period.start());
		GeneralDate tempStart = period.start();
		GeneralDate tempEnd = period.end();
		YearMonth startYearMonth = tempStart.yearMonth();
		YearMonth endYearMonth = tempEnd.yearMonth();
		YearMonthPeriod yearMonthPeriod = new YearMonthPeriod(startYearMonth, endYearMonth);
		List<String> listEmployeeID = responseImprovementAdapter.reduceTargetResponseImprovement(employeeIds,
				endDatePerior, alCheckConByCategory.get().getExtractTargetCondition());
		
		// 対象者の件数をチェック : 対象者 ≦ 0
		if (listEmployeeID.isEmpty()) {
			return Collections.emptyList();
		}
		List<EmployeeSearchDto> employeesDto = employees.stream().filter(c -> listEmployeeID.contains(c.getId()))
				.collect(Collectors.toList());

		/** Tạm thời dùng list 436 để test */
		// 月別実績を取得する
		Map<String, List<MonthlyRecordValueImport>> resultActuals = actualMultipleMonthAdapter
					.getActualMultipleMonth(listEmployeeID, yearMonthPeriod, listCategory);
		// tab1
		listValueExtractAlarm.addAll(this.extraResultMulMon(companyID, listExtra, period, employeesDto, resultActuals));
		
		return listValueExtractAlarm;
	}

	// tab1
	private List<ValueExtractAlarm> extraResultMulMon(String companyId, List<MulMonCheckCondDomainEventDto> listExtra,
			DatePeriod period, List<EmployeeSearchDto> employees,
			Map<String, List<MonthlyRecordValueImport>> resultActuals) {
		
		List<ValueExtractAlarm> listValueExtractAlarm = new ArrayList<>();
		//xu ly ngay
		GeneralDate tempStart = period.start();
		GeneralDate tempEnd = period.end();		
		String periodYearMonth = tempStart.toString("yyyy/MM")+"~"+tempEnd.toString("yyyy/MM");
		
		//lưu các tháng phù hợp của trường hợp number
		ArrayList<Integer> listMonthNumber = new ArrayList<>();
		for (MulMonCheckCondDomainEventDto extra : listExtra) {
			for (EmployeeSearchDto employee : employees) {
				boolean checkAddAlarm = false;
				listMonthNumber.clear();;
				int countContinus = 0;
				int countNumber = 0;
				float sumActual = 0;
				// trung binh
				float avg = 0.0f;
				TypeCheckWorkRecordMultipleMonthImport checkItem = EnumAdaptor.valueOf(extra.getTypeCheckItem(), TypeCheckWorkRecordMultipleMonthImport.class);
				List<MonthlyRecordValueImport> result = resultActuals.get(employee.getId());
				ErAlAtdItemConAdapterDto erAlAtdItemConAdapterDto = extra.getErAlAtdItem();
				int compare = erAlAtdItemConAdapterDto.getCompareOperator();
				CompareOperatorText compareOperatorText = convertCompareType(compare);
				BigDecimal startValue = erAlAtdItemConAdapterDto.getCompareStartValue();
				BigDecimal endValue = erAlAtdItemConAdapterDto.getCompareEndValue();
				String nameErrorAlarm = "";

				List<Integer> tmp = extra.getErAlAtdItem().getCountableAddAtdItems();
				List<Integer> tmp2 = extra.getErAlAtdItem().getCountableSubAtdItems();
				if(!CollectionUtil.isEmpty(tmp)) {
					List<AttendanceItemName> listAttdName =  attdItemNameDomainService.getNameOfAttendanceItem(tmp, 0);
					nameErrorAlarm = listAttdName.get(0).getAttendanceItemName();
				}else {
					if(!CollectionUtil.isEmpty(tmp2)){
					List<AttendanceItemName> listAttdName =  attdItemNameDomainService.getNameOfAttendanceItem(tmp2, 0);
					nameErrorAlarm = listAttdName.get(0).getAttendanceItemName();
					}
				}
				String alarmDescription = "";
				// Tinh tong thực tích va gia tri trung binh 0->5
				for (MonthlyRecordValueImport eachResult : result) {
					List<ItemValue> itemValues = eachResult.getItemValues();
					for (ItemValue itemValue :itemValues ) {
						sumActual += Integer.parseInt(itemValue.getValue());
						avg = sumActual / (result.size()*eachResult.getItemValues().size());
					}
					
				}
				
				//Xử lí trường hợp continusMonth 6-7-8
				if(extra.getTypeCheckItem() == TypeCheckWorkRecordMultipleMonthImport.CONTINUOUS_TIME.value 
						|| extra.getTypeCheckItem() == TypeCheckWorkRecordMultipleMonthImport.CONTINUOUS_TIMES.value
							|| extra.getTypeCheckItem() == TypeCheckWorkRecordMultipleMonthImport.CONTINUOUS_AMOUNT.value){
				for (MonthlyRecordValueImport eachResult : result) {
					List<ItemValue> itemValues = eachResult.getItemValues();
					float sumActualPermonth = 0;
					for (ItemValue itemValue : itemValues) {
						sumActualPermonth += Integer.parseInt(itemValue.getValue());
						if (checkPerMonth(extra, sumActualPermonth)) {
							countContinus++;
							if (countContinus >= extra.getContinuousMonths()) {
								// TODO
								countContinus = 0;
							}
						} else {
							countContinus = 0;
						}
						}
					}
				}
				
				//Xử lí trường hợp numberMonth 9-10-11
				if(extra.getTypeCheckItem() == TypeCheckWorkRecordMultipleMonthImport.NUMBER_TIME.value 
						|| extra.getTypeCheckItem() == TypeCheckWorkRecordMultipleMonthImport.NUMBER_TIMES.value
						|| extra.getTypeCheckItem() == TypeCheckWorkRecordMultipleMonthImport.NUMBER_AMOUNT.value){
					for (MonthlyRecordValueImport eachResult : result) {
						float sumActualPermonth = 0;
						List<ItemValue> itemValues = eachResult.getItemValues();
						for (ItemValue itemValue : itemValues) {
							sumActualPermonth += Integer.parseInt(itemValue.getValue());
						}
						if (checkPerMonth(extra, sumActualPermonth)) {
							listMonthNumber.add(eachResult.getYearMonth().month());
							countNumber++;
						} 
					}
				}
				
					switch (checkItem) {
					// TIME(0,"時間")
					case TIME:
					case TIMES:
					case AMOUNT:
						if (checkPerMonth(extra, sumActual)) {
							
							checkAddAlarm = true;
							String startValueTime = String.valueOf(startValue.intValue()/60)+":"+String.valueOf(startValue.intValue()%60);
							String endValueTime = "";
							if(compare<=5) {
								alarmDescription = TextResource.localize("KAL010_254",periodYearMonth,nameErrorAlarm,compareOperatorText.getCompareLeft(),startValueTime);
							}else {
								endValueTime = String.valueOf(endValue.intValue()/60)+":"+String.valueOf(endValue.intValue()%60);
								if(compare>5 && compare<=7) {
									alarmDescription = TextResource.localize("KAL010_255",periodYearMonth,startValueTime,
											compareOperatorText.getCompareLeft(),
											nameErrorAlarm,
											compareOperatorText.getCompareright(),
											endValueTime
											);	
								}else {
									alarmDescription = TextResource.localize("KAL010_256",periodYearMonth,
											startValueTime,
											compareOperatorText.getCompareLeft(),
											nameErrorAlarm,
											nameErrorAlarm,
											compareOperatorText.getCompareright(),
											endValueTime
											);
								}
							}
						}
						break;
					case AVERAGE_TIME:
					case AVERAGE_TIMES:
					case AVERAGE_AMOUNT:
						if (checkPerMonth(extra, avg)) {
							checkAddAlarm = true;
							String startValueTime = String.valueOf(startValue.intValue()/60)+":"+String.valueOf(startValue.intValue()%60);
							String endValueTime = "";
							if(compare<=5) {
								alarmDescription = TextResource.localize("KAL010_264",periodYearMonth,nameErrorAlarm,compareOperatorText.getCompareLeft(),startValueTime);
							}else {
								endValueTime = String.valueOf(endValue.intValue()/60)+":"+String.valueOf(endValue.intValue()%60);
								if(compare>5 && compare<=7) {
									alarmDescription = TextResource.localize("KAL010_265",periodYearMonth,startValueTime,
											compareOperatorText.getCompareLeft(),
											nameErrorAlarm,
											compareOperatorText.getCompareright(),
											endValueTime
											);	
								}else {
									alarmDescription = TextResource.localize("KAL010_266",periodYearMonth,
											startValueTime,
											compareOperatorText.getCompareLeft(),
											nameErrorAlarm,
											nameErrorAlarm,
											compareOperatorText.getCompareright(),
											endValueTime
											);
								}
							}
						}
						break;
					case CONTINUOUS_TIME:
					case CONTINUOUS_TIMES:
					case CONTINUOUS_AMOUNT:
						if (checkMulMonth(extra, countContinus)) {
							checkAddAlarm = true;
							String startValueTime = String.valueOf(startValue.intValue()/60)+":"+String.valueOf(startValue.intValue()%60);
							alarmDescription = TextResource.localize("KAL010_260",periodYearMonth,nameErrorAlarm,compareOperatorText.getCompareLeft(),startValueTime,String.valueOf(extra.getContinuousMonths()));
						}
						break;
					// 9-10-11
					default:
						if (checkMulMonth(extra, countNumber) && listMonthNumber.size()>0) {
							checkAddAlarm = true;
							String startValueTime = String.valueOf(startValue.intValue()/60)+":"+String.valueOf(startValue.intValue()%60);
							alarmDescription = TextResource.localize("KAL010_270",periodYearMonth,nameErrorAlarm,
									convertCompareType(extra.getCompareOperator()).getCompareLeft(),
									startValueTime,listMonthNumber.toString(),
									String.valueOf(extra.getTimes()));
					
						}
						break;
				}
				if (checkAddAlarm) {
					ValueExtractAlarm resultMonthlyValue = new ValueExtractAlarm(employee.getWorkplaceId(),
							employee.getId(), tempStart.toString("yyyy/MM"), TextResource.localize("KAL010_250"),
							checkItem.nameId, alarmDescription, extra.getDisplayMessage());
					listValueExtractAlarm.add(resultMonthlyValue);
				}
			}
			
		}
		return listValueExtractAlarm;
	}
	private boolean checkMulMonth(MulMonCheckCondDomainEventDto extra, int count) {
		boolean check = false;
		if(compareSingle(extra.getTimes(), count, extra.getCompareOperator())){
			check = true;
		}
		return check;
	}

	private boolean checkPerMonth(MulMonCheckCondDomainEventDto extra, float sumActual) {
		boolean check = false;
		BigDecimal sumActualBD = new BigDecimal(sumActual);
		if (extra.getCompareOperator() <= 5) {
			if (compareSingle(extra.getErAlAtdItem().getCompareStartValue(), sumActualBD, extra.getErAlAtdItem().getCompareOperator())) {
				check = true;
			}
		}else {
			if (CompareDouble(extra.getErAlAtdItem().getCompareStartValue(), extra.getErAlAtdItem().getCompareEndValue(), sumActualBD, extra.getErAlAtdItem().getCompareOperator())) {
				check = true;
			}
		}
		return check;
	}
	private boolean compareSingle(double valueAgreement, double value, int compareType) {
		boolean check = false;
		switch (compareType) {
		case 0:/* 等しい（＝） */
			if (value == valueAgreement)
				check = true;
			break;
		case 1:/* 等しくない（≠） */
			if (value != valueAgreement)
				check = true;
			break;
		case 2:/* より大きい（＞） */
			if (value > valueAgreement)
				check = true;
			break;
		case 3:/* 以上（≧） */
			if (value >= valueAgreement)
				check = true;
			break;
		case 4:/* より小さい（＜） */
			if (value < valueAgreement)
				check = true;
			break;
		default:/* 以下（≦） */
			if (value <= valueAgreement)
				check = true;
			break;
		}

		return check;
	}

	private boolean compareSingle(BigDecimal valueAgreement, BigDecimal value, int compareType) {
		boolean check = false;
		switch (compareType) {
		case 0:/* 等しい（＝） */
			if (valueAgreement == value)
				check = true;
			break;
		case 1:/* 等しくない（≠） */
			if (value != valueAgreement)
				check = true;
			break;
		case 2:/* より大きい（＞） */
			if (value.compareTo(valueAgreement) == 1)
				check = true;
			break;
		case 3:/* 以上（≧） */
			if (value.compareTo(valueAgreement) >= 0)
				check = true;
			break;
		case 4:/* より小さい（＜） */
			if (value.compareTo(valueAgreement) == -1)
				check = true;
			break;
		default:/* 以下（≦） */
			if (value.compareTo(valueAgreement) <= 0)
				check = true;
			break;
		}

		return check;
	}

	
	private boolean CompareDouble(BigDecimal value, BigDecimal valueAgreementStart, BigDecimal valueAgreementEnd, int compare) {
		boolean check = false;
		switch (compare) {
		/* 範囲の間（境界値を含まない）（＜＞） */
		case 6:
			if (value.compareTo(valueAgreementStart)>0 && value.compareTo(valueAgreementEnd)<0 ) {
				check = true;
			}
			break;
			/* 範囲の間（境界値を含む）（≦≧） */
		case 7:
			if (value.compareTo(valueAgreementStart)>=0 && value.compareTo(valueAgreementEnd)<=0 ) {
				check = true;
			}
			break;
			/* 範囲の外（境界値を含まない）（＞＜） */
		case 8:
			if (value.compareTo(valueAgreementStart)<0 || value.compareTo(valueAgreementEnd)>0 ) {
				check = true;
			}
			break;
			/* 範囲の外（境界値を含む）（≧≦） */
		default:
			if (value.compareTo(valueAgreementStart)<=0 || value.compareTo(valueAgreementEnd)>=0 ) {
				check = true;
			}
			break;
		}
		return check;
	}

	private CompareOperatorText convertCompareType(int compareOperator) {
		CompareOperatorText compare = new CompareOperatorText();
		switch (compareOperator) {
		case 0:/* 等しい（＝） */
			compare.setCompareLeft("＝");
			compare.setCompareright("");
			break;
		case 1:/* 等しくない（≠） */
			compare.setCompareLeft("≠");
			compare.setCompareright("");
			break;
		case 2:/* より大きい（＞） */
			compare.setCompareLeft("＞");
			compare.setCompareright("");
			break;
		case 3:/* 以上（≧） */
			compare.setCompareLeft("≧");
			compare.setCompareright("");
			break;
		case 4:/* より小さい（＜） */
			compare.setCompareLeft("＜");
			compare.setCompareright("");
			break;
		case 5:/* 以下（≦） */
			compare.setCompareLeft("≦");
			compare.setCompareright("");
			break;
		case 6:/* 範囲の間（境界値を含まない）（＜＞） */
			compare.setCompareLeft("＜");
			compare.setCompareright("＜");
			break;
		case 7:/* 範囲の間（境界値を含む）（≦≧） */
			compare.setCompareLeft("≦");
			compare.setCompareright("≦");
			break;
		case 8:/* 範囲の外（境界値を含まない）（＞＜） */
			compare.setCompareLeft("＞");
			compare.setCompareright("＞");
			break;

		default:/* 範囲の外（境界値を含む）（≧≦） */
			compare.setCompareLeft("≧");
			compare.setCompareright("≧");
			break;
		}

		return compare;
	}

}
