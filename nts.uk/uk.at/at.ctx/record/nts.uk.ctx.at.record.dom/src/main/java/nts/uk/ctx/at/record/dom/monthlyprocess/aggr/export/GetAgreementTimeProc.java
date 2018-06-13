package nts.uk.ctx.at.record.dom.monthlyprocess.aggr.export;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.val;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.actualworkinghours.AttendanceTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.agreement.AgreementTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.RepositoriesRequiredByMonthlyAggr;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureDate;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.UseClassification;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 処理：36協定時間の取得
 * @author shuichu_ishida
 */
public class GetAgreementTimeProc {

	/** 月別集計が必要とするリポジトリ */
	private RepositoriesRequiredByMonthlyAggr repositories;
	/** 締めの取得 */
	private ClosureRepository closureRepository;
	
	/** 会社ID */
	private String companyId;
	/** 年月 */
	private YearMonth yearMonth;
	/** 締めID */
	private ClosureId closureId;
	/** 締め日 */
	private ClosureDate closureDate;
	/** エラーメッセージ */
	private String errorMessage;
	
	public GetAgreementTimeProc(
			RepositoriesRequiredByMonthlyAggr repositories,
			ClosureRepository closureRepository) {
		
		this.repositories = repositories;
		this.closureRepository = closureRepository;
	}
	
	/**
	 * 36協定時間の取得
	 * @param companyId 会社ID
	 * @param employeeIds 社員ID
	 * @param yearMonth 年月
	 * @param closureId 締めID
	 * @return 36協定時間一覧
	 */
	public List<AgreementTimeDetail> get(String companyId, List<String> employeeIds, YearMonth yearMonth,
			ClosureId closureId) {

		List<AgreementTimeDetail> returnList = new ArrayList<>();
		this.companyId = companyId;
		this.yearMonth = yearMonth;
		this.closureId = closureId;
		this.errorMessage = null;
		
		// 年月を集計期間に変換
		val aggrPeriod = this.convertToAggregatePeriod();
		if (aggrPeriod == null) return returnList;
		
		for (val employeeId : employeeIds){
			
			// 36協定時間一覧を作成
			AgreementTimeDetail aggrTimeDetail = new AgreementTimeDetail(employeeId);

			// 労働条件項目を取得する
			val workConditionItemOpt =
					this.repositories.getWorkingConditionItem().getBySidAndStandardDate(employeeId, aggrPeriod.end());
			if (!workConditionItemOpt.isPresent()) continue;
			val workConditionItem = workConditionItemOpt.get();
			
			// 「日別実績の勤怠時間」を取得
			val confirmedAttdTimeList =
					this.repositories.getAttendanceTimeOfDaily().findByPeriodOrderByYmd(employeeId, aggrPeriod);

			// 確定情報の取得
			val confirmed = this.getConfirmed(employeeId, aggrPeriod, workConditionItem,
					Optional.of(confirmedAttdTimeList));
			
			// 社員の申請を反映　（反映結果の取得）
			//*****（未）　申請反映側処理の完成後、本実装。（永続化でない）「取得モード」で貰う。
			//List<AttendanceTimeOfDailyPerformance> appReflectAttdTimeList = new ArrayList<>();
			
			// 未反映申請反映後情報の取得
			AgreementTimeOfMonthly afterAppReflect = null;
			//val afterAppReflect = this.getConfirmed(employeeId, aggrPeriod, workConditionItem,
			//		Optional.of(appReflectAttdTimeList));
			
			aggrTimeDetail = AgreementTimeDetail.of(employeeId, confirmed, afterAppReflect, this.errorMessage);
			returnList.add(aggrTimeDetail);
		}
		
		return returnList;
	}
	
	/**
	 * 年月を集計期間に変換
	 * @return 集計期間
	 */
	private DatePeriod convertToAggregatePeriod(){
		
		// 「締め」を取得
		val closureOpt = this.closureRepository.findById(this.companyId, this.closureId.value);
		if (!closureOpt.isPresent()) return null;
		val closure = closureOpt.get();
		if (closure.getUseClassification() != UseClassification.UseClass_Use) return null;
		
		// 締め日を取得
		val closureHistoryOpt = closure.getHistoryByYearMonth(this.yearMonth);
		if (!closureHistoryOpt.isPresent()) return null;
		val closureHistory = closureHistoryOpt.get();
		this.closureDate = closureHistory.getClosureDate();
		
		// 36協定運用設定を取得
		val agreementOperationSetOpt = this.repositories.getAgreementOperationSet().find(this.companyId);
		if (!agreementOperationSetOpt.isPresent()) return null;
		val agreementOperationSet = agreementOperationSetOpt.get();
		
		// 年月から集計期間を取得
		val aggrPeriodOpt = agreementOperationSet.getAggregatePeriodByYearMonth(this.yearMonth, closure);
		if (!aggrPeriodOpt.isPresent()) return null;
		
		// 集計期間を返す
		return aggrPeriodOpt.get().getPeriod();
	}
	
	/**
	 * 確定情報の取得
	 * @param employeeId 社員ID
	 * @param aggrPeriod 集計期間
	 * @param workingConditionItem 労働条件項目
	 * @param attendanceTimeOfDailysOpt 日別実績の勤怠時間リスト
	 * @return 月別実績の36協定時間
	 */
	private AgreementTimeOfMonthly getConfirmed(
			String employeeId, DatePeriod aggrPeriod, WorkingConditionItem workingConditionItem,
			Optional<List<AttendanceTimeOfDailyPerformance>> attendanceTimeOfDailysOpt){
		
		// 「月別実績の勤怠時間」を取得
		AttendanceTimeOfMonthly attendanceTimeOfMonthly = null;
		val attendanceTimeOfMonthlys = this.repositories.getAttendanceTimeOfMonthly()
				.findByYMAndClosureIdOrderByStartYmd(employeeId, this.yearMonth, this.closureId);
		if (attendanceTimeOfMonthlys.size() <= 0) {
			
			// 「締め」を取得する
			val closureOpt = this.closureRepository.findById(this.companyId, this.closureId.value);
			if (!closureOpt.isPresent()) return null;
			val closure = closureOpt.get();
			if (closure.getUseClassification() != UseClassification.UseClass_Use) return null;
			
			// 指定した年月日時点の締め期間を取得する
			val closurePeriodOpt = closure.getClosurePeriodByYmd(aggrPeriod.start());
			if (!closurePeriodOpt.isPresent()) return null;
			val closurePeriod = closurePeriodOpt.get();

			// 「月別実績の勤怠時間」を作成する
			attendanceTimeOfMonthly = new AttendanceTimeOfMonthly(
					employeeId, this.yearMonth, this.closureId, this.closureDate, closurePeriod.getPeriod());
		}
		else {
			attendanceTimeOfMonthly = attendanceTimeOfMonthlys.get(0);
		}
		if (attendanceTimeOfMonthly == null) return null;
	
		// 36協定時間の集計
		val monthlyCalculation = attendanceTimeOfMonthly.getMonthlyCalculation();
		val agreementTimeOpt = monthlyCalculation.aggregateAgreementTime(
				this.companyId, employeeId, this.yearMonth, this.closureId, this.closureDate, aggrPeriod,
				attendanceTimeOfDailysOpt, Optional.empty(), Optional.empty(), this.repositories);
		if (agreementTimeOpt.isPresent()){
			
			// エラーメッセージがあれば、エラーメッセージを入れる
			if (!monthlyCalculation.getErrorInfos().isEmpty()){
				this.errorMessage = monthlyCalculation.getErrorInfos().get(0).getMessage().v();
			}
			
			// 月別実績の36協定時間を返す
			return agreementTimeOpt.get().getAgreementTime();
		}
		
		return null;
	}
}
