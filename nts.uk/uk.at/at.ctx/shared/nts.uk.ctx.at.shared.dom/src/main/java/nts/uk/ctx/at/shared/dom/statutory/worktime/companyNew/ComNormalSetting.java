/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew;

import java.util.List;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.MonthlyTime;
import nts.uk.ctx.at.shared.dom.common.Year;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.MonthStatutoryWorkingHourDeforWorker;

/**
 * The Class ComNormalSetting.
 */
@Getter
// 会社別通常勤務月間労働時間.
public class ComNormalSetting extends AggregateRoot implements MonthStatutoryWorkingHourDeforWorker{

	/** The company id. */
	/** 会社ID. */
	private CompanyId companyId;
	
	/** The year. */
	/** 年. */
	private Year year;
	
	/** The statutory setting. */
	/** 法定時間. */
	private List<MonthlyTime> statutorySetting;

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.MonthStatutoryWorkingHourDeforWorker#getListStatutorySetting()
	 */
	@Override
	public List<MonthlyTime> getListStatutorySetting() {
		return statutorySetting;
	}
	
	/**
	 * Instantiates a new com normal setting.
	 *
	 * @param memento the memento
	 */
	public ComNormalSetting (ComNormalSetting memento) {
		this.companyId  = memento.getCompanyId();
		this.year = memento.getYear();
		this.statutorySetting = memento.getStatutorySetting();
	}
	
	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento (ComNormalSettingSetMemento memento) {
		memento.setCompanyId(this.companyId);
		memento.setYear(this.year);
		memento.setStatutorySetting(this.statutorySetting);	
	}

}
