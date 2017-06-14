/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.employment.statutory.worktime;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import nts.uk.ctx.at.shared.dom.common.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.Year;
import nts.uk.ctx.at.shared.dom.employment.statutory.worktime.CompanySettingGetMemento;
import nts.uk.ctx.at.shared.dom.employment.statutory.worktime.shared.DeformationLaborSetting;
import nts.uk.ctx.at.shared.dom.employment.statutory.worktime.shared.FlexSetting;
import nts.uk.ctx.at.shared.dom.employment.statutory.worktime.shared.Monthly;
import nts.uk.ctx.at.shared.dom.employment.statutory.worktime.shared.NormalSetting;
import nts.uk.ctx.at.shared.dom.employment.statutory.worktime.shared.WeekStart;
import nts.uk.ctx.at.shared.dom.employment.statutory.worktime.shared.WorkingTimeSetting;
import nts.uk.ctx.at.shared.infra.entity.employment.statutory.worktime.JcwtstCompanyWtSet;

/**
 * The Class JpaCompanySettingGetMemento.
 */
public class JpaCompanySettingGetMemento implements CompanySettingGetMemento {

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

	/**
	 * Instantiates a new jpa company setting get memento.
	 *
	 * @param typeValue the type value
	 */
	public JpaCompanySettingGetMemento(List<JcwtstCompanyWtSet> typeValue) {
		this.companyId = new CompanyId(typeValue.get(0).getJcwtstCompanyWtSetPK().getCid());
		this.year = new Year(typeValue.get(0).getJcwtstCompanyWtSetPK().getYK());
		this.flexSetting = new FlexSetting();

		typeValue.forEach(item -> {
			switch (item.getJcwtstCompanyWtSetPK().getCtg()) {
			case 0:
				this.normalSetting = new NormalSetting(this.getWorkTimeSetting(item),
						WeekStart.valueOf(item.getWeeklyTime()));
				break;
			case 1:
				if (item.getJcwtstCompanyWtSetPK().getType() == 1) {
					this.flexSetting.setSpecifiedSetting(this.getWorkTimeSetting(item));
					break;
				}
				this.flexSetting.setStatutorySetting(this.getWorkTimeSetting(item));
				break;
			case 2:
				this.deformedSetting = new DeformationLaborSetting(this.getWorkTimeSetting(item),
						WeekStart.valueOf(item.getWeeklyTime()));
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
	private WorkingTimeSetting getWorkTimeSetting(JcwtstCompanyWtSet item) {
		WorkingTimeSetting wts = new WorkingTimeSetting();
		wts.setDaily(new AttendanceTime(item.getDailyTime()));
		wts.setWeekly(new AttendanceTime(item.getDailyTime()));
		wts.setMonthly(this.getMonthly(item));
		return wts;
	}

	/**
	 * Gets the monthly.
	 *
	 * @param item the item
	 * @return the monthly
	 */
	private Set<Monthly> getMonthly(JcwtstCompanyWtSet item) {
		Set<Monthly> monthly = new HashSet<Monthly>();
		monthly.add(new Monthly(new AttendanceTime(item.getJanTime()), java.time.Month.JANUARY));
		monthly.add(new Monthly(new AttendanceTime(item.getFebTime()), java.time.Month.FEBRUARY));
		monthly.add(new Monthly(new AttendanceTime(item.getMarTime()), java.time.Month.MARCH));
		monthly.add(new Monthly(new AttendanceTime(item.getAprTime()), java.time.Month.APRIL));
		monthly.add(new Monthly(new AttendanceTime(item.getMayTime()), java.time.Month.MAY));
		monthly.add(new Monthly(new AttendanceTime(item.getJunTime()), java.time.Month.JUNE));
		monthly.add(new Monthly(new AttendanceTime(item.getJulTime()), java.time.Month.JULY));
		monthly.add(new Monthly(new AttendanceTime(item.getAugTime()), java.time.Month.AUGUST));
		monthly.add(new Monthly(new AttendanceTime(item.getSepTime()), java.time.Month.SEPTEMBER));
		monthly.add(new Monthly(new AttendanceTime(item.getOctTime()), java.time.Month.OCTOBER));
		monthly.add(new Monthly(new AttendanceTime(item.getNovTime()), java.time.Month.NOVEMBER));
		monthly.add(new Monthly(new AttendanceTime(item.getDecTime()), java.time.Month.DECEMBER));
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

}
