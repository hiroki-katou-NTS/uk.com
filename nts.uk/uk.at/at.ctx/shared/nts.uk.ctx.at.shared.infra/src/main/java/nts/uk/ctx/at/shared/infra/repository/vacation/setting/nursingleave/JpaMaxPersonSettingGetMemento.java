/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.vacation.setting.nursingleave;

import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.MaxPersonSettingGetMemento;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingNumberLeaveDay;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingNumberPerson;
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
    public NursingNumberLeaveDay getNursingNumberLeaveDay() {
        return new NursingNumberLeaveDay(this.entity.getNursingNumLeaveDay());
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.
     * MaxPersonSettingGetMemento#getNursingNumberLeaveDay2()
     */
    @Override
	public NursingNumberLeaveDay getNursingNumberLeaveDay2() {
		return new NursingNumberLeaveDay(this.entity.getNursingNumLeaveDay2());
	}

	@Override
	public NursingNumberPerson getNursingNumberPerson() {
		return new NursingNumberPerson(this.entity.getNursingNumPerson());
	}	

	@Override
	public NursingNumberPerson getNursingNumberPerson2() {
		return new NursingNumberPerson(this.entity.getNursingNumPerson2());
	}
}
