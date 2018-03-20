package nts.uk.ctx.at.record.infra.repository.divergence.time.message;

import javax.ejb.Stateless;

import nts.uk.ctx.at.record.dom.divergence.time.message.WorkTypeDivergenceTimeErrorAlarmMessage;
import nts.uk.ctx.at.record.dom.divergence.time.message.WorkTypeDivergenceTimeErrorAlarmMessageRepository;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;

/**
 * The Class JpaWorkTypeDivergenceTimeErrorAlarmMessageRepository.
 */
@Stateless
public class JpaWorkTypeDivergenceTimeErrorAlarmMessageRepository
		implements WorkTypeDivergenceTimeErrorAlarmMessageRepository {

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.divergence.time.message.WorkTypeDivergenceTimeErrorAlarmMessageRepository#getByDivergenceTimeNo(java.lang.Integer)
	 */
	@Override
	public WorkTypeDivergenceTimeErrorAlarmMessage getByDivergenceTimeNo(Integer divergenceTimeNo, CompanyId cId,
			WorkTypeCode workTypeCode) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.divergence.time.message.WorkTypeDivergenceTimeErrorAlarmMessageRepository#add(nts.uk.ctx.at.record.dom.divergence.time.message.WorkTypeDivergenceTimeErrorAlarmMessage)
	 */
	@Override
	public void add(WorkTypeDivergenceTimeErrorAlarmMessage message) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.divergence.time.message.WorkTypeDivergenceTimeErrorAlarmMessageRepository#update(nts.uk.ctx.at.record.dom.divergence.time.message.WorkTypeDivergenceTimeErrorAlarmMessage)
	 */
	@Override
	public void update(WorkTypeDivergenceTimeErrorAlarmMessage message) {
		// TODO Auto-generated method stub

	}

}
