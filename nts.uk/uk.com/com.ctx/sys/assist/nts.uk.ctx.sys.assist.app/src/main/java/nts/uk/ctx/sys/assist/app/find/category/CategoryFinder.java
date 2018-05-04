package nts.uk.ctx.sys.assist.app.find.category;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.sys.assist.dom.category.Category;
import nts.uk.ctx.sys.assist.dom.category.CategoryRepository;
import nts.uk.ctx.sys.assist.dom.category.CategoryService;

@Stateless
public class CategoryFinder
{

    @Inject
    private CategoryRepository finder;
    
    @Inject
    private CategoryService categoryService;

    public List<CategoryDto> getAllCategory(){
        return finder.getAllCategory().stream().map(item -> CategoryDto.fromDomain(item))
                .collect(Collectors.toList());
    }
    
    
    public List<CategoryDto> getCategoryBySystemType(int systemType) {
    	List<CategoryDto> listCategoryDto = new ArrayList<>();
    	List<Category> listCategory =  categoryService.categoriesBySystemType(systemType);
    	listCategoryDto = listCategory.stream().map(c -> CategoryDto.fromDomain(c)).collect(Collectors.toList());
    	return listCategoryDto;
    }
    
    public List<CategoryDto> getCategoryByCodeOrName(int systemType,String keySearch,List<String> categoriesIgnore){
    	List<CategoryDto> listCategoryDto = new ArrayList<>();
    	List<Category> listCategory =  categoryService.categoriesByCodeOrName(systemType, keySearch, categoriesIgnore);
    	listCategoryDto = listCategory.stream().map(c -> CategoryDto.fromDomain(c)).collect(Collectors.toList());
    	return listCategoryDto;
    }
    
}
