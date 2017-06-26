package nts.uk.ctx.at.shared.dom.bonuspay.services;

import java.util.List;

import nts.uk.ctx.at.shared.dom.bonuspay.setting.BonusPaySetting;

public interface BonusPaySettingService {

	public List<BonusPaySetting> getAllBonusPaySetting(String companyId);

	public void addBonusPaySetting(BonusPaySetting domain);

	public void updateBonusPaySetting(BonusPaySetting domain);

}
