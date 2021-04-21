package nts.uk.ctx.sys.gateway.dom.login.password.authenticate;

import java.util.Optional;

import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.sys.gateway.dom.login.IdentifiedEmployeeInfo;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.acountlock.AccountLockPolicy;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.password.PasswordPolicy;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.password.PasswordPolicyTestHelper.DUMMY;
import nts.uk.ctx.sys.shared.dom.employee.EmployeeDataMngInfoImport;
import nts.uk.ctx.sys.shared.dom.employee.SDelAtr;
import nts.uk.ctx.sys.shared.dom.user.ContractCode;
import nts.uk.ctx.sys.shared.dom.user.DisabledSegment;
import nts.uk.ctx.sys.shared.dom.user.LoginID;
import nts.uk.ctx.sys.shared.dom.user.User;
import nts.uk.ctx.sys.shared.dom.user.password.HashPassword;
import nts.uk.ctx.sys.shared.dom.user.password.PassStatus;

public class PasswordAuthenticateWithEmployeeCodeTestHelper {
	
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
	
	public static class DUMMY{
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
		public static FailedAuthenticateTask FAILED_TASKS = new FailedAuthenticateTask(Optional.of(AtomTask.none()),Optional.of(AtomTask.none()));
		static EmployeeDataMngInfoImport IMPORTED = FailedPasswordHelper.IMPORTED;
		static IdentifiedEmployeeInfo EMP_INFO = new IdentifiedEmployeeInfo(PasswordAuthenticateWithEmployeeCodeTestHelper.IMPORTED, PasswordAuthenticateWithEmployeeCodeTestHelper.USER);
		static AccountLockPolicy ACCOUNT_LOCK_POLICY = AccountLockPolicy.createFromJavaType("", 0, 0, "", true);
		static PasswordPolicy PASSWORD_POLICY = 	new PasswordPolicy(DUMMY.CONTRACT_CD,DUMMY.NOTICE_PASSWORD_CHANGE,DUMMY.IS_LOGIN,DUMMY.INITIAL_PASSWORD_CHANGE,DUMMY.IS_USE,DUMMY.PASSWORD_HISTORY_COUNT,DUMMY.PASSWORD_VALIDATE_PERIOD,DUMMY.PASSWORD_COMPLEX);
	}
}
