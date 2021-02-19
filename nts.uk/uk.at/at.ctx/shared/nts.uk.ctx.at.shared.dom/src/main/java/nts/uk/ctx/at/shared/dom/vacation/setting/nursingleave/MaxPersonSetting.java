/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * The Class 介護看護休暇上限人数設定.
 */
@Getter
public class MaxPersonSetting extends DomainObject {
    
    /** The nursing number leave day.
     * 介護看護休暇日数: 看護休暇日数
     * */
    private NumberDayNursing nursingNumberLeaveDay;
    
    /** The nursing number person.
     * 要介護看護人数: 看護休暇日数
     * */
    private NumberDayNursing nursingNumberLeaveDay2;
    
    /**
     * Instantiates a new max person setting.
     *
     * @param memento the memento
     */
    public MaxPersonSetting(MaxPersonSettingGetMemento memento) {
        this.nursingNumberLeaveDay = memento.getNursingNumberLeaveDay();
        this.nursingNumberLeaveDay2 = memento.getNursingNumberLeaveDay2();
    }
    
    /**
     * Save to memento.
     *
     * @param memento the memento
     */
    public void saveToMemento(MaxPersonSettingSetMemento memento) {
        memento.setNursingNumberLeaveDay(this.nursingNumberLeaveDay);
        memento.setNursingNumberLeaveDay2(this.nursingNumberLeaveDay2);
    }
}
