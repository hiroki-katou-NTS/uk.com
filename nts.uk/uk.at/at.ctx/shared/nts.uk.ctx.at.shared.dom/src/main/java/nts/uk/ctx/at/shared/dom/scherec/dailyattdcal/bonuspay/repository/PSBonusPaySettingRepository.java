/**
 * 9:51:40 AM Jun 6, 2017
 */
package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.repository;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.setting.PersonalBonusPaySetting;



/**
 * @author hungnm
 *
 */
public interface PSBonusPaySettingRepository {

	List<PersonalBonusPaySetting> getListSetting(List<String> lstEmployeeId);

	Optional<PersonalBonusPaySetting> getPersonalBonusPaySetting(String employeeId);

	void addPBPSetting(PersonalBonusPaySetting personalBonusPaySetting);

	void updatePBPSetting(PersonalBonusPaySetting personalBonusPaySetting);

	void removePBPSetting(PersonalBonusPaySetting personalBonusPaySetting);
}
