/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.infra.repository.wageledger;

import java.util.List;

import nts.uk.ctx.pr.report.dom.company.CompanyCode;
import nts.uk.ctx.pr.report.dom.wageledger.outputsetting.WLCategorySetting;
import nts.uk.ctx.pr.report.dom.wageledger.outputsetting.WLOutputSettingCode;
import nts.uk.ctx.pr.report.dom.wageledger.outputsetting.WLOutputSettingName;
import nts.uk.ctx.pr.report.dom.wageledger.outputsetting.WLOutputSettingSetMemento;
import nts.uk.ctx.pr.report.infra.entity.wageledger.QlsptLedgerFormHead;
import nts.uk.ctx.pr.report.infra.entity.wageledger.QlsptLedgerFormHeadPK;

/**
 * The Class JpaWLOutputSettingSetMemento.
 */
public class JpaWLOutputSettingSetMemento implements WLOutputSettingSetMemento{
	
	/** The entity. */
	protected QlsptLedgerFormHead entity;
	
	/**
	 * Instantiates a new jpa WL output setting set memento.
	 *
	 * @param entity the entity
	 */
	public JpaWLOutputSettingSetMemento(QlsptLedgerFormHead entity) {
		super();
		this.entity = entity;
		
		// Init pk.
		QlsptLedgerFormHeadPK pk = new QlsptLedgerFormHeadPK();
		this.entity.setQlsptLedgerFormHeadPK(pk);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.pr.report.dom.wageledger.outputsetting.WLOutputSettingSetMemento
	 * #setCode(nts.uk.ctx.pr.report.dom.wageledger.outputsetting.WLOutputSettingCode)
	 */
	@Override
	public void setCode(WLOutputSettingCode code) {
		this.entity.getQlsptLedgerFormHeadPK().setCcd(code.v());
		
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.pr.report.dom.wageledger.outputsetting.WLOutputSettingSetMemento
	 * #setName(nts.uk.ctx.pr.report.dom.wageledger.outputsetting.WLOutputSettingName)
	 */
	@Override
	public void setName(WLOutputSettingName name) {
		this.entity.setFormName(name.v());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.pr.report.dom.wageledger.outputsetting.WLOutputSettingSetMemento
	 * #setCategorySettings(java.util.List)
	 */
	@Override
	public void setCategorySettings(List<WLCategorySetting> categorySettings) {
		// TODO Auto-generated method stub
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.pr.report.dom.wageledger.outputsetting.WLOutputSettingSetMemento
	 * #setOnceSheetPerPerson(java.lang.Boolean)
	 */
	@Override
	public void setOnceSheetPerPerson(Boolean onceSheetPerPerson) {
		// TODO: wait change domain.
		//this.entity.setPrint1pageByPersonSet(print1pageByPersonSet);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.pr.report.dom.wageledger.outputsetting.WLOutputSettingSetMemento
	 * #setCompanyCode(nts.uk.ctx.pr.report.dom.company.CompanyCode)
	 */
	@Override
	public void setCompanyCode(CompanyCode companyCode) {
		this.entity.getQlsptLedgerFormHeadPK().setCcd(companyCode.v());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.pr.report.dom.wageledger.outputsetting.WLOutputSettingSetMemento
	 * #setVersion(long)
	 */
	@Override
	public void setVersion(long version) {
		// Do nothing.
		throw new UnsupportedOperationException();
	}



}
