/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.vacation.setting.acquisitionrule;

import java.util.List;

import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.acquisitionrule.AcquisitionOrder;
import nts.uk.ctx.at.shared.dom.vacation.setting.acquisitionrule.AcquisitionRuleSetMemento;
import nts.uk.ctx.at.shared.dom.vacation.setting.acquisitionrule.AcquisitionType;
import nts.uk.ctx.at.shared.dom.vacation.setting.acquisitionrule.AnnualHoliday;
import nts.uk.ctx.at.shared.dom.vacation.setting.acquisitionrule.HoursHoliday;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.acquisitionrule.KarstAcquisitionRule;

/**
 * The Class JpaAcquisitionRuleSetMemento.
 */
public class JpaAcquisitionRuleSetMemento implements AcquisitionRuleSetMemento {

	/** The type value. */
	private KarstAcquisitionRule typeValue;

	/**
	 * Instantiates a new jpa acquisition rule set memento.
	 *
	 * @param typeValue
	 *            the type value
	 */
	public JpaAcquisitionRuleSetMemento(KarstAcquisitionRule typeValue) {
		this.typeValue = typeValue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.vacation.setting.acquisitionrule.
	 * VaAcRuleSetMemento#setCompanyId(java.lang.String)
	 */
	@Override
	public void setCompanyId(String companyId) {
		this.typeValue.setCid(companyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.vacation.setting.acquisitionrule.
	 * VaAcRuleSetMemento#setSettingclassification(nts.uk.ctx.pr.core.dom.
	 * vacation.setting.acquisitionrule.Settingclassification)
	 */
	@Override
	public void setCategory(ManageDistinct category) {
		this.typeValue.setCategory(category.value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.vacation.setting.acquisitionrule.
	 * VaAcRuleSetMemento#setAcquisitionOrder(java.util.List)
	 */
	@Override
	public void setAcquisitionOrder(List<AcquisitionOrder> listVacationAcquisitionOrder) {
		if (!CollectionUtil.isEmpty(listVacationAcquisitionOrder)) {
			for (AcquisitionOrder e : listVacationAcquisitionOrder) {
				// Annual Paid Leave
				if (e.getVacationType().equals(AcquisitionType.AnnualPaidLeave)) {
					this.typeValue.setAnnualPaid(e.getPriority().v());
				}
				// Exsess Holiday
//				if (e.getVacationType().equals(AcquisitionType.ExsessHoliday)) {
//					this.typeValue.setExsessHoliday(e.getPriority().v());
//				}

				// Funded Paid Holiday
				if (e.getVacationType().equals(AcquisitionType.FundedPaidHoliday)) {
					this.typeValue.setFundedPaidHoliday(e.getPriority().v());
				}

				// Substitute Holiday
				if (e.getVacationType().equals(AcquisitionType.SubstituteHoliday)) {
					this.typeValue.setSabstituteHoliday(e.getPriority().v());
				}

				// Compensatory Day Off
				if (e.getVacationType().equals(AcquisitionType.CompensatoryDayOff)) {
					this.typeValue.setCompensatoryDayOff(e.getPriority().v());
				}

				// Special Holiday
//				if (e.getVacationType().equals(AcquisitionType.SpecialHoliday)) {
//					this.typeValue.setSpecialHoliday(e.getPriority().v());
//				}
			}
		}
	}

	@Override
	public void setAnnualHoliday(AnnualHoliday annualHoliday) {		
		if(!annualHoliday.isPriorityPause()) {
			this.typeValue.setCompensatoryDayOff(0);
		} else {
			this.typeValue.setCompensatoryDayOff(1);
		}
		
		if(!annualHoliday.isPrioritySubstitute()) {
			this.typeValue.setSabstituteHoliday(0);
		} else {
			this.typeValue.setSabstituteHoliday(1);
		}
		
		if(!annualHoliday.isSixtyHoursOverrideHoliday()) {
			this.typeValue.setFundedPaidHoliday(0);
		} else {
			this.typeValue.setFundedPaidHoliday(1);
		}
	}

	@Override
	public void setHoursHoliday(HoursHoliday hoursHoliday) {
		if(!hoursHoliday.isPriorityOverpaid()) {
			this.typeValue.setExcessHoliday(0);
		} else {
			this.typeValue.setExcessHoliday(1);
		}
		
		if(!hoursHoliday.isSixtyHoursOverrideHoliday()) {
			this.typeValue.setOverrideHoliday(0);
		} else {
			this.typeValue.setOverrideHoliday(1);
		}
	}
}
