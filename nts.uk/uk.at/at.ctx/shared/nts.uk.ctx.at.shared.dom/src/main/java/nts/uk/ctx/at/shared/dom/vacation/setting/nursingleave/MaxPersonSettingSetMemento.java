/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave;

/**
 * The Interface MaxPersonSettingSetMemento.
 */
public interface MaxPersonSettingSetMemento {

    /**
     * Sets the nursing number leave day.
     *
     * @param nursingNumberLeaveDay the new nursing number leave day
     */
    void setNursingNumberLeaveDay(ChildCareNurseUpperLimit nursingNumberLeaveDay);

    /**
     * Sets the nursing number person.
     *
     * @param nursingNumberPerson the new nursing number person
     */
    void setNursingNumberPerson(NumberOfCaregivers nursingNumberPerson);
}
