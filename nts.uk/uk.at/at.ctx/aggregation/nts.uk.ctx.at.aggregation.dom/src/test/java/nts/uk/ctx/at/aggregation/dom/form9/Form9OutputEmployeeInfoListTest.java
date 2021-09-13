package nts.uk.ctx.at.aggregation.dom.form9;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle.LicenseClassification;

@RunWith(JMockit.class)
public class Form9OutputEmployeeInfoListTest {
	
	@Test
	public void testGetter(
				@Injectable List<Form9OutputEmployeeInfo> employeeInfos) {
		
		val result = new Form9OutputEmployeeInfoList(employeeInfos);
		
		NtsAssert.invokeGetters(result);
		
	}
	
	/**
	 * target: getEmployeeIdList
	 */
	@Test
	public void testGetEmployeeIdList() {
		
		val employeeInfos = Arrays.asList(
					Helper.createForm9OutputEmployeeInfo("sid_1")
				,	Helper.createForm9OutputEmployeeInfo("sid_2")
				,	Helper.createForm9OutputEmployeeInfo("sid_3")
				,	Helper.createForm9OutputEmployeeInfo("sid_4"));
		
		val outputEmployeeInfos = new Form9OutputEmployeeInfoList(employeeInfos);
		
		//Act
		val result = outputEmployeeInfos.getEmployeeIdList();
		
		//Assert
		assertThat( result ).containsExactly("sid_1", "sid_2", "sid_3", "sid_4");
		
	}
	
	private static class Helper{
		/**
		 * 様式９の出力社員情報を作る
		 * @param employeeId 社員ＩＤ
		 * @return
		 */
		public static Form9OutputEmployeeInfo createForm9OutputEmployeeInfo(String employeeId) {
			return new Form9OutputEmployeeInfo(employeeId
					,	LicenseClassification.NURSE_ASSOCIATE//DUMMY
					,	"fullName"//DUMMY
					,	true, false, true, true, true, false//DUMMY
						);
		}
	}
}
