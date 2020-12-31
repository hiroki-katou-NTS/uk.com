/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.vacation.setting.nursingleave;

import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.MaxPersonSettingGetMemento;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NumberDayNursing;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.nursingleave.KnlmtNursingLeaveSet;

/**
 * The Class JpaMaxPersonSettingGetMemento.
 */
public class JpaMaxPersonSettingGetMemento implements MaxPersonSettingGetMemento {
    
    /** The entity. */
    private KnlmtNursingLeaveSet entity;
    
    /**
     * Instantiates a new jpa max person setting get memento.
     *
     * @param entity the entity
     */
    public JpaMaxPersonSettingGetMemento(KnlmtNursingLeaveSet entity) {
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
     * MaxPersonSettingGetMemento#getNursingNumberLeaveDay2()
     */
    @Override
    public NumberDayNursing getNursingNumberLeaveDay2() {
        return new NumberDayNursing(this.entity.getNursingNumLeaveDay2());
    }

}
