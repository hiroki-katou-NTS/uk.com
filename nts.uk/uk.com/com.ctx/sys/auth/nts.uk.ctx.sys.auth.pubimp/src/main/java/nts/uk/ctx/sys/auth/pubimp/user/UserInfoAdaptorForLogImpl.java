package nts.uk.ctx.sys.auth.pubimp.user;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
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
		
		return this.userRepo.getByAssociatedPersonId(employee.getPId())
				.map(u -> UserInfo.employee(u.getUserID(), employeeId, employee.getPName()))
				.get();
	}

	@Override
	public UserInfo findByUserId(String userId) {

		return this.userRepo.getByUserID(userId)
				.map(u -> UserInfo.user(userId, u.getUserName().v()))
				.get();
	}

}
