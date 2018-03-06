/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew;

import java.util.List;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.MonthlyTime;
import nts.uk.ctx.at.shared.dom.common.WorkplaceId;
import nts.uk.ctx.at.shared.dom.common.Year;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.MonthStatutoryWorkingHourFlexWork;

/**
 * The Class WkpFlexSetting.
 */
@Getter
// 職場別フレックス勤務月間労働時間
public class WkpFlexSetting extends AggregateRoot implements MonthStatutoryWorkingHourFlexWork {

	/** The company id. */
	private CompanyId companyId;

	/** The workplace id. */
	private WorkplaceId workplaceId;

	/** The year. */
	private Year year;

	/** The statutory setting. */
	private List<MonthlyTime> statutorySetting;

	/** The specified setting. */
	private List<MonthlyTime> specifiedSetting;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.
	 * MonthStatutoryWorkingHourFlexWork#getListStatutorySetting()
	 */
	@Override
	public List<MonthlyTime> getListStatutorySetting() {
		return statutorySetting;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.
	 * MonthStatutoryWorkingHourFlexWork#getListSpecifiedSetting()
	 */
	@Override
	public List<MonthlyTime> getListSpecifiedSetting() {
		return specifiedSetting;
	}

	/**
	 * Instantiates a new wkp flex setting.
	 *
	 * @param memento
	 *            the memento
	 */
	public WkpFlexSetting(WkpFlexSetting memento) {
		this.companyId = memento.getCompanyId();
		this.workplaceId = memento.getWorkplaceId();
		this.year = memento.getYear();
		this.statutorySetting = memento.getStatutorySetting();
		this.specifiedSetting = memento.getSpecifiedSetting();

	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(WkpFlexSettingSetMemento memento) {
		memento.setCompanyId(this.companyId);
		memento.setWorkplaceId(this.workplaceId);
		memento.setYear(this.year);
		memento.setStatutorySetting(this.statutorySetting);
		memento.setSpecifiedSetting(this.specifiedSetting);
	}
}
