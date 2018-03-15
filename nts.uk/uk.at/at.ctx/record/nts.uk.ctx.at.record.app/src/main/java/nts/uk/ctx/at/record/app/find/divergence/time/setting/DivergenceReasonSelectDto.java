package nts.uk.ctx.at.record.app.find.divergence.time.setting;

import nts.uk.ctx.at.record.dom.divergence.time.setting.DivergenceInputRequired;
import nts.uk.ctx.at.record.dom.divergence.time.setting.DivergenceReason;
import nts.uk.ctx.at.record.dom.divergence.time.setting.DivergenceReasonCode;
import nts.uk.ctx.at.record.dom.divergence.time.setting.DivergenceReasonSelectSetMemento;

// TODO: Auto-generated Javadoc
/**
 * The Class DivergenceReasonSelectDto.
 */
public class DivergenceReasonSelectDto implements DivergenceReasonSelectSetMemento {

	/** The divergence reason code. */
	private String divergenceReasonCode;

	/** The reason. */
	private String reason;

	/** The reason required. */
	int reasonRequired;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.divergence.time.setting.
	 * DivergenceReasonSelectSetMemento#setDivergenceReasonCode(nts.uk.ctx.at.
	 * record.dom.divergence.time.setting.DivergenceReasonCode)
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
		// TODO Auto-generated method stub
		this.divergenceReasonCode = reason.toString();

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
		// TODO Auto-generated method stub
		this.reasonRequired = reasonRequired.value;
	}

}
