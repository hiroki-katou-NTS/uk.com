package nts.uk.ctx.sys.auth.dom.algorithm;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.sys.auth.dom.adapter.employee.JobTitleAdapter;
import nts.uk.ctx.sys.auth.dom.employee.dto.JobTitleValueImport;
import nts.uk.ctx.sys.auth.dom.grant.rolesetjob.RoleSetGrantedJobTitleRepository;
import nts.uk.ctx.sys.auth.dom.grant.rolesetperson.RoleSetGrantedPerson;
import nts.uk.ctx.sys.auth.dom.grant.rolesetperson.RoleSetGrantedPersonRepository;
import nts.uk.ctx.sys.auth.dom.roleset.ApprovalAuthority;
import nts.uk.ctx.sys.auth.dom.roleset.RoleSet;
import nts.uk.ctx.sys.auth.dom.roleset.RoleSetRepository;

@Stateless
public class CanApprovalOnBaseDateServiceImpl implements CanApprovalOnBaseDateService {

	@Inject
	private RoleSetGrantedPersonRepository roleSetPersonRepo;

	@Inject
	private JobTitleAdapter jobTitleAdapter;

	@Inject
	private RoleSetGrantedJobTitleRepository roleSetGrantedJobTitleRepo;

	@Inject
	private RoleSetRepository roleSetRepo;

	@Override
	public boolean canApprovalOnBaseDate(String companyId, String employeeID, GeneralDate date) {
		// Acquire domain model "Role set Individual Grant"
		String roleSetCode = "";
		Optional<RoleSetGrantedPerson> roleSetGrand = roleSetPersonRepo.findByIDAndDate(companyId, employeeID, date);
		if (!roleSetGrand.isPresent()) {
			JobTitleValueImport jobTitle = jobTitleAdapter.findJobTitleBySid(employeeID, date);

			Optional<String> roleJobTitle = roleSetGrantedJobTitleRepo.getRoleSetCd(companyId, jobTitle.getPositionId());
			if (roleJobTitle.isPresent()) {
				roleSetCode = roleJobTitle.get();
			}
		} else {
			roleSetCode = roleSetGrand.get().getRoleSetCd().toString();
		}
		// Acquire domain model "Roll set"
		Optional<RoleSet> roleSet = roleSetRepo.findByCidRollSetCDAuthor(companyId, roleSetCode.toString(), ApprovalAuthority.HasRight.value);
		if (roleSet.isPresent()) {
			return true;
		} else {
			return false;
		}
	}

}
