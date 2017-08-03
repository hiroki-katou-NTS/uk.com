package nts.uk.ctx.bs.person.dom.person.info.category;

import java.util.List;
import java.util.Optional;

public interface PerInfoCategoryRepositoty {
	
	List<PersonInfoCategory> getAllPerInfoCategory(String companyId);
	Optional<PersonInfoCategory> getPerInfoCategory(String perInfoCategoryId);
}
