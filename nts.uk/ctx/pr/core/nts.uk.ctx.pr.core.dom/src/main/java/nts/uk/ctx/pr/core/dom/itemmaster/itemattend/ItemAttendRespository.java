package nts.uk.ctx.pr.core.dom.itemmaster.itemattend;

import java.util.List;
import java.util.Optional;

public interface ItemAttendRespository {
	
	Optional<ItemAttend> find(String companyCode, String itemCode);
	
	/**
	 * Find all item attend by company
	 * @param companyCode
	 * @return
	 */
	List<ItemAttend> findAll(String companyCode);
	
	/**
	 * Update information of item attend
	 * @param item domain
	 */
	void update(ItemAttend item);
	
}
