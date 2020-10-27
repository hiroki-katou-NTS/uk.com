package nts.uk.ctx.at.record.infra.repository.divergence.time.history;

import java.math.BigDecimal;

import nts.uk.ctx.at.record.dom.divergence.time.history.DivergenceReferenceTimeUsageUnitSetMemento;
import nts.uk.ctx.at.record.infra.entity.divergence.time.history.KrcmtDvgcUnitSet;

/**
 * The Class JpaDivergenceReferenceTimeUsageUnitSetMemento.
 */
public class JpaDivergenceReferenceTimeUsageUnitSetMemento implements DivergenceReferenceTimeUsageUnitSetMemento{

	/** The entity. */
	private KrcmtDvgcUnitSet krcmtDvgcUnitSet;
		
	/**
	 * Instantiates a new jpa divergence reference time usage unit set memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaDivergenceReferenceTimeUsageUnitSetMemento(KrcmtDvgcUnitSet entity) {
		this.krcmtDvgcUnitSet = entity;
	}	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.divergence.time.history.
	 * DivergenceReferenceTimeUsageUnitSetMemento#setCompanyId(java.lang.
	 * String)
	 */
	@Override
	public void setCompanyId(String companyId) {
		this.krcmtDvgcUnitSet.setCid(companyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.divergence.time.history.
	 * DivergenceReferenceTimeUsageUnitSetMemento#setWorkTypeUseSet(java.lang.
	 * BigDecimal)
	 */
	@Override
	public void setWorkTypeUseSet(boolean workTypeUseSet) {
		this.krcmtDvgcUnitSet.setWorktypeUseSet(workTypeUseSet ? BigDecimal.ONE : BigDecimal.ZERO);
	}

}
