/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.vacation.setting.compensatoryleave;

import java.util.ArrayList;
import java.util.List;

import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryAcquisitionUse;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryDigestiveTimeUnit;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryLeaveComGetMemento;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryOccurrenceSetting;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.compensatoryleave.KclmtCompensLeaveCom;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.compensatoryleave.KocmtOccurrenceSet;

/**
 * The Class JpaCompensLeaveComGetMemento.
 */
public class JpaCompensLeaveComGetMemento implements CompensatoryLeaveComGetMemento {
    
    /** The entity. */
    private KclmtCompensLeaveCom entity;
    
    /**
     * Instantiates a new jpa compens leave com get memento.
     *
     * @param entity the entity
     */
    public JpaCompensLeaveComGetMemento(KclmtCompensLeaveCom entity) {
        this.entity = entity;
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.
     * CompensatoryLeaveComGetMemento#getCompanyId()
     */
    @Override
    public String getCompanyId() {
        return this.entity.getCid();
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.
     * CompensatoryLeaveComGetMemento#getIsManaged()
     */
    @Override
    public ManageDistinct getIsManaged() {
        return ManageDistinct.valueOf(this.entity.getManageAtr());
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.
     * CompensatoryLeaveComGetMemento#getCompensatoryAcquisitionUse()
     */
    @Override
    public CompensatoryAcquisitionUse getCompensatoryAcquisitionUse() {
    	JpaCompensAcquisitionUseGetMemento memento = new JpaCompensAcquisitionUseGetMemento(this.entity.getKclmtAcquisitionCom());
    	CompensatoryAcquisitionUse data = new CompensatoryAcquisitionUse(memento);
        return data;
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.
     * CompensatoryLeaveComGetMemento#getCompensatoryDigestiveTimeUnit()
     */
    @Override
    public CompensatoryDigestiveTimeUnit getCompensatoryDigestiveTimeUnit() {
        return new CompensatoryDigestiveTimeUnit(
                new JpaCompensDigestiveTimeUnitGetMemento(this.entity.getKctmtDigestTimeCom()));
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.
     * CompensatoryLeaveComGetMemento#getCompensatoryOccurrenceSetting()
     */
    @Override
    public List<CompensatoryOccurrenceSetting> getCompensatoryOccurrenceSetting() {
        List<CompensatoryOccurrenceSetting> listSetting = new ArrayList<>();
        for (KocmtOccurrenceSet entity : this.entity.getListOccurrence()) {
            listSetting.add(new CompensatoryOccurrenceSetting(new JpaCompensOccurrenceSettingGetMemento(entity)));
        }
        return listSetting;
    }

}
