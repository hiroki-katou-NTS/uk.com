package nts.uk.ctx.at.auth.dom.employmentrole;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.time.GeneralDate;
import nts.arc.enums.EnumAdaptor;

/**
 * 
 * @author chungnt
 *
 */

@RunWith(JMockit.class)
public class GetEmployeesServiceTest {
	String companyId = "companyId";
	String employeeID = "employeeId";
	String userID = "userID";
	String roleID = "roleID";
	GeneralDate baseDate = GeneralDate.today();
	
	Map<String, String> map = new HashMap<String, String>();
	Map<String, String> mapCondition = new HashMap<String, String>();

	EmploymentRole employmentRole = new EmploymentRole(companyId, roleID);
	EmploymentRole employmentRole1 = new EmploymentRole(companyId, roleID,
			EnumAdaptor.valueOf(4, ScheduleEmployeeRef.class),
			null, null, null, null);

	@Injectable
	private GetEmployeesService.Require require;

	//// $ロール = require.ロールを取得する(ログイン社員の就業ロールID).isNotPresent()
	@Test
	public void testGetEmployeesServiceTestTest_1() {

		map.put("A", "A");
		map.put("B", "B");

		new Expectations() {
			{
				require.getEmploymentRoleById(companyId, roleID);
				
				require.getAllEmployees(companyId, baseDate);
				result = map;
			}
		};

		Map<String, String> result = GetEmployeesService.get(require, companyId, userID, employeeID, roleID, baseDate);

		assertThat((result).isEmpty()).isFalse();
		assertThat(result.get("A")).isEqualTo("A");
		assertThat(result.get("B")).isEqualTo("B");
	}
	
	////$ロール = require.ロールを取得する(ログイン社員の就業ロールID)
	// if $ロール.実績工数社員参照 != 社員参照範囲と同じ
	@Test
	public void testGetEmployeesServiceTestTest_2() {

		map.put("A", "A");
		map.put("B", "B");

		new Expectations() {
			{
				require.getEmploymentRoleById(companyId, roleID);
				result = Optional.of(employmentRole);

				require.getAllEmployees(companyId, baseDate);
				result = map;
			}
		};

		Map<String, String> resultTest = GetEmployeesService.get(require, companyId, userID, employeeID, roleID, baseDate);

		assertThat((resultTest).isEmpty()).isFalse();
		assertThat(resultTest.get("A")).isEqualTo("A");
		assertThat(resultTest.get("B")).isEqualTo("B");
	}

	//// $ロール = require.ロールを取得する(ログイン社員の就業ロールID)
	// if $ロール.実績工数社員参照 == 社員参照範囲と同じ
	@Test
	public void testGetEmployeesServiceTestTest_3() {

		mapCondition.put("A", "A");
		mapCondition.put("B", "B");

		new Expectations() {
			{
				require.getEmploymentRoleById(companyId, roleID);
				result = Optional.of(employmentRole1);

				require.getReferenceableEmployees(userID, employeeID, baseDate);
				result = mapCondition;
			}
		};

		Map<String, String> resultTest = GetEmployeesService.get(require, companyId, userID, employeeID, roleID,
				baseDate);

		assertThat((resultTest).isEmpty()).isFalse();
		assertThat(resultTest.get("A")).isEqualTo("A");
		assertThat(resultTest.get("B")).isEqualTo("B");
	}
}
