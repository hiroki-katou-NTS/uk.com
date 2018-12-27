package nts.uk.ctx.sys.assist.dom.categoryfordelete;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.sys.assist.dom.storage.SystemType;

@Stateless
public class CategoryForDelService  {
	
	 @Inject
	 private CategoryForDeleteRepository finder;
	
	
	public List<CategoryForDelete> categoriesBySystemType(int systemType) {
		List<CategoryForDelete> listCategory =  new ArrayList<>();
		if(systemType == SystemType.PERSON_SYSTEM.value) {
    		listCategory = finder.findByPossibilitySystem();
    	} else if (systemType == SystemType.ATTENDANCE_SYSTEM.value) {
    		listCategory = finder.findByAttendanceSystem();
    	} else if (systemType == SystemType.PAYROLL_SYSTEM.value) {
    		listCategory = finder.findByPaymentAvailability();
    	} else if (systemType == SystemType.OFFICE_HELPER.value) {
    		listCategory = finder.findBySchelperSystem();
    	}
		return listCategory;
	}

	
	public List<CategoryForDelete> categoriesByCodeOrName(int systemType, String keySearch, List<String> categoriesIgnore) {
		List<CategoryForDelete> listCategory = new ArrayList<>();
		if(systemType == SystemType.PERSON_SYSTEM.value) {
    		listCategory = finder.findByPossibilitySystemAndCodeName(keySearch, categoriesIgnore);
    	} else if (systemType == SystemType.ATTENDANCE_SYSTEM.value) {
    		listCategory = finder.findByAttendanceSystemAndCodeName(keySearch, categoriesIgnore);
    	} else if (systemType == SystemType.PAYROLL_SYSTEM.value) {
    		listCategory = finder.findByPaymentAvailabilityAndCodeName(keySearch, categoriesIgnore);
    	} else if (systemType == SystemType.OFFICE_HELPER.value) {
    		listCategory = finder.findBySchelperSystemAndCodeName(keySearch, categoriesIgnore);
    	}
		return listCategory;
	}

}
