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
 * ScreenQuery 休暇の利用区分の判定
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
		
		// step1   button A3_3 KALMT_ANNUAL_PAID_LEAVE
		// call 年休の使用区分を取得する
		String companyId = AppContexts.user().companyId();
		AnnualPaidLeaveSetting annualLeaveSet = annualPaidLeaveSettingRepo.findByCompanyId(companyId);
		if (annualLeaveSet != null){
			result.clsOfAnnualHoliday = annualLeaveSet.isManaged();
		}else{
			result.clsOfAnnualHoliday = null;
		} 
			
		// step2  button A3_4 KMFMT_RETENTION_YEARLY
		Optional<RetentionYearlySetting> retentionYearlySet = retentionYearlySettingRepo.findByCompanyId(companyId);
		if (retentionYearlySet.isPresent()) {
			result.divisionOfAnnualHoliday = retentionYearlySet.get().getManagementCategory().value == ManageDistinct.YES.value;
		} else {
			result.divisionOfAnnualHoliday = null;
		}
		
		// step3  button A3_1 KCLMT_COMPENS_LEAVE_COM
		CompensatoryLeaveComSetting compensatoryLeaveComSet = compensLeaveComSetRepo.find( companyId);
		if(compensatoryLeaveComSet != null){
			result.subLeaveUseDivision = compensatoryLeaveComSet.isManaged();
		}else{
			result.subLeaveUseDivision = null;
		}
		
		// step4  button A3_2 KSVST_COM_SUBST_VACATION
		Optional<ComSubstVacation> comSubstVacation = comSubstVacationRepo.findById(companyId);
		if (comSubstVacation.isPresent()) {
			result.dvisionOfZhenxiuUse = comSubstVacation.get().isManaged();
		} else {
			result.dvisionOfZhenxiuUse = null;
		}
		
		// step5   button A3_5 KSHST_COM_60H_VACATION
		Optional<Com60HourVacation> com60HourVacation = com60HourVacationRepo.findById(companyId);
		if (com60HourVacation.isPresent()) {
			result.overtimeUseCls60H = com60HourVacation.get().isManaged();
		} else {
			result.overtimeUseCls60H = null;
		}
		
		return result;
	}

}
