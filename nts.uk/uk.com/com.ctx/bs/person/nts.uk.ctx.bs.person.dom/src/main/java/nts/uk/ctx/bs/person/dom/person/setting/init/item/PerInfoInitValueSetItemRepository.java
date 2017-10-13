package nts.uk.ctx.bs.person.dom.person.setting.init.item;

import java.util.List;

public interface PerInfoInitValueSetItemRepository {
	
	/**
	 * get all init value item
	 * @param perInfoCtgId
	 * @return
	 */
	List<PerInfoInitValueSetItem> getAllItem(String perInfoCtgId);
	
	/**
	 * get detail item
	 * @param perInfoCtgId
	 * @param perInfoItemDefId
	 * @return
	 */
	PerInfoInitValueSetItem getDetailItem(String perInfoCtgId, String perInfoItemDefId);
	
	/**
	 * check category have item list ? 
	 * @param perInfoCtgId
	 * @return
	 */
	boolean isExist(String perInfoCtgId);


}
