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
	public boolean isExist(String userID, String companyID, RoleType roleType) {
		Optional<RoleIndividualGrant> roleIndividualGrant = roleIndividualGrantRepo.findRoleIndividualGrant(userID, companyID, roleType);
		return roleIndividualGrant.isPresent();
	}

	@Override
	public void create(RoleIndividualGrant roleIndividualGrant) {
		// Check exist UserID
		if (roleIndividualGrant.getUserId() == null) {
			throw new BusinessException("Msg_218");
		}
		// check Exist roleIndividualGrant
		if (!isExist(roleIndividualGrant.getUserId(), roleIndividualGrant.getCompanyId(), roleIndividualGrant.getRoleType())) {
			throw new BusinessException("Msg_716");
		}
		// Create RoleIndividualGrant
		roleIndividualGrantRepo.add(roleIndividualGrant);
	}

	@Override
	public void update(RoleIndividualGrant roleIndividualGrant) {
		if (roleIndividualGrant.getUserId() == null) {
			throw new BusinessException("Msg_218");
		}
		// Update RoleIndividualGrant
		roleIndividualGrantRepo.update(roleIndividualGrant);
	}

	@Override
	public void remove(String userID, String companyID, RoleType roleType) {
		// Remove RoleIndividualGrant
		roleIndividualGrantRepo.remove(userID, companyID, roleType);
	}

	/*
	 * @Override public searchRoleIndividualGrant(String companyID, int
	 * roleType) { //Get ListRole List<Role> listRole =
	 * roleRepo.findByType(companyID, roleType); List<String> listRoleID =
	 * listRole.stream().map(c -> c.getRoleId()).collect(Collectors.toList());
	 * // Get RoleIndividualGrant by roleID companyID List<RoleIndividualGrant>
	 * listRoleIndividualGrant =
	 * roleIndividualGrantRepo.findBycomIDandRoldID(companyID, listRoleID);
	 * List<String> listUserId = listRoleIndividualGrant.stream().map(c ->
	 * c.getUserId()).collect(Collectors.toList()); // Get User by userID
	 * 
	 * List<User> listUser = userRepo.getByListUser(listUserId); List<String>
	 * listAssPersonID =listUser.stream().map(c
	 * ->c.getAssociatedPersonID()).collect(Collectors.toList());
	 * 
	 * if(listAssPersonID!= null){ //listAssPersonID = personIds
	 * List<PersonImport> listPerson =
	 * personAdapter.findByPersonIds(listUserId); if(listPerson!=null){
	 * //ドメインモデル「ロール個人別付与」を取得する //Acquire domain model "Role individual grant"
	 * // List <RoleIndividualGrant> 1listRoleIndividualGrant =
	 * roleIndividualGrantRepo.find(listUserId, listRoleID, companyID); } }
	 * 
	 * 
	 * 
	 * }
	 */

}
