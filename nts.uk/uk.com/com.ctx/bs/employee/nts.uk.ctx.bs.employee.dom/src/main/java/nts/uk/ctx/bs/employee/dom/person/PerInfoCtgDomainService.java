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

/**
 * The class process business 
 * @author xuan vinh
 *
 */

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
	 

	/**
	 * get person information item definition
	 * @param paramObject
	 * @return List<PersonInfoItemDefinition>
	 */

	public List<PersonInfoItemDefinition> getPerItemDef(ParamForGetPerItem paramObject) {
		
		// get person infor role auth
//		PersonInfoRoleAuth personInfoRoleAuth = personInfoRoleAuthRepository
//				.getDetailPersonRoleAuth(paramObject.getRoleId(), paramObject.getCompanyId()).get();
		PersonInfoItemDefinition parrentPerInfoDef = new PersonInfoItemDefinition();
		if (paramObject.getPersonInfoCategory().getCategoryType() == CategoryType.MULTIINFO) 
			parrentPerInfoDef = getPerInfoItemDefWithAuth(paramObject).get(0);
		else 
			parrentPerInfoDef = getPerInfoItemDefWithHis(paramObject);
		
		List<PersonInfoItemDefinition> lstResult = new ArrayList<>();
		if (parrentPerInfoDef.getItemTypeState().getItemType() == ItemType.SET_ITEM) {
			//get itemId list of children
			SetItem setItem = (SetItem) parrentPerInfoDef.getItemTypeState();
			// get children by itemId list
			lstResult = perInfoItemDefRepositoty.getPerInfoItemDefByListId(setItem.getItems(), paramObject.getContractCode());
		}
		lstResult.add(parrentPerInfoDef);		
		return lstResult;
	}
	
	/**
	 * get list person information item definition and filter by auth
	 * @param paramObject
	 * @return List<PersonInfoItemDefinition>
	 */
	
	public List<PersonInfoItemDefinition> getPerInfoItemDefWithAuth(ParamForGetPerItem paramObject){
		// get per info item def with order
		List<PersonInfoItemDefinition> lstPerInfoDef = perInfoItemDefRepositoty.getPerInfoItemByCtgId(
				paramObject.getPersonInfoCategory().getPersonInfoCategoryId(), paramObject.getCompanyId(),
				paramObject.getContractCode());
		// filter by auth
		return lstPerInfoDef.stream().filter(x -> {
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
		}).collect(Collectors.toList());
	}
	
	/**
	 * get list person information item definition and filter by date range
	 * @param paramObject
	 * @return List<PersonInfoItemDefinition>
	 */
	private PersonInfoItemDefinition getPerInfoItemDefWithHis(ParamForGetPerItem paramObject){
		DateRangeItem dateRangeItem = this.perInfoCategoryRepositoty
				.getDateRangeItemByCtgId(paramObject.getParentInfoId());
		return perInfoItemDefRepositoty
				.getPerInfoItemByCtgId(paramObject.getPersonInfoCategory().getPersonInfoCategoryId(), paramObject.getCompanyId(),
						paramObject.getContractCode())
				.stream().filter(x -> {
					return x.getPerInfoItemDefId() == dateRangeItem.getDateRangeItemId();
				}).findFirst().get();
	}

}
