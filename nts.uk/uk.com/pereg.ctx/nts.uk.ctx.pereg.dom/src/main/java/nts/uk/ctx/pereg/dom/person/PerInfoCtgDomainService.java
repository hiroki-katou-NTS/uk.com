package nts.uk.ctx.pereg.dom.person;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pereg.dom.person.info.category.IsAbolition;
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
	private PerInfoItemDefRepositoty perInfoItemDefRepositoty;

	@Inject
	private PersonInfoItemAuthRepository personInfoItemAuthRepository;
	
	/**
	 * get list person information item definition and filter by auth
	 * @param paramObject
	 * @return List<PersonInfoItemDefinition>
	 */
	
	public List<PersonInfoItemDefinition> getPerItemDef(ParamForGetPerItem paramObject){
		// get per info item def with order
		List<PersonInfoItemDefinition> lstPerInfoDef = perInfoItemDefRepositoty.getAllPerInfoItemDefByCategoryId(
				paramObject.getPersonInfoCategory().getPersonInfoCategoryId(), paramObject.getContractCode());
		// filter by auth
		return lstPerInfoDef.stream().filter(x -> {
			if(x.getIsAbolition() == IsAbolition.ABOLITION) return false;
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
	
	

}
