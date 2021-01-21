package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.AbsRecRemainMngOfInPeriod;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.DailyInterimRemainMngData;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.BreakDayOffRemainMngOfInPeriod;
import nts.uk.ctx.at.shared.dom.remainingnumber.export.param.AggrResultOfAnnAndRsvLeave;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.service.InPeriodOfSpecialLeaveResultInfor;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.MonAggrCompanySettings;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.MonAggrEmployeeSettings;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.MonthlyCalculatingDailys;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.IntegrationOfMonthly;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryLeaveComSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.ComSubstVacation;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.shr.com.time.calendar.date.ClosureDate;

/**
 * ドメインサービス：月別実績を集計する
 * @author shuichi_ishida
 */
public class AggregateMonthlyRecordService {

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
	public static AggregateMonthlyRecordValue aggregate(RequireM2 require, CacheCarrier cacheCarrier, 
			String companyId, String employeeId,
			YearMonth yearMonth, ClosureId closureId, ClosureDate closureDate, DatePeriod datePeriod,
			AggrResultOfAnnAndRsvLeave prevAggrResult,
			Optional<AbsRecRemainMngOfInPeriod> prevAbsRecResultOpt,
			Optional<BreakDayOffRemainMngOfInPeriod> prevBreakDayOffResultOpt,
			Map<Integer, InPeriodOfSpecialLeaveResultInfor> prevSpecialLeaveResultMap,
			MonAggrCompanySettings companySets, MonAggrEmployeeSettings employeeSets,
			Optional<List<IntegrationOfDaily>> dailyWorks, Optional<IntegrationOfMonthly> monthlyWork) {
		
		return aggregate(require, cacheCarrier, companyId, employeeId, yearMonth, closureId, closureDate, datePeriod, prevAggrResult, prevAbsRecResultOpt, 
				prevBreakDayOffResultOpt, prevSpecialLeaveResultMap, companySets, employeeSets, dailyWorks, monthlyWork, false);
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
	public static AggregateMonthlyRecordValue aggregate(RequireM2 require, CacheCarrier cacheCarrier, 
			String companyId, String employeeId, YearMonth yearMonth,
			ClosureId closureId, ClosureDate closureDate, DatePeriod datePeriod,
			AggrResultOfAnnAndRsvLeave prevAggrResult, Optional<AbsRecRemainMngOfInPeriod> prevAbsRecResultOpt,
			Optional<BreakDayOffRemainMngOfInPeriod> prevBreakDayOffResultOpt,
			Map<Integer, InPeriodOfSpecialLeaveResultInfor> prevSpecialLeaveResultMap,
			MonAggrCompanySettings companySets, MonAggrEmployeeSettings employeeSets,
			Optional<List<IntegrationOfDaily>> dailyWorks, Optional<IntegrationOfMonthly> monthlyWork,
			Boolean remainingProcAtr) {
		
		AggregateMonthlyRecordServiceProc proc = new AggregateMonthlyRecordServiceProc();
		
		return proc.aggregate(require, cacheCarrier, companyId, employeeId, yearMonth, closureId, closureDate,
				datePeriod, prevAggrResult, prevAbsRecResultOpt, prevBreakDayOffResultOpt, prevSpecialLeaveResultMap,
				companySets, employeeSets, dailyWorks, monthlyWork, remainingProcAtr);
	}
	
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
	public static Map<GeneralDate, DailyInterimRemainMngData> mapInterimRemainData(RequireM1 require, 
			CacheCarrier cacheCarrier, String cid, String sid, DatePeriod datePeriod) {
		AggregateMonthlyRecordServiceProc proc = new AggregateMonthlyRecordServiceProc();
		
		MonAggrEmployeeSettings monAggreEmpSetting = MonAggrEmployeeSettings.loadSettings(require, cacheCarrier, cid, sid, datePeriod);
		
		proc.setMonthlyCalculatingDailys(MonthlyCalculatingDailys.loadData(require, sid, datePeriod, monAggreEmpSetting));
		proc.setCompanyId(cid);
		proc.setEmployeeId(sid);
		Optional<ComSubstVacation> absSettingOpt = require.comSubstVacation(cid);
		CompensatoryLeaveComSetting dayOffSetting = require.compensatoryLeaveComSetting(cid);
		MonAggrCompanySettings comSetting = new MonAggrCompanySettings();
		comSetting.setAbsSettingOpt(absSettingOpt);
		comSetting.setDayOffSetting(dayOffSetting);
		proc.setCompanySets(comSetting);
		proc.createDailyInterimRemainMngs(require, cacheCarrier, datePeriod);
		return proc.getDailyInterimRemainMngs();
	}	
	
	public static interface RequireM2 extends AggregateMonthlyRecordServiceProc.RequireM15 {
		
	}
	
	public static interface RequireM1 extends MonthlyCalculatingDailys.RequireM4, AggregateMonthlyRecordServiceProc.RequireM7,
		MonAggrEmployeeSettings.RequireM2 {

		Optional<ComSubstVacation> comSubstVacation(String companyId);
		
		CompensatoryLeaveComSetting compensatoryLeaveComSetting(String companyId);
	}
}