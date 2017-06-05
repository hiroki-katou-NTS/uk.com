/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.infra.repository.company.organization.classification;

import lombok.Setter;
import nts.uk.ctx.basic.dom.company.organization.CompanyId;
import nts.uk.ctx.basic.dom.company.organization.classification.ClassificationCode;
import nts.uk.ctx.basic.dom.company.organization.classification.ClassificationName;
import nts.uk.ctx.basic.dom.company.organization.classification.ClassificationSetMemento;
import nts.uk.ctx.basic.infra.entity.company.organization.classification.CclmtClassification;
import nts.uk.ctx.basic.infra.entity.company.organization.classification.CclmtClassificationPK;

/**
 * The Class JpaManagementCategorySetMemento.
 */
public class JpaClassificationSetMemento implements ClassificationSetMemento{
	
	/** The cclmt management category. */
	@Setter
	private CclmtClassification cclmtClassification;
	
	/**
	 * Instantiates a new jpa management category set memento.
	 *
	 * @param cclmtClassification the cclmt management category
	 */
	public JpaClassificationSetMemento(CclmtClassification cclmtClassification) {
		this.cclmtClassification = cclmtClassification;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.basic.dom.company.organization.category.
	 * ManagementCategorySetMemento#setCompanyId(java.lang.String)
	 */
	@Override
	public void setCompanyId(CompanyId companyId) {
		CclmtClassificationPK pk = new CclmtClassificationPK();
		pk.setCcid(companyId.v());
		this.cclmtClassification.setCclmtClassificationPK(pk);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.basic.dom.company.organization.category.
	 * ManagementCategorySetMemento#setManagementCategoryCode(nts.uk.ctx.basic.
	 * dom.company.organization.category.ManagementCategoryCode)
	 */
	@Override
	public void setManagementCategoryCode(ClassificationCode managementCategoryCode) {
		CclmtClassificationPK pk = this.cclmtClassification.getCclmtClassificationPK();
		pk.setCode(managementCategoryCode.v());
		this.cclmtClassification.setCclmtClassificationPK(pk);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.basic.dom.company.organization.category.
	 * ManagementCategorySetMemento#setManagementCategoryName(nts.uk.ctx.basic.
	 * dom.company.organization.category.ManagementCategoryName)
	 */
	@Override
	public void setManagementCategoryName(ClassificationName managementCategoryName) {
		this.cclmtClassification.setName(managementCategoryName.v());
	}

}
