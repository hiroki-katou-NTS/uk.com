package nts.uk.ctx.pereg.dom.employeeinfo.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pereg.dom.person.info.item.PerInfoItemDefRepositoty;
import nts.uk.ctx.pereg.dom.person.info.item.PersonInfoItemDefinition;
import nts.uk.ctx.pereg.dom.roles.auth.category.PersonInfoAuthType;
import nts.uk.ctx.pereg.dom.roles.auth.item.PersonInfoItemAuthRepository;

@Stateless
public class EmpCtgDomainServices {

	/**
	 * LaiTv
	 */
	@Inject
	private PerInfoItemDefRepositoty perInfoItemDefRepositoty;

	@Inject
	private PersonInfoItemAuthRepository personInfoItemAuthRepository;

	/**
	 * get list person information item definition and filter by auth
	 * 
	 * @param paramObject
	 * @return List<PersonInfoItemDefinition>
	 */

	public List<PersonInfoItemDefinition> getPerInfoItemDefWithAuth(ParamForGetItemDef paramObject) {
		// get per info item def with order
		List<PersonInfoItemDefinition> lstPerInfoDef = perInfoItemDefRepositoty.getAllPerInfoItemDefByCategoryId(
				paramObject.getPersonInfoCategory().getPersonInfoCategoryId(), paramObject.getContractCode());

		// filter by auth return
		lstPerInfoDef.stream().filter(x -> {
			return paramObject.isSelfAuth()
					? personInfoItemAuthRepository
							.getItemDetai(paramObject.getRoleId(), x.getPerInfoCategoryId(), x.getPerInfoItemDefId())
							.get().getSelfAuth() != PersonInfoAuthType.HIDE
					: personInfoItemAuthRepository
							.getItemDetai(paramObject.getRoleId(), x.getPerInfoCategoryId(), x.getPerInfoItemDefId())
							.get().getOtherAuth() != PersonInfoAuthType.HIDE;
		}).collect(Collectors.toList());

		return lstPerInfoDef;
	}

}
