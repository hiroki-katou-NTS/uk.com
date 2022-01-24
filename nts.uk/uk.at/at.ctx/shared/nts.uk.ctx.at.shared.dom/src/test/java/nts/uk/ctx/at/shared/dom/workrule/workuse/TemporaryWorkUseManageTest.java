package nts.uk.ctx.at.shared.dom.workrule.workuse;

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
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.personallaborcondition.UseAtr;
import nts.uk.shr.com.license.option.OptionLicense;

@RunWith(JMockit.class)
public class TemporaryWorkUseManageTest {
	
	@Injectable
	private TemporaryWorkUseManage.Require require;
	
	@Test
	public void getters() {
		TemporaryWorkUseManage manage = new TemporaryWorkUseManage(new CompanyId("000000000006-0008"), UseAtr.USE);
		NtsAssert.invokeGetters(manage);
	}
	
	/**
	 * Test [1] 臨時勤務を利用するか
	 * 
	 */
	@Test
	public void testUseTemporaryWork() {
		// @使用区分 = しない
		TemporaryWorkUseManage manage = TemporaryWorkUseManageHelper.createTemporaryWorkUseManage_NotUse(UseAtr.NOTUSE);
		new Expectations() {{
			require.getOptionLicense();
			result = new OptionLicense() {};
		}};
		
		boolean result = manage.checkUseTemporaryWork(require);
		assertThat( result ).isFalse();
		
		// @使用区分 = する
		manage = TemporaryWorkUseManageHelper.createTemporaryWorkUseManage_Use(UseAtr.USE);
		result = manage.checkUseTemporaryWork(require);
		assertThat( result ).isTrue();
	}
	
	/**
	 * Test [2] 臨時勤務に対応す日次の勤怠項目を取得する
	 */
	@Test
	public void testgetDailyAttdItemsCorrespondTemporaryWork() {
		TemporaryWorkUseManage manage = TemporaryWorkUseManageHelper.createTemporaryWorkUseManage();
		val result = manage.getDailyAttdItemsCorrespondTemporaryWork();
		assertThat(result.containsAll(Arrays.asList(50, 51))).isTrue();
	}
	
	/**
	 * Test [3] 臨時勤務に対応す月次の勤怠項目を取得する
	 */
	@Test
	public void testMonthAttdItemsCorrespondTemporaryWork() {
		TemporaryWorkUseManage manage = TemporaryWorkUseManageHelper.createTemporaryWorkUseManage();
		val result = manage.getMonthAttdItemsCorrespondTemporaryWork();
		// 臨時勤務に対応する月次の勤怠項目
		assertThat(result.containsAll(Arrays.asList(1397, 1849))).isTrue();
	}
	
	/**
	 * Test [4] 利用できない日次の勤怠項目を取得する
	 */
	@Test
	public void testGetDailyAttendanceItems() {
		// @使用区分 = する
		TemporaryWorkUseManage manage = TemporaryWorkUseManageHelper.createTemporaryWorkUseManage_Use(UseAtr.USE);
		new Expectations() {{
			require.getOptionLicense();
			result = new OptionLicense() {};
		}};
		List<Integer> result = manage.getDailyAttendanceItems(require);
		assertThat(result.isEmpty()).isTrue();
		
		// @使用区分 = しない
		manage = TemporaryWorkUseManageHelper.createTemporaryWorkUseManage_NotUse(UseAtr.NOTUSE);
		result = manage.getDailyAttendanceItems(require);
		assertThat(result.containsAll(Arrays.asList(50, 51))).isTrue();
	}
	
	/**
	 * Test [5] 利用できない月次の勤怠項目を取得する
	 */
	@Test
	public void testGetMonthlyAttendanceItems() {
		// @使用区分 = する
		TemporaryWorkUseManage manage = TemporaryWorkUseManageHelper.createTemporaryWorkUseManage_Use(UseAtr.USE);
		new Expectations() {{
			require.getOptionLicense();
			result = new OptionLicense() {};
		}};
		List<Integer> result = manage.getMonthlyAttendanceItems(require);
		assertThat(result.isEmpty()).isTrue();
		
		// @使用区分 = しない
		manage = TemporaryWorkUseManageHelper.createTemporaryWorkUseManage_NotUse(UseAtr.NOTUSE);
		result = manage.getMonthlyAttendanceItems(require);
		assertThat(result.containsAll(Arrays.asList(1397, 1849))).isTrue();
	}
}
