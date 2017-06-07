/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.vacation.setting.nursingleave;

import java.util.List;
import java.util.stream.Collectors;

import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.MaxPersonSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingCategory;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingVacationSettingGetMemento;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.nursingleave.KmfmtNursingLeaveSet;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.nursingleave.KmfmtWorkType;

/**
 * The Class JpaNursingVacationSettingGetMemento.
 */
public class JpaNursingVacationSettingGetMemento implements NursingVacationSettingGetMemento {
    
    /** The entity nursing. */
    private KmfmtNursingLeaveSet entityNursing;
    
    /** The entity work types. */
    private List<KmfmtWorkType> entityWorkTypes;
    
    /**
     * Instantiates a new jpa nursing vacation setting get memento.
     *
     * @param entityNursing the entity nursing
     * @param entityWorkTypes the entity work types
     */
    public JpaNursingVacationSettingGetMemento(KmfmtNursingLeaveSet entityNursing,
            List<KmfmtWorkType> entityWorkTypes) {
        this.entityNursing = entityNursing;
        this.entityWorkTypes = entityWorkTypes;
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.
     * NursingVacationSettingGetMemento#getCompanyId()
     */
    @Override
    public String getCompanyId() {
        return this.entityNursing.getCid();
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
        return NursingCategory.valueOf(this.entityNursing.getNursingCtr());
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

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.
     * NursingVacationSettingGetMemento#getWorkTypeCodes()
     */
    @Override
    public List<String> getWorkTypeCodes() {
        return this.entityWorkTypes.stream()
                .map(entity -> entity.getWorkTypeCode())
                .collect(Collectors.toList());
    }

}
