package nts.uk.ctx.at.record.dom.divergence.time.message;

/**
 * The Interface WorkTypeDivergenceTimeErrorAlarmMessageRepository.
 */
public interface WorkTypeDivergenceTimeErrorAlarmMessageRepository {

	/**
	 * Gets the by divergence time no.
	 *
	 * @param divergenceTimeNo
	 *            the divergence time no
	 * @return the by divergence time no
	 */
	public WorkTypeDivergenceTimeErrorAlarmMessage getByDivergenceTimeNo(Integer divergenceTimeNo);

	/**
	 * Adds the.
	 *
	 * @param message
	 *            the message
	 */
	public void add(WorkTypeDivergenceTimeErrorAlarmMessage message);

	/**
	 * Update.
	 *
	 * @param message
	 *            the message
	 */
	public void update(WorkTypeDivergenceTimeErrorAlarmMessage message);
}
