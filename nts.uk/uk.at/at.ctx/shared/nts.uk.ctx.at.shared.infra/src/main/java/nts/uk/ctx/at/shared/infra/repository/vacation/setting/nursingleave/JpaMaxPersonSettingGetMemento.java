/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.vacation.setting.nursingleave;

import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.ChildCareNurseUpperLimit;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.MaxPersonSettingGetMemento;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NumberOfCaregivers;
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
    public ChildCareNurseUpperLimit getNursingNumberLeaveDay() {
        return new ChildCareNurseUpperLimit(this.entity.getNursingNumLeaveDay());
    }

    /*
     * (non-Javadoc)
     *
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.
     * MaxPersonSettingGetMemento#getNursingNumberLeaveDay2()
     */
    @Override
    public NumberOfCaregivers getNursingNumberPerson() {
        return new NumberOfCaregivers(this.entity.getNursingNumPerson());
    }
    
	@Override
	public ChildCareNurseUpperLimit getNursingNumberLeaveDay2() {
		return new ChildCareNurseUpperLimit(this.entity.getNursingNumLeaveDay2());
	}


	@Override
	public NumberOfCaregivers getNursingNumberPerson2() {
		return new NumberOfCaregivers(this.entity.getNursingNumPerson2());
	}
}
