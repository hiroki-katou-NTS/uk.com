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
import nts.uk.ctx.sys.shared.dom.user.password.HashPassword;
import nts.uk.ctx.sys.shared.dom.user.password.PassStatus;

public class Helper {
	
	static FailedAuthenticateTask anyTask = new FailedAuthenticateTask(
			Optional.of(AtomTask.none()),
			Optional.of(AtomTask.none()));
	static FailedAuthenticateTask noTask = new FailedAuthenticateTask(Optional.empty(), Optional.empty());
	
	static User USER =
			new User(
					DUMMY.USER_ID,
					DUMMY.IS_DEFAULT,
					new HashPassword(DUMMY.PASSWORD),
					new LoginID(DUMMY.LOGIN_ID),
					new ContractCode(DUMMY.CONTRACT_CODE),
					DUMMY.DATE,
					DisabledSegment.False,
					DisabledSegment.False,
					Optional.empty(),
					Optional.empty(),
					Optional.empty(),
					PassStatus.InitPassword
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
		static String CONTRACT_CODE = "contractCode";
		static String COMPANY_ID = "companyId";
		static String USER_ID = "user";
		static boolean IS_DEFAULT = true;
		static String PERSON_ID = "personId";
		static String LOGIN_ID = "employeeId";
		static String EMPLOYEE_ID = "employeeId";
		static String EMPLOYEE_CODE = "employeeCode";
		static String PASSWORD = "password";
		static SDelAtr DELETE_STATUS = SDelAtr.DELETED;
		static GeneralDateTime DATETIME = GeneralDateTime.now();
		static GeneralDate DATE = GeneralDate.today();
		static String REMOVE_REASON = "reason";
		static String EXTERNAL_CODE = "externalCode";
		static User USER = Helper.USER;
		static EmployeeDataMngInfoImport IMPORTED = Helper.IMPORTED;
		static IdentifiedEmployeeInfo EMP_INFO = new IdentifiedEmployeeInfo(Helper.DUMMY.IMPORTED, Helper.DUMMY.USER);
		static AccountLockPolicy ACCOUNT_LOCK_POLICY = AccountLockPolicy.createFromJavaType("", 0, 0, "", true);
		static LockoutData LOCKOUT_DATA = LockoutData.autoLock(new ContractCode(""),"",LoginMethod.NORMAL_LOGIN);
	}
		
}
