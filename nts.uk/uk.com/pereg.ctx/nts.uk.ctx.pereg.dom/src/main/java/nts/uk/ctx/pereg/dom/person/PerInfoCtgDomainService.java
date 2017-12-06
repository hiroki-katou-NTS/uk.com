package nts.uk.ctx.pereg.dom.person;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pereg.dom.person.info.category.CategoryType;
import nts.uk.ctx.pereg.dom.person.info.category.IsFixed;
import nts.uk.ctx.pereg.dom.person.info.category.PerInfoCategoryRepositoty;
import nts.uk.ctx.pereg.dom.person.info.daterangeitem.DateRangeItem;
import nts.uk.ctx.pereg.dom.person.info.item.PerInfoItemDefRepositoty;
import nts.uk.ctx.pereg.dom.person.info.item.PersonInfoItemDefinition;
import nts.uk.ctx.pereg.dom.roles.auth.category.PersonInfoAuthType;
import nts.uk.ctx.pereg.dom.roles.auth.item.PersonInfoItemAuth;
import nts.uk.ctx.pereg.dom.roles.auth.item.PersonInfoItemAuthRepository;

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
		if (paramObject.getPersonInfoCategory().getCategoryType() == CategoryType.MULTIINFO
				|| paramObject.getPersonInfoCategory().getCategoryType() == CategoryType.SINGLEINFO) 
			return getPerInfoItemDefWithAuth(paramObject);
		else 
			return getPerInfoItemDefWithHis(paramObject);	
	}
	
	/**
	 * get list person information item definition and filter by auth
	 * @param paramObject
	 * @return List<PersonInfoItemDefinition>
	 */
	
	private List<PersonInfoItemDefinition> getPerInfoItemDefWithAuth(ParamForGetPerItem paramObject){
		// get per info item def with order
		List<PersonInfoItemDefinition> lstPerInfoDef = perInfoItemDefRepositoty.getAllPerInfoItemDefByCategoryId(
				paramObject.getPersonInfoCategory().getPersonInfoCategoryId(), paramObject.getContractCode());
		// filter by auth
		return lstPerInfoDef.stream().filter(x -> {
			Optional<PersonInfoItemAuth> personInfoItemAuth = personInfoItemAuthRepository
					.getItemDetai(paramObject.getRoleId(), paramObject.getPersonInfoCategory().getPersonInfoCategoryId(),
							x.getPerInfoItemDefId());
			if(personInfoItemAuth.isPresent())
				return paramObject.isSelfAuth()?
						personInfoItemAuthRepository
							.getItemDetai(paramObject.getRoleId(), paramObject.getPersonInfoCategory().getPersonInfoCategoryId(),
									x.getPerInfoItemDefId())
							.get().getSelfAuth() != PersonInfoAuthType.HIDE:
						personInfoItemAuthRepository
							.getItemDetai(paramObject.getRoleId(), paramObject.getPersonInfoCategory().getPersonInfoCategoryId(),
									x.getPerInfoItemDefId())
							.get().getOtherAuth() != PersonInfoAuthType.HIDE;
			else return false;			
		}).collect(Collectors.toList());
	}
	
	/**
	 * get list person information item definition and filter by date range
	 * @param paramObject
	 * @return List<PersonInfoItemDefinition>
	 */
	private List<PersonInfoItemDefinition> getPerInfoItemDefWithHis(ParamForGetPerItem paramObject){	
		List<PersonInfoItemDefinition> lstPersonInfoItemDefinition = perInfoItemDefRepositoty
				.getAllPerInfoItemDefByCategoryId(paramObject.getPersonInfoCategory().getPersonInfoCategoryId(), paramObject.getContractCode());
		if(paramObject.getPersonInfoCategory().getIsFixed() == IsFixed.NOT_FIXED) return lstPersonInfoItemDefinition;
		DateRangeItem dateRangeItem = this.perInfoCategoryRepositoty
				.getDateRangeItemByCtgId(paramObject.getPersonInfoCategory().getPersonInfoCategoryId());
		return lstPersonInfoItemDefinition
				.stream().filter(x -> {
					return x.getPerInfoItemDefId() == dateRangeItem.getDateRangeItemId();
				}).collect(Collectors.toList());
	}

}
