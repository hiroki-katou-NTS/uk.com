/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.flexset;

import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.usecls.ApplyAtr;
import nts.uk.ctx.at.shared.dom.worktime.flexset.CoreTimeSettingSetMemento;
import nts.uk.ctx.at.shared.dom.worktime.flexset.TimeSheet;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtWtFle;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtWtFlePK;

/**
 * The Class JpaCoreTimeSettingGetMemento.
 */
public class JpaCoreTimeSettingSetMemento implements CoreTimeSettingSetMemento {
	
	/** The entity. */
	private KshmtWtFle entity;
	
	/**
	 * Instantiates a new jpa core time setting set memento.
	 *
	 * @param entity the entity
	 */
	public JpaCoreTimeSettingSetMemento(KshmtWtFle entity) {
		super();
		if(entity.getKshmtWtFlePK() == null){
			entity.setKshmtWtFlePK(new KshmtWtFlePK());
		}
		this.entity = entity;
	}



	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flexset.CoreTimeSettingSetMemento#
	 * setCoreTimeSheet(nts.uk.ctx.at.shared.dom.worktime.flexset.TimeSheet)
	 */
	@Override
	public void setCoreTimeSheet(TimeSheet coreTimeSheet) {
		if(coreTimeSheet!=null){
			coreTimeSheet.saveToMemento(new JpaFlexTimeSheetSetMemento(this.entity));
		}
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flexset.CoreTimeSettingSetMemento#
	 * setTimesheet(nts.uk.ctx.at.shared.dom.common.usecls.ApplyAtr)
	 */
	@Override
	public void setTimesheet(ApplyAtr timesheet) {
		this.entity.setCoretimeUseAtr(timesheet.value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flexset.CoreTimeSettingSetMemento#
	 * setMinWorkTime(nts.uk.ctx.at.shared.dom.common.time.AttendanceTime)
	 */
	@Override
	public void setMinWorkTime(AttendanceTime minWorkTime) {
		if (minWorkTime != null) {
			this.entity.setLeastWorkTime(minWorkTime.valueAsMinutes());
		}
	}
	

}
