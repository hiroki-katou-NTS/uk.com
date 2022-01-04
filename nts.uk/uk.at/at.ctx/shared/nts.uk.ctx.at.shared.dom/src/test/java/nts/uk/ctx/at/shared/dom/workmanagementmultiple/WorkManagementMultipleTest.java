package nts.uk.ctx.at.shared.dom.workmanagementmultiple;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.shr.com.license.option.OptionLicense;

@RunWith(JMockit.class)
public class WorkManagementMultipleTest {

	
	@Injectable
	private WorkManagementMultiple.Require require;
	
	@Test
	public void getters() {
		WorkManagementMultiple multiple = new WorkManagementMultiple("000000000006-0008", UseATR.use);
		NtsAssert.invokeGetters(multiple);
	}
	
	/**
	 * Test [1] 臨時勤務を利用するか
	 * 
	 */
	@Test
	public void testUseTemporaryWork() {
		// @使用区分 = しない
		WorkManagementMultiple multiple = WorkManagementMultipleHelper.createWorkManagementMultiple_NotUse(UseATR.notUse);
		new Expectations() {{
			require.getOptionLicense();
			result = new OptionLicense() {};
		}};
		
		boolean result = multiple.checkUseMultiWork(require);
		assertThat( result ).isFalse();
		
		// @使用区分 = する
		new Expectations() {{
			require.getOptionLicense();
			result = new OptionLicense() {};
		}};
		multiple = WorkManagementMultipleHelper.createWorkManagementMultiple_Use(UseATR.use);
		result = multiple.checkUseMultiWork(require);
		assertThat( result ).isTrue();
	}
	
	/**
	 * Test [2] 臨時勤務に対応す日次の勤怠項目を取得する
	 */
	@Test
	public void testgetDailyAttdItemsCorrespondTemporaryWork() {
		WorkManagementMultiple multiple = WorkManagementMultipleHelper.createWorkManagementMultiple();
		val result = multiple.getDailyAttdItemsCorrespondMultiWork();
		assertThat(result.containsAll(Arrays.asList(5, 6))).isTrue();
	}
	
	/**
	 * Test [3] 臨時勤務に対応す月次の勤怠項目を取得する
	 */
	@Test
	public void testMonthAttdItemsCorrespondTemporaryWork() {
		WorkManagementMultiple multiple = WorkManagementMultipleHelper.createWorkManagementMultiple();
		val result = multiple.getMonthAttdItemsCorrespondMultiWork();
		// 臨時勤務に対応する月次の勤怠項目
		assertThat(result.contains(1396)).isTrue();
	}
	
	/**
	 * Test [4] 利用できない日次の勤怠項目を取得する
	 */
	@Test
	public void testGetDailyAttendanceItems() {
		// @使用区分 = する
		WorkManagementMultiple multiple = WorkManagementMultipleHelper.createWorkManagementMultiple_Use(UseATR.use);
		new Expectations() {{
			require.getOptionLicense();
			result = new OptionLicense() {};
		}};
		List<Integer> result = multiple.getDailyAttendanceItems(require);
		assertThat(result.isEmpty()).isTrue();
		
		// @使用区分 = しない
		multiple = WorkManagementMultipleHelper.createWorkManagementMultiple_NotUse(UseATR.notUse);
		result = multiple.getDailyAttendanceItems(require);
		assertThat(result.containsAll(Arrays.asList(5, 6))).isTrue();
	}
	
	/**
	 * Test [5] 利用できない月次の勤怠項目を取得する
	 */
	@Test
	public void testGetMonthlyAttendanceItems() {
		// @使用区分 = する
		WorkManagementMultiple multiple = WorkManagementMultipleHelper.createWorkManagementMultiple_Use(UseATR.use);
		new Expectations() {{
			require.getOptionLicense();
			result = new OptionLicense() {};
		}};
		List<Integer> result = multiple.getMonthlyAttendanceItems(require);
		assertThat(result.isEmpty()).isTrue();
		
		// @使用区分 = しない
		multiple = WorkManagementMultipleHelper.createWorkManagementMultiple_NotUse(UseATR.notUse);
		result = multiple.getMonthlyAttendanceItems(require);
		assertThat(result.containsAll(Arrays.asList(1396))).isTrue();
	}
}
