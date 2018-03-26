package nts.uk.ctx.at.record.app.command.divergence.time.history;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.record.dom.divergence.time.history.DivergenceReferenceTimeUsageUnitGetMemento;
import nts.uk.shr.com.context.AppContexts;

@Getter
@Setter
public class DivergenceReferenceTimeUsageUnitDto implements DivergenceReferenceTimeUsageUnitGetMemento{
	
	/** The work type usage set */
	private BigDecimal workTypeUseSet;
	
	/**
	 * Instantiates a new divergence ref time usage unit dto.
	 */
	public DivergenceReferenceTimeUsageUnitDto(){
		super();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.divergence.time.history.DivergenceReferenceTimeUsageUnitGetMemento#getCompanyId()
	 */
	@Override
	public String getCompanyId() {
		return AppContexts.user().companyId();
	}
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.divergence.time.history.DivergenceReferenceTimeUsageUnitGetMemento#getWorkTypeUseSet()
	 */
	@Override
	public BigDecimal getWorkTypeUseSet(){
		return this.workTypeUseSet;
	}
	
}
