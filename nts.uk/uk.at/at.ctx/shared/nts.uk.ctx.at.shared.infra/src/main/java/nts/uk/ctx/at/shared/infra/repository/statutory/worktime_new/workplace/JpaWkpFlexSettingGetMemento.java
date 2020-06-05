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
import nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.WkpFlexSettingGetMemento;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.workingplace.KshstWkpFlexSet;

/**
 * The Class JpaWkpFlexSettingGetMemento.
 */
public class JpaWkpFlexSettingGetMemento implements WkpFlexSettingGetMemento {

	/** The entity. */
	private KshstWkpFlexSet entity;

	/**
	 * Instantiates a new jpa com flex setting get memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaWkpFlexSettingGetMemento(KshstWkpFlexSet entity) {
		this.entity = entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.
	 * FlexSettingGetMemento#getYear()
	 */
	@Override
	public Year getYear() {
		return new Year(this.entity.getKshstWkpFlexSetPK().getYear());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.
	 * WkpFlexSettingGetMemento#getCompanyId()
	 */
	@Override
	public CompanyId getCompanyId() {
		return new CompanyId(this.entity.getKshstWkpFlexSetPK().getCid());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.
	 * WkpFlexSettingGetMemento#getWorkplaceId()
	 */
	@Override
	public WorkplaceId getWorkplaceId() {
		return new WorkplaceId(this.entity.getKshstWkpFlexSetPK().getWkpId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.
	 * FlexSettingGetMemento#getStatutorySetting()
	 */
	@Override
	public List<MonthlyUnit> getStatutorySetting() {
		List<MonthlyUnit> monthlyUnits = new ArrayList<>();
		monthlyUnits.add(new MonthlyUnit(new Month(Month.JANUARY),
				new MonthlyEstimateTime(this.entity.getStatJanTime())));
		monthlyUnits.add(new MonthlyUnit(new Month(Month.FEBRUARY),
				new MonthlyEstimateTime(this.entity.getStatFebTime())));
		monthlyUnits.add(new MonthlyUnit(new Month(Month.MARCH),
				new MonthlyEstimateTime(this.entity.getStatMarTime())));
		monthlyUnits.add(new MonthlyUnit(new Month(Month.APRIL),
				new MonthlyEstimateTime(this.entity.getStatAprTime())));
		monthlyUnits.add(new MonthlyUnit(new Month(Month.MAY),
				new MonthlyEstimateTime(this.entity.getStatMayTime())));
		monthlyUnits.add(new MonthlyUnit(new Month(Month.JUNE),
				new MonthlyEstimateTime(this.entity.getStatJunTime())));
		monthlyUnits.add(new MonthlyUnit(new Month(Month.JULY),
				new MonthlyEstimateTime(this.entity.getStatJulTime())));
		monthlyUnits.add(new MonthlyUnit(new Month(Month.AUGUST),
				new MonthlyEstimateTime(this.entity.getStatAugTime())));
		monthlyUnits.add(new MonthlyUnit(new Month(Month.SEPTEMBER),
				new MonthlyEstimateTime(this.entity.getStatSepTime())));
		monthlyUnits.add(new MonthlyUnit(new Month(Month.OCTOBER),
				new MonthlyEstimateTime(this.entity.getStatOctTime())));
		monthlyUnits.add(new MonthlyUnit(new Month(Month.NOVEMBER),
				new MonthlyEstimateTime(this.entity.getStatNovTime())));
		monthlyUnits.add(new MonthlyUnit(new Month(Month.DECEMBER),
				new MonthlyEstimateTime(this.entity.getStatDecTime())));
		return monthlyUnits;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.
	 * FlexSettingGetMemento#getSpecifiedSetting()
	 */
	@Override
	public List<MonthlyUnit> getSpecifiedSetting() {
		List<MonthlyUnit> monthlyUnits = new ArrayList<>();
		monthlyUnits.add(new MonthlyUnit(new Month(Month.JANUARY),
				new MonthlyEstimateTime(this.entity.getSpecJanTime())));
		monthlyUnits.add(new MonthlyUnit(new Month(Month.FEBRUARY),
				new MonthlyEstimateTime(this.entity.getSpecFebTime())));
		monthlyUnits.add(new MonthlyUnit(new Month(Month.MARCH),
				new MonthlyEstimateTime(this.entity.getSpecMarTime())));
		monthlyUnits.add(new MonthlyUnit(new Month(Month.APRIL),
				new MonthlyEstimateTime(this.entity.getSpecAprTime())));
		monthlyUnits.add(new MonthlyUnit(new Month(Month.MAY),
				new MonthlyEstimateTime(this.entity.getSpecMayTime())));
		monthlyUnits.add(new MonthlyUnit(new Month(Month.JUNE),
				new MonthlyEstimateTime(this.entity.getSpecJunTime())));
		monthlyUnits.add(new MonthlyUnit(new Month(Month.JULY),
				new MonthlyEstimateTime(this.entity.getSpecJulTime())));
		monthlyUnits.add(new MonthlyUnit(new Month(Month.AUGUST),
				new MonthlyEstimateTime(this.entity.getSpecAugTime())));
		monthlyUnits.add(new MonthlyUnit(new Month(Month.SEPTEMBER),
				new MonthlyEstimateTime(this.entity.getSpecSepTime())));
		monthlyUnits.add(new MonthlyUnit(new Month(Month.OCTOBER),
				new MonthlyEstimateTime(this.entity.getSpecOctTime())));
		monthlyUnits.add(new MonthlyUnit(new Month(Month.NOVEMBER),
				new MonthlyEstimateTime(this.entity.getSpecNovTime())));
		monthlyUnits.add(new MonthlyUnit(new Month(Month.DECEMBER),
				new MonthlyEstimateTime(this.entity.getSpecDecTime())));
		return monthlyUnits;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.
	 * FlexSettingGetMemento#getWeekAveSetting()
	 */
	@Override
	public List<MonthlyUnit> getWeekAveSetting() {
		List<MonthlyUnit> monthlyUnits = new ArrayList<>();
		monthlyUnits.add(new MonthlyUnit(new Month(Month.JANUARY),
				new MonthlyEstimateTime(this.entity.getWeekJanTime())));
		monthlyUnits.add(new MonthlyUnit(new Month(Month.FEBRUARY),
				new MonthlyEstimateTime(this.entity.getWeekFebTime())));
		monthlyUnits.add(new MonthlyUnit(new Month(Month.MARCH),
				new MonthlyEstimateTime(this.entity.getWeekMarTime())));
		monthlyUnits.add(new MonthlyUnit(new Month(Month.APRIL),
				new MonthlyEstimateTime(this.entity.getWeekAprTime())));
		monthlyUnits.add(new MonthlyUnit(new Month(Month.MAY),
				new MonthlyEstimateTime(this.entity.getWeekMayTime())));
		monthlyUnits.add(new MonthlyUnit(new Month(Month.JUNE),
				new MonthlyEstimateTime(this.entity.getWeekJunTime())));
		monthlyUnits.add(new MonthlyUnit(new Month(Month.JULY),
				new MonthlyEstimateTime(this.entity.getWeekJulTime())));
		monthlyUnits.add(new MonthlyUnit(new Month(Month.AUGUST),
				new MonthlyEstimateTime(this.entity.getWeekAugTime())));
		monthlyUnits.add(new MonthlyUnit(new Month(Month.SEPTEMBER),
				new MonthlyEstimateTime(this.entity.getWeekSepTime())));
		monthlyUnits.add(new MonthlyUnit(new Month(Month.OCTOBER),
				new MonthlyEstimateTime(this.entity.getWeekOctTime())));
		monthlyUnits.add(new MonthlyUnit(new Month(Month.NOVEMBER),
				new MonthlyEstimateTime(this.entity.getWeekNovTime())));
		monthlyUnits.add(new MonthlyUnit(new Month(Month.DECEMBER),
				new MonthlyEstimateTime(this.entity.getWeekDecTime())));
		return monthlyUnits;
	}
}
