package nts.uk.ctx.sys.auth.dom.grant.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.sys.auth.dom.grant.roleindividual.RoleIndividualGrant;
import nts.uk.ctx.sys.auth.dom.grant.roleindividual.RoleIndividualGrantRepository;
import nts.uk.ctx.sys.auth.dom.role.RoleType;
import nts.uk.ctx.sys.auth.dom.user.User;
import nts.uk.ctx.sys.auth.dom.user.UserRepository;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Stateless
public class RoleIndividualServiceImpl implements RoleIndividualService {

	@Inject
	private RoleIndividualGrantRepository roleIndividualGrantRepo;

	@Inject
	private UserRepository userRepository;	

	@Override
	public boolean checkSysAdmin(String userID, DatePeriod validPeriod) {

		List<RoleIndividualGrant> listRoleIndividualGrant = roleIndividualGrantRepo.findByUserAndRole(userID, RoleType.SYSTEM_MANAGER.value);
		if (listRoleIndividualGrant.isEmpty()) {
			return false;
		}

		// Create new List System Admin
		List<CheckSysAdmin> listCheckSysAdmin = new ArrayList<CheckSysAdmin>();
		listCheckSysAdmin.add(new CheckSysAdmin(userID, validPeriod.start(), validPeriod.end()));

		List<RoleIndividualGrant> listSysAdmin = roleIndividualGrantRepo.findByRoleType(RoleType.SYSTEM_MANAGER.value);

		List<RoleIndividualGrant> filterListRoleIndividualGrant = listSysAdmin.stream().filter(c -> !c.getUserId().equals(userID) && c.getRoleType().equals(RoleType.SYSTEM_MANAGER)).collect(Collectors.toList());

		List<String> userIds = filterListRoleIndividualGrant.stream().map(c -> c.getUserId()).collect(Collectors.toList());
		
		List<User> users = new ArrayList<User>();
		if (!userIds.isEmpty())
			users = userRepository.getByListUser(userIds);

		for (RoleIndividualGrant roleIndividualGrant : filterListRoleIndividualGrant) {
			User user = users.stream().filter(c -> c.getUserID().equals(roleIndividualGrant.getUserId())).findFirst().get();
			CheckSysAdmin checkSysAdmin = new CheckSysAdmin(userID, roleIndividualGrant.getValidPeriod().start(), roleIndividualGrant.getValidPeriod().end());

			if (roleIndividualGrant.getValidPeriod().end().after(user.getExpirationDate())) {
				checkSysAdmin.setEndDate(user.getExpirationDate());
			}
			listCheckSysAdmin.add(checkSysAdmin);
		}

		listCheckSysAdmin.sort((a, b) -> {
			return b.getEndDate().compareTo(a.getEndDate());
		});

		GeneralDate validStartDate = GeneralDate.max();
		GeneralDate validEndDate = GeneralDate.max();

		for (CheckSysAdmin checkSysAdmin : listCheckSysAdmin) {
			if (checkSysAdmin.getStartDate().before(validStartDate) && checkSysAdmin.getEndDate().afterOrEquals(validEndDate)) {
				validStartDate = checkSysAdmin.getStartDate();
			}
		}

		if (validStartDate.beforeOrEquals(GeneralDate.today()) && validEndDate.equals(GeneralDate.max())) {
			return true;
		}

		return false;

	}

	@Data
	@AllArgsConstructor
	protected class CheckSysAdmin {
		private String userID;
		private GeneralDate startDate;
		private GeneralDate endDate;
	}
}
