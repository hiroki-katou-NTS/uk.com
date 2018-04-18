package nts.uk.ctx.at.record.dom.monthlycommon.aggrperiod;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.enums.ExecutionType;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;

/**
 * 集計期間を取得する
 * @author shuichu_ishida
 */
@Stateless
public class GetClosurePeriodImpl implements GetClosurePeriod {

	/** 集計すべき期間を計算 */
	@Inject
	private CalcPeriodForAggregate calcPeriodForAggregate;
	
	/** 集計期間を取得する */
	@Override
	public List<ClosurePeriod> get(String companyId, String employeeId, GeneralDate criteriaDate,
			Optional<YearMonth> yearMonthOpt, Optional<ClosureId> closureIdOpt, Optional<ExecutionType> executionTypeOpt) {
		
		// ※　Optinal引数の組み合わせ不足等エラーは、throw new RuntimeError...で。
		//    現時点では、引数の利用が未設計。（2018.3.15 shuichi_ishida）
		
		// 集計すべき期間を計算
		return this.calcPeriodForAggregate.algorithm(companyId, employeeId, criteriaDate);
	}
}
