package nts.uk.ctx.sys.gateway.dom.login.password.authenticate;

import java.util.Optional;

import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.sys.gateway.dom.login.IdentifiedEmployeeInfo;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.acountlock.AccountLockPolicy;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.acountlock.locked.LockoutData;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.acountlock.locked.LoginMethod;
import nts.uk.ctx.sys.shared.dom.employee.EmployeeDataMngInfoImport;
import nts.uk.ctx.sys.shared.dom.employee.SDelAtr;
import nts.uk.ctx.sys.shared.dom.user.ContractCode;
import nts.uk.ctx.sys.shared.dom.user.DisabledSegment;
import nts.uk.ctx.sys.shared.dom.user.LoginID;
import nts.uk.ctx.sys.shared.dom.user.User;

public class FailedPasswordHelper {
	
	static FailedAuthenticateTask noTask = new FailedAuthenticateTask(Optional.empty(), Optional.empty());
	
	static User USER =
			new User(
					DUMMY.USER_ID,
					DUMMY.IS_DEFAULT,
					new LoginID(DUMMY.LOGIN_ID),
					new ContractCode(DUMMY.CONTRACT_CODE),
					DUMMY.DATE,
					DisabledSegment.False,
					DisabledSegment.False,
					Optional.empty(),
					Optional.empty(),
					Optional.empty()
					);
	
	static EmployeeDataMngInfoImport IMPORTED = 
			new EmployeeDataMngInfoImport(
					DUMMY.COMPANY_ID, 
					DUMMY.PERSON_ID, 
					DUMMY.EMPLOYEE_ID, 
					DUMMY.EMPLOYEE_CODE, 
					DUMMY.DELETE_STATUS, 
					DUMMY.DATETIME, 
					DUMMY.REMOVE_REASON, 
					DUMMY.EXTERNAL_CODE);
	
	static class DUMMY{
		static final String CONTRACT_CODE = "contractCode";
		static final String COMPANY_ID = "companyId";
		static final String USER_ID = "user";
		static final boolean IS_DEFAULT = true;
		static final String PERSON_ID = "personId";
		static final String LOGIN_ID = "employeeId";
		static final String EMPLOYEE_ID = "employeeId";
		static final String EMPLOYEE_CODE = "employeeCode";
		static final String PASSWORD = "password";
		static final SDelAtr DELETE_STATUS = SDelAtr.DELETED;
		static final GeneralDateTime DATETIME = GeneralDateTime.min();
		static final GeneralDate DATE = GeneralDate.today();
		static final String REMOVE_REASON = "reason";
		static final String EXTERNAL_CODE = "externalCode";
		static final User USER = FailedPasswordHelper.USER;
		static final EmployeeDataMngInfoImport IMPORTED = FailedPasswordHelper.IMPORTED;
		static final IdentifiedEmployeeInfo EMP_INFO = new IdentifiedEmployeeInfo(FailedPasswordHelper.DUMMY.IMPORTED, FailedPasswordHelper.DUMMY.USER);
		static final AccountLockPolicy ACCOUNT_LOCK_POLICY = AccountLockPolicy.createFromJavaType("", 0, 0, "", true);
		static final LockoutData LOCKOUT_DATA = LockoutData.autoLock(new ContractCode(""),"",LoginMethod.NORMAL_LOGIN);
		static final FailedAuthenticateTask FAILED_TASKS = new FailedAuthenticateTask(Optional.of(AtomTask.none()),Optional.of(AtomTask.none()));
	}
		
}
