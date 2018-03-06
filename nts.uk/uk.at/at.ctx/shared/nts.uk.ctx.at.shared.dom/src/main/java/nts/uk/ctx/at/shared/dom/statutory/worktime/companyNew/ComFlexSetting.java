/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.MonthlyTime;
import nts.uk.ctx.at.shared.dom.common.Year;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.MonthStatutoryWorkingHourFlexWork;

/**
 * The Class ComFlexSetting.
 */
@Getter
@Setter
// 会社別フレックス勤務月間労働時間.
public class ComFlexSetting extends AggregateRoot implements MonthStatutoryWorkingHourFlexWork {

	/** The company id. */
	/** 会社ID. */
	private CompanyId companyId;

	/** The year. */
	/** 年. */
	private Year year;

	/** The statutory setting. */
	/** 法定時間. */
	private List<MonthlyTime> statutorySetting;

	/** The specified setting. */
	/** 所定時間. */
	private List<MonthlyTime> specifiedSetting;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.
	 * MonthStatutoryWorkingHourFlexWork#getListStatutorySetting()
	 */
	@Override
	public List<MonthlyTime> getListStatutorySetting() {
		return statutorySetting;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.
	 * MonthStatutoryWorkingHourFlexWork#getListSpecifiedSetting()
	 */
	@Override
	public List<MonthlyTime> getListSpecifiedSetting() {
		return specifiedSetting;
	}

	/**
	 * Instantiates a new com flex setting.
	 *
	 * @param memento
	 *            the memento
	 */
	public ComFlexSetting(ComFlexSetting memento) {
		this.companyId = memento.getCompanyId();
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
	public void saveToMemento(ComFlexSettingSetMemento memento) {
		memento.setCompanyId(this.companyId);
		memento.setYear(this.year);
		memento.setStatutorySetting(this.statutorySetting);
		memento.setSpecifiedSetting(this.specifiedSetting);
	}
}
