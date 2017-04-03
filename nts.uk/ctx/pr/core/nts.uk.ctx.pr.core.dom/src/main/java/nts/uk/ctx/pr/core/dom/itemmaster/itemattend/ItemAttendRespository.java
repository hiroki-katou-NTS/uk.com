package nts.uk.ctx.pr.core.dom.itemmaster.itemattend;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.pr.core.dom.itemmaster.AvePayAtr;

public interface ItemAttendRespository {
	
	Optional<ItemAttend> find(String companyCode, String itemCode);
	
	/**
	 * Find all item attend by company
	 * @param companyCode
	 * @return
	 */
	List<ItemAttend> findAll(String companyCode);
	
	/**
	 * Find all item attend by company
	 * @param companyCode
	 * @return
	 */
	List<ItemAttend> findAll(String companyCode, AvePayAtr avePayAtr);
	
	/**
	 * Update information of item attend
	 * @param item domain
	 */
	void update(ItemAttend item);

	/**
	 * Update average wage attribute of multiple item
	 * @param companyCode company code
	 * @param itemCodeList list of item code
	 * @param avePayAtr average wage attribute: NotApplicable or Object
	 */
	void updateItems(String companyCode, List<String> itemCodeList, AvePayAtr avePayAtr);
	
	void add(ItemAttend itemAttend);

	void delete(String companyCode);
	
}
