package nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Optional;

import javax.inject.Inject;

import lombok.Getter;
import lombok.val;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.affiliation.AffiliationInfoOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.affiliation.AggregateAffiliationInfo;
import nts.uk.ctx.at.record.dom.monthly.anyitem.AggregateAnyItem;
import nts.uk.ctx.at.record.dom.monthly.anyitem.AnyItemOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.calc.MonthlyAggregateAtr;
import nts.uk.ctx.at.record.dom.monthly.calc.MonthlyCalculation;
import nts.uk.ctx.at.record.dom.monthly.vacation.ClosureStatus;
import nts.uk.ctx.at.record.dom.monthly.vacation.annualleave.AnnLeaRemNumEachMonth;
import nts.uk.ctx.at.record.dom.monthly.vacation.annualleave.AnnualLeaveAttdRateDays;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.MonthlyAggregationErrorInfo;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.excessoutside.ExcessOutsideWorkMng;
import nts.uk.ctx.at.record.dom.optitem.PerformanceAtr;
import nts.uk.ctx.at.record.dom.remainingnumber.absenceleave.temp.InterimAbsenceRecruitService;
import nts.uk.ctx.at.record.dom.remainingnumber.absenceleave.temp.TempAbsenceLeaveService;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.GetAnnAndRsvRemNumWithinPeriod;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.TempAnnualLeaveMngMode;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.param.AggrResultOfAnnAndRsvLeave;
import nts.uk.ctx.at.record.dom.remainingnumber.dayoff.temp.InterimBreakDayoffService;
import nts.uk.ctx.at.record.dom.remainingnumber.dayoff.temp.TempDayoffService;
import nts.uk.ctx.at.record.dom.weekly.AttendanceTimeOfWeekly;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.ErrMessageContent;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmployeeImport;
import nts.uk.ctx.at.shared.dom.common.WorkplaceId;
import nts.uk.ctx.at.shared.dom.common.anyitem.AnyAmountMonth;
import nts.uk.ctx.at.shared.dom.common.anyitem.AnyTimeMonth;
import nts.uk.ctx.at.shared.dom.common.anyitem.AnyTimesMonth;
import nts.uk.ctx.at.shared.dom.common.days.MonthlyDays;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.JobTitleId;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureDate;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 処理：ドメインサービス：月別実績を集計する
 * @author shuichu_ishida
 */
@Getter
public class AggregateMonthlyRecordServiceProc {
	/** 暫定代休・休出管理データ */
	@Inject
	private InterimBreakDayoffService interimBreakDayoffService;
	/** 暫定振休・振出管理データ */
	@Inject
	private InterimAbsenceRecruitService interimAbsRecService;
	/** 月別集計が必要とするリポジトリ */
	private RepositoriesRequiredByMonthlyAggr repositories;
	/** 期間中の年休積休残数を取得 */
	private GetAnnAndRsvRemNumWithinPeriod getAnnAndRsvRemNumWithinPeriod;
	/** （仮対応用）振休 */
	private TempAbsenceLeaveService tempAbsenceLeaveService;
	/** （仮対応用）代休 */
	private TempDayoffService tempDayoffService;
	
	/** 集計結果 */
	private AggregateMonthlyRecordValue aggregateResult;
	/** エラー情報 */
	private Map<String, MonthlyAggregationErrorInfo> errorInfos;

	/** 会社ID */
	private String companyId;
	/** 社員ID */
	private String employeeId;
	/** 年月 */
	private YearMonth yearMonth;
	/** 締めID */
	private ClosureId closureId;
	/** 締め日 */
	private ClosureDate closureDate;
	
	/** 月の計算中の日別実績データ */
	private MonthlyCalculatingDailys monthlyCalculatingDailys;
	/** 労働条件項目 */
	private List<WorkingConditionItem> workingConditionItems;
	/** 労働条件 */
	private Map<String, DatePeriod> workingConditions;
	/** 前回集計結果　（年休積立年休の集計結果） */
	private AggrResultOfAnnAndRsvLeave prevAggrResult;
	/** 週NO管理 */
	private Map<YearMonth, Integer> weekNoMap;
	/** 手修正あり */
	private boolean isRetouch;
	
	public AggregateMonthlyRecordServiceProc(
			RepositoriesRequiredByMonthlyAggr repositories,
			GetAnnAndRsvRemNumWithinPeriod getAnnAndRsvRemNumWithinPeriod,
			TempAbsenceLeaveService tempAbsenceLeaveService,
			TempDayoffService tempDayoffService){

		this.repositories = repositories;
		this.getAnnAndRsvRemNumWithinPeriod = getAnnAndRsvRemNumWithinPeriod;
		this.tempAbsenceLeaveService = tempAbsenceLeaveService;
		this.tempDayoffService = tempDayoffService;
	}
	
	/**
	 * 集計処理
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param yearMonth 年月
	 * @param closureId 締めID
	 * @param closureDate 締め日付
	 * @param datePeriod 期間
	 * @param prevAggrResult 前回集計結果　（年休積立年休の集計結果）
	 * @return 集計結果
	 */
	public AggregateMonthlyRecordValue aggregate(
			String companyId, String employeeId, YearMonth yearMonth,
			ClosureId closureId, ClosureDate closureDate, DatePeriod datePeriod,
			AggrResultOfAnnAndRsvLeave prevAggrResult) {
		
		this.aggregateResult = new AggregateMonthlyRecordValue();
		this.errorInfos = new HashMap<>();

		this.companyId = companyId;
		this.employeeId = employeeId;
		this.yearMonth = yearMonth;
		this.closureId = closureId;
		this.closureDate = closureDate;
		this.prevAggrResult = prevAggrResult;
		this.weekNoMap = new HashMap<>();
		this.isRetouch = false;
		
		// 社員を取得する
		EmployeeImport employee = null;
		employee = this.repositories.getEmpEmployee().findByEmpId(employeeId);
		if (employee == null){
			this.aggregateResult.addErrorInfos("001", new ErrMessageContent(TextResource.localize("Msg_1156")));
			return this.aggregateResult;
		}
		
		// 入社前、退職後を期間から除く　→　一か月の集計期間
		val termInOffice = new DatePeriod(employee.getEntryDate(), employee.getRetiredDate());
		DatePeriod monthPeriod = this.confirmProcPeriod(datePeriod, termInOffice);
		if (monthPeriod == null) {
			// 処理期間全体が、入社前または退職後の時
			return this.aggregateResult;
		}
		
		// 計算に必要なデータを準備する
		this.monthlyCalculatingDailys = this.prepareCaluculationDatas(monthPeriod);
		
		// 「労働条件項目」を取得
		List<WorkingConditionItem> workingConditionItems = this.repositories.getWorkingConditionItem()
				.getBySidAndPeriodOrderByStrD(employeeId, monthPeriod);
		if (workingConditionItems.isEmpty()){
			this.aggregateResult.addErrorInfos("001", new ErrMessageContent(TextResource.localize("Msg_430")));
			return this.aggregateResult;
		}
		
		// 同じ労働制の履歴を統合
		this.IntegrateHistoryOfSameWorkSys(workingConditionItems);
		
		// 所属情報の作成
		val affiliationInfo = this.createAffiliationInfo(monthPeriod);
		if (affiliationInfo == null) return this.aggregateResult;
		this.aggregateResult.getAffiliationInfoList().add(affiliationInfo);
		
		// 項目の数だけループ
		for (val workingConditionItem : this.workingConditionItems){

			// 「労働条件」の該当履歴から期間を取得
			val historyId = workingConditionItem.getHistoryId();
			if (!this.workingConditions.containsKey(historyId)) continue;

			// 処理期間を計算　（一か月の集計期間と労働条件履歴期間の重複を確認する）
			val term = this.workingConditions.get(historyId);
			DatePeriod aggrPeriod = this.confirmProcPeriod(monthPeriod, term);
			if (aggrPeriod == null) {
				// 履歴の期間と重複がない時
				continue;
			}
			
			// 月別実績の勤怠時間を集計
			val aggregateResult = this.aggregateAttendanceTime(aggrPeriod, workingConditionItem);
			val attendanceTime = aggregateResult.getAttendanceTime();
			if (attendanceTime == null) continue;
			
			// データを合算する
			val itrAttendanceTime = this.aggregateResult.getAttendanceTimeList().iterator();
			while (itrAttendanceTime.hasNext()){
				val calcedAttendanceTime = itrAttendanceTime.next();
				if (calcedAttendanceTime.equals(attendanceTime)){
					attendanceTime.sum(calcedAttendanceTime);
					itrAttendanceTime.remove();
				}
			}

			// 計算中のエラー情報の取得
			val monthlyCalculation = attendanceTime.getMonthlyCalculation();
			for (val errorInfo : monthlyCalculation.getErrorInfos()){
				this.errorInfos.putIfAbsent(errorInfo.getResourceId(), errorInfo);
			}
			
			// 計算結果を戻り値に蓄積
			this.aggregateResult.getAttendanceTimeList().add(aggregateResult.getAttendanceTime());
			this.aggregateResult.getAttendanceTimeWeeks().addAll(aggregateResult.getAttendanceTimeWeeks());
		}
		
		ListIterator<AttendanceTimeOfMonthly> itrAttencdanceTime =
				this.aggregateResult.getAttendanceTimeList().listIterator();
		while (itrAttencdanceTime.hasNext()){
			AttendanceTimeOfMonthly attendanceTime = itrAttencdanceTime.next();
			
			// 手修正された項目を元に戻す
			attendanceTime = this.undoRetouchValues(attendanceTime);
				
			// 手修正を戻してから計算必要な項目を再度計算
			if (this.isRetouch) itrAttencdanceTime.set(this.recalcAttendanceTime(attendanceTime));
		}
		
		// 36協定時間の集計
		MonthlyCalculation monthlyCalculationForAgreement = new MonthlyCalculation();
		val agreementTimeOpt = monthlyCalculationForAgreement.aggregateAgreementTime(
				this.companyId, this.employeeId, this.yearMonth, this.closureId, this.closureDate,
				monthPeriod,
				Optional.of(new ArrayList<>(this.monthlyCalculatingDailys.getAttendanceTimeOfDailyMap().values())),
				Optional.empty(), Optional.empty(), this.repositories);
		if (agreementTimeOpt.isPresent()){
			val agreementTime = agreementTimeOpt.get();
			val agreementTimeList = this.aggregateResult.getAgreementTimeList();
			agreementTimeList.removeIf(c -> { return (c.getYearMonth() == agreementTime.getYearMonth());});
			agreementTimeList.add(agreementTime);
		}
		
		// 残数処理
		this.remainingProcess(monthPeriod);
		
		// 月別実績の任意項目を集計
		this.aggregateAnyItem(monthPeriod);
		
		// 大塚カスタマイズ
		this.customizeForOtsuka();
		
		// 戻り値にエラー情報を移送
		for (val errorInfo : this.errorInfos.values()){
			this.aggregateResult.getErrorInfos().putIfAbsent(errorInfo.getResourceId(), errorInfo);
		}
		
		return this.aggregateResult;
	}
	
	/**
	 * 月の計算中データを準備する
	 * @param period 期間
	 * @return 月の計算中データ
	 */
	private MonthlyCalculatingDailys prepareCaluculationDatas(DatePeriod period){
		
		MonthlyCalculatingDailys result = new MonthlyCalculatingDailys();

		// 取得期間を　開始日-6日～終了日　とする　（前月の最終週の集計のため）
		DatePeriod findPeriod = new DatePeriod(period.start().addDays(-6), period.end());
		
		// 日別実績の勤怠時間　取得
		val attendanceTimeOfDailyList =
				this.repositories.getAttendanceTimeOfDaily().findByPeriodOrderByYmd(this.employeeId, findPeriod);
		for (val attendanceTimeOfDaily : attendanceTimeOfDailyList){
			result.getAttendanceTimeOfDailyMap().putIfAbsent(attendanceTimeOfDaily.getYmd(), attendanceTimeOfDaily);
		}
		
		// 日別実績の勤務情報　取得
		val workInfoOfDailyList =
				this.repositories.getWorkInformationOfDaily().findByPeriodOrderByYmd(this.employeeId, findPeriod);
		for (val workInfoOfDaily : workInfoOfDailyList){
			result.getWorkInfoOfDailyMap().putIfAbsent(workInfoOfDaily.getYmd(), workInfoOfDaily);
		}
		
		// 日別実績の出退勤　取得
		val timeLeaveOfDailyList =
				this.repositories.getTimeLeavingOfDaily().findbyPeriodOrderByYmd(this.employeeId, findPeriod);
		for (val timeLeaveOfDaily : timeLeaveOfDailyList){
			result.getTimeLeaveOfDailyMap().putIfAbsent(timeLeaveOfDaily.getYmd(), timeLeaveOfDaily);
		}
		
		return result;
	}
	
	/**
	 * 同じ労働制の履歴を統合
	 * @param target 労働条件項目リスト　（統合前）
	 * @return 労働条件項目リスト　（統合後）
	 */
	private void IntegrateHistoryOfSameWorkSys(List<WorkingConditionItem> target){

		this.workingConditionItems = new ArrayList<>();
		this.workingConditions = new HashMap<>();
		
		val itrTarget = target.listIterator();
		while (itrTarget.hasNext()){
			
			// 要素[n]を取得
			WorkingConditionItem startItem = itrTarget.next();
			val startHistoryId = startItem.getHistoryId();
			val startConditionOpt = this.repositories.getWorkingCondition().getByHistoryId(startHistoryId);
			if (!startConditionOpt.isPresent()) continue;
			val startCondition = startConditionOpt.get();
			if (startCondition.getDateHistoryItem().isEmpty()) continue;
			DatePeriod startPeriod = startCondition.getDateHistoryItem().get(0).span();
			
			// 要素[n]と要素[n+1]以降を順次比較
			WorkingConditionItem endItem = null;
			while (itrTarget.hasNext()){
				WorkingConditionItem nextItem = target.get(itrTarget.nextIndex());
				if (startItem.getLaborSystem() != nextItem.getLaborSystem() ||
					startItem.getHourlyPaymentAtr() != nextItem.getHourlyPaymentAtr()){
					
					// 労働制または時給者区分が異なる履歴が見つかった時点で、労働条件の統合をやめる
					break;
				}
			
				// 労働制と時給者区分が同じ履歴の要素を順次取得
				endItem = itrTarget.next();
			}
			
			// 次の要素がなくなった、または、異なる履歴が見つかれば、集計要素を確定する
			if (endItem == null){
				this.workingConditionItems.add(startItem);
				this.workingConditions.putIfAbsent(startHistoryId, startPeriod);
				continue;
			}
			val endHistoryId = endItem.getHistoryId();
			val endConditionOpt = this.repositories.getWorkingCondition().getByHistoryId(endHistoryId);
			if (!endConditionOpt.isPresent()) continue;;
			val endCondition = endConditionOpt.get();
			if (endCondition.getDateHistoryItem().isEmpty()) continue;
			this.workingConditionItems.add(endItem);
			this.workingConditions.putIfAbsent(endHistoryId,
					new DatePeriod(startPeriod.start(), endCondition.getDateHistoryItem().get(0).end()));
		}
	}
	
	/**
	 * 月別実績の勤怠時間を集計
	 * @param datePeriod 期間
	 * @param workingConditionItem 労働条件項目
	 * @return 月別実績の勤怠時間
	 */
	private AggregateAttendanceTimeValue aggregateAttendanceTime(
			DatePeriod datePeriod,
			WorkingConditionItem workingConditionItem){
		
		AggregateAttendanceTimeValue result = new AggregateAttendanceTimeValue();
		
		// 週Noを確認する
		this.weekNoMap.putIfAbsent(this.yearMonth, 0);
		val startWeekNo = this.weekNoMap.get(this.yearMonth) + 1;
		
		// 労働制を確認する
		val workingSystem = workingConditionItem.getLaborSystem();
		
		// 月別実績の勤怠時間　初期設定
		val attendanceTime = new AttendanceTimeOfMonthly(
				this.employeeId, this.yearMonth, this.closureId, this.closureDate, datePeriod);
		attendanceTime.prepareAggregation(this.companyId, datePeriod, workingConditionItem,
				startWeekNo, this.monthlyCalculatingDailys, this.repositories);
		val monthlyCalculation = attendanceTime.getMonthlyCalculation();
		if (monthlyCalculation.getErrorInfos().size() > 0) {
			for (val errorInfo : monthlyCalculation.getErrorInfos()){
				this.errorInfos.putIfAbsent(errorInfo.getResourceId(), errorInfo);
			}
			return result;
		}
		
		// 月の計算
		monthlyCalculation.aggregate(datePeriod, MonthlyAggregateAtr.MONTHLY,
				Optional.empty(), Optional.empty(), this.repositories);
		
		// 縦計
		{
			// 週単位の期間を取得
			for (val attendanceTimeWeek : attendanceTime.getMonthlyCalculation().getAttendanceTimeWeeks()){
				DatePeriod weekPeriod = attendanceTimeWeek.getPeriod();
				
				// 週の縦計
				val verticalTotalWeek = attendanceTimeWeek.getVerticalTotal();
				verticalTotalWeek.verticalTotal(this.companyId, this.employeeId, weekPeriod, workingSystem,
						this.monthlyCalculatingDailys, this.repositories);
			}
			
			// 月の縦計
			val verticalTotal = attendanceTime.getVerticalTotal();
			verticalTotal.verticalTotal(this.companyId, this.employeeId, datePeriod, workingSystem,
					this.monthlyCalculatingDailys, this.repositories);
		}
		
		// 時間外超過
		ExcessOutsideWorkMng excessOutsideWorkMng = new ExcessOutsideWorkMng(monthlyCalculation);
		excessOutsideWorkMng.aggregate(this.repositories);
		if (excessOutsideWorkMng.getErrorInfos().size() > 0) {
			for (val errorInfo : excessOutsideWorkMng.getErrorInfos()){
				this.errorInfos.putIfAbsent(errorInfo.getResourceId(), errorInfo);
			}
		}
		attendanceTime.setExcessOutsideWork(excessOutsideWorkMng.getExcessOutsideWork());

		// 回数集計
		{
			// 勤務種類リストを取得する
			val workTypeList = this.repositories.getWorkType().findByCompanyId(this.companyId);
			
			// 週単位の期間を取得
			for (val attendanceTimeWeek : attendanceTime.getMonthlyCalculation().getAttendanceTimeWeeks()){
				DatePeriod weekPeriod = attendanceTimeWeek.getPeriod();
				
				// 週の回数集計
				val totalCountWeek = attendanceTimeWeek.getTotalCount();
				totalCountWeek.totalize(this.companyId, this.employeeId, weekPeriod,
						this.monthlyCalculatingDailys, workTypeList, this.repositories);
			}
			
			// 月の回数集計
			val totalCount = attendanceTime.getTotalCount();
			totalCount.totalize(this.companyId, this.employeeId, datePeriod,
					this.monthlyCalculatingDailys, workTypeList, this.repositories);
		}
		
		// 集計結果を返す
		result.setAttendanceTime(attendanceTime);
		for (val attendanceTimeWeek : attendanceTime.getMonthlyCalculation().getAttendanceTimeWeeks()){
			val nowWeekNo = this.weekNoMap.get(this.yearMonth);
			if (nowWeekNo < attendanceTimeWeek.getWeekNo()){
				this.weekNoMap.put(this.yearMonth, attendanceTimeWeek.getWeekNo());
			}
			result.getAttendanceTimeWeeks().add(attendanceTimeWeek);
		}
		return result;
	}

	/**
	 * 月別実績の任意項目を集計
	 * @param monthPeriod 月の期間
	 */
	private void aggregateAnyItem(DatePeriod monthPeriod){

		// 週単位の期間を取得
		ListIterator<AttendanceTimeOfWeekly> itrWeeks =
				this.aggregateResult.getAttendanceTimeWeeks().listIterator();
		while (itrWeeks.hasNext()){
			AttendanceTimeOfWeekly attendanceTimeWeek = itrWeeks.next();
			
			// 週間集計
			itrWeeks.set(this.aggregateAnyItemWeek(attendanceTimeWeek));
		}
		
		// 月間集計
		this.aggregateAnyItemMonth(monthPeriod);
	}
	
	/**
	 * 週間集計
	 * @param period 期間
	 */
	private AttendanceTimeOfWeekly aggregateAnyItemWeek(AttendanceTimeOfWeekly attendanceTimeWeek){
		
		// 日別実績の任意項目の取得
		DatePeriod period = attendanceTimeWeek.getPeriod();
		List<String> employeeIds = new ArrayList<>();
		employeeIds.add(this.employeeId);
		val anyItemValueOfDailys = this.repositories.getAnyItemValueOfDaily().finds(employeeIds, period);
		
		// 任意項目ごとに集計する
		Map<Integer, AggregateAnyItem> anyItemTotals = new HashMap<>();
		for (val anyItemValueOfDaily : anyItemValueOfDailys){
			if (anyItemValueOfDaily.getItems() == null) continue;
			val ymd = anyItemValueOfDaily.getYmd();
			for (val item : anyItemValueOfDaily.getItems()){
				if (item.getItemNo() == null) continue;
				Integer itemNo = item.getItemNo().v();
				
				if (period.contains(ymd)){
					anyItemTotals.putIfAbsent(itemNo, new AggregateAnyItem(itemNo));
					anyItemTotals.get(itemNo).addFromDaily(item);
				}
			}
		}
		
		// 任意項目を取得
		val optionalItems = this.repositories.getOptionalItem().findAll(this.companyId);
		for (val optionalItem : optionalItems){
			Integer optionalItemNo = optionalItem.getOptionalItemNo().v();
			
			// 利用条件の判定
			//*****（未）　作成中　→　高須さん
			{

				// 属性に応じて初期化
				AnyTimeMonth anyTime = null;
				AnyTimesMonth anyTimes = null;
				AnyAmountMonth anyAmount = null;
				switch (optionalItem.getOptionalItemAtr()){
				case TIME:
					anyTime = new AnyTimeMonth(0);
					break;
				case NUMBER:
					anyTimes = new AnyTimesMonth(0.0);
					break;
				case AMOUNT:
					anyAmount = new AnyAmountMonth(0);
					break;
				}
				
				// 縦計処理
				if (anyItemTotals.containsKey(optionalItemNo)){
					val anyItemTotal = anyItemTotals.get(optionalItemNo);
					if (anyItemTotal.getTime().isPresent()){
						if (anyTime == null) anyTime = new AnyTimeMonth(0);
						anyTime = anyTime.addMinutes(anyItemTotal.getTime().get().v());
					}
					if (anyItemTotal.getTimes().isPresent()){
						if (anyTimes == null) anyTimes = new AnyTimesMonth(0.0);
						anyTimes = anyTimes.addTimes(anyItemTotal.getTimes().get().v().doubleValue());
					}
					if (anyItemTotal.getAmount().isPresent()){
						if (anyAmount == null) anyAmount = new AnyAmountMonth(0);
						anyAmount = anyAmount.addAmount(anyItemTotal.getAmount().get().v());
					}
				}
				
				// 集計任意項目を返す
				attendanceTimeWeek.getAnyItem().getAnyItemValues().put(optionalItemNo, AggregateAnyItem.of(
						optionalItemNo, anyTime, anyTimes, anyAmount));
			}
		}
		
		return attendanceTimeWeek;
	}
	
	/**
	 * 月間集計
	 * @param period 期間
	 */
	private void aggregateAnyItemMonth(DatePeriod period){
		
		// 日別実績の任意項目の取得
		List<String> employeeIds = new ArrayList<>();
		employeeIds.add(this.employeeId);
		val anyItemValueOfDailys = this.repositories.getAnyItemValueOfDaily().finds(employeeIds, period);
		
		// 任意項目ごとに集計する
		Map<Integer, AggregateAnyItem> anyItemTotals = new HashMap<>();
		for (val anyItemValueOfDaily : anyItemValueOfDailys){
			if (anyItemValueOfDaily.getItems() == null) continue;
			val ymd = anyItemValueOfDaily.getYmd();
			for (val item : anyItemValueOfDaily.getItems()){
				if (item.getItemNo() == null) continue;
				Integer itemNo = item.getItemNo().v();
				
				if (period.contains(ymd)){
					anyItemTotals.putIfAbsent(itemNo, new AggregateAnyItem(itemNo));
					anyItemTotals.get(itemNo).addFromDaily(item);
				}
			}
		}
		
		// 任意項目を取得
		val optionalItems = this.repositories.getOptionalItem().findAll(this.companyId);
		for (val optionalItem : optionalItems){
			Integer optionalItemNo = optionalItem.getOptionalItemNo().v();

			// 利用条件の判定
			//*****（未）　作成中　→　高須さん
			{
				
				// 属性に応じて初期化
				AnyTimeMonth anyTime = null;
				AnyTimesMonth anyTimes = null;
				AnyAmountMonth anyAmount = null;
				switch (optionalItem.getOptionalItemAtr()){
				case TIME:
					anyTime = new AnyTimeMonth(0);
					break;
				case NUMBER:
					anyTimes = new AnyTimesMonth(0.0);
					break;
				case AMOUNT:
					anyAmount = new AnyAmountMonth(0);
					break;
				}
				
				// 「実績区分」を判断
				if (optionalItem.getPerformanceAtr() == PerformanceAtr.DAILY_PERFORMANCE){

					// 日別実績　縦計処理
					if (anyItemTotals.containsKey(optionalItemNo)){
						val anyItemTotal = anyItemTotals.get(optionalItemNo);
						if (anyItemTotal.getTime().isPresent()){
							if (anyTime == null) anyTime = new AnyTimeMonth(0);
							anyTime = anyTime.addMinutes(anyItemTotal.getTime().get().v());
						}
						if (anyItemTotal.getTimes().isPresent()){
							if (anyTimes == null) anyTimes = new AnyTimesMonth(0.0);
							anyTimes = anyTimes.addTimes(anyItemTotal.getTimes().get().v().doubleValue());
						}
						if (anyItemTotal.getAmount().isPresent()){
							if (anyAmount == null) anyAmount = new AnyAmountMonth(0);
							anyAmount = anyAmount.addAmount(anyItemTotal.getAmount().get().v());
						}
					}
				}
				else {
					
					// 月別実績　計算処理
					//*****（未）　作成中　→　高須さん
				}
				
				// 月別実績の任意項目を返す
				this.aggregateResult.putAnyItemOrUpdate(AnyItemOfMonthly.of(
						this.employeeId, this.yearMonth, this.closureId, this.closureDate, optionalItemNo,
						Optional.ofNullable(anyTime), Optional.ofNullable(anyTimes), Optional.ofNullable(anyAmount)));
			}
		}
	}
	
	/**
	 * 手修正された項目を元に戻す
	 * @param attendanceTime 月別実績の勤怠時間
	 * @return 月別実績の勤怠時間
	 */
	private AttendanceTimeOfMonthly undoRetouchValues(AttendanceTimeOfMonthly attendanceTime){

		this.isRetouch = false;
		
		// 既存データを読み込む
		val oldDataOpt = this.repositories.getAttendanceTimeOfMonthly().find(
				this.employeeId, this.yearMonth, this.closureId, this.closureDate);
		if (!oldDataOpt.isPresent()) return attendanceTime;
		val monthlyConverter = this.repositories.getAttendanceItemConverter().createMonthlyConverter();
		val oldItemConvert = monthlyConverter.withAttendanceTime(oldDataOpt.get());

		// 計算後データを確認
		val convert = monthlyConverter.withAttendanceTime(attendanceTime);
		
		// 月別実績の編集状態を取得
		
		{
			
			// 勤怠項目IDから項目を判断
			
			// 該当する勤怠項目IDの値を計算前に戻す
			
		}
		
		return attendanceTime;
	}

	/**
	 * 手修正を戻してから計算必要な項目を再度計算
	 * @param attendanceTime 月別実績の勤怠時間
	 * @return 月別実績の勤怠時間
	 */
	private AttendanceTimeOfMonthly recalcAttendanceTime(AttendanceTimeOfMonthly attendanceTime){
	
		val monthlyCalculation = attendanceTime.getMonthlyCalculation();
		
		// 残業合計時間を集計する
		monthlyCalculation.getAggregateTime().getOverTime().recalcTotal();
		
		// 休出合計時間を集計する
		monthlyCalculation.getAggregateTime().getHolidayWorkTime().recalcTotal();

		// 総労働時間と36協定時間の再計算
		monthlyCalculation.recalcTotalAndAgreement(attendanceTime.getDatePeriod(),
				MonthlyAggregateAtr.MONTHLY, this.repositories);
		
		return attendanceTime;
	}
	
	/**
	 * 残数処理
	 * @param period 期間
	 */
	private void remainingProcess(DatePeriod period){
		
		// 年休、積休
		this.annualAndReserveLeaveRemain(period);
		
		// 振休（仮対応）
		this.absenceLeaveRemain_temp(period);
		
		// 代休（仮対応）
		this.dayoffRemain_temp(period);
	}
	
	/**
	 * 年休、積休
	 * @param period 期間
	 */
	private void annualAndReserveLeaveRemain(DatePeriod period){

		// 期間中の年休積休残数を取得
		val aggrResult = this.getAnnAndRsvRemNumWithinPeriod.algorithm(
				this.companyId, this.employeeId, period, TempAnnualLeaveMngMode.MONTHLY,
				period.end(), false, true, Optional.of(false), Optional.empty(), Optional.empty(),
				this.prevAggrResult.getAnnualLeave(), this.prevAggrResult.getReserveLeave());
		
		if (aggrResult.getAnnualLeave().isPresent()){
			val asOfPeriodEnd = aggrResult.getAnnualLeave().get().getAsOfPeriodEnd();
			val remainingNumber = asOfPeriodEnd.getRemainingNumber();
			
			// 年休月別残数データを更新
			AnnLeaRemNumEachMonth annLeaRemNum = AnnLeaRemNumEachMonth.of(
					this.employeeId,
					this.yearMonth,
					this.closureId,
					this.closureDate,
					period,
					ClosureStatus.UNTREATED,
					remainingNumber.getAnnualLeaveNoMinus(),
					remainingNumber.getAnnualLeaveWithMinus(),
					remainingNumber.getHalfDayAnnualLeaveNoMinus(),
					remainingNumber.getHalfDayAnnualLeaveWithMinus(),
					asOfPeriodEnd.getGrantInfo(),
					remainingNumber.getTimeAnnualLeaveNoMinus(),
					remainingNumber.getTimeAnnualLeaveWithMinus(),
					AnnualLeaveAttdRateDays.of(
							new MonthlyDays(0.0),
							new MonthlyDays(0.0),
							new MonthlyDays(0.0)),
					asOfPeriodEnd.isAfterGrantAtr());
			this.aggregateResult.getAnnLeaRemNumEachMonthList().add(annLeaRemNum);
			
			// 年休エラー処理
			for (val annualLeaveError : aggrResult.getAnnualLeave().get().getAnnualLeaveErrors()){
				MonthlyAggregationErrorInfo errorInfo = null;
				//*****（未）　エラーに対して、対応するメッセージを追加する必要がある。未設計。2018.5.6 shuichi_ishida
				switch (annualLeaveError){
				case SHORTAGE_AL_OF_UNIT_DAY_BFR_GRANT:
				case SHORTAGE_AL_OF_UNIT_DAY_AFT_GRANT:
				case SHORTAGE_TIMEAL_BEFORE_GRANT:
				case SHORTAGE_TIMEAL_AFTER_GRANT:
				case EXCESS_MAX_TIMEAL_BEFORE_GRANT:
				case EXCESS_MAX_TIMEAL_AFTER_GRANT:
					errorInfo = new MonthlyAggregationErrorInfo(
							"XXX", new ErrMessageContent(TextResource.localize("Msg_XXX")));
					break;
				}
				if (errorInfo != null) this.errorInfos.putIfAbsent("XXX", errorInfo);
			}
		}
		
		// 集計結果を前回集計結果に引き継ぐ
		this.aggregateResult.setAggrResultOfAnnAndRsvLeave(aggrResult);
	}
	
	/**
	 * （仮対応用）振休
	 * @param period 期間
	 */
	private void absenceLeaveRemain_temp(DatePeriod period){
		
		this.aggregateResult.getAbsenceLeaveRemainList().add(
				this.tempAbsenceLeaveService.algorithm(this.companyId, this.employeeId, this.yearMonth,
						period, this.closureId, this.closureDate));
		// 暫定振休・振出管理データを削除
		this.interimAbsRecService.remove(employeeId, period);
	}
	
	/**
	 * （仮対応用）代休
	 * @param period 期間
	 */
	private void dayoffRemain_temp(DatePeriod period){
		
		this.aggregateResult.getMonthlyDayoffRemainList().add(
				this.tempDayoffService.algorithm(this.companyId, this.employeeId, this.yearMonth,
						period, this.closureId, this.closureDate));
		// 暫定代休・休出管理データを削除
		this.interimBreakDayoffService.remove(employeeId, period);
	}
	
	/**
	 * 大塚カスタマイズ
	 */
	private void customizeForOtsuka(){
		
		// 時短日割適用日数
		this.TimeSavingDailyRateApplyDays();
	}
	
	/**
	 * 処理期間との重複を確認する　（重複期間を取り出す）
	 * @param target 処理期間
	 * @param comparison 比較対象期間
	 * @return 重複期間　（null = 重複なし）
	 */
	private DatePeriod confirmProcPeriod(DatePeriod target, DatePeriod comparison){

		DatePeriod overlap = null;		// 重複期間
		
		// 開始前
		if (target.isBefore(comparison)) return overlap;
		
		// 終了後
		if (target.isAfter(comparison)) return overlap;
		
		// 重複あり
		overlap = target;
		
		// 開始日より前を除外
		if (overlap.contains(comparison.start())){
			overlap = overlap.cutOffWithNewStart(comparison.start());
		}
		
		// 終了日より後を除外
		if (overlap.contains(comparison.end())){
			overlap = overlap.cutOffWithNewEnd(comparison.end());
		}

		return overlap;
	}
	
	/**
	 * 所属情報の作成
	 * @param datePeriod 期間
	 * @return 月別実績の所属情報
	 */
	private AffiliationInfoOfMonthly createAffiliationInfo(DatePeriod datePeriod){
		
		// 月初の所属情報を取得
		val firstInfoOfDailyOpt = this.repositories.getAffiliationInfoOfDaily().findByKey(
				this.employeeId, datePeriod.start());
		if (!firstInfoOfDailyOpt.isPresent()){
			val errorInfo = new MonthlyAggregationErrorInfo(
					"003", new ErrMessageContent(TextResource.localize("Msg_1157")));
			this.errorInfos.putIfAbsent(errorInfo.getResourceId(), errorInfo);
			return null;
		}
		val firstInfoOfDaily = firstInfoOfDailyOpt.get();
		val firstWorkTypeOfDailyOpt = this.repositories.getWorkTypeOfDaily().findByKey(
				this.employeeId, datePeriod.start());
		if (!firstWorkTypeOfDailyOpt.isPresent()){
			val errorInfo = new MonthlyAggregationErrorInfo(
					"003", new ErrMessageContent(TextResource.localize("Msg_1157")));
			this.errorInfos.putIfAbsent(errorInfo.getResourceId(), errorInfo);
			return null;
		}
		val firstWorkTypeOfDaily = firstWorkTypeOfDailyOpt.get();
		
		// 月初の情報を作成
		val firstInfo = AggregateAffiliationInfo.of(
				firstInfoOfDaily.getEmploymentCode(),
				new WorkplaceId(firstInfoOfDaily.getWplID()),
				new JobTitleId(firstInfoOfDaily.getJobTitleID()),
				firstInfoOfDaily.getClsCode(),
				firstWorkTypeOfDaily.getWorkTypeCode());

		// 月末がシステム日付以降の場合、月初の情報を月末の情報とする
		if (datePeriod.end().after(GeneralDate.today())){

			// 月別実績の所属情報を返す
			return AffiliationInfoOfMonthly.of(this.employeeId, this.yearMonth, this.closureId, this.closureDate,
					firstInfo, firstInfo);
		}
		
		// 月末の所属情報を取得
		val lastInfoOfDailyOpt = this.repositories.getAffiliationInfoOfDaily().findByKey(
				this.employeeId, datePeriod.end());
		if (!lastInfoOfDailyOpt.isPresent()){
			val errorInfo = new MonthlyAggregationErrorInfo(
					"004", new ErrMessageContent(TextResource.localize("Msg_1157")));
			this.errorInfos.putIfAbsent(errorInfo.getResourceId(), errorInfo);
			return null;
		}
		val lastInfoOfDaily = lastInfoOfDailyOpt.get();
		val lastWorkTypeOfDailyOpt = this.repositories.getWorkTypeOfDaily().findByKey(
				this.employeeId, datePeriod.end());
		if (!lastWorkTypeOfDailyOpt.isPresent()){
			val errorInfo = new MonthlyAggregationErrorInfo(
					"004", new ErrMessageContent(TextResource.localize("Msg_1157")));
			this.errorInfos.putIfAbsent(errorInfo.getResourceId(), errorInfo);
			return null;
		}
		val lastWorkTypeOfDaily = lastWorkTypeOfDailyOpt.get();

		// 月末の情報を作成
		val lastInfo = AggregateAffiliationInfo.of(
				lastInfoOfDaily.getEmploymentCode(),
				new WorkplaceId(lastInfoOfDaily.getWplID()),
				new JobTitleId(lastInfoOfDaily.getJobTitleID()),
				lastInfoOfDaily.getClsCode(),
				lastWorkTypeOfDaily.getWorkTypeCode());
		
		// 月別実績の所属情報を返す
		return AffiliationInfoOfMonthly.of(this.employeeId, this.yearMonth, this.closureId, this.closureDate,
				firstInfo, lastInfo);
	}
	
	/**
	 * 時短日割適用日数
	 */
	private void TimeSavingDailyRateApplyDays(){
		
		// 月別実績の所属情報を取得
		val affiliationInfoOpt = this.aggregateResult.getAffiliationInfo(
				this.employeeId, this.yearMonth, this.closureId, this.closureDate);
		if (!affiliationInfoOpt.isPresent()) return;
		
		// 月末の勤務情報を判断
		val lastInfo = affiliationInfoOpt.get().getLastInfo();
		if (lastInfo.getBusinessTypeCd().v().compareTo("0000002030") == 0){
			
			// 任意項目50にセット
			this.aggregateResult.putAnyItemOrUpdate(AnyItemOfMonthly.of(
					this.employeeId, this.yearMonth, this.closureId, this.closureDate,
					50, Optional.empty(), Optional.of(new AnyTimesMonth(20.67)), Optional.empty()));
		}
	}
}
