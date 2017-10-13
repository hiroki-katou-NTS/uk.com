/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.dom.workplace_old;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.bs.employee.dom.common.CompanyId;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * The Class Work place.
 */
@Getter
public class Workplace extends AggregateRoot{
	
	/** The company id. */
	//莨夂､ｾID
	private CompanyId companyId;
	
	/** The period. */
	//譛滄俣
	private DatePeriod period;
	
	/** The work place id. */
	//閨ｷ蝣ｴID
	private WorkplaceId workplaceId;
	
	/** The work place code. */
	//閨ｷ蝣ｴ繧ｳ繝ｼ繝�
	private WorkplaceCode workplaceCode;
	
	/** The workplace name. */
	//閨ｷ蝣ｴ蜷咲ｧｰ
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
