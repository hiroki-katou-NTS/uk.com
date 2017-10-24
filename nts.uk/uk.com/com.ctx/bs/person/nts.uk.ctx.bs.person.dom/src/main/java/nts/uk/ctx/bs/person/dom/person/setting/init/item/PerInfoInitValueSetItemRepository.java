package nts.uk.ctx.bs.person.dom.person.setting.init.item;

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
	List<PerInfoInitValueSetItem> getAllItem(String settingId, String perInfoCtgId);

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
	
	/**
	 * addItem
	 * @param item
	 */
	void addItem(PerInfoInitValueSetItem item);
	
	
	void update(PerInfoInitValueSetItem item);
}
