/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.statutory.worktime_new.company;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.Month;
import nts.uk.ctx.at.shared.dom.common.Year;
import nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComDeforLaborSettingSetMemento;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.MonthlyUnit;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.company.KshstComDeforLarSet;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.company.KshstComDeforLarSetPK;

/**
 * The Class JpaComDeforLaborSettingSetMemento.
 */
public class JpaComDeforLaborSettingSetMemento implements ComDeforLaborSettingSetMemento {

	/** The entity. */
	private KshstComDeforLarSet entity;

	/**
	 * Instantiates a new jpa com defor labor setting set memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaComDeforLaborSettingSetMemento(KshstComDeforLarSet entity) {
		super();
		if (entity.getKshstComDeforLarSetPK() == null) {
			entity.setKshstComDeforLarSetPK(new KshstComDeforLarSetPK());
		}
		this.entity = entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.
	 * DeforLaborSettingSetMemento#setYear(nts.uk.ctx.at.shared.dom.common.Year)
	 */
	@Override
	public void setYear(Year year) {
		this.entity.getKshstComDeforLarSetPK().setYear(year.v());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.
	 * ComDeforLaborSettingSetMemento#setCompanyId(nts.uk.ctx.at.shared.dom.
	 * common.CompanyId)
	 */
	@Override
	public void setCompanyId(CompanyId companyId) {
		this.entity.getKshstComDeforLarSetPK().setCid(companyId.v());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.
	 * DeforLaborSettingSetMemento#setStatutorySetting(java.util.List)
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
