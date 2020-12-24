/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.vacation.setting.annualpaidleave;

import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeAnnualRoundProcesCla;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeDigestiveUnit;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.TimeAnnualSettingSetMemento;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.TimeAnnualLeaveTimeDay;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.TimeAnnualMaxDay;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.annualpaidleave.KtvmtTimeAnnualSet;

/**
 * The Class JpaTimeVacationSettingSetMemento.
 */
public class JpaTimeAnnualSettingSetMemento implements TimeAnnualSettingSetMemento {
    
    /** The entity. */
    private KtvmtTimeAnnualSet entity;
    
    /**
     * Instantiates a new jpa time vacation setting set memento.
     *
     * @param entity the entity
     */
    public JpaTimeAnnualSettingSetMemento(KtvmtTimeAnnualSet entity) {
        this.entity = entity;
    }
    
    @Override
    public void setCompanyId(String companyId) {
        this.entity.setCid(companyId);
    }
    
    @Override
    public void setTimeManageType(ManageDistinct timeManageType) {
        this.entity.setTimeManageAtr(timeManageType.value);
    }
    @Override
    public void setTimeUnit(TimeDigestiveUnit timeUnit) {
        this.entity.setTimeUnit(timeUnit.value);
    }

    @Override
    public void setMaxYearDayLeave(TimeAnnualMaxDay maxYearDayLeave) {
        this.entity.setTimeMaxDayManageAtr(maxYearDayLeave.manageType.value);
        this.entity.setTimeMaxDayReference(maxYearDayLeave.reference.value);
        this.entity.setTimeMaxDayUnifComp(maxYearDayLeave.maxNumberUniformCompany.v());
    }

	@Override
	public void setRoundProcessClassific(TimeAnnualRoundProcesCla timeAnnualRoundProcesCla) {
		this.entity.setRoundProcessCla(timeAnnualRoundProcesCla.value);
	}

	@Override
	public void setTimeAnnualLeaveTimeDay(TimeAnnualLeaveTimeDay timeAnnualLeaveTimeDay) {
		this.entity.setTimeOfDayRef(timeAnnualLeaveTimeDay.getTimeOfDayReference().value);
		this.entity.setUnifromTime(timeAnnualLeaveTimeDay.getUniformTime().isPresent() ? timeAnnualLeaveTimeDay.getUniformTime().get().v() : null );
		this.entity.setContractTimeRound(timeAnnualLeaveTimeDay.getContractTimeRound().isPresent() ? timeAnnualLeaveTimeDay.getContractTimeRound().get().value : null );
		
	}
}
