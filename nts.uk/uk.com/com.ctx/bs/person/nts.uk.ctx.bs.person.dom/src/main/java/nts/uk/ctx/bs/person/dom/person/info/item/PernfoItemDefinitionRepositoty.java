package nts.uk.ctx.bs.person.dom.person.info.item;

import java.util.List;

public interface PernfoItemDefinitionRepositoty {

	List<PersonInfoItemDefinition> getAllPerInfoItemDefByCategoryId(String perInfoCategoryId);

	PersonInfoItemDefinition getPerInfoItemDefById(String perInfoItemDefId);
	
	List<PersonInfoItemDefinition> getPerInfoItemDefByListId(List<String> perInfoItemDefIds);
}
