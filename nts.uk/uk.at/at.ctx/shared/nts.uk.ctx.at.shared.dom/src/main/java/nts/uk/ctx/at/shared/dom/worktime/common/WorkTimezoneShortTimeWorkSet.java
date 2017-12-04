/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * The Class WorkTimezoneShortTimeWorkSet.
 */
//就業時間帯の短時間勤務設定
@Getter
public class WorkTimezoneShortTimeWorkSet extends DomainObject{

	/** The nurs timezone work use. */
	//介護時間帯に勤務した場合に勤務として扱う
	private boolean nursTimezoneWorkUse;
	
	/** The employment time deduct. */
	//就業時間から控除する
	private boolean employmentTimeDeduct;
	
	/** The child care work use. */
	//育児時間帯に勤務した場合に勤務として扱う
	private boolean childCareWorkUse;

	/**
	 * Instantiates a new work timezone short time work set.
	 *
	 * @param memento the memento
	 */
	public WorkTimezoneShortTimeWorkSet(WorkTimezoneShortTimeWorkSetGetMemento memento) {
		this.nursTimezoneWorkUse = memento.getNursTimezoneWorkUse();
		this.employmentTimeDeduct = memento.getEmploymentTimeDeduct();
		this.childCareWorkUse = memento.getChildCareWorkUse();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(WorkTimezoneShortTimeWorkSetSetMemento memento) {
		memento.setNursTimezoneWorkUse(this.nursTimezoneWorkUse);
		memento.setEmploymentTimeDeduct(this.employmentTimeDeduct);
		memento.setChildCareWorkUse(this.childCareWorkUse);
	}
}
