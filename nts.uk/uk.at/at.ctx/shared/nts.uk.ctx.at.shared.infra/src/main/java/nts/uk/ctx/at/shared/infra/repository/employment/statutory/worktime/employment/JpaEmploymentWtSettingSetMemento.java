/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.employment.statutory.worktime.employment;

import java.util.List;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.Year;
import nts.uk.ctx.at.shared.dom.employment.statutory.worktime.employment.EmploymentWtSettingSetMemento;
import nts.uk.ctx.at.shared.dom.employment.statutory.worktime.shared.DeformationLaborSetting;
import nts.uk.ctx.at.shared.dom.employment.statutory.worktime.shared.FlexSetting;
import nts.uk.ctx.at.shared.dom.employment.statutory.worktime.shared.Monthly;
import nts.uk.ctx.at.shared.dom.employment.statutory.worktime.shared.NormalSetting;
import nts.uk.ctx.at.shared.dom.employment.statutory.worktime.shared.WorkingTimeSetting;
import nts.uk.ctx.at.shared.infra.entity.employment.statutory.worktime.employment.JewtstEmploymentWtSet;
import nts.uk.ctx.at.shared.infra.entity.employment.statutory.worktime.employment.JewtstEmploymentWtSetPK;
import nts.uk.ctx.at.shared.infra.repository.employment.statutory.worktime.WtSettingConstant;

/**
 * The Class JpaEmploymentWtSettingSetMemento.
 */
@Getter
public class JpaEmploymentWtSettingSetMemento implements EmploymentWtSettingSetMemento {

	/** The normal. */
	private JewtstEmploymentWtSet normal;

	/** The flex statutory. */
	private JewtstEmploymentWtSet flexStatutory;

	/** The flex specified. */
	private JewtstEmploymentWtSet flexSpecified;

	/** The deformed. */
	private JewtstEmploymentWtSet deformed;

	/**
	 * Instantiates a new jpa employment wt setting set memento.
	 */
	public JpaEmploymentWtSettingSetMemento() {
		this.deformed = new JewtstEmploymentWtSet();
		this.normal = new JewtstEmploymentWtSet();
		this.flexSpecified = new JewtstEmploymentWtSet();
		this.flexStatutory = new JewtstEmploymentWtSet();
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
		JewtstEmploymentWtSetPK staPk = this.flexStatutory.getJewtstEmploymentWtSetPK();
		staPk.setCtg(WtSettingConstant.FLEX);
		staPk.setType(WtSettingConstant.STATUTORY);
		this.flexStatutory.setJewtstEmploymentWtSetPK(staPk);

		// Set statutory work time setting.
		WorkingTimeSetting statutory = flexSetting.getStatutorySetting();
		this.flexStatutory.setDailyTime(statutory.getDaily().v());
		this.flexStatutory.setWeeklyTime(statutory.getWeekly().v());
		this.flexStatutory = this.setMonthly(this.flexStatutory, statutory.getMonthly());

		// Set specified category & type.
		JewtstEmploymentWtSetPK spePk = this.flexSpecified.getJewtstEmploymentWtSetPK();
		spePk.setCtg(WtSettingConstant.FLEX);
		spePk.setType(WtSettingConstant.SPECIFIED);
		this.flexSpecified.setJewtstEmploymentWtSetPK(spePk);

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
		JewtstEmploymentWtSetPK pk = this.deformed.getJewtstEmploymentWtSetPK();
		pk.setCtg(WtSettingConstant.DEFORMED);
		pk.setType(WtSettingConstant.STATUTORY);
		this.deformed.setJewtstEmploymentWtSetPK(pk);

		// Set start week
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
		JewtstEmploymentWtSetPK pkNo = this.normal.getJewtstEmploymentWtSetPK();
		JewtstEmploymentWtSetPK pkDe = this.deformed.getJewtstEmploymentWtSetPK();
		JewtstEmploymentWtSetPK pkSpe = this.flexSpecified.getJewtstEmploymentWtSetPK();
		JewtstEmploymentWtSetPK pkSta = this.flexStatutory.getJewtstEmploymentWtSetPK();

		pkNo.setYK(year.v());
		pkDe.setYK(year.v());
		pkSpe.setYK(year.v());
		pkSta.setYK(year.v());

		this.deformed.setJewtstEmploymentWtSetPK(pkDe);
		this.normal.setJewtstEmploymentWtSetPK(pkNo);
		this.flexSpecified.setJewtstEmploymentWtSetPK(pkSpe);
		this.flexStatutory.setJewtstEmploymentWtSetPK(pkSta);
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
		JewtstEmploymentWtSetPK pkNo = new JewtstEmploymentWtSetPK();
		JewtstEmploymentWtSetPK pkDe = new JewtstEmploymentWtSetPK();
		JewtstEmploymentWtSetPK pkSpe = new JewtstEmploymentWtSetPK();
		JewtstEmploymentWtSetPK pkSta = new JewtstEmploymentWtSetPK();

		pkNo.setCid(companyId.v());
		pkDe.setCid(companyId.v());
		pkSpe.setCid(companyId.v());
		pkSta.setCid(companyId.v());

		this.deformed.setJewtstEmploymentWtSetPK(pkDe);
		this.normal.setJewtstEmploymentWtSetPK(pkNo);
		this.flexSpecified.setJewtstEmploymentWtSetPK(pkSpe);
		this.flexStatutory.setJewtstEmploymentWtSetPK(pkSta);
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
		JewtstEmploymentWtSetPK pk = this.normal.getJewtstEmploymentWtSetPK();
		pk.setCtg(WtSettingConstant.NORMAL);
		pk.setType(WtSettingConstant.STATUTORY);
		this.normal.setJewtstEmploymentWtSetPK(pk);

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
	 * @param entity
	 *            the entity
	 * @param monthly
	 *            the monthly
	 * @return the jewtst employment wt set
	 */
	private JewtstEmploymentWtSet setMonthly(JewtstEmploymentWtSet entity, List<Monthly> monthly) {
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.employment.statutory.worktime.employment.
	 * EmploymentWtSettingSetMemento#setEmploymentCode(java.lang.String)
	 */
	@Override
	public void setEmploymentCode(String employmentCode) {
		JewtstEmploymentWtSetPK pkNo = this.normal.getJewtstEmploymentWtSetPK();
		JewtstEmploymentWtSetPK pkDe = this.deformed.getJewtstEmploymentWtSetPK();
		JewtstEmploymentWtSetPK pkSpe = this.flexSpecified.getJewtstEmploymentWtSetPK();
		JewtstEmploymentWtSetPK pkSta = this.flexStatutory.getJewtstEmploymentWtSetPK();

		pkNo.setEmptCd(employmentCode);
		pkDe.setEmptCd(employmentCode);
		pkSpe.setEmptCd(employmentCode);
		pkSta.setEmptCd(employmentCode);

		this.deformed.setJewtstEmploymentWtSetPK(pkDe);
		this.normal.setJewtstEmploymentWtSetPK(pkNo);
		this.flexSpecified.setJewtstEmploymentWtSetPK(pkSpe);
		this.flexStatutory.setJewtstEmploymentWtSetPK(pkSta);
	}

}
