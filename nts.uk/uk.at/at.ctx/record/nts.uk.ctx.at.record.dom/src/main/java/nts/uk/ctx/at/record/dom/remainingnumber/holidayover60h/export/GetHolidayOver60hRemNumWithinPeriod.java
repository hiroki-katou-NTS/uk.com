package nts.uk.ctx.at.record.dom.remainingnumber.holidayover60h.export;

import java.util.List;
import java.util.Optional;

import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.remainingnumber.holidayover60h.export.param.AggrResultOfHolidayOver60h;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.export.InterimRemainMngMode;
import nts.uk.ctx.at.shared.dom.remainingnumber.holidayover60h.interim.TmpHolidayOver60hMng;

public interface GetHolidayOver60hRemNumWithinPeriod {

	/**
	 * 期間中の60H超休残数を取得 
	 * @param require Require
	 * @param cacheCarrier CacheCarrier
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param aggrPeriod 集計期間
	 * @param mode 実績のみ参照区分
	 * @param criteriaDate 基準日
	 * @param isOverWriteOpt 上書きフラグ
	 * @param forOverWriteListOpt 上書き用の暫定60H超休管理データ
	 * @param prevAnnualLeaveOpt 前回の60H超休の集計結果
	 * @return 60H超休の集計結果
	 */
	AggrResultOfHolidayOver60h algorithm(
			GetHolidayOver60hRemNumWithinPeriod.RequireM1 require, 
			CacheCarrier cacheCarrier,
			String companyId, 
			String employeeId, 
			DatePeriod aggrPeriod, 
			InterimRemainMngMode mode,
			GeneralDate criteriaDate, 
			Optional<Boolean> isOverWriteOpt, 
			Optional<List<TmpHolidayOver60hMng>> forOverWriteList,
			Optional<AggrResultOfHolidayOver60h> prevHolidayOver60h);

	/**
	 * Require
	 * @author masaaki_jinno
	 *
	 */
	public static interface RequireM1{
	}
}
