package nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.sha.ShaFlexMonthActCalSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.sha.ShaDeforLaborMonthActCalSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.sha.ShaRegulaMonthActCalSet;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSet.LaborWorkTypeAttr;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.defor.DeforLaborTimeSha;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.regular.RegularLaborTimeSha;

@RunWith(JMockit.class)
public class GetSettingStatusForEachEmployeeTest {

	@Injectable
	private GetSettingStatusForEachEmployee.Require require;

	private static String CID = "000000000000-0001";

	private static String SID00001 = "ae7fe82e-a7bd-4ce3-adeb-5cd403a9d570";

	private static String SID00002 = "8f9edce4-e135-4a1e-8dca-ad96abe405d6";

	private static String SID00003 = "9787c06b-3c71-4508-8e06-c70ad41f042a";
	
	private static String SID00004 = "abe4cf98-45cd-4508-2321-c70ad41f042b";
	
	
	@Test
	public void getter() {
		GetSettingStatusForEachEmployee eachEmployee = new GetSettingStatusForEachEmployee();
		NtsAssert.invokeGetters(eachEmployee);
	}

	@Test
	public void testRegular() {
		
		new Expectations() {
			{
				require.findEmployeeByCid(CID, LaborWorkTypeAttr.REGULAR_LABOR);
				result = Arrays.asList(
						MonthlyWorkTimeSetSha.of(null, SID00001, null, null,null),
						MonthlyWorkTimeSetSha.of(null, SID00003, null, null,null));
				
				require.findAll(CID);
				result = Arrays.asList(
						RegularLaborTimeSha.of(null, SID00002, null,null),
						RegularLaborTimeSha.of(null, SID00003, null,null));

				require.findRegulaMonthActCalSetByCid(CID);
				result = Arrays.asList(
						ShaRegulaMonthActCalSet.of(SID00004, null, null, null));

			}
		};

		List<String> employeeIds = GetSettingStatusForEachEmployee.getSettingStatusForEachEmployee(require,
				"000000000000-0001", LaborWorkTypeAttr.REGULAR_LABOR);

		assertThat(employeeIds).isEqualTo(Arrays.asList(SID00001, SID00003, SID00002, SID00004));

	}
	
	@Test
	public void testFlex() {
		
		new Expectations() {
			{
				require.findEmployeeByCid(CID, LaborWorkTypeAttr.FLEX);
				result = Arrays.asList(
						MonthlyWorkTimeSetSha.of(null, SID00001, null, null,null),
						MonthlyWorkTimeSetSha.of(null, SID00003, null, null,null));
				
				require.findAllShaByCid(CID);
				result = Arrays.asList(
						ShaFlexMonthActCalSet.of(null,null,null,null,null,SID00002),
						ShaFlexMonthActCalSet.of(null,null,null,null,null,SID00003));

			}
		};

		List<String> employeeIds = GetSettingStatusForEachEmployee.getSettingStatusForEachEmployee(require,
				"000000000000-0001", LaborWorkTypeAttr.FLEX);

		assertThat(employeeIds).isEqualTo(Arrays.asList(SID00001, SID00003, SID00002));

	}
	
	@Test
	public void testDefor() {
		
		new Expectations() {
			{
				require.findEmployeeByCid(CID, LaborWorkTypeAttr.DEFOR_LABOR);
				result = Arrays.asList(
						MonthlyWorkTimeSetSha.of(null, SID00001, null, null,null),
						MonthlyWorkTimeSetSha.of(null, SID00003, null, null,null)
						);
				
				require.findDeforLaborTimeShaByCid(CID);
				result = Arrays.asList( 
						DeforLaborTimeSha.of(null, SID00002, null, null),
						DeforLaborTimeSha.of(null, SID00003, null, null));

				require.findByCid(CID);
				result = Arrays.asList(
						ShaDeforLaborMonthActCalSet.of(SID00004, null, null, null, null, null));

			}
		};

		List<String> employeeIds = GetSettingStatusForEachEmployee.getSettingStatusForEachEmployee(require,
				"000000000000-0001", LaborWorkTypeAttr.DEFOR_LABOR);

		assertThat(employeeIds).isEqualTo(Arrays.asList(SID00001, SID00003, SID00002, SID00004));

	}

}
