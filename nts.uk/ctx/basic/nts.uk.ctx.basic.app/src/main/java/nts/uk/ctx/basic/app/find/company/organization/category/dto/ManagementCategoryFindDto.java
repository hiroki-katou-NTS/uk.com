/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.app.find.company.organization.category.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.basic.dom.company.organization.CompanyId;
import nts.uk.ctx.basic.dom.company.organization.category.ManagementCategoryCode;
import nts.uk.ctx.basic.dom.company.organization.category.ManagementCategoryName;
import nts.uk.ctx.basic.dom.company.organization.category.ManagementCategorySetMemento;

/**
 * The Class ManagementCategoryFindDto.
 */
@Getter
@Setter
public class ManagementCategoryFindDto implements ManagementCategorySetMemento{
	
	/** The code. */
	private String code;
	
	/** The name. */
	private String name;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.basic.dom.company.organization.category.
	 * ManagementCategorySetMemento#setCompanyId(java.lang.String)
	 */
	@Override
	public void setCompanyId(CompanyId companyId) {
		// No thing code
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.basic.dom.company.organization.category.
	 * ManagementCategorySetMemento#setManagementCategoryCode(nts.uk.ctx.basic.
	 * dom.company.organization.category.ManagementCategoryCode)
	 */
	@Override
	public void setManagementCategoryCode(ManagementCategoryCode managementCategoryCode) {
		this.code = managementCategoryCode.v();
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.basic.dom.company.organization.category.
	 * ManagementCategorySetMemento#setManagementCategoryName(nts.uk.ctx.basic.
	 * dom.company.organization.category.ManagementCategoryName)
	 */
	@Override
	public void setManagementCategoryName(ManagementCategoryName managementCategoryName) {
		this.name = managementCategoryName.v();
		
	}

}
