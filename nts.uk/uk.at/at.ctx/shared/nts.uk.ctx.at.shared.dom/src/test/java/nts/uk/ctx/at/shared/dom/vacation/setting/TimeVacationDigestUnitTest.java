package nts.uk.ctx.at.shared.dom.vacation.setting;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeVacationDigestUnit.Require;

@RunWith(JMockit.class)
public class TimeVacationDigestUnitTest {

	@Injectable
	private Require require;
	
	@Test
	public void getters() {
		TimeVacationDigestUnit domain = TimeVacationDigestUnitHelper.createDefault();
		NtsAssert.invokeGetters(domain);
	}
	
	/**
	 * Test [1] 消化単位をチェックする
	 * Case 1
	 * 時間休暇が管理するかチェックする = false
	 */
	@Test
	public void testCheckDigestUnit1_1() {
		val domain = TimeVacationDigestUnitHelper.createDefault();
		new Expectations() {
			{
				require.getOptionLicense();
				result = TimeVacationDigestUnitHelper.getOptionLicense(false);
			}
		};
		boolean checkDigestUnit = domain.checkDigestUnit(require, AttendanceTime.ZERO, ManageDistinct.YES);
		assertThat(checkDigestUnit).isTrue();
	}
	
	/**
	 * Test [1] 消化単位をチェックする
	 * Case 2
	 * 時間休暇が管理するかチェックする = true
	 * 「休暇使用時間」 % 「@消化単位」 = 0
	 */
	@Test
	public void testCheckDigestUnit1_2() {
		val domain = new TimeVacationDigestUnit(ManageDistinct.YES, TimeDigestiveUnit.OneHour);
		new Expectations() {
			{
				require.getOptionLicense();
				result = TimeVacationDigestUnitHelper.getOptionLicense(true);
			}
		};
		boolean checkDigestUnit = domain.checkDigestUnit(require, new AttendanceTime(1200), ManageDistinct.YES);
		assertThat(checkDigestUnit).isTrue();
		
	}
	
	/**
	 * Test [1] 消化単位をチェックする
	 * Case 3
	 * 時間休暇が管理するかチェックする = true
	 * 「休暇使用時間」 % 「@消化単位」 != 0
	 */
	@Test
	public void testCheckDigestUnit1_3() {
		val domain = new TimeVacationDigestUnit(ManageDistinct.YES, TimeDigestiveUnit.OneHour);
		new Expectations() {
			{
				require.getOptionLicense();
				result = TimeVacationDigestUnitHelper.getOptionLicense(true);
			}
		};
		boolean checkDigestUnit = domain.checkDigestUnit(require, new AttendanceTime(1201), ManageDistinct.YES);
		assertThat(checkDigestUnit).isFalse();
		
	}
	
	/**
	 * Test [2] 時間休暇が管理するか
	 * Case 1
	 * $Option.就業.時間休暇 = false
	 */
	@Test
	public void testIsVacationTimeManage1_1() {
		val domain = TimeVacationDigestUnitHelper.createDefault();
		new Expectations() {
			{
				require.getOptionLicense();
				result = TimeVacationDigestUnitHelper.getOptionLicense(false);
			}
		};
		boolean isVacationTimeManage = domain.isVacationTimeManage(require, ManageDistinct.YES);
		assertThat(isVacationTimeManage).isFalse();
	}
	
	/**
	 * Test [2] 時間休暇が管理するか
	 * Case 2
	 * $Option.就業.時間休暇 == true;
	 * 管理区分　＝＝　管理する　&&　@管理区分　＝＝　管理する
	 */
	@Test
	public void testIsVacationTimeManage1_2() {
		val domain = TimeVacationDigestUnitHelper.createWithManage(ManageDistinct.YES);
		new Expectations() {
			{
				require.getOptionLicense();
				result = TimeVacationDigestUnitHelper.getOptionLicense(true);
			}
		};
		boolean isVacationTimeManage = domain.isVacationTimeManage(require, ManageDistinct.YES);
		assertThat(isVacationTimeManage).isTrue();
	}
	
	/**
	 * Test [2] 時間休暇が管理するか
	 * Case 3
	 * $Option.就業.時間休暇 == true;
	 * 管理区分　＝＝　管理しない　&&　@管理区分　＝＝　管理する
	 */
	@Test
	public void testIsVacationTimeManage1_3() {
		val domain = TimeVacationDigestUnitHelper.createWithManage(ManageDistinct.YES);
		new Expectations() {
			{
				require.getOptionLicense();
				result = TimeVacationDigestUnitHelper.getOptionLicense(true);
			}
		};
		boolean isVacationTimeManage = domain.isVacationTimeManage(require, ManageDistinct.NO);
		assertThat(isVacationTimeManage).isFalse();
	}
	
	/**
	 * Test [2] 時間休暇が管理するか
	 * Case 4
	 * $Option.就業.時間休暇 == true;
	 * 管理区分　＝＝　管理する　&&　@管理区分　＝＝　管理しない
	 */
	@Test
	public void testIsVacationTimeManage1_4() {
		val domain = TimeVacationDigestUnitHelper.createWithManage(ManageDistinct.NO);
		new Expectations() {
			{
				require.getOptionLicense();
				result = TimeVacationDigestUnitHelper.getOptionLicense(true);
			}
		};
		boolean isVacationTimeManage = domain.isVacationTimeManage(require, ManageDistinct.YES);
		assertThat(isVacationTimeManage).isFalse();
	}
	
	/**
	 * Test [3] 時間休暇が管理するか
	 * Case 1
	 * $Option.就業.時間休暇 == false
	 */
	@Test
	public void testIsVacationTimeManage2_1() {
		val domain = TimeVacationDigestUnitHelper.createDefault();
		new Expectations() {
			{
				require.getOptionLicense();
				result = TimeVacationDigestUnitHelper.getOptionLicense(false);
			}
		};
		boolean isVacationTimeManage = domain.isVacationTimeManage(require);
		assertThat(isVacationTimeManage).isFalse();
	}
	
	/**
	 * Test [3] 時間休暇が管理するか
	 * Case 2
	 * $Option.就業.時間休暇 == true
	 * @管理区分　＝　管理する
	 */
	@Test
	public void testIsVacationTimeManage2_2() {
		val domain = TimeVacationDigestUnitHelper.createWithManage(ManageDistinct.YES);
		new Expectations() {
			{
				require.getOptionLicense();
				result = TimeVacationDigestUnitHelper.getOptionLicense(true);
			}
		};
		boolean isVacationTimeManage = domain.isVacationTimeManage(require);
		assertThat(isVacationTimeManage).isTrue();
	}
	
	/**
	 * Test [3] 時間休暇が管理するか
	 * Case 3
	 * $Option.就業.時間休暇 == true
	 * @管理区分　＝　管理しない
	 */
	@Test
	public void testIsVacationTimeManage2_3() {
		val domain = TimeVacationDigestUnitHelper.createWithManage(ManageDistinct.NO);
		new Expectations() {
			{
				require.getOptionLicense();
				result = TimeVacationDigestUnitHelper.getOptionLicense(true);
			}
		};
		boolean isVacationTimeManage = domain.isVacationTimeManage(require);
		assertThat(isVacationTimeManage).isFalse();
	}
	
	/**
	 * Test [4] 消化単位をチェックする
	 * Case 1
	 * $時間管理区分　==　false
	 */
	@Test
	public void testCheckDigestUnit2_1() {
		val domain = TimeVacationDigestUnitHelper.createDefault();
		new Expectations() {
			{
				require.getOptionLicense();
				result = TimeVacationDigestUnitHelper.getOptionLicense(false);
			}
		};
		boolean checkDigestUnit = domain.checkDigestUnit(require, AttendanceTime.ZERO);
		assertThat(checkDigestUnit).isTrue();
	}
	
	/**
	 * Test [4] 消化単位をチェックする
	 * Case 2
	 * $時間管理区分　==　true
	 * 休暇使用時間　%　@消化単位　==　0
	 */
	@Test
	public void testCheckDigestUnit2_2() {
		val domain = new TimeVacationDigestUnit(ManageDistinct.YES, TimeDigestiveUnit.OneHour);
		new Expectations() {
			{
				require.getOptionLicense();
				result = TimeVacationDigestUnitHelper.getOptionLicense(true);
			}
		};
		boolean checkDigestUnit = domain.checkDigestUnit(require, new AttendanceTime(600));
		assertThat(checkDigestUnit).isTrue();
	}
	
	/**
	 * Test [4] 消化単位をチェックする
	 * Case 3
	 * $時間管理区分　==　true
	 * 休暇使用時間　%　@消化単位　!=　0
	 */
	@Test
	public void testCheckDigestUnit2_3() {
		val domain = new TimeVacationDigestUnit(ManageDistinct.YES, TimeDigestiveUnit.OneHour);
		new Expectations() {
			{
				require.getOptionLicense();
				result = TimeVacationDigestUnitHelper.getOptionLicense(true);
			}
		};
		boolean checkDigestUnit = domain.checkDigestUnit(require, new AttendanceTime(601));
		assertThat(checkDigestUnit).isFalse();
	}
}
