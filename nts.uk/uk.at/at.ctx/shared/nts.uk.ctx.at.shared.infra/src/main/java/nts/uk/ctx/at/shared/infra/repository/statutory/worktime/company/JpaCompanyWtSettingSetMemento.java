/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.statutory.worktime.company;

import java.util.List;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.Year;
import nts.uk.ctx.at.shared.dom.statutory.worktime.company.CompanyWtSettingSetMemento;
import nts.uk.ctx.at.shared.dom.statutory.worktime.shared.DeformationLaborSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.shared.FlexSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.shared.Monthly;
import nts.uk.ctx.at.shared.dom.statutory.worktime.shared.NormalSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.shared.WorkingTimeSetting;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime.company.KcwstCompanyWtSet;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime.company.KcwstCompanyWtSetPK;
import nts.uk.ctx.at.shared.infra.repository.statutory.worktime.WtSettingConstant;

/**
 * The Class JpaCompanyWtSettingSetMemento.
 */
@Getter
public class JpaCompanyWtSettingSetMemento implements CompanyWtSettingSetMemento {

	/** The normal. */
	private KcwstCompanyWtSet normal;

	/** The flex statutory. */
	private KcwstCompanyWtSet flexStatutory;

	/** The flex specified. */
	private KcwstCompanyWtSet flexSpecified;

	/** The deformed. */
	private KcwstCompanyWtSet deformed;

	/**
	 * Instantiates a new jpa company wt setting set memento.
	 */
	public JpaCompanyWtSettingSetMemento() {
		this.deformed = new KcwstCompanyWtSet();
		this.normal = new KcwstCompanyWtSet();
		this.flexSpecified = new KcwstCompanyWtSet();
		this.flexStatutory = new KcwstCompanyWtSet();
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
		KcwstCompanyWtSetPK staPk = this.flexStatutory.getJcwstCompanyWtSetPK();
		staPk.setCtg(WtSettingConstant.FLEX);
		staPk.setType(WtSettingConstant.STATUTORY);
		this.flexStatutory.setJcwstCompanyWtSetPK(staPk);

		// Set statutory work time setting.
		WorkingTimeSetting statutory = flexSetting.getStatutorySetting();
		this.flexStatutory.setDailyTime(statutory.getDaily().valueAsMinutes());
		this.flexStatutory.setWeeklyTime(statutory.getWeekly().valueAsMinutes());
		this.flexStatutory = this.setMonthly(this.flexStatutory, statutory.getMonthly());

		// Set specified category & type.
		KcwstCompanyWtSetPK spePk = this.flexSpecified.getJcwstCompanyWtSetPK();
		spePk.setCtg(WtSettingConstant.FLEX);
		spePk.setType(WtSettingConstant.SPECIFIED);
		this.flexSpecified.setJcwstCompanyWtSetPK(spePk);

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
		KcwstCompanyWtSetPK pk = this.deformed.getJcwstCompanyWtSetPK();
		pk.setCtg(WtSettingConstant.DEFORMED);
		pk.setType(WtSettingConstant.STATUTORY);
		this.deformed.setJcwstCompanyWtSetPK(pk);

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
		KcwstCompanyWtSetPK pkNo = this.normal.getJcwstCompanyWtSetPK();
		KcwstCompanyWtSetPK pkDe = this.deformed.getJcwstCompanyWtSetPK();
		KcwstCompanyWtSetPK pkSpe = this.flexSpecified.getJcwstCompanyWtSetPK();
		KcwstCompanyWtSetPK pkSta = this.flexStatutory.getJcwstCompanyWtSetPK();

		pkNo.setYK(year.v());
		pkDe.setYK(year.v());
		pkSpe.setYK(year.v());
		pkSta.setYK(year.v());

		this.deformed.setJcwstCompanyWtSetPK(pkDe);
		this.normal.setJcwstCompanyWtSetPK(pkNo);
		this.flexSpecified.setJcwstCompanyWtSetPK(pkSpe);
		this.flexStatutory.setJcwstCompanyWtSetPK(pkSta);
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
		KcwstCompanyWtSetPK pkNo = new KcwstCompanyWtSetPK();
		KcwstCompanyWtSetPK pkDe = new KcwstCompanyWtSetPK();
		KcwstCompanyWtSetPK pkSpe = new KcwstCompanyWtSetPK();
		KcwstCompanyWtSetPK pkSta = new KcwstCompanyWtSetPK();

		pkNo.setCid(companyId.v());
		pkDe.setCid(companyId.v());
		pkSpe.setCid(companyId.v());
		pkSta.setCid(companyId.v());

		this.deformed.setJcwstCompanyWtSetPK(pkDe);
		this.normal.setJcwstCompanyWtSetPK(pkNo);
		this.flexSpecified.setJcwstCompanyWtSetPK(pkSpe);
		this.flexStatutory.setJcwstCompanyWtSetPK(pkSta);
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
		KcwstCompanyWtSetPK pk = this.normal.getJcwstCompanyWtSetPK();
		pk.setCtg(WtSettingConstant.NORMAL);
		pk.setType(WtSettingConstant.STATUTORY);
		this.normal.setJcwstCompanyWtSetPK(pk);

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
	 * @param entity the entity
	 * @param monthly the monthly
	 * @return the jcwtst company wt set
	 */
	private KcwstCompanyWtSet setMonthly(KcwstCompanyWtSet entity, List<Monthly> monthly) {
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

}
