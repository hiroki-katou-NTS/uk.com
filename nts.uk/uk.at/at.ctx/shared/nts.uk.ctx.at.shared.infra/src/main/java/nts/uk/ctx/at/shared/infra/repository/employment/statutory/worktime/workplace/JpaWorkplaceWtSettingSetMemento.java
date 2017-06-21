/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.employment.statutory.worktime.workplace;

import java.util.List;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.Year;
import nts.uk.ctx.at.shared.dom.employment.statutory.worktime.shared.DeformationLaborSetting;
import nts.uk.ctx.at.shared.dom.employment.statutory.worktime.shared.FlexSetting;
import nts.uk.ctx.at.shared.dom.employment.statutory.worktime.shared.Monthly;
import nts.uk.ctx.at.shared.dom.employment.statutory.worktime.shared.NormalSetting;
import nts.uk.ctx.at.shared.dom.employment.statutory.worktime.shared.WorkingTimeSetting;
import nts.uk.ctx.at.shared.dom.employment.statutory.worktime.workplace.WorkPlaceWtSettingSetMemento;
import nts.uk.ctx.at.shared.infra.entity.employment.statutory.worktime.workplace.JwpwtstWorkplaceWtSet;
import nts.uk.ctx.at.shared.infra.entity.employment.statutory.worktime.workplace.JwpwtstWorkplaceWtSetPK;
import nts.uk.ctx.at.shared.infra.repository.employment.statutory.worktime.WtSettingConstant;

/**
 * The Class JpaWorkplaceWtSettingSetMemento.
 */
@Getter
public class JpaWorkplaceWtSettingSetMemento implements WorkPlaceWtSettingSetMemento {

	/** The normal. */
	private JwpwtstWorkplaceWtSet normal;

	/** The flex statutory. */
	private JwpwtstWorkplaceWtSet flexStatutory;

	/** The flex specified. */
	private JwpwtstWorkplaceWtSet flexSpecified;

	/** The deformed. */
	private JwpwtstWorkplaceWtSet deformed;

	/**
	 * Instantiates a new jpa workplace wt setting set memento.
	 */
	public JpaWorkplaceWtSettingSetMemento() {
		this.deformed = new JwpwtstWorkplaceWtSet();
		this.normal = new JwpwtstWorkplaceWtSet();
		this.flexSpecified = new JwpwtstWorkplaceWtSet();
		this.flexStatutory = new JwpwtstWorkplaceWtSet();
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
		JwpwtstWorkplaceWtSetPK staPk = this.flexStatutory.getJwpwtstWorkplaceWtSetPK();
		staPk.setCtg(WtSettingConstant.FLEX);
		staPk.setType(WtSettingConstant.STATUTORY);
		this.flexStatutory.setJwpwtstWorkplaceWtSetPK(staPk);

		// Set statutory work time setting.
		WorkingTimeSetting statutory = flexSetting.getStatutorySetting();
		this.flexStatutory.setDailyTime(statutory.getDaily().v());
		this.flexStatutory.setWeeklyTime(statutory.getWeekly().v());
		this.flexStatutory = this.setMonthly(this.flexStatutory, statutory.getMonthly());

		// Set specified category & type.
		JwpwtstWorkplaceWtSetPK spePk = this.flexSpecified.getJwpwtstWorkplaceWtSetPK();
		spePk.setCtg(WtSettingConstant.FLEX);
		spePk.setType(WtSettingConstant.SPECIFIED);
		this.flexSpecified.setJwpwtstWorkplaceWtSetPK(spePk);

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
		JwpwtstWorkplaceWtSetPK pk = this.deformed.getJwpwtstWorkplaceWtSetPK();
		pk.setCtg(WtSettingConstant.DEFORMED);
		pk.setType(WtSettingConstant.STATUTORY);
		this.deformed.setJwpwtstWorkplaceWtSetPK(pk);

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
		JwpwtstWorkplaceWtSetPK pkNo = this.normal.getJwpwtstWorkplaceWtSetPK();
		JwpwtstWorkplaceWtSetPK pkDe = this.deformed.getJwpwtstWorkplaceWtSetPK();
		JwpwtstWorkplaceWtSetPK pkSpe = this.flexSpecified.getJwpwtstWorkplaceWtSetPK();
		JwpwtstWorkplaceWtSetPK pkSta = this.flexStatutory.getJwpwtstWorkplaceWtSetPK();

		pkNo.setYK(year.v());
		pkDe.setYK(year.v());
		pkSpe.setYK(year.v());
		pkSta.setYK(year.v());

		this.deformed.setJwpwtstWorkplaceWtSetPK(pkDe);
		this.normal.setJwpwtstWorkplaceWtSetPK(pkNo);
		this.flexSpecified.setJwpwtstWorkplaceWtSetPK(pkSpe);
		this.flexStatutory.setJwpwtstWorkplaceWtSetPK(pkSta);
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
		JwpwtstWorkplaceWtSetPK pkNo = new JwpwtstWorkplaceWtSetPK();
		JwpwtstWorkplaceWtSetPK pkDe = new JwpwtstWorkplaceWtSetPK();
		JwpwtstWorkplaceWtSetPK pkSpe = new JwpwtstWorkplaceWtSetPK();
		JwpwtstWorkplaceWtSetPK pkSta = new JwpwtstWorkplaceWtSetPK();

		pkNo.setCid(companyId.v());
		pkDe.setCid(companyId.v());
		pkSpe.setCid(companyId.v());
		pkSta.setCid(companyId.v());

		this.deformed.setJwpwtstWorkplaceWtSetPK(pkDe);
		this.normal.setJwpwtstWorkplaceWtSetPK(pkNo);
		this.flexSpecified.setJwpwtstWorkplaceWtSetPK(pkSpe);
		this.flexStatutory.setJwpwtstWorkplaceWtSetPK(pkSta);
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
		JwpwtstWorkplaceWtSetPK pk = this.normal.getJwpwtstWorkplaceWtSetPK();
		pk.setCtg(WtSettingConstant.NORMAL);
		pk.setType(WtSettingConstant.STATUTORY);
		this.normal.setJwpwtstWorkplaceWtSetPK(pk);

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
	 * @return the jwpwtst workplace wt set
	 */
	private JwpwtstWorkplaceWtSet setMonthly(JwpwtstWorkplaceWtSet entity, List<Monthly> monthly) {
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
	 * @see nts.uk.ctx.at.shared.dom.employment.statutory.worktime.workplace.
	 * WorkPlaceWtSettingSetMemento#setWorkPlaceId(java.lang.String)
	 */
	@Override
	public void setWorkPlaceId(String workPlaceId) {
		JwpwtstWorkplaceWtSetPK pkNo = this.normal.getJwpwtstWorkplaceWtSetPK();
		JwpwtstWorkplaceWtSetPK pkDe = this.deformed.getJwpwtstWorkplaceWtSetPK();
		JwpwtstWorkplaceWtSetPK pkSpe = this.flexSpecified.getJwpwtstWorkplaceWtSetPK();
		JwpwtstWorkplaceWtSetPK pkSta = this.flexStatutory.getJwpwtstWorkplaceWtSetPK();

		pkNo.setWkpId(workPlaceId);
		pkDe.setWkpId(workPlaceId);
		pkSpe.setWkpId(workPlaceId);
		pkSta.setWkpId(workPlaceId);

		this.deformed.setJwpwtstWorkplaceWtSetPK(pkDe);
		this.normal.setJwpwtstWorkplaceWtSetPK(pkNo);
		this.flexSpecified.setJwpwtstWorkplaceWtSetPK(pkSpe);
		this.flexStatutory.setJwpwtstWorkplaceWtSetPK(pkSta);

	}

}
