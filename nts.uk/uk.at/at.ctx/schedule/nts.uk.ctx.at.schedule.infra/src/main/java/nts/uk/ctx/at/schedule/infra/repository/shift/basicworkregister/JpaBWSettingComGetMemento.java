/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.shift.basicworkregister;

import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.BasicWorkSettingGetMemento;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.WorkdayDivision;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.WorkingCode;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.WorktypeCode;
import nts.uk.ctx.at.schedule.infra.entity.shift.basicworkregister.KscmtBasicWorkCom;
import nts.uk.ctx.at.schedule.infra.entity.shift.basicworkregister.KscmtBasicWorkComPK;

/**
 * The Class JpaBWSettingComGetMemento.
 */
public class JpaBWSettingComGetMemento implements BasicWorkSettingGetMemento {

	/** The type value. */
	private KscmtBasicWorkCom typeValue;

	/**
	 * Instantiates a new jpa basic work setting get memento.
	 *
	 * @param typeValue
	 *            the type value
	 */
	public JpaBWSettingComGetMemento(KscmtBasicWorkCom typeValue) {
		super();
		if(typeValue.getKscmtBasicWorkComPK() == null){
			typeValue.setKscmtBasicWorkComPK(new KscmtBasicWorkComPK());
		}
		this.typeValue = typeValue;
	}

	/**
	 * Gets the work typecode.
	 *
	 * @return the work typecode
	 */
	@Override
	public WorktypeCode getWorkTypecode() {
		return new WorktypeCode(this.typeValue.getWorktypeCode());
	}

	/**
	 * Gets the sift code.
	 *
	 * @return the sift code
	 */
	@Override
	public WorkingCode getSiftCode() {
		return new WorkingCode(this.typeValue.getWorkingCode());
	}

	/**
	 * Gets the work day division.
	 *
	 * @return the work day division
	 */
	@Override
	public WorkdayDivision getWorkDayDivision() {
		return WorkdayDivision.valuesOf(this.typeValue.getKscmtBasicWorkComPK().getWorkdayDivision());
	}

}
