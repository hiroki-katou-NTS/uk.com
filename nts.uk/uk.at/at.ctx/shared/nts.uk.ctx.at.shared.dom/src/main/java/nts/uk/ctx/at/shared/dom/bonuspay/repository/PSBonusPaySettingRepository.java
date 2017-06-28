/**
 * 9:51:40 AM Jun 6, 2017
 */
package nts.uk.ctx.at.shared.dom.bonuspay.repository;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.shared.dom.bonuspay.primitives.EmployeeId;
import nts.uk.ctx.at.shared.dom.bonuspay.setting.PersonalBonusPaySetting;

/**
 * @author hungnm
 *
 */
public interface PSBonusPaySettingRepository {

	List<PersonalBonusPaySetting> getListSetting(List<EmployeeId> lstEmployeeId);

	Optional<PersonalBonusPaySetting> getPersonalBonusPaySetting(EmployeeId employeeId);

	void addPBPSetting(PersonalBonusPaySetting personalBonusPaySetting);

	void updatePBPSetting(PersonalBonusPaySetting personalBonusPaySetting);

	void removePBPSetting(PersonalBonusPaySetting personalBonusPaySetting);
}
