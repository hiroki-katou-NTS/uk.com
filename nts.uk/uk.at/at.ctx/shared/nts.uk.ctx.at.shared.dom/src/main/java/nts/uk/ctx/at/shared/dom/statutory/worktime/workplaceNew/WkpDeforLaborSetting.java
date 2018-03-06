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
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.MonthStatutoryWorkingHourDeforWorker;


/**
 * The Class WkpDeforLaborSetting.
 */
@Getter
// 職場別変形労働月間労働時間
public class WkpDeforLaborSetting extends AggregateRoot implements MonthStatutoryWorkingHourDeforWorker{

	/** The company id. */
	private CompanyId companyId;
	
	/** The workplace id. */
	private WorkplaceId workplaceId;
	
	/** The year. */
	private Year year;
	
	/** The statutory setting. */
	private List<MonthlyTime> statutorySetting;
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.MonthStatutoryWorkingHourDeforWorker#getListStatutorySetting()
	 */
	@Override
	public List<MonthlyTime> getListStatutorySetting() {
		return statutorySetting;
	}
	
	/**
	 * Instantiates a new wkp defor labor setting.
	 *
	 * @param memento
	 *            the memento
	 */
	public WkpDeforLaborSetting(WkpDeforLaborSetting memento) {
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
	public void saveToMemento(WkpDeforLaborSettingSetMemento memento) {
		memento.setCompanyId(this.companyId);
		memento.setWorkplaceId(this.workplaceId);
		memento.setYear(this.year);
		memento.setStatutorySetting(this.statutorySetting);
	}
}
