/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.dom.company.organization.classification;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.basic.dom.company.organization.CompanyId;

/**
 * The Class Classification.
 */
@Getter
public class Classification extends DomainObject{

	/** The company id. */
	private CompanyId companyId;
	
	/** The classification code. */
	private ClassificationCode classificationCode;
	
	/** The classification name. */
	private ClassificationName classificationName;
	
	/**
	 * Instantiates a new classification.
	 *
	 * @param memento the memento
	 */
	public Classification(ClassificationGetMemento memento) {
		this.companyId = memento.getCompanyId();
		this.classificationCode = memento.getClassificationCode();
		this.classificationName = memento.getClassificationName();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(ClassificationSetMemento memento) {
		memento.setCompanyId(this.companyId);
		memento.setManagementCategoryCode(this.classificationCode);
		memento.setManagementCategoryName(this.classificationName);
	}
	
}
