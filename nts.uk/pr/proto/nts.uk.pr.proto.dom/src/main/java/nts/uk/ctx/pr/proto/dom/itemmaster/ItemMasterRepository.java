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
	Optional<ItemMaster> findAll(String companyCode, String categoryType);

	/**
	 * find item master by company code, category type, item master(code or name)
	 * @param companyCode
	 * @param categoryType
	 * @param itemMaster
	 * @return
	 */
	List<ItemMaster> find(String companyCode, String categoryType, String itemMaster);
}
