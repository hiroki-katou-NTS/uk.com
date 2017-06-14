/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.employment.statutory.worktime;

import java.util.List;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.Year;
import nts.uk.ctx.at.shared.dom.employment.statutory.worktime.CompanySettingSetMemento;
import nts.uk.ctx.at.shared.dom.employment.statutory.worktime.shared.DeformationLaborSetting;
import nts.uk.ctx.at.shared.dom.employment.statutory.worktime.shared.FlexSetting;
import nts.uk.ctx.at.shared.dom.employment.statutory.worktime.shared.Monthly;
import nts.uk.ctx.at.shared.dom.employment.statutory.worktime.shared.NormalSetting;
import nts.uk.ctx.at.shared.dom.employment.statutory.worktime.shared.WorkingTimeSetting;
import nts.uk.ctx.at.shared.infra.entity.employment.statutory.worktime.JcwtstCompanyWtSet;
import nts.uk.ctx.at.shared.infra.entity.employment.statutory.worktime.JcwtstCompanyWtSetPK;

/**
 * The Class JpaCompanySettingSetMemento.
 */
@Getter
public class JpaCompanySettingSetMemento implements CompanySettingSetMemento {

	/** The normal. */
	private JcwtstCompanyWtSet normal;
	
	/** The flex statutory. */
	private JcwtstCompanyWtSet flexStatutory;
	
	/** The flex specified. */
	private JcwtstCompanyWtSet flexSpecified;
	
	/** The deformed. */
	private JcwtstCompanyWtSet deformed;

	/**
	 * Instantiates a new jpa company setting set memento.
	 */
	public JpaCompanySettingSetMemento() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.employment.statutory.worktime.
	 * CompanySettingSetMemento#setFlexSetting(nts.uk.ctx.at.shared.dom.
	 * employment.statutory.worktime.shared.FlexSetting)
	 */
	@Override
	public void setFlexSetting(FlexSetting flexSetting) {
		this.flexStatutory.getJcwtstCompanyWtSetPK().setCtg(1);
		this.flexStatutory.getJcwtstCompanyWtSetPK().setType(0);
		WorkingTimeSetting statutory = flexSetting.getStatutorySetting();
		this.flexStatutory.setDailyTime(statutory.getDaily().v());
		this.flexStatutory.setWeeklyTime(statutory.getWeekly().v());
		this.flexStatutory = this.setMonthly(this.flexStatutory, statutory.getMonthly());

		this.flexSpecified.getJcwtstCompanyWtSetPK().setCtg(1);
		this.flexSpecified.getJcwtstCompanyWtSetPK().setType(1);
		WorkingTimeSetting specified = flexSetting.getSpecifiedSetting();
		this.flexSpecified.setDailyTime(specified.getDaily().v());
		this.flexSpecified.setWeeklyTime(specified.getWeekly().v());
		this.flexSpecified = this.setMonthly(this.flexSpecified, specified.getMonthly());

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.employment.statutory.worktime.
	 * CompanySettingSetMemento#setDeformationLaborSetting(nts.uk.ctx.at.shared.
	 * dom.employment.statutory.worktime.shared.DeformationLaborSetting)
	 */
	@Override
	public void setDeformationLaborSetting(DeformationLaborSetting deformationLaborSetting) {
		this.deformed.getJcwtstCompanyWtSetPK().setCtg(2);
		this.deformed.getJcwtstCompanyWtSetPK().setType(0);
		this.deformed.setStrWeek(deformationLaborSetting.getWeekStart().value);
		WorkingTimeSetting wts = deformationLaborSetting.getStatutorySetting();
		this.deformed.setDailyTime(wts.getDaily().v());
		this.deformed.setWeeklyTime(wts.getWeekly().v());
		this.deformed = this.setMonthly(this.deformed, wts.getMonthly());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.employment.statutory.worktime.
	 * CompanySettingSetMemento#setYear(nts.uk.ctx.at.shared.dom.common.Year)
	 */
	@Override
	public void setYear(Year year) {
		JcwtstCompanyWtSetPK pk = this.normal.getJcwtstCompanyWtSetPK();
		pk.setYK(year.v());
		this.deformed = new JcwtstCompanyWtSet(pk);
		this.normal = new JcwtstCompanyWtSet(pk);
		this.flexSpecified = new JcwtstCompanyWtSet(pk);
		this.flexStatutory = new JcwtstCompanyWtSet(pk);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.employment.statutory.worktime.
	 * CompanySettingSetMemento#setCompanyId(nts.uk.ctx.at.shared.dom.common.
	 * CompanyId)
	 */
	@Override
	public void setCompanyId(CompanyId companyId) {
		JcwtstCompanyWtSetPK pk = new JcwtstCompanyWtSetPK();
		pk.setCid(companyId.v());
		this.deformed = new JcwtstCompanyWtSet(pk);
		this.normal = new JcwtstCompanyWtSet(pk);
		this.flexSpecified = new JcwtstCompanyWtSet(pk);
		this.flexStatutory = new JcwtstCompanyWtSet(pk);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.employment.statutory.worktime.
	 * CompanySettingSetMemento#setNormalSetting(nts.uk.ctx.at.shared.dom.
	 * employment.statutory.worktime.shared.NormalSetting)
	 */
	@Override
	public void setNormalSetting(NormalSetting normalSetting) {
		this.normal.getJcwtstCompanyWtSetPK().setCtg(0);
		this.normal.getJcwtstCompanyWtSetPK().setType(0);
		this.normal.setStrWeek(normalSetting.getWeekStart().value);
		WorkingTimeSetting wts = normalSetting.getStatutorySetting();
		this.normal.setDailyTime(wts.getDaily().v());
		this.normal.setWeeklyTime(wts.getWeekly().v());
		this.normal = this.setMonthly(this.normal, wts.getMonthly());
	}

	/**
	 * Sets the monthly.
	 *
	 * @param entity the entity
	 * @param monthly the monthly
	 * @return the jcwtst company wt set
	 */
	private JcwtstCompanyWtSet setMonthly(JcwtstCompanyWtSet entity, List<Monthly> monthly) {
		monthly.forEach(month -> {
			switch (month.getMonth()) {
			case JANUARY:
				entity.setJanTime(month.getTime().v());
				break;
			case FEBRUARY:
				entity.setFebTime(month.getTime().v());
				break;
			case MARCH:
				entity.setMarTime(month.getTime().v());
				break;
			case APRIL:
				entity.setAprTime(month.getTime().v());
				break;
			case MAY:
				entity.setMayTime(month.getTime().v());
				break;
			case JUNE:
				entity.setJunTime(month.getTime().v());
				break;
			case JULY:
				entity.setJulTime(month.getTime().v());
				break;
			case AUGUST:
				entity.setAugTime(month.getTime().v());
				break;
			case SEPTEMBER:
				entity.setSepTime(month.getTime().v());
				break;
			case OCTOBER:
				entity.setOctTime(month.getTime().v());
				break;
			case NOVEMBER:
				entity.setNovTime(month.getTime().v());
				break;
			case DECEMBER:
				entity.setDecTime(month.getTime().v());
				break;
			default:
				break;
			}
		});
		return entity;
	}

}
