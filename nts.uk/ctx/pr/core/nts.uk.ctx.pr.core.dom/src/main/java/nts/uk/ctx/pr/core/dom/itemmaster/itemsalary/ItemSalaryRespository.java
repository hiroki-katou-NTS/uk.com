package nts.uk.ctx.pr.core.dom.itemmaster.itemsalary;

import java.util.List;
import java.util.Optional;

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
}
