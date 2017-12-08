package nts.uk.ctx.sys.auth.app.find._role;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.sys.auth.app.find.grant.rolesetjob.RoleSetDto;
import nts.uk.ctx.sys.auth.dom.grant.roleindividual.RoleIndividualGrant;
import nts.uk.ctx.sys.auth.dom.grant.roleindividual.RoleIndividualGrantRepository;
import nts.uk.ctx.sys.auth.dom.role.RoleRepository;
import nts.uk.ctx.sys.auth.dom.role.RoleType;
import nts.uk.ctx.sys.auth.dom.user.User;
import nts.uk.ctx.sys.auth.dom.user.UserRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class RoleTypeFinder {

	@Inject
	RoleIndividualGrantRepository roleIndividualGrantRepository;

	@Inject
	RoleRepository roleRepository;

	@Inject
	UserRepository userRepository;

	public InforRoleSetJobDto getAllByRoleType(int value) {
		String companyId = AppContexts.user().companyId();
		if (companyId == null)
			return null;

		List<RoleTypeDto> roleTypeDtos = new ArrayList<>();
		List<RoleDto> roleDtos = new ArrayList<>();
		List<RoleIndividualGrantDto> rGrantDto = new ArrayList<>();

		for (RoleType r : RoleType.values()) {
			roleTypeDtos.add(new RoleTypeDto(r.value, r.nameId, r.description));
		}
		
		if (roleTypeDtos.size() != 0) {
			roleDtos = roleRepository.findByType(companyId, value).stream()
					.map(item -> new RoleDto(item.getRoleId(), item.getRoleCode().v(), item.getName().v()))
					.collect(Collectors.toList());
		}
		
		if (roleDtos.size() != 0) {
			List<RoleIndividualGrant> listRoleIndividualGrant = null;

			List<String> listUserID = listRoleIndividualGrant.stream().map(c -> c.getUserId()).distinct()
					.collect(Collectors.toList());
			List<User> listUser = userRepository.getByListUser(listUserID);
			for (RoleIndividualGrant roleIndividualGrant : listRoleIndividualGrant) {
				String userName = listUser.stream().filter(c -> c.getUserID().equals(roleIndividualGrant.getUserId()))
						.findFirst().get().getUserName().v();
				rGrantDto.add(RoleIndividualGrantDto.fromDomain(roleIndividualGrant, userName));
			}
		}
		InforRoleSetJobDto inforRoleSetJobDto = new InforRoleSetJobDto(roleTypeDtos, roleDtos, rGrantDto);

		return inforRoleSetJobDto;
	}

	public List<RoleIndividualGrantDto> findByRoleId(String roleId) {
		String companyId = AppContexts.user().companyId();
		if (companyId == null)
			return null;
		
		List<RoleIndividualGrant> listRoleIndividualGrant = null;

		List<String> listUserID = listRoleIndividualGrant.stream().map(c -> c.getUserId()).distinct()
				.collect(Collectors.toList());
		List<User> listUser = userRepository.getByListUser(listUserID);

		List<RoleIndividualGrantDto> rGrantDto = new ArrayList<RoleIndividualGrantDto>();
		for (RoleIndividualGrant roleIndividualGrant : listRoleIndividualGrant) {
			String userName = listUser.stream().filter(c -> c.getUserID().equals(roleIndividualGrant.getUserId()))
					.findFirst().get().getUserName().v();
			rGrantDto.add(RoleIndividualGrantDto.fromDomain(roleIndividualGrant, userName));
		}

		return rGrantDto;
	}

	public RoleIndividualGrantDto findByRoleGrant(String userId, String roleId) {
		String companyId = AppContexts.user().companyId();
		if (companyId == null)
			return null;
		
		RoleIndividualGrant rGrant = null;
		User user = userRepository.getByUserID(userId).get();

		RoleIndividualGrantDto grantDto = RoleIndividualGrantDto.fromDomain(rGrant, user.getUserName().v());
		return grantDto;
	}
	

}
