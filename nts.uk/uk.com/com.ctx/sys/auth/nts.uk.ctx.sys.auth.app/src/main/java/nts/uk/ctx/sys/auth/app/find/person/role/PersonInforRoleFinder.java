package nts.uk.ctx.sys.auth.app.find.person.role;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.sys.auth.dom.role.PersonRole;
import nts.uk.ctx.sys.auth.dom.role.PersonRoleRepository;
import nts.uk.ctx.sys.auth.dom.role.Role;
import nts.uk.ctx.sys.auth.dom.role.RoleRepository;
import nts.uk.ctx.sys.auth.dom.role.RoleType;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class PersonInforRoleFinder {
	@Inject
	private RoleRepository roleRepo;

	@Inject
	private PersonRoleRepository personRoleRepo;

	public List<PersonInfoRole> find() {
		List<PersonInfoRole> result = new ArrayList<PersonInfoRole>();
		
		String companyId = AppContexts.user().companyId();
		// get domain role
		List<Role> roles = roleRepo.findByType(companyId, RoleType.PERSONAL_INFO);
		if (roles !=null && !roles.isEmpty()) {
			List<String> roleIds = roles.stream().map(x ->x.getRoleId()).collect(Collectors.toList());
			Map<String, PersonRole> mapPerson = personRoleRepo.find(roleIds).stream().collect(Collectors.toMap(PersonRole::getRoleId,  Function.identity()));
			PersonInfoRole personInfoRole = new PersonInfoRole();
			roles.forEach(role ->{
				personInfoRole.setRoleId(role.getRoleId());
				personInfoRole.setAssignAtr(role.getAssignAtr());
				personInfoRole.setEmployeeReferenceRange(role.getEmployeeReferenceRange());
				personInfoRole.setRoleType(role.getRoleType());
				personInfoRole.setRoleCode(role.getRoleCode().toString());
				
				// get domain PersonRole
				Optional<PersonRole> personRoleOpt = personRoleRepo.find(role.getRoleId());
				if (personRoleOpt.isPresent())
					personInfoRole.setReferFutureDate(personRoleOpt.get().getReferFutureDate());
				result.add(personInfoRole);
			});
		}	
		return result;
	}
}
