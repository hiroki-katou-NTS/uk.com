package nts.uk.ctx.at.shared.dom.bonuspay.services;

import java.util.List;

import nts.uk.ctx.at.shared.dom.bonuspay.primitives.BonusPaySettingCode;
import nts.uk.ctx.at.shared.dom.bonuspay.setting.BonusPaySetting;
import nts.uk.ctx.at.shared.dom.bonuspay.setting.BonusPayTimesheet;
import nts.uk.ctx.at.shared.dom.bonuspay.setting.SpecBonusPayTimesheet;

public interface BonusPaySettingService {

//	public List<BonusPaySetting> getAllBonusPaySetting(String companyId);

	public void addBonusPaySetting(BonusPaySetting domain, List<BonusPayTimesheet> listBonusPayTimeSheet, List<SpecBonusPayTimesheet> listSpecBonusPayTimeSheet);

	public void updateBonusPaySetting(BonusPaySetting domain, List<BonusPayTimesheet> listBonusPayTimeSheet, List<SpecBonusPayTimesheet> listSpecBonusPayTimeSheet);

	public void deleteBonusPaySetting(String companyId, String bonusPaySettingCode);
	
	boolean isExisted(String companyId, BonusPaySettingCode code);

}
