/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew;

import java.util.List;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.MonthStatutoryWorkingHourDeforWorker;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.MonthlyUnit;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.NormalSetting;

/**
 * The Class ComNormalSetting.
 */
@Getter
// 会社別通常勤務月間労働時間.
public class ComNormalSetting extends NormalSetting
		implements MonthStatutoryWorkingHourDeforWorker {

	/** The company id. */
	// 会社ID
	private CompanyId companyId;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.
	 * MonthStatutoryWorkingHourDeforWorker#getListStatutorySetting()
	 */
	@Override
	public List<MonthlyUnit> getStatutorySetting() {
		return statutorySetting;
	}

	/**
	 * Instantiates a new com normal setting.
	 *
	 * @param memento
	 *            the memento
	 */
	public ComNormalSetting(ComNormalSettingGetMemento memento) {
		this.companyId = memento.getCompanyId();
		this.year = memento.getYear();
		this.statutorySetting = memento.getStatutorySetting();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(ComNormalSettingSetMemento memento) {
		memento.setCompanyId(this.companyId);
		memento.setYear(this.year);
		memento.setStatutorySetting(this.statutorySetting);
	}

}
