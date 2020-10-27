/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.shift.basicworkregister;

import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.BasicWorkSettingSetMemento;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.WorkdayDivision;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.WorkingCode;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.WorktypeCode;
import nts.uk.ctx.at.schedule.infra.entity.shift.basicworkregister.KscmtBasicWorkCom;
import nts.uk.ctx.at.schedule.infra.entity.shift.basicworkregister.KscmtBasicWorkComPK;

/**
 * The Class JpaBWSettingComSetMemento.
 */
public class JpaBWSettingComSetMemento implements BasicWorkSettingSetMemento {

	/** The type value. */
	private KscmtBasicWorkCom typeValue;

	/**
	 * Instantiates a new jpa BW setting com set memento.
	 *
	 * @param typeValue the type value
	 */
	public JpaBWSettingComSetMemento(KscmtBasicWorkCom typeValue) {
		if(typeValue.getKscmtBasicWorkComPK() == null){
			typeValue.setKscmtBasicWorkComPK(new KscmtBasicWorkComPK());
		}
		this.typeValue = typeValue;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.basicworkregister.
	 * BasicWorkSettingSetMemento#setWorkTypeCode(nts.uk.ctx.at.schedule.dom.
	 * shift.basicworkregister.WorktypeCode)
	 */
	@Override
	public void setWorkTypeCode(WorktypeCode worktypeCode) {
		this.typeValue.setWorktypeCode(worktypeCode.v());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.basicworkregister.
	 * BasicWorkSettingSetMemento#setSiftCode(nts.uk.ctx.at.schedule.dom.shift.
	 * basicworkregister.WorkingCode)
	 */
	@Override
	public void setSiftCode(WorkingCode workingCode) {
		if (workingCode == null) {
			this.typeValue.setWorkingCode(null);
		} else {
			this.typeValue.setWorkingCode(workingCode.v());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.basicworkregister.
	 * BasicWorkSettingSetMemento#setWorkDayDivision(nts.uk.ctx.at.schedule.dom.
	 * shift.basicworkregister.WorkdayDivision)
	 */
	@Override
	public void setWorkDayDivision(WorkdayDivision workdayDivision) {
		this.typeValue.getKscmtBasicWorkComPK().setWorkdayDivision(workdayDivision.value);
	}

}
