package nts.uk.ctx.at.record.dom.bonuspay.services;

import nts.uk.ctx.at.record.dom.bonuspay.primitives.BonusPaySettingCode;
import nts.uk.ctx.at.record.dom.bonuspay.setting.BonusPaySetting;

public interface BonusPaySettingService {

//	public List<BonusPaySetting> getAllBonusPaySetting(String companyId);

	public void addBonusPaySetting(BonusPaySetting domain);

	public void updateBonusPaySetting(BonusPaySetting domain);

	public void deleteBonusPaySetting(String companyId, String bonusPaySettingCode);
	
	boolean isExisted(String companyId, BonusPaySettingCode code);

}
