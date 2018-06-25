/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.find.shift.basicworkregister.dto;

import org.apache.commons.lang3.StringUtils;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.BasicWorkSettingSetMemento;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.WorkdayDivision;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.WorkingCode;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.WorktypeCode;


/**
 * The Class BasicWorkSettingFindDto.
 */
@Getter
@Setter
public class BasicWorkSettingFindDto implements BasicWorkSettingSetMemento {

	/** The work type code. */
	private String workTypeCode;

	/** The working code. */
	private String workingCode;

	/** The work day division. */
	private Integer workDayDivision;

	/** The work type display name. */
	private String workTypeDisplayName;

	/** The working display name. */
	private String workingDisplayName;

	/*
	 * (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.shift.basicworkregister.
	 * BasicWorkSettingSetMemento#setWorkTypeCode(nts.uk.ctx.at.schedule.dom.
	 * shift.basicworkregister.WorktypeCode)
	 */
	@Override
	public void setWorkTypeCode(WorktypeCode worktypeCode) {
		this.workTypeCode = worktypeCode.v();
	}

	/*
	 * (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.shift.basicworkregister.
	 * BasicWorkSettingSetMemento#setSiftCode(nts.uk.ctx.at.schedule.dom.shift.
	 * basicworkregister.WorkingCode)
	 */
	@Override
	public void setSiftCode(WorkingCode workingCode) {
		if (workingCode == null) {
			this.workingCode = StringUtils.EMPTY;
		} else {
			this.workingCode = workingCode.v();
		}
		
	}

	/*
	 * (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.shift.basicworkregister.
	 * BasicWorkSettingSetMemento#setWorkDayDivision(nts.uk.ctx.at.schedule.dom.
	 * shift.basicworkregister.WorkdayDivision)
	 */
	@Override
	public void setWorkDayDivision(WorkdayDivision workdayDivision) {
		this.workDayDivision = workdayDivision.value;
	}

	/**
	 * Sets the work type display name.
	 *
	 * @param worktypeDisplayName the new work type display name
	 */
	public void setWorkTypeDisplayName(String worktypeDisplayName) {
		this.workTypeDisplayName = worktypeDisplayName;
	}

	/**
	 * Sets the working display name.
	 *
	 * @param workingDisplayName the new working display name
	 */
	public void setWorkingDisplayName(String workingDisplayName) {
		this.workingDisplayName = workingDisplayName;
	}
}
