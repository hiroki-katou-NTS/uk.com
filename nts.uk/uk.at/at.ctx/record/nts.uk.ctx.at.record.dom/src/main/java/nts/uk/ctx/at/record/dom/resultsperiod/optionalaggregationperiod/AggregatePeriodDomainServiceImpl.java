package nts.uk.ctx.at.record.dom.resultsperiod.optionalaggregationperiod;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.executionstatusmanage.optionalperiodprocess.AggrPeriodExcution;
import nts.uk.ctx.at.record.dom.executionstatusmanage.optionalperiodprocess.AggrPeriodExcutionRepository;
import nts.uk.ctx.at.record.dom.executionstatusmanage.optionalperiodprocess.periodexcution.ExecutionStatus;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Stateless
public class AggregatePeriodDomainServiceImpl implements AggregatePeriodDomainService{
	
	@Inject
	private AggrPeriodExcutionRepository repo;

	@Override
	public AggProcessState checkAggrPeriod(String companyId, String excuteId,
			DatePeriod datePeriod) {

		// 正常終了 : 0
		// 中断 : 1
		AggProcessState status = AggProcessState.SUCCESS;
		Optional<AggrPeriodExcution> optional = repo.findByAggr(companyId,excuteId);
		
		// 中断依頼が出されているかチェックする
		if (optional.isPresent() && optional.get().getExecutionStatus().isPresent() 
				&& optional.get().getExecutionStatus().get().value == ExecutionStatus.STOP_EXECUTION.value) {
			return AggProcessState.INTERRUPTION;
		}
		//TODO goi tinh toan
		
		// 中断依頼が出されているかチェックする
		if (optional.isPresent() && optional.get().getExecutionStatus().isPresent() 
				&& optional.get().getExecutionStatus().get().value == ExecutionStatus.STOP_EXECUTION.value) {
			return AggProcessState.INTERRUPTION;
		}
		
		
		return status;
		
		
	}
}
