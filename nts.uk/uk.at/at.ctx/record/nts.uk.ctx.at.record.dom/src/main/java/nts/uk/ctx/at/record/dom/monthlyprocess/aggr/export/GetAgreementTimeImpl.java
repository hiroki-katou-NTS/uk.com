package nts.uk.ctx.at.record.dom.monthlyprocess.aggr.export;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.RepositoriesRequiredByMonthlyAggr;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;

/**
 * 実装：36協定時間の取得
 * @author shuichu_ishida
 */
@Stateless
public class GetAgreementTimeImpl implements GetAgreementTime {

	/** 月別集計が必要とするリポジトリ */
	@Inject
	private RepositoriesRequiredByMonthlyAggr repositories;
	
	/** 36協定時間の取得 */
	@Override
	public List<AgreementTimeDetail> get(String companyId, List<String> employeeIds, YearMonth yearMonth,
			ClosureId closureId) {

		GetAgreementTimeProc proc = new GetAgreementTimeProc(this.repositories);
		return proc.get(companyId, employeeIds, yearMonth, closureId);
	}
}
