/**
 * 9:51:40 AM Jun 6, 2017
 */
package nts.uk.ctx.at.shared.dom.bonuspay.repository;

import java.util.List;

import nts.uk.ctx.at.shared.dom.bonuspay.setting.PersonalBonusPaySetting;

/**
 * @author hungnm
 *
 */
public interface PSBonusPaySettingRepository {
	
	List<PersonalBonusPaySetting> getListSetting(List<String> lstEmployeeId);
	
	void addListSetting(List<PersonalBonusPaySetting> lstSetting);
	
	void updateListSetting(List<PersonalBonusPaySetting> lstSetting);
	
	void removeListSetting(List<PersonalBonusPaySetting> lstSetting);
}
