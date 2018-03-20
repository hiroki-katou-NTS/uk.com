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
import nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComNormalSettingSetMemento;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.MonthlyUnit;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.company.KshstComNormalSet;

/**
 * The Class JpaCompanyWtSettingSetMemento.
 */
@Getter
public class JpaComNormalSettingSetMemento implements ComNormalSettingSetMemento {
	
	private KshstComNormalSet entity;
	
	public JpaComNormalSettingSetMemento() {
		this.entity = new KshstComNormalSet();
	}

	@Override
	public void setYear(Year year) {
		this.entity.getKshstComNormalSetPK().setYear(year.v());
	}

	@Override
	public void setCompanyId(CompanyId companyId) {
		this.entity.getKshstComNormalSetPK().setCid(companyId.v());;
	}
	
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
	
	private Map<Integer, Integer> toMonthlyEstimateTimeMap (List<MonthlyUnit> statutorySetting) {
		return statutorySetting.stream().collect(Collectors.toMap(unit -> unit.getMonth().v(), unit -> unit.getMonthlyTime().v()));
	}

}
