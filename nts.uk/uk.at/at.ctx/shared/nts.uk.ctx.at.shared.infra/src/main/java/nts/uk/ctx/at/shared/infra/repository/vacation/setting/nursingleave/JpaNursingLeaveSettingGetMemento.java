/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.vacation.setting.nursingleave;

//import java.util.List;
import java.util.Optional;
//import java.util.stream.Collectors;

import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.MaxPersonSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingCategory;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingLeaveSettingGetMemento;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.nursingleave.KshmtHdnursingLeave;

/**
 * The Class JpaNursingVacationSettingGetMemento.
 */
public class JpaNursingLeaveSettingGetMemento implements NursingLeaveSettingGetMemento {
    
    /** The entity nursing. */
    private KshmtHdnursingLeave entityNursing;
    
    /**
     * Instantiates a new jpa nursing vacation setting get memento.
     *
     * @param entityNursing the entity nursing
     * @param entityWorkTypes the entity work types
     */
    public JpaNursingLeaveSettingGetMemento(KshmtHdnursingLeave entityNursing) {
        this.entityNursing = entityNursing;
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.
     * NursingVacationSettingGetMemento#getCompanyId()
     */
    @Override
    public String getCompanyId() {
        return this.entityNursing.getKshmtHdnursingLeavePK().getCid();
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.
     * NursingVacationSettingGetMemento#getManageType()
     */
    @Override
    public ManageDistinct getManageType() {
        return ManageDistinct.valueOf(this.entityNursing.getManageType());
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.
     * NursingVacationSettingGetMemento#getNursingCategory()
     */
    @Override
    public NursingCategory getNursingCategory() {
        return NursingCategory.valueOf(this.entityNursing.getKshmtHdnursingLeavePK().getNursingCtr());
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.
     * NursingVacationSettingGetMemento#getStartMonthDay()
     */
    @Override
    public Integer getStartMonthDay() {
        return this.entityNursing.getStartMonthDay();
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.
     * NursingVacationSettingGetMemento#getMaxPersonSetting()
     */
    @Override
    public MaxPersonSetting getMaxPersonSetting() {
        return new MaxPersonSetting(new JpaMaxPersonSettingGetMemento(this.entityNursing));
    }

	@Override
	public Optional<Integer> getSpecialHolidayFrame() {
		return Optional.of(this.entityNursing.getSpecialHolidayFrame());
	}

	@Override
	public Optional<Integer> getWorkAbsence() {
		return Optional.of(this.entityNursing.getWorkAbsence());
	}

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.
     * NursingVacationSettingGetMemento#getWorkTypeCodes()
     */
//    @Override
//    public List<String> getWorkTypeCodes() {
//        return this.entityNursing.getListWorkType().stream()
//                .filter(entity -> entity.getKnlmtNursingWorkTypePK().getNursingCtr() == this.entityNursing
//                        .getKshmtHdnursingLeavePK().getNursingCtr())
//                .map(entity -> entity.getWorkTypeCode())
//                .collect(Collectors.toList());
//    }

}
