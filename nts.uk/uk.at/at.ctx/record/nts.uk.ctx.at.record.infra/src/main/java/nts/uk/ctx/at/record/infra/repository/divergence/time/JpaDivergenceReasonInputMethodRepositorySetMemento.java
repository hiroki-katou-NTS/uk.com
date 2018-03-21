package nts.uk.ctx.at.record.infra.repository.divergence.time;

import java.math.BigDecimal;
import java.util.List;

import nts.uk.ctx.at.record.dom.divergence.time.DivergenceReasonInputMethodSetMemento;
import nts.uk.ctx.at.record.dom.divergence.time.DivergenceReasonSelect;
import nts.uk.ctx.at.record.infra.entity.divergence.time.KrcstDvgcTime;

public class JpaDivergenceReasonInputMethodRepositorySetMemento implements DivergenceReasonInputMethodSetMemento {

	/** The entities. */
	private KrcstDvgcTime entity;

	/**
	 * Instantiates a new jpa divergence time repository set memento.
	 */
	public JpaDivergenceReasonInputMethodRepositorySetMemento() {

	}

	/**
	 * Instantiates a new jpa divergence time repository set memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaDivergenceReasonInputMethodRepositorySetMemento(KrcstDvgcTime entity) {
		this.entity = entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.divergence.time.setting.
	 * DivergenceReasonInputMethodSetMemento#setDivergenceTimeNo(int)
	 */
	@Override
	public void setDivergenceTimeNo(int DivergenceTimeNo) {

		this.entity.getId().setNo(DivergenceTimeNo);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.divergence.time.setting.
	 * DivergenceReasonInputMethodSetMemento#setCompanyId(java.lang.String)
	 */
	@Override
	public void setCompanyId(String companyId) {
		// No code

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.divergence.time.setting.
	 * DivergenceReasonInputMethodSetMemento#setDivergenceReasonInputed(boolean)
	 */
	@Override
	public void setDivergenceReasonInputed(boolean divergenceReasonInputed) {
		if (divergenceReasonInputed)
			this.entity.setReasonInputCanceled(new BigDecimal(1));
		else
			this.entity.setReasonInputCanceled(new BigDecimal(0));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.divergence.time.setting.
	 * DivergenceReasonInputMethodSetMemento#setDivergenceReasonSelected(
	 * boolean)
	 */
	@Override
	public void setDivergenceReasonSelected(boolean divergenceReasonSelected) {
		if (divergenceReasonSelected)
			this.entity.setReasonSelectCanceled(new BigDecimal(1));
		else
			this.entity.setReasonSelectCanceled(new BigDecimal(0));

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.divergence.time.setting.
	 * DivergenceReasonInputMethodSetMemento#setReasons(java.util.List)
	 */
	@Override
	public void setReasons(List<DivergenceReasonSelect> reason) {
		// no code

	}

}
