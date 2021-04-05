/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave;

/**
 * The Interface MaxPersonSettingGetMemento.
 */
public interface MaxPersonSettingGetMemento {

    /**
     * Gets the nursing number leave day.
     *
     * @return the nursing number leave day
     */
	ChildCareNurseUpperLimit getNursingNumberLeaveDay();

    /**
     * Gets the nursing number person.
     *
     * @return the nursing number person
     */
	NumberOfCaregivers getNursingNumberPerson();
}

