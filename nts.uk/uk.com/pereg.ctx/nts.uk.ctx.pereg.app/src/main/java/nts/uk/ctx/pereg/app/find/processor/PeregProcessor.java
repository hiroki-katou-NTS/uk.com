package nts.uk.ctx.pereg.app.find.processor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import find.layout.classification.LayoutPersonInfoClsDto;
import find.layout.classification.LayoutPersonInfoValueDto;
import find.person.info.category.PerInfoCategoryFinder;
import find.person.info.item.PerInfoItemDefForLayoutDto;
import find.person.info.item.PerInfoItemDefForLayoutFinder;
import lombok.val;
import nts.uk.ctx.bs.employee.app.find.layout.dto.EmpMaintLayoutDto;
import nts.uk.ctx.bs.employee.dom.person.ParamForGetPerItem;
import nts.uk.ctx.bs.employee.dom.person.PerInfoCtgDomainService;
import nts.uk.ctx.bs.employee.dom.regpersoninfo.personinfoadditemdata.item.EmpInfoItemData;
import nts.uk.ctx.bs.employee.dom.regpersoninfo.personinfoadditemdata.item.EmpInfoItemDataRepository;
import nts.uk.ctx.bs.person.dom.person.info.category.IsFixed;
import nts.uk.ctx.bs.person.dom.person.info.category.PerInfoCategoryRepositoty;
import nts.uk.ctx.bs.person.dom.person.info.category.PersonEmployeeType;
import nts.uk.ctx.bs.person.dom.person.info.category.PersonInfoCategory;
import nts.uk.ctx.bs.person.dom.person.info.item.PersonInfoItemDefinition;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.pereg.app.find.LayoutingProcessor;
import nts.uk.shr.pereg.app.find.LayoutingResult;
import nts.uk.shr.pereg.app.find.PeregQuery;

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
	private LayoutingProcessor layoutingProcessor;
	
	@Inject
	private PerInfoCategoryRepositoty perInfoCtgRepositoty;
	
	public EmpMaintLayoutDto getCategoryChild(PeregQuery query)
	{
		// app context
		String contractCode = AppContexts.user().contractCode();
		String companyId = AppContexts.user().companyId();
		String loginEmpId = AppContexts.user().employeeId();
		//String roleId = AppContexts.user().roles().forPersonalInfo();
		String roleId = "99900000-0000-0000-0000-000000000001";
		
		// get ctgCd
		PersonInfoCategory perInfoCtg = perInfoCtgRepositoty.getPerInfoCategory(query.getCtgId(), contractCode).get();
		query.setCtgCd(perInfoCtg.getCategoryCode().v());
		
		// get PerInfoItemDefForLayoutDto
		//check per info auth
		if(!perInfoCategoryFinder.checkPerInfoCtgAuth(query.getEmpId(), perInfoCtg.getPersonInfoCategoryId()))
			return new EmpMaintLayoutDto();
		
		//get item def
		List<PersonInfoItemDefinition> lstItemDef = perInfoCtgDomainService
				.getPerItemDef(new ParamForGetPerItem(perInfoCtg, query.getInfoId(), roleId == null ? "" : roleId, companyId,
						contractCode, loginEmpId.equals(query.getEmpId())));
		if(lstItemDef.size() == 0) return new EmpMaintLayoutDto();
		List<PerInfoItemDefForLayoutDto> lstPerInfoItemDef = new ArrayList<>();
		for (int i = 0; i < lstItemDef.size(); i++)
			lstPerInfoItemDef.add(perInfoItemDefForLayoutFinder.createFromDomain(query.getEmpId(), lstItemDef.get(i), query.getCtgCd(), i));
		
		EmpMaintLayoutDto empMaintLayoutDto = new EmpMaintLayoutDto();
		
		if(perInfoCtg.getIsFixed() == IsFixed.FIXED){
			//get domain data
			LayoutingResult returnValue = layoutingProcessor.handler(query);
			val queryResult = (PeregQueryResult)returnValue.getQueryResult();

			//set fixed data
			LayoutMapping.mapFixDto(empMaintLayoutDto, queryResult.getDto(), returnValue.getDtoFinderClass(), lstPerInfoItemDef);
			
			int startOptionDtoPos = lstPerInfoItemDef.size();
			if(perInfoCtg.getPersonEmployeeType() == PersonEmployeeType.EMPLOYEE)
					LayoutMapping.mapEmpOptionalDto(empMaintLayoutDto, queryResult.getEmpOptionalData(), lstPerInfoItemDef, startOptionDtoPos);
			else LayoutMapping.mapPerOptionalDto(empMaintLayoutDto, queryResult.getPerOptionalData(), lstPerInfoItemDef, startOptionDtoPos);
		}
		//set optional data
		return empMaintLayoutDto;
	}
	
	private void setOptionalData(EmpMaintLayoutDto empMaintLayoutDto, String recordId, PersonInfoCategory perInfoCtg, List<PerInfoItemDefForLayoutDto> lstPerInfoItemDef, int startOptionDtoPos){
		if(perInfoCtg.getPersonEmployeeType() == PersonEmployeeType.EMPLOYEE)
			setEmpInfoItemData(empMaintLayoutDto, recordId, lstPerInfoItemDef, startOptionDtoPos);
		else 
			setPerInfoItemData();
	}
	
	private void setEmpInfoItemData(EmpMaintLayoutDto empMaintLayoutDto, String recordId, List<PerInfoItemDefForLayoutDto> lstPerInfoItemDef, int startOptionDtoPos){
		List<EmpInfoItemData> lstCtgItemOptionalDto = empInfoItemDataRepository.getAllInfoItemByRecordId(recordId);
		if(lstCtgItemOptionalDto.size() > 0){
			for(EmpInfoItemData data : lstCtgItemOptionalDto) {
				Optional<PerInfoItemDefForLayoutDto> item = lstPerInfoItemDef.stream().filter(x -> {
					return x.getItemCode().equals(data.getItemCode());
					}).findFirst();
				if(item.isPresent()){
					LayoutPersonInfoClsDto layoutPerInfoClsDto = new LayoutPersonInfoClsDto();
					layoutPerInfoClsDto.setDispOrder(startOptionDtoPos);
					layoutPerInfoClsDto.getItems().add(LayoutPersonInfoValueDto.initData(item.get(), getOptionalDataValue(item.get(), data)));
					empMaintLayoutDto.getClassificationItems().add(layoutPerInfoClsDto);
					startOptionDtoPos ++;
				}
			}
		}
	}
	
	private void setPerInfoItemData(){
		
	}
	
	private Object getOptionalDataValue(PerInfoItemDefForLayoutDto item, EmpInfoItemData data){
		
		return null;
	}
}
