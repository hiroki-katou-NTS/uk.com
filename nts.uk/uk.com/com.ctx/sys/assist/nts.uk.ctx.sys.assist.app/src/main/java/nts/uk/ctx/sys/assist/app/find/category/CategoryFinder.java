package nts.uk.ctx.sys.assist.app.find.category;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.sys.assist.dom.category.Category;
import nts.uk.ctx.sys.assist.dom.category.CategoryRepository;
import nts.uk.ctx.sys.assist.dom.storage.SystemType;

@Stateless
public class CategoryFinder
{

    @Inject
    private CategoryRepository finder;

    public List<CategoryDto> getAllCategory(){
        return finder.getAllCategory().stream().map(item -> CategoryDto.fromDomain(item))
                .collect(Collectors.toList());
    }
    
    
    public List<CategoryDto> getCategoryBySystemType(int systemType) {
    	
    	List<Category> listCategory =  null;
    	List<CategoryDto> listCategoryDto = new ArrayList<>();
    	if(systemType == SystemType.PERSON_SYSTEM.value) {
    		listCategory = finder.findByPossibilitySystem();
    	} else if (systemType == SystemType.ATTENDANCE_SYSTEM.value) {
    		listCategory = finder.findByAttendanceSystem();
    	} else if (systemType == SystemType.PAYROLL_SYSTEM.value) {
    		listCategory = finder.findByPaymentAvailability();
    	} else if (systemType == SystemType.OFFICE_HELPER.value) {
    		listCategory = finder.findBySchelperSystem();
    	}
    	listCategoryDto = listCategory.stream().map(c -> CategoryDto.fromDomain(c)).collect(Collectors.toList());
    	
    	return listCategoryDto;
    }
    
    public List<CategoryDto> getCategoryByCodeOrName(int systemType,String keySearch,List<String> categoriesIgnore){
    	List<Category> listCategory =  null;
    	List<CategoryDto> listCategoryDto = new ArrayList<>();
    	if(systemType == SystemType.PERSON_SYSTEM.value) {
    		listCategory = finder.findByPossibilitySystemAndCodeName(keySearch, categoriesIgnore);
    	} else if (systemType == SystemType.ATTENDANCE_SYSTEM.value) {
    		listCategory = finder.findByAttendanceSystemAndCodeName(keySearch, categoriesIgnore);
    	} else if (systemType == SystemType.PAYROLL_SYSTEM.value) {
    		listCategory = finder.findByPaymentAvailabilityAndCodeName(keySearch, categoriesIgnore);
    	} else if (systemType == SystemType.OFFICE_HELPER.value) {
    		listCategory = finder.findBySchelperSystemAndCodeName(keySearch, categoriesIgnore);
    	}
    	listCategoryDto = listCategory.stream().map(c -> CategoryDto.fromDomain(c)).collect(Collectors.toList());
    	return listCategoryDto;
    }
    
}
