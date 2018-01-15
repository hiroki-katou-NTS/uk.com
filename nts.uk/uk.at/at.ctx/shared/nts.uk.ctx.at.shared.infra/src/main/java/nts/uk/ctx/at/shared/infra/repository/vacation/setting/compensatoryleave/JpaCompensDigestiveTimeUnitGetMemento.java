/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.vacation.setting.compensatoryleave;

import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeDigestiveUnit;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryDigestiveTimeUnitGetMemento;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.compensatoryleave.KctmtDigestTimeCom;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.compensatoryleave.KctmtDigestTimeEmp;

/**
 * The Class JpaCompensDigestiveTimeUnitGetMemento.
 */
public class JpaCompensDigestiveTimeUnitGetMemento implements CompensatoryDigestiveTimeUnitGetMemento {
    
    /** The entity com. */
    private KctmtDigestTimeCom entityCom;
    
    /** The entity emp. */
    private KctmtDigestTimeEmp entityEmp;
    
    /**
     * Instantiates a new jpa compens digestive time unit get memento.
     *
     * @param obj the obj
     */
    public JpaCompensDigestiveTimeUnitGetMemento(Object obj) {
        this.initObject(obj);
    }
    
    /**
     * Inits the object.
     *
     * @param obj the obj
     */
    private void initObject(Object obj) {
        boolean isCorrect = false;
        if (obj instanceof KctmtDigestTimeCom) {
            this.entityCom = (KctmtDigestTimeCom) obj;
            isCorrect = true;
        }
        else if (obj instanceof KctmtDigestTimeEmp) {
            this.entityEmp = (KctmtDigestTimeEmp) obj;
            isCorrect = true;
        }
        if (!isCorrect) {
            throw new RuntimeException("Object incorrect.");
        }
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.
     * CompensatoryDigestiveTimeUnitGetMemento#getIsManageByTime()
     */
    @Override
    public ManageDistinct getIsManageByTime() {
        return ManageDistinct.valueOf(
                this.entityCom == null ? this.entityEmp.getManageAtr() : this.entityCom.getManageAtr());
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.
     * CompensatoryDigestiveTimeUnitGetMemento#getDigestiveUnit()
     */
    @Override
    public TimeDigestiveUnit getDigestiveUnit() {
        return TimeDigestiveUnit.valueOf(
                this.entityCom == null ? this.entityEmp.getDigestiveUnit() : this.entityCom.getDigestiveUnit());
    }

}
