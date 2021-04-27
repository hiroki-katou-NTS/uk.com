/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave;

import java.util.ArrayList;
import java.util.List;

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
    private NursingNumberLeaveDay nursingNumberLeaveDay;
    
    /** The nursing number person.
     * 要介護看護人数: 看護休暇日数
     * */
    private NursingNumberPerson nursingNumberPerson;
    
    /**
     * Instantiates a new max person setting.
     *
     * @param memento the memento
     */
    public MaxPersonSetting(NursingNumberLeaveDay nursingNumberLeaveDay, NursingNumberPerson nursingNumberPerson) {
        this.nursingNumberLeaveDay = nursingNumberLeaveDay;
        this.nursingNumberPerson = nursingNumberPerson;
    }
    
    public static List<MaxPersonSetting> getList(MaxPersonSettingGetMemento memento){
    	List<MaxPersonSetting> maxPersonSettings = new ArrayList<MaxPersonSetting>();
    	maxPersonSettings.add(new MaxPersonSetting(memento.getNursingNumberLeaveDay(), memento.getNursingNumberPerson()));
    	maxPersonSettings.add(new MaxPersonSetting(memento.getNursingNumberLeaveDay2(), memento.getNursingNumberPerson2()));
    	return maxPersonSettings;
    }
    
    /**
     * Save to memento.
     *
     * @param memento the memento
     */
    public void saveToMemento(MaxPersonSettingSetMemento memento) {
        memento.setNursingNumberLeaveDay(this.nursingNumberLeaveDay);
        memento.setNursingNumberPerson(this.nursingNumberPerson);
    }
}
