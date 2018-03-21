package nts.uk.ctx.at.record.infra.repository.divergence.time;

import java.util.List;

import nts.uk.ctx.at.record.dom.divergence.time.DivergenceReasonInputMethodGetMemento;
import nts.uk.ctx.at.record.dom.divergence.time.DivergenceReasonSelect;
import nts.uk.ctx.at.record.infra.entity.divergence.time.KrcstDvgcTime;

// TODO: Auto-generated Javadoc
/**
 * The Class JpaDivergenceReasonInputMethodRepositoryGetMemento.
 */
public class JpaDivergenceReasonInputMethodRepositoryGetMemento implements DivergenceReasonInputMethodGetMemento {

	/** The entities. */
	private KrcstDvgcTime entities;

	/**
	 * Instantiates a new jpa divergence reason input method repository get
	 * memento.
	 */
	public JpaDivergenceReasonInputMethodRepositoryGetMemento() {

	}

	/**
	 * Instantiates a new jpa divergence reason input method repository get
	 * memento.
	 *
	 * @param entities
	 *            the entities
	 */
	public JpaDivergenceReasonInputMethodRepositoryGetMemento(KrcstDvgcTime entities) {

		this.entities = entities;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.divergence.time.setting.
	 * DivergenceReasonInputMethodGetMemento#getDivergenceTimeNo()
	 */
	@Override
	public int getDivergenceTimeNo() {
		return entities.getId().getNo();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.divergence.time.setting.
	 * DivergenceReasonInputMethodGetMemento#getCompanyId()
	 */
	@Override
	public String getCompanyId() {
		return entities.getId().getCid();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.divergence.time.setting.
	 * DivergenceReasonInputMethodGetMemento#getDivergenceReasonInputed()
	 */
	@Override
	public boolean getDivergenceReasonInputed() {		
		if (entities.getDvgcReasonInputed().intValue() == 1)
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.divergence.time.setting.
	 * DivergenceReasonInputMethodGetMemento#getDivergenceReasonSelected()
	 */
	@Override
	public boolean getDivergenceReasonSelected() {
		if (entities.getDvgcReasonInputed().intValue() == 1)
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.divergence.time.setting.
	 * DivergenceReasonInputMethodGetMemento#getReasons()
	 */
	@Override
	public List<DivergenceReasonSelect> getReasons() {
		return null;
	}

}
