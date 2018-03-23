/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.statutory.worktime_new.workplace;

import java.util.List;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.WorkplaceId;
import nts.uk.ctx.at.shared.dom.common.Year;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.MonthlyUnit;
import nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.WkpFlexSettingSetMemento;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.workingplace.KshstWkpFlexSet;
import nts.uk.ctx.at.shared.infra.repository.statutory.worktime_new.share.JpaDefaultSettingSetMemento;

/**
 * The Class JpaWkpFlexSettingSetMemento.
 */

/**
 * Gets the entity.
 *
 * @return the entity
 */

/**
 * Gets the entity.
 *
 * @return the entity
 */

/**
 * Gets the entity.
 *
 * @return the entity
 */
@Getter
public class JpaWkpFlexSettingSetMemento extends JpaDefaultSettingSetMemento implements WkpFlexSettingSetMemento {
	
	/** The entity. */
	private KshstWkpFlexSet entity;

	/**
	 * Instantiates a new jpa wkp flex setting set memento.
	 */
	public JpaWkpFlexSettingSetMemento() {
		this.entity = new KshstWkpFlexSet(); 
	}

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.FlexSettingSetMemento#setYear(nts.uk.ctx.at.shared.dom.common.Year)
	 */
	@Override
	public void setYear(Year year) {
		this.entity.getKshstWkpFlexSetPK().setYear(year.v());
	}

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.FlexSettingSetMemento#setStatutorySetting(java.util.List)
	 */
	@Override
	public void setStatutorySetting(List<MonthlyUnit> statutorySetting) {
		setStatutorySettingToFlexSet(this.entity, statutorySetting);
	}

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.FlexSettingSetMemento#setSpecifiedSetting(java.util.List)
	 */
	@Override
	public void setSpecifiedSetting(List<MonthlyUnit> specifiedSetting) {
		setSpecSettingToFlexSet(this.entity, specifiedSetting);
	}

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.WkpFlexSettingSetMemento#setCompanyId(nts.uk.ctx.at.shared.dom.common.CompanyId)
	 */
	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.WkpFlexSettingSetMemento#setCompanyId(nts.uk.ctx.at.shared.dom.common.CompanyId)
	 */
	@Override
	public void setCompanyId(CompanyId companyId) {
		this.entity.getKshstWkpFlexSetPK().setCid(companyId.v());
	}

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.WkpFlexSettingSetMemento#setWorkplaceId(nts.uk.ctx.at.shared.dom.common.WorkplaceId)
	 */
	@Override
	public void setWorkplaceId(WorkplaceId workplaceId) {
		this.entity.getKshstWkpFlexSetPK().setWkpId(workplaceId.v());
	}
}
