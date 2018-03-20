package nts.uk.ctx.at.record.dom.divergence.time.message;

/**
 * The Interface DivergenceTimeErrorAlarmMessageRepository.
 */
public interface DivergenceTimeErrorAlarmMessageRepository {
	
	/**
	 * Gets the by divergence time no.
	 *
	 * @param divergenceTimeNo the divergence time no
	 * @return the by divergence time no
	 */
	public DivergenceTimeErrorAlarmMessage getByDivergenceTimeNo(Integer divergenceTimeNo);
	
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
