package nts.uk.ctx.sys.auth.app.find.grant.rolesetperson;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import nts.arc.error.BusinessException;
import nts.uk.ctx.sys.auth.app.find.roleset.RoleSetDto;
import nts.uk.ctx.sys.auth.dom.grant.rolesetperson.RoleSetGrantedPersonRepository;
import nts.uk.ctx.sys.auth.dom.roleset.RoleSetRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author HungTT
 *
 */

@Stateless
public class RoleSetGrantedPersonFinder {

	@Inject
	private RoleSetRepository roleSetRepo;

	@Inject
	private RoleSetGrantedPersonRepository roleSetPersonRepo;

	public List<RoleSetGrantedPersonDto> getAllData() {
		String companyId = AppContexts.user().companyId();
		if (StringUtils.isNoneEmpty(companyId))
			return null;
		// get Role Set by companyId, sort ASC
		List<RoleSetDto> listRoleSet = roleSetRepo.findByCompanyId(companyId).stream()
				.map(item -> RoleSetDto.build(item)).collect(Collectors.toList());

		if (listRoleSet == null || listRoleSet.isEmpty()) {
			throw new BusinessException("Msg_713");
		}

		listRoleSet.sort((rs1, rs2) -> rs1.getRoleSetCd().compareTo(rs2.getRoleSetCd()));

		// get all role set granted person companyId, roleSetCd, return list
		List<RoleSetGrantedPersonDto> listRoleSetPerson = new ArrayList<>();
		for (RoleSetDto item : listRoleSet) {
			listRoleSetPerson.addAll(roleSetPersonRepo.getAll(item.getRoleSetCd(), companyId).stream()
					.map(c -> RoleSetGrantedPersonDto.fromDomain(c)).collect(Collectors.toList()));
		}

		if (listRoleSetPerson != null && !listRoleSetPerson.isEmpty()) {
			return listRoleSetPerson;
		} else
			return null;

		// sort by empCd (SCD) asc
		// 0 => new mode, else => update mode, select first item
	}
}
