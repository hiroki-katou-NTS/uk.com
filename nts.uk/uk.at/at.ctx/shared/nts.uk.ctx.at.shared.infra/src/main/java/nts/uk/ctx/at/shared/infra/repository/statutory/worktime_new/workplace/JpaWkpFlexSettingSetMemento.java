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
import nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.WkpFlexSettingSetMemento;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.workingplace.KshstWkpFlexSet;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.workingplace.KshstWkpFlexSetPK;

/**
 * The Class JpaWkpFlexSettingSetMemento.
 */
public class JpaWkpFlexSettingSetMemento implements WkpFlexSettingSetMemento {

	/** The entity. */
	private KshstWkpFlexSet entity;

	/**
	 * Instantiates a new jpa wkp flex setting set memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaWkpFlexSettingSetMemento(KshstWkpFlexSet entity) {
		super();
		if (entity.getKshstWkpFlexSetPK() == null) {
			entity.setKshstWkpFlexSetPK(new KshstWkpFlexSetPK());
		}
		this.entity = entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.
	 * WkpFlexSettingSetMemento#setCompanyId(nts.uk.ctx.at.shared.dom.common.
	 * CompanyId)
	 */
	@Override
	public void setCompanyId(CompanyId companyId) {
		this.entity.getKshstWkpFlexSetPK().setCid(companyId.v());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.
	 * WkpFlexSettingSetMemento#setWorkplaceId(nts.uk.ctx.at.shared.dom.common.
	 * WorkplaceId)
	 */
	@Override
	public void setWorkplaceId(WorkplaceId workplaceId) {
		this.entity.getKshstWkpFlexSetPK().setWkpId(workplaceId.v());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.
	 * FlexSettingSetMemento#setYear(nts.uk.ctx.at.shared.dom.common.Year)
	 */
	@Override
	public void setYear(Year year) {
		this.entity.getKshstWkpFlexSetPK().setYear(year.v());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.
	 * FlexSettingSetMemento#setStatutorySetting(java.util.List)
	 */
	@Override
	public void setStatutorySetting(List<MonthlyUnit> statutorySetting) {
		Map<Integer, Integer> map = toMonthlyEstimateTimeMap(statutorySetting);
		this.entity.setStatJanTime(map.get(Month.JANUARY));
		this.entity.setStatFebTime(map.get(Month.FEBRUARY));
		this.entity.setStatMarTime(map.get(Month.MARCH));
		this.entity.setStatAprTime(map.get(Month.APRIL));
		this.entity.setStatMayTime(map.get(Month.MAY));
		this.entity.setStatJunTime(map.get(Month.JUNE));
		this.entity.setStatJulTime(map.get(Month.JULY));
		this.entity.setStatAugTime(map.get(Month.AUGUST));
		this.entity.setStatSepTime(map.get(Month.SEPTEMBER));
		this.entity.setStatOctTime(map.get(Month.OCTOBER));
		this.entity.setStatNovTime(map.get(Month.NOVEMBER));
		this.entity.setStatDecTime(map.get(Month.DECEMBER));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.
	 * FlexSettingSetMemento#setSpecifiedSetting(java.util.List)
	 */
	@Override
	public void setSpecifiedSetting(List<MonthlyUnit> specifiedSetting) {
		Map<Integer, Integer> map = toMonthlyEstimateTimeMap(specifiedSetting);
		this.entity.setSpecJanTime(map.get(Month.JANUARY));
		this.entity.setSpecFebTime(map.get(Month.FEBRUARY));
		this.entity.setSpecMarTime(map.get(Month.MARCH));
		this.entity.setSpecAprTime(map.get(Month.APRIL));
		this.entity.setSpecMayTime(map.get(Month.MAY));
		this.entity.setSpecJunTime(map.get(Month.JUNE));
		this.entity.setSpecJulTime(map.get(Month.JULY));
		this.entity.setSpecAugTime(map.get(Month.AUGUST));
		this.entity.setSpecSepTime(map.get(Month.SEPTEMBER));
		this.entity.setSpecOctTime(map.get(Month.OCTOBER));
		this.entity.setSpecNovTime(map.get(Month.NOVEMBER));
		this.entity.setSpecDecTime(map.get(Month.DECEMBER));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.
	 * FlexSettingSetMemento#setWeekAveSetting(java.util.List)
	 */
	@Override
	public void setWeekAveSetting(List<MonthlyUnit> weekAveSetting) {
		Map<Integer, Integer> map = toMonthlyEstimateTimeMap(weekAveSetting);
		this.entity.setWeekJanTime(map.get(Month.JANUARY));
		this.entity.setWeekFebTime(map.get(Month.FEBRUARY));
		this.entity.setWeekMarTime(map.get(Month.MARCH));
		this.entity.setWeekAprTime(map.get(Month.APRIL));
		this.entity.setWeekMayTime(map.get(Month.MAY));
		this.entity.setWeekJunTime(map.get(Month.JUNE));
		this.entity.setWeekJulTime(map.get(Month.JULY));
		this.entity.setWeekAugTime(map.get(Month.AUGUST));
		this.entity.setWeekSepTime(map.get(Month.SEPTEMBER));
		this.entity.setWeekOctTime(map.get(Month.OCTOBER));
		this.entity.setWeekNovTime(map.get(Month.NOVEMBER));
		this.entity.setWeekDecTime(map.get(Month.DECEMBER));
	}
	
	/**
	 * To monthly estimate time map.
	 *
	 * @param setting
	 *            the setting
	 * @return the map
	 */
	private Map<Integer, Integer> toMonthlyEstimateTimeMap(List<MonthlyUnit> setting) {
		return setting.stream().collect(
				Collectors.toMap(unit -> unit.getMonth().v(), unit -> unit.getMonthlyTime().v()));
	}

}
