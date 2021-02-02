package nts.uk.ctx.at.record.pub.remainnumber.holidayover60h;

import java.util.List;
import java.util.Optional;

import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.remainingnumber.holidayover60h.export.GetHolidayOver60hRemNumWithinPeriod;
import nts.uk.ctx.at.record.pub.remainnumber.holidayover60h.export.AggrResultOfHolidayOver60hExport;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.export.InterimRemainMngMode;
import nts.uk.ctx.at.shared.dom.remainingnumber.holidayover60h.interim.TmpHolidayOver60hMng;

/**
 * 60H超休残数計算
 * @author masaaki_jinno
 *
 */
public interface GetHolidayOver60hRemNumWithinPeriodPub {

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
	AggrResultOfHolidayOver60hExport algorithm(
			GetHolidayOver60hRemNumWithinPeriod.RequireM1 require,
			CacheCarrier cacheCarrier,
			String companyId,
			String employeeId,
			DatePeriod aggrPeriod,
			InterimRemainMngMode mode,
			GeneralDate criteriaDate,
			Optional<Boolean> isOverWriteOpt,
			Optional<List<TmpHolidayOver60hMng>> forOverWriteList,
			Optional<AggrResultOfHolidayOver60hExport> prevHolidayOver60h);

	/**
	 * Require
	 * @author masaaki_jinno
	 *
	 */
	public static interface RequireM1{
	}
}
