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
    private ChildCareNurseUpperLimit nursingNumberLeaveDay;
    
    /** The nursing number person.
     * 要介護看護人数: 看護休暇人数
     * */
    private NumberOfCaregivers nursingNumberPerson;
    
    /**
     * Instantiates a new max person setting.
     *
     * @param memento the memento
     */
    public MaxPersonSetting(ChildCareNurseUpperLimit nursingNumberLeaveDay, NumberOfCaregivers nursingNumberPerson) {
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

    public MaxPersonSetting() {
        this.nursingNumberLeaveDay = new ChildCareNurseUpperLimit(0);
        this.nursingNumberPerson = new NumberOfCaregivers(0);
    }

    public static MaxPersonSetting of(ChildCareNurseUpperLimit nursingNumberLeaveDay, NumberOfCaregivers nursingNumberPerson) {
    	MaxPersonSetting d = new MaxPersonSetting();
    	d.nursingNumberLeaveDay= nursingNumberLeaveDay;
    	d.nursingNumberPerson = nursingNumberPerson;
		return d;
    }

//	/**
//	 * ファクトリー
//	 * @param nursingNumberLeaveDay 看護休暇日数
//	 * @param nursingNumberPerson 看護休暇人数
//	 * @return 介護看護休暇上限人数設定
//	 */
//	public static MaxPersonSetting of(
//			ChildCareNurseUpperLimit nursingNumberLeaveDay,
//			NumberOfCaregivers nursingNumberPerson){
//
//		MaxPersonSetting domain = new MaxPersonSetting();
//		domain.nursingNumberLeaveDay = nursingNumberLeaveDay;
//		domain.nursingNumberPerson = nursingNumberPerson;
//		return domain;
//	}
//
//    /**
//     * Instantiates a new max person setting.
//     *
//     * @param memento the memento
//     */
//    public MaxPersonSetting(MaxPersonSettingGetMemento memento) {
//        this.nursingNumberLeaveDay = memento.getNursingNumberLeaveDay();
//        this.nursingNumberPerson = memento.getNursingNumberPerson();
//    }
//
//    /**
//     * Save to memento.
//     *
//     * @param memento the memento
//     */
//    public void saveToMemento(MaxPersonSettingSetMemento memento) {
//        memento.setNursingNumberLeaveDay(this.nursingNumberLeaveDay);
//        memento.setNursingNumberPerson(this.nursingNumberPerson);
//    }
}
