package nts.uk.ctx.at.shared.app.find.scherec.monthlyattdcal.aggr.vtotalmethod;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.workrule.vacation.specialvacation.timespecialvacation.TimeSpecialLeaveManagementSetting;
import nts.uk.ctx.at.shared.dom.workrule.vacation.specialvacation.timespecialvacation.TimeSpecialLeaveMngSetRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@TransactionAttribute()
public class TimeSpecialLeaveManagementSettingFinder {
	
	   @Inject
	    private TimeSpecialLeaveMngSetRepository timeSpecialLeaveMngSetRepository;
	   
		public TimeSpecialLeaveManagementSettingDto findByCid() {
			String companyId = AppContexts.user().companyId();
			Optional<TimeSpecialLeaveManagementSetting> domain = this.timeSpecialLeaveMngSetRepository.findByCompany(companyId);
			 return domain.map(this::converetToDto).orElse(null);
		}
		
	    private TimeSpecialLeaveManagementSettingDto converetToDto(TimeSpecialLeaveManagementSetting setting) {
	    	TimeSpecialLeaveManagementSettingDto dto = new TimeSpecialLeaveManagementSettingDto(null, null);
	        dto.setTimeManageType(setting.getTimeVacationDigestUnit().getManage().value);
	        dto.setTimeUnit(setting.getTimeVacationDigestUnit().getDigestUnit().value);
	        return dto;
	    }
}
