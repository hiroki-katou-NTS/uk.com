/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;

/**
 * The Class CompensatoryLeaveComSetting.
 */
@Getter
public class CompensatoryLeaveComSetting extends AggregateRoot {
	
	/** The company id. */
	private String companyId;
	
	/** The is managed. */
	private ManageDistinct isManaged;
	
	/** The normal vacation setting. */
	private NormalVacationSetting normalVacationSetting;
	
	/** The occurrence vacation setting. */
	private OccurrenceVacationSetting occurrenceVacationSetting;
	
	public CompensatoryLeaveComSetting(CompensatoryLeaveComGetMemento memento)
	{
		this.companyId = memento.getCompanyId();
		this.isManaged = memento.getIsManaged();
		this.normalVacationSetting = memento.getNormalVacationSetting();
		this.occurrenceVacationSetting = memento.getOccurrenceVacationSetting();
	}
	
	public void saveToMemento(CompensatoryLeaveComSetMemento memento)
	{
		memento.setCompanyId(this.companyId);
		memento.setIsManaged(this.isManaged);
		memento.setNormalVacationSetting(this.normalVacationSetting);
		memento.setOccurrenceVacationSetting(this.occurrenceVacationSetting);
	}
}
