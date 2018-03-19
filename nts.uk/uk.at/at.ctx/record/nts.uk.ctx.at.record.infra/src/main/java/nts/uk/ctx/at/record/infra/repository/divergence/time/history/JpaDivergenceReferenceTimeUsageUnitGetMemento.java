package nts.uk.ctx.at.record.infra.repository.divergence.time.history;

import java.math.BigDecimal;

import lombok.Setter;
import nts.uk.ctx.at.record.dom.divergence.time.history.DivergenceReferenceTimeUsageUnitGetMemento;
import nts.uk.ctx.at.record.infra.entity.divergence.time.KrcstDrtUseUnit;

public class JpaDivergenceReferenceTimeUsageUnitGetMemento implements DivergenceReferenceTimeUsageUnitGetMemento{
	
	/** The entity. */
	@Setter
	private KrcstDrtUseUnit krcstDrtUseUnit;
	
	public JpaDivergenceReferenceTimeUsageUnitGetMemento() {
		
	}
	
	public JpaDivergenceReferenceTimeUsageUnitGetMemento(KrcstDrtUseUnit krcstDrtUseUnit){
		this.krcstDrtUseUnit = krcstDrtUseUnit;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.divergence.time.history.
	 * DivergenceReferenceTimeUsageUnitGetMemento#getCompanyId()
	 */
	@Override
	public String getCompanyId() {
		// TODO Auto-generated method stub
		return this.krcstDrtUseUnit.getCid();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.divergence.time.history.
	 * DivergenceReferenceTimeUsageUnitGetMemento#getWorkTypeUseSet()
	 */
	@Override
	public BigDecimal getWorkTypeUseSet() {
		// TODO Auto-generated method stub
		return this.krcstDrtUseUnit.getWorktypeUseSet();
	}

}
