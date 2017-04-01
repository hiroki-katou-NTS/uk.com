package nts.uk.ctx.pr.core.dom.itemmaster.itemsalary;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.pr.core.dom.itemmaster.AvePayAtr;

public interface ItemSalaryRespository {
	Optional<ItemSalary> find(String companyCode, String itemCode);
	
	/**
	 * Find all item salary by company
	 * @param companyCode
	 * @return
	 */
	List<ItemSalary> findAll(String companyCode);
	
	/**
	 * update information of item salary
	 * @param item domain ItemSalary
	 */
	void update(ItemSalary item);
	
	/**
	 * Update average wage attribute of multiple item
	 * @param companyCode company code
	 * @param itemCodeList list of item code
	 * @param avePayAtr average wage attribute: NotApplicable or Object
	 */
	void updateItems(String companyCode, List<String> itemCodeList, AvePayAtr avePayAtr);
	
	void add(ItemSalary item);

	void delete(String companyCode);
}
