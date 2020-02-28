package nts.uk.ctx.hr.develop.dom.careermgmt.setting.algorithm;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.hr.develop.dom.careermgmt.setting.CareerMgmtSettingRepository;

@Stateless
public class CarrierManagementOperationSetting {

	@Inject
	private CareerMgmtSettingRepository careerMgmtSettingRepo; 
	
	//最大階層レベルの取得
	public int getCareerPathMaxClassLevel(String cId) {
		return careerMgmtSettingRepo.getCareerMgmtSetting(cId).getMaxClassLevel().value;
	}
	
}
