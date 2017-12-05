package nts.uk.ctx.pereg.app.find.employee.category;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pereg.app.find.person.category.PerInfoCtgFullDto;
import nts.uk.ctx.pereg.app.find.processor.LayoutingProcessor;
import nts.uk.ctx.pereg.dom.person.info.category.CategoryType;
import nts.uk.ctx.pereg.dom.person.info.category.IsFixed;
import nts.uk.ctx.pereg.dom.person.info.category.PerInfoCategoryRepositoty;
import nts.uk.ctx.pereg.dom.person.info.category.PersonInfoCategory;
import nts.uk.ctx.pereg.dom.roles.auth.PersonInfoPermissionType;
import nts.uk.ctx.pereg.dom.roles.auth.category.PersonInfoCategoryAuth;
import nts.uk.ctx.pereg.dom.roles.auth.category.PersonInfoCategoryAuthRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.pereg.app.ComboBoxObject;
import nts.uk.shr.pereg.app.find.PeregQuery;

@Stateless
public class EmpCtgFinder {
	
	@Inject
	private PerInfoCategoryRepositoty perInfoCategoryRepositoty;
	
	@Inject
	private PersonInfoCategoryAuthRepository personInfoCategoryAuthRepository;
	
	@Inject
	private LayoutingProcessor layoutingProcessor;
	
	@Inject
	private PerInfoCategoryRepositoty perInfoCtgRepositoty;
	

	public List<PerInfoCtgFullDto> getAllPerInfoCtg(String employeeIdSelected) {
		String companyId = AppContexts.user().companyId();
		String empIdCurrentLogin = AppContexts.user().employeeId();
		// get roleIdOfLogin from app context;
		 String roleIdOfLogin = "99900000-0000-0000-0000-000000000001";
		//String roleIdOfLogin = AppContexts.user().roles().forPersonalInfo();

		// get list Category
		 List<PersonInfoCategory> listCategory = perInfoCategoryRepositoty.getAllPerInfoCtg(companyId);
		
		boolean isSelf = employeeIdSelected.equals(empIdCurrentLogin);
		List<PersonInfoCategory> returnList = listCategory.stream().filter(x -> {
			Optional<PersonInfoCategoryAuth> perInfoCtgAuth = personInfoCategoryAuthRepository.getDetailPersonCategoryAuthByPId(roleIdOfLogin, x.getPersonInfoCategoryId());
			if(!perInfoCtgAuth.isPresent()) return false;
			return isSelf ? perInfoCtgAuth.get().getAllowPersonRef() == PersonInfoPermissionType.YES
				:perInfoCtgAuth.get().getAllowOtherRef() == PersonInfoPermissionType.YES;
		}).collect(Collectors.toList());
		return returnList.stream().map(x -> new PerInfoCtgFullDto(x.getPersonInfoCategoryId(), x.getCategoryCode().v(), x.getCategoryName().v(), 
				x.getPersonEmployeeType().value, x.getIsAbolition().value, x.getCategoryType().value, x.getIsFixed().value) ).collect(Collectors.toList());
	};
	
	
	public List<ComboBoxObject> getListInfoCtgByCtgIdAndSid(PeregQuery query){
		String contractCode = AppContexts.user().contractCode();
		PersonInfoCategory perInfoCtg = perInfoCtgRepositoty.getPerInfoCategory(query.getCategoryId(), contractCode).get();
		if(perInfoCtg.getIsFixed() == IsFixed.NOT_FIXED) return new ArrayList<>();
		if(perInfoCtg.getCategoryType() == CategoryType.SINGLEINFO) return new ArrayList<>();
		query.setCategoryCode(perInfoCtg.getCategoryCode().v());
		return layoutingProcessor.getListFirstItems(query);
	}


}
