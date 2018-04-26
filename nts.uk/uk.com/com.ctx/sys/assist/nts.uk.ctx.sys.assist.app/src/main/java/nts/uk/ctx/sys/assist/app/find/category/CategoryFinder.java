package nts.uk.ctx.sys.assist.app.find.category;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.sys.assist.dom.category.CategoryRepository;

@Stateless
public class CategoryFinder
{

    @Inject
    private CategoryRepository finder;

    public List<CategoryDto> getAllCategory(){
       /* return finder.getAllCategory().stream().map(item -> CategoryDto.fromDomain(item))
                .collect(Collectors.toList());*/
    	return null;
    }
    
    
    public List<CusCategoryDto> getCategoryBySystemType(int systemType) {
    	List<CusCategoryDto> listCusCategory = new ArrayList<>();
    	for (int i = 0; i < 4; i++) {
    		CusCategoryDto cusCategory = new CusCategoryDto();
    		cusCategory.setCode(i);
    		cusCategory.setName("Category");
    		cusCategory.setPeriod("1111111");
    		cusCategory.setRange("2222222");
    		listCusCategory.add(cusCategory);
		}
    	return listCusCategory;
    }
    
}
