package nts.uk.ctx.pereg.app.find.processor;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.gul.reflection.AnnotationUtil;
import nts.gul.reflection.FieldsWorkerStream;
import nts.gul.reflection.ReflectionUtil;
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
import nts.uk.ctx.pereg.dom.person.additemdata.item.EmpInfoItemData;
import nts.uk.ctx.pereg.dom.person.additemdata.item.EmpInfoItemDataRepository;
import nts.uk.ctx.pereg.dom.person.info.category.CategoryType;
import nts.uk.ctx.pereg.dom.person.info.category.IsFixed;
import nts.uk.ctx.pereg.dom.person.info.category.PerInfoCategoryRepositoty;
import nts.uk.ctx.pereg.dom.person.info.category.PersonEmployeeType;
import nts.uk.ctx.pereg.dom.person.info.category.PersonInfoCategory;
import nts.uk.ctx.pereg.dom.person.info.daterangeitem.DateRangeItem;
import nts.uk.ctx.pereg.dom.person.info.item.PerInfoItemDefRepositoty;
import nts.uk.ctx.pereg.dom.person.info.item.PersonInfoItemDefinition;
import nts.uk.ctx.pereg.dom.person.layout.classification.LayoutItemType;
import nts.uk.ctx.pereg.dom.person.personinfoctgdata.categor.PerInfoCtgData;
import nts.uk.ctx.pereg.dom.person.personinfoctgdata.categor.PerInfoCtgDataRepository;
import nts.uk.ctx.pereg.dom.person.personinfoctgdata.item.PerInfoItemDataRepository;
import nts.uk.ctx.pereg.dom.person.personinfoctgdata.item.PersonInfoItemData;
import nts.uk.ctx.pereg.dom.roles.auth.PersonInfoPermissionType;
import nts.uk.ctx.pereg.dom.roles.auth.category.PersonInfoAuthType;
import nts.uk.ctx.pereg.dom.roles.auth.category.PersonInfoCategoryAuth;
import nts.uk.ctx.pereg.dom.roles.auth.category.PersonInfoCategoryAuthRepository;
import nts.uk.ctx.pereg.dom.roles.auth.item.PersonInfoItemAuth;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.pereg.app.PeregItem;
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
	
	@Inject
	private PersonInfoCategoryAuthRepository perAuth;
	
	@Inject
	private PerInfoItemDefRepositoty perItemRepo;
	
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
		
		PeregDto peregDto = null;
		
		if ((perInfoCtg.getIsFixed() == IsFixed.FIXED && query.getInfoId() != null)
				||(query.getInfoId() == null && perInfoCtg.getCategoryType() == CategoryType.SINGLEINFO)) {
			peregDto = layoutingProcessor.findSingle(query);
		}
		CheckViewOnlyReturnObj checkViewOnly = new CheckViewOnlyReturnObj();
		if(perInfoCtg.getCategoryType() != CategoryType.SINGLEINFO) {
			checkViewOnly = checkCtgIsViewOnly(peregDto, perInfoCtg, roleId, query.getInfoId(), loginEmpId.equals(query.getEmployeeId()));
		}
		for (int i = 0; i < lstItemDef.size(); i++) {
			PerInfoItemDefForLayoutDto perInfoItemDefForLayoutDto = perInfoItemDefForLayoutFinder
					.createFromDomain(query.getEmployeeId(), perInfoCtg.getCategoryType().value, lstItemDef.get(i), perInfoCtg.getCategoryCode().v(), i, roleId, checkViewOnly.isViewOnly, checkViewOnly.startDate);
			if (perInfoItemDefForLayoutDto != null)
				lstPerInfoItemDefForLayout.add(perInfoItemDefForLayoutDto);
		}

		EmpMaintLayoutDto empMaintLayoutDto = new EmpMaintLayoutDto();
		
		List<LayoutPersonInfoClsDto> classItemList = getClassItemList(query, perInfoCtg, lstPerInfoItemDefForLayout, peregDto);
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
			PersonInfoCategory perInfoCtg, List<PerInfoItemDefForLayoutDto> lstPerInfoItemDef, PeregDto peregDto) {

		List<LayoutPersonInfoClsDto> classItemList = creatClassItemList(lstPerInfoItemDef);
		if(perInfoCtg.getCategoryType() != CategoryType.SINGLEINFO
				&& query.getInfoId() == null && query.getStandardDate() == null)
			return classItemList;
		if (perInfoCtg.getIsFixed() == IsFixed.FIXED) {
			 
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
			if(query.getInfoId() == null && query.getStandardDate() == null && perInfoCtg.getCategoryType() != CategoryType.SINGLEINFO) {
				MappingFactory.matchEmpOptionData(null, classItemList, new ArrayList<>());
			}
			else {
				empInfoCtgDatas = empInCtgDataRepo.getByEmpIdAndCtgId(query.getEmployeeId(),
						perInfoCtg.getPersonInfoCategoryId());
			}
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
	
	private CheckViewOnlyReturnObj checkCtgIsViewOnly(PeregDto peregDto, PersonInfoCategory perInfoCtg, String roleId, String infoId, boolean isSelf) {
		CheckViewOnlyReturnObj returnObject = new CheckViewOnlyReturnObj();
		PersonInfoCategoryAuth perInfoCtgAuth = perAuth.getDetailPersonCategoryAuthByPId(roleId, perInfoCtg.getPersonInfoCategoryId()).get();
		String exceptionItemCode = "CS00003";
		if(infoId == null) {
			if((perInfoCtgAuth.getOtherAllowAddHis() == PersonInfoPermissionType.NO && !isSelf)
					||(perInfoCtgAuth.getSelfAllowAddHis() == PersonInfoPermissionType.NO && isSelf)) {
				returnObject.isViewOnly = false;
				return returnObject;
			}
		}else {
			String sDateId = "";	
			String eDateId = "";	
			if(!perInfoCtg.getCategoryCode().v().equals(exceptionItemCode)) {
				DateRangeItem dateRangeItem = perInfoCtgRepositoty
						.getDateRangeItemByCategoryId(perInfoCtg.getPersonInfoCategoryId());
				eDateId = dateRangeItem.getEndDateItemId();
				sDateId = dateRangeItem.getStartDateItemId();
			}
			
			Object sValue = null;
			Object eValue = null;
			if(perInfoCtg.getIsFixed() == IsFixed.FIXED) {			
				List<Field> fields =  AnnotationUtil.getStreamOfFieldsAnnotated(peregDto.getDtoClass(), PeregItem.class).collect(Collectors.toList());
				sValue = getDateValueOfFixedCtg(peregDto, perInfoCtg.getCategoryCode().v(), exceptionItemCode, fields, sDateId, true);
				eValue = getDateValueOfFixedCtg(peregDto, perInfoCtg.getCategoryCode().v(), exceptionItemCode, fields, eDateId, false);
			}else {
				if(perInfoCtg.getPersonEmployeeType() == PersonEmployeeType.EMPLOYEE) {
					Optional<EmpInfoItemData> dateForStartDate = empInfoItemDataRepository.getInfoItemByItemDefIdAndRecordId(sDateId, infoId);
					Optional<EmpInfoItemData> dateForEndDate = empInfoItemDataRepository.getInfoItemByItemDefIdAndRecordId(eDateId, infoId);
					sValue = dateForStartDate.isPresent() ? dateForStartDate.get().getDataState().getDateValue() : null;
					eValue = dateForEndDate.isPresent() ? dateForEndDate.get().getDataState().getDateValue() : null;
					
				}else {
					Optional<PersonInfoItemData> dateForStartDate = perInfoItemDataRepository.getPerInfoItemDataByItemDefIdAndRecordId(sDateId, infoId);
					Optional<PersonInfoItemData> dateForEndDate = perInfoItemDataRepository.getPerInfoItemDataByItemDefIdAndRecordId(eDateId, infoId);
					sValue = dateForStartDate.isPresent() ? dateForStartDate.get().getDataState().getDateValue() : null;
					eValue = dateForEndDate.isPresent() ? dateForEndDate.get().getDataState().getDateValue() : null;
				}			
			}
			Period period = getPeriod(sValue, eValue);
			GeneralDate sDate = GeneralDate.fromString(sValue.toString(), "yyyy/MM/dd");
			returnObject.startDate = sDate;
			if(period == Period.PRESENT) {
				returnObject.isViewOnly = false;
				return returnObject;
			}
			if(period == Period.FUTURE) {
				returnObject.isViewOnly = isSelf?perInfoCtgAuth.getSelfFutureHisAuth() == PersonInfoAuthType.REFERENCE : perInfoCtgAuth.getOtherFutureHisAuth() == PersonInfoAuthType.REFERENCE;
				return returnObject;
			}else {
				returnObject.isViewOnly = isSelf?perInfoCtgAuth.getSelfPastHisAuth() == PersonInfoAuthType.REFERENCE : perInfoCtgAuth.getOtherPastHisAuth() == PersonInfoAuthType.REFERENCE;
				return returnObject;
			}
		}
		return returnObject;
	}
	private Object getDateValueOfFixedCtg(PeregDto peregDto, String ctgCode, String exceptionItemCode, List<Field> fields, String dateId, boolean isStart) {
		Optional<Field> field = Optional.empty();
		if(ctgCode.equals(exceptionItemCode)) {
			if(!isStart)
				field = Optional.of(fields.get(1));
			else field = Optional.of(fields.get(0));
		}else {
			String dateItemCode = perItemRepo.getPerInfoItemDefById(dateId, AppContexts.user().contractCode()).get().getItemCode().v();
			List<Field> filterFields= fields.stream().filter(f -> {
				return f.getAnnotation(PeregItem.class).value().equals(dateItemCode);
			}).collect(Collectors.toList());
			if(filterFields.size() > 0)
				field = filterFields.stream().findFirst();
			else return null;
			
		}
		if(field.isPresent()) {
			return ReflectionUtil.getFieldValue(field.get(), peregDto.getDomainDto());
		}else return null;
	}
	
	private Period getPeriod(Object sValue, Object eValue) {
		GeneralDate sDate = GeneralDate.fromString(sValue.toString(), "yyyy/MM/dd");
		GeneralDate today = GeneralDate.today();
		if(eValue == null) {
			return sDate.beforeOrEquals(today) ? Period.PRESENT : Period.FUTURE;
		}else {
			GeneralDate eDate = GeneralDate.fromString(eValue.toString(), "yyyy/MM/dd");
			return today.after(eDate)?Period.PAST: (today.afterOrEquals(sDate)?Period.PRESENT: Period.FUTURE);
		}
	}
	private enum Period{
		PAST(1),
		PRESENT(2),
		FUTURE(3);
		Period(int a) {}
	}
}
class CheckViewOnlyReturnObj{
	boolean isViewOnly;
	GeneralDate startDate;
}