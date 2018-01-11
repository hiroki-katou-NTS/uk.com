package nts.uk.ctx.pereg.app.find.employee.category;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDataMngInfo;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDataMngInfoRepository;
import nts.uk.ctx.pereg.app.find.layout.dto.EmpMaintLayoutDto;
import nts.uk.ctx.pereg.app.find.person.category.PerInfoCategoryFinder;
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
	
	@Inject 
	private EmployeeDataMngInfoRepository employeeRepository;
	
	@Inject
	private PerInfoCategoryFinder perInfoCategoryFinder;
	
	/**
	 * Get all category by selected employee
	 * @author xuan vinh
	 * 
	 * @param employeeIdSelected
	 * @return List<Category Dto>
	 */
	public List<PerInfoCtgFullDto> getAllPerInfoCtg(String selectedEmployeeIdId) {
		//App contexts
		String companyId = AppContexts.user().companyId();
		String empIdCurrentLogin = AppContexts.user().employeeId();
		String roleIdOfLogin = AppContexts.user().roles().forPersonalInfo();
		//String roleIdOfLogin = "99900000-0000-0000-0000-000000000001";

		// get list Category
		List<PersonInfoCategory> listCategory = perInfoCategoryRepositoty.getAllPerInfoCtg(companyId);

		boolean isSelf = selectedEmployeeIdId.equals(empIdCurrentLogin);
		
		// get category domain list
		List<PersonInfoCategory> returnList = listCategory.stream().filter(x -> {
			Optional<PersonInfoCategoryAuth> perInfoCtgAuth = personInfoCategoryAuthRepository
					.getDetailPersonCategoryAuthByPId(roleIdOfLogin, x.getPersonInfoCategoryId());
			if (!perInfoCtgAuth.isPresent())
				return false;
			return isSelf ? perInfoCtgAuth.get().getAllowPersonRef() == PersonInfoPermissionType.YES
					: perInfoCtgAuth.get().getAllowOtherRef() == PersonInfoPermissionType.YES;
		}).collect(Collectors.toList());
		
		//convert to dto and return
		return returnList.stream()
				.map(x -> new PerInfoCtgFullDto(x.getPersonInfoCategoryId(), x.getCategoryCode().v(),
						x.getCategoryName().v(), x.getPersonEmployeeType().value, x.getIsAbolition().value,
						x.getCategoryType().value, x.getIsFixed().value))
				.collect(Collectors.toList());
	};
	
	/**
	 * get list information by category and employee id
	 * 
	 * @author xuan vinh
	 * @param query
	 * @return list<combobox object>
	 */
	public List<ComboBoxObject> getListInfoCtgByCtgIdAndSid(PeregQuery query){
		// app contexts
		String contractCode = AppContexts.user().contractCode();
		String roleId = AppContexts.user().roles().forPersonalInfo();
		//String roleId = "99900000-0000-0000-0000-000000000001";

		//get category
		PersonInfoCategory perInfoCtg = perInfoCtgRepositoty.getPerInfoCategory(query.getCategoryId(), contractCode)
				.get();
		if (!perInfoCategoryFinder.checkPerInfoCtgAuth(query.getEmployeeId(), perInfoCtg.getPersonInfoCategoryId(), roleId)) {
			return new ArrayList<>();
		}
		if (perInfoCtg.getCategoryType() == CategoryType.SINGLEINFO)
			return new ArrayList<>();
		query.setCtgType(perInfoCtg.getCategoryType().value);
		//get combobox object
		if (perInfoCtg.getIsFixed() == IsFixed.NOT_FIXED)
			return getInfoListOfOptionalCtg(perInfoCtg, query);		
		query.setCategoryCode(perInfoCtg.getCategoryCode().v());
		return layoutingProcessor.getListFirstItems(query);
	}
	
	private List<ComboBoxObject> getInfoListOfOptionalCtg(PersonInfoCategory perInfoCtg, PeregQuery query){
		if(perInfoCtg.getCategoryType() == CategoryType.SINGLEINFO) return new ArrayList<>();
		else if(perInfoCtg.getCategoryType() == CategoryType.MULTIINFO){ return null;}
		else return getInfoListHistType(perInfoCtg, query);
	}

	private List<ComboBoxObject> getInfoListHistType(PersonInfoCategory perInfoCtg, PeregQuery query) {
		// app contexts
		String contractCode = AppContexts.user().contractCode();
		String companyId = AppContexts.user().companyId();
		String loginEmpId = AppContexts.user().employeeId();
		String roleId = AppContexts.user().roles().forPersonalInfo();

		// get item def
		List<PersonInfoItemDefinition> lstItemDef = perInfoCtgDomainService
				.getPerItemDef(new ParamForGetPerItem(perInfoCtg, query.getInfoId(), roleId == null ? "" : roleId,
						companyId, contractCode, loginEmpId.equals(query.getEmployeeId())));
		DateRangeItem dateRangeItem = perInfoCtgRepositoty
				.getDateRangeItemByCategoryId(perInfoCtg.getPersonInfoCategoryId());
		Optional<PersonInfoItemDefinition> period = lstItemDef.stream().filter(x -> {
			return x.getPerInfoItemDefId().equals(dateRangeItem.getDateRangeItemId());
		}).findFirst();
		if (!period.isPresent())
			return new ArrayList<>();
		List<String> timePerInfoItemDefIds = ((SetItem) period.get().getItemTypeState()).getItems();
		return perInfoCtg.getPersonEmployeeType() == PersonEmployeeType.EMPLOYEE
				? getHistInfoEmployeeType(timePerInfoItemDefIds, query) : getHistInfoPersonType(timePerInfoItemDefIds, query);
	}
	
	private List<ComboBoxObject> getHistInfoPersonType(List<String> timePerInfoItemDefIds, PeregQuery query){
		List<ComboBoxObject> lstComboBoxObject = new ArrayList<>();	
		
		EmployeeDataMngInfo employee = employeeRepository.findByEmpId(query.getEmployeeId()).get();
		// get EmpInfoCtgData to get record id
		List<PerInfoCtgData> lstPerInfoCtgData = perInfoCtgDataRepository.getByPerIdAndCtgId(employee.getPersonId(), query.getCategoryId());
		if(lstPerInfoCtgData.size() == 0) return lstComboBoxObject;
		
		//get lst item data and filter base on item def
		List<PersonInfoItemData> lstValidItemData = new ArrayList<>();
		for(PerInfoCtgData empInfoCtgData :  lstPerInfoCtgData){
			//get option value value combo box
			String value = empInfoCtgData.getRecordId();
			//get option text
			List<String> optionText = new ArrayList<>();
			List<PersonInfoItemData> lstPerInfoCtgItemData = perInfoItemDataRepository.getAllInfoItemByRecordId(empInfoCtgData.getRecordId());
			if(lstPerInfoCtgItemData.size() != 0) {
				for(PersonInfoItemData itemData : lstValidItemData){
					if(timePerInfoItemDefIds.contains(itemData.getPerInfoItemDefId()))
					{
						Object dateValue = itemData.getDataState().getDateValue();
						optionText.add(dateValue == null ? "" : dateValue.toString());	
					}
				}
				sortDate(optionText, query);
				if(optionText.size() > 0)
					lstComboBoxObject.add(ComboBoxObject.toComboBoxObject(value, optionText.get(0), optionText.get(1)));
			}
		}
		return lstComboBoxObject;
	}
	
	private List<ComboBoxObject> getHistInfoEmployeeType(List<String> timePerInfoItemDefIds, PeregQuery query){
		// get EmpInfoCtgData to get record id
		List<EmpInfoCtgData> lstEmpInfoCtgData = emInfoCtgDataRepository.getByEmpIdAndCtgId(query.getEmployeeId(), query.getCategoryId());
		if(lstEmpInfoCtgData.size() == 0) return new ArrayList<>();
		
		Map<String, ComboBoxObject> comboBoxs = new HashMap<>();
		for(EmpInfoCtgData empInfoCtgData :  lstEmpInfoCtgData){
			//get option value value combo box
			String value = empInfoCtgData.getRecordId();
			//get option text
			List<String> optionText = new ArrayList<>();
			List<EmpInfoItemData> lstEmpInfoCtgItemData = empInfoItemDataRepository.getAllInfoItemByRecordId(empInfoCtgData.getRecordId());
			if(lstEmpInfoCtgItemData.size() != 0) {
				for(EmpInfoItemData itemData : lstEmpInfoCtgItemData){
					if(timePerInfoItemDefIds.contains(itemData.getPerInfoDefId())){
						Object dateValue = itemData.getDataState().getDateValue();
						optionText.add(dateValue == null ? "" : dateValue.toString());	
					}
				}
				sortDate(optionText, query);
				if(optionText.size() > 0)
					comboBoxs.put(optionText.get(0), ComboBoxObject.toComboBoxObject(value, optionText.get(0), optionText.get(1)));
			}
		}
		return sortComboBox(comboBoxs);
	}
	private void sortDate(List<String> optionText, PeregQuery query){
		optionText.sort((a, b) -> {
			if(a.equals("")) return 1;
			if(b.equals("")) return 0;
			GeneralDate start = GeneralDate.fromString(a, "yyyy/MM/dd");
			GeneralDate end = GeneralDate.fromString(b, "yyyy/MM/dd");
			return start.compareTo(end);
		});
	
		optionText.set(1, dateToString(optionText.get(1), query));
	}
	private String dateToString(String dateValue, PeregQuery query){
		return GeneralDate.max().equals(GeneralDate.fromString(dateValue, "yyyy/MM/dd")) && query.getCtgType() == 3? "" :dateValue;
	}
	
	private List<ComboBoxObject> sortComboBox(Map<String, ComboBoxObject> comboBoxs){
		List<String> strDates = comboBoxs.entrySet().stream().map(x -> x.getKey()).collect(Collectors.toList());
		strDates.sort((a, b) -> {
			GeneralDate before = GeneralDate.fromString(a, "yyyy/MM/dd");
			GeneralDate after = GeneralDate.fromString(b, "yyyy/MM/dd");
			return after.compareTo(before);
		});
		return strDates.stream().map(x -> comboBoxs.get(x)).collect(Collectors.toList());
	}
}
