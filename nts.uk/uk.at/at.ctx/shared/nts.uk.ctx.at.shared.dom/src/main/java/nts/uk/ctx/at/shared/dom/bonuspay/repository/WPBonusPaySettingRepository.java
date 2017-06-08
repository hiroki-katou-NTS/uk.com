/**
 * 9:53:14 AM Jun 6, 2017
 */
package nts.uk.ctx.at.shared.dom.bonuspay.repository;

import java.util.List;

import nts.uk.ctx.at.shared.dom.bonuspay.setting.WorkplaceBonusPaySetting;

/**
 * @author hungnm
 *
 */
public interface WPBonusPaySettingRepository {

	List<WorkplaceBonusPaySetting> getListSetting(List<String> lstWorkplace);

	void addListSetting(List<WorkplaceBonusPaySetting> lstSetting);

	void updateListSetting(List<WorkplaceBonusPaySetting> lstSetting);

	void removeListSetting(List<WorkplaceBonusPaySetting> lstSetting);

}
