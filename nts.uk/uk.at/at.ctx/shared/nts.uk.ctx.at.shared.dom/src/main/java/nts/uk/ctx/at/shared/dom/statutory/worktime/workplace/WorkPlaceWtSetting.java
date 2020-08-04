/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.statutory.worktime.workplace;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.Year;
import nts.uk.ctx.at.shared.dom.statutory.worktime.shared.DeformationLaborSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.shared.FlexSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.shared.NormalSetting;

/**
 * 職場労働時間設定.
 */
@Getter
public class WorkPlaceWtSetting extends AggregateRoot {

	/** フレックス勤務労働時間設定. */
	private FlexSetting flexSetting;

	/** 変形労働労働時間設定. */
	private DeformationLaborSetting deformationLaborSetting;

	/** 年. */
	private Year year;

	/** 会社ID. */
	private CompanyId companyId;

	/** 職場ID. */
	private String workPlaceId;

	/** 通常勤務労働時間設定. */
	private NormalSetting normalSetting;

	/**
	 * Instantiates a new work place setting.
	 *
	 * @param memento the memento
	 */
	public WorkPlaceWtSetting(WorkPlaceWtSettingGetMemento memento) {
		this.companyId = memento.getCompanyId();
		this.flexSetting = memento.getFlexSetting();
		this.deformationLaborSetting = memento.getDeformationLaborSetting();
		this.normalSetting = memento.getNormalSetting();
		this.year = memento.getYear();
		this.workPlaceId = memento.getWorkPlaceId();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(WorkPlaceWtSettingSetMemento memento) {
		memento.setCompanyId(this.companyId);
		memento.setYear(this.year);
		memento.setDeformationLaborSetting(this.deformationLaborSetting);
		memento.setFlexSetting(this.flexSetting);
		memento.setNormalSetting(this.normalSetting);
		memento.setWorkPlaceId(this.workPlaceId);
	}
}
