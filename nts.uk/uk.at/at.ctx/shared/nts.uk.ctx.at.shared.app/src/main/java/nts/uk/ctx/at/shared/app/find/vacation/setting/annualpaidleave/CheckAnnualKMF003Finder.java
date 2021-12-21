package nts.uk.ctx.at.shared.app.find.vacation.setting.annualpaidleave;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.app.find.vacation.setting.annualpaidleave.dto.CheckAnnualKMF003Dto;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualPaidLeaveSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualPaidLeaveSettingRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class CheckAnnualKMF003Finder {
	
	@Inject
	private AnnualPaidLeaveSettingRepository annualRep;
	
	public CheckAnnualKMF003Dto findByCom(){
		String companyId = AppContexts.user().companyId();
		AnnualPaidLeaveSetting paidLeaveSetting = this.annualRep.findByCompanyId(companyId);
        if (paidLeaveSetting == null) {
            return null;
        }
        return this.toDto(paidLeaveSetting);
	} 
	
	private CheckAnnualKMF003Dto toDto(AnnualPaidLeaveSetting setting){
		CheckAnnualKMF003Dto dto = new CheckAnnualKMF003Dto();
		// check time
		dto.setManageType(setting.getManageAnnualSetting().getHalfDayManage().manageType.value);
		// check year
		dto.setMaxManageType(setting.getTimeSetting().getMaxYearDayLeave().manageType.value);
		// check year
		dto.setMaxReference(setting.getTimeSetting().getMaxYearDayLeave().reference.value);
		// check time
		dto.setReference(setting.getManageAnnualSetting().getHalfDayManage().reference.value);
		// check year
		dto.setTimeManageType(setting.getTimeSetting().getTimeVacationDigestUnit().getManage().value);
		return dto;
	}
}
