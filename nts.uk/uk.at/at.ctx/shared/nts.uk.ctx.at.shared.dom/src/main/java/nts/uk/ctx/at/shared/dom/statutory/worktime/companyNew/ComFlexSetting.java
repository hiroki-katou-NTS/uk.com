/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.FlexSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.MonthStatutoryWorkingHourFlexWork;

/**
 * The Class ComFlexSetting.
 */
@Getter
// 会社別フレックス勤務月間労働時間.
public class ComFlexSetting extends FlexSetting implements MonthStatutoryWorkingHourFlexWork {

	/** The company id. */
	/** 会社ID. */
	private CompanyId companyId;

	/**
	 * Instantiates a new com flex setting.
	 *
	 * @param memento
	 *            the memento
	 */
	public ComFlexSetting(ComFlexSettingGetMemento memento) {
		this.companyId = memento.getCompanyId();
		this.year = memento.getYear();
		this.statutorySetting = memento.getStatutorySetting();
		this.specifiedSetting = memento.getSpecifiedSetting();
		this.weekAveSetting = memento.getWeekAveSetting();
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
		memento.setWeekAveSetting(this.weekAveSetting);
	}

}
