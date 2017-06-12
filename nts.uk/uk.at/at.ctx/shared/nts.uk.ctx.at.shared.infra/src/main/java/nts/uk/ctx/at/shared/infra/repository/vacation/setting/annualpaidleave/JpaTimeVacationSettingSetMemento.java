/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.vacation.setting.annualpaidleave;

import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeVacationDigestiveUnit;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.TimeVacationSettingSetMemento;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.YearVacationTimeMaxDay;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.annualpaidleave.KtvmtTimeVacationSet;

/**
 * The Class JpaTimeVacationSettingSetMemento.
 */
public class JpaTimeVacationSettingSetMemento implements TimeVacationSettingSetMemento {
    
    /** The entity. */
    private KtvmtTimeVacationSet entity;
    
    /**
     * Instantiates a new jpa time vacation setting set memento.
     *
     * @param entity the entity
     */
    public JpaTimeVacationSettingSetMemento(KtvmtTimeVacationSet entity) {
        this.entity = entity;
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.
     * TimeVacationSettingSetMemento#setCompanyId(java.lang.String)
     */
    @Override
    public void setCompanyId(String companyId) {
        this.entity.setCid(companyId);
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.
     * TimeVacationSettingSetMemento#setTimeManageType(nts.uk.ctx.at.shared.dom.
     * vacation.setting.ManageDistinct)
     */
    @Override
    public void setTimeManageType(ManageDistinct timeManageType) {
        this.entity.setTimeManageAtr(timeManageType.value);
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.
     * TimeVacationSettingSetMemento#setTimeUnit(nts.uk.ctx.at.shared.dom.
     * vacation.setting.TimeVacationDigestiveUnit)
     */
    @Override
    public void setTimeUnit(TimeVacationDigestiveUnit timeUnit) {
        this.entity.setTimeUnit(timeUnit.value);
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.
     * TimeVacationSettingSetMemento#setMaxYearDayLeave(nts.uk.ctx.at.shared.dom
     * .vacation.setting.annualpaidleave.YearVacationTimeMaxDay)
     */
    @Override
    public void setMaxYearDayLeave(YearVacationTimeMaxDay maxYearDayLeave) {
        this.entity.setTimeMaxDayManageAtr(maxYearDayLeave.manageType.value);
        this.entity.setTimeMaxDayReference(maxYearDayLeave.reference.value);
        this.entity.setTimeMaxDayUnifComp(maxYearDayLeave.maxNumberUniformCompany.v());
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.
     * TimeVacationSettingSetMemento#setEnoughTimeOneDay(boolean)
     */
    @Override
    public void setEnoughTimeOneDay(boolean isEnoughTimeOneDay) {
        this.entity.setIsEnoughTimeOneDay(isEnoughTimeOneDay == true ? 1 : 0);
    }

}
