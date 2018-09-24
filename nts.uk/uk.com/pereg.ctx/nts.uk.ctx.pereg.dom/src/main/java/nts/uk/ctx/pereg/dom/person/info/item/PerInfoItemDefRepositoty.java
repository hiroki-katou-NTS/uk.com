package nts.uk.ctx.pereg.dom.person.info.item;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import nts.uk.ctx.pereg.dom.person.info.order.PerInfoItemDefOrder;

public interface PerInfoItemDefRepositoty {

	/**
	 * only parent items
	 * 
	 * @param perInfoCategoryId
	 * @param contractCd
	 * @return
	 */
	List<PersonInfoItemDefinition> getAllPerInfoItemDefByCategoryId(String perInfoCategoryId, String contractCd);

	Map<String, List<Object[]>> getAllPerInfoItemDefByListCategoryId(List<String> lstPerInfoCategoryId,
			String contractCd);

	/**
	 * all items Warning: set and table item have not ItemIdList
	 * 
	 * @param perInfoCategoryId
	 * @param contractCd
	 * @return
	 */
	List<PersonInfoItemDefinition> getAllItemDefByCategoryId(String perInfoCategoryId, String contractCd);

	List<PersonInfoItemDefinition> getAllItemByCtgWithAuth(String perInfoCategoryId, String contractCd, String roleId,
			boolean isSelfRef);

	Optional<PersonInfoItemDefinition> getPerInfoItemDefById(String perInfoItemDefId, String contractCd);
	
	Optional<PersonInfoItemDefinition> getPerInfoItemDefByCtgCdItemCdCid(String categoryCode, String itemCd , String cid , String contractCd);

	List<PersonInfoItemDefinition> getPerInfoItemDefByListId(List<String> listItemDefId, String contractCd);

	List<PersonInfoItemDefinition> getPerInfoItemDefByListIdv2(List<String> listItemDefId, String contractCd);

	List<String> getPerInfoItemsName(String perInfoCtgId, String contractCd);

	String addPerInfoItemDefRoot(PersonInfoItemDefinition perInfoItemDef, String contractCd, String ctgCode);

	void updatePerInfoItemDefRoot(PersonInfoItemDefinition perInfoItemDef, String contractCd);

	String getPerInfoItemCodeLastest(String contractCd, String categoryCd);

	List<String> addPerInfoItemDefByCtgIdList(PersonInfoItemDefinition perInfoItemDef, List<String> perInfoCtgId);

	List<PerInfoItemDefOrder> getPerInfoItemDefOrdersByCtgId(String perInfoCtgId);

	int getItemDispOrderBy(String perInfoCtgId, String perInfoItemDefId);

	List<String> getRequiredIds(String contractCd, String companyId);

	void removePerInfoItemDef(List<String> perInfoCtgIds, String categoryCd, String contractCd, String itemCode);

	boolean checkItemNameIsUnique(String perInfoCtgId, String newItemName, String perInfoItemDefId);

	// Sonnlb Code

	List<PersonInfoItemDefinition> getAllPerInfoItemDefByCategoryIdWithoutAbolition(String perInfoCtgId,
			String contractCd);

	void updatePerInfoItemDef(PersonInfoItemDefinition perInfoItemDef);

	String getItemDefaultName(String categoryCd, String itemCode);

	Optional<PerInfoItemDefOrder> getPerInfoItemDefOrdersByItemId(String perInfoItemDefId);

	void UpdateOrderItem(PerInfoItemDefOrder itemOrder);
	
	void updateOrderItem(List<PerInfoItemDefOrder> itemOrder);

	List<PersonInfoItemDefinition> getAllPerInfoItemDefByCategoryIdWithoutSetItem(String perInfoCtgId,
			String contractCd);

	List<PersonInfoItemDefinitionSimple> getRequiredItemFromCtgCdLst(String contractCd, String companyId,
			List<String> CtgCodeList);

	// Sonnlb Code

	int countPerInfoItemDefInCategory(String perInfoCategoryId, String companyId);

	int countPerInfoItemDefInCategoryNo812(String perInfoCategoryId, String companyId);

	List<PersonInfoItemDefinition> getPerInfoItemByCtgIdAndOrder(String perInfoCategoryId, String companyId,
			String contractCd);

	/**
	 * getNotFixedPerInfoItemDefByCategoryId
	 * 
	 * @param perInfoCategoryId
	 * @param contractCd
	 * @return
	 */
	List<PersonInfoItemDefinition> getNotFixedPerInfoItemDefByCategoryId(String perInfoCategoryId, String contractCd);

	List<PersonInfoItemDefinition> getPerInfoItemByCtgId(String personInfoCategoryId, String companyId,
			String contractCode);

	/**
	 * @author lanlt get All Item selection thuộc kiểu datatype = 6 & thuộc kiểu
	 *         code dùng cho màn hình cps016
	 */
	boolean checkExistedSelectionItemId(String selectionItemId);

	/**
	 * 
	 */
	List<PersonInfoItemDefinition> getAllItemUsedByCtgId(List<String> ctgId);

	/**
	 * @author lanlt \get All Item Required of a company
	 * @param contractCd
	 * @param companyId
	 * @return
	 */
	List<String> getAllRequiredIds(String contractCd, String companyId);

	/**
	 * @author lanlt
	 * @param ctgId
	 * @return
	 */
	List<String> getAllRequiredIdsByCtgId(String contract, String ctgId);

	List<PersonInfoItemDefinition> getPerInfoItemByCtgCd(String ctgCd, String companyId);

	List<String> getAllItemIdsByCtgCode(String cid, String ctgCode);

	List<PersonInfoItemDefinition> getItemDefByCtgCdAndComId(String perInfoCtgCd, String CompanyId);

	void updateItemDefNameAndAbolition(List<PersonInfoItemDefinition> lst, String companyId);

	List<PersonInfoItemDefinition> getItemLstByListId(List<String> listItemDefId, String contractCd, String companyId, List<String> categoryCodeLst);

	List<PersonInfoItemDefinition> getItemLstByListId(List<String> listItemDefId, String ctgId, String categoryCd,
			String contractCd);

	List<PerInfoItemDefOrder> getItemOrderByCtgId(String ctgId);
	String getItemName(String contractCode, String companyId, String categoryCode, String itemCode);
	
	List<PersonInfoItemDefinition> getAllItemId(List<String> ctgIdLst, List<String> itemCodeLst);

	void updateAbolitionItem(List<PersonInfoItemDefinition> itemLst);
	
	Map<String, List<PersonInfoItemDefinition>> getByListCategoryIdWithoutAbolition(List<String> lstPerInfoCategoryId,
			String contractCd);
	
	Map<String, List<ItemBasicInfo>> getItemCDByListCategoryIdWithAbolition(List<String> lstPerInfoCategoryId,
			String contractCd);

}
