/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.statutory.worktime_new.company;

import java.util.ArrayList;
import java.util.List;

import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.Year;
import nts.uk.ctx.at.shared.dom.common.Month;
import nts.uk.ctx.at.shared.dom.common.MonthlyEstimateTime;
import nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComNormalSettingGetMemento;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.MonthlyUnit;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.company.KshstComNormalSet;

/**
 * The Class JpaComNormalSettingGetMemento.
 */
public class JpaComNormalSettingGetMemento implements ComNormalSettingGetMemento {

	private KshstComNormalSet entity;

	public JpaComNormalSettingGetMemento(KshstComNormalSet entity) {
		this.entity = entity;
	}

	@Override
	public Year getYear() {
		return new Year(this.entity.getKshstComNormalSetPK().getYear());
	}

	@Override
	public CompanyId getCompanyId() {
		return new CompanyId(this.entity.getKshstComNormalSetPK().getCid());
	}

	@Override
	public List<MonthlyUnit> getStatutorySetting() {
		return this.toMonthlyUnits();
	}
	
	private List<MonthlyUnit> toMonthlyUnits() {
		List<MonthlyUnit> monthlyUnits = new ArrayList<>();
		monthlyUnits.add(new MonthlyUnit( new Month(Month.JANUARY), new MonthlyEstimateTime(this.entity.getJanTime())));
		monthlyUnits.add(new MonthlyUnit( new Month(Month.FEBRUARY), new MonthlyEstimateTime(this.entity.getFebTime())));
		monthlyUnits.add(new MonthlyUnit( new Month(Month.MARCH), new MonthlyEstimateTime(this.entity.getMarTime())));
		monthlyUnits.add(new MonthlyUnit( new Month(Month.APRIL), new MonthlyEstimateTime(this.entity.getAprTime())));
		monthlyUnits.add(new MonthlyUnit( new Month(Month.MAY), new MonthlyEstimateTime(this.entity.getMayTime())));
		monthlyUnits.add(new MonthlyUnit( new Month(Month.JUNE), new MonthlyEstimateTime(this.entity.getJunTime())));
		monthlyUnits.add(new MonthlyUnit( new Month(Month.JULY), new MonthlyEstimateTime(this.entity.getJulTime())));
		monthlyUnits.add(new MonthlyUnit( new Month(Month.AUGUST), new MonthlyEstimateTime(this.entity.getAugTime())));
		monthlyUnits.add(new MonthlyUnit( new Month(Month.SEPTEMBER), new MonthlyEstimateTime(this.entity.getSepTime())));
		monthlyUnits.add(new MonthlyUnit( new Month(Month.OCTOBER), new MonthlyEstimateTime(this.entity.getOctTime())));
		monthlyUnits.add(new MonthlyUnit( new Month(Month.NOVEMBER), new MonthlyEstimateTime(this.entity.getNovTime())));
		monthlyUnits.add(new MonthlyUnit( new Month(Month.DECEMBER), new MonthlyEstimateTime(this.entity.getDecTime())));
		return monthlyUnits;
	}

}
