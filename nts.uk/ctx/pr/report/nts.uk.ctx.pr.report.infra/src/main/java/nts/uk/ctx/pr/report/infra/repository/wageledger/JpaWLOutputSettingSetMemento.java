/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.infra.repository.wageledger;

import java.util.List;
import java.util.stream.Collectors;

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
	
	/** The company code. */
	private CompanyCode companyCode;
	
	/** The code. */
	private WLOutputSettingCode code;
	
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
		if (this.entity.getQlsptLedgerFormHeadPK() == null) {
			this.entity.setQlsptLedgerFormHeadPK(new QlsptLedgerFormHeadPK());
		}
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.pr.report.dom.wageledger.outputsetting.WLOutputSettingSetMemento
	 * #setCode(nts.uk.ctx.pr.report.dom.wageledger.outputsetting.WLOutputSettingCode)
	 */
	@Override
	public void setCode(WLOutputSettingCode code) {
		this.entity.getQlsptLedgerFormHeadPK().setFormCd(code.v());
		this.code = code;
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
		this.entity.setQlsptLedgerFormDetailList(categorySettings.stream().map(cate -> {
			JpaWLCategorySettingSetMemento setMemento = new JpaWLCategorySettingSetMemento(
					this.companyCode, this.code);
			cate.saveToMemento(setMemento);
			return setMemento.getCategoryEntities();
		}).flatMap(cate -> cate.stream()).collect(Collectors.toList()));
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.pr.report.dom.wageledger.outputsetting.WLOutputSettingSetMemento
	 * #setOnceSheetPerPerson(java.lang.Boolean)
	 */
	@Override
	public void setOnceSheetPerPerson(Boolean onceSheetPerPerson) {
		// 1 => true.
		// 0 => false.
		this.entity.setPrint1pageByPersonSet(onceSheetPerPerson ? 1 : 0);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.pr.report.dom.wageledger.outputsetting.WLOutputSettingSetMemento
	 * #setCompanyCode(nts.uk.ctx.pr.report.dom.company.CompanyCode)
	 */
	@Override
	public void setCompanyCode(CompanyCode companyCode) {
		this.entity.getQlsptLedgerFormHeadPK().setCcd(companyCode.v());
		this.companyCode = companyCode;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.pr.report.dom.wageledger.outputsetting.WLOutputSettingSetMemento
	 * #setVersion(long)
	 */
	@Override
	public void setVersion(long version) {
		this.entity.setExclusVer(0);
	}



}
