package nts.uk.ctx.at.record.dom.workrecord.erroralarm.weekly.algorithm;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.uk.ctx.at.shared.dom.alarmList.AlarmCategory;
import nts.uk.ctx.at.shared.dom.alarmList.persistenceextractresult.*;
import org.apache.logging.log4j.util.Strings;

import nts.arc.task.parallel.ManagedParallelWithContext;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.attendanceitem.CheckedCondition;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.attendanceitem.CompareRange;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.attendanceitem.CompareSingleValue;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.attendanceitem.CountableTarget;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums.CompareOperatorText;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums.ConvertCompareTypeToText;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.mastercheck.algorithm.WorkPlaceHistImportAl;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.mastercheck.algorithm.WorkPlaceIdAndPeriodImportAl;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.CheckedTimeDuration;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.HolidayTime;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.weekly.ExtractionCondScheduleWeekly;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.weekly.ExtractionCondScheduleWeeklyRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.weekly.WeeklyCheckItemType;
import nts.uk.ctx.at.record.dom.workrecord.errorsetting.ContinuousCount;
import nts.uk.ctx.at.record.dom.workrecord.errorsetting.algorithm.CalCountForConsecutivePeriodChecking;
import nts.uk.ctx.at.record.dom.workrecord.errorsetting.algorithm.CalCountForConsecutivePeriodOutput;
import nts.uk.ctx.at.shared.dom.alarmList.extractionResult.AlarmListCheckInfor;
import nts.uk.ctx.at.shared.dom.alarmList.extractionResult.AlarmListCheckType;
import nts.uk.ctx.at.shared.dom.alarmList.extractionResult.ExtractionAlarmPeriodDate;
import nts.uk.ctx.at.shared.dom.alarmList.extractionResult.ResultOfEachCondition;
import nts.uk.ctx.at.shared.dom.scherec.attendanceitem.converter.service.AttendanceItemConvertFactory;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.weekly.AttendanceTimeOfWeekly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.weekly.converter.WeeklyRecordToAttendanceItemConverter;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.i18n.TextResource;

@Stateless
public class WeeklyCheckServiceImpl implements WeeklyCheckService {
	@Inject
	private ManagedParallelWithContext parallelManager;
	@Inject
	private WeeklyAttendanceItemService weeklyAttendanceItemService;
	@Inject
	private GetWeeklyPerformanceService getWeeklyPerformanceService;
	@Inject
	private ExtractionCondScheduleWeeklyRepository extractionCondScheduleWeeklyRepository;
	@Inject
	private AttendanceItemConvertFactory attendanceItemConvertFactory;
	@Inject
	private CalCountForConsecutivePeriodChecking calCountForConsecutivePeriodChecking;
	@Inject
	private ConvertCompareTypeToText convertComparaToText;
	
	@Override
	public void extractWeeklyCheck(String cid, List<String> lstSid, DatePeriod period,
			List<WorkPlaceHistImportAl> wplByListSidAndPeriods, String listOptionalItem,
			List<ResultOfEachCondition> lstResultCondition, List<AlarmListCheckInfor> lstCheckType,
			Consumer<Integer> counter, Supplier<Boolean> shouldStop, List<AlarmEmployeeList> alarmEmployeeList,
			List<AlarmExtractionCondition> alarmExtractConditions, String alarmCheckConditionCode) {
		String contractCode = AppContexts.user().contractCode();
		
		parallelManager.forEach(CollectionUtil.partitionBySize(lstSid, 100), emps -> {
			synchronized (this) {
				if (shouldStop.get()) {
					return;
				}
			}
			
			// 週次の勤怠項目を取得する
			Map<Integer, String> attendanceItemMap = weeklyAttendanceItemService.getAttendanceItem(cid);
			
			// 週別実績の値を取得
			// QA#116337
			List<AttendanceTimeOfWeekly> attendanceTimeOfWeeklys = getWeeklyPerformanceService.getValues(lstSid, period);
			
			// ドメインモデル「週別実績の抽出条件」を取得する
			// 条件: ID　＝　Input．週次のアラームチェック条件．チェック条件．任意抽出条件
			// Output: List＜週別実績の任意抽出条件＞
			List<ExtractionCondScheduleWeekly> weeklyConds = extractionCondScheduleWeeklyRepository.getScheAnyCond(
					contractCode, cid, listOptionalItem).stream().filter(x -> x.isUse()).collect(Collectors.toList());
			
			// Input．List＜社員ID＞をループ
			for (String sid: lstSid) {
                List<AlarmExtractInfoResult> lstExtractInfoResult = new ArrayList<>();
				// 取得したList＜週別実績の任意抽出条件＞をループする
				for (ExtractionCondScheduleWeekly weeklyCond: weeklyConds) {
					String alarmCode = String.valueOf(weeklyCond.getSortOrder());
					val lstExtractCond = alarmExtractConditions.stream()
							.filter(x -> x.getAlarmListCheckType() == AlarmListCheckType.FreeCheck && x.getAlarmCheckConditionNo().equals(String.valueOf(alarmCode)))
							.findAny();
					if (!lstExtractCond.isPresent()) {
						alarmExtractConditions.add(new AlarmExtractionCondition(
								String.valueOf(alarmCode),
								new AlarmCheckConditionCode(alarmCheckConditionCode),
								AlarmCategory.WEEKLY,
								AlarmListCheckType.FreeCheck
						));
					}
					
					int count = 0;
					// Input．期間の開始月からループする
					for (YearMonth ym: period.yearMonthsBetween()) {
						// ・職場ID　＝　Input．List＜職場ID＞をループ中の年月日から探す
						String wpkId = "";
						Optional<WorkPlaceHistImportAl> optWorkPlaceHistImportAl = wplByListSidAndPeriods.stream()
								.filter(x -> x.getEmployeeId().equals(sid)).findFirst();
						if(optWorkPlaceHistImportAl.isPresent()) {
							Optional<WorkPlaceIdAndPeriodImportAl> optWorkPlaceIdAndPeriodImportAl = optWorkPlaceHistImportAl.get().getLstWkpIdAndPeriod().stream()
									.filter(x -> x.getDatePeriod().start().beforeOrEquals(ym.firstGeneralDate()) 
											&& x.getDatePeriod().end().afterOrEquals(ym.lastGeneralDate())).findFirst();
							if(optWorkPlaceIdAndPeriodImportAl.isPresent()) {
								wpkId = optWorkPlaceIdAndPeriodImportAl.get().getWorkplaceId();
							}
						}
						
						// ループ中の社員の月の週別実績の勤務時間を絞り込み
						// 期間．開始日　＞＝ （Input. 期間．開始日　＞　ループ中の年月．開始日　？　Input. 期間．開始日　：　ループ中の年月．開始日）　QA#115666
						GeneralDate startCompare = period.start().after(ym.firstGeneralDate()) ? period.start() : ym.firstGeneralDate();
						// 期間．終了日　＜＝　（Input. 期間．終了日　＜　ループ中の年月．終了日　？　Input. 期間．終了日　：　ループ中の年月．終了日）　QA#115666
						GeneralDate endCompare = period.end().before(ym.lastGeneralDate()) ? period.end() : ym.lastGeneralDate();
						List<AttendanceTimeOfWeekly> attendanceTimeOfWeeklyYms = attendanceTimeOfWeeklys.stream()
								.filter(x -> x.getEmployeeId().equals(sid) && x.getYearMonth().equals(ym) 
										&& x.getPeriod().start().afterOrEquals(startCompare) && x.getPeriod().start().beforeOrEquals(endCompare))
								.collect(Collectors.toList());
						
						// 絞り込みしたList＜週別実績の勤怠時間＞をループする
						for (AttendanceTimeOfWeekly attWeekly : attendanceTimeOfWeeklyYms) {
							// 任意抽出条件のアラーム値を作成する
							ExtractResultDetail extractDetail = createAlarmExtraction(
									attWeekly, weeklyCond, count, attendanceItemMap, cid, sid, wpkId, ym, attendanceTimeOfWeeklyYms.size());
							if (extractDetail == null) {
								continue;
							}

							if (lstExtractInfoResult.stream().anyMatch(i -> i.getAlarmCategory() == AlarmCategory.WEEKLY
									&& i.getAlarmCheckConditionCode().v().equals(alarmCheckConditionCode)
									&& i.getAlarmListCheckType() == AlarmListCheckType.FreeCheck
									&& i.getAlarmCheckConditionNo().equals(alarmCode))) {
								for (AlarmExtractInfoResult i : lstExtractInfoResult) {
									if (i.getAlarmCategory() == AlarmCategory.WEEKLY
											&& i.getAlarmCheckConditionCode().v().equals(alarmCheckConditionCode)
											&& i.getAlarmListCheckType() == AlarmListCheckType.FreeCheck
											&& i.getAlarmCheckConditionNo().equals(alarmCode)) {
										List<ExtractResultDetail> tmp = new ArrayList<>(i.getExtractionResultDetails());
										tmp.add(extractDetail);
										i.setExtractionResultDetails(tmp);
										break;
									}
								}
							} else {
								List<ExtractResultDetail> listDetail = new ArrayList<>(Arrays.asList(extractDetail));
								lstExtractInfoResult.add(new AlarmExtractInfoResult(
										alarmCode,
										new AlarmCheckConditionCode(alarmCheckConditionCode),
										AlarmCategory.WEEKLY,
										AlarmListCheckType.FreeCheck,
										listDetail
								));
							}

                            List<AlarmExtractionCondition> lstExtractCondition = alarmExtractConditions.stream()
                                    .filter(x -> x.getAlarmListCheckType() == AlarmListCheckType.FreeCheck && x.getAlarmCheckConditionNo().equals(String.valueOf(alarmCode)))
                                    .collect(Collectors.toList());
                            if (lstExtractCondition.isEmpty()) {
                                alarmExtractConditions.add(new AlarmExtractionCondition(
                                        String.valueOf(alarmCode),
                                        new AlarmCheckConditionCode(alarmCheckConditionCode),
                                        AlarmCategory.WEEKLY,
                                        AlarmListCheckType.FreeCheck
                                ));
                            }
						}
					}
					
					// 各チェック条件の結果を作成
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
	private ExtractResultDetail createAlarmExtraction(
			AttendanceTimeOfWeekly attWeekly,
			ExtractionCondScheduleWeekly weeklyCond,
			int count,
			Map<Integer, String> attendanceItemMap,
			String cid,
			String sid,
			String wpkId,
			YearMonth ym,
			int sizeWeeklyActualAttendanceTime) {
		
		boolean check = false;
		ContinuousOutput continuousOutput = new ContinuousOutput();
		
		// 週次のコンバーターを交換 ErAlAttendanceItemCondition
		// QA#115685
		WeeklyRecordToAttendanceItemConverter weeklyConvert = attendanceItemConvertFactory.createWeeklyConverter();
		weeklyConvert.withAttendanceTime(attWeekly);
		
		@SuppressWarnings("rawtypes")
		WeeklyAttendanceItemCondition cond = convertToErAlAttendanceItem(cid, weeklyCond);
		
		WeeklyCheckItemType checkItemType = weeklyCond.getCheckItemType();
		switch (checkItemType) {
		case TIME:
		case TIMES:
		case DAY_NUMBER:
			// 勤怠項目をチェックする
			check = checkAttendanceItem(cond, item -> {
				return weeklyConvert.convert(attendanceItemMap.keySet()).stream().map(iv -> getValue(iv))
						.collect(Collectors.toList());
			});
			break;
		case CONTINUOUS_TIME:
		case CONTINUOUS_TIMES:
		case CONTINUOUS_DAY:
			// 連続の項目の実績をチェック
			continuousOutput = checkPerformanceOfConsecutiveItem(
					attWeekly, weeklyCond, cond, weeklyConvert.convert(attendanceItemMap.keySet()), count);
			break;
			
		default:
			break;
		}
		
		// 
		ExtractionAlarmPeriodDate extractionAlarmPeriodDate = null;
		Optional<String> comment = Optional.empty();
		String alarmContent = Strings.EMPTY;
		
		// チェック項目の種類は連続じゃないの場合　－＞#KAL010_1314 
		// {0}　＝　Input．週別実績の勤怠時間から計算した値 QA#115666
		String weeklyActualAttendanceTime = String.valueOf(sizeWeeklyActualAttendanceTime);
		String checkTargetValue = TextResource.localize("KAL010_1314", weeklyActualAttendanceTime);
		
		// 週別実績の任意抽出条件．チェック項目の種類！＝4,5,6　AND　該当区分　＝　True
		// OR
		// 週別実績の任意抽出条件．チェック項目の種類＝＝4 or 5or 6　AND　取得したOptional<連続カウント＞　!＝　Empty
		if ((!weeklyCond.isContinuos() && check) || (weeklyCond.isContinuos() && continuousOutput.continuousCountOpt.isPresent())) {
			// 「抽出結果詳細」を作成
			// アラーム項目日付　＝Input．週別実績の勤怠時間．期間．開始日
			extractionAlarmPeriodDate = new ExtractionAlarmPeriodDate(
					Optional.of(ym.firstGeneralDate()), Optional.empty());
			// コメント　＝　Input．週別実績の任意抽出条件．表示メッセージ
			if (weeklyCond.getErrorAlarmMessage() != null && weeklyCond.getErrorAlarmMessage().isPresent()) {
				comment = Optional.ofNullable(weeklyCond.getErrorAlarmMessage().get().v());
			}
			// アラーム内容
			String param0 = getCompareOperatorText(weeklyCond.getCheckConditions(), weeklyCond.getCheckItemType());
			
			// 	チェック項目の種類は連続じゃないの場合　－＞#KAL010_1308
			String param1 = TextResource.localize("KAL010_1308");
			// チェック項目の種類は連続じゃないの場合　－＞Input．週別実績の勤怠時間から計算した値 
			String param2 = weeklyActualAttendanceTime;
			if (weeklyCond.isContinuos()) {
				String continuousPeriodValue = String.valueOf(weeklyCond.getContinuousPeriod().get().v());
				param0 += TextResource.localize("KAL010_1312", continuousPeriodValue);
				// チェック項目の種類は連続の場合　－＞#KAL010_1309
				param1 = TextResource.localize("KAL010_1309");
				param2 += TextResource.localize("KAL010_1311");
				
				// チェック項目の種類は連続の場合　－＞#KAL010_1313 {0}　＝　取得した連続カウント　
				checkTargetValue = TextResource.localize("KAL010_1313", String.valueOf(continuousOutput.continuousCountOpt.get().getConsecutiveYears()));
			}
			
			alarmContent = TextResource.localize("KAL010_1310", param0, param1, param2);
			
			// 取得したアカウント、作成した「抽出結果詳細」を返す
			ExtractResultDetail detail = new ExtractResultDetail(
					extractionAlarmPeriodDate,
					weeklyCond.getName().v(),
					alarmContent,
					GeneralDateTime.now(),
					Optional.ofNullable(wpkId),
					comment,
					Optional.ofNullable(checkTargetValue));
			return detail;
		}
		
		return null;
	}
	
	/**
	 * Convert ExtractionCondScheduleWeekly to ErAlAttendanceItemCondition
	 * @param cid company id
	 * @param weeklyCond ExtractionCondScheduleWeekly
	 * @return ErAlAttendanceItemCondition
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private WeeklyAttendanceItemCondition convertToErAlAttendanceItem(String cid, ExtractionCondScheduleWeekly weeklyCond) {
		WeeklyAttendanceItemCondition cond = new WeeklyAttendanceItemCondition<>();
		
		if(weeklyCond.getCheckedTarget().isPresent()) {
			CountableTarget countableTarget = (CountableTarget)weeklyCond.getCheckedTarget().get();
			cond.setCountableTarget(
					countableTarget.getAddSubAttendanceItems().getAdditionAttendanceItems(), 
					countableTarget.getAddSubAttendanceItems().getSubstractionAttendanceItems());			
		}
		
		if (weeklyCond.getCheckConditions() != null) {
			if (weeklyCond.getCheckConditions() instanceof CompareRange) {
				CompareRange compareRange = (CompareRange)weeklyCond.getCheckConditions();
				cond.setCompareRange(compareRange);
			} else {
				CompareSingleValue<Double> compareRangeSingle = (CompareSingleValue)weeklyCond.getCheckConditions();
				cond.setCompareSingleValue(compareRangeSingle);
			}
		}
		
		return cond;
	}
	
	private ContinuousOutput checkPerformanceOfConsecutiveItem(AttendanceTimeOfWeekly attWeekly,
			ExtractionCondScheduleWeekly weeklyCond, WeeklyAttendanceItemCondition<?> erAlAtdItemCon, List<ItemValue> convert, int count) {
		ContinuousOutput ouput = new ContinuousOutput();
		// 勤怠項目をチェックする
		// Output: 該当区分
		boolean errorAtr = checkAttendanceItem(erAlAtdItemCon, item -> {
			if (item.isEmpty()) {
				return new ArrayList<>();
			}
			return convert.stream().filter(x -> item.contains(x.getItemId())).map(iv -> getValue(iv))
					.collect(Collectors.toList());
		});
		// 
		int continuousPeriod = 0;
		if (weeklyCond.getContinuousPeriod().isPresent()) {
			continuousPeriod = weeklyCond.getContinuousPeriod().get().v();
		}
		
		CalCountForConsecutivePeriodOutput calCountForConsecutivePeriodOutput = calCountForConsecutivePeriodChecking.getContinuousCount(
				count, 
				continuousPeriod, 
				errorAtr, 
				null);
		ouput.check = errorAtr;
		ouput.continuousCountOpt = calCountForConsecutivePeriodOutput.getOptContinuousCount();
		ouput.count = calCountForConsecutivePeriodOutput.getCount();
		
		return ouput;
	}

	private Boolean checkAttendanceItem(WeeklyAttendanceItemCondition<?> erAlAtdItemCon, Function<List<Integer>, List<Double>> getValueFromItemIds) {
		return erAlAtdItemCon.checkTarget(getValueFromItemIds);
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
	public String getCompareOperatorText(CheckedCondition checkCondition, WeeklyCheckItemType weeklyCheckType) {
		String checkCondTypeName = weeklyCheckType.nameId;
		
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
	
	private class ContinuousOutput {
		/** カウント */
		@SuppressWarnings("unused")
		public int count = 0;
		
		/** 該当区分 */
		@SuppressWarnings("unused")
		public boolean check = false;
		
		/** Optional<連続カウント＞ */
		public Optional<ContinuousCount> continuousCountOpt = Optional.empty();
	}
}
