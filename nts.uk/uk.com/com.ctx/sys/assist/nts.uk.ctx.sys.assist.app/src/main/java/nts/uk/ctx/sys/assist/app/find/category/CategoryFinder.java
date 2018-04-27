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
    		listCategory = finder.findByPossibilitySystem(systemType);
    	} else if (systemType == SystemType.ATTENDANCE_SYSTEM.value) {
    		listCategory = finder.findByAttendanceSystem(systemType);
    	} else if (systemType == SystemType.PAYROLL_SYSTEM.value) {
    		listCategory = finder.findByPaymentAvailability(systemType);
    	} else if (systemType == SystemType.OFFICE_HELPER.value) {
    		listCategory = finder.findBySchelperSystem(systemType);
    	}
    	listCategoryDto = listCategory.stream().map(c -> CategoryDto.fromDomain(c)).collect(Collectors.toList());
    	
    	return listCategoryDto;
    }
    
}
