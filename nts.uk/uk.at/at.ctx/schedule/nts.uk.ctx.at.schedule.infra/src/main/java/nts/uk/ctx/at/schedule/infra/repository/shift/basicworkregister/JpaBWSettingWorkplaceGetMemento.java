/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.shift.basicworkregister;

import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.BasicWorkSettingGetMemento;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.WorkdayDivision;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.WorkingCode;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.WorktypeCode;
import nts.uk.ctx.at.schedule.infra.entity.shift.basicworkregister.KscmtBasicWorkWkp;
import nts.uk.ctx.at.schedule.infra.entity.shift.basicworkregister.KscmtBasicWorkWkpPK;

/**
 * The Class JpaBWSettingWorkplaceGetMemento.
 */
public class JpaBWSettingWorkplaceGetMemento implements BasicWorkSettingGetMemento {

	/** The type value. */
	private KscmtBasicWorkWkp typeValue;

	/**
	 * Instantiates a new jpa BW setting workplace get memento.
	 *
	 * @param typeValue
	 *            the type value
	 */
	public JpaBWSettingWorkplaceGetMemento(KscmtBasicWorkWkp typeValue) {
		super();
		if(typeValue.getKscmtBasicWorkWkpPK() == null){
			typeValue.setKscmtBasicWorkWkpPK(new KscmtBasicWorkWkpPK());
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
		return this.typeValue.getWorkingCode() == null ? null : new WorkingCode(this.typeValue.getWorkingCode());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.basicworkregister.
	 * BasicWorkSettingGetMemento#getWorkDayDivision()
	 */
	@Override
	public WorkdayDivision getWorkDayDivision() {
		return WorkdayDivision.valuesOf(this.typeValue.getKscmtBasicWorkWkpPK().getWorkdayDivision());
	}

}
