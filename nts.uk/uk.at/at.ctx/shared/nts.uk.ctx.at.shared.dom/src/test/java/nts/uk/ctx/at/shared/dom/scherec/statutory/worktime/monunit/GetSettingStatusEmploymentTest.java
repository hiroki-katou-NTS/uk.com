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
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.emp.EmpFlexMonthActCalSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.emp.EmpDeforLaborMonthActCalSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.emp.EmpRegulaMonthActCalSet;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSet.LaborWorkTypeAttr;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.defor.DeforLaborTimeEmp;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.regular.RegularLaborTimeEmp;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;

@RunWith(JMockit.class)
public class GetSettingStatusEmploymentTest {

	@Injectable
	private GetSettingStatusEmployment.Require require;

	private static String CID = "000000000000-0001";

	private static String EMPCD1 = "05";

	private static String EMPCD2 = "KO";

	private static String EMPCD3 = "08";
	
	private static String EMPCD4 = "69";

	@Test
	public void getter() {
		GetSettingStatusEmployment employment = new GetSettingStatusEmployment();
		NtsAssert.invokeGetters(employment);
	}

	@Test
	public void testRegular() {

		new Expectations() {
			{
				require.findEmploymentbyCid(CID, LaborWorkTypeAttr.REGULAR_LABOR);
				result = Arrays.asList(
						MonthlyWorkTimeSetEmp.of(null, new EmploymentCode(EMPCD1), null, null, null),
						MonthlyWorkTimeSetEmp.of(null, new EmploymentCode(EMPCD3), null, null, null)
						);

				require.findListByCid(CID);
				result = Arrays.asList(
						RegularLaborTimeEmp.of(null, new EmploymentCode(EMPCD2), null, null),
						RegularLaborTimeEmp.of(null, new EmploymentCode(EMPCD3), null, null)
						);

				require.findByCid(CID);
				result = Arrays.asList(
						EmpRegulaMonthActCalSet.of(new EmploymentCode(EMPCD4), null, null, null)
						);

			}
		};

		List<String> empCds = GetSettingStatusEmployment.getSettingEmployment(require,
				"000000000000-0001", LaborWorkTypeAttr.REGULAR_LABOR);

		assertThat(empCds).isEqualTo(Arrays.asList(EMPCD1, EMPCD3, EMPCD2, EMPCD4));

	}

	@Test
	public void testDefor() {

		new Expectations() {
			{
				require.findEmploymentbyCid(CID, LaborWorkTypeAttr.DEFOR_LABOR);
				result = Arrays.asList(MonthlyWorkTimeSetEmp.of(null, new EmploymentCode(EMPCD1), null, null, null),
						MonthlyWorkTimeSetEmp.of(null, new EmploymentCode(EMPCD3), null, null, null));

				require.findDeforLaborByCid(CID);
				result = Arrays.asList(DeforLaborTimeEmp.of(null, new EmploymentCode(EMPCD2), null, null),
						DeforLaborTimeEmp.of(null, new EmploymentCode(EMPCD3), null, null));

				require.findEmpDeforLabor(CID);
				result = Arrays.asList(EmpDeforLaborMonthActCalSet.of(new EmploymentCode(EMPCD4), null, null, null, null, null));
			}
		};

		List<String> empCds = GetSettingStatusEmployment.getSettingEmployment(require,
				"000000000000-0001", LaborWorkTypeAttr.DEFOR_LABOR);

		assertThat(empCds).isEqualTo(Arrays.asList(EMPCD1, EMPCD3, EMPCD2, EMPCD4));

	}

	@Test
	public void testFlex() {

		new Expectations() {
			{
				require.findEmploymentbyCid(CID, LaborWorkTypeAttr.FLEX);
				result = Arrays.asList(MonthlyWorkTimeSetEmp.of(null, new EmploymentCode(EMPCD1), null, null, null),
						MonthlyWorkTimeSetEmp.of(null, new EmploymentCode(EMPCD3), null, null, null));

				require.findEmpFlexMonthByCid(CID);
				result = Arrays.asList(EmpFlexMonthActCalSet.of(null, null, null, null, null, new EmploymentCode(EMPCD2)),
						EmpFlexMonthActCalSet.of(null, null, null, null, null, new EmploymentCode(EMPCD3)));

			}
		};

		List<String> empCds = GetSettingStatusEmployment.getSettingEmployment(require,
				"000000000000-0001", LaborWorkTypeAttr.FLEX);

		assertThat(empCds).isEqualTo(Arrays.asList(EMPCD1, EMPCD3, EMPCD2));

	}

}
