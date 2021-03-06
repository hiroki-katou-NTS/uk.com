package nts.uk.ctx.at.record.infra.repository.divergence.time.reason;

import nts.uk.ctx.at.record.dom.divergence.time.reason.DivergenceInputRequired;
import nts.uk.ctx.at.record.dom.divergence.time.reason.DivergenceReason;
import nts.uk.ctx.at.record.dom.divergence.time.reason.DivergenceReasonCode;
import nts.uk.ctx.at.record.dom.divergence.time.reason.DivergenceReasonSelectGetMemento;
import nts.uk.ctx.at.record.infra.entity.divergence.reason.KrcmtDvgcReason;

/**
 * The Class JpaDivergenceReasonSelectRepositoryGetMemento.
 */
public class JpaDivergenceReasonSelectGetMemento implements DivergenceReasonSelectGetMemento {

	/** The entity. */
	private KrcmtDvgcReason entity;

	/**
	 * Instantiates a new jpa divergence reason select repository get memento.
	 */
	public JpaDivergenceReasonSelectGetMemento() {

	}

	/**
	 * Instantiates a new jpa divergence reason select repository get memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaDivergenceReasonSelectGetMemento(KrcmtDvgcReason entity) {
		this.entity = entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.divergence.time.reason.
	 * DivergenceReasonSelectGetMemento#getDivergenceReasonCode()
	 */
	@Override
	public DivergenceReasonCode getDivergenceReasonCode() {
		return new DivergenceReasonCode(this.entity.getId().getReasonCd());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.divergence.time.reason.
	 * DivergenceReasonSelectGetMemento#getReason()
	 */
	@Override
	public DivergenceReason getReason() {
		return new DivergenceReason(this.entity.getReason());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.divergence.time.reason.
	 * DivergenceReasonSelectGetMemento#getReasonRequired()
	 */
	@Override
	public DivergenceInputRequired getReasonRequired() {
		return DivergenceInputRequired.valueOf(this.entity.getReasonRequired().intValue());
	}

}
