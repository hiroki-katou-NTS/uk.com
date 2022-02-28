package nts.uk.ctx.at.shared.dom.service;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualPaidLeaveSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualPaidLeaveSettingRepository;
import nts.uk.shr.com.context.AppContexts;
/**
 * 
 * @author yennth
 *
 */
@Stateless
public class GrantHdTblRepositoryImpl implements GrantHdTblRepository{
	
	@Inject
	private AnnualPaidLeaveSettingRepository annualRep;
	/**
	 * check update/insert limit time in holiday 半日年休上限回数  or not
	 * @author yennth
	 */
	@Override
	public Boolean checkLimitTime() {
		String companyId = AppContexts.user().companyId();
		AnnualPaidLeaveSetting annualPaid = annualRep.findByCompanyId(companyId);
		int manageType = annualPaid.getManageAnnualSetting().getHalfDayManage().manageType.value;
		int reference = annualPaid.getManageAnnualSetting().getHalfDayManage().reference.value;
		if(manageType == 1 && reference == 1){
			return true;
		}
		return false;
	}
	/**
	 * check update/insert limit day in year 時間年休上限日数 or not
	 * @author yennth
	 */
	@Override
	public Boolean checkLimitDay() {
		String companyId = AppContexts.user().companyId();
		AnnualPaidLeaveSetting annualPaid = annualRep.findByCompanyId(companyId);
		int timeManageType = annualPaid.getTimeSetting().getTimeVacationDigestUnit().getManage().value;
		int reference = annualPaid.getTimeSetting().getMaxYearDayLeave().reference.value;
		int manageType = annualPaid.getTimeSetting().getMaxYearDayLeave().manageType.value;
		if(timeManageType == 1 && manageType == 1 && reference == 1){
			return true;
		}
		return false;
	}

}
