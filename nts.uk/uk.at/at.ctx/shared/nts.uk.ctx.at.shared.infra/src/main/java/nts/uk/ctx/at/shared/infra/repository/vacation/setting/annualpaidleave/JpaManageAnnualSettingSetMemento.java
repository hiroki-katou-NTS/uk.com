/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.vacation.setting.annualpaidleave;

import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualLeaveGrantDay;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.DisplaySetting;
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
    
    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.
     * ManageAnnualSettingSetMemento#setCompanyId(java.lang.String)
     */
    @Override
    public void setCompanyId(String companyId) {
        this.entity.setCid(companyId);
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.
     * ManageAnnualSettingSetMemento#setMaxGrantDay(nts.uk.ctx.at.shared.dom.
     * vacation.setting.annualpaidleave.AnnualLeaveGrantDay)
     */
    @Override
    public void setMaxGrantDay(AnnualLeaveGrantDay maxGrantDay) {
        if (maxGrantDay != null) {
            this.entity.setHalfMaxGrantDay(maxGrantDay.v());
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.
     * ManageAnnualSettingSetMemento#setHalfDayManage(nts.uk.ctx.at.shared.dom.
     * vacation.setting.annualpaidleave.HalfDayManage)
     */
    @Override
    public void setHalfDayManage(HalfDayManage halfDayManage) {
        this.entity.setHalfMaxDayYear(halfDayManage.maxDayOfYear);
        this.entity.setHalfManageAtr(halfDayManage.manageType.value);
        this.entity.setHalfMaxReference(halfDayManage.reference.value);
        this.entity.setHalfMaxUniformComp(halfDayManage.maxNumberUniformCompany.v());
        this.entity.setRoundProcessCla(halfDayManage.roundProcesCla.value);
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.
     * ManageAnnualSettingSetMemento#setWorkDayCalculate(boolean)
     */
    @Override
    public void setWorkDayCalculate(boolean isWorkDayCalculate) {
        this.entity.setIsWorkDayCal(isWorkDayCalculate == true ? 1 : 0);
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.
     * ManageAnnualSettingSetMemento#setRemainingNumberSetting(nts.uk.ctx.at.
     * shared.dom.vacation.setting.annualpaidleave.RemainingNumberSetting)
     */
    @Override
    public void setRemainingNumberSetting(RemainingNumberSetting remainingNumberSetting) {
        this.entity.setRetentionYear(remainingNumberSetting.retentionYear.v());
        if (remainingNumberSetting.remainingDayMaxNumber != null) {
            this.entity.setRemainingMaxDay(remainingNumberSetting.remainingDayMaxNumber.v());
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.
     * ManageAnnualSettingSetMemento#setDisplaySetting(nts.uk.ctx.at.shared.dom.
     * vacation.setting.annualpaidleave.DisplaySetting)
     */
    @Override
    public void setDisplaySetting(DisplaySetting displaySetting) {
        this.entity.setNextGrantDayDispAtr(displaySetting.nextGrantDayDisplay.value);
        this.entity.setRemainingNumDispAtr(displaySetting.remainingNumberDisplay.value);
    }

    /*
     * (non-Javadoc)
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.
     * ManageAnnualSettingSetMemento#setYearLyOfDays
     * (nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.YearLyOfNumberDays)
     */
	@Override
	public void setYearLyOfDays(YearLyOfNumberDays yearLyOfNumberDays) {
		 if (yearLyOfNumberDays != null) {
			 this.entity.setYearlyOfDays(yearLyOfNumberDays.v());
		 }		
	}
}
