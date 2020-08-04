/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.statutory.worktime_new.workplace;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.Month;
import nts.uk.ctx.at.shared.dom.common.WorkplaceId;
import nts.uk.ctx.at.shared.dom.common.Year;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.MonthlyUnit;
import nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.WkpNormalSettingSetMemento;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.workingplace.KshstWkpNormalSet;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.workingplace.KshstWkpNormalSetPK;

/**
 * The Class JpaWkpNormalSettingSetMemento.
 */
public class JpaWkpNormalSettingSetMemento implements WkpNormalSettingSetMemento {

	/** The entity. */
	private KshstWkpNormalSet entity;

	/**
	 * Instantiates a new jpa com normal setting set memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaWkpNormalSettingSetMemento(KshstWkpNormalSet entity) {
		super();
		if (entity.getKshstWkpNormalSetPK() == null) {
			entity.setKshstWkpNormalSetPK(new KshstWkpNormalSetPK());
		}
		this.entity = entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.
	 * NormalSettingSetMemento#setYear(nts.uk.ctx.at.shared.dom.common.Year)
	 */
	@Override
	public void setYear(Year year) {
		this.entity.getKshstWkpNormalSetPK().setYear(year.v());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.
	 * WkpNormalSettingSetMemento#setCompanyId(nts.uk.ctx.at.shared.dom.common.
	 * CompanyId)
	 */
	@Override
	public void setCompanyId(CompanyId companyId) {
		this.entity.getKshstWkpNormalSetPK().setCid(companyId.v());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.
	 * WkpNormalSettingSetMemento#setWorkplaceId(nts.uk.ctx.at.shared.dom.common
	 * .WorkplaceId)
	 */
	@Override
	public void setWorkplaceId(WorkplaceId workplaceId) {
		this.entity.getKshstWkpNormalSetPK().setWkpId(workplaceId.v());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.
	 * NormalSettingSetMemento#setStatutorySetting(java.util.List)
	 */
	@Override
	public void setStatutorySetting(List<MonthlyUnit> statutorySetting) {
		Map<Integer, Integer> map = toMonthlyEstimateTimeMap(statutorySetting);
		this.entity.setJanTime(map.get(Month.JANUARY));
		this.entity.setFebTime(map.get(Month.FEBRUARY));
		this.entity.setMarTime(map.get(Month.MARCH));
		this.entity.setAprTime(map.get(Month.APRIL));
		this.entity.setMayTime(map.get(Month.MAY));
		this.entity.setJunTime(map.get(Month.JUNE));
		this.entity.setJulTime(map.get(Month.JULY));
		this.entity.setAugTime(map.get(Month.AUGUST));
		this.entity.setSepTime(map.get(Month.SEPTEMBER));
		this.entity.setOctTime(map.get(Month.OCTOBER));
		this.entity.setNovTime(map.get(Month.NOVEMBER));
		this.entity.setDecTime(map.get(Month.DECEMBER));
	}

	/**
	 * To monthly estimate time map.
	 *
	 * @param statutorySetting
	 *            the statutory setting
	 * @return the map
	 */
	private Map<Integer, Integer> toMonthlyEstimateTimeMap(List<MonthlyUnit> statutorySetting) {
		return statutorySetting.stream().collect(
				Collectors.toMap(unit -> unit.getMonth().v(), unit -> unit.getMonthlyTime().v()));
	}

}
