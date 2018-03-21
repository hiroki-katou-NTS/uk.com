package nts.uk.ctx.at.record.app.command.divergence.time;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.record.dom.divergence.time.DivergenceInputRequired;
import nts.uk.ctx.at.record.dom.divergence.time.DivergenceReason;
import nts.uk.ctx.at.record.dom.divergence.time.DivergenceReasonCode;
import nts.uk.ctx.at.record.dom.divergence.time.DivergenceReasonSelectGetMemento;



@Getter
@Setter
@AllArgsConstructor
public class DivergenceReasonSelectAddCommand implements DivergenceReasonSelectGetMemento {

	/** The divergence time no. */
	private int divergenceTimeNo;

	/** The divergence reason code. */
	private String divergenceReasonCode;

	/** The reason. */
	private String reason;

	/** The reason required. */
	private int reasonRequired;
	
	public DivergenceReasonSelectAddCommand(){
		
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.divergence.time.setting.DivergenceReasonSelectGetMemento#getDivergenceReasonCode()
	 */
	@Override
	public DivergenceReasonCode getDivergenceReasonCode() {
		return new DivergenceReasonCode(divergenceReasonCode);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.divergence.time.setting.DivergenceReasonSelectGetMemento#getReason()
	 */
	@Override
	public DivergenceReason getReason() {
		return new DivergenceReason(reason);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.divergence.time.setting.DivergenceReasonSelectGetMemento#getReasonRequired()
	 */
	@Override
	public DivergenceInputRequired getReasonRequired() {
		return DivergenceInputRequired.valueOf(reasonRequired);
	}

}
