package nts.uk.ctx.sys.assist.app.find.categoryfordelete;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.sys.assist.dom.categoryfordelete.CategoryForDelService;
import nts.uk.ctx.sys.assist.dom.categoryfordelete.CategoryForDelete;
import nts.uk.ctx.sys.assist.dom.categoryfordelete.CategoryForDeleteRepository;

@Stateless
public class CategoryForDeleteFinder
{

    @Inject
    private CategoryForDeleteRepository finder;
    
    @Inject
    private CategoryForDelService categoryForDelService;

    public List<CategoryForDelDto> getAllCategory(){
        return finder.getAllCategory().stream().map(item -> CategoryForDelDto.fromDomain(item))
                .collect(Collectors.toList());
    }
    
    
    public List<CategoryForDelDto> getCategoryBySystemType(int systemType) {
    	List<CategoryForDelDto> listCategoryDto = new ArrayList<>();
    	List<CategoryForDelete> listCategory =  categoryForDelService.categoriesBySystemType(systemType);
    	listCategoryDto = listCategory.stream().map(c -> CategoryForDelDto.fromDomain(c)).collect(Collectors.toList());
    	return listCategoryDto;
    }
    
    public List<CategoryForDelDto> getCategoryByCodeOrName(int systemType,String keySearch,List<String> categoriesIgnore){
    	List<CategoryForDelDto> listCategoryDto = new ArrayList<>();
    	List<CategoryForDelete> listCategory =  categoryForDelService.categoriesByCodeOrName(systemType, keySearch, categoriesIgnore);
    	listCategoryDto = listCategory.stream().map(c -> CategoryForDelDto.fromDomain(c)).collect(Collectors.toList());
    	return listCategoryDto;
    }
    
}
