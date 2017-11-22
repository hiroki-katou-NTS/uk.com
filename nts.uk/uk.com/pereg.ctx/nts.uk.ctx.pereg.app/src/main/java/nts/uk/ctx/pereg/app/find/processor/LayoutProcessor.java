package nts.uk.ctx.pereg.app.find.processor;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import find.person.info.category.PerInfoCategoryFinder;
import find.person.info.item.PerInfoItemDefForLayoutDto;
import find.person.info.item.PerInfoItemDefForLayoutFinder;
import nts.uk.ctx.bs.employee.app.find.layout.dto.EmpMaintLayoutDto;
import nts.uk.ctx.bs.employee.app.find.person.PersonQueryResult;
import nts.uk.ctx.bs.employee.dom.person.ParamForGetPerItem;
import nts.uk.ctx.bs.employee.dom.person.PerInfoCtgDomainService;
import nts.uk.ctx.bs.person.dom.person.info.category.PerInfoCategoryRepositoty;
import nts.uk.ctx.bs.person.dom.person.info.category.PersonInfoCategory;
import nts.uk.ctx.bs.person.dom.person.info.item.PersonInfoItemDefinition;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.pereg.app.find.LayoutingProcessor;
import nts.uk.shr.pereg.app.find.PeregQuery;
import nts.uk.shr.pereg.app.find.LayoutingResult;

@Stateless
public class LayoutProcessor {
	@Inject
	private PerInfoCategoryFinder perInfoCategoryFinder;
	
	@Inject
	private PerInfoCtgDomainService perInfoCtgDomainService;
	
	@Inject
	private PerInfoItemDefForLayoutFinder perInfoItemDefForLayoutFinder;
	
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
		//get domain data
		LayoutingResult returnValue = layoutingProcessor.handler(query);
		Object domainData = returnValue.getDomainData();
		
		EmpMaintLayoutDto empMaintLayoutDto = LayoutMapping.map(domainData, returnValue.getDtoFinderClass(), lstPerInfoItemDef);
		
		return empMaintLayoutDto;
	}
}
