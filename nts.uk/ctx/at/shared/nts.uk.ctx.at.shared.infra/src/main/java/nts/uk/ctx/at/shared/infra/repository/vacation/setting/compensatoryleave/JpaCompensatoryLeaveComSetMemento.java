/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.vacation.setting.compensatoryleave;

import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryLeaveComSetMemento;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.NormalVacationSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.OccurrenceVacationSetting;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.compensatoryleave.KmfmtCompensLeaveCom;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.compensatoryleave.KmfmtNormalVacationSet;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.compensatoryleave.KmfmtOccurVacationSet;

/**
 * The Class JpaCompensatoryLeaveComSetMemento.
 */
public class JpaCompensatoryLeaveComSetMemento implements CompensatoryLeaveComSetMemento {
    
    /** The entity. */
    @Inject
    private KmfmtCompensLeaveCom entity;

    /**
     * Instantiates a new jpa compensatory leave com set memento.
     *
     * @param entity the entity
     */
    public JpaCompensatoryLeaveComSetMemento(KmfmtCompensLeaveCom entity) {
        this.entity = entity;
    }
    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.
     * CompensatoryLeaveComSetMemento#setCompanyId(java.lang.String)
     */
    @Override
    public void setCompanyId(String companyId) {
        this.entity.setCid(companyId);
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.
     * CompensatoryLeaveComSetMemento#setIsManaged(nts.uk.ctx.at.shared.dom.
     * vacation.setting.ManageDistinct)
     */
    @Override
    public void setIsManaged(ManageDistinct isManaged) {
        this.entity.setManageAtr(isManaged.value);
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.
     * CompensatoryLeaveComSetMemento#setNormalVacationSetting(nts.uk.ctx.at.
     * shared.dom.vacation.setting.compensatoryleave.NormalVacationSetting)
     */
    @Override
    public void setNormalVacationSetting(NormalVacationSetting setting) {
        KmfmtNormalVacationSet entityNormal = new KmfmtNormalVacationSet();
        setting.saveToMemento(new JpaNormalVacationSetMemento(entityNormal));
        entityNormal.setCid(this.entity.getCid());
        this.entity.setNormal(entityNormal);
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.
     * CompensatoryLeaveComSetMemento#setOccurrenceVacationSetting(nts.uk.ctx.at
     * .shared.dom.vacation.setting.compensatoryleave.OccurrenceVacationSetting)
     */
    @Override
    public void setOccurrenceVacationSetting(OccurrenceVacationSetting setting) {
        KmfmtOccurVacationSet entityOccur = new KmfmtOccurVacationSet();
        setting.saveToMemento(new JpaOccurrenceVacationSetMemento(entityOccur));
        entityOccur.setCid(this.entity.getCid());
        this.entity.setOccur(entityOccur);
    }

}
