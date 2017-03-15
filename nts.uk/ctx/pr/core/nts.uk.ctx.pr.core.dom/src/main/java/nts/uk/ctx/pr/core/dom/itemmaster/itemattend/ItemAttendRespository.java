package nts.uk.ctx.pr.core.dom.itemmaster.itemattend;

import java.util.Optional;

public interface ItemAttendRespository {
	
	Optional<ItemAttend> find(String companyCode, String itemCode);
	
	/**
	 * Update information of item attend
	 * @param item domain
	 */
	void update(String companyCode, ItemAttend item);
	
}
