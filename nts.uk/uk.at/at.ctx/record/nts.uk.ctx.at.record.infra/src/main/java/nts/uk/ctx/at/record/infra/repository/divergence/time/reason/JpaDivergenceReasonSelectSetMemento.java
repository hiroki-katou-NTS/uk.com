package nts.uk.ctx.at.record.infra.repository.divergence.time.reason;

import java.math.BigDecimal;

import nts.uk.ctx.at.record.dom.divergence.time.reason.DivergenceInputRequired;
import nts.uk.ctx.at.record.dom.divergence.time.reason.DivergenceReason;
import nts.uk.ctx.at.record.dom.divergence.time.reason.DivergenceReasonCode;
import nts.uk.ctx.at.record.dom.divergence.time.reason.DivergenceReasonSelectSetMemento;
import nts.uk.ctx.at.record.infra.entity.divergence.reason.KrcmtDvgcReason;
import nts.uk.ctx.at.record.infra.entity.divergence.reason.KrcmtDvgcReasonPK;

/**
 * The Class JpaDivergenceReasonSelectRepositorySetMemento.
 */
public class JpaDivergenceReasonSelectSetMemento implements DivergenceReasonSelectSetMemento {

	/** The entity. */
	private KrcmtDvgcReason entity;

	/**
	 * Instantiates a new jpa divergence reason select repository set memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaDivergenceReasonSelectSetMemento(KrcmtDvgcReason entity) {
		if (entity.getId() == null) {
			KrcmtDvgcReasonPK PK = new KrcmtDvgcReasonPK();
			entity.setId(PK);
		}

		this.entity = entity;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.divergence.time.reason.
	 * DivergenceReasonSelectSetMemento#setDivergenceReasonCode(nts.uk.ctx.at.
	 * record.dom.divergence.time.reason.DivergenceReasonCode)
	 */
	@Override
	public void setDivergenceReasonCode(DivergenceReasonCode divergenceReasonCode) {
		this.entity.getId().setReasonCd(divergenceReasonCode.toString());

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.divergence.time.reason.
	 * DivergenceReasonSelectSetMemento#setReason(nts.uk.ctx.at.record.dom.
	 * divergence.time.reason.DivergenceReason)
	 */
	@Override
	public void setReason(DivergenceReason reason) {
		this.entity.setReason(reason.toString());

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.divergence.time.reason.
	 * DivergenceReasonSelectSetMemento#setReasonRequired(nts.uk.ctx.at.record.
	 * dom.divergence.time.reason.DivergenceInputRequired)
	 */
	@Override
	public void setReasonRequired(DivergenceInputRequired reasonRequired) {
		this.entity.setReasonRequired(new BigDecimal(reasonRequired.value));
	}
}
