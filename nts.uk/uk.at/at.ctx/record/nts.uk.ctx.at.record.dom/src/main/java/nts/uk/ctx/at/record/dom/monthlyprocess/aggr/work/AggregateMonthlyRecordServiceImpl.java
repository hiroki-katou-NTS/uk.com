package nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.time.YearMonth;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.com.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.affiliation.AffiliationInfoOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.affiliation.AggregateAffiliationInfo;
import nts.uk.ctx.at.record.dom.monthly.anyitem.AnyAmountMonth;
import nts.uk.ctx.at.record.dom.monthly.anyitem.AnyItemOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.anyitem.AnyTimeMonth;
import nts.uk.ctx.at.record.dom.monthly.anyitem.AnyTimesMonth;
import nts.uk.ctx.at.record.dom.monthly.calc.MonthlyAggregateAtr;
import nts.uk.ctx.at.record.dom.monthly.calc.MonthlyCalculation;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.MonthlyAggregationErrorInfo;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.excessoutside.ExcessOutsideWorkMng;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.ErrMessageContent;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmployeeImport;
import nts.uk.ctx.at.shared.dom.common.WorkplaceId;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.JobTitleId;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureDate;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;

/**
 * ドメインサービス：月別実績を集計する
 * @author shuichi_ishida
 */
@Stateless
public class AggregateMonthlyRecordServiceImpl implements AggregateMonthlyRecordService {

	/** 月別集計が必要とするリポジトリ */
	@Inject
	private RepositoriesRequiredByMonthlyAggr repositories;
	
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
	
	/** 労働条件項目 */
	private List<WorkingConditionItem> workingConditionItems;
	/** 労働条件 */
	private Map<String, DatePeriod> workingConditions;
	
	/**
	 * 集計処理　（アルゴリズム）
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param yearMonth 年月
	 * @param closureId 締めID
	 * @param closureDate 締め日付
	 * @param datePeriod 期間
	 * @return 集計結果
	 */
	@Override
	public AggregateMonthlyRecordValue aggregate(String companyId, String employeeId, YearMonth yearMonth,
			ClosureId closureId, ClosureDate closureDate, DatePeriod datePeriod) {
		
		this.aggregateResult = new AggregateMonthlyRecordValue();
		this.errorInfos = new HashMap<>();

		this.companyId = companyId;
		this.employeeId = employeeId;
		this.yearMonth = yearMonth;
		this.closureId = closureId;
		this.closureDate = closureDate;
		
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
			val attendanceTime = this.aggregateAttendanceTime(aggrPeriod, workingConditionItem);
			if (attendanceTime == null) continue;
			
			// 月別実績の任意項目を集計
			this.aggregateAnyItem();
			
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
			this.aggregateResult.getAttendanceTimeList().add(attendanceTime);
		}
		
		if (this.workingConditionItems.size() > 0){
			val workingConditionItem = this.workingConditionItems.get(this.workingConditionItems.size() - 1);
			
			// 36協定時間の集計
			MonthlyCalculation monthlyCalculationForAgreement = new MonthlyCalculation();
			val agreementTimeOpt = monthlyCalculationForAgreement.aggregateAgreementTime(
					this.companyId, this.employeeId, this.yearMonth, this.closureId, this.closureDate, monthPeriod,
					workingConditionItem, Optional.empty(), this.repositories);
			if (agreementTimeOpt.isPresent()){
				val agreementTime = agreementTimeOpt.get();
				val agreementTimeList = this.aggregateResult.getAgreementTimeList();
				agreementTimeList.removeIf(c -> { return (c.getYearMonth() == agreementTime.getYearMonth());});
				agreementTimeList.add(agreementTime);
			}
		}
		
		// 残数処理
		
		// 大塚カスタマイズ
		this.customizeForOtsuka();
		
		// 戻り値にエラー情報を移送
		for (val errorInfo : this.errorInfos.values()){
			this.aggregateResult.getErrorInfos().putIfAbsent(errorInfo.getResourceId(), errorInfo);
		}
		
		return this.aggregateResult;
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
	private AttendanceTimeOfMonthly aggregateAttendanceTime(
			DatePeriod datePeriod,
			WorkingConditionItem workingConditionItem){
		
		// 労働制を確認する
		val workingSystem = workingConditionItem.getLaborSystem();
		
		// 月別実績の勤怠時間　初期設定
		val attendanceTime = new AttendanceTimeOfMonthly(
				this.employeeId, this.yearMonth, this.closureId, this.closureDate, datePeriod);
		attendanceTime.prepareAggregation(companyId, datePeriod, workingConditionItem, this.repositories);
		val monthlyCalculation = attendanceTime.getMonthlyCalculation();
		if (monthlyCalculation.getErrorInfos().size() > 0) {
			for (val errorInfo : monthlyCalculation.getErrorInfos()){
				this.errorInfos.putIfAbsent(errorInfo.getResourceId(), errorInfo);
			}
			return null;
		}
		
		// 月の計算
		monthlyCalculation.aggregate(datePeriod, MonthlyAggregateAtr.MONTHLY,
				Optional.empty(), Optional.empty(), this.repositories);
		
		// 縦計
		val verticalTotal = attendanceTime.getVerticalTotal();
		verticalTotal.verticalTotal(this.companyId, this.employeeId, datePeriod, workingSystem, this.repositories);
		
		// 時間外超過
		ExcessOutsideWorkMng excessOutsideWorkMng = new ExcessOutsideWorkMng(monthlyCalculation);
		excessOutsideWorkMng.aggregate(this.repositories);
		attendanceTime.setExcessOutsideWork(excessOutsideWorkMng.getExcessOutsideWork());

		// 回数集計
		
		// 月別実績の勤怠時間を返す
		return attendanceTime;
	}
	
	/**
	 * 月別実績の任意項目を集計
	 */
	private void aggregateAnyItem(){
		
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
			this.aggregateResult.addAnyItemOrUpdate(AnyItemOfMonthly.of(
					this.employeeId, this.yearMonth, this.closureId, this.closureDate,
					50, new AnyTimeMonth(0), new AnyTimesMonth(20.67), new AnyAmountMonth(0)));
		}
	}
}
