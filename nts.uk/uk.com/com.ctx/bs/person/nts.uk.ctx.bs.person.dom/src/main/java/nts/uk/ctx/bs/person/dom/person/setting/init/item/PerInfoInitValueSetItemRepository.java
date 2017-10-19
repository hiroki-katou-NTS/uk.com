package nts.uk.ctx.bs.person.dom.person.setting.init.item;

import java.util.List;

public interface PerInfoInitValueSetItemRepository {

	/**
	 * getAllInitValueItem
	 * 
	 * @param settingId
	 * @return List<PerInfoInitValueSetItem>
	 */

	List<PerInfoInitValueSetItem> getAllInitValueItem(String settingId);

	/**
	 * get all init value
	 * 
	 * @param perInfoCtgId
	 * @param settingId
	 * @return List<PerInfoInitValueSetItem>
	 */

	List<PerInfoInitValueSetItem> getAllInitValueItem(String perInfoCtgId, String settingId);

	/**
	 * get all init value item: initvalueItem ^ itemDefined
	 * 
	 * @param perInfoCtgId
	 * @return List<PerInfoInitValueSetItem>
	 */
	List<PerInfoInitValueSetItem> getAllItem(String perInfoCtgId);

	/**
	 * get detail item
	 * 
	 * @param perInfoCtgId
	 * @param perInfoItemDefId
	 * @return
	 */
	PerInfoInitValueSetItem getDetailItem(String perInfoCtgId, String perInfoItemDefId);

	/**
	 * delete by perInfoItemDefId, perInfoCtgId, settingId
	 * 
	 * @param perInfoItemDefId
	 * @param perInfoCtgId
	 * @param settingId
	 */
	void delete(String perInfoItemDefId, String perInfoCtgId, String settingId);

	/**
	 * check category have item list ?
	 * 
	 * @param perInfoCtgId
	 * @return
	 */
	boolean isExist(String perInfoCtgId);

	/**
	 * delete all item by settingId
	 * 
	 * @param settingId
	 */
	void deleteAllBySetId(String settingId);

	/**
	 * add all list item
	 * 
	 * @param lstItem
	 */
	void addAllItem(List<PerInfoInitValueSetItem> lstItem);
}
