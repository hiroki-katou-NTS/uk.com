package nts.uk.ctx.pr.proto.dom.itemmaster;

import java.util.List;
import java.util.Optional;

public interface ItemMasterRepository {

	/**
	 * Get all of company
	 * 
	 * @param companyCode
	 * @return
	 */
	List<ItemMaster> findAll(String companyCode);

	/**
	 * get All Item Master
	 * 
	 * @param companyCode
	 * @param categoryAtr
	 * @return list Item Master
	 */
	List<ItemMaster> findAllByCategory(String companyCode, int categoryAtr);

	/**
	 * get Item Master
	 * 
	 * @param companyCode
	 * @param categoryAtr
	 * @param itemCode
	 * @return list Item Master
	 */
	Optional<ItemMaster> getItemMaster(String companyCode, int categoryAtr, String itemCode);
	
	/**
	 * Find item master
	 * @param companyCode company code
	 * @param categoryAtr category attribute
	 * @param itemCode item code
	 * @return item master
	 */
	Optional<ItemMaster> find(String companyCode, int categoryAtr, String itemCode);
}
