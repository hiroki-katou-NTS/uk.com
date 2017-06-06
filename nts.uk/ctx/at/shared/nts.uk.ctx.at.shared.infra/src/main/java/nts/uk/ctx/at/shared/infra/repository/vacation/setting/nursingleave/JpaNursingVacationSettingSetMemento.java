/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.vacation.setting.nursingleave;

import java.util.List;

import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.MaxPersonSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingCategory;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingVacationSettingSetMemento;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.nursingleave.KmfmtNursingLeaveSet;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.nursingleave.KmfmtWorkType;

/**
 * The Class JpaNursingVacationSettingSetMemento.
 */
public class JpaNursingVacationSettingSetMemento implements NursingVacationSettingSetMemento {
    
    /** The entity nursing. */
    private KmfmtNursingLeaveSet entityNursing;
    
    /** The entity work types. */
    private List<KmfmtWorkType> entityWorkTypes;
    
    /**
     * Instantiates a new jpa nursing vacation setting set memento.
     *
     * @param entityNursing the entity nursing
     * @param entityWorkTypes the entity work types
     */
    public JpaNursingVacationSettingSetMemento(KmfmtNursingLeaveSet entityNursing,
            List<KmfmtWorkType> entityWorkTypes) {
        this.entityNursing = entityNursing;
        this.entityWorkTypes = entityWorkTypes;
    }
    
    /**
     * Sets the company id.
     *
     * @param companyId the new company id
     */
    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.
     * NursingVacationSettingSetMemento#setCompanyId(java.lang.String)
     */
    @Override
    public void setCompanyId(String companyId) {
        this.entityNursing.setCid(companyId);
    }

    /**
     * Sets the manage type.
     *
     * @param manageType the new manage type
     */
    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.
     * NursingVacationSettingSetMemento#setManageType(nts.uk.ctx.at.shared.dom.
     * vacation.setting.ManageDistinct)
     */
    @Override
    public void setManageType(ManageDistinct manageType) {
        this.entityNursing.setManageType(manageType.value);
    }

    /**
     * Sets the nursing category.
     *
     * @param nursingCategory the new nursing category
     */
    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.
     * NursingVacationSettingSetMemento#setNursingCategory(nts.uk.ctx.at.shared.
     * dom.vacation.setting.nursingleave.NursingCategory)
     */
    @Override
    public void setNursingCategory(NursingCategory nursingCategory) {
        this.entityNursing.setNursingCtr(nursingCategory.value);
    }

    /**
     * Sets the start month day.
     *
     * @param startMonthDay the new start month day
     */
    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.
     * NursingVacationSettingSetMemento#setStartMonthDay(java.lang.Integer)
     */
    @Override
    public void setStartMonthDay(Integer startMonthDay) {
        this.entityNursing.setStartMonthDay(startMonthDay);
    }

    /**
     * Sets the max person setting.
     *
     * @param maxPersonSetting the new max person setting
     */
    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.
     * NursingVacationSettingSetMemento#setMaxPersonSetting(nts.uk.ctx.at.shared
     * .dom.vacation.setting.nursingleave.MaxPersonSetting)
     */
    @Override
    public void setMaxPersonSetting(MaxPersonSetting maxPersonSetting) {
        this.entityNursing.setNursingNumLeaveDay(maxPersonSetting.getNursingNumberLeaveDay());
        this.entityNursing.setNursingNumPerson(maxPersonSetting.getNursingNumberPerson());
    }

    /**
     * Sets the work type codes.
     *
     * @param workTypeCodes the new work type codes
     */
    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.
     * NursingVacationSettingSetMemento#setWorkTypeCodes(java.util.List)
     */
    @Override
    public void setWorkTypeCodes(List<String> workTypeCodes) {
        workTypeCodes.forEach(code -> {
            this.entityWorkTypes.add(new KmfmtWorkType(this.entityNursing.getCid(), code));
        });
    }
}
