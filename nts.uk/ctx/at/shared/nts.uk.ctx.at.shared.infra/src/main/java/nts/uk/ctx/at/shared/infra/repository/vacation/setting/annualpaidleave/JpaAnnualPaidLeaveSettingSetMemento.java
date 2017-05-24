/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.vacation.setting.annualpaidleave;

import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualPaidLeaveSettingSetMemento;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.ManageAnnualSetting;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.annualpaidleave.KmfmtAnnualPaidLeave;

/**
 * The Class JpaAnnualPaidLeaveSettingSetMemento.
 */
public class JpaAnnualPaidLeaveSettingSetMemento implements AnnualPaidLeaveSettingSetMemento {

	/** The annual paid leave. */
	@Inject
	private KmfmtAnnualPaidLeave annualPaidLeave;

	/**
	 * Instantiates a new jpa annual paid leave setting set memento.
	 *
	 * @param annualPaidLeave
	 *            the annual paid leave
	 */
	public JpaAnnualPaidLeaveSettingSetMemento(KmfmtAnnualPaidLeave annualPaidLeave) {
		this.annualPaidLeave = annualPaidLeave;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.vacation.setting.annualpaidleave.
	 * AnnualPaidLeaveSettingSetMemento#setCompanyId(java.lang.String)
	 */
	@Override
	public void setCompanyId(String companyId) {
		this.annualPaidLeave.setCid(companyId);
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
		this.annualPaidLeave.setManageAtr(yearManageType.value);
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
	}

}
