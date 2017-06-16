/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.dom.company;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

@Getter
public class Company extends AggregateRoot {

	/** The company code. */
	// 会社コード
	private CompanyCode companyCode;

	/** The company id. */
	//会社ID
	private CompanyId companyId;

	/** The start month. */
	// 期首月
	private StartMonth startMonth;

	
	/**
	 * Instantiates a new company.
	 *
	 * @param memento the memento
	 */
	public Company(CompanyGetMemento memento){
		this.companyCode = memento.getCompanyCode();
		this.companyId = memento.getCompanyId();
		this.startMonth= memento.getStartMonth();
	}
	
	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(CompanySetMemento memento){
		memento.setCompanyCode(this.companyCode);
		memento.setCompanyId(this.companyId);
		memento.setStartMonth(this.startMonth);
	}
}
