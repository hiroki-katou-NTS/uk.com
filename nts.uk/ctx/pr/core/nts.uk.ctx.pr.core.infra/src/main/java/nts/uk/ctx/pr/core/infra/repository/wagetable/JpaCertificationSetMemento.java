/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.infra.repository.wagetable;

import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.wagetable.CertificationSetMemento;
import nts.uk.ctx.pr.core.infra.entity.wagetable.QcemtCertification;
import nts.uk.ctx.pr.core.infra.entity.wagetable.QcemtCertificationPK;

/**
 * The Class JpaCertificationSetMemento.
 */
public class JpaCertificationSetMemento implements CertificationSetMemento {

	/** The type value. */
	protected QcemtCertification typeValue;

	/**
	 * Instantiates a new jpa certification set memento.
	 *
	 * @param typeValue
	 *            the type value
	 */
	public JpaCertificationSetMemento(QcemtCertification typeValue) {
		this.typeValue = typeValue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.wagetable.CertificationSetMemento#setCompanyCode(
	 * nts.uk.ctx.core.dom.company.CompanyCode)
	 */
	@Override
	public void setCompanyCode(CompanyCode companyCode) {
		QcemtCertificationPK qcemtCertificationPK = new QcemtCertificationPK();
		qcemtCertificationPK.setCcd(companyCode.v());
		this.typeValue.setQcemtCertificationPK(qcemtCertificationPK);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.wagetable.CertificationSetMemento#setCode(java.
	 * lang.String)
	 */
	@Override
	public void setCode(String code) {
		QcemtCertificationPK qcemtCertificationPK = this.typeValue.getQcemtCertificationPK();
		qcemtCertificationPK.setCertCd(code);
		this.typeValue.setQcemtCertificationPK(qcemtCertificationPK);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.wagetable.CertificationSetMemento#setName(java.
	 * lang.String)
	 */
	@Override
	public void setName(String name) {
		this.typeValue.setName(name);
	}

}
