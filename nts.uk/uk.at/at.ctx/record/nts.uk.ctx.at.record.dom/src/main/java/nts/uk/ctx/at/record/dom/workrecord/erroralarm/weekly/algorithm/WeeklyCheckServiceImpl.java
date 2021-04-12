package nts.uk.ctx.at.record.dom.workrecord.erroralarm.weekly.algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.logging.log4j.util.Strings;

import nts.arc.enums.EnumAdaptor;
import nts.arc.task.parallel.ManagedParallelWithContext;
import nts.arc.time.GeneralDateTime;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.attendanceitem.AttendanceItemCondition;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.attendanceitem.CompareRange;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.attendanceitem.CompareSingleValue;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.attendanceitem.CountableTarget;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.attendanceitem.ErAlAttendanceItemCondition;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums.ConditionType;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums.ErrorAlarmConditionType;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums.LogicalOperator;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.mastercheck.algorithm.WorkPlaceHistImportAl;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.mastercheck.algorithm.WorkPlaceIdAndPeriodImportAl;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.weekly.ExtractionCondScheduleWeekly;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.weekly.ExtractionCondScheduleWeeklyRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.weekly.WeeklyCheckItemType;
import nts.uk.ctx.at.record.dom.workrecord.errorsetting.ContinuousCount;
import nts.uk.ctx.at.record.dom.workrecord.errorsetting.algorithm.CalCountForConsecutivePeriodChecking;
import nts.uk.ctx.at.shared.dom.alarmList.extractionResult.AlarmListCheckInfor;
import nts.uk.ctx.at.shared.dom.alarmList.extractionResult.AlarmListCheckType;
import nts.uk.ctx.at.shared.dom.alarmList.extractionResult.ExtractionAlarmPeriodDate;
import nts.uk.ctx.at.shared.dom.alarmList.extractionResult.ExtractionResultDetail;
import nts.uk.ctx.at.shared.dom.alarmList.extractionResult.ResultOfEachCondition;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.service.AttendanceItemConvertFactory;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.erroralarm.ErrorAlarmWorkRecordCode;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.converter.MonthlyRecordToAttendanceItemConverter;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.weekly.AttendanceTimeOfWeekly;
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
	
	@Override
	public void extractWeeklyCheck(String cid, List<String> lstSid, DatePeriod period,
			List<WorkPlaceHistImportAl> wplByListSidAndPeriods, String listOptionalItem,
			List<ResultOfEachCondition> lstResultCondition, List<AlarmListCheckInfor> lstCheckType,
			Consumer<Integer> counter, Supplier<Boolean> shouldStop) {
		String contractCode = AppContexts.user().companyCode();
		
		parallelManager.forEach(CollectionUtil.partitionBySize(lstSid, 100), emps -> {
			synchronized (this) {
				if (shouldStop.get()) {
					return;
				}
			}
			
			// 週次の勤怠項目を取得する
			Map<Integer, String> attendanceItemMap = weeklyAttendanceItemService.getAttendanceItem(cid);
			
			// 週別実績の値を取得
			List<AttendanceTimeOfWeekly> attendanceTimeOfWeeklys = getWeeklyPerformanceService.getValues(lstSid, period);
			
			// ドメインモデル「週別実績の抽出条件」を取得する
			// 条件: ID　＝　Input．週次のアラームチェック条件．チェック条件．任意抽出条件
			// Output: List＜週別実績の任意抽出条件＞
			List<ExtractionCondScheduleWeekly> weeklyConds = extractionCondScheduleWeeklyRepository.getScheAnyCond(
					contractCode, cid, listOptionalItem).stream().filter(x -> x.isUse()).collect(Collectors.toList());
			
			// Input．List＜社員ID＞をループ
			for (String sid: lstSid) {
				
				// 取得したList＜週別実績の任意抽出条件＞をループする
				for (ExtractionCondScheduleWeekly weeklyCond: weeklyConds) {
					String alarmCode = String.valueOf(weeklyCond.getSortOrder());
					
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
						List<AttendanceTimeOfWeekly> attendanceTimeOfWeeklyYms = attendanceTimeOfWeeklys.stream()
								.filter(x -> x.getEmployeeId().equals(sid) && x.getYearMonth().equals(ym))
								.collect(Collectors.toList());
						
						// 絞り込みしたList＜週別実績の勤怠時間＞をループする
						for (AttendanceTimeOfWeekly attWeekly: attendanceTimeOfWeeklyYms) {
							// 任意抽出条件のアラーム値を作成する
							ExtractionResultDetail extractDetail = createAlarmExtraction(
									attWeekly, weeklyCond, count, attendanceItemMap, cid, sid, wpkId, ym);
							
							List<ResultOfEachCondition> lstResultTmp = lstResultCondition.stream()
									.filter(x -> x.getCheckType() == AlarmListCheckType.FreeCheck && x.getNo().equals(alarmCode)).collect(Collectors.toList());
							List<ExtractionResultDetail> listDetail = new ArrayList<>();
							if(lstResultTmp.isEmpty()) {
								listDetail.add(extractDetail);
								lstResultCondition.add(new ResultOfEachCondition(EnumAdaptor.valueOf(1, AlarmListCheckType.class), alarmCode, 
										listDetail));	
							} else {
								lstResultCondition.stream().forEach(x -> x.getLstResultDetail().add(extractDetail));
							}
							
							Optional<AlarmListCheckInfor> optCheckInfor = lstCheckType.stream()
									.filter(x -> x.getChekType() == AlarmListCheckType.FreeCheck && x.getNo().equals(String.valueOf(alarmCode)))
									.findFirst();
							if(!optCheckInfor.isPresent()) {
								lstCheckType.add(new AlarmListCheckInfor(String.valueOf(alarmCode), AlarmListCheckType.FreeCheck));
							}
						}
					}
					
					// 各チェック条件の結果を作成
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
	private ExtractionResultDetail createAlarmExtraction(
			AttendanceTimeOfWeekly attWeekly,
			ExtractionCondScheduleWeekly weeklyCond,
			int count,
			Map<Integer, String> attendanceItemMap,
			String cid,
			String sid,
			String wpkId,
			YearMonth ym) {
		
		boolean check = false;
		ContinuousOutput continuousOutput = new ContinuousOutput();
		
		// 週次のコンバーターを交換 ErAlAttendanceItemCondition
		//List<ItemValue> convert = monthlyRecordToAttendanceItemConverter.convert(attendanceItemMap.keySet()); TODO
		List<ItemValue> convert = new ArrayList<>();
		MonthlyRecordToAttendanceItemConverter con = attendanceItemConvertFactory.createMonthlyConverter();
		ErAlAttendanceItemCondition cond = new ErAlAttendanceItemCondition<>(
				cid, 
				"",
				weeklyCond.getSortOrder(), 
				0, true, 
				ErrorAlarmConditionType.ATTENDANCE_ITEM.value);
		
		if(weeklyCond.getCheckedTarget().isPresent()) {
			CountableTarget countableTarget = (CountableTarget)weeklyCond.getCheckedTarget().get();
			cond.setCountableTarget(
					countableTarget.getAddSubAttendanceItems().getAdditionAttendanceItems(), 
					countableTarget.getAddSubAttendanceItems().getSubstractionAttendanceItems());			
		}
		
		if (weeklyCond.getCheckConditions() != null) {
			if (weeklyCond.getCheckConditions() instanceof CompareRange) {
				CompareRange compareRange = (CompareRange)weeklyCond.getCheckConditions();
				cond.setCompareRange(
						compareRange.getCompareOperator().value, 
						compareRange.getStartValue(), 
						compareRange.getStartValue());
			} else {
				CompareSingleValue<Double> compareRange = (CompareSingleValue)weeklyCond.getCheckConditions();
				cond.setCompareSingleValue(
						compareRange.getCompareOpertor().value, 
						ConditionType.ATTENDANCE_ITEM.value, (Double)compareRange.getValue());
			}
		}
		
		WeeklyCheckItemType checkItemType = weeklyCond.getCheckItemType();
		switch (checkItemType) {
		case TIME:
		case TIMES:
		case DAY_NUMBER:
			// 勤怠項目をチェックする
			check = checkAttendanceItem(cond, item -> {
				return convert.stream().map(iv -> getValue(iv))
						.collect(Collectors.toList());
			});
			break;
		case CONTINUOUS_TIME:
		case CONTINUOUS_TIMES:
		case CONTINUOUS_DAY:
			// 連続の項目の実績をチェック
			continuousOutput = checkPerformanceOfConsecutiveItem(
					attWeekly, weeklyCond, cond, convert, count);
			break;
			
		default:
			break;
		}
		
		// 
		ExtractionAlarmPeriodDate extractionAlarmPeriodDate = null;
		Optional<String> comment = Optional.empty();
		String alarmContent = Strings.EMPTY;
		
		// チェック項目の種類は連続じゃないの場合　－＞#KAL010_1314 
		// {0}　＝　Input．週別実績の勤怠時間から計算した値 TODO chua ro lay truong nao
		String weeklyActualAttendanceTime = String.valueOf(attWeekly.getWeeklyCalculation().getTotalWorkingTime());
		String checkTargetValue = TextResource.localize("KAL010_1314");
		
		// 週別実績の任意抽出条件．チェック項目の種類！＝4,5,6　AND　該当区分　＝　True
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
			String param0 = Strings.EMPTY;
			// 	チェック項目の種類は連続じゃないの場合　－＞#KAL010_1308
			String param1 = TextResource.localize("KAL010_1308");
			// チェック項目の種類は連続じゃないの場合　－＞Input．週別実績の勤怠時間から計算した値 
			String param2 = weeklyActualAttendanceTime;
			if (weeklyCond.isContinuos()) {
				String continuousPeriodValue = String.valueOf(weeklyCond.getContinuousPeriod().get().v());
				param0 = TextResource.localize("KAL010_1312", continuousPeriodValue);
				// チェック項目の種類は連続の場合　－＞#KAL010_1309
				param1 = TextResource.localize("KAL010_1309");
				param2 += TextResource.localize("KAL010_1311");
				
				// チェック項目の種類は連続の場合　－＞#KAL010_1313 {0}　＝　取得した連続カウント　
				checkTargetValue = TextResource.localize("KAL010_1313", String.valueOf(continuousOutput.continuousCountOpt.get().getConsecutiveYears()));
			}
			
			alarmContent = TextResource.localize("KAL010_1310", param0);
		}
		
		// 取得したアカウント、作成した「抽出結果詳細」を返す
		ExtractionResultDetail detail = new ExtractionResultDetail(sid, 
				extractionAlarmPeriodDate, 
				weeklyCond.getName().v(), 
				alarmContent, 
				GeneralDateTime.now(), 
				Optional.ofNullable(wpkId), 
				comment, 
				Optional.ofNullable(checkTargetValue));
		return detail;
	}
	
	private ContinuousOutput checkPerformanceOfConsecutiveItem(AttendanceTimeOfWeekly attWeekly,
			ExtractionCondScheduleWeekly weeklyCond, ErAlAttendanceItemCondition<?> erAlAtdItemCon, List<ItemValue> convert, int count) {
		ContinuousOutput ouput = new ContinuousOutput();
		// 勤怠項目をチェックする
		// Output: 該当区分
		boolean errorAtr = checkAttendanceItem(erAlAtdItemCon, item -> {
			if (item.isEmpty()) {
				return new ArrayList<>();
			}
			return convert.stream().map(iv -> getValue(iv))
					.collect(Collectors.toList());
		});
		// 
		int continuousPeriod = 0;
		if (weeklyCond.getContinuousPeriod().isPresent()) {
			continuousPeriod = weeklyCond.getContinuousPeriod().get().v();
		}
		Optional<ContinuousCount> optContinuousCount = Optional.empty();
		calCountForConsecutivePeriodChecking.getContinuousCount(
				optContinuousCount, 
				count, 
				continuousPeriod, 
				errorAtr, 
				null);
		ouput.check = errorAtr;
		ouput.continuousCountOpt = optContinuousCount;
		ouput.count = count;
		
		return ouput;
	}

	private Boolean checkAttendanceItem(ErAlAttendanceItemCondition<?> erAlAtdItemCon, Function<List<Integer>, List<Double>> getValueFromItemIds) {
		return erAlAtdItemCon.checkTarget(getValueFromItemIds);
	}
	
	private Double getValue(ItemValue value) {
		if(value.getValueType()==ValueType.DATE){
			return 0d;
		}
		if (value.value() == null) {
			return 0d;
		}
		else if (value.getValueType().isDouble()||value.getValueType().isInteger()) {
			return value.getValueType().isDouble() ? ((Double) value.value()) : Double.valueOf((Integer) value.value());
		}
		return 0d;
	}
	
	private class ContinuousOutput {
		/** カウント */
		public int count = 0;
		
		/** 該当区分 */
		public boolean check = false;
		
		/** Optional<連続カウント＞ */
		public Optional<ContinuousCount> continuousCountOpt = Optional.empty();
	}
}
