/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.vacation.setting.annualpaidleave;

import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.HalfDayManage;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.ManageAnnualSettingSetMemento;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.RemainingNumberSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.YearLyOfNumberDays;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.annualpaidleave.KshmtHdpaidSetMng;

/**
 * The Class JpaManageAnnualSettingSetMemento.
 */
public class JpaManageAnnualSettingSetMemento implements ManageAnnualSettingSetMemento {
    
    /** The entity. */
    private KshmtHdpaidSetMng entity;
    
    /**
     * Instantiates a new jpa manage annual setting set memento.
     *
     * @param entity the entity
     */
    public JpaManageAnnualSettingSetMemento(KshmtHdpaidSetMng entity) {
        this.entity = entity;
    }
    
    @Override
    public void setCompanyId(String companyId) {
        this.entity.setCid(companyId);
    }
    
    @Override
    public void setHalfDayManage(HalfDayManage halfDayManage) {
        this.entity.setHalfManageAtr(halfDayManage.manageType.value);
        this.entity.setHalfMaxReference(halfDayManage.reference.value);
        this.entity.setHalfMaxUniformComp(halfDayManage.maxNumberUniformCompany.v());
        this.entity.setRoundProcessCla(halfDayManage.roundProcesCla.value);
    }

    @Override
    public void setWorkDayCalculate(boolean isWorkDayCalculate) {
        this.entity.setIsWorkDayCal(isWorkDayCalculate == true ? 1 : 0);
    }

    @Override
    public void setRemainingNumberSetting(RemainingNumberSetting remainingNumberSetting) {
        this.entity.setRetentionYear(remainingNumberSetting.retentionYear.v());
    }

	@Override
	public void setYearLyOfDays(YearLyOfNumberDays yearLyOfNumberDays) {
		 if (yearLyOfNumberDays != null) {
			 this.entity.setYearlyOfDays(yearLyOfNumberDays.v());
		 }		
	}
}
