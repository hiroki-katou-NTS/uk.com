/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.basicworkregister;

import org.apache.commons.lang3.StringUtils;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * The Class BasicWorkSetting. 基本勤務設定
 */

@Getter
public class BasicWorkSetting extends DomainObject {
	
	/** The work type code. */
	private WorktypeCode worktypeCode;
	
	/** The sift code. */
	private WorkingCode workingCode;
	
	/** The work day division. */
	private WorkdayDivision workdayDivision;
	
	/**
	 * Instantiates a new basic work setting.
	 *
	 * @param memento the memento
	 */
	public BasicWorkSetting(BasicWorkSettingGetMemento memento) {
		this.worktypeCode = memento.getWorkTypecode();
		this.workingCode = memento.getSiftCode();
		this.workdayDivision = memento.getWorkDayDivision();
	}
	
	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(BasicWorkSettingSetMemento memento) {
		memento.setWorkTypeCode(this.worktypeCode);
		if (this.workingCode == null || StringUtils.isEmpty(this.workingCode.v())) {
			memento.setSiftCode(null);
		} else {
			memento.setSiftCode(this.workingCode);
		}
		memento.setWorkDayDivision(this.workdayDivision);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((workdayDivision == null) ? 0 : workdayDivision.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BasicWorkSetting other = (BasicWorkSetting) obj;
		if (workdayDivision != other.workdayDivision)
			return false;
		return true;
	}

	public BasicWorkSetting(WorktypeCode worktypeCode, WorkingCode workingCode, WorkdayDivision workdayDivision) {
		super();
		this.worktypeCode = worktypeCode;
		this.workingCode = workingCode;
		this.workdayDivision = workdayDivision;
	}

	
	
}
