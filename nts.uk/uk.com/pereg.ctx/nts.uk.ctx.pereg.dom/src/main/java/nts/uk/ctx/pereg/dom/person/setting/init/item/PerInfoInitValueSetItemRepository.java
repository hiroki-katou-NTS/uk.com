package nts.uk.ctx.pereg.dom.person.setting.init.item;

import java.util.List;
import java.util.Optional;

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
	List<PerInfoInitValueSetItemDetail> getAllItem(String settingId, String perInfoCtgId);

	// sonnlb

	/**
	 * get all init value item: initvalueItem ^ itemDefined
	 * 
	 * @param perInfoCtgId
	 * @return List<PerInfoInitValueSetItem>
	 */
	List<PerInfoInitValueSetItemDetail> getAllInitItem(String settingId, String perInfoCtgId, String cid);

	// sonnlb

	/**
	 * get detail item
	 * 
	 * @param perInfoCtgId
	 * @param perInfoItemDefId
	 * @return Optional<PerInfoInitValueSetItem>
	 */
	Optional<PerInfoInitValueSetItem> getDetailItem(String settingId, String perInfoCtgId, String perInfoItemDefId);

	/**
	 * delete by perInfoItemDefId, perInfoCtgId, settingId
	 * 
	 * @param perInfoItemDefId
	 * @param perInfoCtgId
	 * @param settingId
	 */
	void delete(String perInfoItemDefId, String perInfoCtgId, String settingId);

	/**
	 * check category have item init list ?
	 * 
	 * @param perInfoCtgId
	 * @param settingId
	 * @return
	 */
	boolean isExist(String settingId, String perInfoCtgId);

	/**
	 * check category have item init list ?
	 * 
	 * @param perInfoCtgId
	 * @param settingId
	 * @return
	 */
	List<String> isExistItem(List<String> perInfoCtgId);

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

	/**
	 * addItem
	 * 
	 * @param item
	 */
	void addItem(PerInfoInitValueSetItem item);

	void update(PerInfoInitValueSetItem item);
	
	boolean hasItemData(String itemCd, List<String> perInfoCtgId);
}
