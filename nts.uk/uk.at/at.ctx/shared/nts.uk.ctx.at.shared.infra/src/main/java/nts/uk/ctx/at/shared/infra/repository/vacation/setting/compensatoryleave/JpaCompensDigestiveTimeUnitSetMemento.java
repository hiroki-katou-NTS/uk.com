/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.vacation.setting.compensatoryleave;

import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeDigestiveUnit;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryDigestiveTimeUnitSetMemento;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.compensatoryleave.KctmtDigestTimeCom;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.compensatoryleave.KctmtDigestTimeEmp;

/**
 * The Class JpaCompensDigestiveTimeUnitSetMemento.
 */
public class JpaCompensDigestiveTimeUnitSetMemento implements CompensatoryDigestiveTimeUnitSetMemento {
    
    /** The entity com. */
    private KctmtDigestTimeCom entityCom;
    
    /** The entity emp. */
    private KctmtDigestTimeEmp entityEmp;
    
    /**
     * Instantiates a new jpa compens digestive time unit set memento.
     *
     * @param obj the obj
     */
    public JpaCompensDigestiveTimeUnitSetMemento(Object obj) {
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
     * CompensatoryDigestiveTimeUnitSetMemento#setIsManageByTime(nts.uk.ctx.at.
     * shared.dom.vacation.setting.ManageDistinct)
     */
    @Override
    public void setIsManageByTime(ManageDistinct isManageByTime) {
        if (this.entityCom != null) {
            this.entityCom.setManageAtr(isManageByTime.value);
        } else {
            this.entityEmp.setManageAtr(isManageByTime.value);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.
     * CompensatoryDigestiveTimeUnitSetMemento#setDigestiveUnit(nts.uk.ctx.at.
     * shared.dom.vacation.setting.TimeVacationDigestiveUnit)
     */
    @Override
    public void setDigestiveUnit(TimeDigestiveUnit digestiveUnit) {
        if (this.entityCom != null) {
            this.entityCom.setDigestiveUnit(digestiveUnit.value);
        } else {
            this.entityEmp.setDigestiveUnit(digestiveUnit.value);
        }
    }

}
