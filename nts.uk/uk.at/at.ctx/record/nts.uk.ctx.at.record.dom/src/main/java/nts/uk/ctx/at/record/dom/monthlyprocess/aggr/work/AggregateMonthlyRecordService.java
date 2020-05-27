package nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.IntegrationOfDaily;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.IntegrationOfMonthly;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.AggregateMonthlyRecordServiceImpl.Require;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.param.AggrResultOfAnnAndRsvLeave;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.AbsRecRemainMngOfInPeriod;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.DailyInterimRemainMngData;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.BreakDayOffRemainMngOfInPeriod;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.service.InPeriodOfSpecialLeaveResultInfor;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.shr.com.time.calendar.date.ClosureDate;

/**
 * ドメインサービス：月別実績を集計する
 * @author shuichi_ishida
 */
public interface AggregateMonthlyRecordService {

	/**
	 * 集計処理
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param yearMonth 年月
	 * @param closureId 締めID
	 * @param closureDate 締め日付
	 * @param datePeriod 期間
	 * @param prevAggrResult 前回集計結果　（年休積立年休の集計結果）
	 * @param prevAbsRecResultOpt 前回集計結果　（振休振出の集計結果）
	 * @param prevBreakDayOffResultOpt 前回集計結果　（代休の集計結果）
	 * @param prevSpecialLeaveResultMap 前回集計結果　（特別休暇の集計結果）
	 * @param companySets 月別集計で必要な会社別設定
	 * @param employeeSets 月別集計で必要な社員別設定
	 * @param dailyWorks 日別実績(WORK)List
	 * @param monthlyWork 月別実績(WORK)
	 * @return 集計結果
	 */
	AggregateMonthlyRecordValue aggregate(String companyId, String employeeId,
			YearMonth yearMonth, ClosureId closureId, ClosureDate closureDate, DatePeriod datePeriod,
			AggrResultOfAnnAndRsvLeave prevAggrResult,
			Optional<AbsRecRemainMngOfInPeriod> prevAbsRecResultOpt,
			Optional<BreakDayOffRemainMngOfInPeriod> prevBreakDayOffResultOpt,
			Map<Integer, InPeriodOfSpecialLeaveResultInfor> prevSpecialLeaveResultMap,
			MonAggrCompanySettings companySets, MonAggrEmployeeSettings employeeSets,
			Optional<List<IntegrationOfDaily>> dailyWorks, Optional<IntegrationOfMonthly> monthlyWork);

	/**
	 * 集計処理
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param yearMonth 年月
	 * @param closureId 締めID
	 * @param closureDate 締め日付
	 * @param datePeriod 期間
	 * @param prevAggrResult 前回集計結果　（年休積立年休の集計結果）
	 * @param prevAbsRecResultOpt 前回集計結果　（振休振出の集計結果）
	 * @param prevBreakDayOffResultOpt 前回集計結果　（代休の集計結果）
	 * @param prevSpecialLeaveResultMap 前回集計結果　（特別休暇の集計結果）
	 * @param companySets 月別集計で必要な会社別設定
	 * @param employeeSets 月別集計で必要な社員別設定
	 * @param dailyWorks 日別実績(WORK)List
	 * @param monthlyWork 月別実績(WORK)
	 * @param remainingProcAtr 残数処理フラグ
	 * @return 集計結果
	 */
	AggregateMonthlyRecordValue aggregate(String companyId, String employeeId,
			YearMonth yearMonth, ClosureId closureId, ClosureDate closureDate, DatePeriod datePeriod,
			AggrResultOfAnnAndRsvLeave prevAggrResult,
			Optional<AbsRecRemainMngOfInPeriod> prevAbsRecResultOpt,
			Optional<BreakDayOffRemainMngOfInPeriod> prevBreakDayOffResultOpt,
			Map<Integer, InPeriodOfSpecialLeaveResultInfor> prevSpecialLeaveResultMap,
			MonAggrCompanySettings companySets, MonAggrEmployeeSettings employeeSets,
			Optional<List<IntegrationOfDaily>> dailyWorks, Optional<IntegrationOfMonthly> monthlyWork,
			Boolean remainingProcAtr);
	
	AggregateMonthlyRecordValue aggregateRequire(Require require, CacheCarrier cacheCarrier, String companyId,
			String employeeId, YearMonth yearMonth, ClosureId closureId, ClosureDate closureDate, DatePeriod datePeriod,
			AggrResultOfAnnAndRsvLeave prevAggrResult, Optional<AbsRecRemainMngOfInPeriod> prevAbsRecResultOpt,
			Optional<BreakDayOffRemainMngOfInPeriod> prevBreakDayOffResultOpt,
			Map<Integer, InPeriodOfSpecialLeaveResultInfor> prevSpecialLeaveResultMap,
			MonAggrCompanySettings companySets, MonAggrEmployeeSettings employeeSets,
			Optional<List<IntegrationOfDaily>> dailyWorks, Optional<IntegrationOfMonthly> monthlyWork,
			Boolean remainingProcAtr);
	
	/**
	 * public Workを考慮した月次処理用の暫定残数管理データを作成する
	 * @param cid
	 * @param sid
	 * @param datePeriod
	 * @param dailyWorksOpt
	 * @param repositories
	 * @param companySets
	 * @return
	 */
	Map<GeneralDate, DailyInterimRemainMngData> mapInterimRemainData(String cid, String sid, DatePeriod datePeriod);
}