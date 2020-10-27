/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.vacation.setting.nursingleave;

import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.MaxPersonSettingGetMemento;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NumberDayNursing;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.nursingleave.KshmtHdnursingLeave;

/**
 * The Class JpaMaxPersonSettingGetMemento.
 */
public class JpaMaxPersonSettingGetMemento implements MaxPersonSettingGetMemento {
    
    /** The entity. */
    private KshmtHdnursingLeave entity;
    
    /**
     * Instantiates a new jpa max person setting get memento.
     *
     * @param entity the entity
     */
    public JpaMaxPersonSettingGetMemento(KshmtHdnursingLeave entity) {
        this.entity = entity;
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.
     * MaxPersonSettingGetMemento#getNursingNumberLeaveDay()
     */
    @Override
    public NumberDayNursing getNursingNumberLeaveDay() {
        return new NumberDayNursing(this.entity.getNursingNumLeaveDay());
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.
     * MaxPersonSettingGetMemento#getNursingNumberPerson()
     */
    @Override
    public NumberDayNursing getNursingNumberPerson() {
        return new NumberDayNursing(this.entity.getNursingNumPerson());
    }

}
