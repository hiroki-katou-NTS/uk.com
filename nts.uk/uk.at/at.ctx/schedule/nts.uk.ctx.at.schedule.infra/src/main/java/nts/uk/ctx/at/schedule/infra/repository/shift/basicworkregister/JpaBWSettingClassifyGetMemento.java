/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.shift.basicworkregister;

import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.BasicWorkSettingGetMemento;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.WorkdayDivision;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.WorkingCode;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.WorktypeCode;
import nts.uk.ctx.at.schedule.infra.entity.shift.basicworkregister.KscmtBasicWorkCls;
import nts.uk.ctx.at.schedule.infra.entity.shift.basicworkregister.KscmtBasicWorkClsPK;

/**
 * The Class JpaBWSettingClassifyGetMemento.
 */
public class JpaBWSettingClassifyGetMemento implements BasicWorkSettingGetMemento {

	/** The type value. */
	private KscmtBasicWorkCls typeValue;

	/**
	 * Instantiates a new jpa BW setting classify get memento.
	 *
	 * @param typeValue
	 *            the type value
	 */
	public JpaBWSettingClassifyGetMemento(KscmtBasicWorkCls typeValue) {
		super();
		if(typeValue.getKscmtBasicWorkClsPK() == null){
			typeValue.setKscmtBasicWorkClsPK(new KscmtBasicWorkClsPK());
		}
		this.typeValue = typeValue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.basicworkregister.
	 * BasicWorkSettingGetMemento#getWorkTypecode()
	 */
	@Override
	public WorktypeCode getWorkTypecode() {
		return new WorktypeCode(this.typeValue.getWorktypeCode());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.basicworkregister.
	 * BasicWorkSettingGetMemento#getSiftCode()
	 */
	@Override
	public WorkingCode getSiftCode() {
		if (this.typeValue.getWorkingCode() == null) {
			return null;
		}
		return new WorkingCode(this.typeValue.getWorkingCode());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.basicworkregister.
	 * BasicWorkSettingGetMemento#getWorkDayDivision()
	 */
	@Override
	public WorkdayDivision getWorkDayDivision() {
		return WorkdayDivision.valuesOf(this.typeValue.getKscmtBasicWorkClsPK().getWorkdayDivision());
	}

}
