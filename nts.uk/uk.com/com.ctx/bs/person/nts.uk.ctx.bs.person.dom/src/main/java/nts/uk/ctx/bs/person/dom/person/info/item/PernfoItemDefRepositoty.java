package nts.uk.ctx.bs.person.dom.person.info.item;

import java.util.List;
import java.util.Optional;

public interface PernfoItemDefRepositoty {

	List<PersonInfoItemDefinition> getAllPerInfoItemDefByCategoryId(String perInfoCategoryId);

	Optional<PersonInfoItemDefinition> getPerInfoItemDefById(String perInfoItemDefId);
	
	List<PersonInfoItemDefinition> getPerInfoItemDefByListId(List<String> listItemDefId);
}
