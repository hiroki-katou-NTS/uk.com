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
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.wkp.WkpFlexMonthActCalSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.wkp.WkpDeforLaborMonthActCalSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.wkp.WkpRegulaMonthActCalSet;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSet.LaborWorkTypeAttr;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.defor.DeforLaborTimeWkp;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.regular.RegularLaborTimeWkp;
@RunWith(JMockit.class)
public class GeSettingStatusForEachWorkplaceTest {

	@Injectable
	private GeSettingStatusForEachWorkplace.Require require;

	private static String CID = "000000000000-0001";

	private static String WKP1 = "4865aea2-48cb-43ed-b1c5-150e24f7fa99";

	private static String WKP2 = "e1543f3e-d144-42e6-bcf4-665ee46aef9c";

	private static String WKP3 = "509a8d6a-d644-413e-85da-fbae5776e5ce";
	
	private static String WKP4 = "1b70774e-8e1c-4b4d-a71d-153261886c74";
	
	
	@Test
	public void getter() {
		GeSettingStatusForEachWorkplace eachWorkplace = new GeSettingStatusForEachWorkplace();
		NtsAssert.invokeGetters(eachWorkplace);
	}

	@Test
	public void testRegular() {
		
		new Expectations() {
			{
				require.findWorkplace(CID, LaborWorkTypeAttr.REGULAR_LABOR);
				result = Arrays.asList(
						MonthlyWorkTimeSetWkp.of(null, WKP1, null, null, null),
						MonthlyWorkTimeSetWkp.of(null, WKP3, null, null, null));
				
				require.findAll(CID);
				result = Arrays.asList(
						RegularLaborTimeWkp.of(null, WKP2, null,null),
						RegularLaborTimeWkp.of(null, WKP3, null,null));

				require.findWkpRegulaMonthAll(CID);
				result = Arrays.asList(
						WkpRegulaMonthActCalSet.of(WKP4, null, null,null));

			}
		};

		List<String> wkpIds = GeSettingStatusForEachWorkplace.geSettingStatusForEachWorkplace(require,
				"000000000000-0001", LaborWorkTypeAttr.REGULAR_LABOR);

		assertThat(wkpIds).isEqualTo(Arrays.asList(WKP1, WKP3, WKP2, WKP4));

	}
	

	
	@Test
	public void testDefor() {
		
		new Expectations() {
			{
				require.findWorkplace(CID, LaborWorkTypeAttr.DEFOR_LABOR);
				result = Arrays.asList(
						MonthlyWorkTimeSetWkp.of(null, WKP1, null, null, null),
						MonthlyWorkTimeSetWkp.of(null, WKP3, null, null, null)
						);
				
				require.findDeforLaborTimeWkpByCid(CID);
				result = Arrays.asList(
						DeforLaborTimeWkp.of(null, WKP2, null, null),
						DeforLaborTimeWkp.of(null, WKP3, null, null));

				require.findAllByCid(CID);
				result = Arrays.asList(
						WkpDeforLaborMonthActCalSet.of(WKP4, null, null, null, null, null)
						);

			}
		};

		List<String> wkpIds = GeSettingStatusForEachWorkplace.geSettingStatusForEachWorkplace(require,
				"000000000000-0001", LaborWorkTypeAttr.DEFOR_LABOR);

		assertThat(wkpIds).isEqualTo(Arrays.asList(WKP1, WKP3, WKP2, WKP4));

	}
	
	@Test
	public void testFlex() {
		
		new Expectations() {
			{
				require.findWorkplace(CID, LaborWorkTypeAttr.FLEX);
				result = Arrays.asList(
						MonthlyWorkTimeSetWkp.of(null, WKP1, null, null, null),
						MonthlyWorkTimeSetWkp.of(null, WKP3, null, null, null)
						);
				
				require.findByCid(CID);
				result = Arrays.asList(
						WkpFlexMonthActCalSet.of(null,null, null, null, null, WKP2),
						WkpFlexMonthActCalSet.of(null,null, null, null, null, WKP3) );

			}
		};

		List<String> wkpIds = GeSettingStatusForEachWorkplace.geSettingStatusForEachWorkplace(require,
				"000000000000-0001", LaborWorkTypeAttr.FLEX);

		assertThat(wkpIds).isEqualTo(Arrays.asList(WKP1, WKP3, WKP2));

	}

}
