package nts.uk.ctx.at.record.dom.workrecord.erroralarm.weekly.algorithm;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.ErAlCategory;
import nts.uk.ctx.at.shared.dom.alarmList.AlarmCategory;
import nts.uk.ctx.at.shared.dom.alarmList.persistenceextractresult.*;
import nts.uk.ctx.at.shared.dom.monthlyattditem.MonthlyAttendanceItemAtr;

import org.apache.logging.log4j.util.Strings;

import nts.arc.task.parallel.ManagedParallelWithContext;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.attendanceitem.AddSubAttendanceItems;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.attendanceitem.CheckedCondition;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.attendanceitem.CheckedTarget;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.attendanceitem.CompareRange;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.attendanceitem.CompareSingleValue;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.attendanceitem.CountableTarget;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums.CompareOperatorText;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums.ConvertCompareTypeToText;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.mastercheck.algorithm.WorkPlaceHistImportAl;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.CheckedTimeDuration;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.HolidayTime;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.weekly.ExtractionCondWeekly;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.weekly.ExtractionCondWeeklyRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.weekly.WeeklyCheckItemType;
import nts.uk.ctx.at.record.dom.workrecord.errorsetting.ContinuousCount;
import nts.uk.ctx.at.record.dom.workrecord.errorsetting.algorithm.CalCountForConsecutivePeriodChecking;
import nts.uk.ctx.at.record.dom.workrecord.errorsetting.algorithm.CalCountForConsecutivePeriodOutput;
import nts.uk.ctx.at.shared.dom.alarmList.extractionResult.AlarmListCheckInfor;
import nts.uk.ctx.at.shared.dom.alarmList.extractionResult.AlarmListCheckType;
import nts.uk.ctx.at.shared.dom.alarmList.extractionResult.ResultOfEachCondition;
import nts.uk.ctx.at.shared.dom.scherec.attendanceitem.converter.service.AttendanceItemConvertFactory;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.adapter.attendanceitemname.AttItemName;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.weekly.AttendanceTimeOfWeekly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattendanceitem.service.CompanyMonthlyItemService;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class WeeklyCheckServiceImpl implements WeeklyCheckService {
	@Inject
	private ManagedParallelWithContext parallelManager;
	@Inject
	private WeeklyAttendanceItemService weeklyAttendanceItemService;
	@Inject
	private GetWeeklyPerformanceService getWeeklyPerformanceService;
	@Inject
	private ExtractionCondWeeklyRepository extractionCondWeeklyRepository;
	@Inject
	private AttendanceItemConvertFactory attendanceItemConvertFactory;
	@Inject
	private CalCountForConsecutivePeriodChecking calCountForConsecutivePeriodChecking;
	@Inject
	private ConvertCompareTypeToText convertComparaToText;
	@Inject
	private CompanyMonthlyItemService atName;
	
	@Override
	public void extractWeeklyCheck(String cid, List<String> lstSid, DatePeriod period,
			List<WorkPlaceHistImportAl> wplByListSidAndPeriods, String listOptionalItem,
			List<ResultOfEachCondition> lstResultCondition, List<AlarmListCheckInfor> lstCheckType,
			Consumer<Integer> counter, Supplier<Boolean> shouldStop, List<AlarmEmployeeList> alarmEmployeeList,
			List<AlarmExtractionCondition> alarmExtractConditions, String alarmCheckConditionCode) {
		String contractCode = AppContexts.user().contractCode();
		
		// ????????????????????????????????????
		Map<Integer, String> attendanceItemMap = weeklyAttendanceItemService.getAttendanceItem(cid);
		
		// ???????????????????????????
		// QA#116337
		List<AttendanceTimeOfWeekly> attendanceTimeOfWeeklys = getWeeklyPerformanceService.getValues(lstSid, period);

		parallelManager.forEach(CollectionUtil.partitionBySize(lstSid, 100), emps -> {
			synchronized (this) {
				if (shouldStop.get()) {
					return;
				}
			}
			
			// Input???List?????????ID???????????????
			for (String sid: emps) {
                List<AlarmExtractInfoResult> lstExtractInfoResult = new ArrayList<>();
                
				// ????????????List?????????????????????????????????????????????????????????
//				for (ExtractionCondWeekly weeklyCond: weeklyConds) {
				for (String code : Arrays.asList(listOptionalItem)) {
					ExtractionCondWeekly weeklyCond = extractionCondWeeklyRepository.getAnyCond(cid, ErAlCategory.WEEKLY.value, code);

					// ????????????????????????0
					int count = 0;

					val lstExtractCond = alarmExtractConditions.stream()
							.filter(x -> x.getAlarmListCheckType() == AlarmListCheckType.FreeCheck && x.getAlarmCheckConditionCode().equals(code))
							.findAny();
					if (!lstExtractCond.isPresent()) {
						alarmExtractConditions.add(new AlarmExtractionCondition(
								"",
								new AlarmCheckConditionCode(alarmCheckConditionCode),
								AlarmCategory.WEEKLY,
								AlarmListCheckType.FreeCheck
						));
					}
					
					// Input??????????????????????????????????????????
					for (YearMonth ym: period.yearMonthsBetween()) {
						// ????????????????????????????????????????????????????????????????????????
						// ??????????????????????????? ???Input. ?????????????????????????????????????????????????????????????????????Input. ??????????????????????????????????????????????????????????????????QA#115666
						GeneralDate startCompare = period.start().after(ym.firstGeneralDate()) ? period.start() : ym.firstGeneralDate();
						// ?????????????????????????????????Input. ?????????????????????????????????????????????????????????????????????Input. ??????????????????????????????????????????????????????????????????QA#115666
						GeneralDate endCompare = period.end().before(ym.lastGeneralDate()) ? period.end() : ym.lastGeneralDate();
						
						List<AttendanceTimeOfWeekly> attendanceTimeOfWeeklyYmsByEmp = attendanceTimeOfWeeklys.stream().filter(x -> x.getEmployeeId().equals(sid)).collect(Collectors.toList());
						List<AttendanceTimeOfWeekly> attendanceTimeOfWeeklyYms = attendanceTimeOfWeeklyYmsByEmp.stream()
								.filter(x -> x.getYearMonth().equals(ym) 
										&& x.getPeriod().start().afterOrEquals(startCompare) && x.getPeriod().start().beforeOrEquals(endCompare))
								.collect(Collectors.toList());
						
						Collections.sort(attendanceTimeOfWeeklyYms, new Comparator<AttendanceTimeOfWeekly>() {
		                	public int compare(AttendanceTimeOfWeekly o1, AttendanceTimeOfWeekly o2) {
		                		if (o1.getPeriod() == null || o2.getPeriod() == null || o1.getPeriod().start() == null || o2.getPeriod().start() == null)
		                			return 0;
		                		return o1.getPeriod().start().compareTo(o2.getPeriod().start());
						    }
		            	});
						
						// ??????????????????List???????????????????????????????????????????????????
						for (AttendanceTimeOfWeekly attWeekly : attendanceTimeOfWeeklyYms) {
							// ???????????????????????????????????????????????????
							ExtractResultDetailAndCount extractDetail = createAlarmExtraction(
									attWeekly, weeklyCond, count, attendanceItemMap, cid, sid, ym,
									attendanceTimeOfWeeklyYmsByEmp, wplByListSidAndPeriods);
							count = extractDetail.count;
							if (extractDetail.detail == null) {
								continue;
							}

							List<ExtractResultDetail> listDetail = new ArrayList<>(Arrays.asList(extractDetail.detail));
							AlarmExtractInfoResult newResult = new AlarmExtractInfoResult(
									"",
									new AlarmCheckConditionCode(alarmCheckConditionCode),
									AlarmCategory.WEEKLY,
									AlarmListCheckType.FreeCheck,
									listDetail
							);
							Optional<AlarmExtractInfoResult> existentResult = lstExtractInfoResult.stream()
									.filter(i -> i.equals(newResult))
									.findFirst();
							if (existentResult.isPresent()) {
								existentResult.get().getExtractionResultDetails().add(extractDetail.detail);
							} else {
								lstExtractInfoResult.add(newResult);
							}

                            List<AlarmExtractionCondition> lstExtractCondition = alarmExtractConditions.stream()
                                    .filter(x -> x.getAlarmListCheckType() == AlarmListCheckType.FreeCheck && x.getAlarmCheckConditionCode().equals(code))
                                    .collect(Collectors.toList());
                            if (lstExtractCondition.isEmpty()) {
                                alarmExtractConditions.add(new AlarmExtractionCondition(
										"",
                                        new AlarmCheckConditionCode(alarmCheckConditionCode),
                                        AlarmCategory.WEEKLY,
                                        AlarmListCheckType.FreeCheck
                                ));
                            }
						}
					}
					
					// ???????????????????????????????????????
				}
				if (!lstExtractInfoResult.isEmpty()) {
					if (alarmEmployeeList.stream().anyMatch(i -> i.getEmployeeID().equals(sid))) {
						for (AlarmEmployeeList i : alarmEmployeeList) {
							if (i.getEmployeeID().equals(sid)) {
								List<AlarmExtractInfoResult> temp = new ArrayList<>(i.getAlarmExtractInfoResults());
								temp.addAll(lstExtractInfoResult);
								i.setAlarmExtractInfoResults(temp);
								break;
							}
						}
					} else {
						alarmEmployeeList.add(new AlarmEmployeeList(lstExtractInfoResult, sid));
					}
				}
			}
			
			synchronized (this) {
				counter.accept(emps.size());
			}
		});
	}

	/*
	 * Create alarm extraction condition
	 */
	private ExtractResultDetailAndCount createAlarmExtraction(
			AttendanceTimeOfWeekly attWeekly,
			ExtractionCondWeekly weeklyCond,
			int count,
			Map<Integer, String> attendanceItemMap,
			String cid,
			String sid,
			YearMonth ym,
			List<AttendanceTimeOfWeekly> attWeeklyBySid,
			List<WorkPlaceHistImportAl> wplByListSidAndPeriods) {

// ???????????????????????????????????????????????????????????????????????????????????????????????????
//		boolean check = false;
//		ContinuousOutput continuousOutput = new ContinuousOutput();
//
//		int currentIndex = attWeeklyBySid.indexOf(attWeekly);
//
//		// ???????????????????????????????????? ErAlAttendanceItemCondition
//		// QA#115685
//		WeeklyRecordToAttendanceItemConverter weeklyConvert = attendanceItemConvertFactory.createWeeklyConverter();
//		weeklyConvert.withAttendanceTime(attWeekly);
//		List<ItemValue> itemValues =  weeklyConvert.convert(attendanceItemMap.keySet());
//
//		@SuppressWarnings("rawtypes")
//		WeeklyAttendanceItemCondition cond = convertToErAlAttendanceItem(cid, weeklyCond);
//
//		// Input?????????????????????????????????????????????????????????????????????????????????
//		WeeklyCheckItemType checkItemType = weeklyCond.getCheckItemType();
//		switch (checkItemType) {
//		case TIME:
//		case TIMES:
//		case DAY_NUMBER:
//			// ?????????????????????????????????
//			check = checkAttendanceItem(cond, item -> {
//				if (item.isEmpty()) {
//					return new ArrayList<>();
//				}
//
//				return itemValues.stream().filter(x -> item.contains(x.getItemId())).map(iv -> getValue(iv))
//						.collect(Collectors.toList());
//			});
//			break;
//		case CONTINUOUS_TIME:
//		case CONTINUOUS_TIMES:
//		case CONTINUOUS_DAY:
//			// ???????????????????????????????????????
//			continuousOutput = checkPerformanceOfConsecutiveItem(
//					attWeekly, weeklyCond, cond, itemValues, count, currentIndex, attWeeklyBySid.size(), attWeeklyBySid);
//			count = continuousOutput.count;
//			check = continuousOutput.check;
//			break;
//		default:
//			break;
//		}
//
//		Double weeklyActualAttendanceTimeValue = calAttendanceItem(cond, item -> {
//			if (item.isEmpty()) {
//				return new ArrayList<>();
//			}
//			return itemValues.stream().filter(x -> item.contains(x.getItemId())).map(iv -> getValue(iv))
//					.collect(Collectors.toList());
//		});
//
//		//
//		ExtractionAlarmPeriodDate extractionAlarmPeriodDate = null;
//		Optional<String> comment = Optional.empty();
//		String alarmContent = Strings.EMPTY;
//
//		// ??????????????????????????????????????????????????????????????????#KAL010_1314
//		// {0}?????????Input??????????????????????????????????????????????????? QA#115666
//		String weeklyActualAttendanceTime = weeklyActualAttendanceTimeValue.toString();
//		if (checkItemType == WeeklyCheckItemType.TIME || checkItemType == WeeklyCheckItemType.CONTINUOUS_TIME) {
//			weeklyActualAttendanceTime = formatTime(weeklyActualAttendanceTimeValue.intValue());
//		} else if (checkItemType == WeeklyCheckItemType.TIMES || checkItemType == WeeklyCheckItemType.CONTINUOUS_TIMES) {
//			weeklyActualAttendanceTime = String.valueOf(weeklyActualAttendanceTimeValue.intValue());
//		}
//
//		String checkTargetValue = TextResource.localize("KAL010_1314", weeklyActualAttendanceTime);
//
//		// ????????????????????????????????????True (QA#117728)
//		if (check) {
//			// ?????????????????????????????????
//
//			// ?????????ID?????????Input???List?????????ID??????????????????????????????????????????
//			// Input???List?????????????????????
//			//   ???????????????????????????????????????????????????????????????????????????????????????????????????????????? ???118576
//			String wpkId = "";
//			Optional<WorkPlaceHistImportAl> optWorkPlaceHistImportAl = wplByListSidAndPeriods.stream()
//					.filter(x -> x.getEmployeeId().equals(sid)).findFirst();
//			if(optWorkPlaceHistImportAl.isPresent()) {
//				Optional<WorkPlaceIdAndPeriodImportAl> optWorkPlaceIdAndPeriodImportAl = optWorkPlaceHistImportAl.get().getLstWkpIdAndPeriod().stream()
//						.filter(x -> x.getDatePeriod().start().beforeOrEquals(attWeekly.getPeriod().start())
//								&& x.getDatePeriod().end().afterOrEquals(attWeekly.getPeriod().start())).findFirst();
//				if(optWorkPlaceIdAndPeriodImportAl.isPresent()) {
//					wpkId = optWorkPlaceIdAndPeriodImportAl.get().getWorkplaceId();
//				}
//			}
//
//			// ??????????????????????????????Input???????????????????????????????????????????????????
//			GeneralDate startDate = attWeekly.getPeriod().start();
//			if (weeklyCond.isContinuos()) {
//				int index = 0; //(currentIndex + 1) == sizeWeeklyActualAttendanceTime && !continuousOutput.continuousCountOpt.isPresent()  ? currentIndex : currentIndex - 1;
//				if (!continuousOutput.continuousCountOpt.isPresent()) {
//					index = currentIndex - count + 1;
//				} else {
//					index = currentIndex - continuousOutput.continuousCountOpt.get().getConsecutiveYears();
//				}
//				startDate = attWeeklyBySid.get(index).getPeriod().start();
//			}
//
//			extractionAlarmPeriodDate = new ExtractionAlarmPeriodDate(
//					Optional.of(startDate), Optional.empty());
//			// ?????????????????????Input????????????????????????????????????????????????????????????
//			if (weeklyCond.getErrorAlarmMessage() != null && weeklyCond.getErrorAlarmMessage().isPresent()) {
//				comment = Optional.ofNullable(weeklyCond.getErrorAlarmMessage().get().v());
//			}
//			// ??????????????????
//			String param0 = getCompareOperatorText(cid, weeklyCond.getCheckConditions(), weeklyCond.getCheckItemType(), weeklyCond.getCheckedTarget());
//
//			// 	??????????????????????????????????????????????????????????????????#KAL010_1308
//			String param1 = TextResource.localize("KAL010_1308");
//			// ??????????????????????????????????????????????????????????????????Input???????????????????????????????????????????????????
//			String param2 = weeklyActualAttendanceTime;
//			if (weeklyCond.isContinuos()) {
//				String continuousPeriodValue = String.valueOf(weeklyCond.getContinuousPeriod().get().v());
//				param0 += TextResource.localize("KAL010_1312", continuousPeriodValue);
//				// ??????????????????????????????????????????????????????#KAL010_1309
//				param1 = TextResource.localize("KAL010_1309");
//
//				// #117728
//				if (continuousOutput.continuousCountOpt.isPresent()) {
//					// ?????????????????????????????????+???#KAL010_1311
//					param2 = continuousOutput.continuousCountOpt.get().getConsecutiveYears() + TextResource.localize("KAL010_1311");
//
//					// ??????????????????????????????????????????????????????#KAL010_1313 {0}???????????????????????????????????????
//					checkTargetValue = TextResource.localize("KAL010_1313", String.valueOf(continuousOutput.continuousCountOpt.get().getConsecutiveYears()));
//				} else {
//					// ?????????????????????
//					param2 = String.valueOf(count) + TextResource.localize("KAL010_1311");
//
//					// ??????????????????????????????????????????????????????#KAL010_1313 {0}??????????????????????????????
//					checkTargetValue = TextResource.localize("KAL010_1313", String.valueOf(count));
//				}
//			}
//
//			alarmContent = TextResource.localize("KAL010_1310", param0, param1, param2);
//
//			// ???????????????????????????????????????????????????????????????????????????
//			ExtractResultDetail detail = new ExtractResultDetail(
//					extractionAlarmPeriodDate,
//					weeklyCond.getName().v(),
//					alarmContent,
//					GeneralDateTime.now(),
//					Optional.ofNullable(wpkId),
//					comment,
//					Optional.ofNullable(checkTargetValue));
//
//			// ????????????????????????
//			//count = 0;
//
//			return new ExtractResultDetailAndCount(detail, count);
//		}
		
		return new ExtractResultDetailAndCount(null, count);
	}

// ???????????????????????????????????????????????????????????????????????????????????????????????????
//	/**
//	 * Convert ExtractionCondScheduleWeekly to ErAlAttendanceItemCondition
//	 * @param cid company id
//	 * @param weeklyCond ExtractionCondScheduleWeekly
//	 * @return ErAlAttendanceItemCondition
//	 */
//	@SuppressWarnings({ "rawtypes", "unchecked" })
//	private WeeklyAttendanceItemCondition convertToErAlAttendanceItem(String cid, ExtractionCondWeekly weeklyCond) {
//		WeeklyAttendanceItemCondition cond = new WeeklyAttendanceItemCondition<>();
//
//		if(weeklyCond.getCheckedTarget().isPresent()) {
//			CountableTarget countableTarget = (CountableTarget)weeklyCond.getCheckedTarget().get();
//			cond.setCountableTarget(
//					countableTarget.getAddSubAttendanceItems().getAdditionAttendanceItems(),
//					countableTarget.getAddSubAttendanceItems().getSubstractionAttendanceItems());
//		}
//
//		if (weeklyCond.getCheckConditions() != null) {
//			if (weeklyCond.getCheckConditions() instanceof CompareRange) {
//				CompareRange compareRange = (CompareRange)weeklyCond.getCheckConditions();
//				cond.setCompareRange(compareRange);
//			} else {
//				CompareSingleValue<Double> compareRangeSingle = (CompareSingleValue)weeklyCond.getCheckConditions();
//				cond.setCompareSingleValue(compareRangeSingle);
//			}
//		}
//
//		return cond;
//	}
	
//	private ContinuousOutput checkPerformanceOfConsecutiveItem(AttendanceTimeOfWeekly attWeekly,
//                                                               ExtractionCondWeekly weeklyCond, WeeklyAttendanceItemCondition<?> erAlAtdItemCon, List<ItemValue> convert, int count,
//                                                               int currentIndex, int sizeLoop, List<AttendanceTimeOfWeekly> attWeeklyBySid) {
//		ContinuousOutput ouput = new ContinuousOutput();
//		// ?????????????????????????????????
//		// Output: ????????????
//		boolean errorAtr = checkAttendanceItem(erAlAtdItemCon, item -> {
//			if (item.isEmpty()) {
//				return new ArrayList<>();
//			}
//			return convert.stream().filter(x -> item.contains(x.getItemId())).map(iv -> getValue(iv))
//					.collect(Collectors.toList());
//		});
//
//		//
//		int continuousPeriod = 0;
//		if (weeklyCond.getContinuousPeriod().isPresent()) {
//			continuousPeriod = weeklyCond.getContinuousPeriod().get().v();
//		}
//
//		// ????????????????????????????????????
//		CalCountForConsecutivePeriodOutput calCountForConsecutivePeriodOutput = calCountForConsecutivePeriodChecking.getContinuousCount(
//				count,
//				continuousPeriod,
//				errorAtr);
//		// QA#117728
//		currentIndex = currentIndex + 1;
//		ouput.check = calCountForConsecutivePeriodOutput.getOptContinuousCount().isPresent()
//				|| (errorAtr && currentIndex == sizeLoop && calCountForConsecutivePeriodOutput.getCount() >= continuousPeriod);
//		ouput.continuousCountOpt = calCountForConsecutivePeriodOutput.getOptContinuousCount();
//		ouput.count = calCountForConsecutivePeriodOutput.getCount();
//
//		return ouput;
//	}

	private Boolean checkAttendanceItem(WeeklyAttendanceItemCondition<?> erAlAtdItemCon, Function<List<Integer>, List<Double>> getValueFromItemIds) {
		return erAlAtdItemCon.checkTarget(getValueFromItemIds);
	}
	
	private Double calAttendanceItem(WeeklyAttendanceItemCondition<?> erAlAtdItemCon, Function<List<Integer>, List<Double>> getValueFromItemIds) {
		return erAlAtdItemCon.calculateTargetValue(getValueFromItemIds);
	}
	
	private Double getValue(ItemValue value) {
		if(value.value() == null){
			return 0d;
		}
		
		if (value.getValueType().isDouble() || value.getValueType().isInteger()) {
			return value.getValueType().isDouble() ? ((Double) value.value()) : Double.valueOf((Integer) value.value());
		}
		
		return 0d;
	}
	
	/**
	 * Get parameter 0 for alarm content 
	 */
	@SuppressWarnings({ "rawtypes" })
	public String getCompareOperatorText(String cid, CheckedCondition checkCondition, WeeklyCheckItemType weeklyCheckType, Optional<CheckedTarget> checkedTarget) {
		String checkCondTypeName = weeklyCheckType.nameId;
		
		if (checkedTarget.isPresent()) { // #118542
			CountableTarget countableTarget = (CountableTarget)checkedTarget.get();
			AddSubAttendanceItems addSubAttendanceItems = countableTarget.getAddSubAttendanceItems();
			
			List<Integer> itemIds = new ArrayList<>();
			if (addSubAttendanceItems.getAdditionAttendanceItems().size() > 0) {
				itemIds.addAll(addSubAttendanceItems.getAdditionAttendanceItems());
			}
			if (addSubAttendanceItems.getSubstractionAttendanceItems().size() > 0) {
				itemIds.addAll(addSubAttendanceItems.getSubstractionAttendanceItems());
			}
			
			List<MonthlyAttendanceItemAtr> attrs = new ArrayList<>();
			attrs.add(MonthlyAttendanceItemAtr.TIME);
			attrs.add(MonthlyAttendanceItemAtr.NUMBER);
			attrs.add(MonthlyAttendanceItemAtr.DAYS);
			List<AttItemName> attendanceItems = atName.getMonthlyItems(cid, Optional.empty(), itemIds, attrs);
			
			if (addSubAttendanceItems.getAdditionAttendanceItems().size() > 0) {
				checkCondTypeName = attendanceItems.stream()
						.filter(x -> addSubAttendanceItems.getAdditionAttendanceItems().contains(x.getAttendanceItemId()))
						.map(x -> x.getAttendanceItemName()).collect(Collectors.joining("+", "", ""));
			}
			
			if (addSubAttendanceItems.getSubstractionAttendanceItems().size() > 0) {
				checkCondTypeName += '-' + attendanceItems.stream()
						.filter(x -> addSubAttendanceItems.getSubstractionAttendanceItems().contains(x.getAttendanceItemId()))
						.map(x -> x.getAttendanceItemName()).collect(Collectors.joining("-", "", ""));
			}
		}
		
		int compare = checkCondition instanceof CompareSingleValue 
				? ((CompareSingleValue) checkCondition).getCompareOpertor().value
				: ((CompareRange) checkCondition).getCompareOperator().value;
				
		CompareOperatorText compareOperatorText = convertComparaToText.convertCompareType(compare);
		
		String startValueStr = Strings.EMPTY;
		String endValueStr = Strings.EMPTY;
		Double startValue = checkCondition instanceof CompareSingleValue 
						? (Double)((CompareSingleValue) checkCondition).getValue()
						: (Double)((CompareRange) checkCondition).getStartValue();
		Double endValue = checkCondition instanceof CompareRange  
				? (Double)((CompareRange) checkCondition).getEndValue() : null;
				
		switch (weeklyCheckType) {
		case TIME:
		case CONTINUOUS_TIME:
			HolidayTime startTime = new HolidayTime(startValue.intValue());
			startValueStr = startTime.getTimeWithFormat();
			if (endValue != null) {
				CheckedTimeDuration endTime = new CheckedTimeDuration(endValue.intValue());
				endValueStr = endTime.getTimeWithFormat();
			}
			break;
		case TIMES:
		case CONTINUOUS_TIMES:		
			startValueStr = String.valueOf(startValue.intValue());
			if (endValue != null) {
				endValueStr = String.valueOf(endValue.intValue());
			}
			break;
		case DAY_NUMBER:
		case CONTINUOUS_DAY:
			startValueStr = startValue.toString();
			if (endValue != null) {
				endValueStr = endValue.toString();
			}
			break;
		default:
			break;
		}
		
		String variable0 = "";
		if(compare <= 5) {
			variable0 = checkCondTypeName + compareOperatorText.getCompareLeft() + startValueStr;
		} else {
			if (compare == 6 || compare == 7) {
				variable0 = startValueStr + compareOperatorText.getCompareLeft() + checkCondTypeName
						+ compareOperatorText.getCompareright() + endValueStr;
			} else {
				variable0 = checkCondTypeName + compareOperatorText.getCompareLeft() + startValueStr
						+ ", " + checkCondTypeName + compareOperatorText.getCompareright() + endValueStr;
			}
		}
		
		return variable0;
	}
	
	/**
	 * Format time
	 * because not defined primitive value => function created!
	 * @param value integer value time
	 * @return format time HH:MM
	 */
	private String formatTime(int value) {
		int hours = Math.abs(value) / 60;
		int minute = Math.abs(value) % 60;
		
		return hours + ":" + (minute < 10 ? "0" + minute : minute); 
	}
	
	private class ContinuousOutput {
		/** ???????????? */
		public int count = 0;
		
		/** ???????????? */
		@SuppressWarnings("unused")
		public boolean check = false;
		
		/** Optional<????????????????????? */
		public Optional<ContinuousCount> continuousCountOpt = Optional.empty();
	}
	
	private class ExtractResultDetailAndCount {
		public ExtractResultDetail detail;
		
		public int count = 0;
		
		public ExtractResultDetailAndCount(ExtractResultDetail detail, int count) {
			this.detail = detail;
			this.count = count;
		}
	}
}
