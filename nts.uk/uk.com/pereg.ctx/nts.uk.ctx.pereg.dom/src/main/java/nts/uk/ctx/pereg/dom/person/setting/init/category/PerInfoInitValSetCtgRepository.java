package nts.uk.ctx.pereg.dom.person.setting.init.category;

import java.util.List;
import java.util.Optional;

public interface PerInfoInitValSetCtgRepository {
	// sonnlb
	/**
	 * getAllInitValSetCtg
	 * 
	 * @param initValueSettingId
	 * @return List<PerInfoInitValSetCtg>
	 */
	List<InitValSettingCtg> getAllCategoryBySetId(String initValueSettingId);

	// sonnlb

	/**
	 * getAllInitValueCtg
	 * 
	 * @param settingId
	 * @return List<PerInfoInitValueSettingCtg>
	 */
	List<PerInfoInitValSetCtg> getAllInitValueCtg(String settingId);

	/**
	 * getAllCategory isAbortlition: false employeeType = 2 categoryType # 2 vs #5
	 * category_parent_cd: not set
	 * 
	 * @param initValueSettingId,
	 *            String settingId
	 * @return List<PerInfoInitValueSettingCtg>
	 */
	List<PerInfoInitValueSettingCtg> getAllCategory(String companyId, String settingId, int salaryUseAtr, int personnelUseAtr, int employmentUseAtr);

	/**
	 * getDetailInitValSetCtg
	 * 
	 * @param settingId
	 * @param perInfoCtgId
	 * @return PerInfoInitValSetCtg
	 */

	Optional<PerInfoInitValSetCtg> getDetailInitValSetCtg(String settingId, String perInfoCtgId);

	/**
	 * add PerInfoInitValSetCtg
	 * 
	 * @param domain
	 */
	void add(PerInfoInitValSetCtg domain);

	/**
	 * update PerInfoInitValSetCtg
	 * 
	 * @param domain
	 */
	void update(PerInfoInitValSetCtg domain);

	/**
	 * delete PerInfoInitValSetCtg
	 * 
	 * @param perInfoCtgId,
	 *            settingId
	 */
	void delete(String perInfoCtgId, String settingId);
	
	
	/**
	 * delete PerInfoInitValSetCtg
	 * 
	 * @param settingId
	 */
	void delete(String settingId);
	
	/**
	 * add All list category
	 * @param lstCtg
	 */
	void addAllCtg(List<PerInfoInitValSetCtg> lstCtg);

}
