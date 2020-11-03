/**
 * 
 */
package nts.uk.screen.at.app.kcp015;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualPaidLeaveSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualPaidLeaveSettingRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensLeaveComSetRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryLeaveComSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.RetentionYearlySetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.RetentionYearlySettingRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.sixtyhours.Com60HourVacation;
import nts.uk.ctx.at.shared.dom.vacation.setting.sixtyhours.Com60HourVacationRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.ComSubstVacation;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.ComSubstVacationRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author laitv
 *
 */
@Stateless
public class JudgmentVacationCls {
	
	@Inject 
	private AnnualPaidLeaveSettingRepository annualPaidLeaveSettingRepo;
	@Inject 
	private RetentionYearlySettingRepository retentionYearlySettingRepo;
	@Inject
	private CompensLeaveComSetRepository compensLeaveComSetRepo;
	@Inject
	private ComSubstVacationRepository comSubstVacationRepo;
	@Inject
	private Com60HourVacationRepository com60HourVacationRepo;
	
	public KCP015Dto getData(){
		
		KCP015Dto result = new KCP015Dto();
		
		// step1
		// call 年休の使用区分を取得する
		boolean isManageAnnualLeave = false;
		String companyId = AppContexts.user().companyId();
		AnnualPaidLeaveSetting annualLeaveSet = annualPaidLeaveSettingRepo.findByCompanyId(companyId);
		if (annualLeaveSet != null) 
			isManageAnnualLeave = annualLeaveSet.isManaged();
		result.clsOfAnnualHoliday = isManageAnnualLeave;
		
		// step2
		Optional<RetentionYearlySetting> retentionYearlySet = retentionYearlySettingRepo.findByCompanyId(companyId);
		if (retentionYearlySet.isPresent()) {
			result.divisionOfAnnualHoliday = retentionYearlySet.get().getManagementCategory().value == ManageDistinct.YES.value;
		} else {
			result.divisionOfAnnualHoliday = false;
		}
		
		// step3
		CompensatoryLeaveComSetting compensatoryLeaveComSet = compensLeaveComSetRepo.find( companyId);
		if(compensatoryLeaveComSet != null){
			result.subLeaveUseDivision = compensatoryLeaveComSet.isManaged();
		}else{
			result.subLeaveUseDivision = false;
		}
		
		// step4
		Optional<ComSubstVacation> comSubstVacation = comSubstVacationRepo.findById(companyId);
		if (comSubstVacation.isPresent()) {
			result.dvisionOfZhenxiuUse = comSubstVacation.get().isManaged();
		} else {
			result.dvisionOfZhenxiuUse = false;
		}
		
		// step5
		Optional<Com60HourVacation> com60HourVacation = com60HourVacationRepo.findById(companyId);
		if (com60HourVacation.isPresent()) {
			result.overtimeUseCls60H = com60HourVacation.get().isManaged();
		} else {
			result.overtimeUseCls60H = false;
		}
		
		return result;
	}

}
