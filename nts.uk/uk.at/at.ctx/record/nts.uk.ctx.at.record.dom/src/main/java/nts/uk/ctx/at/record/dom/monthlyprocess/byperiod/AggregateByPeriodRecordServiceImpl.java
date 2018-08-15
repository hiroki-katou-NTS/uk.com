package nts.uk.ctx.at.record.dom.monthlyprocess.byperiod;

import javax.ejb.Stateless;

import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.MonAggrCompanySettings;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.MonAggrEmployeeSettings;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 任意期間集計Mgr　（アルゴリズム）
 * @author shuichu_ishida
 */
@Stateless
public class AggregateByPeriodRecordServiceImpl implements AggregateByPeriodRecordService {

	/** アルゴリズム */
	@Override
	public AggregateByPeriodRecordValue algorithm(String companyId, String employeeId, DatePeriod period,
			MonAggrCompanySettings companySets, MonAggrEmployeeSettings employeeSets) {
		
		return new AggregateByPeriodRecordValue();
	}
}
