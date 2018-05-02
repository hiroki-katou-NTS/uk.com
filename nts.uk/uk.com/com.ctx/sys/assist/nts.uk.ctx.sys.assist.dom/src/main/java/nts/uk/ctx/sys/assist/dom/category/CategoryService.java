package nts.uk.ctx.sys.assist.dom.category;

import java.util.List;

public interface CategoryService {
	
	List<Category> categoriesBySystemType(int systemType);
	List<Category> categoriesByCodeOrName(int systemType,String keySearch,List<String> categoriesIgnore);
}
