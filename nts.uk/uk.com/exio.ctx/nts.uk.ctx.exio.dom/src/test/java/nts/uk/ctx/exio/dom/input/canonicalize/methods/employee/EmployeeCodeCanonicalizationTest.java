package nts.uk.ctx.exio.dom.input.canonicalize.methods.employee;

import java.util.Optional;

import org.junit.Test;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDataMngInfo;
import nts.uk.ctx.exio.dom.input.canonicalize.Helper;
import nts.uk.ctx.exio.dom.input.canonicalize.methods.CanonicalizationMethodRequire;
import nts.uk.ctx.exio.dom.input.canonicalize.methods.EmployeeCodeCanonicalization;

public class EmployeeCodeCanonicalizationTest {

	@Injectable
	CanonicalizationMethodRequire.Require require;
	
	@Test
	public void single(@Injectable EmployeeDataMngInfo employeeData) {
		
		String employeeCode = "code";
		String employeeId = "id";

		int itemNoEmployeeCode = 1;
		int itemNoEmployeeId = 2;
		
		val revisedData = Helper.Revised.item(itemNoEmployeeCode, employeeCode);
		
		new Expectations() {{
			employeeData.getEmployeeId();
			result = employeeId;
			
			require.getEmployeeDataMngInfoByEmployeeCode(employeeCode);
			result = Optional.of(employeeData);
		}};
		
		val target = new EmployeeCodeCanonicalization(itemNoEmployeeCode, itemNoEmployeeId);
		
		val actual = target.canonicalize(require, revisedData);
		
		Helper.asserts(actual)
			.equal(itemNoEmployeeCode, employeeCode)
			.equal(itemNoEmployeeId, employeeId);
	}
}
