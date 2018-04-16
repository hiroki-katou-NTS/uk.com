/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.DeforLaborSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.MonthStatutoryWorkingHourDeforWorker;

/**
 * The Class ComDeformationLaborSetting.
 */
@Getter
// 会社別変形労働月間労働時間
public class ComDeforLaborSetting extends DeforLaborSetting
		implements MonthStatutoryWorkingHourDeforWorker {

	// 会社ID
	/** The company id. */
	private CompanyId companyId;

	/**
	 * Instantiates a new com deformation labor setting.
	 *
	 * @param memento
	 *            the memento
	 */
	public ComDeforLaborSetting(ComDeforLaborSettingGetMemento memento) {
		super();
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
	public void saveToMemento(ComDeforLaborSettingSetMemento memento) {
		memento.setCompanyId(this.companyId);
		memento.setYear(this.year);
		memento.setStatutorySetting(this.statutorySetting);
	}

}
