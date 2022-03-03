package nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeVacationDigestUnit.Require;

@RunWith(JMockit.class)
public class AnnualPaidLeaveSettingTest {
	
	@Injectable
	private Require require;

	/**
	 * Test [9] 時間年休を管理するか
	 * Case 1: $Option.就業.時間休暇 = true
	 */
	@Test
	public void testIsManageTimeAnnualLeave1() {
		AnnualPaidLeaveSetting domain = AnnualPaidLeaveSettingTestHelper.createDefault();
		new Expectations() {
    		{
    			require.getOptionLicense();
    			result = AnnualPaidLeaveSettingTestHelper.getOptionLicense(true);
    		}
		};
		boolean isManageTimeAnnualLeave = domain.isManageTimeAnnualLeave(require);
		assertThat(isManageTimeAnnualLeave).isTrue();
	}
	
	/**
	 * Test [9] 時間年休を管理するか
	 * Case 2: $Option.就業.時間休暇 = false
	 */
	@Test
	public void testIsManageTimeAnnualLeave2() {
		AnnualPaidLeaveSetting domain = AnnualPaidLeaveSettingTestHelper.createDefault();
		new Expectations() {
    		{
    			require.getOptionLicense();
    			result = AnnualPaidLeaveSettingTestHelper.getOptionLicense(false);
    		}
		};
		boolean isManageTimeAnnualLeave = domain.isManageTimeAnnualLeave(require);
		assertThat(isManageTimeAnnualLeave).isFalse();
	}
	
	/**
	 * Test [10] 利用する休暇時間の消化単位をチェックする
	 * Case 1: $Option.就業.時間休暇 = true && 「休暇使用時間」 % 「@消化単位」 = 0
	 */
	@Test
	public void testCheckVacationTimeUnitUsed1() {
		AnnualPaidLeaveSetting domain = AnnualPaidLeaveSettingTestHelper.createDefault();
		new Expectations() {
    		{
    			require.getOptionLicense();
    			result = AnnualPaidLeaveSettingTestHelper.getOptionLicense(true);
    		}
		};
		boolean checkDigestUnits = domain.checkVacationTimeUnitUsed(require, new AttendanceTime(600));
		assertThat(checkDigestUnits).isTrue();
	}
	
	/**
	 * Test [10] 利用する休暇時間の消化単位をチェックする
	 * Case 2: $Option.就業.時間休暇 = true && 「休暇使用時間」 % 「@消化単位」 != 0
	 */
	@Test
	public void testCheckVacationTimeUnitUsed2() {
		AnnualPaidLeaveSetting domain = AnnualPaidLeaveSettingTestHelper.createDefault();
		new Expectations() {
    		{
    			require.getOptionLicense();
    			result = AnnualPaidLeaveSettingTestHelper.getOptionLicense(true);
    		}
		};
		boolean checkDigestUnits = domain.checkVacationTimeUnitUsed(require, new AttendanceTime(11));
		assertThat(checkDigestUnits).isFalse();
	}
}
