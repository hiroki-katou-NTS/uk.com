package nts.uk.ctx.pereg.dom.person.personinfoctgdata.item;

import java.util.List;
import java.util.Optional;

/**
 *
 */
public interface PerInfoItemDataRepository {

	List<PersonInfoItemData> getAllInfoItem(String categoryCd);
	
	List<PersonInfoItemData> getAllInfoItemByPidCtgId(String ctgId, String pid);
	
	List<PersonInfoItemData> getAllInfoItemByRecordId(String recordId);
	
	Optional<PersonInfoItemData> getPerInfoItemDataByItemDefIdAndRecordId(String perInfoItemDef, String recordId);
	
	List<PersonInfoItemData> getItemData(String itemDefId, List<String> recordIds);
	
	/**
	 * Add item data
	 * @param domain
	 */
	void addItemData(PersonInfoItemData domain);
	
	/**
	 * register
	 * @param domain
	 */
	void registerItemData(PersonInfoItemData domain);
	
	/**
	 * Update item data
	 * @param domain
	 */
	void updateItemData(PersonInfoItemData domain);
	
	/**
	 * Delete item data
	 * @param domain
	 */
	void deleteItemData(PersonInfoItemData domain);
	
	boolean hasItemData(List<String> ctgId, String itemCd);

}
