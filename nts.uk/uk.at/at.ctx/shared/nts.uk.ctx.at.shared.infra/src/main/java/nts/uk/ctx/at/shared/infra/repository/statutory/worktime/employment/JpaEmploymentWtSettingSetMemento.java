/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.statutory.worktime.employment;

import java.util.List;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.Year;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employment.EmploymentWtSettingSetMemento;
import nts.uk.ctx.at.shared.dom.statutory.worktime.shared.DeformationLaborSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.shared.FlexSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.shared.Monthly;
import nts.uk.ctx.at.shared.dom.statutory.worktime.shared.NormalSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.shared.WorkingTimeSetting;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime.employment.KewstEmploymentWtSet;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime.employment.KewstEmploymentWtSetPK;
import nts.uk.ctx.at.shared.infra.repository.statutory.worktime.WtSettingConstant;

/**
 * The Class JpaEmploymentWtSettingSetMemento.
 */
@Getter
public class JpaEmploymentWtSettingSetMemento implements EmploymentWtSettingSetMemento {

	/** The normal. */
	private KewstEmploymentWtSet normal;

	/** The flex statutory. */
	private KewstEmploymentWtSet flexStatutory;

	/** The flex specified. */
	private KewstEmploymentWtSet flexSpecified;

	/** The deformed. */
	private KewstEmploymentWtSet deformed;

	/**
	 * Instantiates a new jpa employment wt setting set memento.
	 */
	public JpaEmploymentWtSettingSetMemento() {
		this.deformed = new KewstEmploymentWtSet();
		this.normal = new KewstEmploymentWtSet();
		this.flexSpecified = new KewstEmploymentWtSet();
		this.flexStatutory = new KewstEmploymentWtSet();
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
		KewstEmploymentWtSetPK staPk = this.flexStatutory.getJewstEmploymentWtSetPK();
		staPk.setCtg(WtSettingConstant.FLEX);
		staPk.setType(WtSettingConstant.STATUTORY);
		this.flexStatutory.setJewstEmploymentWtSetPK(staPk);

		// Set statutory work time setting.
		WorkingTimeSetting statutory = flexSetting.getStatutorySetting();
		this.flexStatutory.setDailyTime(statutory.getDaily().valueAsMinutes());
		this.flexStatutory.setWeeklyTime(statutory.getWeekly().valueAsMinutes());
		this.flexStatutory = this.setMonthly(this.flexStatutory, statutory.getMonthly());

		// Set specified category & type.
		KewstEmploymentWtSetPK spePk = this.flexSpecified.getJewstEmploymentWtSetPK();
		spePk.setCtg(WtSettingConstant.FLEX);
		spePk.setType(WtSettingConstant.SPECIFIED);
		this.flexSpecified.setJewstEmploymentWtSetPK(spePk);

		// Set specified work time setting.
		WorkingTimeSetting specified = flexSetting.getSpecifiedSetting();
		this.flexSpecified.setDailyTime(specified.getDaily().valueAsMinutes());
		this.flexSpecified.setWeeklyTime(specified.getWeekly().valueAsMinutes());
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
		KewstEmploymentWtSetPK pk = this.deformed.getJewstEmploymentWtSetPK();
		pk.setCtg(WtSettingConstant.DEFORMED);
		pk.setType(WtSettingConstant.STATUTORY);
		this.deformed.setJewstEmploymentWtSetPK(pk);

		// Set start week
		this.deformed.setStrWeek(deformationLaborSetting.getWeekStart().value);

		// Set work time setting.
		WorkingTimeSetting wts = deformationLaborSetting.getStatutorySetting();
		this.deformed.setDailyTime(wts.getDaily().valueAsMinutes());
		this.deformed.setWeeklyTime(wts.getWeekly().valueAsMinutes());
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
		KewstEmploymentWtSetPK pkNo = this.normal.getJewstEmploymentWtSetPK();
		KewstEmploymentWtSetPK pkDe = this.deformed.getJewstEmploymentWtSetPK();
		KewstEmploymentWtSetPK pkSpe = this.flexSpecified.getJewstEmploymentWtSetPK();
		KewstEmploymentWtSetPK pkSta = this.flexStatutory.getJewstEmploymentWtSetPK();

		pkNo.setYK(year.v());
		pkDe.setYK(year.v());
		pkSpe.setYK(year.v());
		pkSta.setYK(year.v());

		this.deformed.setJewstEmploymentWtSetPK(pkDe);
		this.normal.setJewstEmploymentWtSetPK(pkNo);
		this.flexSpecified.setJewstEmploymentWtSetPK(pkSpe);
		this.flexStatutory.setJewstEmploymentWtSetPK(pkSta);
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
		KewstEmploymentWtSetPK pkNo = new KewstEmploymentWtSetPK();
		KewstEmploymentWtSetPK pkDe = new KewstEmploymentWtSetPK();
		KewstEmploymentWtSetPK pkSpe = new KewstEmploymentWtSetPK();
		KewstEmploymentWtSetPK pkSta = new KewstEmploymentWtSetPK();

		pkNo.setCid(companyId.v());
		pkDe.setCid(companyId.v());
		pkSpe.setCid(companyId.v());
		pkSta.setCid(companyId.v());

		this.deformed.setJewstEmploymentWtSetPK(pkDe);
		this.normal.setJewstEmploymentWtSetPK(pkNo);
		this.flexSpecified.setJewstEmploymentWtSetPK(pkSpe);
		this.flexStatutory.setJewstEmploymentWtSetPK(pkSta);
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
		KewstEmploymentWtSetPK pk = this.normal.getJewstEmploymentWtSetPK();
		pk.setCtg(WtSettingConstant.NORMAL);
		pk.setType(WtSettingConstant.STATUTORY);
		this.normal.setJewstEmploymentWtSetPK(pk);

		// Set start week.
		this.normal.setStrWeek(normalSetting.getWeekStart().value);

		// Set work time setting.
		WorkingTimeSetting wts = normalSetting.getStatutorySetting();
		this.normal.setDailyTime(wts.getDaily().valueAsMinutes());
		this.normal.setWeeklyTime(wts.getWeekly().valueAsMinutes());
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
	private KewstEmploymentWtSet setMonthly(KewstEmploymentWtSet entity, List<Monthly> monthly) {
		monthly.forEach(month -> {
			switch (month.getMonth()) {
			case JANUARY:
				entity.setJanTime(month.getTime().valueAsMinutes());
				break;
			case FEBRUARY:
				entity.setFebTime(month.getTime().valueAsMinutes());
				break;
			case MARCH:
				entity.setMarTime(month.getTime().valueAsMinutes());
				break;
			case APRIL:
				entity.setAprTime(month.getTime().valueAsMinutes());
				break;
			case MAY:
				entity.setMayTime(month.getTime().valueAsMinutes());
				break;
			case JUNE:
				entity.setJunTime(month.getTime().valueAsMinutes());
				break;
			case JULY:
				entity.setJulTime(month.getTime().valueAsMinutes());
				break;
			case AUGUST:
				entity.setAugTime(month.getTime().valueAsMinutes());
				break;
			case SEPTEMBER:
				entity.setSepTime(month.getTime().valueAsMinutes());
				break;
			case OCTOBER:
				entity.setOctTime(month.getTime().valueAsMinutes());
				break;
			case NOVEMBER:
				entity.setNovTime(month.getTime().valueAsMinutes());
				break;
			case DECEMBER:
				entity.setDecTime(month.getTime().valueAsMinutes());
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
		KewstEmploymentWtSetPK pkNo = this.normal.getJewstEmploymentWtSetPK();
		KewstEmploymentWtSetPK pkDe = this.deformed.getJewstEmploymentWtSetPK();
		KewstEmploymentWtSetPK pkSpe = this.flexSpecified.getJewstEmploymentWtSetPK();
		KewstEmploymentWtSetPK pkSta = this.flexStatutory.getJewstEmploymentWtSetPK();

		pkNo.setEmptCd(employmentCode);
		pkDe.setEmptCd(employmentCode);
		pkSpe.setEmptCd(employmentCode);
		pkSta.setEmptCd(employmentCode);

		this.deformed.setJewstEmploymentWtSetPK(pkDe);
		this.normal.setJewstEmploymentWtSetPK(pkNo);
		this.flexSpecified.setJewstEmploymentWtSetPK(pkSpe);
		this.flexStatutory.setJewstEmploymentWtSetPK(pkSta);
	}

}
