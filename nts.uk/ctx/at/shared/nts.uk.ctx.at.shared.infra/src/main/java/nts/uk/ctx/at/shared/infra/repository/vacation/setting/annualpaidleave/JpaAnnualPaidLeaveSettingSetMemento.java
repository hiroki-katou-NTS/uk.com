/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.vacation.setting.annualpaidleave;

import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualPaidLeaveSettingSetMemento;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.ManageAnnualSetting;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.annualpaidleave.KmfmtAnnualPaidLeave;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.annualpaidleave.KmfmtMngAnnualSet;

/**
 * The Class JpaAnnualPaidLeaveSettingSetMemento.
 */
public class JpaAnnualPaidLeaveSettingSetMemento implements AnnualPaidLeaveSettingSetMemento {

	/** The entity. */
	@Inject
	private KmfmtAnnualPaidLeave entity;
	
	/**
	 * Instantiates a new jpa annual paid leave setting set memento.
	 *
	 * @param entity the entity
	 */
	public JpaAnnualPaidLeaveSettingSetMemento(KmfmtAnnualPaidLeave entity) {
		this.entity = entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.vacation.setting.annualpaidleave.
	 * AnnualPaidLeaveSettingSetMemento#setCompanyId(java.lang.String)
	 */
	@Override
	public void setCompanyId(String companyId) {
		this.entity.setCid(companyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.vacation.setting.annualpaidleave.
	 * AnnualPaidLeaveSettingSetMemento#setYearManageType(nts.uk.ctx.pr.core.dom
	 * .vacation.setting.ManageDistinct)
	 */
	@Override
	public void setYearManageType(ManageDistinct yearManageType) {
		this.entity.setManageAtr(yearManageType.value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.vacation.setting.annualpaidleave.
	 * AnnualPaidLeaveSettingSetMemento#setYearManageSetting(nts.uk.ctx.pr.core.
	 * dom.vacation.setting.annualpaidleave.ManageAnnualSetting)
	 */
	@Override
	public void setYearManageSetting(ManageAnnualSetting yearManageSetting) {
	    KmfmtMngAnnualSet entityMange = new KmfmtMngAnnualSet();
	    JpaManageAnnualSettingSetMemento memento = new JpaManageAnnualSettingSetMemento(entityMange);
	    yearManageSetting.saveToMemento(memento);
	    entityMange.setCid(this.entity.getCid());
	    this.entity.setEntityManage(entityMange);
	}

}
