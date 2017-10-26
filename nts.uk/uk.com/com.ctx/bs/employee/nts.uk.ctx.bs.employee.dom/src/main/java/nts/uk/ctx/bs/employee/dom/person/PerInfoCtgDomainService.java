package nts.uk.ctx.bs.employee.dom.person;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.bs.employee.dom.familyrelatedinformation.care.FamilyCare;
import nts.uk.ctx.bs.employee.dom.familyrelatedinformation.care.FamilyCareRepository;
import nts.uk.ctx.bs.employee.dom.familyrelatedinformation.incometax.IncomeTax;
import nts.uk.ctx.bs.employee.dom.familyrelatedinformation.incometax.IncomeTaxRepository;
import nts.uk.ctx.bs.employee.dom.familyrelatedinformation.socialinsurance.FamilySocialInsurance;
import nts.uk.ctx.bs.employee.dom.familyrelatedinformation.socialinsurance.FamilySocialInsuranceRepository;
import nts.uk.ctx.bs.employee.dom.position.jobposition.SubJobPosRepository;
import nts.uk.ctx.bs.employee.dom.position.jobposition.SubJobPosition;
import nts.uk.ctx.bs.person.dom.person.info.category.CategoryType;
import nts.uk.ctx.bs.person.dom.person.info.category.PerInfoCategoryRepositoty;
import nts.uk.ctx.bs.person.dom.person.info.category.PersonEmployeeType;
import nts.uk.ctx.bs.person.dom.person.info.daterangeitem.DateRangeItem;
import nts.uk.ctx.bs.person.dom.person.info.item.ItemType;
import nts.uk.ctx.bs.person.dom.person.info.item.PerInfoItemDefRepositoty;
import nts.uk.ctx.bs.person.dom.person.info.item.PersonInfoItemDefinition;
import nts.uk.ctx.bs.person.dom.person.info.setitem.SetItem;
import nts.uk.ctx.bs.person.dom.person.role.auth.category.PersonInfoAuthType;
import nts.uk.ctx.bs.person.dom.person.role.auth.item.PersonInfoItemAuthRepository;

@Stateless
public class PerInfoCtgDomainService {

	@Inject
	private PerInfoCategoryRepositoty perInfoCategoryRepositoty;
//	@Inject
//	private PersonInfoRoleAuthRepository personInfoRoleAuthRepository;
	@Inject
	private PerInfoItemDefRepositoty perInfoItemDefRepositoty;

	@Inject
	private PersonInfoItemAuthRepository personInfoItemAuthRepository;
	 
	@Inject
	private SubJobPosRepository subJobPosRepository;
	
	@Inject 
	private IncomeTaxRepository incomeTaxRepository;
	
	@Inject 
	private FamilySocialInsuranceRepository familySocialInsuranceRepository;
	
	
	@Inject 
	private FamilyCareRepository familyCareRepository;
	

	public List<PersonInfoItemDefinition> getPerItemDef(ParamForGetPerItem paramObject) {
		
		// get person infor role auth
//		PersonInfoRoleAuth personInfoRoleAuth = personInfoRoleAuthRepository
//				.getDetailPersonRoleAuth(paramObject.getRoleId(), paramObject.getCompanyId()).get();
		PersonInfoItemDefinition parrentPerInfoDef = new PersonInfoItemDefinition();
		if (paramObject.getPersonInfoCategory().getCategoryType() == CategoryType.MULTIINFO) {
			// get per info item def with order
			List<PersonInfoItemDefinition> lstPerInfoDef = perInfoItemDefRepositoty.getPerInfoItemByCtgId(
					paramObject.getPersonInfoCategory().getPersonInfoCategoryId(), paramObject.getCompanyId(),
					paramObject.getContractCode());
			// filter by auth
			parrentPerInfoDef = lstPerInfoDef.stream().filter(x -> {
				return paramObject
						.isSelfAuth()
								? personInfoItemAuthRepository
										.getItemDetai(paramObject.getRoleId(), paramObject.getParentInfoId(),
												x.getPerInfoItemDefId())
										.get().getSelfAuth() != PersonInfoAuthType.HIDE
								: personInfoItemAuthRepository
										.getItemDetai(paramObject.getRoleId(), paramObject.getParentInfoId(),
												x.getPerInfoItemDefId())
										.get().getOtherAuth() != PersonInfoAuthType.HIDE;
			}).collect(Collectors.toList()).get(0);

		} else {
			DateRangeItem dateRangeItem = this.perInfoCategoryRepositoty
					.getDateRangeItemByCtgId(paramObject.getParentInfoId());
			parrentPerInfoDef = perInfoItemDefRepositoty
					.getPerInfoItemByCtgId(paramObject.getPersonInfoCategory().getPersonInfoCategoryId(), paramObject.getCompanyId(),
							paramObject.getContractCode())
					.stream().filter(x -> {
						return x.getPerInfoItemDefId() == dateRangeItem.getDateRangeItemId();
					}).findFirst().get();
		}
		List<PersonInfoItemDefinition> lstResult = new ArrayList<>();
		if (parrentPerInfoDef.getItemTypeState().getItemType() == ItemType.SET_ITEM) {
			//get itemId list of children
			SetItem setItem = (SetItem) parrentPerInfoDef.getItemTypeState();
			// get children by itemId list
			lstResult = perInfoItemDefRepositoty.getPerInfoItemDefByListId(setItem.getItems(), paramObject.getContractCode());
		}
		lstResult.add(parrentPerInfoDef);
		if(paramObject.getPersonInfoCategory().getPersonEmployeeType() == PersonEmployeeType.EMPLOYEE){
			switch(paramObject.getPersonInfoCategory().getCategoryCode().v()){
				case "CS00005":
					IncomeTax incomeTax  = incomeTaxRepository.getIncomeTaxById(paramObject.getParentInfoId()).get();
					break;
				case "CS00006":
					FamilySocialInsurance familySocialInsurance = familySocialInsuranceRepository.getFamilySocialInsById(paramObject.getParentInfoId()).get();
					break;
				case "CS00007":
					FamilyCare familyCare = familyCareRepository.getFamilyCareById(paramObject.getParentInfoId()).get();
					break;
				case "CS00013":
					List<SubJobPosition> lstSubJobPos = subJobPosRepository.getSubJobPosByDeptId(paramObject.getParentInfoId());
					break;
			}
		}
		return lstResult;
	}

}
