/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.vacation.setting.nursingleave;

import java.util.ArrayList;
import java.util.List;

import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.MaxPersonSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingCategory;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingVacationSettingSetMemento;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.nursingleave.KmfmtNursingLeaveSet;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.nursingleave.KmfmtNursingLeaveSetPK;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.nursingleave.KmfmtWorkType;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.nursingleave.KmfmtWorkTypePK;

/**
 * The Class JpaNursingVacationSettingSetMemento.
 */
public class JpaNursingVacationSettingSetMemento implements NursingVacationSettingSetMemento {
    
    /** The entity nursing. */
    private KmfmtNursingLeaveSet entityNursing;
    
    /**
     * Instantiates a new jpa nursing vacation setting set memento.
     *
     * @param entityNursing the entity nursing
     * @param entityWorkTypes the entity work types
     */
    public JpaNursingVacationSettingSetMemento(KmfmtNursingLeaveSet entityNursing) {
        this.entityNursing = entityNursing;
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
        KmfmtNursingLeaveSetPK pk = new KmfmtNursingLeaveSetPK();
        pk.setCid(companyId);
        this.entityNursing.setKmfmtNursingLeaveSetPK(pk);
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
        KmfmtNursingLeaveSetPK pk = this.entityNursing.getKmfmtNursingLeaveSetPK();
        pk.setNursingCtr(nursingCategory.value);
        this.entityNursing.setKmfmtNursingLeaveSetPK(pk);
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
        JpaMaxPersonSettingSetMemento memento = new JpaMaxPersonSettingSetMemento(this.entityNursing);
        maxPersonSetting.saveToMemento(memento);
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
        List<KmfmtWorkType> listWorkType = new ArrayList<>();
        for (int i = 0; i < workTypeCodes.size(); i++) {
            String workTypeCode = workTypeCodes.get(i);
            
            KmfmtWorkTypePK pk = new KmfmtWorkTypePK();
            pk.setCid(this.entityNursing.getKmfmtNursingLeaveSetPK().getCid());
            pk.setNursingCtr(this.entityNursing.getKmfmtNursingLeaveSetPK().getNursingCtr());
            pk.setOrderNumber(i);
            
            listWorkType.add(new KmfmtWorkType(pk, workTypeCode));
        }
        this.entityNursing.setListWorkType(listWorkType);
    }
}
