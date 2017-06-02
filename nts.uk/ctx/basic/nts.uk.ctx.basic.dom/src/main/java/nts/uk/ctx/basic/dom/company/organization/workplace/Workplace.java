/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.dom.company.organization.workplace;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.basic.dom.common.history.Period;
import nts.uk.ctx.basic.dom.company.organization.CompanyId;

/**
 * The Class Work place.
 */
@Getter
public class Workplace extends AggregateRoot{
	
	/** The company id. */
	//会社ID
	private CompanyId companyId;
	
	/** The period. */
	//期間
	private Period period;
	
	/** The work place id. */
	//職場ID
	private WorkplaceId workplaceId;
	
	/** The work place code. */
	//職場コード
	private WorkplaceCode workplaceCode;
	
	/** The workplace name. */
	//職場名称
	private WorkplaceName workplaceName;
	
	/**
	 * Instantiates a new workplace.
	 *
	 * @param memento the memento
	 */
	public Workplace(WorkplaceGetMemento memento) {
		this.companyId = memento.getCompanyId();
		this.period = memento.getPeriod();
		this.workplaceId = memento.getWorkplaceId();
		this.workplaceCode = memento.getWorkplaceCode();
		this.workplaceName = memento.getWorkplaceName();
	}
	
	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(WorkplaceSetMemento memento){
		memento.setCompanyId(this.companyId);
		memento.setPeriod(this.period);
		memento.setWorkplaceId(this.workplaceId);
		memento.setWorkplaceCode(this.workplaceCode);
		memento.setWorkplaceName(this.workplaceName);
	}
}
