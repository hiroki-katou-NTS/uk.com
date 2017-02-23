/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.wagetable;

import java.util.Set;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.wagetable.certification.Certification;
import nts.uk.ctx.pr.core.dom.wagetable.certification.CertifyGroupGetMemento;
import nts.uk.ctx.pr.core.dom.wagetable.certification.CertifyGroupSetMemento;
import nts.uk.ctx.pr.core.dom.wagetable.certification.MultipleTargetSetting;

/**
 * The Class CertifyGroup.
 */
@Getter
public class WageTableHead extends DomainObject {

	/** The company code. */
	private CompanyCode companyCode;

	/** The code. */
	private WageTableCode code;

	/** The name. */
	private WageTableName name;

	/** The multi apply set. */
	private MultipleTargetSetting multiApplySet;

	/** The certifies. */
	private Set<Certification> certifies;

	// =================== Memento State Support Method ===================
	/**
	 * Instantiates a new certify group.
	 *
	 * @param memento
	 *            the memento
	 */
	public WageTableHead(CertifyGroupGetMemento memento) {
		this.companyCode = memento.getCompanyCode();
		this.multiApplySet = memento.getMultiApplySet();
		this.certifies = memento.getCertifies();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(CertifyGroupSetMemento memento) {
		memento.setCompanyCode(this.companyCode);
		memento.setMultiApplySet(this.multiApplySet);
		memento.setCertifies(this.certifies);
	}

}
