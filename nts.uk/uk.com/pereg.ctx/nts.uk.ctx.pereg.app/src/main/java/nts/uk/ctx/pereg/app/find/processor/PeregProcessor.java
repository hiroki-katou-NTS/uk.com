package nts.uk.ctx.pereg.app.find.processor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.app.find.dailyperformanceformat.businesstype.BusinessTypeDto;
import nts.uk.ctx.pereg.app.find.common.InitDefaultValue;
import nts.uk.ctx.pereg.app.find.common.LayoutControlComBoBox;
import nts.uk.ctx.pereg.app.find.common.MappingFactory;
import nts.uk.ctx.pereg.app.find.common.StampCardLength;
import nts.uk.ctx.pereg.app.find.layout.dto.EmpMaintLayoutDto;
import nts.uk.ctx.pereg.app.find.layoutdef.classification.ActionRole;
import nts.uk.ctx.pereg.app.find.layoutdef.classification.LayoutPersonInfoClsDto;
import nts.uk.ctx.pereg.app.find.layoutdef.classification.LayoutPersonInfoValueDto;
import nts.uk.ctx.pereg.app.find.person.category.PerInfoCategoryFinder;
import nts.uk.ctx.pereg.app.find.person.category.PerInfoCtgFullDto;
import nts.uk.ctx.pereg.app.find.person.info.item.PerInfoItemDefForLayoutDto;
import nts.uk.ctx.pereg.app.find.person.info.item.PerInfoItemDefForLayoutFinder;
import nts.uk.ctx.pereg.dom.person.additemdata.category.EmInfoCtgDataRepository;
import nts.uk.ctx.pereg.dom.person.additemdata.category.EmpInfoCtgData;
import nts.uk.ctx.pereg.dom.person.additemdata.item.EmpInfoItemDataRepository;
import nts.uk.ctx.pereg.dom.person.info.category.CategoryType;
import nts.uk.ctx.pereg.dom.person.info.category.PerInfoCategoryRepositoty;
import nts.uk.ctx.pereg.dom.person.info.category.PersonInfoCategory;
import nts.uk.ctx.pereg.dom.person.info.item.PerInfoItemDefRepositoty;
import nts.uk.ctx.pereg.dom.person.info.item.PersonInfoItemDefinition;
import nts.uk.ctx.pereg.dom.person.layout.classification.LayoutItemType;
import nts.uk.ctx.pereg.dom.person.personinfoctgdata.categor.PerInfoCtgData;
import nts.uk.ctx.pereg.dom.person.personinfoctgdata.categor.PerInfoCtgDataRepository;
import nts.uk.ctx.pereg.dom.person.personinfoctgdata.item.PerInfoItemDataRepository;
import nts.uk.ctx.pereg.dom.roles.auth.category.PersonInfoAuthType;
import nts.uk.ctx.pereg.dom.roles.auth.item.PersonInfoItemAuth;
import nts.uk.ctx.pereg.dom.roles.auth.item.PersonInfoItemAuthRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;
import nts.uk.shr.pereg.app.find.PeregQuery;
import nts.uk.shr.pereg.app.find.dto.OptionalItemDataDto;
import nts.uk.shr.pereg.app.find.dto.PeregDto;

@Stateless
public class PeregProcessor {
	@Inject
	private PerInfoCategoryFinder perInfoCategoryFinder;

	@Inject
	private PerInfoItemDefForLayoutFinder itemForLayoutFinder;

	@Inject
	private EmInfoCtgDataRepository empInCtgDataRepo;

	@Inject
	private EmpInfoItemDataRepository empInfoItemDataRepository;

	@Inject
	private PerInfoItemDataRepository perInfoItemDataRepository;

	@Inject
	private PerInfoCtgDataRepository perInCtgDataRepo;

	@Inject
	private LayoutingProcessor layoutingProcessor;

	@Inject
	private PerInfoCategoryRepositoty perInfoCtgRepositoty;

	@Inject
	private PerInfoItemDefRepositoty perItemRepo;

	@Inject
	private PersonInfoItemAuthRepository itemAuthRepo;

	@Inject
	private InitDefaultValue initDefaultValue;

	@Inject
	private StampCardLength stampCardLength;

	@Inject
	private LayoutControlComBoBox layoutControlComboBox;

	/**
	 * get person information category and it's children (Hiển thị category và
	 * danh sách tab category con của nó)
	 * 
	 * @param ctgId
	 * @return list PerCtgInfo: cha va danh sach con
	 */
	public List<PerInfoCtgFullDto> getCtgTab(String ctgId) {
		String contractCode = AppContexts.user().contractCode();
		String companyId = AppContexts.user().companyId();
		PersonInfoCategory perInfoCtg = perInfoCtgRepositoty.getPerInfoCategory(ctgId, contractCode).get();
		List<PersonInfoCategory> lstPerInfoCtg = new ArrayList<>();
		lstPerInfoCtg = perInfoCtgRepositoty.getPerInfoCtgByParentCdWithOrder(perInfoCtg.getCategoryCode().v(),
				contractCode, companyId, true);
		lstPerInfoCtg.add(0, perInfoCtg);
		return lstPerInfoCtg.stream()
				.map(x -> new PerInfoCtgFullDto(x.getPersonInfoCategoryId(), x.getCategoryCode().v(),
						x.getCategoryParentCode().v(), x.getCategoryName().v(), x.getPersonEmployeeType().value,
						x.getIsAbolition().value, x.getCategoryType().value, x.getIsFixed().value))
				.collect(Collectors.toList());
	}

	public EmpMaintLayoutDto getCategoryDetail(PeregQuery query) {
		// app context
		LoginUserContext loginUser = AppContexts.user();
		String contractCode = loginUser.contractCode();
		String loginEmpId = loginUser.employeeId();
		String roleId = loginUser.roles().forPersonalInfo();

		String employeeId = query.getEmployeeId();
		String categoryId = query.getCategoryId();
		boolean isSelfAuth = loginEmpId.equals(employeeId);

		// get category
		PersonInfoCategory perInfoCtg = perInfoCtgRepositoty.getPerInfoCategory(categoryId, contractCode).get();

		// get PerInfoItemDefForLayoutDto
		// check per info auth
		if (!perInfoCategoryFinder.checkCategoryAuth(query, perInfoCtg, roleId)) {
			return new EmpMaintLayoutDto();
		}
		
		// map PersonInfoItemDefinition →→ PerInfoItemDefForLayoutDto
		List<PerInfoItemDefForLayoutDto> lstPerInfoItemDefForLayout = getPerItemDefForLayout(perInfoCtg, contractCode,
				roleId, employeeId, isSelfAuth);
		if (lstPerInfoItemDefForLayout.isEmpty()) {
			return new EmpMaintLayoutDto();
		}

		List<LayoutPersonInfoClsDto> classItemList = getDataClassItemList(query, perInfoCtg, lstPerInfoItemDefForLayout);

		// set default value
		initDefaultValue.setDefaultValue(classItemList);

		// special process with category CS00069 item IS00779. change string
		// length
		stampCardLength.updateLength(perInfoCtg, classItemList);

		return new EmpMaintLayoutDto(classItemList);

	}

	private List<LayoutPersonInfoClsDto> getDataClassItemList(PeregQuery query, PersonInfoCategory perInfoCtg,
			List<PerInfoItemDefForLayoutDto> lstPerInfoItemDef) {
		
		// combo-box sẽ lấy dựa theo các ngày startDate của từng category
		GeneralDate comboBoxStandardDate = GeneralDate.today();
		
		List<LayoutPersonInfoClsDto> classItemList = creatClassItemList(lstPerInfoItemDef, perInfoCtg);

		if (perInfoCtg.isFixed()) {

			PeregDto peregDto = layoutingProcessor.findSingle(query);

			if (peregDto != null) {
				// map data
				MappingFactory.mapListItemClass(peregDto, classItemList);
				
				Map<String, Object> itemValueMap = MappingFactory.getFullDtoValue(peregDto);
				List<String> standardDateItemCodes = Arrays.asList("IS00020", "IS00077", "IS00082", "IS00119", "IS00781");
				for (String itemCode : standardDateItemCodes) {
					if (itemValueMap.containsKey(itemCode)) {
						comboBoxStandardDate = (GeneralDate) itemValueMap.get(itemCode);
						break;
					}
				}
				//liên quan đến bug #102480, sửa script categoryType = 6 => 3
//				 //edit with category CS00021 勤務種別 change type of category when history item is latest
//				if (query.getCategoryCode().equals("CS00021")) {
//					BusinessTypeDto businessTypeDto = (BusinessTypeDto) peregDto.getDomainDto();
//					if (businessTypeDto.isLatestHistory()) {
//						changeCategoryType(classItemList, CategoryType.NODUPLICATEHISTORY);
//					}
//				}
			}

		} else {
			switch (perInfoCtg.getCategoryType()) {
			case SINGLEINFO:
				setOptionDataSingleCategory(perInfoCtg, classItemList, query);
				break;
			default:
				setOptionDataWithRecordId(perInfoCtg, classItemList, query);
				break;
			}

		}
		//liên quan đến bug #102480, sửa script categoryType = 6 => 3
//		// edit with category CS00021 勤務種別 change type of category when create new
//		if (query.getCategoryCode().equals("CS00021") && query.getInfoId() == null) {
//			changeCategoryType(classItemList, CategoryType.NODUPLICATEHISTORY);
//		}
		
		// get Combo-Box List
		layoutControlComboBox.getComboBoxListForSelectionItems(query.getEmployeeId(), perInfoCtg, classItemList,
				comboBoxStandardDate);

		return classItemList;
	}
	
//liên quan đến bug #102480, sửa script categoryType = 6 => 3
//	private void changeCategoryType(List<LayoutPersonInfoClsDto> classItemList, CategoryType type) {
//		classItemList.forEach(classItem -> {
//			classItem.getItems().forEach(item -> {
//				LayoutPersonInfoValueDto valueItem = (LayoutPersonInfoValueDto) item;
//				valueItem.setCtgType(type.value);
//			});
//		});
//	}

	private List<LayoutPersonInfoClsDto> creatClassItemList(List<PerInfoItemDefForLayoutDto> lstClsItem, PersonInfoCategory perInfoCtg) {
		return lstClsItem.stream().map(item -> {
			LayoutPersonInfoClsDto layoutPerInfoClsDto = new LayoutPersonInfoClsDto();
			layoutPerInfoClsDto.setPersonInfoCategoryID(item.getPerInfoCtgId());
			layoutPerInfoClsDto.setPersonInfoCategoryCD(item.getPerInfoCtgCd());
			layoutPerInfoClsDto.setLayoutItemType(LayoutItemType.ITEM);
			layoutPerInfoClsDto.setClassName(item.getItemName());
			layoutPerInfoClsDto.setDispOrder(item.getDispOrder());
			layoutPerInfoClsDto.getItems().add(LayoutPersonInfoValueDto.initData(item, perInfoCtg));
			if (item.getItemTypeState().getItemType() != 2) {
				item.getLstChildItemDef().forEach(childItem -> {
					layoutPerInfoClsDto.setDispOrder(childItem.getDispOrder());
					layoutPerInfoClsDto.getItems().add(LayoutPersonInfoValueDto.initData(childItem, perInfoCtg));
				});
			}
			return layoutPerInfoClsDto;
		}).collect(Collectors.toList());
	}

	private void setOptionDataSingleCategory(PersonInfoCategory perInfoCtg, List<LayoutPersonInfoClsDto> classItemList,
			PeregQuery query) {
		if (perInfoCtg.isEmployeeType()) {
			// employee option data
			List<EmpInfoCtgData> empInfoCtgDatas = empInCtgDataRepo.getByEmpIdAndCtgId(query.getEmployeeId(),
					perInfoCtg.getPersonInfoCategoryId());
			if (!empInfoCtgDatas.isEmpty()) {
				String recordId = empInfoCtgDatas.get(0).getRecordId();
				getAndMapEmpOptionItem(recordId, classItemList);
			}
		} else {
			// person option data
			List<PerInfoCtgData> perInfoCtgDatas = perInCtgDataRepo.getByPerIdAndCtgId(query.getPersonId(),
					perInfoCtg.getPersonInfoCategoryId());

			if (!perInfoCtgDatas.isEmpty()) {
				String recordId = perInfoCtgDatas.get(0).getRecordId();
				getAndMapPerOptionItem(recordId, classItemList);
			}
		}

	}

	private void setOptionDataWithRecordId(PersonInfoCategory perInfoCtg, List<LayoutPersonInfoClsDto> classItemList,
			PeregQuery query) {
		if (perInfoCtg.isEmployeeType()) {
			// employee option data
			getAndMapEmpOptionItem(query.getInfoId(), classItemList);
		} else {
			// person option data
			getAndMapPerOptionItem(query.getInfoId(), classItemList);
		}

	}

	private void getAndMapEmpOptionItem(String recordId, List<LayoutPersonInfoClsDto> classItemList) {
		List<OptionalItemDataDto> empOptionItemData = empInfoItemDataRepository.getAllInfoItemByRecordId(recordId)
				.stream().map(x -> x.genToPeregDto()).collect(Collectors.toList());
		MappingFactory.matchOptionalItemData(recordId, classItemList, empOptionItemData);
	}

	private void getAndMapPerOptionItem(String recordId, List<LayoutPersonInfoClsDto> classItemList) {
		List<OptionalItemDataDto> perOptionItemData = perInfoItemDataRepository.getAllInfoItemByRecordId(recordId)
				.stream().map(x -> x.genToPeregDto()).collect(Collectors.toList());
		MappingFactory.matchOptionalItemData(recordId, classItemList, perOptionItemData);
	}

	private List<PerInfoItemDefForLayoutDto> getPerItemDefForLayout(PersonInfoCategory category, String contractCode,
			String roleId, String employeeId, boolean isSelf) {

		// get per info item def with order
		List<PersonInfoItemDefinition> fullItemDefinitionList = perItemRepo
				.getAllItemDefByCategoryId(category.getPersonInfoCategoryId(), contractCode);

		List<PersonInfoItemDefinition> parentItemDefinitionList = fullItemDefinitionList.stream()
				.filter(item -> item.haveNotParentCode()).collect(Collectors.toList());

		List<PerInfoItemDefForLayoutDto> lstReturn = new ArrayList<>();

		Map<String, PersonInfoItemAuth> mapItemAuth = itemAuthRepo
				.getAllItemAuth(roleId, category.getPersonInfoCategoryId()).stream()
				.collect(Collectors.toMap(e -> e.getPersonItemDefId(), e -> e));

		for (int i = 0; i < parentItemDefinitionList.size(); i++) {
			PersonInfoItemDefinition itemDefinition = parentItemDefinitionList.get(i);

			// check authority
			PersonInfoItemAuth personInfoItemAuth = mapItemAuth.get(itemDefinition.getPerInfoItemDefId());
			if (personInfoItemAuth == null) {
				continue;
			}
			PersonInfoAuthType roleOfItem = isSelf ? personInfoItemAuth.getSelfAuth()
					: personInfoItemAuth.getOtherAuth();

			if (roleOfItem == PersonInfoAuthType.HIDE) {
				continue;
			}

			// convert item-definition to layoutDto
			ActionRole role = roleOfItem == PersonInfoAuthType.REFERENCE ? ActionRole.VIEW_ONLY : ActionRole.EDIT;

			PerInfoItemDefForLayoutDto itemDto = itemForLayoutFinder.createItemLayoutDto(category, itemDefinition, i,
					role);

			// get and convert childrenItems
			List<PerInfoItemDefForLayoutDto> childrenItems = itemForLayoutFinder
					.getChildrenItems(fullItemDefinitionList, category, itemDefinition, i, role);

			itemDto.setLstChildItemDef(childrenItems);

			lstReturn.add(itemDto);
		}

		return lstReturn;
	}

}
