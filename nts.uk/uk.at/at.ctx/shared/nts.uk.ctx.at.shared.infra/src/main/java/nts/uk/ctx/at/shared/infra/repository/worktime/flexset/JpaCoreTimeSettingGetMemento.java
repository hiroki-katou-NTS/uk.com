/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.flexset;

import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.usecls.ApplyAtr;
import nts.uk.ctx.at.shared.dom.worktime.flexset.CoreTimeSettingGetMemento;
import nts.uk.ctx.at.shared.dom.worktime.flexset.TimeSheet;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtWtFle;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtWtFlePK;

/**
 * The Class JpaCoreTimeSettingGetMemento.
 */
public class JpaCoreTimeSettingGetMemento implements CoreTimeSettingGetMemento {
	
	/** The entity. */
	private KshmtWtFle entity;
	
	/**
	 * Instantiates a new jpa core time setting get memento.
	 *
	 * @param entity the entity
	 */
	public JpaCoreTimeSettingGetMemento(KshmtWtFle entity) {
		super();
		if(entity.getKshmtWtFlePK() == null){
			entity.setKshmtWtFlePK(new KshmtWtFlePK());
		}
		this.entity = entity;
	}

	/**
	 * Gets the core time sheet.
	 *
	 * @return the core time sheet
	 */
	@Override
	public TimeSheet getCoreTimeSheet() {
		return new TimeSheet(new JpaFlexTimeSheetGetMemento(this.entity));
	}

	/**
	 * Gets the timesheet.
	 *
	 * @return the timesheet
	 */
	@Override
	public ApplyAtr getTimesheet() {
		return ApplyAtr.valueOf(this.entity.getCoretimeUseAtr());
	}

	/**
	 * Gets the min work time.
	 *
	 * @return the min work time
	 */
	@Override
	public AttendanceTime getMinWorkTime() {
		return new AttendanceTime(this.entity.getLeastWorkTime());
	}
	

}
