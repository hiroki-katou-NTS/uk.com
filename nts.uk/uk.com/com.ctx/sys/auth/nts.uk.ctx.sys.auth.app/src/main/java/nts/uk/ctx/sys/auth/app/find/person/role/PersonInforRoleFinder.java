package nts.uk.ctx.sys.auth.app.find.person.role;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.sys.auth.dom.role.Role;
import nts.uk.ctx.sys.auth.dom.role.RoleRepository;
import nts.uk.ctx.sys.auth.dom.role.RoleType;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class PersonInforRoleFinder {
	@Inject
	private RoleRepository roleRepo;
	
	public PersonInfoRole find(){
		String companyId = AppContexts.user().companyId();
		Optional<Role> roleOPt= roleRepo.findByType(companyId, RoleType.PERSONAL_INFO);
		
		return null;		
	}
}
