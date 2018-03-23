/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.statutory.worktime_new.workplace;

import java.util.ArrayList;
import java.util.List;

import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.Month;
import nts.uk.ctx.at.shared.dom.common.MonthlyEstimateTime;
import nts.uk.ctx.at.shared.dom.common.WorkplaceId;
import nts.uk.ctx.at.shared.dom.common.Year;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.MonthlyUnit;
import nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.WkpDeforLaborSettingGetMemento;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.workingplace.KshstWkpDeforLarSet;

/**
 * The Class JpaWkpDeforLaborSettingGetMemento.
 */
public class JpaWkpDeforLaborSettingGetMemento implements WkpDeforLaborSettingGetMemento {

	/** The entity. */
	private KshstWkpDeforLarSet entity;

	/**
	 * Instantiates a new jpa com defor labor setting get memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaWkpDeforLaborSettingGetMemento(KshstWkpDeforLarSet entity) {
		this.entity = entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.
	 * DeforLaborSettingGetMemento#getYear()
	 */
	@Override
	public Year getYear() {
		return new Year(this.entity.getKshstWkpDeforLarSetPK().getYear());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.
	 * WkpDeforLaborSettingGetMemento#getCompanyId()
	 */
	@Override
	public CompanyId getCompanyId() {
		return new CompanyId(this.entity.getKshstWkpDeforLarSetPK().getCid());
	}

	@Override
	public WorkplaceId getWorkplaceId() {
		return new WorkplaceId(this.entity.getKshstWkpDeforLarSetPK().getWkpId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.
	 * DeforLaborSettingGetMemento#getStatutorySetting()
	 */
	@Override
	public List<MonthlyUnit> getStatutorySetting() {
		List<MonthlyUnit> monthlyUnits = new ArrayList<>();
		monthlyUnits.add(new MonthlyUnit(new Month(Month.JANUARY),
				new MonthlyEstimateTime(this.entity.getJanTime())));
		monthlyUnits.add(new MonthlyUnit(new Month(Month.FEBRUARY),
				new MonthlyEstimateTime(this.entity.getFebTime())));
		monthlyUnits.add(new MonthlyUnit(new Month(Month.MARCH),
				new MonthlyEstimateTime(this.entity.getMarTime())));
		monthlyUnits.add(new MonthlyUnit(new Month(Month.APRIL),
				new MonthlyEstimateTime(this.entity.getAprTime())));
		monthlyUnits.add(new MonthlyUnit(new Month(Month.MAY),
				new MonthlyEstimateTime(this.entity.getMayTime())));
		monthlyUnits.add(new MonthlyUnit(new Month(Month.JUNE),
				new MonthlyEstimateTime(this.entity.getJunTime())));
		monthlyUnits.add(new MonthlyUnit(new Month(Month.JULY),
				new MonthlyEstimateTime(this.entity.getJulTime())));
		monthlyUnits.add(new MonthlyUnit(new Month(Month.AUGUST),
				new MonthlyEstimateTime(this.entity.getAugTime())));
		monthlyUnits.add(new MonthlyUnit(new Month(Month.SEPTEMBER),
				new MonthlyEstimateTime(this.entity.getSepTime())));
		monthlyUnits.add(new MonthlyUnit(new Month(Month.OCTOBER),
				new MonthlyEstimateTime(this.entity.getOctTime())));
		monthlyUnits.add(new MonthlyUnit(new Month(Month.NOVEMBER),
				new MonthlyEstimateTime(this.entity.getNovTime())));
		monthlyUnits.add(new MonthlyUnit(new Month(Month.DECEMBER),
				new MonthlyEstimateTime(this.entity.getDecTime())));
		return monthlyUnits;
	}

}
