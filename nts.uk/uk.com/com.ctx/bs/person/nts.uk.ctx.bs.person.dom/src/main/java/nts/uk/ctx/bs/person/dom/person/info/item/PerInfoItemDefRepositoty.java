package nts.uk.ctx.bs.person.dom.person.info.item;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.bs.person.dom.person.info.order.PerInfoItemDefOrder;
import nts.uk.ctx.bs.person.dom.person.info.setting.copysetting.EmpCopySettingItem;

public interface PerInfoItemDefRepositoty {

	List<PersonInfoItemDefinition> getAllPerInfoItemDefByCategoryId(String perInfoCategoryId, String contractCd);

	Optional<PersonInfoItemDefinition> getPerInfoItemDefById(String perInfoItemDefId, String contractCd);

	List<PersonInfoItemDefinition> getPerInfoItemDefByListId(List<String> listItemDefId, String contractCd);

	List<String> getPerInfoItemsName(String perInfoCtgId, String contractCd);

	String addPerInfoItemDefRoot(PersonInfoItemDefinition perInfoItemDef, String contractCd, String ctgCode);

	void updatePerInfoItemDefRoot(PersonInfoItemDefinition perInfoItemDef, String contractCd);

	String getPerInfoItemCodeLastest(String contractCd, String categoryCd);

	List<String> addPerInfoItemDefByCtgIdList(PersonInfoItemDefinition perInfoItemDef, List<String> perInfoCtgId);

	List<PerInfoItemDefOrder> getPerInfoItemDefOrdersByCtgId(String perInfoCtgId);

	int getItemDispOrderBy(String perInfoCtgId, String perInfoItemDefId);

	List<String> getRequiredIds(String contractCd, String companyId);

	void removePerInfoItemDefRoot(List<String> perInfoCtgIds, String categoryCd, String contractCd, String itemCode);

	boolean checkItemNameIsUnique(String perInfoCtgId, String newItemName, String perInfoItemDefId);

	// Sonnlb Code

	List<PersonInfoItemDefinition> getAllPerInfoItemDefByCategoryIdWithoutAbolition(String perInfoCtgId,
			String contractCd);

	void updatePerInfoItemDef(PersonInfoItemDefinition perInfoItemDef);

	String getItemDefaultName(String categoryCd, String itemCode);

	Optional<PerInfoItemDefOrder> getPerInfoItemDefOrdersByItemId(String perInfoItemDefId);

	void UpdateOrderItem(PerInfoItemDefOrder itemOrder);

	List<PersonInfoItemDefinition> getAllPerInfoItemDefByCategoryIdWithoutSetItem(String perInfoCtgId,
			String contractCd);

	List<PersonInfoItemDefinition> getAllItemFromIdList(String contractCd, List<EmpCopySettingItem> itemList);

	// Sonnlb Code

	// vinhpx start
	int countPerInfoItemDefInCategory(String perInfoCategoryId, String companyId);

	int countPerInfoItemDefInCopySetting(String perInfoItemDefId, String companyId);

	List<PersonInfoItemDefinition> getPerInfoItemByCtgId(String perInfoCategoryId, String companyId, String contractCd);

	void removePerInfoItemInCopySetting(String perInforCtgId, String companyId);

	void updatePerInfoItemInCopySetting(String perInforCtgId, List<String> perInfoItemDefIds);

	// vinhpx end
}
