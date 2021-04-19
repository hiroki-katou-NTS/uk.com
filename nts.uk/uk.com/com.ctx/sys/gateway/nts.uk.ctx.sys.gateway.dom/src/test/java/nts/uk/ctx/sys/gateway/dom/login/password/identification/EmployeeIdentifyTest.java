package nts.uk.ctx.sys.gateway.dom.login.password.identification;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.Test;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import nts.uk.ctx.sys.gateway.dom.login.password.identification.helper.Helper;
import nts.uk.ctx.sys.shared.dom.employee.EmployeeDataMngInfoImport;
import nts.uk.ctx.sys.shared.dom.user.User;

public class EmployeeIdentifyTest {

	@Injectable
	private EmployeeIdentify.Require require;
	
	@Test  
	public void success() {
		String employeeID = "EMPEMPID";
		Optional<EmployeeDataMngInfoImport> res =Optional.of(Helper.setEmployeeID(employeeID));
		String userID = "USEUSERID"; 
		val dummyUser = new Helper.DummyUser.Builder().userID(userID).addDay(1).build();
		Optional<User> user = Optional.of(dummyUser);
		new Expectations() {{
			require.getEmployeeDataMngInfoImportByEmployeeCode("", "");
			result = res;
			
			require.getUserByPersonId("");
			result = user;
		}};
		
		val re = EmployeeIdentify.identifyByEmployeeCode(require, "", "");
		
		assertThat(re.isSuccess()).isTrue();
		assertThat(re.getEmployeeInfo().get().getEmployee().getEmployeeId()).isEqualTo(employeeID);
		assertThat(re.getEmployeeInfo().get().getUser().getUserID()).isEqualTo(userID);
		assertThat(re.getFailureLog()).isEqualTo(Optional.empty());
	}
	
	@Test
	public void unableToIdentifyEmployeeByCode() {
		new Expectations() {{
			require.getEmployeeDataMngInfoImportByEmployeeCode("", "");
			result = Optional.empty();
		}};
		
		val result = EmployeeIdentify.identifyByEmployeeCode(require, "", "");
		
		assertThat(result.isFailed()).isTrue();
		assertThat(result.getEmployeeInfo()).isEqualTo(Optional.empty());
	}

	@Test
	public void unableToIdentifyUserByUserId() {
		Optional<EmployeeDataMngInfoImport> dummyImport =Optional.of( Helper.dummyImported);
		new Expectations() {{
			require.getEmployeeDataMngInfoImportByEmployeeCode("", "");
			result = dummyImport;
			
			require.getUserByPersonId("");
			result = Optional.empty();
		}};
		
		val result = EmployeeIdentify.identifyByEmployeeCode(require, "", "");
		
		assertThat(result.isFailed()).isTrue();
		assertThat(result.getEmployeeInfo()).isEqualTo(Optional.empty());
	}
	
	@Test
	public void expiredUser() {
		Optional<EmployeeDataMngInfoImport> res =Optional.of(Helper.dummyImported);
		Optional<User> user = Optional.of(new Helper.DummyUser.Builder().build());
		new Expectations() {{
			require.getEmployeeDataMngInfoImportByEmployeeCode("", "");
			result = res;
			
			require.getUserByPersonId("");
			result = user;
		}};
		
		val result = EmployeeIdentify.identifyByEmployeeCode(require, "", "");
		
		assertThat(result.isFailed()).isTrue();
		assertThat(result.getEmployeeInfo()).isEqualTo(Optional.empty());
	}
}
