package nts.uk.ctx.sys.auth.app.find.person.role;

import java.util.Optional;

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

	public PersonInfoRole find() {
		PersonInfoRole result = new PersonInfoRole();
		String companyId = AppContexts.user().companyId();
		// get domain role
		Optional<Role> roleOpt = roleRepo.findByType(companyId, RoleType.PERSONAL_INFO);
		if (roleOpt.isPresent()) {
			Role role = roleOpt.get();
			result.setRoleId(role.getRoleId());
			result.setAssignAtr(role.getAssignAtr());
			result.setEmployeeReferenceRange(role.getEmployeeReferenceRange());
			result.setRoleType(role.getRoleType());
			result.setRoleCode(role.getRoleCode().toString());
			
			// get domain PersonRole
			Optional<PersonRole> personRoleOpt = personRoleRepo.find(role.getRoleId());
			if (personRoleOpt.isPresent())
				result.setReferFutureDate(personRoleOpt.get().getReferFutureDate());
		}
		return result;
	}
}
