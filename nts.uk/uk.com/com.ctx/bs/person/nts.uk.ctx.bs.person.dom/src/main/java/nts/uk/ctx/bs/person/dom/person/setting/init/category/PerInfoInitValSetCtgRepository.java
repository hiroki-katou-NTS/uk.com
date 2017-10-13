package nts.uk.ctx.bs.person.dom.person.setting.init.category;

import java.util.List;

import nts.uk.ctx.bs.person.dom.person.info.category.PersonInfoCategory;

public interface PerInfoInitValSetCtgRepository {
	
	/**
	 * getAllInitValSetCtg
	 * @param initValueSettingId
	 * @return List<PerInfoInitValSetCtg>
	 */
	List<PerInfoInitValSetCtg> getAllInitValSetCtg(String initValueSettingId);
	
	/**
	 * getAllCategory 
	 * isAbortlition: false
     * employeeType = 2
     * categoryType  # 2 vs  #5
     * category_parent_cd: not set
	 * @param initValueSettingId
	 * @return
	 */
	List<PerInfoInitValueSettingCtg> getAllCategory(String companyId);
	
	/**
	 * getDetailInitValSetCtg
	 * @param initValueSettingId
	 * @param initValueSettingCtgId
	 * @return PerInfoInitValSetCtg
	 */
	
	PerInfoInitValSetCtg getDetailInitValSetCtg(String initValueSettingId, String initValueSettingCtgId);
	
	/**
	 * add PerInfoInitValSetCtg
	 * @param domain
	 */
	void add(PerInfoInitValSetCtg domain);
	
	/**
	 * update PerInfoInitValSetCtg
	 * @param domain
	 */
	void update(PerInfoInitValSetCtg domain);
	
	/**
	 * delete PerInfoInitValSetCtg
	 * @param initValueSettingCtgId
	 */
	void delete(String initValueSettingCtgId);

}
