package nts.uk.ctx.sys.auth.pubimp.user;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.uk.ctx.bs.employee.pub.employee.EmployeeBasicInfoExport;
import nts.uk.ctx.bs.employee.pub.employee.SyEmployeePub;
import nts.uk.ctx.sys.auth.dom.user.UserRepository;
import nts.uk.shr.com.security.audittrail.UserInfoAdaptorForLog;
import nts.uk.shr.com.security.audittrail.correction.content.UserInfo;

/**
 * Publish UserInfo for Audit Trail logging (KIBAN).
 */
@Stateless
public class UserInfoAdaptorForLogImpl implements UserInfoAdaptorForLog {

	@Inject
	private UserRepository userRepo;
	
	@Inject
	private SyEmployeePub employeePub;
	
	@Override
	public UserInfo findByEmployeeId(String employeeId) {
		
		val employee = this.employeePub.findBySId(employeeId);
		if (employee == null) {
			throw new RuntimeException("employee not found: " + employeeId);
		}
		
		return this.getUserInfoByEmployee(employee);
	}

	@Override
	public List<UserInfo> findByEmployeeId(List<String> employeeIds) {

		val employees = this.employeePub.findBySIds(employeeIds);
		return employees.stream()
				.map(e -> this.getUserInfoByEmployee(e))
				.collect(Collectors.toList());
	}

	@Override
	public UserInfo findByUserId(String userId) {

		return this.userRepo.getByUserID(userId)
				.map(u -> UserInfo.user(userId,
						u.getUserName().isPresent() ? u.getUserName().get().v() : ""))
				.get();
	}

	@Override
	public List<UserInfo> findByUserId(List<String> userIds) {

		return this.userRepo.getByListUser(userIds).stream()
				.map(u -> UserInfo.user(u.getUserID(),
						u.getUserName().isPresent() ? u.getUserName().get().v() : ""))
				.collect(Collectors.toList());
	}


	private UserInfo getUserInfoByEmployee(EmployeeBasicInfoExport employee) {
		return this.userRepo.getByAssociatedPersonId(employee.getPId())
				.map(u -> UserInfo.employee(u.getUserID(), employee.getEmployeeId(), employee.getPName()))
				.get();
	}
	
	@Override
	public UserInfo findByEmployeeIdAndCompanyId(String employeeId, String companyId) {
		
		val employee = this.employeePub.findBySIdAndCompanyId(employeeId, companyId);
		if (employee == null) {
			throw new RuntimeException("employee not found: " + employeeId);
		}
		
		return this.getUserInfoByEmployee(employee);
	}
}
