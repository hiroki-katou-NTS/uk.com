package nts.uk.ctx.pereg.app.find.processor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDataMngInfoRepository;
import nts.uk.ctx.pereg.app.find.common.MappingFactory;
import nts.uk.ctx.pereg.app.find.layout.dto.EmpMaintLayoutDto;
import nts.uk.ctx.pereg.app.find.layoutdef.classification.LayoutPersonInfoClsDto;
import nts.uk.ctx.pereg.app.find.layoutdef.classification.LayoutPersonInfoValueDto;
import nts.uk.ctx.pereg.app.find.person.category.PerInfoCategoryFinder;
import nts.uk.ctx.pereg.app.find.person.category.PerInfoCtgFullDto;
import nts.uk.ctx.pereg.app.find.person.info.item.PerInfoItemDefForLayoutDto;
import nts.uk.ctx.pereg.app.find.person.info.item.PerInfoItemDefForLayoutFinder;
import nts.uk.ctx.pereg.dom.person.ParamForGetPerItem;
import nts.uk.ctx.pereg.dom.person.PerInfoCtgDomainService;
import nts.uk.ctx.pereg.dom.person.additemdata.category.EmInfoCtgDataRepository;
import nts.uk.ctx.pereg.dom.person.additemdata.category.EmpInfoCtgData;
import nts.uk.ctx.pereg.dom.person.additemdata.item.EmpInfoItemDataRepository;
import nts.uk.ctx.pereg.dom.person.info.category.CategoryType;
import nts.uk.ctx.pereg.dom.person.info.category.IsFixed;
import nts.uk.ctx.pereg.dom.person.info.category.PerInfoCategoryRepositoty;
import nts.uk.ctx.pereg.dom.person.info.category.PersonEmployeeType;
import nts.uk.ctx.pereg.dom.person.info.category.PersonInfoCategory;
import nts.uk.ctx.pereg.dom.person.info.item.PersonInfoItemDefinition;
import nts.uk.ctx.pereg.dom.person.layout.classification.LayoutItemType;
import nts.uk.ctx.pereg.dom.person.personinfoctgdata.categor.PerInfoCtgData;
import nts.uk.ctx.pereg.dom.person.personinfoctgdata.categor.PerInfoCtgDataRepository;
import nts.uk.ctx.pereg.dom.person.personinfoctgdata.item.PerInfoItemDataRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.pereg.app.find.PeregQuery;
import nts.uk.shr.pereg.app.find.dto.EmpOptionalDto;
import nts.uk.shr.pereg.app.find.dto.PeregDto;
import nts.uk.shr.pereg.app.find.dto.PersonOptionalDto;

@Stateless
public class PeregProcessor {
	@Inject
	private PerInfoCategoryFinder perInfoCategoryFinder;
	
	@Inject
	private PerInfoCtgDomainService perInfoCtgDomainService;
	
	@Inject
	private PerInfoItemDefForLayoutFinder perInfoItemDefForLayoutFinder;
	
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
	private EmployeeDataMngInfoRepository empRepo;
	
	
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
	
	/**
	 * get data in tab
	 * 
	 * @param query
	 * @return EmpMaintLayoutDto
	 */
	public EmpMaintLayoutDto getCategoryChild(PeregQuery query) {
		return getCategoryDetail(query, true);
	}
	
	/**
	 * get sub detail data in tab
	 * 
	 * @param query
	 * @return EmpMaintLayoutDto
	 */
	public EmpMaintLayoutDto getSubDetailInCtgChild(PeregQuery query) {
		return getCategoryDetail(query, false);
	}
	
	/**
	 * get detail data in tab
	 * 
	 * @param query - params received from client
	 * @param isMainDetail - to detect detail tab or sub detail in tab
	 * @return EmpMaintLayoutDto
	 */
	private EmpMaintLayoutDto getCategoryDetail(PeregQuery query, boolean isMainDetail) {
		// app context
		String contractCode = AppContexts.user().contractCode();
		String companyId = AppContexts.user().companyId();
		String loginEmpId = AppContexts.user().employeeId();
		String roleId = AppContexts.user().roles().forPersonalInfo();
		//String roleId = "99900000-0000-0000-0000-000000000001";

		// get Person ID
		query.setPersonId(empRepo.findByEmpId(query.getEmployeeId()).get().getPersonId());

		// get category
		PersonInfoCategory perInfoCtg = perInfoCtgRepositoty.getPerInfoCategory(query.getCategoryId(), contractCode)
				.get();
		query.setCategoryCode(perInfoCtg.getCategoryCode().v());
		if (!isMainDetail) {
			query.setCategoryCode(perInfoCtg.getCategoryCode().v() + "SD");
		}

		// get PerInfoItemDefForLayoutDto
		// check per info auth
		if (!perInfoCategoryFinder.checkPerInfoCtgAuth(query.getEmployeeId(), perInfoCtg.getPersonInfoCategoryId(), roleId)) {
			return new EmpMaintLayoutDto();
		}

		// get item definition
		ParamForGetPerItem getItemDefParam = new ParamForGetPerItem(perInfoCtg, query.getInfoId(),
				roleId == null ? "" : roleId, companyId, contractCode, loginEmpId.equals(query.getEmployeeId()));
		List<PersonInfoItemDefinition> lstItemDef = perInfoCtgDomainService.getPerItemDef(getItemDefParam);
		
		if (lstItemDef.size() == 0) {
			return new EmpMaintLayoutDto();
		}
		
		// map PersonInfoItemDefinition →→ PerInfoItemDefForLayoutDto
		List<PerInfoItemDefForLayoutDto> lstPerInfoItemDefForLayout = new ArrayList<>();
		for (int i = 0; i < lstItemDef.size(); i++) {
			PerInfoItemDefForLayoutDto perInfoItemDefForLayoutDto = perInfoItemDefForLayoutFinder
					.createFromDomain(query.getEmployeeId(), perInfoCtg.getCategoryType().value, lstItemDef.get(i), perInfoCtg.getCategoryCode().v(), i, roleId);
			if (perInfoItemDefForLayoutDto != null)
				lstPerInfoItemDefForLayout.add(perInfoItemDefForLayoutDto);
		}

		EmpMaintLayoutDto empMaintLayoutDto = new EmpMaintLayoutDto();
		
		List<LayoutPersonInfoClsDto> classItemList = getClassItemList(query, perInfoCtg, lstPerInfoItemDefForLayout);
		empMaintLayoutDto.setClassificationItems(classItemList);
		
		return empMaintLayoutDto;
	}
	
	/**
	 * set data in tab
	 * 
	 * @param empMaintLayoutDto
	 * @param query
	 * @param perInfoCtg
	 * @param lstPerInfoItemDef
	 */
	private List<LayoutPersonInfoClsDto> getClassItemList(PeregQuery query,
			PersonInfoCategory perInfoCtg, List<PerInfoItemDefForLayoutDto> lstPerInfoItemDef) {

		List<LayoutPersonInfoClsDto> classItemList = creatClassItemList(lstPerInfoItemDef);
		if(perInfoCtg.getCategoryType() != CategoryType.SINGLEINFO
				&& query.getInfoId() == null && query.getStandardDate() == null)
			return classItemList;
		if (perInfoCtg.getIsFixed() == IsFixed.FIXED) {
			// get peregDto
			PeregDto peregDto = layoutingProcessor.findSingle(query);
			if (peregDto != null) {
				// map data
				MappingFactory.mapListItemClass(peregDto, classItemList);
			}
		} else {
			switch (perInfoCtg.getCategoryType()) {
			case SINGLEINFO:
				setOptionData(perInfoCtg, classItemList, query);
				break;
			case CONTINUOUSHISTORY:
			case NODUPLICATEHISTORY:
				String recordId = query.getInfoId();
				setOptionalDataByRecordId(recordId, perInfoCtg.getPersonEmployeeType(), classItemList);
			default:
				break;
			}
		}
		return classItemList;
	}
	
	private List<LayoutPersonInfoClsDto> creatClassItemList(List<PerInfoItemDefForLayoutDto> lstClsItem) {
		List<LayoutPersonInfoClsDto> classItemList = new ArrayList<>();
		lstClsItem.forEach(item -> {
			LayoutPersonInfoClsDto layoutPerInfoClsDto = new LayoutPersonInfoClsDto();
			layoutPerInfoClsDto.setPersonInfoCategoryID(item.getPerInfoCtgId());
			layoutPerInfoClsDto.setLayoutItemType(LayoutItemType.ITEM);
			layoutPerInfoClsDto.setClassName(item.getItemName());
			layoutPerInfoClsDto.setDispOrder(item.getDispOrder());
			layoutPerInfoClsDto.getItems().add(LayoutPersonInfoValueDto.initData(item, null));
			if (item.getItemDefType() != 2) {
				item.getLstChildItemDef().forEach(childItem -> {
					layoutPerInfoClsDto.setDispOrder(childItem.getDispOrder());
					layoutPerInfoClsDto.getItems().add(LayoutPersonInfoValueDto.initData(childItem, null));
				});
			}
			classItemList.add(layoutPerInfoClsDto);
		});
		return classItemList;
	}
	
	private void setOptionData(PersonInfoCategory perInfoCtg, List<LayoutPersonInfoClsDto> classItemList,
			PeregQuery query) {
		if (perInfoCtg.getPersonEmployeeType() == PersonEmployeeType.EMPLOYEE) {
			List<EmpInfoCtgData> empInfoCtgDatas = new ArrayList<>();
			if(query.getInfoId() != null || query.getStandardDate() != null)
					empInfoCtgDatas = empInCtgDataRepo.getByEmpIdAndCtgId(query.getEmployeeId(),
					perInfoCtg.getPersonInfoCategoryId());
			else MappingFactory.matchEmpOptionData(null, classItemList, new ArrayList<>());
			if (!empInfoCtgDatas.isEmpty()) {
				String recordId = empInfoCtgDatas.get(0).getRecordId();
				List<EmpOptionalDto> empOptionItemData = empInfoItemDataRepository.getAllInfoItemByRecordId(recordId)
						.stream().map(x -> x.genToPeregDto()).collect(Collectors.toList());
				MappingFactory.matchEmpOptionData(recordId, classItemList, empOptionItemData);
			}
		} else {
			List<PerInfoCtgData> perInfoCtgDatas = new ArrayList<>();
			if(query.getInfoId() != null || query.getStandardDate() != null)
				perInfoCtgDatas = perInCtgDataRepo.getByPerIdAndCtgId(query.getPersonId(),
					perInfoCtg.getPersonInfoCategoryId());
			else MappingFactory.matchEmpOptionData(null, classItemList, new ArrayList<>());
			if (!perInfoCtgDatas.isEmpty()) {
				String recordId = perInfoCtgDatas.get(0).getRecordId();
				List<PersonOptionalDto> perOptionItemData = perInfoItemDataRepository.getAllInfoItemByRecordId(recordId)
						.stream().map(x -> x.genToPeregDto()).collect(Collectors.toList());
				MappingFactory.matchPerOptionData(recordId, classItemList, perOptionItemData);
			}
		}

	}
	
	private void setOptionalDataByRecordId(String recordId, PersonEmployeeType type, 
			List<LayoutPersonInfoClsDto> classItemList ){
		if (type == PersonEmployeeType.EMPLOYEE) {
			List<EmpOptionalDto> empOptionItemData = empInfoItemDataRepository
					.getAllInfoItemByRecordId(recordId).stream().map(x -> x.genToPeregDto())
					.collect(Collectors.toList());
			MappingFactory.matchEmpOptionData(recordId, classItemList, empOptionItemData);
		} else {
			List<PersonOptionalDto> perOptionItemData = perInfoItemDataRepository
					.getAllInfoItemByRecordId(recordId).stream().map(x -> x.genToPeregDto())
					.collect(Collectors.toList());
			MappingFactory.matchPerOptionData(recordId, classItemList, perOptionItemData);
		}
	}
	
}
