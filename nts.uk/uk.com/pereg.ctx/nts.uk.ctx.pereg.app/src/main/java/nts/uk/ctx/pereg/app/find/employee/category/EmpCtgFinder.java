package nts.uk.ctx.pereg.app.find.employee.category;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDataMngInfo;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDataMngInfoRepository;
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
import nts.uk.ctx.pereg.dom.roles.auth.category.PersonInfoAuthType;
import nts.uk.ctx.pereg.dom.roles.auth.category.PersonInfoCategoryAuth;
import nts.uk.ctx.pereg.dom.roles.auth.category.PersonInfoCategoryAuthRepository;
import nts.uk.ctx.pereg.dom.roles.auth.item.PersonInfoItemAuth;
import nts.uk.ctx.pereg.dom.roles.auth.item.PersonInfoItemAuthRepository;
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

	@Inject
	private PersonInfoItemAuthRepository itemAuth;
	
	@Inject
	private EmployeeDataMngInfoRepository empRepo;

	/**
	 * Get all category by selected employee
	 * 
	 * @author xuan vinh
	 * 
	 * @param employeeIdSelected
	 * @return List<Category Dto>
	 */
	public List<PerInfoCtgFullDto> getAllPerInfoCtg(String selectedEmployeeIdId) {

		// App contexts of login employee
		String loginCompanyId = AppContexts.user().companyId();
		String empIdCurrentLogin = AppContexts.user().employeeId();
		String roleIdOfLogin = AppContexts.user().roles().forPersonalInfo();
		
		boolean isSelf = selectedEmployeeIdId.equals(empIdCurrentLogin);
		String curEmCompanyId = empRepo.findByEmpId(selectedEmployeeIdId).get().getCompanyId();
		
		// get list Category
		List<PersonInfoCategory> listCategory = isSelf ? perInfoCategoryRepositoty.getAllCtgWithAuth(loginCompanyId, roleIdOfLogin, 1, 0, !curEmCompanyId.equals(loginCompanyId)) : 
			perInfoCategoryRepositoty.getAllCtgWithAuth(loginCompanyId, roleIdOfLogin, 0, 1, !curEmCompanyId.equals(loginCompanyId));

		List<PerInfoCtgFullDto> returnDtoList = listCategory.stream()
				.map(x -> new PerInfoCtgFullDto(x.getPersonInfoCategoryId(), x.getCategoryCode().v(),
						x.getCategoryName().v(), x.getPersonEmployeeType().value, x.getIsAbolition().value,
						x.getCategoryType().value, x.getIsFixed().value))
				.collect(Collectors.toList());

		noDuplicateHistoryFilter(returnDtoList, selectedEmployeeIdId);

		// convert to dto and return
		return returnDtoList;

	};

	private List<PerInfoCtgFullDto> noDuplicateHistoryFilter(List<PerInfoCtgFullDto> returnLst,
			String selectedEmployeeIdId) {

		List<PerInfoCtgFullDto> noDupList = returnLst.stream()
				.filter(c -> c.getCategoryType() == CategoryType.NODUPLICATEHISTORY.value).collect(Collectors.toList());

		if (!CollectionUtil.isEmpty(noDupList)) {
			noDupList.forEach(ctg -> {

				// TODO

			});
		}
		return returnLst;
	}

	/**
	 * get list information by category and employee id
	 * 
	 * @author xuan vinh
	 * @param query
	 * @return list<combobox object>
	 */
	public List<ComboBoxObject> getListInfoCtgByCtgIdAndSid(PeregQuery query) {
		// app contexts
		String contractCode = AppContexts.user().contractCode();
		String empIdCurrentLogin = AppContexts.user().employeeId();
		String roleId = AppContexts.user().roles().forPersonalInfo();
		String categoryId = query.getCategoryId();

		String loginCId = AppContexts.user().companyId();
		String viewerCId = (employeeRepository.findByEmpId(query.getEmployeeId()).get()).getCompanyId();
		boolean isSameCom = loginCId.equals(viewerCId);
		// String roleId = "99900000-0000-0000-0000-000000000001";

		// get category
		PersonInfoCategory perInfoCtg = perInfoCtgRepositoty.getPerInfoCategory(categoryId, contractCode)
				.get();
		
		if (perInfoCtg.getCategoryType() == CategoryType.SINGLEINFO) {
			return new ArrayList<>();
		}
		
		if (!perInfoCategoryFinder.checkPerInfoCtgAuth(query.getEmployeeId(), categoryId,
				roleId)) {
			return new ArrayList<>();
		}
		
		// check ctg auth 
		PersonInfoCategoryAuth ctgAuth = personInfoCategoryAuthRepository
				.getDetailPersonCategoryAuthByPId(roleId, categoryId).get();
		boolean isSelf = query.getEmployeeId().equals(empIdCurrentLogin);
		boolean allowCtgAuth = checkRole(ctgAuth, roleId, categoryId, isSelf, isSameCom);
		if(!allowCtgAuth) {
			return new ArrayList<>();	
		}
		
		if(perInfoCtg.getCategoryType() == CategoryType.MULTIINFO) {
			return layoutingProcessor.getListFirstItems(query);
		}
		
		query.setCtgType(perInfoCtg.getCategoryType().value);
		// get combobox object
		List<PersonInfoItemDefinition> lstItemDef = perInfoCtgDomainService
				.getPerItemDef(new ParamForGetPerItem(perInfoCtg, query.getInfoId(), roleId == null ? "" : roleId,
						loginCId, contractCode, empIdCurrentLogin.equals(query.getEmployeeId())));
		Optional<PersonInfoItemDefinition> period;
		if (!perInfoCtg.getCategoryCode().v().equals("CS00003")) {
			DateRangeItem dateRangeItem = perInfoCtgRepositoty
					.getDateRangeItemByCategoryId(categoryId).get();
			period = lstItemDef.stream().filter(x -> x.getPerInfoItemDefId().equals(dateRangeItem.getDateRangeItemId()))
					.findFirst();

		} else {
			period = Optional.of(lstItemDef.get(0));
		}
		
		List<ComboBoxObject> infoList = new ArrayList<>();
		if (perInfoCtg.getIsFixed() == IsFixed.NOT_FIXED) {
			infoList = getInfoListOfOptionalCtg(perInfoCtg, query, period);
		} else {
			query.setCategoryCode(perInfoCtg.getCategoryCode().v());
			infoList = layoutingProcessor.getListFirstItems(query);
		}

		List<ComboBoxObject> resultList = fiterOfContHist(ctgAuth, infoList, roleId, isSelf);
		if(lstItemDef.size() > 0)
			resultList.add(new ComboBoxObject(null, period.get().getItemName().v()));
		return resultList ;
	}
	
	private List<ComboBoxObject> getInfoListOfOptionalCtg(PersonInfoCategory perInfoCtg, PeregQuery query, Optional<PersonInfoItemDefinition> period) {
		if (perInfoCtg.getCategoryType() == CategoryType.SINGLEINFO)
			return new ArrayList<>();
		else if (perInfoCtg.getCategoryType() == CategoryType.MULTIINFO) {
			return null;
		} else
			return getInfoListHistType(perInfoCtg, query, period);
	}

	private List<ComboBoxObject> getInfoListHistType(PersonInfoCategory perInfoCtg, PeregQuery query, Optional<PersonInfoItemDefinition> period) {
		

		// get item def
		
		
		if (!period.isPresent())
			return new ArrayList<>();
		List<String> timePerInfoItemDefIds = ((SetItem) period.get().getItemTypeState()).getItems();
		return perInfoCtg.getPersonEmployeeType() == PersonEmployeeType.EMPLOYEE
				? getHistInfoEmployeeType(timePerInfoItemDefIds, query)
				: getHistInfoPersonType(timePerInfoItemDefIds, query);
		
	}

	private List<ComboBoxObject> getHistInfoPersonType(List<String> timePerInfoItemDefIds, PeregQuery query) {
		List<ComboBoxObject> lstComboBoxObject = new ArrayList<>();

		EmployeeDataMngInfo employee = employeeRepository.findByEmpId(query.getEmployeeId()).get();
		// get EmpInfoCtgData to get record id
		List<PerInfoCtgData> lstPerInfoCtgData = perInfoCtgDataRepository.getByPerIdAndCtgId(employee.getPersonId(),
				query.getCategoryId());
		if (lstPerInfoCtgData.size() == 0)
			return lstComboBoxObject;

		// get lst item data and filter base on item def
		List<PersonInfoItemData> lstValidItemData = new ArrayList<>();
		for (PerInfoCtgData empInfoCtgData : lstPerInfoCtgData) {
			// get option value value combo box
			String value = empInfoCtgData.getRecordId();
			// get option text
			List<String> optionText = new ArrayList<>();
			List<PersonInfoItemData> lstPerInfoCtgItemData = perInfoItemDataRepository
					.getAllInfoItemByRecordId(empInfoCtgData.getRecordId());
			if (lstPerInfoCtgItemData.size() != 0) {
				for (PersonInfoItemData itemData : lstValidItemData) {
					if (timePerInfoItemDefIds.contains(itemData.getPerInfoItemDefId())) {
						Object dateValue = itemData.getDataState().getDateValue();
						optionText.add(dateValue == null ? "" : dateValue.toString());
					}
				}
				sortDate(optionText, query);
				if (optionText.size() > 0)
					lstComboBoxObject.add(ComboBoxObject.toComboBoxObject(value, optionText.get(0), optionText.get(1)));
			}
		}
		return lstComboBoxObject;
	}

	private List<ComboBoxObject> getHistInfoEmployeeType(List<String> timePerInfoItemDefIds, PeregQuery query) {
		// get EmpInfoCtgData to get record id
		List<EmpInfoCtgData> lstEmpInfoCtgData = emInfoCtgDataRepository.getByEmpIdAndCtgId(query.getEmployeeId(),
				query.getCategoryId());
		if (lstEmpInfoCtgData.size() == 0)
			return new ArrayList<>();

		List<ComboBoxObject> comboBoxs = new ArrayList<>();
		for (EmpInfoCtgData empInfoCtgData : lstEmpInfoCtgData) {
			// get option value value combo box
			String value = empInfoCtgData.getRecordId();
			// get option text
			List<String> optionText = new ArrayList<>();
			List<EmpInfoItemData> lstEmpInfoCtgItemData = empInfoItemDataRepository
					.getAllInfoItemByRecordId(empInfoCtgData.getRecordId());
			if (lstEmpInfoCtgItemData.size() != 0) {
				for (EmpInfoItemData itemData : lstEmpInfoCtgItemData) {
					if (timePerInfoItemDefIds.contains(itemData.getPerInfoDefId())) {
						Object dateValue = itemData.getDataState().getDateValue();
						optionText.add(dateValue == null ? "" : dateValue.toString());
					}
				}
				sortDate(optionText, query);
				if (optionText.size() > 0)
					comboBoxs.add(ComboBoxObject.toComboBoxObject(value, optionText.get(0), optionText.get(1)));
			}
		}
		 sortComboBox(comboBoxs);
		 return comboBoxs;
	}

	private void sortDate(List<String> optionText, PeregQuery query) {
		optionText.sort((a, b) -> {
			if (a.equals("") || b.equals(""))
				return b.length() - a.length();
			GeneralDate start = GeneralDate.fromString(a, "yyyy/MM/dd");
			GeneralDate end = GeneralDate.fromString(b, "yyyy/MM/dd");
			return start.compareTo(end);
		});
		if(!optionText.get(1).equals(""))
			optionText.set(1, dateToString(optionText.get(1), query));
	}

	private String dateToString(String dateValue, PeregQuery query) {
		/*return GeneralDate.max().equals(GeneralDate.fromString(dateValue, "yyyy/MM/dd")) && query.getCtgType() == 3 ? ""
				: dateValue;*/
		return GeneralDate.max().equals(GeneralDate.fromString(dateValue, "yyyy/MM/dd"))? ""
				: dateValue;
	}

	private void sortComboBox(List<ComboBoxObject> comboBoxs) {
		comboBoxs.sort((a, b) -> {
			String aStartDate = a.getOptionText().substring(0, 10);
			String bStartDate = b.getOptionText().substring(0, 10);
			return GeneralDate.fromString(bStartDate, "yyyy/MM/dd").compareTo(GeneralDate.fromString(aStartDate, "yyyy/MM/dd"));
		});
	}

	/**
	 * filter based on past and future history
	 * 
	 * @param infoList
	 * @param roleId
	 * @param isSelf
	 * @return
	 */
	private List<ComboBoxObject> fiterOfContHist(PersonInfoCategoryAuth perInfoCtgAuth, List<ComboBoxObject> infoList,
			String roleId, boolean isSelf) {
		GeneralDate today = GeneralDate.today();
		return infoList.stream().filter(x -> {
			boolean isPast = false;
			
			String enddate = x.getOptionText().substring(13);
			if (!enddate.equals("")) {
				isPast = today.after(GeneralDate.fromString(enddate, "yyyy/MM/dd"));
			}
			if (!isPast) {
				String sDate = x.getOptionText().substring(0, 10);
				GeneralDate startDate = GeneralDate.fromString(sDate, "yyyy/MM/dd");
				if(today.afterOrEquals(startDate)) return true;
				return isSelf ? perInfoCtgAuth.getSelfFutureHisAuth() != PersonInfoAuthType.HIDE
						: perInfoCtgAuth.getOtherFutureHisAuth() != PersonInfoAuthType.HIDE;
			} else {
				return isSelf ? perInfoCtgAuth.getSelfPastHisAuth() != PersonInfoAuthType.HIDE
						: perInfoCtgAuth.getOtherPastHisAuth() != PersonInfoAuthType.HIDE;
			}
		}).collect(Collectors.toList());
	}

	private boolean checkRole(PersonInfoCategoryAuth perInfoCtgAuth, String roleId, String categoryId, boolean isSelf,
			boolean isSameCom) {
		if (perInfoCtgAuth == null)
			return false;
		if (isSelf) {
			if (perInfoCtgAuth.getAllowPersonRef() == PersonInfoPermissionType.YES) {
				List<PersonInfoItemAuth> lstItemAuths = itemAuth.getAllItemAuth(roleId, categoryId);
				return lstItemAuths.stream().filter(item -> item.getSelfAuth().value == 1).collect(Collectors.toList())
						.size() != lstItemAuths.size();
			} else
				return false;
		} else {
			if (!isSameCom) {
				if(perInfoCtgAuth.getAllowOtherCompanyRef() == PersonInfoPermissionType.NO) return false;
			}
			/*
			if (!(perInfoCtgAuth.getAllowOtherCompanyRef() == PersonInfoPermissionType.NO))
				return false;*/
			if (perInfoCtgAuth.getAllowOtherRef() == PersonInfoPermissionType.YES) {
				List<PersonInfoItemAuth> lstItemAuths = itemAuth.getAllItemAuth(roleId, categoryId);
				int hiddenItemsSize =  lstItemAuths.stream().filter(item -> item.getOtherAuth().value == 1).collect(Collectors.toList())
						.size();
				int itemSize = lstItemAuths.size();
				return hiddenItemsSize != itemSize;
			} else
				return false;
		}
	}
	
}
