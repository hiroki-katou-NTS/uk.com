package nts.uk.ctx.bs.person.dom.person.setting.init;

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
	 * update PerInfoInitValueSetting
	 * 
	 * @param domain
	 */
	void update(PerInfoInitValueSetting domain);

	/**
	 * insert PerInfoInitValueSetting
	 * 
	 * @param domain
	 */
	void insert(PerInfoInitValueSetting domain);

	/**
	 * delete initValueSettingId
	 * 
	 * @param initValueSettingId
	 */
	void delete(String initValueSettingId);

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
