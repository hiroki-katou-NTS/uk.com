/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.WorkplaceId;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.MonthStatutoryWorkingHourDeforWorker;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.NormalSetting;

/**
 * The Class WkpNormalSetting.
 */
@Getter
// 職場別通常勤務月間労働時間
public class WkpNormalSetting extends NormalSetting
		implements MonthStatutoryWorkingHourDeforWorker {

	/** The company id. */
	// 会社ID
	private CompanyId companyId;

	/** The workplace id. */
	// 職場ID
	private WorkplaceId workplaceId;

	/**
	 * Instantiates a new wkp normal setting.
	 *
	 * @param memento
	 *            the memento
	 */
	public WkpNormalSetting(WkpNormalSettingGetMemento memento) {
		this.companyId = memento.getCompanyId();
		this.workplaceId = memento.getWorkplaceId();
		this.year = memento.getYear();
		this.statutorySetting = memento.getStatutorySetting();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(WkpNormalSettingSetMemento memento) {
		memento.setCompanyId(this.companyId);
		memento.setWorkplaceId(this.workplaceId);
		memento.setYear(this.year);
		memento.setStatutorySetting(this.statutorySetting);
	}
}
