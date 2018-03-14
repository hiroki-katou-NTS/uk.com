package nts.uk.ctx.at.record.app.find.divergence.time.setting;

import java.util.List;

import lombok.Getter;
import nts.uk.ctx.at.record.dom.divergence.time.history.DivergenceType;
import nts.uk.ctx.at.record.dom.divergence.time.setting.DivergenceTimeErrorCancelMethod;
import nts.uk.ctx.at.record.dom.divergence.time.setting.DivergenceTimeName;
import nts.uk.ctx.at.record.dom.divergence.time.setting.DivergenceTimeSetMemento;
import nts.uk.ctx.at.record.dom.divergence.time.setting.DivergenceTimeUseSet;


/**
 * The Class DivergenceTimeDto.
 */
@Getter
public class DivergenceTimeDto implements DivergenceTimeSetMemento{
	
	/** The divergence time no. */
	private int divergenceTimeNo;
	
	/** The Use classification. */
	private int divergenceTimeUseSet;
	
	/**  The divergence time name. */
	private String divergenceTimeName;
	
	/**  The divergence type. */
	private int divType;
	
	/**  The divergence time error cancel method. */
	private boolean reasonInput;
	
	/** The reason select. */
	private boolean reasonSelect;
	
	/**
	 * Instantiates a new divergence time dto.
	 */
	public DivergenceTimeDto() {
		super();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.divergence.time.setting.DivergenceTimeSetMemento#setDivergenceTimeNo(int)
	 */
	@Override
	public void setDivergenceTimeNo(int divergenceTimeNo) {
		this.divergenceTimeNo =  divergenceTimeNo;
		
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.divergence.time.setting.DivergenceTimeSetMemento#setCompanyId(java.lang.String)
	 */
	@Override
	public void setCompanyId(String companyId) {
		//no coding
		
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.divergence.time.setting.DivergenceTimeSetMemento#setDivTimeUseSet(nts.uk.ctx.at.record.dom.divergence.time.setting.DivergenceTimeUseSet)
	 */
	@Override
	public void setDivTimeUseSet(DivergenceTimeUseSet divTimeUset) {
		this.divergenceTimeUseSet = divTimeUset.value;
		
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.divergence.time.setting.DivergenceTimeSetMemento#setDivTimeName(nts.uk.ctx.at.record.dom.divergence.time.setting.DivergenceTimeName)
	 */
	@Override
	public void setDivTimeName(DivergenceTimeName divTimeName) {
		this.divergenceTimeName = divTimeName.toString();
		
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.divergence.time.setting.DivergenceTimeSetMemento#setDivType(nts.uk.ctx.at.record.dom.divergence.time.history.DivergenceType)
	 */
	@Override
	public void setDivType(DivergenceType divType) {
		this.divType = divType.value;
		
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.divergence.time.setting.DivergenceTimeSetMemento#setErrorCancelMedthod(nts.uk.ctx.at.record.dom.divergence.time.setting.DivergenceTimeErrorCancelMethod)
	 */
	@Override
	public void setErrorCancelMedthod(DivergenceTimeErrorCancelMethod errorCancelMedthod) {
		this.reasonInput = errorCancelMedthod.isReasonInputed();
		this.reasonSelect = errorCancelMedthod.isReasonSelected();
		
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.divergence.time.setting.DivergenceTimeSetMemento#setTarsetItems(java.util.List)
	 */
	@Override
	public void setTarsetItems(List<Double> targetItems) {
		//no code
		
	}
	
	
	
	
}
