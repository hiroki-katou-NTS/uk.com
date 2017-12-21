package nts.uk.ctx.sys.auth.dom.grant.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.uk.ctx.sys.auth.dom.adapter.person.PersonAdapter;
import nts.uk.ctx.sys.auth.dom.adapter.person.PersonImport;
import nts.uk.ctx.sys.auth.dom.grant.roleindividual.RoleIndividualGrant;
import nts.uk.ctx.sys.auth.dom.grant.roleindividual.RoleIndividualGrantRepository;
import nts.uk.ctx.sys.auth.dom.role.Role;
import nts.uk.ctx.sys.auth.dom.role.RoleRepository;
import nts.uk.ctx.sys.auth.dom.role.RoleType;
import nts.uk.ctx.sys.auth.dom.user.User;
import nts.uk.ctx.sys.auth.dom.user.UserRepository;

@Stateless
public class RoleIndividualComServiceImpl implements RoleIndividualComService {

	@Inject
	private RoleIndividualGrantRepository roleIndividualGrantRepo;

	@Inject
	private RoleRepository roleRepo;

	@Inject
	private UserRepository userRepo;

	@Inject
	private PersonAdapter personAdapter;

	@Override
	public List<RoleIndividualGrant> selectByRoleType(int roleType) {
		List<RoleIndividualGrant> listRoleIndividualGrant = roleIndividualGrantRepo.findByRoleType(roleType);
		List<String> listUserID = listRoleIndividualGrant.stream().map(c-> c.getUserId()).collect(Collectors.toList());
		List<User> listUser = userRepo.getByListUser(listUserID);
		
		//List<PersonImport> listPerson = personAdapter.findByPersonIds(listAssPersonID);
		return listRoleIndividualGrant;
	}

}
