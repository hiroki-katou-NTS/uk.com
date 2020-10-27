/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.vacation.setting.nursingleave;

import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.MaxPersonSettingSetMemento;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NumberDayNursing;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.nursingleave.KshmtHdnursingLeave;

/**
 * The Class JpaMaxPersonSettingSetMemento.
 */
public class JpaMaxPersonSettingSetMemento implements MaxPersonSettingSetMemento {
    
    /** The entity. */
    private KshmtHdnursingLeave entity;
    
    /**
     * Instantiates a new jpa max person setting set memento.
     *
     * @param entity the entity
     */
    public JpaMaxPersonSettingSetMemento(KshmtHdnursingLeave entity) {
        this.entity = entity;
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.
     * MaxPersonSettingSetMemento#setNursingNumberLeaveDay(java.lang.Integer)
     */
    @Override
    public void setNursingNumberLeaveDay(NumberDayNursing nursingNumberLeaveDay) {
        if (nursingNumberLeaveDay != null) {
            this.entity.setNursingNumLeaveDay(nursingNumberLeaveDay.v());
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.
     * MaxPersonSettingSetMemento#setNursingNumberPerson(java.lang.Integer)
     */
    @Override
    public void setNursingNumberPerson(NumberDayNursing nursingNumberPerson) {
        if(nursingNumberPerson != null) {
            this.entity.setNursingNumPerson(nursingNumberPerson.v());
        }
    }

}
