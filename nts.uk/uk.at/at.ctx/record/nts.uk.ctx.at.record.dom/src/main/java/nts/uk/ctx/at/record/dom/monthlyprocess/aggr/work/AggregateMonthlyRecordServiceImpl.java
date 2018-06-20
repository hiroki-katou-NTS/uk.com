package nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.YearMonth;
import nts.uk.shr.com.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.remainingnumber.absenceleave.temp.TempAbsenceLeaveService;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.GetAnnAndRsvRemNumWithinPeriod;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.param.AggrResultOfAnnAndRsvLeave;
import nts.uk.ctx.at.record.dom.remainingnumber.dayoff.temp.TempDayoffService;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureDate;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;

/**
 * ドメインサービス：月別実績を集計する
 * @author shuichi_ishida
 */
@Stateless
public class AggregateMonthlyRecordServiceImpl implements AggregateMonthlyRecordService {

	/** 月別集計が必要とするリポジトリ */
	@Inject
	private RepositoriesRequiredByMonthlyAggr repositories;
	/** 期間中の年休積休残数を取得 */
	@Inject
	private GetAnnAndRsvRemNumWithinPeriod getAnnAndRsvRemNumWithinPeriod;
	/** （仮対応用）振休 */
	@Inject
	private TempAbsenceLeaveService tempAbsenceLeaveService;
	/** （仮対応用）代休 */
	@Inject
	private TempDayoffService tempDayoffService;
	
	/** 集計処理　（アルゴリズム） */
	@Override
	public AggregateMonthlyRecordValue aggregate(
			String companyId, String employeeId, YearMonth yearMonth,
			ClosureId closureId, ClosureDate closureDate, DatePeriod datePeriod,
			AggrResultOfAnnAndRsvLeave prevAggrResult,
			MonAggrCompanySettings companySets, MonAggrEmployeeSettings employeeSets) {
		
		AggregateMonthlyRecordServiceProc proc = new AggregateMonthlyRecordServiceProc(
				this.repositories,
				this.getAnnAndRsvRemNumWithinPeriod,
				this.tempAbsenceLeaveService,
				this.tempDayoffService);
		
		return proc.aggregate(companyId, employeeId, yearMonth, closureId, closureDate,
				datePeriod, prevAggrResult, companySets, employeeSets);
	}	
}
