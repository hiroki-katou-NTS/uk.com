/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.statutory.worktime_new.company;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.Month;
import nts.uk.ctx.at.shared.dom.common.Year;
import nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComFlexSettingSetMemento;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.MonthlyUnit;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.company.KshstComFlexSet;

/**
 * The Class JpaCompanyWtSettingSetMemento.
 */
@Getter
public class JpaComFlexSettingSetMemento implements ComFlexSettingSetMemento {
	
	private KshstComFlexSet entity;

	public JpaComFlexSettingSetMemento() {
		this.entity = new KshstComFlexSet(); 
	}

	@Override
	public void setCompanyId(CompanyId companyId) {
		this.entity.getKshstComFlexSetPK().setCid(companyId.v());
	}

	@Override
	public void setYear(Year year) {
		this.entity.getKshstComFlexSetPK().setYear(year.v());
	}

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
	
	private Map<Integer, Integer> toMonthlyEstimateTimeMap (List<MonthlyUnit> setting) {
		return setting.stream().collect(Collectors.toMap(unit -> unit.getMonth().v(), unit -> unit.getMonthlyTime().v()));
	}
}
