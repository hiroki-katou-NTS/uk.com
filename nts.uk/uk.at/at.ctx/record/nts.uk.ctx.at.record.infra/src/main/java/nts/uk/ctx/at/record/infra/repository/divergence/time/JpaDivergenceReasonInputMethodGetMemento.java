package nts.uk.ctx.at.record.infra.repository.divergence.time;

import java.util.List;

import nts.uk.ctx.at.record.dom.divergence.time.DivergenceReasonInputMethodGetMemento;
import nts.uk.ctx.at.record.dom.divergence.time.reason.DivergenceReasonSelect;
import nts.uk.ctx.at.record.infra.entity.divergence.time.KrcmtDvgcTime;

/**
 * The Class JpaDivergenceReasonInputMethodRepositoryGetMemento.
 */
public class JpaDivergenceReasonInputMethodGetMemento implements DivergenceReasonInputMethodGetMemento {

	/** The entities. */
	private KrcmtDvgcTime entities;

	/**
	 * Instantiates a new jpa divergence reason input method repository get
	 * memento.
	 */
	public JpaDivergenceReasonInputMethodGetMemento() {

	}

	/**
	 * Instantiates a new jpa divergence reason input method repository get
	 * memento.
	 *
	 * @param entities
	 *            the entities
	 */
	public JpaDivergenceReasonInputMethodGetMemento(KrcmtDvgcTime entities) {

		this.entities = entities;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.divergence.time.DivergenceReasonInputMethodGetMemento#getDivergenceTimeNo()
	 */
	@Override
	public int getDivergenceTimeNo() {
		return entities.getId().getNo();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.divergence.time.DivergenceReasonInputMethodGetMemento#getCompanyId()
	 */
	@Override
	public String getCompanyId() {
		return entities.getId().getCid();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.divergence.time.DivergenceReasonInputMethodGetMemento#getDivergenceReasonInputed()
	 */
	@Override
	public boolean getDivergenceReasonInputed() {
		return entities.getDvgcReasonInputed().intValue() == 1;

	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.divergence.time.DivergenceReasonInputMethodGetMemento#getDivergenceReasonSelected()
	 */
	@Override
	public boolean getDivergenceReasonSelected() {
		return entities.getDvgcReasonSelected().intValue() == 1;

	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.divergence.time.DivergenceReasonInputMethodGetMemento#getReasons()
	 */
	@Override
	public List<DivergenceReasonSelect> getReasons() {
		return null;
	}

}
