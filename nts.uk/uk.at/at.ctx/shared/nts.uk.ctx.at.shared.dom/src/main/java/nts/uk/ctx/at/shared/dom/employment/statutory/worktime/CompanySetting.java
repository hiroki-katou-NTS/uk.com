/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.employment.statutory.worktime;

import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.Year;
import nts.uk.ctx.at.shared.dom.employment.statutory.worktime.shared.DeformationLaborSetting;
import nts.uk.ctx.at.shared.dom.employment.statutory.worktime.shared.FlexSetting;
import nts.uk.ctx.at.shared.dom.employment.statutory.worktime.shared.NormalSetting;

/**
 * 会社労働時間設定.
 */
public class CompanySetting extends AggregateRoot {

	/** フレックス勤務労働時間設定. */
	private FlexSetting flexSetting;

	/** 変形労働労働時間設定. */
	private DeformationLaborSetting deformationLaborSetting;

	/** 年. */
	private Year year;

	/** 会社ID. */
	private CompanyId companyId;

	/** 通常勤務労働時間設定. */
	private NormalSetting normalSetting;

	/**
	 * Instantiates a new company setting.
	 *
	 * @param memento the memento
	 */
	public CompanySetting(CompanySettingGetMemento memento) {
		this.companyId = memento.getCompanyId();
		this.flexSetting = memento.getFlexSetting();
		this.deformationLaborSetting = memento.getDeformationLaborSetting();
		this.normalSetting = memento.getNormalSetting();
		this.year = memento.getYear();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(CompanySettingSetMemento memento) {
		memento.setCompanyId(this.companyId);
		memento.setYear(this.year);
		memento.setDeformationLaborSetting(this.deformationLaborSetting);
		memento.setFlexSetting(this.flexSetting);
		memento.setNormalSetting(this.normalSetting);
	}
}
