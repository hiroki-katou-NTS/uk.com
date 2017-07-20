/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.basicworkregister;

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
		memento.setSiftCode(this.workingCode);
		memento.setWorkDayDivision(this.workdayDivision);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((workingCode == null) ? 0 : workingCode.hashCode());
		result = prime * result + ((workdayDivision == null) ? 0 : workdayDivision.hashCode());
		result = prime * result + ((worktypeCode == null) ? 0 : worktypeCode.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BasicWorkSetting other = (BasicWorkSetting) obj;
		if (workingCode == null) {
			if (other.workingCode != null)
				return false;
		} else if (!workingCode.equals(other.workingCode))
			return false;
		if (workdayDivision != other.workdayDivision)
			return false;
		if (worktypeCode == null) {
			if (other.worktypeCode != null)
				return false;
		} else if (!worktypeCode.equals(other.worktypeCode))
			return false;
		return true;
	}
	
}
