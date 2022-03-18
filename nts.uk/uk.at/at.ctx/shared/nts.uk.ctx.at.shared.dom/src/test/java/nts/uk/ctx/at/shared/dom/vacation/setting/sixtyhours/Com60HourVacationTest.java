package nts.uk.ctx.at.shared.dom.vacation.setting.sixtyhours;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.SixtyHourExtra;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeDigestiveUnit;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeVacationDigestUnit;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeVacationDigestUnit.Require;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeVacationDigestUnitHelper;

@RunWith(JMockit.class)
public class Com60HourVacationTest {

	@Injectable
	private Require require;
	
	private Com60HourVacation create() {
		return new Com60HourVacation("DUMMY-CID", new TimeVacationDigestUnit(ManageDistinct.YES, TimeDigestiveUnit.OneHour), SixtyHourExtra.ALLWAYS);
	}
	
	/**
	 * Test [1] 利用する休暇時間の消化単位をチェックする
	 * Case 1: $Option.就業.時間休暇 = true && 「休暇使用時間」 % 「@消化単位」 = 0
	 */
	@Test
	public void testCheckVacationTimeUnitUsed1() {
		val domain = create();
		new Expectations() {
			{
				require.getOptionLicense();
				result = Com60HourVacationTestHelper.getOptionLicense(true);
			}
		};
		boolean checkVacationTimeUnitUsed = domain.checkVacationTimeUnitUsed(require, new AttendanceTime(600));
		assertThat(checkVacationTimeUnitUsed).isTrue();
	}
	
	/**
	 * Test [1] 利用する休暇時間の消化単位をチェックする
	 * Case 2: $Option.就業.時間休暇 = true && 「休暇使用時間」 % 「@消化単位」 != 0
	 */
	@Test
	public void testCheckVacationTimeUnitUsed2() {
		val domain = create();
		new Expectations() {
			{
				require.getOptionLicense();
				result = Com60HourVacationTestHelper.getOptionLicense(true);
			}
		};
		boolean checkVacationTimeUnitUsed = domain.checkVacationTimeUnitUsed(require, new AttendanceTime(11));
		assertThat(checkVacationTimeUnitUsed).isFalse();
	}
	
	/**
	 * Test [2] 時間60H超休を管理するか
	 * Case 1: $Option.就業.時間休暇 = true
	 */
	@Test
	public void testIsVacationTimeManage1() {
		val domain = create();
		new Expectations() {
			{
				require.getOptionLicense();
				result = Com60HourVacationTestHelper.getOptionLicense(true);
			}
		};
		boolean isVacationTimeManage = domain.isVacationTimeManage(require);
		assertThat(isVacationTimeManage).isTrue();
	}
	
	/**
	 * Test [2] 時間60H超休を管理するか
	 * Case 2: $Option.就業.時間休暇 = false
	 */
	@Test
	public void testIsVacationTimeManage2() {
		val domain = create();
		new Expectations() {
			{
				require.getOptionLicense();
				result = TimeVacationDigestUnitHelper.getOptionLicense(false);
			}
		};
		boolean isVacationTimeManage = domain.isVacationTimeManage(require);
		assertThat(isVacationTimeManage).isFalse();
	}
	
}
