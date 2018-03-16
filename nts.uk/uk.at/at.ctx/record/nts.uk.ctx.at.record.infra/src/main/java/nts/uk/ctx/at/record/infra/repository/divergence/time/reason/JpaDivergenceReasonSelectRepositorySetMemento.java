package nts.uk.ctx.at.record.infra.repository.divergence.time.reason;

import java.math.BigDecimal;

import nts.uk.ctx.at.record.dom.divergence.time.setting.DivergenceInputRequired;
import nts.uk.ctx.at.record.dom.divergence.time.setting.DivergenceReason;
import nts.uk.ctx.at.record.dom.divergence.time.setting.DivergenceReasonCode;
import nts.uk.ctx.at.record.dom.divergence.time.setting.DivergenceReasonSelectSetMemento;
import nts.uk.ctx.at.record.infra.entity.divergence.reason.KrcstDvgcReason;

// TODO: Auto-generated Javadoc
/**
 * The Class JpaDivergenceReasonSelectRepositorySetMemento.
 */
public class JpaDivergenceReasonSelectRepositorySetMemento implements DivergenceReasonSelectSetMemento {

	/** The entity. */
	private KrcstDvgcReason entity;

	/**
	 * Instantiates a new jpa divergence reason select repository set memento.
	 */
	public JpaDivergenceReasonSelectRepositorySetMemento() {

	}

	/**
	 * Instantiates a new jpa divergence reason select repository set memento.
	 *
	 * @param entity the entity
	 */
	public JpaDivergenceReasonSelectRepositorySetMemento(KrcstDvgcReason entity) {
		this.entity = entity;

	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.divergence.time.setting.DivergenceReasonSelectSetMemento#setDivergenceReasonCode(nts.uk.ctx.at.record.dom.divergence.time.setting.DivergenceReasonCode)
	 */
	@Override
	public void setDivergenceReasonCode(DivergenceReasonCode divergenceReasonCode) {
		this.entity.getId().setReasonCode(divergenceReasonCode.toString());

	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.divergence.time.setting.DivergenceReasonSelectSetMemento#setReason(nts.uk.ctx.at.record.dom.divergence.time.setting.DivergenceReason)
	 */
	@Override
	public void setReason(DivergenceReason reason) {
		this.entity.setReason(reason.toString());

	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.divergence.time.setting.DivergenceReasonSelectSetMemento#setReasonRequired(nts.uk.ctx.at.record.dom.divergence.time.setting.DivergenceInputRequired)
	 */
	@Override
	public void setReasonRequired(DivergenceInputRequired reasonRequired) {
		this.entity.setReasonRequired(new BigDecimal(reasonRequired.value));
	}
}
