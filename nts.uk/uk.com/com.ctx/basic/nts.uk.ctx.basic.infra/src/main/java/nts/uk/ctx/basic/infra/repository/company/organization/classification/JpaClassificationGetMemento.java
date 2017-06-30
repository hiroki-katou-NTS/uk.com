/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.infra.repository.company.organization.classification;

import lombok.Setter;
import nts.uk.ctx.basic.dom.company.organization.CompanyId;
import nts.uk.ctx.basic.dom.company.organization.classification.ClassificationCode;
import nts.uk.ctx.basic.dom.company.organization.classification.ClassificationGetMemento;
import nts.uk.ctx.basic.dom.company.organization.classification.ClassificationName;
import nts.uk.ctx.basic.infra.entity.company.organization.classification.CclmtClassification;

/**
 * The Class JpaClassificationGetMemento.
 */
public class JpaClassificationGetMemento implements ClassificationGetMemento{
	
	/** The cclmt classification. */
	
	/**
	 * Sets the cclmt classification.
	 *
	 * @param cclmtClassification the new cclmt classification
	 */
	@Setter
	private CclmtClassification cclmtClassification;
	
	/**
	 * Instantiates a new jpa classification get memento.
	 *
	 * @param cclmtClassification the cclmt classification
	 */
	public JpaClassificationGetMemento(CclmtClassification cclmtClassification) {
		this.cclmtClassification = cclmtClassification;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.basic.dom.company.organization.category.
	 * ManagementCategoryGetMemento#getCompanyId()
	 */
	@Override
	public CompanyId getCompanyId() {
		return new CompanyId(this.cclmtClassification.getCclmtClassificationPK().getCid());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.basic.dom.company.organization.category.
	 * ManagementCategoryGetMemento#getManagementCategoryCode()
	 */
	@Override
	public ClassificationCode getClassificationCode() {
		return new ClassificationCode(
				this.cclmtClassification.getCclmtClassificationPK().getCode());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.basic.dom.company.organization.category.
	 * ManagementCategoryGetMemento#getManagementCategoryName()
	 */
	@Override
	public ClassificationName getClassificationName() {
		return new ClassificationName(this.cclmtClassification.getName());
	}
	
}
