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
import nts.uk.ctx.sys.shared.dom.user.password.HashPassword;
import nts.uk.ctx.sys.shared.dom.user.password.PassStatus;

public class Helper {
	public static EmployeeDataMngInfoImport dummyImported = 
			new EmployeeDataMngInfoImport(
					"", 
					"", 
					"", 
					"", 
					SDelAtr.NOTDELETED, 
					GeneralDateTime.now(), 
					"", 
					"");
	public static EmployeeDataMngInfoImport setEmployeeID(String employeeID) {
		val imported = dummyImported;
		imported.setEmployeeId(employeeID);
		return imported;
	}
	

	public static class DummyUser{
		static final User BASE = new User(
				"", 
				false, 
				new HashPassword(""), 
				new LoginID(""),
				new ContractCode(""), 
				GeneralDate.today(), 
				DisabledSegment.False, 
				DisabledSegment.False, 
				Optional.empty(), 
				Optional.empty(), 
				Optional.empty(), 
				PassStatus.InitPassword);
		
		public static class Builder{
			String userID = BASE.getUserID();
			GeneralDate expirationDate = BASE.getExpirationDate();
			
			public Builder userID(String userID) {
				this.userID = userID;
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
						BASE.getPassword(),
						BASE.getLoginID(),
						BASE.getContractCode(),
						expirationDate,
						BASE.getSpecialUser(),
						BASE.getMultiCompanyConcurrent(),
						BASE.getMailAddress(),
						BASE.getUserName(),
						BASE.getAssociatedPersonID(),
						BASE.getPassStatus());
			}
		}
	}
}
