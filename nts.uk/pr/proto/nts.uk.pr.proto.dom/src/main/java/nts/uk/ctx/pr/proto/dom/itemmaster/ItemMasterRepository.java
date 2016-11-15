package nts.uk.ctx.pr.proto.dom.itemmaster;

import java.util.List;
import java.util.Optional;

public interface ItemMasterRepository {
	
	/**
	 * find all item master by company code and category type
	 * @param companyCode
	 * @param categoryType
	 * @return
	 */
	List<ItemMaster> findAll(String companyCode, int categoryType);

	/**
	 * find item master by company code, category type, item master code
	 * @param companyCode
	 * @param categoryType
	 * @param itemCode
	 * @return
	 */
	Optional<ItemMaster> find(String companyCode, int categoryType, String itemCode);
}
