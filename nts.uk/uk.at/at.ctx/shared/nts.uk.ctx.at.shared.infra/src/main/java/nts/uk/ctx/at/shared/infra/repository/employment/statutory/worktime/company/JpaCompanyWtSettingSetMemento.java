/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.employment.statutory.worktime.company;

import java.util.List;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.Year;
import nts.uk.ctx.at.shared.dom.employment.statutory.worktime.company.CompanyWtSettingSetMemento;
import nts.uk.ctx.at.shared.dom.employment.statutory.worktime.shared.DeformationLaborSetting;
import nts.uk.ctx.at.shared.dom.employment.statutory.worktime.shared.FlexSetting;
import nts.uk.ctx.at.shared.dom.employment.statutory.worktime.shared.Monthly;
import nts.uk.ctx.at.shared.dom.employment.statutory.worktime.shared.NormalSetting;
import nts.uk.ctx.at.shared.dom.employment.statutory.worktime.shared.WorkingTimeSetting;
import nts.uk.ctx.at.shared.infra.entity.employment.statutory.worktime.company.JcwtstCompanyWtSet;
import nts.uk.ctx.at.shared.infra.entity.employment.statutory.worktime.company.JcwtstCompanyWtSetPK;

/**
 * The Class JpaCompanySettingSetMemento.
 */
@Getter
public class JpaCompanyWtSettingSetMemento implements CompanyWtSettingSetMemento {

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
	public JpaCompanyWtSettingSetMemento() {
		this.deformed = new JcwtstCompanyWtSet();
		this.normal = new JcwtstCompanyWtSet();
		this.flexSpecified = new JcwtstCompanyWtSet();
		this.flexStatutory = new JcwtstCompanyWtSet();
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
		// Set statutory category & type.
		JcwtstCompanyWtSetPK staPk = this.flexStatutory.getJcwtstCompanyWtSetPK();
		staPk.setCtg(1);
		staPk.setType(0);
		this.flexStatutory.setJcwtstCompanyWtSetPK(staPk);

		// Set statutory work time setting.
		WorkingTimeSetting statutory = flexSetting.getStatutorySetting();
		this.flexStatutory.setDailyTime(statutory.getDaily().v());
		this.flexStatutory.setWeeklyTime(statutory.getWeekly().v());
		this.flexStatutory = this.setMonthly(this.flexStatutory, statutory.getMonthly());

		// Set specified category & type.
		JcwtstCompanyWtSetPK spePk = this.flexSpecified.getJcwtstCompanyWtSetPK();
		spePk.setCtg(1);
		spePk.setType(1);
		this.flexSpecified.setJcwtstCompanyWtSetPK(spePk);

		// Set specified work time setting.
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
		// Set category & type.
		JcwtstCompanyWtSetPK pk = this.deformed.getJcwtstCompanyWtSetPK();
		pk.setCtg(2);
		pk.setType(0);
		this.deformed.setJcwtstCompanyWtSetPK(pk);

		//Set start week
		this.deformed.setStrWeek(deformationLaborSetting.getWeekStart().value);
		
		// Set work time setting.
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
		JcwtstCompanyWtSetPK pkNo = this.normal.getJcwtstCompanyWtSetPK();
		JcwtstCompanyWtSetPK pkDe = this.deformed.getJcwtstCompanyWtSetPK();
		JcwtstCompanyWtSetPK pkSpe = this.flexSpecified.getJcwtstCompanyWtSetPK();
		JcwtstCompanyWtSetPK pkSta = this.flexStatutory.getJcwtstCompanyWtSetPK();

		pkNo.setYK(year.v());
		pkDe.setYK(year.v());
		pkSpe.setYK(year.v());
		pkSta.setYK(year.v());

		this.deformed.setJcwtstCompanyWtSetPK(pkDe);
		this.normal.setJcwtstCompanyWtSetPK(pkNo);
		this.flexSpecified.setJcwtstCompanyWtSetPK(pkSpe);
		this.flexStatutory.setJcwtstCompanyWtSetPK(pkSta);
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
		JcwtstCompanyWtSetPK pkNo = new JcwtstCompanyWtSetPK();
		JcwtstCompanyWtSetPK pkDe = new JcwtstCompanyWtSetPK();
		JcwtstCompanyWtSetPK pkSpe = new JcwtstCompanyWtSetPK();
		JcwtstCompanyWtSetPK pkSta = new JcwtstCompanyWtSetPK();

		pkNo.setCid(companyId.v());
		pkDe.setCid(companyId.v());
		pkSpe.setCid(companyId.v());
		pkSta.setCid(companyId.v());

		this.deformed.setJcwtstCompanyWtSetPK(pkDe);
		this.normal.setJcwtstCompanyWtSetPK(pkNo);
		this.flexSpecified.setJcwtstCompanyWtSetPK(pkSpe);
		this.flexStatutory.setJcwtstCompanyWtSetPK(pkSta);
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
		// Set category & type.
		JcwtstCompanyWtSetPK pk = this.normal.getJcwtstCompanyWtSetPK();
		pk.setCtg(0);
		pk.setType(0);
		this.normal.setJcwtstCompanyWtSetPK(pk);

		// Set start week.
		this.normal.setStrWeek(normalSetting.getWeekStart().value);

		// Set work time setting.
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
