package nts.uk.ctx.at.record.dom.remainingnumber.reserveleave.export;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.MonAggrCompanySettings;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.MonthlyCalculatingDailys;
import nts.uk.ctx.at.record.dom.remainingnumber.reserveleave.export.param.AggrResultOfReserveLeave;

/**
 * 実装：期間中の積立年休残数を取得する
 * @author shuichu_ishida
 */
@Stateless
public class GetRsvLeaRemNumWithinPeriodImpl implements GetRsvLeaRemNumWithinPeriod {

	/** 期間中の積立年休残数を取得する */
	@Override
	public Optional<AggrResultOfReserveLeave> algorithm(GetRsvLeaRemNumWithinPeriodParam param) {
		return this.algorithm(param, Optional.empty(), Optional.empty());
	}
	
	/** 期間中の積立年休残数を取得する　（月別集計用） */
	@Override
	public Optional<AggrResultOfReserveLeave> algorithm(GetRsvLeaRemNumWithinPeriodParam param,
			Optional<MonAggrCompanySettings> companySets, Optional<MonthlyCalculatingDailys> monthlyCalcDailys) {
		
		return Optional.of(new AggrResultOfReserveLeave());
	}
}
