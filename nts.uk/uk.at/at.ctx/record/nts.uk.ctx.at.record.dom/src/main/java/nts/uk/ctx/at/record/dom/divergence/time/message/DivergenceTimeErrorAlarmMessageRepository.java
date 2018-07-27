package nts.uk.ctx.at.record.dom.divergence.time.message;

import java.util.List;
import java.util.Optional;

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
	public Optional<DivergenceTimeErrorAlarmMessage> findByDivergenceTimeNo(CompanyId cId, Integer divergenceTimeNo);
	
	/**
	 * Find by divergence time no list.
	 *
	 * @param cId the c id
	 * @param divergenceTimeNoList the divergence time no list
	 * @return the list
	 */
	public List<DivergenceTimeErrorAlarmMessage> findByDivergenceTimeNoList(CompanyId cId, List<Integer> divergenceTimeNoList);
	
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
