package nts.uk.ctx.bs.person.dom.person.info.item;

import java.util.List;
import java.util.Optional;

public interface PernfoItemDefRepositoty {

	List<PersonInfoItemDefinition> getAllPerInfoItemDefByCategoryId(String perInfoCategoryId, String contractCd);

	Optional<PersonInfoItemDefinition> getPerInfoItemDefById(String perInfoItemDefId, String contractCd);

	List<PersonInfoItemDefinition> getPerInfoItemDefByListId(List<String> listItemDefId, String contractCd);

	List<String> getPerInfoItemsName(String perInfoCtgId, String contractCd);

	void addPerInfoItemDefRoot(PersonInfoItemDefinition perInfoItemDef, String contractCd);

	void updatePerInfoItemDefRoot(PersonInfoItemDefinition perInfoItemDef, String contractCd);

	String getPerInfoItemCodeLastest(String contractCd, String categoryCd);

	boolean checkItemNameIsUnique(String perInfoCtgId, String newItemName);
	
	void addPerInfoItemDefByCtgIdList(PersonInfoItemDefinition perInfoItemDef, List<String> perInfoCtgId);

}
