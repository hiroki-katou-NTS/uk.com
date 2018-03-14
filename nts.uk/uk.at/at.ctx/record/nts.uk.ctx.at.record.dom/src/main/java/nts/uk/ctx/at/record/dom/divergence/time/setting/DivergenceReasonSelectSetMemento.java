package nts.uk.ctx.at.record.dom.divergence.time.setting;

public interface DivergenceReasonSelectSetMemento {

	/**
	 * Sets the divergence reason code.
	 *
	 * @return the divergence reason code
	 */
	void setDivergenceReasonCode(DivergenceReasonCode divergenceReasonCode);
	/**
	 * Sets the divergence reason.
	 *
	 * @return the divergence reason
	 */
	void setReason (DivergenceReason reason);
	/**
	 * Sets the divergence reason required.
	 *
	 * @return the divergence reason required
	 */
	void setReasonRequired(DivergenceInputRequired reasonRequired);
}
