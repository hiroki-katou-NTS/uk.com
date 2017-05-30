/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.dom.company.organization.category;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * The Class Classification.
 */
@Getter
public class ManagementCategory extends DomainObject{

	/** The company id. */
	private String companyId;
	
	/** The classification code. */
	private ManagementCategoryCode managementCategoryCode;
	
	/** The classification name. */
	private ManagementCategoryName managementCategoryName;
	
	/**
	 * Instantiates a new classification.
	 *
	 * @param memento the memento
	 */
	public ManagementCategory(ManagementCategoryGetMemento memento) {
		this.companyId = memento.getCompanyId();
		this.managementCategoryCode = memento.getManagementCategoryCode();
		this.managementCategoryName = memento.getManagementCategoryName();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(ManagementCategorySetMemento memento) {
		memento.setCompanyId(this.companyId);
		memento.setManagementCategoryCode(this.managementCategoryCode);
		memento.setManagementCategoryName(this.managementCategoryName);
	}
	
}
