/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.infra.repository.company.organization.catetory;

import javax.ejb.Stateless;

import lombok.Setter;
import nts.uk.ctx.basic.dom.company.organization.category.CompanyId;
import nts.uk.ctx.basic.dom.company.organization.category.ManagementCategoryCode;
import nts.uk.ctx.basic.dom.company.organization.category.ManagementCategoryName;
import nts.uk.ctx.basic.dom.company.organization.category.ManagementCategorySetMemento;
import nts.uk.ctx.basic.infra.entity.company.organization.catetory.CclmtManagementCategory;
import nts.uk.ctx.basic.infra.entity.company.organization.catetory.CclmtManagementCategoryPK;

/**
 * The Class JpaManagementCategorySetMemento.
 */
public class JpaManagementCategorySetMemento implements ManagementCategorySetMemento{
	
	/** The cclmt management category. */
	@Setter
	private CclmtManagementCategory cclmtManagementCategory;
	
	/**
	 * Instantiates a new jpa management category set memento.
	 *
	 * @param cclmtManagementCategory the cclmt management category
	 */
	public JpaManagementCategorySetMemento(CclmtManagementCategory cclmtManagementCategory) {
		this.cclmtManagementCategory = cclmtManagementCategory;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.basic.dom.company.organization.category.
	 * ManagementCategorySetMemento#setCompanyId(java.lang.String)
	 */
	@Override
	public void setCompanyId(CompanyId companyId) {
		CclmtManagementCategoryPK pk = new CclmtManagementCategoryPK();
		pk.setCcid(companyId.v());
		this.cclmtManagementCategory.setCclmtManagementCategoryPK(pk);
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
		CclmtManagementCategoryPK pk = this.cclmtManagementCategory.getCclmtManagementCategoryPK();
		pk.setCode(managementCategoryCode.v());
		this.cclmtManagementCategory.setCclmtManagementCategoryPK(pk);
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
		this.cclmtManagementCategory.setName(managementCategoryName.v());
	}

}
