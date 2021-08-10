package nts.uk.ctx.sys.gateway.dom.login.password.identification.helper;

import java.util.Optional;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.sys.shared.dom.employee.EmployeeDataMngInfoImport;
import nts.uk.ctx.sys.shared.dom.employee.SDelAtr;
import nts.uk.ctx.sys.shared.dom.user.ContractCode;
import nts.uk.ctx.sys.shared.dom.user.DisabledSegment;
import nts.uk.ctx.sys.shared.dom.user.LoginID;
import nts.uk.ctx.sys.shared.dom.user.User;

public class Helper {
	public static EmployeeDataMngInfoImport dummyImported = 
			new EmployeeDataMngInfoImport(
					DUMMY.COMPANY_ID, 
					DUMMY.PERSON_ID, 
					DUMMY.EMPLOYEE_ID, 
					DUMMY.EMPLOYEE_CD, 
					SDelAtr.NOTDELETED, 
					GeneralDateTime.min(), 
					DUMMY.REMOVE_REASON, 
					DUMMY.EXTERNAL_CODE);
	public static EmployeeDataMngInfoImport setEmployeeID(String employeeID) {
		val imported = dummyImported;
		imported.setEmployeeId(employeeID);
		return imported;
	}
	

	public static class DummyUser{
		static final User BASE = new User(
				DUMMY.USER_ID, 
				false, 
				new LoginID(DUMMY.LOGIN_ID),
				new ContractCode(DUMMY.CONTRACT_CODE), 
				GeneralDate.min(), 
				DisabledSegment.False, 
				DisabledSegment.False, 
				Optional.empty(), 
				Optional.empty(), 
				Optional.empty());
		
		public static class Builder{
			String userID = BASE.getUserID();
			GeneralDate expirationDate = BASE.getExpirationDate();
			
			public Builder userID(String userID) {
				this.userID = userID;
				return this;
			}
			
			public Builder expirationDate(GeneralDate ymd) {
				this.expirationDate = ymd;
				return this;
			}
			
			public Builder addDay(int afterDays) {
				this.expirationDate = this.expirationDate.addDays(afterDays);
				return this;
			}
			
			public User build() {
				return new User(
						this.userID, 
						BASE.isDefaultUser(), 
						BASE.getLoginID(),
						BASE.getContractCode(),
						expirationDate,
						BASE.getSpecialUser(),
						BASE.getMultiCompanyConcurrent(),
						BASE.getMailAddress(),
						BASE.getUserName(),
						BASE.getAssociatedPersonID());
			}
		}
	}
	public static class DUMMY{
		public final static String CONTRACT_CODE = "contractCode"; 
		public final static String COMPANY_ID = "companyId"; 
		public final static String USER_ID = "userId"; 
		public final static String PERSON_ID = "personId";
		public final static String EMPLOYEE_ID = "employeeId";
		public final static String LOGIN_ID = "loginId";
		public final static String EMPLOYEE_CD = "employeeCode";
		public final static String HASHPASSWORD = "dummyHashPassword";
		public final static String REMOVE_REASON = "removeReason";
		public final static String EXTERNAL_CODE = "externalCode";
	}
}
