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
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingLeaveSettingSetMemento;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.nursingleave.KnlmtNursingLeaveSet;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.nursingleave.KnlmtNursingLeaveSetPK;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.nursingleave.KnlmtNursingWorkType;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.nursingleave.KnlmtNursingWorkTypePK;

/**
 * The Class JpaNursingVacationSettingSetMemento.
 */
public class JpaNursingLeaveSettingSetMemento implements NursingLeaveSettingSetMemento {
    
    /** The entity nursing. */
    private KnlmtNursingLeaveSet entityNursing;
    
    /**
     * Instantiates a new jpa nursing leave setting set memento.
     *
     * @param entityNursing the entity nursing
     */
    public JpaNursingLeaveSettingSetMemento(KnlmtNursingLeaveSet entityNursing) {
        this.entityNursing = entityNursing;
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.
     * NursingVacationSettingSetMemento#setCompanyId(java.lang.String)
     */
    @Override
    public void setCompanyId(String companyId) {
        KnlmtNursingLeaveSetPK pk = this.entityNursing.getKnlmtNursingLeaveSetPK();
        if (pk == null) {
            pk = new KnlmtNursingLeaveSetPK();
        }
        pk.setCid(companyId);
        this.entityNursing.setKnlmtNursingLeaveSetPK(pk);
    }

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

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.
     * NursingVacationSettingSetMemento#setNursingCategory(nts.uk.ctx.at.shared.
     * dom.vacation.setting.nursingleave.NursingCategory)
     */
    @Override
    public void setNursingCategory(NursingCategory nursingCategory) {
        KnlmtNursingLeaveSetPK pk = this.entityNursing.getKnlmtNursingLeaveSetPK();
        if (pk == null) {
            pk = new KnlmtNursingLeaveSetPK();
        }
        pk.setNursingCtr(nursingCategory.value);
        this.entityNursing.setKnlmtNursingLeaveSetPK(pk);
    }

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

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.
     * NursingVacationSettingSetMemento#setWorkTypeCodes(java.util.List)
     */
    @Override
    public void setWorkTypeCodes(List<String> workTypeCodes) {
        List<KnlmtNursingWorkType> listWorkType = new ArrayList<>();
        for (int i = 0; i < workTypeCodes.size(); i++) {
            String workTypeCode = workTypeCodes.get(i);
            
            KnlmtNursingWorkTypePK pk = new KnlmtNursingWorkTypePK();
            pk.setCid(this.entityNursing.getKnlmtNursingLeaveSetPK().getCid());
            pk.setNursingCtr(this.entityNursing.getKnlmtNursingLeaveSetPK().getNursingCtr());
            pk.setOrderNumber(i);
            
            listWorkType.add(new KnlmtNursingWorkType(pk, workTypeCode));
        }
        this.entityNursing.setListWorkType(listWorkType);
    }
}
