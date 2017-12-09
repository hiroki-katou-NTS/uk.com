package nts.uk.ctx.pereg.app.find.employee.category;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pereg.app.find.person.category.PerInfoCtgFullDto;
import nts.uk.ctx.pereg.app.find.processor.LayoutingProcessor;
import nts.uk.ctx.pereg.dom.person.ParamForGetPerItem;
import nts.uk.ctx.pereg.dom.person.PerInfoCtgDomainService;
import nts.uk.ctx.pereg.dom.person.additemdata.category.EmInfoCtgDataRepository;
import nts.uk.ctx.pereg.dom.person.additemdata.category.EmpInfoCtgData;
import nts.uk.ctx.pereg.dom.person.additemdata.item.EmpInfoItemData;
import nts.uk.ctx.pereg.dom.person.additemdata.item.EmpInfoItemDataRepository;
import nts.uk.ctx.pereg.dom.person.info.category.CategoryType;
import nts.uk.ctx.pereg.dom.person.info.category.IsFixed;
import nts.uk.ctx.pereg.dom.person.info.category.PerInfoCategoryRepositoty;
import nts.uk.ctx.pereg.dom.person.info.category.PersonEmployeeType;
import nts.uk.ctx.pereg.dom.person.info.category.PersonInfoCategory;
import nts.uk.ctx.pereg.dom.person.info.daterangeitem.DateRangeItem;
import nts.uk.ctx.pereg.dom.person.info.item.PersonInfoItemDefinition;
import nts.uk.ctx.pereg.dom.person.info.setitem.SetItem;
import nts.uk.ctx.pereg.dom.person.personinfoctgdata.categor.PerInfoCtgData;
import nts.uk.ctx.pereg.dom.person.personinfoctgdata.categor.PerInfoCtgDataRepository;
import nts.uk.ctx.pereg.dom.person.personinfoctgdata.item.PerInfoItemDataRepository;
import nts.uk.ctx.pereg.dom.person.personinfoctgdata.item.PersonInfoItemData;
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
	
	@Inject
	private PerInfoCtgDomainService perInfoCtgDomainService;
	
	@Inject
	private EmInfoCtgDataRepository emInfoCtgDataRepository;
	
	@Inject
	private PerInfoCtgDataRepository perInfoCtgDataRepository;
	
	@Inject
	private PerInfoItemDataRepository perInfoItemDataRepository;
	
	@Inject
	private EmpInfoItemDataRepository empInfoItemDataRepository;
	

	public List<PerInfoCtgFullDto> getAllPerInfoCtg(String employeeIdSelected) {
		String companyId = AppContexts.user().companyId();
		String empIdCurrentLogin = AppContexts.user().employeeId();
		String roleIdOfLogin = AppContexts.user().roles().forCompanyAdmin();

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
		if(perInfoCtg.getIsFixed() == IsFixed.NOT_FIXED) return getListInfoCtgByCtgIdAndSidTypeOptional(perInfoCtg, query);
		if(perInfoCtg.getCategoryType() == CategoryType.SINGLEINFO) return new ArrayList<>();
		query.setCategoryCode(perInfoCtg.getCategoryCode().v());
		return layoutingProcessor.getListFirstItems(query);
	}
	
	private List<ComboBoxObject> getListInfoCtgByCtgIdAndSidTypeOptional(PersonInfoCategory perInfoCtg, PeregQuery query){
		if(perInfoCtg.getCategoryType() == CategoryType.SINGLEINFO) return new ArrayList<>();
		else if(perInfoCtg.getCategoryType() == CategoryType.MULTIINFO){ return null;}
		else return getListInfoTypeHist(perInfoCtg, query);
	}

	private List<ComboBoxObject> getListInfoTypeHist(PersonInfoCategory perInfoCtg, PeregQuery query){
		// app context
		String contractCode = AppContexts.user().contractCode();
		String companyId = AppContexts.user().companyId();
		String loginEmpId = AppContexts.user().employeeId();
		String roleId = AppContexts.user().roles().forCompanyAdmin();
		// get item def
		List<PersonInfoItemDefinition> lstItemDef = perInfoCtgDomainService
				.getPerItemDef(new ParamForGetPerItem(perInfoCtg, query.getInfoId(), roleId == null ? "" : roleId,
															companyId, contractCode, loginEmpId.equals(query.getEmployeeId())));
		DateRangeItem dateRangeItem = perInfoCtgRepositoty.getDateRangeItemByCategoryId(perInfoCtg.getPersonInfoCategoryId());
		return perInfoCtg.getPersonEmployeeType() == PersonEmployeeType.EMPLOYEE ?
				getEmpInfoTypeHist(lstItemDef, dateRangeItem, query) : getPerInfoTypeHist(lstItemDef, dateRangeItem, query);
		
//		
//		Optional<PersonInfoItemDefinition> period = lstItemDef.stream().filter(x -> { return x.getPerInfoItemDefId().equals(dateRangeItem.getDateRangeItemId());}).findFirst();
//		if(!period.isPresent()) return new ArrayList<>();
//		List<String> timePerInfoItemDefIds = ((SetItem)period.get().getItemTypeState()).getItems();
//		return perInfoCtg.getPersonEmployeeType() == PersonEmployeeType.EMPLOYEE ?
//				getEmpInfoTypeHist() : getPerInfoTypeHist(query, timePerInfoItemDefIds);
	}
	
	private List<ComboBoxObject> getPerInfoTypeHist(List<PersonInfoItemDefinition> lstItemDef, DateRangeItem dateRangeItem, PeregQuery query){
		List<ComboBoxObject> lstComboBoxObject = new ArrayList<>();	
		// get EmpInfoCtgData to get record id
		List<PerInfoCtgData> lstPerInfoCtgData = perInfoCtgDataRepository.getByPerIdAndCtgId(query.getEmployeeId(), query.getCategoryId());
		if(lstPerInfoCtgData.size() == 0) return lstComboBoxObject;
		
		//get lst item data and filter base on item def
		List<PersonInfoItemData> lstValidItemData = new ArrayList<>();
		for(PerInfoCtgData empInfoCtgData :  lstPerInfoCtgData){
			//get option value value combo box
			String value = empInfoCtgData.getRecordId();
			//get option text
			Optional<PersonInfoItemDefinition> period = lstItemDef.stream().filter(x -> { return x.getPerInfoItemDefId().equals(dateRangeItem.getDateRangeItemId());}).findFirst();
			if(!period.isPresent()) break;
			List<String> timePerInfoItemDefIds = ((SetItem)period.get().getItemTypeState()).getItems();
			
			List<String> optionText = new ArrayList<>();
			List<PersonInfoItemData> lstItemData = perInfoItemDataRepository.getAllInfoItemByRecordId(empInfoCtgData.getRecordId());
			lstValidItemData = lstItemData.stream().filter(
					item -> (lstItemDef.stream().filter(i -> i.getPerInfoItemDefId().equals(item.getPerInfoItemDefId())).count()) > 0
					).collect(Collectors.toList());
			for(PersonInfoItemData itemData : lstValidItemData){
				if(timePerInfoItemDefIds.contains(itemData.getPerInfoItemDefId()))
					optionText.add(itemData.getPerInfoItemDefId());
				lstComboBoxObject.add(ComboBoxObject.toComboBoxObject(value, optionText.get(0), optionText.get(0)));
			}
			
		}
		return lstComboBoxObject;
	}
	//[b49ae8bd-f423-46ff-8cfc-ea0fecce2b54, f1ad76db-3cb3-4b79-ae29-86804bcc0865]
	private List<ComboBoxObject> getEmpInfoTypeHist(List<PersonInfoItemDefinition> lstItemDef, DateRangeItem dateRangeItem, PeregQuery query){
		List<ComboBoxObject> lstComboBoxObject = new ArrayList<>();	
		// get EmpInfoCtgData to get record id
		List<EmpInfoCtgData> lstEmpInfoCtgData = emInfoCtgDataRepository.getByEmpIdAndCtgId(query.getEmployeeId(), query.getCategoryId());
		if(lstEmpInfoCtgData.size() == 0) return lstComboBoxObject;
		
		//get lst item data and filter base on item def
		List<EmpInfoItemData> lstValidItemData = new ArrayList<>();
		for(EmpInfoCtgData empInfoCtgData :  lstEmpInfoCtgData){
			//get option value value combo box
			String value = empInfoCtgData.getRecordId();
			//get option text
			Optional<PersonInfoItemDefinition> period = lstItemDef.stream().filter(x -> { return x.getPerInfoItemDefId().equals(dateRangeItem.getDateRangeItemId());}).findFirst();
			if(!period.isPresent()) break;
			List<String> timePerInfoItemDefIds = ((SetItem)period.get().getItemTypeState()).getItems();
			
			List<String> optionText = new ArrayList<>();
			List<EmpInfoItemData> lstItemData = empInfoItemDataRepository.getAllInfoItemByRecordId(empInfoCtgData.getRecordId());
			lstValidItemData = lstItemData.stream().filter(
					item -> (lstItemDef.stream().filter(i -> i.getPerInfoItemDefId().equals(item.getPerInfoDefId())).count()) > 0
					).collect(Collectors.toList());
			for(EmpInfoItemData itemData : lstItemData){
				if(timePerInfoItemDefIds.contains(itemData.getPerInfoDefId()))
					optionText.add(itemData.getDataState().getDateValue().toString());				
			}
			lstComboBoxObject.add(ComboBoxObject.toComboBoxObject(value, optionText.get(0), optionText.get(1)));
		}
		return lstComboBoxObject;
	}

}
