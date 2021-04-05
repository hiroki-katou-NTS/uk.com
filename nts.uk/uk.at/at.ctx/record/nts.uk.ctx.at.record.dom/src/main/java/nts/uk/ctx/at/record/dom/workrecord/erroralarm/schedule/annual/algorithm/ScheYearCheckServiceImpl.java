package nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.annual.algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.task.parallel.ManagedParallelWithContext;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.arc.time.calendar.period.YearMonthPeriod;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.dom.adapter.workschedule.WorkScheduleWorkInforAdapter;
import nts.uk.ctx.at.record.dom.adapter.workschedule.WorkScheduleWorkInforImport;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.GetAnnAndRsvRemNumWithinPeriod;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.param.AggrResultOfAnnAndRsvLeave;
import nts.uk.ctx.at.record.dom.require.RecordDomRequireService;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.mastercheck.algorithm.StatusOfEmployeeAdapterAl;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.mastercheck.algorithm.WorkPlaceHistImportAl;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.annual.DayCheckCond;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.annual.ExtractionCondScheduleYear;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.annual.ExtractionCondScheduleYearRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.annual.YearCheckItemType;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.daily.ScheYearCheckService;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.monthly.TypeOfDays;
import nts.uk.ctx.at.shared.dom.adapter.attendanceitemname.AttendanceItemNameAdapter;
import nts.uk.ctx.at.shared.dom.alarmList.extractionResult.AlarmListCheckInfor;
import nts.uk.ctx.at.shared.dom.alarmList.extractionResult.ResultOfEachCondition;
import nts.uk.ctx.at.shared.dom.dailyattdcal.converter.DailyRecordShareFinder;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.export.InterimRemainMngMode;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.AttendanceTimeOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.AttendanceTimeOfMonthlyRepository;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingCondition;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemRepository;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class ScheYearCheckServiceImpl implements ScheYearCheckService {
	@Inject
	private AttendanceItemNameAdapter attendanceItemNameAdapter;
	@Inject
	private DailyRecordShareFinder dailyRecordShareFinder;
	@Inject
	private WorkScheduleWorkInforAdapter workScheduleWorkInforAdapter;
	@Inject
	private ExtractionCondScheduleYearRepository condScheduleYearRepository;
	@Inject
	private WorkingConditionItemRepository workingConditionItemRepository;
	@Inject
	private WorkingConditionRepository workingConditionRepository;
	@Inject
    private AttendanceTimeOfMonthlyRepository attendanceTimeOfMonthlyRepo;
	@Inject
	private ManagedParallelWithContext parallelManager;
	@Inject
	private RecordDomRequireService requireService;
	
	@Override
	public void extractScheYearCheck(String cid, List<String> lstSid, DatePeriod dPeriod, String errorCheckId,
			String listOptionalItem, List<WorkPlaceHistImportAl> getWplByListSidAndPeriod,
			List<StatusOfEmployeeAdapterAl> lstStatusEmp, List<ResultOfEachCondition> lstResultCondition,
			List<AlarmListCheckInfor> lstCheckType, Consumer<Integer> counter, Supplier<Boolean> shouldStop) {
		String contractCode = AppContexts.user().contractCode();
		YearMonthPeriod ym = new YearMonthPeriod(dPeriod.start().yearMonth(), dPeriod.end().yearMonth());
		ScheYearPrepareData prepareData = prepareDataBeforeChecking(contractCode, cid, lstSid, dPeriod, errorCheckId, listOptionalItem);
		
		parallelManager.forEach(CollectionUtil.partitionBySize(lstSid, 100), emps -> {
			synchronized (this) {
				if (shouldStop.get()) {
					return;
				}
			}
			
			// 社員の指定期間中の所属期間を取得する
			List<StatusOfEmployeeAdapterAl> lstStatusEmployee = lstStatusEmp;
			
			// 特定属性の項目の予定を作成する
			extractCondition(lstSid, dPeriod, prepareData);
			
			synchronized (this) {
				counter.accept(emps.size());
			}
		});
	}
	
	/*
	 * チェックする前にデータ準備
	 */
	private ScheYearPrepareData prepareDataBeforeChecking(String contractCode, String cid, List<String> lstSid, DatePeriod dPeriod, 
			String errorDailyCheckId, String listOptionalItem) {
		YearMonthPeriod ym = new YearMonthPeriod(dPeriod.start().yearMonth(), dPeriod.end().yearMonth());
		
		// 1, スケジュール年間の勤怠項目を取得する
		Map<Integer, String> attendanceItems = getScheYearAttendanceItems(cid, 0);
		
		// 2, 社員ID(List)、期間を設定して勤務予定を取得する
		List<WorkScheduleWorkInforImport> workScheduleWorkInfos = workScheduleWorkInforAdapter.getBy(lstSid, dPeriod);
		
		// 3, 社員と期間を条件に日別実績を取得する
		List<IntegrationOfDaily> empDailyPerformance = getEmpDailyPerformance(lstSid, dPeriod);
		
		// 4, 社員ID（List）、期間を設定して月別実績を取得する
		List<AttendanceTimeOfMonthly> attendanceTimeOfMonthlies = attendanceTimeOfMonthlyRepo.findBySidsAndYearMonths(lstSid, ym.yearMonthsBetween());
		
		// 5, スケジュール年間の任意抽出条件のデータを取得する
		List<ExtractionCondScheduleYear> condScheYears = getCondScheYear(contractCode, cid, listOptionalItem);
		
		List<TypeOfDays> typeOfDaysSelected = new ArrayList<>();
		typeOfDaysSelected.add(TypeOfDays.WORKING_DAY_NUMBER);
		typeOfDaysSelected.add(TypeOfDays.HOLIDAY_NUMBER);
		typeOfDaysSelected.add(TypeOfDays.DAYOFF_NUMBER);
		typeOfDaysSelected.add(TypeOfDays.SPECIAL_HOLIDAY_NUMBER);
		typeOfDaysSelected.add(TypeOfDays.ABSENTEEDAY_NUMBER);
		typeOfDaysSelected.add(TypeOfDays.SCHE_WORKINGD_AND_WORKINGD);
		
		Map<String, Map<DatePeriod, WorkingConditionItem>> empWorkingCondItem = new HashMap<>();
		
		boolean existItemDay = condScheYears.stream().anyMatch(x -> x.getCheckItemType() == YearCheckItemType.DAY_NUMBER
				&& typeOfDaysSelected.contains(((DayCheckCond)x.getScheCheckConditions()).getTypeOfDays()));
		
		// 6, データをチェック
		if (existItemDay) {
			// 7, 社員ID（List）と期間から労働条件を取得する
			List<WorkingCondition> listWorkingCondition = workingConditionRepository
					.getBySidsAndDatePeriod(lstSid, dPeriod);
			
			// 8, ドメインモデル「労働条件項目」を取得
			List<WorkingConditionItem> workingConditionItems = workingConditionItemRepository
					.getBySidsAndDatePeriodNew(lstSid, dPeriod);
			
			Map<String, WorkingConditionItem> mapWorkingCondtionItem = workingConditionItems.stream()
					.collect(Collectors.toMap(WorkingConditionItem::getHistoryId, x -> x));
			
			// 9, 労働条件を作成
			listWorkingCondition.forEach(x -> {
				Map<DatePeriod, WorkingConditionItem> mapWorkingCondItems = new HashMap<>();
				x.getDateHistoryItem().forEach(y -> {
					WorkingConditionItem workingConditionItem = mapWorkingCondtionItem.get(y.identifier());
					mapWorkingCondItems.put(y.span(), workingConditionItem);
				});
				empWorkingCondItem.put(x.getEmployeeId(), mapWorkingCondItems);
			});
		}
		
		return ScheYearPrepareData.builder()
				.workScheduleWorkInfos(workScheduleWorkInfos)
				.attendanceItems(attendanceItems)
				.attendanceTimeOfMonthlies(attendanceTimeOfMonthlies)
				.listIntegrationDai(empDailyPerformance)
				.scheCondItems(condScheYears)
				.empWorkingCondItem(empWorkingCondItem)
				.build();
	}
	
	/**
	 * 1
	 * @param cid 会社ID　＝　Input．会社ID
	 * @param typeOfAttendanceItem 勤務項目の種類　＝　スケジュール
	 * @return List<勤怠項目コード、勤怠項目名称＞
	 */
	private Map<Integer, String> getScheYearAttendanceItems(String cid, int typeOfAttendanceItem) {
		return attendanceItemNameAdapter.getAttendanceItemNameAsMapName(cid, typeOfAttendanceItem);
	}
	
	/**
	 * 3
	 * @param lstSid List<社員ID> 
	 * @param dPeriod 期間．開始日　＝　Input．期間．開始月の開始日 & 期間．終了日　＝　Input．期間．終了月の終了日
	 * @return 日別勤怠：日別勤怠(Work)
	 */
	private List<IntegrationOfDaily> getEmpDailyPerformance(List<String> lstSid, DatePeriod dPeriod) {
		return dailyRecordShareFinder.findByListEmployeeId(lstSid, dPeriod);
	}
	
	/**
	 * 5
	 * @param cid Input．会社ID
	 * @param checkOptionalId Input．List＜スケジュール年間のエラーアラームチェックID＞
	 * @return List＜スケジュール年間のエラーアラーム＞
	 */
	private List<ExtractionCondScheduleYear> getCondScheYear(String contractCode, String cid, String checkOptionalId) {
		return condScheduleYearRepository.getScheAnyCond(contractCode, cid, checkOptionalId).stream()
				.filter(x -> x.isUse()).collect(Collectors.toList());
	}
	
	private void extractCondition(List<String> listSid, DatePeriod dPeriod, ScheYearPrepareData prepareData) {
		for (ExtractionCondScheduleYear condScheYear: prepareData.getScheCondItems()) {
			for (String sid: listSid) {
				// 総取得結果＝0
				int count = 0;
				
				List<GeneralDate> listDate = dPeriod.datesBetween();
				for(int day = 0; day < listDate.size(); day++) {
					GeneralDate exDate = listDate.get(day);
					
					// 日別勤怠を探す
					//条件：
					//　・社員ID　＝　ループ中の社員ID
					//　・ループ中の年月の開始日＜＝年月日＜＝ループ中の年月の終了日
					//【Output】
					//　・List＜日別勤怠＞
					Optional<IntegrationOfDaily> lstDaily = prepareData.getListIntegrationDai().stream()
							.filter(x -> x.getEmployeeId().equals(sid) && x.getYmd().equals(exDate))
							.findFirst();			
					IntegrationOfDaily integrationDaily = null;
					if(!lstDaily.isPresent()) {
						integrationDaily = lstDaily.get();
					}
					
					// 月別実績を探す
					//条件：
					//　・社員ID　＝　ループ中の社員ID
					//　・年月　＝　ループ中の年月
					//【Output】
					//　・月別実績
					Optional<AttendanceTimeOfMonthly> lstMonthly = prepareData.getAttendanceTimeOfMonthlies().stream()
							.filter(x -> x.getEmployeeId().equals(sid) && x.getYearMonth().equals(exDate.yearMonth()))
							.findFirst();
					AttendanceTimeOfMonthly attendanceTimeOfMonthly = null;
					if(!lstMonthly.isPresent()) {
						attendanceTimeOfMonthly = lstMonthly.get();
					}
					
					// 勤務予定を探す
					//条件：
					//　・社員ID　＝　ループ中の社員ID
					//　・ループ中の年月の開始日＜＝年月日＜＝ループ中の年月の終了日
					//【Output】
					//　・List＜勤務予定＞
					Optional<WorkScheduleWorkInforImport> workScheduleWorkInfosOpt = prepareData.getWorkScheduleWorkInfos().stream()
							.filter(x -> x.getEmployeeId().equals(sid) && x.getYmd().equals(exDate))
							.findFirst();
					WorkScheduleWorkInforImport workScheduleWorkInfos = null;
					if(!workScheduleWorkInfosOpt.isPresent()) {
						workScheduleWorkInfos = workScheduleWorkInfosOpt.get();
					}
					
					// チェック項目をチェック
					switch (condScheYear.getCheckItemType()) {
					case TIME:
						break;
						checkItemTime(cid, sid, wplId, period, condScheYear, attendanceTimeOfMonthly, prepareData, count);
					case DAY_NUMBER:
						break;
						checkItemDay(cid, sid, wplId, period, condScheYear, attendanceTimeOfMonthly, prepareData, count);
					default:
						break;
					}
					
					// 総取得結果　+＝　取得結果
				}
				
				// 条件をチェックする
				
				// 抽出結果詳細を作成
			}
			
			// 各チェック条件の結果を作成する
			// ・チェック種類　＝　自由チェック
			// ・コード　＝　ループ中のスケジュール年間の任意抽出条件．並び順
			// ・抽出結果　＝　作成した抽出結果

		}
	}
	
	// 日数チェック条件をチェック
	private void checkItemDay(
			String cid, String sid, String wplId, DatePeriod period, 
			ExtractionCondScheduleYear condScheYear,
			AttendanceTimeOfMonthly attendanceTimeOfMonthly,
			ScheYearPrepareData prepareData,
			int count) {
		DayCheckCond dayCheckCond = (DayCheckCond)condScheYear.getScheCheckConditions();
		switch (dayCheckCond.getTypeOfDays()) {
		case PUBLIC_HOLIDAY_NUMBER:
			// 期間内の公休残数を集計する
			//TODO QA
			break;
		case ANNUAL_LEAVE_NUMBER:
		case ACC_ANNUAL_LEAVE_NUMBER:
			// 期間中の年休積休残数を取得
			val require = requireService.createRequire();
			val cacheCarrier = new CacheCarrier();
			GeneralDate criteriaDate = GeneralDate.today();
			AggrResultOfAnnAndRsvLeave aggResult = GetAnnAndRsvRemNumWithinPeriod.algorithm(require, cacheCarrier,
                    cid, sid, period, InterimRemainMngMode.OTHER, criteriaDate,
                    false, false, Optional.of(false),
                    Optional.empty(), Optional.empty(), Optional.empty(),
                    Optional.empty(), Optional.empty(), Optional.empty());
			
			// 休暇日数を計算
			if (dayCheckCond.getTypeOfDays() == TypeOfDays.ANNUAL_LEAVE_NUMBER) {
				// 休暇日数　＝　取得した年休積休の集計結果．年休．年休情報(期間終了日時点)．使用日数
				count = aggResult.getAnnualLeave().get().getAsOfPeriodEnd().getUsedDays().v().intValue();
			} else {
				// 休暇日数　＝　取得した年休積休の集計結果．積立年休．積立年休情報(期間終了日時点)．使用日数
				count = aggResult.getReserveLeave().get().getAsOfPeriodEnd().getUsedDays().v().intValue();
			}
			break;
		default:
			// 出勤日数を計算
			// TODO QA 
			break;
		}
	}
	
	/*
	 * 時間チェック条件をチェック
	 */
	private void checkItemTime(
			String cid, String sid, String wplId, DatePeriod period, 
			ExtractionCondScheduleYear condScheYear,
			AttendanceTimeOfMonthly attendanceTimeOfMonthly,
			ScheYearPrepareData prepareData,
			int count) {
		// 当月より前の月かチェック
		
		// 予定時間＋総労働時間をチェック
		// 当月より前の月かチェック
		if (true) {
			count = attendanceTimeOfMonthly.getMonthlyCalculation().getTotalWorkingTime().v();
			return;
		}
		
		List<GeneralDate> listDate = period.datesBetween();
		for(int day = 0; day < listDate.size(); day++) {
			GeneralDate exDate = listDate.get(day);
			// システム日付＜＝ループ中年月日
			if (true) continue; //TODO
			
			// データを探す
			// ・勤務予定　＝　Input．List＜勤務予定＞から年月日　＝　ループ中の年月日を探す
			Optional<WorkScheduleWorkInforImport> workScheduleWorkInfosOpt = prepareData.getWorkScheduleWorkInfos()
					.stream().filter(x -> x.getYmd().equals(exDate)).findFirst();
			
			// ・日別実績　＝　Input．List＜日別実績＞から年月日　＝　ループ中の年月日を探す
			Optional<IntegrationOfDaily> integratoionOpt = prepareData.getListIntegrationDai()
					.stream().filter(x -> x.getYmd().equals(exDate)).findFirst();
			if (!workScheduleWorkInfosOpt.isPresent() && !integratoionOpt.isPresent()) {
				continue;
			}
			
			// 探したデータをチェック
			// システム日付＜＝ループ中年月日
			if (true) { //TODO
				// 総労働　を計算
				// 探した勤務予定　！＝　Empty　AND　探した勤務予定．勤怠時間　！＝　Empty
				// －＞　総労働　＋＝　探した勤務予定．勤怠時間．勤務時間．総労働時間．総労働時間
				if (workScheduleWorkInfosOpt.isPresent()) {
					if (workScheduleWorkInfosOpt.get().getAttendanceTimeWeeks() != null) {
						count += 0 //TODO 探した勤務予定．勤怠時間．勤務時間．総労働時間．総労働時間
					}
				}
			}
			
			//探した日別実績　！＝　Empty　AND　探した日別実績．勤怠時間　！＝　Empty
			//　－＞　総労働　＋＝　探した日別実績．勤怠時間．勤務時間．総労働時間．総労働時間
			if (integratoionOpt.isPresent()
					&& integratoionOpt.get().getAttendanceTimeOfDailyPerformance() != null) {
				count += integratoionOpt.get().getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime().getTotalTime().v();
			}
			
			//探した日別実績　＝＝　Empty　AND　探した勤務予定　！＝　Empty　AND　探した勤務予定．勤怠時間　！＝　Empty
			//　－＞　総労働　＋＝　探した勤務予定．勤怠時間．勤務時間．総労働時間．総労働時間
			if (!integratoionOpt.isPresent() && workScheduleWorkInfosOpt.isPresent() && workScheduleWorkInfosOpt.get().getAttendanceTimeWeeks() != null) {
				// TODO
			}
		}
		
	}
}
