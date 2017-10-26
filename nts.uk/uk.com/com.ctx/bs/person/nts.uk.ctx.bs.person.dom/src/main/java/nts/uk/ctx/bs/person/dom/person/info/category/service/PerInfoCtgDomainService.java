package nts.uk.ctx.bs.person.dom.person.info.category.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.bs.person.dom.person.adapter.SubJobPosAdapter;
import nts.uk.ctx.bs.person.dom.person.adapter.SubJobPosImport;
import nts.uk.ctx.bs.person.dom.person.adapter.familyrelatedinformation.familycare.FamilyCareAdapter;
import nts.uk.ctx.bs.person.dom.person.adapter.familyrelatedinformation.familycare.FamilyCareImport;
import nts.uk.ctx.bs.person.dom.person.adapter.familyrelatedinformation.familysocialinsurance.FamilySocialInsuranceAdapter;
import nts.uk.ctx.bs.person.dom.person.adapter.familyrelatedinformation.familysocialinsurance.FamilySocialInsuranceImport;
import nts.uk.ctx.bs.person.dom.person.adapter.familyrelatedinformation.incometax.IncomeTaxAdapter;
import nts.uk.ctx.bs.person.dom.person.adapter.familyrelatedinformation.incometax.IncomeTaxImport;
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
	private SubJobPosAdapter subJobPosAdapter;
	
	@Inject 
	private IncomeTaxAdapter incomeTaxAdapter;
	
	@Inject 
	private FamilySocialInsuranceAdapter familySocialInsuranceAdapter;
	
	
	@Inject 
	private FamilyCareAdapter familyCareAdapter;
	

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
					IncomeTaxImport incomeTax  = incomeTaxAdapter.getInComeTaxById(paramObject.getParentInfoId()).get();
					break;
				case "CS00006":
					FamilySocialInsuranceImport familySocialInsurance = familySocialInsuranceAdapter.getFamilySocialInseById(paramObject.getParentInfoId()).get();
					break;
				case "CS00007":
					FamilyCareImport familyCare = familyCareAdapter.getFamilyCareByid(paramObject.getParentInfoId()).get();
					break;
				case "CS00013":
					List<SubJobPosImport> lstSubJobPos = subJobPosAdapter.getSubJobPosByDeptId(paramObject.getParentInfoId());
					break;
			}
		}
		return lstResult;
	}

}
