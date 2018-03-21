package nts.uk.ctx.at.record.app.find.divergence.time.setting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.record.dom.divergence.time.DivergenceInputRequired;
import nts.uk.ctx.at.record.dom.divergence.time.DivergenceReason;
import nts.uk.ctx.at.record.dom.divergence.time.DivergenceReasonCode;
import nts.uk.ctx.at.record.dom.divergence.time.DivergenceReasonSelectSetMemento;

// TODO: Auto-generated Javadoc
/**
 * Gets the reason required.
 *
 * @return the reason required
 */
@Getter

/**
 * Sets the divergence time no.
 *
 * @param divergenceTimeNo the new divergence time no
 */
@Setter

/**
 * Instantiates a new divergence reason select dto.
 *
 * @param divergenceTimeNo the divergence time no
 * @param divergenceReasonCode the divergence reason code
 * @param reason the reason
 * @param reasonRequired the reason required
 */
@AllArgsConstructor
public class DivergenceReasonSelectDto implements DivergenceReasonSelectSetMemento {

	/** The divergence time no. */
	private int divergenceTimeNo;
	
	/** The divergence reason code. */
	private String divergenceReasonCode;

	/** The reason. */
	private String reason;

	/** The reason required. */
	private int reasonRequired;

	/**
	 * Instantiates a new divergence reason select dto.
	 */
	public DivergenceReasonSelectDto() {
		super();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.divergence.time.setting.DivergenceReasonSelectSetMemento#setDivergenceReasonCode(nts.uk.ctx.at.record.dom.divergence.time.setting.DivergenceReasonCode)
	 */
	@Override
	public void setDivergenceReasonCode(DivergenceReasonCode divergenceReasonCode) {
		this.divergenceReasonCode = divergenceReasonCode.toString();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.divergence.time.setting.
	 * DivergenceReasonSelectSetMemento#setReason(nts.uk.ctx.at.record.dom.
	 * divergence.time.setting.DivergenceReason)
	 */
	@Override
	public void setReason(DivergenceReason reason) {
		this.reason = reason.toString();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.divergence.time.setting.
	 * DivergenceReasonSelectSetMemento#setReasonRequired(nts.uk.ctx.at.record.
	 * dom.divergence.time.setting.DivergenceInputRequired)
	 */
	@Override
	public void setReasonRequired(DivergenceInputRequired reasonRequired) {

		this.reasonRequired = reasonRequired.value;
	}

}
