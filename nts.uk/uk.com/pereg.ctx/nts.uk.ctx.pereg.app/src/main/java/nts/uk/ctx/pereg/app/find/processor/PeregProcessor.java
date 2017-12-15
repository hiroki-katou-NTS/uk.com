package nts.uk.ctx.pereg.app.find.processor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDataMngInfo;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDataMngInfoRepository;
import nts.uk.ctx.pereg.app.find.common.MappingFactory;
import nts.uk.ctx.pereg.app.find.layout.dto.EmpMaintLayoutDto;
import nts.uk.ctx.pereg.app.find.person.category.PerInfoCategoryFinder;
import nts.uk.ctx.pereg.app.find.person.category.PerInfoCtgFullDto;
import nts.uk.ctx.pereg.app.find.person.info.item.PerInfoItemDefForLayoutDto;
import nts.uk.ctx.pereg.app.find.person.info.item.PerInfoItemDefForLayoutFinder;
import nts.uk.ctx.pereg.dom.person.ParamForGetPerItem;
import nts.uk.ctx.pereg.dom.person.PerInfoCtgDomainService;
import nts.uk.ctx.pereg.dom.person.additemdata.item.EmpInfoItemDataRepository;
import nts.uk.ctx.pereg.dom.person.info.category.CategoryType;
import nts.uk.ctx.pereg.dom.person.info.category.IsFixed;
import nts.uk.ctx.pereg.dom.person.info.category.PerInfoCategoryRepositoty;
import nts.uk.ctx.pereg.dom.person.info.category.PersonEmployeeType;
import nts.uk.ctx.pereg.dom.person.info.category.PersonInfoCategory;
import nts.uk.ctx.pereg.dom.person.info.item.PersonInfoItemDefinition;
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
	private EmpInfoItemDataRepository empInfoItemDataRepository;
	
	@Inject
	private PerInfoItemDataRepository perInfoItemDataRepository;
	
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
		String roleId = AppContexts.user().roles().forCompanyAdmin();

		// get Person ID
		query.setPersonId(empRepo.findByEmpId(query.getEmployeeId()).get().getPersonId());

		// get category
		PersonInfoCategory perInfoCtg = perInfoCtgRepositoty.getPerInfoCategory(query.getCategoryId(), contractCode)
				.get();

		if (!isMainDetail) {
			query.setCategoryCode(perInfoCtg.getCategoryCode().v() + "SD");
		}

		// get PerInfoItemDefForLayoutDto
		// check per info auth
		if (!perInfoCategoryFinder.checkPerInfoCtgAuth(query.getEmployeeId(), perInfoCtg.getPersonInfoCategoryId())) {
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
					.createFromDomain(query.getEmployeeId(), lstItemDef.get(i), query.getCategoryCode(), i);
			if (perInfoItemDefForLayoutDto != null)
				lstPerInfoItemDefForLayout.add(perInfoItemDefForLayoutDto);
		}

		EmpMaintLayoutDto empMaintLayoutDto = new EmpMaintLayoutDto();
		// set fix data
		setEmpMaintLayoutDto(empMaintLayoutDto, query, perInfoCtg, lstPerInfoItemDefForLayout);
		
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
	private void setEmpMaintLayoutDto(EmpMaintLayoutDto empMaintLayoutDto, PeregQuery query,
			PersonInfoCategory perInfoCtg, List<PerInfoItemDefForLayoutDto> lstPerInfoItemDef) {
		
		if (perInfoCtg.getIsFixed() == IsFixed.FIXED) {
			if (perInfoCtg.getCategoryType() != CategoryType.SINGLEINFO && query.getInfoId() == null) {
				MappingFactory.mapListClsDto(empMaintLayoutDto, null, lstPerInfoItemDef);
				// set optional data
				setOptionalData(empMaintLayoutDto, null, perInfoCtg, lstPerInfoItemDef);
			} else {
				// get domain data
				PeregDto peregDto = layoutingProcessor.findSingle(query);
				// set record id
				setRecordId(lstPerInfoItemDef, peregDto == null ? null : peregDto.getDomainDto().getRecordId());
				// set fixed data
				MappingFactory.mapListClsDto(empMaintLayoutDto, peregDto, lstPerInfoItemDef);
				// set optional data
				String recordId = query.getInfoId() == null ? perInfoCtg.getPersonInfoCategoryId() : query.getInfoId();
				setOptionalData(empMaintLayoutDto, recordId, perInfoCtg, lstPerInfoItemDef);
			}

		} else {
			if (perInfoCtg.getCategoryType() != CategoryType.SINGLEINFO && query.getInfoId() == null)
				// set optional data
				setOptionalData(empMaintLayoutDto, null, perInfoCtg, lstPerInfoItemDef);
			else
				setOptionalData(empMaintLayoutDto,
						query.getInfoId() == null ? perInfoCtg.getPersonInfoCategoryId() : query.getInfoId(),
						perInfoCtg, lstPerInfoItemDef);
		}
	}
	
	/**
	 * set optional data in tab
	 * 
	 * @param empMaintLayoutDto
	 * @param recordId
	 * @param perInfoCtg
	 * @param lstPerInfoItemDef
	 */
	private void setOptionalData(EmpMaintLayoutDto empMaintLayoutDto, String recordId, PersonInfoCategory perInfoCtg,
			List<PerInfoItemDefForLayoutDto> lstPerInfoItemDef) {
		if (perInfoCtg.getPersonEmployeeType() == PersonEmployeeType.EMPLOYEE)
			setEmpInfoItemData(empMaintLayoutDto, recordId, lstPerInfoItemDef);
		else
			setPerInfoItemData(empMaintLayoutDto, recordId, lstPerInfoItemDef);
	}
	
	/**
	 * set employee optional data
	 * 
	 * @param empMaintLayoutDto
	 * @param recordId
	 * @param lstPerInfoItemDef
	 */
	private void setEmpInfoItemData(EmpMaintLayoutDto empMaintLayoutDto, String recordId, List<PerInfoItemDefForLayoutDto> lstPerInfoItemDef){
		List<EmpOptionalDto> lstCtgItemOptionalDto = new ArrayList<>();
		if(recordId != null)
			lstCtgItemOptionalDto = empInfoItemDataRepository.getAllInfoItemByRecordId(recordId)
				.stream().map(x -> x.genToPeregDto()).collect(Collectors.toList());
	    setRecordId(lstPerInfoItemDef, lstCtgItemOptionalDto.size() > 0 ? recordId: null);
		MappingFactory.mapEmpOptionalDto(empMaintLayoutDto, lstCtgItemOptionalDto, lstPerInfoItemDef);
	}
	
	/**
	 * set person optional data
	 * 
	 * @param empMaintLayoutDto
	 * @param recordId
	 * @param lstPerInfoItemDef
	 */
	private void setPerInfoItemData(EmpMaintLayoutDto empMaintLayoutDto, String recordId, List<PerInfoItemDefForLayoutDto> lstPerInfoItemDef){
		List<PersonOptionalDto> lstCtgItemOptionalDto = new ArrayList<>();
		if(recordId != null)
			lstCtgItemOptionalDto = perInfoItemDataRepository.getAllInfoItemByRecordId(recordId)
				.stream().map(x -> x.genToPeregDto()).collect(Collectors.toList());
		setRecordId(lstPerInfoItemDef, lstCtgItemOptionalDto.size() > 0 ? recordId: null);
		MappingFactory.mapPerOptionalDto(empMaintLayoutDto, lstCtgItemOptionalDto, lstPerInfoItemDef);
	}
	
	private void setRecordId(List<PerInfoItemDefForLayoutDto> lstPerInfoItemDef, String recordId){
		lstPerInfoItemDef.forEach(item -> {
			item.setRecordId(recordId);
			if(item.getItemDefType() != 2) {
				item.getLstChildItemDef().forEach(i -> i.setRecordId(recordId));
			}
		});
	}

}
