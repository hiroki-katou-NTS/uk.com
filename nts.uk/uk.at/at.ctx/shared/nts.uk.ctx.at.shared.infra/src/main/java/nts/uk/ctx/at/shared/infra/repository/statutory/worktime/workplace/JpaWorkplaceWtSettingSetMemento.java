/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.statutory.worktime.workplace;

import java.util.List;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.Year;
import nts.uk.ctx.at.shared.dom.statutory.worktime.shared.DeformationLaborSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.shared.FlexSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.shared.Monthly;
import nts.uk.ctx.at.shared.dom.statutory.worktime.shared.NormalSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.shared.WorkingTimeSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.workplace.WorkPlaceWtSettingSetMemento;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime.workplace.KwwstWorkplaceWtSet;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime.workplace.KwwstWorkplaceWtSetPK;
import nts.uk.ctx.at.shared.infra.repository.statutory.worktime.WtSettingConstant;

/**
 * The Class JpaWorkplaceWtSettingSetMemento.
 */
@Getter
public class JpaWorkplaceWtSettingSetMemento implements WorkPlaceWtSettingSetMemento {

	/** The normal. */
	private KwwstWorkplaceWtSet normal;

	/** The flex statutory. */
	private KwwstWorkplaceWtSet flexStatutory;

	/** The flex specified. */
	private KwwstWorkplaceWtSet flexSpecified;

	/** The deformed. */
	private KwwstWorkplaceWtSet deformed;

	/**
	 * Instantiates a new jpa workplace wt setting set memento.
	 */
	public JpaWorkplaceWtSettingSetMemento() {
		this.deformed = new KwwstWorkplaceWtSet();
		this.normal = new KwwstWorkplaceWtSet();
		this.flexSpecified = new KwwstWorkplaceWtSet();
		this.flexStatutory = new KwwstWorkplaceWtSet();
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
		KwwstWorkplaceWtSetPK staPk = this.flexStatutory.getJwwstWorkplaceWtSetPK();
		staPk.setCtg(WtSettingConstant.FLEX);
		staPk.setType(WtSettingConstant.STATUTORY);
		this.flexStatutory.setJwwstWorkplaceWtSetPK(staPk);

		// Set statutory work time setting.
		WorkingTimeSetting statutory = flexSetting.getStatutorySetting();
		this.flexStatutory.setDailyTime(statutory.getDaily().valueAsMinutes());
		this.flexStatutory.setWeeklyTime(statutory.getWeekly().valueAsMinutes());
		this.flexStatutory = this.setMonthly(this.flexStatutory, statutory.getMonthly());

		// Set specified category & type.
		KwwstWorkplaceWtSetPK spePk = this.flexSpecified.getJwwstWorkplaceWtSetPK();
		spePk.setCtg(WtSettingConstant.FLEX);
		spePk.setType(WtSettingConstant.SPECIFIED);
		this.flexSpecified.setJwwstWorkplaceWtSetPK(spePk);

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
		KwwstWorkplaceWtSetPK pk = this.deformed.getJwwstWorkplaceWtSetPK();
		pk.setCtg(WtSettingConstant.DEFORMED);
		pk.setType(WtSettingConstant.STATUTORY);
		this.deformed.setJwwstWorkplaceWtSetPK(pk);

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
		KwwstWorkplaceWtSetPK pkNo = this.normal.getJwwstWorkplaceWtSetPK();
		KwwstWorkplaceWtSetPK pkDe = this.deformed.getJwwstWorkplaceWtSetPK();
		KwwstWorkplaceWtSetPK pkSpe = this.flexSpecified.getJwwstWorkplaceWtSetPK();
		KwwstWorkplaceWtSetPK pkSta = this.flexStatutory.getJwwstWorkplaceWtSetPK();

		pkNo.setYK(year.v());
		pkDe.setYK(year.v());
		pkSpe.setYK(year.v());
		pkSta.setYK(year.v());

		this.deformed.setJwwstWorkplaceWtSetPK(pkDe);
		this.normal.setJwwstWorkplaceWtSetPK(pkNo);
		this.flexSpecified.setJwwstWorkplaceWtSetPK(pkSpe);
		this.flexStatutory.setJwwstWorkplaceWtSetPK(pkSta);
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
		KwwstWorkplaceWtSetPK pkNo = new KwwstWorkplaceWtSetPK();
		KwwstWorkplaceWtSetPK pkDe = new KwwstWorkplaceWtSetPK();
		KwwstWorkplaceWtSetPK pkSpe = new KwwstWorkplaceWtSetPK();
		KwwstWorkplaceWtSetPK pkSta = new KwwstWorkplaceWtSetPK();

		pkNo.setCid(companyId.v());
		pkDe.setCid(companyId.v());
		pkSpe.setCid(companyId.v());
		pkSta.setCid(companyId.v());

		this.deformed.setJwwstWorkplaceWtSetPK(pkDe);
		this.normal.setJwwstWorkplaceWtSetPK(pkNo);
		this.flexSpecified.setJwwstWorkplaceWtSetPK(pkSpe);
		this.flexStatutory.setJwwstWorkplaceWtSetPK(pkSta);
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
		KwwstWorkplaceWtSetPK pk = this.normal.getJwwstWorkplaceWtSetPK();
		pk.setCtg(WtSettingConstant.NORMAL);
		pk.setType(WtSettingConstant.STATUTORY);
		this.normal.setJwwstWorkplaceWtSetPK(pk);

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
	 * @return the jwpwtst workplace wt set
	 */
	private KwwstWorkplaceWtSet setMonthly(KwwstWorkplaceWtSet entity, List<Monthly> monthly) {
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
	 * @see nts.uk.ctx.at.shared.dom.employment.statutory.worktime.workplace.
	 * WorkPlaceWtSettingSetMemento#setWorkPlaceId(java.lang.String)
	 */
	@Override
	public void setWorkPlaceId(String workPlaceId) {
		KwwstWorkplaceWtSetPK pkNo = this.normal.getJwwstWorkplaceWtSetPK();
		KwwstWorkplaceWtSetPK pkDe = this.deformed.getJwwstWorkplaceWtSetPK();
		KwwstWorkplaceWtSetPK pkSpe = this.flexSpecified.getJwwstWorkplaceWtSetPK();
		KwwstWorkplaceWtSetPK pkSta = this.flexStatutory.getJwwstWorkplaceWtSetPK();

		pkNo.setWkpId(workPlaceId);
		pkDe.setWkpId(workPlaceId);
		pkSpe.setWkpId(workPlaceId);
		pkSta.setWkpId(workPlaceId);

		this.deformed.setJwwstWorkplaceWtSetPK(pkDe);
		this.normal.setJwwstWorkplaceWtSetPK(pkNo);
		this.flexSpecified.setJwwstWorkplaceWtSetPK(pkSpe);
		this.flexStatutory.setJwwstWorkplaceWtSetPK(pkSta);

	}

}
