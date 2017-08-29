package nts.uk.ctx.bs.person.dom.person.info.item;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.bs.person.dom.person.info.order.PerInfoItemDefOrder;

public interface PerInfoItemDefRepositoty {

	List<PersonInfoItemDefinition> getAllPerInfoItemDefByCategoryId(String perInfoCategoryId, String contractCd);

	Optional<PersonInfoItemDefinition> getPerInfoItemDefById(String perInfoItemDefId, String contractCd);

	List<PersonInfoItemDefinition> getPerInfoItemDefByListId(List<String> listItemDefId, String contractCd);

	List<String> getPerInfoItemsName(String perInfoCtgId, String contractCd);

	String addPerInfoItemDefRoot(PersonInfoItemDefinition perInfoItemDef, String contractCd, String ctgCode);

	void updatePerInfoItemDefRoot(PersonInfoItemDefinition perInfoItemDef, String contractCd);

	void removePerInfoItemDefRoot(List<String> perInfoCtgIds, String categoryCd, String contractCd,
			String itemCode);

	String getPerInfoItemCodeLastest(String contractCd, String categoryCd);

	List<String> addPerInfoItemDefByCtgIdList(PersonInfoItemDefinition perInfoItemDef, List<String> perInfoCtgId);

	List<PerInfoItemDefOrder> getPerInfoItemDefOrdersByCtgId(String perInfoCtgId);

	int getItemDispOrderBy(String perInfoCtgId, String perInfoItemDefId);

	List<String> getRequiredIds(String contractCd, String companyId);

	// Sonnlb Code

	List<PersonInfoItemDefinition> getAllPerInfoItemDefByCategoryIdWithNoAbolition(String perInfoCtgId,
			String contractCd);

	void updatePerInfoItemDef(PersonInfoItemDefinition perInfoItemDef);

	String getItemDefaultName(String categoryCd, String itemCode);

	Optional<PerInfoItemDefOrder> getPerInfoItemDefOrdersByItemId(String perInfoItemDefId);

	void UpdateOrderItem(PerInfoItemDefOrder itemOrder);

	// Sonnlb Code
}
