package nts.uk.ctx.at.record.dom.monthlycommon.aggrperiod;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.adapter.employment.EmploymentHistAdapter;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmploymentRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;

/**
 * 実装：集計すべき期間を計算
 * @author shuichu_ishida
 */
@Stateless
public class CalcPeriodForAggregateImpl implements CalcPeriodForAggregate {

	/** 所属雇用履歴の取得 */
	@Inject
	private EmploymentHistAdapter employmentHistAdapter;
	/** 雇用に紐づく就業締めの取得 */
	@Inject
	private ClosureEmploymentRepository closureEmploymentRepository;
	/** 締めの取得 */
	@Inject
	private ClosureRepository closureRepository;
	/** 当月の期間を算出する */
	@Inject
	private ClosureService closureService;
	
	/** 集計すべき期間を計算 */
	@Override
	public List<ClosurePeriod> algorithm(String companyId, String employeeId, GeneralDate aggrEnd) {
		
		CalcPeriodForAggregateProc proc = new CalcPeriodForAggregateProc(
				this.employmentHistAdapter,
				this.closureEmploymentRepository,
				this.closureRepository,
				this.closureService);
		return proc.algorithm(companyId, employeeId, aggrEnd);
	}
}
