/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.infra.repository.insurance;

import nts.uk.ctx.pr.report.dom.insurance.ChecklistPrintSettingGetMemento;
import nts.uk.ctx.pr.report.infra.entity.insurance.QismtChecklistPrintSet;

/**
 * The Class JpaChecklistPrintSettingGetMemento.
 */
public class JpaChecklistPrintSettingGetMemento implements ChecklistPrintSettingGetMemento {

	/** The entity. */
	private QismtChecklistPrintSet entity;

	/**
	 * Instantiates a new jpa checklist print setting get memento.
	 *
	 * @param entity the entity
	 */
	public JpaChecklistPrintSettingGetMemento(QismtChecklistPrintSet entity) {
		super();
		this.entity = entity;
	}

	/*
	 * (non-Javadoc)
	 * @see nts.uk.ctx.pr.report.dom.insurance.ChecklistPrintSettingGetMemento#
	 * getCompanyCode()
	 */
	@Override
	public String getCompanyCode() {
		return this.entity.getCcd();
	}

	/*
	 * (non-Javadoc)
	 * @see nts.uk.ctx.pr.report.dom.insurance.ChecklistPrintSettingGetMemento#
	 * getShowCategoryInsuranceItem()
	 */
	@Override
	public Boolean getShowCategoryInsuranceItem() {
		return this.entity.getIsShowBreakdownItem() == 1;
	}

	/*
	 * (non-Javadoc)
	 * @see nts.uk.ctx.pr.report.dom.insurance.ChecklistPrintSettingGetMemento#
	 * getShowDeliveryNoticeAmount()
	 */
	@Override
	public Boolean getShowDeliveryNoticeAmount() {
		return this.entity.getIsShowTotalPayMny() == 1;
	}

	/*
	 * (non-Javadoc)
	 * @see nts.uk.ctx.pr.report.dom.insurance.ChecklistPrintSettingGetMemento#
	 * getShowDetail()
	 */
	@Override
	public Boolean getShowDetail() {
		return this.entity.getIsShowPersonSumMny() == 1;
	}

	/*
	 * (non-Javadoc)
	 * @see nts.uk.ctx.pr.report.dom.insurance.ChecklistPrintSettingGetMemento#
	 * getShowOffice()
	 */
	@Override
	public Boolean getShowOffice() {
		return this.entity.getIsShowTotalMnyCol() == 1;
	}

	/*
	 * (non-Javadoc)
	 * @see nts.uk.ctx.pr.report.dom.insurance.ChecklistPrintSettingGetMemento#
	 * getShowTotal()
	 */
	@Override
	public Boolean getShowTotal() {
		return this.entity.getIsShowOfficeSumMny() == 1;
	}

}
