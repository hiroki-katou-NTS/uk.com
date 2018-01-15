/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.statutory.worktime.employment;

import java.util.ArrayList;
import java.util.List;

import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.DailyTime;
import nts.uk.ctx.at.shared.dom.common.MonthlyTime;
import nts.uk.ctx.at.shared.dom.common.WeeklyTime;
import nts.uk.ctx.at.shared.dom.common.Year;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employment.EmploymentWtSettingGetMemento;
import nts.uk.ctx.at.shared.dom.statutory.worktime.shared.DeformationLaborSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.shared.FlexSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.shared.Monthly;
import nts.uk.ctx.at.shared.dom.statutory.worktime.shared.NormalSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.shared.WeekStart;
import nts.uk.ctx.at.shared.dom.statutory.worktime.shared.WorkingTimeSetting;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime.employment.KewstEmploymentWtSet;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime.employment.KewstEmploymentWtSetPK;
import nts.uk.ctx.at.shared.infra.repository.statutory.worktime.WtSettingConstant;

/**
 * The Class JpaEmploymentWtSettingGetMemento.
 */
public class JpaEmploymentWtSettingGetMemento implements EmploymentWtSettingGetMemento {

	/** The company id. */
	private CompanyId companyId;

	/** The normal setting. */
	private NormalSetting normalSetting;

	/** The flex setting. */
	private FlexSetting flexSetting;

	/** The deformed setting. */
	private DeformationLaborSetting deformedSetting;

	/** The year. */
	private Year year;

	/** The employment code. */
	private String employmentCode;

	/**
	 * Instantiates a new jpa employment wt setting get memento.
	 *
	 * @param typeValues the type values
	 */
	public JpaEmploymentWtSettingGetMemento(List<KewstEmploymentWtSet> typeValues) {
		// Get pk
		KewstEmploymentWtSetPK pk = typeValues.get(WtSettingConstant.NORMAL).getJewstEmploymentWtSetPK();
		this.companyId = new CompanyId(pk.getCid());
		this.year = new Year(pk.getYK());
		this.employmentCode = pk.getEmptCd();

		this.setToDomain(typeValues);
	}
	
	/**
	 * Sets the to domain.
	 *
	 * @param entities the new to domain
	 */
	private void setToDomain(List<KewstEmploymentWtSet> entities) {
		this.flexSetting = new FlexSetting();
		entities.forEach(item -> {
			switch (item.getJewstEmploymentWtSetPK().getCtg()) {
			case WtSettingConstant.NORMAL:
				this.normalSetting = new NormalSetting();
				this.normalSetting.setStatutorySetting(this.getWorkTimeSetting(item));
				this.normalSetting.setWeekStart(WeekStart.valueOf(item.getStrWeek()));
				break;
			case WtSettingConstant.FLEX:
				if (item.getJewstEmploymentWtSetPK().getType() == WtSettingConstant.SPECIFIED) {
					this.flexSetting.setSpecifiedSetting(this.getWorkTimeSetting(item));
					break;
				}
				this.flexSetting.setStatutorySetting(this.getWorkTimeSetting(item));
				break;
			case WtSettingConstant.DEFORMED:
				this.deformedSetting = new DeformationLaborSetting();
				this.deformedSetting.setStatutorySetting(this.getWorkTimeSetting(item));
				this.deformedSetting.setWeekStart(WeekStart.valueOf(item.getStrWeek()));
				break;
			default:
				break;
			}
		});
	}

	/**
	 * Gets the work time setting.
	 *
	 * @param item the item
	 * @return the work time setting
	 */
	private WorkingTimeSetting getWorkTimeSetting(KewstEmploymentWtSet item) {
		WorkingTimeSetting wts = new WorkingTimeSetting();
		wts.setDaily(new DailyTime(item.getDailyTime()));
		wts.setWeekly(new WeeklyTime(item.getWeeklyTime()));
		wts.setMonthly(this.getMonthly(item));
		return wts;
	}

	/**
	 * Gets the monthly.
	 *
	 * @param item the item
	 * @return the monthly
	 */
	private List<Monthly> getMonthly(KewstEmploymentWtSet item) {
		List<Monthly> monthly = new ArrayList<Monthly>();
		monthly.add(new Monthly(new MonthlyTime(item.getJanTime()), java.time.Month.JANUARY));
		monthly.add(new Monthly(new MonthlyTime(item.getFebTime()), java.time.Month.FEBRUARY));
		monthly.add(new Monthly(new MonthlyTime(item.getMarTime()), java.time.Month.MARCH));
		monthly.add(new Monthly(new MonthlyTime(item.getAprTime()), java.time.Month.APRIL));
		monthly.add(new Monthly(new MonthlyTime(item.getMayTime()), java.time.Month.MAY));
		monthly.add(new Monthly(new MonthlyTime(item.getJunTime()), java.time.Month.JUNE));
		monthly.add(new Monthly(new MonthlyTime(item.getJulTime()), java.time.Month.JULY));
		monthly.add(new Monthly(new MonthlyTime(item.getAugTime()), java.time.Month.AUGUST));
		monthly.add(new Monthly(new MonthlyTime(item.getSepTime()), java.time.Month.SEPTEMBER));
		monthly.add(new Monthly(new MonthlyTime(item.getOctTime()), java.time.Month.OCTOBER));
		monthly.add(new Monthly(new MonthlyTime(item.getNovTime()), java.time.Month.NOVEMBER));
		monthly.add(new Monthly(new MonthlyTime(item.getDecTime()), java.time.Month.DECEMBER));
		return monthly;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.employment.statutory.worktime.
	 * CompanySettingGetMemento#getFlexSetting()
	 */
	@Override
	public FlexSetting getFlexSetting() {
		return this.flexSetting;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.employment.statutory.worktime.
	 * CompanySettingGetMemento#getDeformationLaborSetting()
	 */
	@Override
	public DeformationLaborSetting getDeformationLaborSetting() {
		return this.deformedSetting;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.employment.statutory.worktime.
	 * CompanySettingGetMemento#getYear()
	 */
	@Override
	public Year getYear() {
		return this.year;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.employment.statutory.worktime.
	 * CompanySettingGetMemento#getCompanyId()
	 */
	@Override
	public CompanyId getCompanyId() {
		return this.companyId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.employment.statutory.worktime.
	 * CompanySettingGetMemento#getNormalSetting()
	 */
	@Override
	public NormalSetting getNormalSetting() {
		return this.normalSetting;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.employment.statutory.worktime.employment.
	 * EmploymentWtSettingGetMemento#getEmploymentCode()
	 */
	@Override
	public String getEmploymentCode() {
		return this.employmentCode;
	}

}
