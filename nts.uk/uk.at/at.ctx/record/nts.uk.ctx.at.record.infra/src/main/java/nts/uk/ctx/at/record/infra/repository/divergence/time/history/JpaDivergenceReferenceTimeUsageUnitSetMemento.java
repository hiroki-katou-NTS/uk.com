package nts.uk.ctx.at.record.infra.repository.divergence.time.history;

import java.math.BigDecimal;

import nts.uk.ctx.at.record.dom.divergence.time.history.DivergenceReferenceTimeUsageUnitSetMemento;
import nts.uk.ctx.at.record.infra.entity.divergence.time.KrcstDrtUseUnit;

public class JpaDivergenceReferenceTimeUsageUnitSetMemento implements DivergenceReferenceTimeUsageUnitSetMemento{

	/** The entity. */
	private KrcstDrtUseUnit krcstDrtUseUnit;
	
	/**
	 * Instantiates a new jpa divergence reference time usage unit set memento.
	 */
	public JpaDivergenceReferenceTimeUsageUnitSetMemento() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Instantiates a new jpa divergence reference time usage unit set memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaDivergenceReferenceTimeUsageUnitSetMemento(KrcstDrtUseUnit krcstDrtUseUnit) {
		// TODO Auto-generated constructor stub
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
		// TODO Auto-generated method stub
		this.krcstDrtUseUnit.setCid(companyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.divergence.time.history.
	 * DivergenceReferenceTimeUsageUnitSetMemento#setWorkTypeUseSet(java.lang.
	 * BigDecimal)
	 */
	@Override
	public void setWorkTypeUseSet(BigDecimal workTypeUseSet) {
		// TODO Auto-generated method stub
		this.krcstDrtUseUnit.setWorktypeUseSet(workTypeUseSet);
	}

}
