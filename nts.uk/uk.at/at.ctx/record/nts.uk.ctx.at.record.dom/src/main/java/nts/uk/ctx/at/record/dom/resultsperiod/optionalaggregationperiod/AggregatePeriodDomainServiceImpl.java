package nts.uk.ctx.at.record.dom.resultsperiod.optionalaggregationperiod;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.command.AsyncCommandHandlerContext;
import nts.uk.ctx.at.record.dom.executionstatusmanage.optionalperiodprocess.AggrPeriodExcution;
import nts.uk.ctx.at.record.dom.executionstatusmanage.optionalperiodprocess.AggrPeriodExcutionRepository;
import nts.uk.ctx.at.record.dom.executionstatusmanage.optionalperiodprocess.periodexcution.ExecutionStatus;
import nts.uk.shr.com.time.calendar.period.DatePeriod;
/**
 * 
 * @author phongtq
 * 社員の任意期間別実績を集計する
 */
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@Stateless
public class AggregatePeriodDomainServiceImpl implements AggregatePeriodDomainService{
	
	@Inject
	private AggrPeriodExcutionRepository repo;

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	@Override
	public <C> AggProcessState checkAggrPeriod(String companyId, String excuteId,
			DatePeriod datePeriod, AsyncCommandHandlerContext<C> asyn) {

		// 正常終了 : 0
		// 中断 : 1
		AggProcessState status = AggProcessState.SUCCESS;
		Optional<AggrPeriodExcution> optional = repo.findByAggr(companyId,excuteId);
		
		// 中断依頼が出されているかチェックする
		if (optional.isPresent() && optional.get().getExecutionStatus().isPresent() 
				&& optional.get().getExecutionStatus().get().value == ExecutionStatus.START_OF_INTERRUPTION.value) {
			return AggProcessState.INTERRUPTION;
		}
		//TODO goi tinh toan
		
		// 中断依頼が出されているかチェックする
		if (optional.isPresent() && optional.get().getExecutionStatus().isPresent() 
				&& optional.get().getExecutionStatus().get().value == ExecutionStatus.START_OF_INTERRUPTION.value) {
			return AggProcessState.INTERRUPTION;
		}
		
		
		return status;
		
		
	}
}
