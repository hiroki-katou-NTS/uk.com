package nts.uk.ctx.pereg.dom.person.info.item;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import nts.uk.ctx.pereg.dom.person.info.category.CategoryType;
import nts.uk.ctx.pereg.dom.person.info.category.PerInfoCategoryRepositoty;
import nts.uk.ctx.pereg.dom.person.info.daterangeitem.DateRangeItem;
import nts.uk.ctx.pereg.dom.roles.auth.category.PersonInfoAuthType;
import nts.uk.ctx.pereg.dom.roles.auth.item.PersonInfoItemAuthRepository;

public class PerInfoItemDefDomainService {
	@Inject
	private PerInfoCategoryRepositoty perInfoCategoryRepositoty;
	
	@Inject
	private PerInfoItemDefRepositoty perInfoItemDefRepositoty;

	@Inject
	private PersonInfoItemAuthRepository personInfoItemAuthRepository;
	/**
	 * get person information item definition
	 * @param paramObject
	 * @return List<PersonInfoItemDefinition>
	 */

	public List<PersonInfoItemDefinition> getPerItemDef(ParamForGetPerItemDef paramObject) {		
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
	
	private List<PersonInfoItemDefinition> getPerInfoItemDefWithAuth(ParamForGetPerItemDef paramObject){
		// get per info item def with order
		List<PersonInfoItemDefinition> lstPerInfoDef = perInfoItemDefRepositoty.getAllPerInfoItemDefByCategoryId(
				paramObject.getPersonInfoCategory().getPersonInfoCategoryId(), paramObject.getContractCode());
		// filter by auth
		return lstPerInfoDef.stream().filter(x -> {
			return paramObject
					.isSelfAuth()
							? personInfoItemAuthRepository
									.getItemDetai(paramObject.getRoleId(), paramObject.getPersonInfoCategory().getPersonInfoCategoryId(),
											x.getPerInfoItemDefId())
									.get().getSelfAuth() != PersonInfoAuthType.HIDE
							: personInfoItemAuthRepository
									.getItemDetai(paramObject.getRoleId(), paramObject.getPersonInfoCategory().getPersonInfoCategoryId(),
											x.getPerInfoItemDefId())
									.get().getOtherAuth() != PersonInfoAuthType.HIDE;
		}).collect(Collectors.toList());
	}
	
	/**
	 * get list person information item definition and filter by date range
	 * @param paramObject
	 * @return List<PersonInfoItemDefinition>
	 */
	private List<PersonInfoItemDefinition> getPerInfoItemDefWithHis(ParamForGetPerItemDef paramObject){
		DateRangeItem dateRangeItem = this.perInfoCategoryRepositoty
				.getDateRangeItemByCtgId(paramObject.getParentInfoId());
		return perInfoItemDefRepositoty
				.getPerInfoItemByCtgId(paramObject.getPersonInfoCategory().getPersonInfoCategoryId(), paramObject.getCompanyId(),
						paramObject.getContractCode())
				.stream().filter(x -> {
					return x.getPerInfoItemDefId() == dateRangeItem.getDateRangeItemId();
				}).collect(Collectors.toList());
	}
}
