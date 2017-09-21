package nts.uk.ctx.bs.person.dom.person.setting.init.category;

import java.util.List;

public interface PerInfoInitValSetCtgRepository {
	
	/**
	 * getAllInitValSetCtg
	 * @param initValueSettingId
	 * @return List<PerInfoInitValSetCtg>
	 */
	List<PerInfoInitValSetCtg> getAllInitValSetCtg(String initValueSettingId);
	
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
