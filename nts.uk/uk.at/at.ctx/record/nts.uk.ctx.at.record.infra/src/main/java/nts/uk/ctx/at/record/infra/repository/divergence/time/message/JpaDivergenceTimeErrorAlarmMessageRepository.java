package nts.uk.ctx.at.record.infra.repository.divergence.time.message;

import javax.ejb.Stateless;

import nts.uk.ctx.at.record.dom.divergence.time.message.DivergenceTimeErrorAlarmMessage;
import nts.uk.ctx.at.record.dom.divergence.time.message.DivergenceTimeErrorAlarmMessageRepository;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Class JpaDivergenceTimeErrorAlarmMessageRepository.
 */
@Stateless
public class JpaDivergenceTimeErrorAlarmMessageRepository implements DivergenceTimeErrorAlarmMessageRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.divergence.time.message.
	 * DivergenceTimeErrorAlarmMessageRepository#getByDivergenceTimeNo(java.lang.
	 * Integer)
	 */
	@Override
	public DivergenceTimeErrorAlarmMessage findByDivergenceTimeNo(CompanyId cId, Integer divergenceTimeNo) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.divergence.time.message.
	 * DivergenceTimeErrorAlarmMessageRepository#add(nts.uk.ctx.at.record.dom.
	 * divergence.time.message.DivergenceTimeErrorAlarmMessage)
	 */
	@Override
	public void add(DivergenceTimeErrorAlarmMessage message) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.divergence.time.message.
	 * DivergenceTimeErrorAlarmMessageRepository#update(nts.uk.ctx.at.record.dom.
	 * divergence.time.message.DivergenceTimeErrorAlarmMessage)
	 */
	@Override
	public void update(DivergenceTimeErrorAlarmMessage message) {
		// TODO Auto-generated method stub

	}

}
