/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.infra.repository.company.organization.catetory;

import lombok.Setter;
import nts.uk.ctx.basic.dom.company.organization.category.ManagementCategoryCode;
import nts.uk.ctx.basic.dom.company.organization.category.ManagementCategoryGetMemento;
import nts.uk.ctx.basic.dom.company.organization.category.ManagementCategoryName;
import nts.uk.ctx.basic.infra.entity.company.organization.catetory.CclmtManagementCategory;

/**
 * The Class JpaManagementCategoryGetMemento.
 */
public class JpaManagementCategoryGetMemento implements ManagementCategoryGetMemento{
	
	/** The cclmt management category. */
	@Setter
	private CclmtManagementCategory cclmtManagementCategory;
	
	public JpaManagementCategoryGetMemento(CclmtManagementCategory cclmtManagementCategory) {
		this.cclmtManagementCategory = cclmtManagementCategory;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.basic.dom.company.organization.category.
	 * ManagementCategoryGetMemento#getCompanyId()
	 */
	@Override
	public String getCompanyId() {
		return this.cclmtManagementCategory.getCclmtManagementCategoryPK().getCcid();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.basic.dom.company.organization.category.
	 * ManagementCategoryGetMemento#getManagementCategoryCode()
	 */
	@Override
	public ManagementCategoryCode getManagementCategoryCode() {
		return new ManagementCategoryCode(
			this.cclmtManagementCategory.getCclmtManagementCategoryPK().getCode());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.basic.dom.company.organization.category.
	 * ManagementCategoryGetMemento#getManagementCategoryName()
	 */
	@Override
	public ManagementCategoryName getManagementCategoryName() {
		return new ManagementCategoryName(this.cclmtManagementCategory.getName());
	}
}
