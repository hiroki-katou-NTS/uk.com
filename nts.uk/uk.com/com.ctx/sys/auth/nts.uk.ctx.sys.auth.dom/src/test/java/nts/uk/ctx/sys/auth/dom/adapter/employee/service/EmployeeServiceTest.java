package nts.uk.ctx.sys.auth.dom.adapter.employee.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.sys.auth.dom.adapter.employee.employeeinfo.EmployeeInformationImport;

/**
 * Test Domain service 社員の情報を取得する
 */
@RunWith(JMockit.class)
public class EmployeeServiceTest { 

	@Injectable
	private EmployeeService.Require require;

	@Mocked
	EmployeeInformationImport empImp = new EmployeeInformationImport("employeeId", true, "positionName", "wkpDisplayName");

	@Test
	public void getEmployeeInformation() {
		
		//Mocking
		String employeeId = "employeeId";
		GeneralDate date = GeneralDate.ymd(1999, 10, 7);
		new Expectations() {
			{
				require.findEmployeeInformation(employeeId, date);
				result = empImp;
			}
		};
		
		//Execute
		val instance = new EmployeeService();
		val result = (EmployeeInformationImport)NtsAssert.Invoke.privateMethod
				(
				instance, 
				"getEmployeeInformation", 
				require,
				employeeId,
				date
				);
		
		//Assertion
		assertThat(result.getEmployeeId()).isEqualTo(empImp.getEmployeeId());
		assertThat(result.getEmployeeId()).isEqualTo(empImp.getEmployeeId());
		assertThat(result.isEmployeeCharge()).isEqualTo(empImp.isEmployeeCharge());
		assertThat(result.getPositionName()).isEqualTo(empImp.getPositionName());
		assertThat(result.getWkpDisplayName()).isEqualTo(empImp.getWkpDisplayName());
	}
}
