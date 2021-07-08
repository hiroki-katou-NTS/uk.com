package nts.uk.ctx.sys.gateway.dom.login.password.authenticate;

import java.util.Optional;

import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.sys.gateway.dom.login.IdentifiedEmployeeInfo;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.password.PasswordPolicy;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.password.Helper;
import nts.uk.ctx.sys.shared.dom.employee.EmployeeDataMngInfoImport;
import nts.uk.ctx.sys.shared.dom.employee.SDelAtr;
import nts.uk.ctx.sys.shared.dom.user.ContractCode;
import nts.uk.ctx.sys.shared.dom.user.DisabledSegment;
import nts.uk.ctx.sys.shared.dom.user.LoginID;
import nts.uk.ctx.sys.shared.dom.user.User;

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
					new LoginID(DUMMY.LOGIN_ID),
					new ContractCode(DUMMY.CONTRACT_CD),
					DUMMY.DATE,
					DisabledSegment.False,
					DisabledSegment.False,
					Optional.empty(),
					Optional.empty(),
					Optional.empty()
					);
	
	public static class DUMMY{
		static final String CONTRACT_CD = "contractCode";
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
		static final GeneralDate DATE = GeneralDate.min();
		static final String REMOVE_REASON = "reason";
		static final String EXTERNAL_CODE = "externalCode";
		static final FailedAuthenticateTask FAILED_TASKS = new FailedAuthenticateTask(Optional.of(AtomTask.none()),Optional.of(AtomTask.none()));
		static final EmployeeDataMngInfoImport IMPORTED = FailedPasswordHelper.IMPORTED;
		static final IdentifiedEmployeeInfo EMP_INFO = new IdentifiedEmployeeInfo(PasswordAuthenticateWithEmployeeCodeTestHelper.IMPORTED, PasswordAuthenticateWithEmployeeCodeTestHelper.USER);
		static final PasswordPolicy PASSWORD_POLICY = 	Helper.Policy.builder().build();
	}
}
