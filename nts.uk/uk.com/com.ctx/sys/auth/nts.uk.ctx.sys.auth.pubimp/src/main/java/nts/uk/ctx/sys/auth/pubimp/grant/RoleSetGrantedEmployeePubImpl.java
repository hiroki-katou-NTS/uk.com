package nts.uk.ctx.sys.auth.pubimp.grant;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.Stateless;
import javax.inject.Inject;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.sys.auth.dom.adapter.workplace.WorkplaceAdapter;
import nts.uk.ctx.sys.auth.dom.algorithm.CanApprovalOnBaseDateService;
import nts.uk.ctx.sys.auth.dom.grant.rolesetperson.RoleSetGrantedPerson;
import nts.uk.ctx.sys.auth.dom.grant.rolesetperson.RoleSetGrantedPersonRepository;
import nts.uk.ctx.sys.auth.dom.roleset.ApprovalAuthority;
import nts.uk.ctx.sys.auth.dom.roleset.RoleSet;
import nts.uk.ctx.sys.auth.dom.roleset.RoleSetRepository;
import nts.uk.ctx.sys.auth.pub.grant.RoleSetGrantedEmployeePub;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Stateless
public class RoleSetGrantedEmployeePubImpl implements RoleSetGrantedEmployeePub {

	@Inject
	private RoleSetRepository roleSetRepo;

	@Inject
	private RoleSetGrantedPersonRepository roleSetPersonRepo;

	@Inject
	private WorkplaceAdapter wkpAdapter;

	
	@Inject
	private CanApprovalOnBaseDateService canApprovalOnBaseDateService;
	
	@Override
	public List<String> findEmpGrantedInWorkplace(String workplaceId, DatePeriod period) {
		String companyId = AppContexts.user().companyId();

		// Execute the algorithm "Acquire Employees from the Workplace"
		List<String> empIds = wkpAdapter.findListSIdByCidAndWkpIdAndPeriod(workplaceId, period.start(), period.end())
				.stream().map(item -> item.getEmployeeId()).collect(Collectors.toList());

		// Acquire the domain model "Role set"
		List<RoleSet> roleSets = roleSetRepo.findByCompanyId(companyId).stream().filter(item -> item.getApprovalAuthority() == ApprovalAuthority.HasRight).collect(Collectors.toList());

		// Acquire domain model "Role set Granted person"
		List<RoleSetGrantedPerson> roleSetPersons = new ArrayList<>();
		for (RoleSet roleSet : roleSets) {
			List<RoleSetGrantedPerson> tmp = roleSetPersonRepo.getAll(roleSet.getRoleSetCd().v(), companyId);
			if (tmp != null && !tmp.isEmpty()) {
				tmp.stream().filter(item -> item.getValidPeriod().contains(period)).collect(Collectors.toList());
				roleSetPersons.addAll(tmp);
			}
		}
		List<String> empIds2 = roleSetPersons.stream().filter(item -> empIds.contains(item.getEmployeeID())).map(item -> item.getEmployeeID()).collect(Collectors.toList());

		return empIds2;
	}

	@Override
	public boolean canApprovalOnBaseDate(String companyId, String employeeID, GeneralDate date) {
		return canApprovalOnBaseDateService.canApprovalOnBaseDate(companyId, employeeID, date);
	}

}
