package nts.uk.ctx.at.record.dom.remainingnumber.reserveleave.export;

import java.util.Optional;

import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.MonAggrCompanySettings;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.MonthlyCalculatingDailys;
import nts.uk.ctx.at.record.dom.remainingnumber.reserveleave.export.param.AggrResultOfReserveLeave;

/**
 * 期間中の積立年休残数を取得する
 * @author shuichu_ishida
 */
public interface GetRsvLeaRemNumWithinPeriod {

	/**
	 * 期間中の積立年休残数を取得する
	 * @param param パラメータ
	 * @return 積立年休の集計結果
	 */
	Optional<AggrResultOfReserveLeave> algorithm(GetRsvLeaRemNumWithinPeriodParam param);

	/**
	 * 期間中の積立年休残数を取得する　（月別集計用）
	 * @param param パラメータ
	 * @param companySets 月別集計で必要な会社別設定
	 * @param monthlyCalcDailys 月の計算中の日別実績データ
	 * @return 積立年休の集計結果
	 */
	Optional<AggrResultOfReserveLeave> algorithm(
			GetRsvLeaRemNumWithinPeriodParam param,
			Optional<MonAggrCompanySettings> companySets,
			Optional<MonthlyCalculatingDailys> monthlyCalcDailys);
}
