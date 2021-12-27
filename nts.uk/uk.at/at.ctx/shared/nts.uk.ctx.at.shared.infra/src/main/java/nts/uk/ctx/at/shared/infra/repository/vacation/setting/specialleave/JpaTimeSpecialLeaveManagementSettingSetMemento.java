package nts.uk.ctx.at.shared.infra.repository.vacation.setting.specialleave;

import nts.uk.ctx.at.shared.dom.vacation.setting.TimeVacationDigestUnit;
import nts.uk.ctx.at.shared.dom.vacation.setting.specialleave.TimeSpecialLeaveManagementSettingSetMemento;
import nts.uk.ctx.at.shared.infra.entity.workrule.vacation.specialvacation.timespecialvacation.KshmtHdspTimeMgt;

public class JpaTimeSpecialLeaveManagementSettingSetMemento implements TimeSpecialLeaveManagementSettingSetMemento {
	
	 private KshmtHdspTimeMgt entity;
	 
	 public JpaTimeSpecialLeaveManagementSettingSetMemento(KshmtHdspTimeMgt entity) {
	        this.entity = entity;
	    }

	@Override
	public void setCompanyId(String companyId) {
		this.entity.setCompanyId(companyId);
		
	}

	@Override
	public void setTimeVacationDigestUnit(TimeVacationDigestUnit timeSetting) {
		this.entity.setManagementAtr(timeSetting.getManage().value);
		this.entity.setUseUnit(timeSetting.getDigestUnit().value);
		
	}

}
