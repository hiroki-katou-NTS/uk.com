package nts.uk.ctx.at.record.app.command.divergence.time.reason;

import lombok.Getter;
import nts.uk.ctx.at.record.dom.divergence.time.reason.DivergenceInputRequired;
import nts.uk.ctx.at.record.dom.divergence.time.reason.DivergenceReason;
import nts.uk.ctx.at.record.dom.divergence.time.reason.DivergenceReasonCode;
import nts.uk.ctx.at.record.dom.divergence.time.reason.DivergenceReasonSelectGetMemento;

@Getter
public class DivergenceReasonSelectSaveCommand implements DivergenceReasonSelectGetMemento {
	/** The divergence time no. */
	private int divergenceTimeNo;

	/** The divergence reason code. */
	private String divergenceReasonCode;

	/** The reason. */
	private String reason;

	/** The reason required. */
	private int reasonRequired;

	/**
	 * Instantiates a new divergence reason select save command.
	 */
	public DivergenceReasonSelectSaveCommand() {
		super();
	}

	@Override
	public DivergenceReasonCode getDivergenceReasonCode() {
		return new DivergenceReasonCode(divergenceReasonCode);
	}

	@Override
	public DivergenceReason getReason() {
		return new DivergenceReason(reason);
	}

	@Override
	public DivergenceInputRequired getReasonRequired() {
		return DivergenceInputRequired.valueOf(reasonRequired);
	}

}
