package nts.uk.ctx.bs.person.dom.person.info.category;

import java.util.List;

public interface PerInfoCategoryRepositoty {
	
	List<PersonInfoCategory> getAllPerInfoCategory();
	PersonInfoCategory getPerInfoCategory(String perInfoCategoryId);
}
