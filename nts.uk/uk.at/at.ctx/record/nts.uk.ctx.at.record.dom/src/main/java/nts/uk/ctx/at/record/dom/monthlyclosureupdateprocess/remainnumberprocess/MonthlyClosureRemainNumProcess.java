package nts.uk.ctx.at.record.dom.monthlyclosureupdateprocess.remainnumberprocess;

import java.util.Map;

import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.monthlyclosureupdateprocess.remainnumberprocess.annualleave.AnnualLeaveProcess;
import nts.uk.ctx.at.record.dom.monthlyclosureupdateprocess.remainnumberprocess.compensatoryholiday.CompensatoryHolidayProcess;
import nts.uk.ctx.at.record.dom.monthlyclosureupdateprocess.remainnumberprocess.specialholiday.SpecialHolidayProcess;
import nts.uk.ctx.at.record.dom.monthlyclosureupdateprocess.remainnumberprocess.substitutionholiday.SubstitutionHolidayProcess;
import nts.uk.ctx.at.record.dom.monthlycommon.aggrperiod.AggrPeriodEachActualClosure;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.DailyInterimRemainMngData;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.AttendanceTimeOfMonthly;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author HungTT - 月締め更新残数処理
 *
 */
public class MonthlyClosureRemainNumProcess {

	/**
	 * 残数処理
	 * @param period 実締め毎集計期間
	 * @param empId 社員ID
	 * @param attTimeMonthly 月別実績の勤怠時間
	 */
	public static AtomTask remainNumberProcess(RequireM1 require, CacheCarrier cacheCarrier,
			AggrPeriodEachActualClosure period, String empId,
			AttendanceTimeOfMonthly attTimeMonthly) {

		String companyId = AppContexts.user().companyId();
		
		// 「月次処理用の暫定残数管理データを作成する」を実行する
		Map<GeneralDate, DailyInterimRemainMngData> interimRemainMngMap =
				require.monthInterimRemainData(cacheCarrier, companyId, empId, period.getPeriod());
		
		// 年休（・積立年休）処理
		return AnnualLeaveProcess.annualHolidayProcess(require, cacheCarrier, period, empId, interimRemainMngMap, attTimeMonthly)
				// 振休処理
				.then(SubstitutionHolidayProcess.substitutionHolidayProcess(require, cacheCarrier, period, empId, interimRemainMngMap))
				// 代休処理
				.then(CompensatoryHolidayProcess.compensatoryHolidayProcess(require, cacheCarrier, period, empId, interimRemainMngMap))
				// 特別休暇処理
				.then(SpecialHolidayProcess.specialHolidayProcess(require, cacheCarrier, period, empId, interimRemainMngMap));
	}
	
	public static interface RequireM1 extends AnnualLeaveProcess.RequireM1, SubstitutionHolidayProcess.RequireM1,
		CompensatoryHolidayProcess.RequireM1, SpecialHolidayProcess.RequireM1 {
		
		Map<GeneralDate, DailyInterimRemainMngData> monthInterimRemainData(CacheCarrier cacheCarrier, String cid, String sid, DatePeriod dateData);
	}
}
