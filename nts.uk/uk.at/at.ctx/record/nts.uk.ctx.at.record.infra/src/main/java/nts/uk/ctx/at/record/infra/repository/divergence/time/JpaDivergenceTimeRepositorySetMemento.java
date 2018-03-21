package nts.uk.ctx.at.record.infra.repository.divergence.time;

import java.math.BigDecimal;
import java.util.List;

import nts.uk.ctx.at.record.dom.divergence.time.DivergenceTimeErrorCancelMethod;
import nts.uk.ctx.at.record.dom.divergence.time.DivergenceTimeName;
import nts.uk.ctx.at.record.dom.divergence.time.DivergenceTimeSetMemento;
import nts.uk.ctx.at.record.dom.divergence.time.DivergenceTimeUseSet;
import nts.uk.ctx.at.record.dom.divergence.time.history.DivergenceType;
import nts.uk.ctx.at.record.infra.entity.divergence.time.KrcstDvgcTime;

public class JpaDivergenceTimeRepositorySetMemento implements DivergenceTimeSetMemento {

	/** The entity. */
	private KrcstDvgcTime entity;

	/**
	 * Instantiates a new jpa divergence time repository set memento.
	 */
	public JpaDivergenceTimeRepositorySetMemento() {

	}

	/**
	 * Instantiates a new jpa divergence time repository set memento.
	 *
	 * @param entity the entity
	 */
	public JpaDivergenceTimeRepositorySetMemento(KrcstDvgcTime entity) {
		this.entity = entity;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.divergence.time.setting.DivergenceTimeSetMemento#setDivergenceTimeNo(int)
	 */
	@Override
	public void setDivergenceTimeNo(int DivergenceTimeNo) {
		this.entity.getId().setNo(DivergenceTimeNo);
		;

	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.divergence.time.setting.DivergenceTimeSetMemento#setCompanyId(java.lang.String)
	 */
	@Override
	public void setCompanyId(String CompanyId) {
		// No coding

	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.divergence.time.setting.DivergenceTimeSetMemento#setDivTimeUseSet(nts.uk.ctx.at.record.dom.divergence.time.setting.DivergenceTimeUseSet)
	 */
	@Override
	public void setDivTimeUseSet(DivergenceTimeUseSet divTimeUset) {
		this.entity.setDvgcTimeUseSet(new BigDecimal(divTimeUset.value));

	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.divergence.time.setting.DivergenceTimeSetMemento#setDivTimeName(nts.uk.ctx.at.record.dom.divergence.time.setting.DivergenceTimeName)
	 */
	@Override
	public void setDivTimeName(DivergenceTimeName divTimeName) {
		this.entity.setDvgcTimeName(divTimeName.toString());

	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.divergence.time.setting.DivergenceTimeSetMemento#setDivType(nts.uk.ctx.at.record.dom.divergence.time.history.DivergenceType)
	 */
	@Override
	public void setDivType(DivergenceType divType) {
		this.entity.setDvgcType(new BigDecimal(divType.value));

	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.divergence.time.setting.DivergenceTimeSetMemento#setErrorCancelMedthod(nts.uk.ctx.at.record.dom.divergence.time.setting.DivergenceTimeErrorCancelMethod)
	 */
	@Override
	public void setErrorCancelMedthod(DivergenceTimeErrorCancelMethod errorCancelMedthod) {
		if (errorCancelMedthod.isReasonInputed())
			this.entity.setReasonInputCanceled(new BigDecimal(1));
		else
			this.entity.setReasonInputCanceled(new BigDecimal(0));

		if (errorCancelMedthod.isReasonSelected())
			this.entity.setReasonSelectCanceled(new BigDecimal(1));
		else
			this.entity.setReasonSelectCanceled(new BigDecimal(0));

	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.divergence.time.setting.DivergenceTimeSetMemento#setTarsetItems(java.util.List)
	 */
	@Override
	public void setTarsetItems(List<Double> targetItems) {
		// no coding

	}

}
