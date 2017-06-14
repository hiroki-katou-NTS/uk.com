/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.vacation.setting.nursingleave;

import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.MaxPersonSettingSetMemento;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.nursingleave.KmfmtNursingLeaveSet;

/**
 * The Class JpaMaxPersonSettingSetMemento.
 */
public class JpaMaxPersonSettingSetMemento implements MaxPersonSettingSetMemento {
    
    /** The entity. */
    private KmfmtNursingLeaveSet entity;
    
    /**
     * Instantiates a new jpa max person setting set memento.
     *
     * @param entity the entity
     */
    public JpaMaxPersonSettingSetMemento(KmfmtNursingLeaveSet entity) {
        this.entity = entity;
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.
     * MaxPersonSettingSetMemento#setNursingNumberLeaveDay(java.lang.Integer)
     */
    @Override
    public void setNursingNumberLeaveDay(Integer nursingNumberLeaveDay) {
        this.entity.setNursingNumLeaveDay(nursingNumberLeaveDay);
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.
     * MaxPersonSettingSetMemento#setNursingNumberPerson(java.lang.Integer)
     */
    @Override
    public void setNursingNumberPerson(Integer nursingNumberPerson) {
        this.entity.setNursingNumPerson(nursingNumberPerson);
    }

}
