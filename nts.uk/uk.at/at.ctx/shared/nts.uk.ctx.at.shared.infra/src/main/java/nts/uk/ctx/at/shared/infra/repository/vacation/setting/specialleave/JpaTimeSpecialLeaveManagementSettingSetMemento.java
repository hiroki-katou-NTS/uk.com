package nts.uk.ctx.at.shared.infra.repository.vacation.setting.specialleave;

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
	public void setManagementAtr(Integer managementAtr) {
		this.entity.setManagementAtr(managementAtr);
		
	}

	@Override
	public void setUseUnit(Integer useUnit) {
		this.entity.setUseUnit(useUnit);
		
	}

}
