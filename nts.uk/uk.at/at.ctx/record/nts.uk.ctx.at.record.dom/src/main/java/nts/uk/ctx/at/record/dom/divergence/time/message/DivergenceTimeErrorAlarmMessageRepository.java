package nts.uk.ctx.at.record.dom.divergence.time.message;

import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Interface DivergenceTimeErrorAlarmMessageRepository.
 */
public interface DivergenceTimeErrorAlarmMessageRepository {
	
	/**
	 * Find by divergence time no.
	 *
	 * @param cId the c id
	 * @param divergenceTimeNo the divergence time no
	 * @return the divergence time error alarm message
	 */
	public DivergenceTimeErrorAlarmMessage findByDivergenceTimeNo(CompanyId cId, Integer divergenceTimeNo);
	
	/**
	 * Adds the.
	 *
	 * @param message the message
	 */
	public void add(DivergenceTimeErrorAlarmMessage message);
	
	/**
	 * Update.
	 *
	 * @param message the message
	 */
	public void update(DivergenceTimeErrorAlarmMessage message);
}
