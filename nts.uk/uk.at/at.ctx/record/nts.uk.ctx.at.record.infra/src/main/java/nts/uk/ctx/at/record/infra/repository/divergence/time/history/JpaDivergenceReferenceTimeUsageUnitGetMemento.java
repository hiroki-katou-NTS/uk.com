package nts.uk.ctx.at.record.infra.repository.divergence.time.history;

import java.math.BigDecimal;

import lombok.Setter;
import nts.uk.ctx.at.record.dom.divergence.time.history.DivergenceReferenceTimeUsageUnitGetMemento;
import nts.uk.ctx.at.record.infra.entity.divergence.time.history.KrcmtDvgcUnitSet;

/**
 * The Class JpaDivergenceReferenceTimeUsageUnitGetMemento.
 */
public class JpaDivergenceReferenceTimeUsageUnitGetMemento implements DivergenceReferenceTimeUsageUnitGetMemento {

	/** The entity. */
	@Setter
	private KrcmtDvgcUnitSet krcmtDvgcUnitSet;

	/**
	 * Instantiates a new jpa divergence reference time usage unit get memento.
	 */
	public JpaDivergenceReferenceTimeUsageUnitGetMemento() {

	}

	/**
	 * Instantiates a new jpa divergence reference time usage unit get memento.
	 *
	 * @param krcmtDvgcUnitSet the krcst drt use unit
	 */
	public JpaDivergenceReferenceTimeUsageUnitGetMemento(KrcmtDvgcUnitSet krcmtDvgcUnitSet) {
		this.krcmtDvgcUnitSet = krcmtDvgcUnitSet;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.divergence.time.history.
	 * DivergenceReferenceTimeUsageUnitGetMemento#getCompanyId()
	 */
	@Override
	public String getCompanyId() {
		return this.krcmtDvgcUnitSet.getCid();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.divergence.time.history.
	 * DivergenceReferenceTimeUsageUnitGetMemento#getWorkTypeUseSet()
	 */
	@Override
	public boolean getWorkTypeUseSet() {
		return this.krcmtDvgcUnitSet.getWorktypeUseSet().equals(BigDecimal.ONE);
	}

}
