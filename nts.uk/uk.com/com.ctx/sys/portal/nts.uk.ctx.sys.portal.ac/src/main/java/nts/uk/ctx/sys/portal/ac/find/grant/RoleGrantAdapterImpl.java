package nts.uk.ctx.sys.portal.ac.find.grant;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.pub.jobtitle.SyJobTitlePub;
import nts.uk.ctx.sys.auth.pub.grant.RoleIndividualGrantExportRepo;
import nts.uk.ctx.sys.auth.pub.grant.RoleSetGrantedPublisher;
import nts.uk.ctx.sys.auth.pub.role.RoleExport;
import nts.uk.ctx.sys.auth.pub.role.RoleExportRepo;
import nts.uk.ctx.sys.auth.pub.roleset.RoleSetPublisher;
import nts.uk.ctx.sys.auth.pub.user.UserPublisher;
import nts.uk.ctx.sys.portal.dom.adapter.role.AffJobHistoryDto;
import nts.uk.ctx.sys.portal.dom.adapter.role.DefaultRoleSetDto;
import nts.uk.ctx.sys.portal.dom.adapter.role.RoleDto;
import nts.uk.ctx.sys.portal.dom.adapter.role.RoleGrantAdapter;
import nts.uk.ctx.sys.portal.dom.adapter.role.RoleSetGrantedJobTitleDetailDto;
import nts.uk.ctx.sys.portal.dom.adapter.role.RoleSetGrantedPersonDto;
import nts.uk.ctx.sys.portal.dom.adapter.role.UserDto;

@Stateless
public class RoleGrantAdapterImpl implements RoleGrantAdapter {

	@Inject
	private RoleIndividualGrantExportRepo roleIndividualGrantRepo;
	
	@Inject
	private RoleExportRepo roleRepo;
	
	@Inject
	private UserPublisher userPublisher;
	
	@Inject
	private RoleSetGrantedPublisher roleSetGrantedPub;
	
	@Inject
	private RoleSetPublisher roleSetPub;
	
	@Inject
	private SyJobTitlePub jobTitlePub;
	
	@Override
	public List<String> getRoleId(String userId) {
		return roleIndividualGrantRepo.getByUser(userId).stream()
				.map(r -> r.getRoleId()).collect(Collectors.toList());
	}
	
	@Override
	public List<RoleDto> findRole(String roleId) {
		List<RoleExport> roles = roleRepo.findById(roleId);
		if (roles == null) return new ArrayList<>();
		return roles.stream().map(r -> new RoleDto(r.getCompanyId(), r.getRoleId(),
				r.getRoleCode(), r.getRoleName())).collect(Collectors.toList());
	}
	
	@Override
	public Optional<UserDto> getUserInfo(String userId) {
		return userPublisher.getUserInfo(userId).map(
				u -> new UserDto(u.getUserID(), u.getUserName(), u.getAssociatedPersonID()));
	}

	@Override
	public Optional<RoleSetGrantedPersonDto> getRoleSetPersonGrant(String employeeId) {
		return roleSetGrantedPub.getPersonGranted(employeeId)
				.map(r -> new RoleSetGrantedPersonDto(r.getRoleSetCd(), r.getCompanyId(), r.getValidPeriod(), r.getEmployeeID()));
	}
	
	@Override
	public Optional<RoleSetGrantedJobTitleDetailDto> getRoleSetJobTitleGrant(String companyId, String jobTitleId) {
		try {
		return roleSetGrantedPub.getJobTitleGranted(companyId).orElseThrow(RuntimeException::new).getDetails()
				.stream().filter(d -> jobTitleId.equals(d.getJobTitleId()))
				.map(d -> new RoleSetGrantedJobTitleDetailDto(d.getRoleSetCd(), d.getJobTitleId(), d.getCompanyId()))
				.findFirst();
		} catch (RuntimeException ex) {
			return Optional.empty();
		}
	}
	
	@Override
	public Optional<AffJobHistoryDto> getAffJobHist(String employeeId, GeneralDate baseDate) {
		return jobTitlePub.findSJobHistBySId(employeeId, baseDate)
				.map(j -> new AffJobHistoryDto(j.getEmployeeId(), j.getJobTitleID(), j.getJobTitleName(), j.getStartDate(), j.getEndDate()));
	}
	
	@Override
	public Optional<DefaultRoleSetDto> getDefaultRoleSet(String companyId) {
		return roleSetPub.getDefault(companyId).map(r -> new DefaultRoleSetDto(r.getCompanyId(), r.getRoleSetCd()));
	}
}
