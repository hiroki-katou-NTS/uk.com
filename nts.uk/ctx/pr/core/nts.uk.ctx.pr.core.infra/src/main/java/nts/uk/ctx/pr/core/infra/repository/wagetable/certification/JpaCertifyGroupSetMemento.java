/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.infra.repository.wagetable.certification;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.wagetable.certification.Certification;
import nts.uk.ctx.pr.core.dom.wagetable.certification.CertifyGroupCode;
import nts.uk.ctx.pr.core.dom.wagetable.certification.CertifyGroupName;
import nts.uk.ctx.pr.core.dom.wagetable.certification.CertifyGroupSetMemento;
import nts.uk.ctx.pr.core.dom.wagetable.certification.MultipleTargetSetting;
import nts.uk.ctx.pr.core.infra.entity.wagetable.certification.QwtmtWagetableCertify;
import nts.uk.ctx.pr.core.infra.entity.wagetable.certification.QwtmtWagetableCertifyG;
import nts.uk.ctx.pr.core.infra.entity.wagetable.certification.QwtmtWagetableCertifyGPK;

/**
 * The Class JpaCertifyGroupSetMemento.
 */
public class JpaCertifyGroupSetMemento implements CertifyGroupSetMemento {

	/** The type value. */
	protected QwtmtWagetableCertifyG typeValue;

	/**
	 * Instantiates a new jpa certify group set memento.
	 *
	 * @param typeValue
	 *            the type value
	 */
	public JpaCertifyGroupSetMemento(QwtmtWagetableCertifyG typeValue) {
		this.typeValue = typeValue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.wagetable.CertifyGroupSetMemento#setCompanyCode(
	 * nts.uk.ctx.core.dom.company.CompanyCode)
	 */
	@Override
	public void setCompanyCode(CompanyCode companyCode) {
		QwtmtWagetableCertifyGPK wagetableCertifyGPK = new QwtmtWagetableCertifyGPK();
		wagetableCertifyGPK.setCcd(companyCode.v());
		this.typeValue.setQwtmtWagetableCertifyGPK(wagetableCertifyGPK);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.wagetable.CertifyGroupSetMemento#setCode(java.lang
	 * .String)
	 */
	@Override
	public void setCode(CertifyGroupCode code) {
		QwtmtWagetableCertifyGPK wagetableCertifyGPK = this.typeValue.getQwtmtWagetableCertifyGPK();
		wagetableCertifyGPK.setCertifyGroupCd(code.v());
		this.typeValue.setQwtmtWagetableCertifyGPK(wagetableCertifyGPK);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.wagetable.CertifyGroupSetMemento#setName(java.lang
	 * .String)
	 */
	@Override
	public void setName(CertifyGroupName name) {
		this.typeValue.setCertifyGroupName(name.v());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.wagetable.CertifyGroupSetMemento#setMultiApplySet(
	 * nts.uk.ctx.pr.core.dom.wagetable.MultipleTargetSetting)
	 */
	@Override
	public void setMultiApplySet(MultipleTargetSetting multiApplySet) {
		this.typeValue.setMultiApplySet(multiApplySet.value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.wagetable.CertifyGroupSetMemento#setCertifies(java
	 * .util.Set)
	 */
	@Override
	public void setCertifies(Set<Certification> certifies) {
		List<QwtmtWagetableCertify> qwtmtWagetableCertifyList = new ArrayList<>();
		for (Certification certification : certifies) {
			QwtmtWagetableCertify qwtmtWagetableCertify = new QwtmtWagetableCertify();
			certification.saveToMemento(new JpaCertificationSetMemento(qwtmtWagetableCertify,
					typeValue.getQwtmtWagetableCertifyGPK().getCertifyGroupCd()));
			qwtmtWagetableCertifyList.add(qwtmtWagetableCertify);
		}
		this.typeValue.setQwtmtWagetableCertifyList(qwtmtWagetableCertifyList);
	}

}
