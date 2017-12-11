package nts.uk.ctx.pereg.dom.person.setting.init;

import java.util.List;
import java.util.Optional;

public interface PerInfoInitValueSettingRepository {
	/**
	 * get All Init Value Setting
	 * 
	 * @param companyId
	 * @return List<PerInfoInitValueSetting>
	 */
	List<PerInfoInitValueSetting> getAllInitValueSetting(String companyId);

	/**
	 * getDetailInitValSetting
	 * 
	 * @param initValueSettingId
	 * @return PerInfoInitValueSetting
	 */

	Optional<PerInfoInitValueSetting> getDetailInitValSetting(String initValueSettingId);

	/**
	 * getDetailInitValSetting
	 * 
	 * @param companyId
	 * @param setCode
	 * @return PerInfoInitValueSetting
	 */

	Optional<PerInfoInitValueSetting> getDetailInitValSetting(String companyId, String setCode);

	/**
	 * getDetailInitValSetting
	 * 
	 * @param companyId
	 * @param setCode
	 * @return PerInfoInitValueSetting
	 */

	Optional<PerInfoInitValueSetting> getDetailInitValSetting(String companyId, String setCode, String settingId);

	/**
	 * update PerInfoInitValueSetting
	 * 
	 * @param domain
	 */
	void update(PerInfoInitValueSetting domain);
	
	/**
	 * update name of PerInfoInitValueSetting
	 * @param settingId
	 * @param settingName
	 */
	void updateName(String settingId, String settingName);

	/**
	 * insert PerInfoInitValueSetting
	 * 
	 * @param domain
	 */
	void insert(PerInfoInitValueSetting domain);

	/**
	 * delete by companyId, settingId, settingCode
	 * @param companyId
	 * @param settingId
	 * @param settingCode
	 */
	void delete(String companyId,String settingId, String settingCode);

	// sonnlb

	/**
	 * getAllInitValueSettingWithItem
	 * 
	 * @param companyId
	 * @return List<PerInfoInitValueSetting>
	 */
	List<PerInfoInitValueSetting> getAllInitValueSettingHasChild(String companyId);

	// sonnlb
}
