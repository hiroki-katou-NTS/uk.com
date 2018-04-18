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
import nts.uk.ctx.at.record.dom.monthly.calc.MonthlyAggregateAtr;
import nts.uk.ctx.at.record.dom.monthly.calc.MonthlyCalculation;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.MonthlyAggregationErrorInfo;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.excessoutside.ExcessOutsideWorkMng;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.ErrMessageContent;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmployeeImport;
import nts.uk.ctx.at.shared.dom.common.WorkplaceId;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.JobTitleId;
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
	
	/** エラー情報 */
	private Map<String, MonthlyAggregationErrorInfo> errorInfos;
	
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
		
		val returnValue = new AggregateMonthlyRecordValue();

		this.errorInfos = new HashMap<>();

		// 「労働条件項目」を取得
		val workingConditionItems = this.repositories.getWorkingConditionItem().getBySidAndPeriodOrderByStrD(
				employeeId, datePeriod);
		if (workingConditionItems.isEmpty()){
			returnValue.addErrorInfos("001", new ErrMessageContent(TextResource.localize("Msg_430")));
			return returnValue;
		}
		
		// 社員を取得する
		EmployeeImport employee = null;
		employee = this.repositories.getEmpEmployee().findByEmpId(employeeId);
		if (employee == null){
			returnValue.addErrorInfos("002", new ErrMessageContent(TextResource.localize("Msg_1156")));
			return returnValue;
		}
		
		// 項目の数だけループ
		for (val workingConditionItem : workingConditionItems){

			// 「労働条件」の該当履歴から期間を取得
			val historyId = workingConditionItem.getHistoryId();
			val workingConditionOpt = this.repositories.getWorkingCondition().getByHistoryId(historyId);
			if (!workingConditionOpt.isPresent()) continue;
			val workingCondition = workingConditionOpt.get();

			// 処理期間を計算　（処理期間と労働条件履歴期間の重複を確認する）
			val dateHistoryItems = workingCondition.getDateHistoryItem();
			if (dateHistoryItems.isEmpty()) continue;
			val term = dateHistoryItems.get(0).span();
			DatePeriod procPeriod = this.confirmProcPeriod(datePeriod, term);
			if (procPeriod == null) {
				// 履歴の期間と重複がない時
				continue;
			}
			
			// 入社前、退職後を期間から除く
			val termInOffice = new DatePeriod(employee.getEntryDate(), employee.getRetiredDate());
			procPeriod = this.confirmProcPeriod(procPeriod, termInOffice);
			if (procPeriod == null) {
				// 処理期間全体が、入社前または退職後の時
				continue;
			}
			
			// 所属情報の作成
			val affiliationInfo = this.createAffiliationInfo(
					companyId, employeeId, yearMonth, closureId, closureDate, procPeriod);
			if (affiliationInfo == null) break;
			returnValue.getAffiliationInfoList().add(affiliationInfo);
			
			// 労働制を確認する
			val workingSystem = workingConditionItem.getLaborSystem();
			
			// 月別実績の勤怠時間　初期設定
			val attendanceTime = new AttendanceTimeOfMonthly(employeeId, yearMonth, closureId, closureDate, procPeriod);
			attendanceTime.prepareAggregation(companyId, procPeriod, workingConditionItem, this.repositories);
			
			// 月の計算
			val monthlyCalculation = attendanceTime.getMonthlyCalculation();
			monthlyCalculation.aggregate(procPeriod, MonthlyAggregateAtr.MONTHLY,
					Optional.empty(), Optional.empty(), this.repositories);
			
			// 36協定時間の集計
			MonthlyCalculation monthlyCalculationForAgreement = new MonthlyCalculation();
			val agreementTimeOpt = monthlyCalculationForAgreement.aggregateAgreementTime(
					companyId, employeeId, yearMonth, closureId, closureDate, procPeriod,
					workingConditionItem, Optional.empty(), this.repositories);
			if (agreementTimeOpt.isPresent()){
				val agreementTime = agreementTimeOpt.get();
				val agreementTimeList = returnValue.getAgreementTimeList();
				agreementTimeList.removeIf(c -> { return (c.getYearMonth() == agreementTime.getYearMonth());});
				agreementTimeList.add(agreementTime);
			}
			
			// 縦計
			val verticalTotal = attendanceTime.getVerticalTotal();
			verticalTotal.verticalTotal(companyId, employeeId, procPeriod, workingSystem, this.repositories);
			
			// 時間外超過
			ExcessOutsideWorkMng excessOutsideWorkMng = new ExcessOutsideWorkMng(monthlyCalculation);
			excessOutsideWorkMng.aggregate(this.repositories);
			attendanceTime.setExcessOutsideWork(excessOutsideWorkMng.getExcessOutsideWork());

			// 合算する
			val itrAttendanceTime = returnValue.getAttendanceTimeList().iterator();
			while (itrAttendanceTime.hasNext()){
				val calcedAttendanceTime = itrAttendanceTime.next();
				if (calcedAttendanceTime.equals(attendanceTime)){
					attendanceTime.sum(calcedAttendanceTime);
					itrAttendanceTime.remove();
				}
			}

			// 計算中のエラー情報の取得
			for (val errorInfo : monthlyCalculation.getErrorInfos()){
				this.errorInfos.putIfAbsent(errorInfo.getResourceId(), errorInfo);
			}
			
			// 計算結果を戻り値に蓄積
			returnValue.getAttendanceTimeList().add(attendanceTime);
		}
		
		// 戻り値にエラー情報を移送
		for (val errorInfo : this.errorInfos.values()){
			returnValue.getErrorInfos().putIfAbsent(errorInfo.getResourceId(), errorInfo);
		}
		
		return returnValue;
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
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param yearMonth 年月
	 * @param closureId 締めID
	 * @param closureDate 締め日
	 * @param datePeriod 期間
	 * @return 月別実績の所属情報
	 */
	private AffiliationInfoOfMonthly createAffiliationInfo(String companyId, String employeeId,
			YearMonth yearMonth, ClosureId closureId, ClosureDate closureDate, DatePeriod datePeriod){
		
		// 月初の所属情報を取得
		val firstInfoOfDailyOpt = this.repositories.getAffiliationInfoOfDaily().findByKey(employeeId, datePeriod.start());
		if (!firstInfoOfDailyOpt.isPresent()){
			val errorInfo = new MonthlyAggregationErrorInfo(
					"003", new ErrMessageContent(TextResource.localize("Msg_1157")));
			this.errorInfos.putIfAbsent(errorInfo.getResourceId(), errorInfo);
			return null;
		}
		val firstInfoOfDaily = firstInfoOfDailyOpt.get();
		val firstWorkTypeOfDailyOpt = this.repositories.getWorkTypeOfDaily().findByKey(employeeId, datePeriod.start());
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
		val lastInfoOfDailyOpt = this.repositories.getAffiliationInfoOfDaily().findByKey(employeeId, datePeriod.end());
		if (!lastInfoOfDailyOpt.isPresent()){
			val errorInfo = new MonthlyAggregationErrorInfo(
					"004", new ErrMessageContent(TextResource.localize("Msg_1157")));
			this.errorInfos.putIfAbsent(errorInfo.getResourceId(), errorInfo);
			return null;
		}
		val lastInfoOfDaily = firstInfoOfDailyOpt.get();
		val lastWorkTypeOfDailyOpt = this.repositories.getWorkTypeOfDaily().findByKey(employeeId, datePeriod.end());
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
		return AffiliationInfoOfMonthly.of(employeeId, yearMonth, closureId, closureDate, firstInfo, lastInfo);
	}
}
