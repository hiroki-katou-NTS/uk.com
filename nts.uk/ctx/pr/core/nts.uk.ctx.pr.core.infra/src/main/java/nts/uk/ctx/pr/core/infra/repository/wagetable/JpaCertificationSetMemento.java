/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.infra.repository.wagetable;

import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.wagetable.certification.CertificationSetMemento;
import nts.uk.ctx.pr.core.infra.entity.wagetable.QcemtCertificationPK;
import nts.uk.ctx.pr.core.infra.entity.wagetable.QwtmtWagetableCertify;
import nts.uk.ctx.pr.core.infra.entity.wagetable.QwtmtWagetableCertifyPK;

/**
 * The Class JpaCertificationSetMemento.
 */
public class JpaCertificationSetMemento implements CertificationSetMemento {

	/** The type value. */
	protected QwtmtWagetableCertify typeValue;

	/**
	 * Instantiates a new jpa certification set memento.
	 *
	 * @param typeValue
	 *            the type value
	 */
	public JpaCertificationSetMemento(QwtmtWagetableCertify typeValue, String groupCode) {
		this.typeValue = typeValue;
		QwtmtWagetableCertifyPK qcemtCertificationPK = new QwtmtWagetableCertifyPK();
		qcemtCertificationPK.setCertifyGroupCd(groupCode);
		this.typeValue.setQwtmtWagetableCertifyPK(qcemtCertificationPK);
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
		QwtmtWagetableCertifyPK qcemtCertificationPK = this.typeValue.getQwtmtWagetableCertifyPK();
		qcemtCertificationPK.setCcd(companyCode.v());
		this.typeValue.setQwtmtWagetableCertifyPK(qcemtCertificationPK);
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
		// Certification Code
		QwtmtWagetableCertifyPK qcemtCertificationPK = this.typeValue.getQwtmtWagetableCertifyPK();
		qcemtCertificationPK.setCertifyCd(code);
		this.typeValue.setQwtmtWagetableCertifyPK(qcemtCertificationPK);
	}

	/**
	 * Sets the name.
	 *
	 * @param name
	 *            the new name
	 */
	@Override
	public void setName(String name) {
		// TODO Auto-generated method stub

	}

}
