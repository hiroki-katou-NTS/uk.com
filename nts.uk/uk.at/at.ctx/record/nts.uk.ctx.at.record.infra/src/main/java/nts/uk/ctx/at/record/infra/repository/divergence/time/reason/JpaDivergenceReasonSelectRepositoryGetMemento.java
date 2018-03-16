package nts.uk.ctx.at.record.infra.repository.divergence.time.reason;

import nts.uk.ctx.at.record.dom.divergence.time.setting.DivergenceInputRequired;
import nts.uk.ctx.at.record.dom.divergence.time.setting.DivergenceReason;
import nts.uk.ctx.at.record.dom.divergence.time.setting.DivergenceReasonCode;
import nts.uk.ctx.at.record.dom.divergence.time.setting.DivergenceReasonSelectGetMemento;
import nts.uk.ctx.at.record.infra.entity.divergence.reason.KrcstDvgcReason;

// TODO: Auto-generated Javadoc
/**
 * The Class JpaDivergenceReasonSelectRepositoryGetMemento.
 */
public class JpaDivergenceReasonSelectRepositoryGetMemento implements DivergenceReasonSelectGetMemento {

	/** The entity. */
	private KrcstDvgcReason entity;

	/**
	 * Instantiates a new jpa divergence reason select repository get memento.
	 */
	JpaDivergenceReasonSelectRepositoryGetMemento() {

	}

	/**
	 * Instantiates a new jpa divergence reason select repository get memento.
	 *
	 * @param entity the entity
	 */
	JpaDivergenceReasonSelectRepositoryGetMemento(KrcstDvgcReason entity) {
		this.entity = entity;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.divergence.time.setting.DivergenceReasonSelectGetMemento#getDivergenceReasonCode()
	 */
	@Override
	public DivergenceReasonCode getDivergenceReasonCode() {
		return new DivergenceReasonCode(this.entity.getId().getReasonCode());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.divergence.time.setting.DivergenceReasonSelectGetMemento#getReason()
	 */
	@Override
	public DivergenceReason getReason() {
		return new DivergenceReason(this.entity.getReason());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.divergence.time.setting.DivergenceReasonSelectGetMemento#getReasonRequired()
	 */
	@Override
	public DivergenceInputRequired getReasonRequired() {
		return DivergenceInputRequired.valueOf(this.entity.getReasonRequired().intValue());
	}

}
