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
import nts.uk.shr.com.license.option.OptionLicense;

@RunWith(JMockit.class)
public class Com60HourVacationTest {

	@Injectable
	private Require require;
	
	private Com60HourVacation create() {
		return new Com60HourVacation("DUMMY-CID", new TimeVacationDigestUnit(ManageDistinct.YES, TimeDigestiveUnit.OneHour), SixtyHourExtra.ALLWAYS);
	}
	
	/**
	 * Test [1] 利用する休暇時間の消化単位をチェックする
	 */
	@Test
	public void testCheckVacationTimeUnitUsed() {
		val domain = create();
		new Expectations() {
			{
				require.getOptionLicense();
				result = new OptionLicense() {};
			}
		};
		boolean checkVacationTimeUnitUsed = domain.checkVacationTimeUnitUsed(require, AttendanceTime.ZERO);
		assertThat(checkVacationTimeUnitUsed);
	}
	
	/**
	 * Test [2] 時間60H超休を管理するか
	 */
	@Test
	public void testIsVacationTimeManage() {
		val domain = create();
		new Expectations() {
			{
				require.getOptionLicense();
				result = new OptionLicense() {};
			}
		};
		boolean isVacationTimeManage = domain.isVacationTimeManage(require);
		assertThat(isVacationTimeManage);
	}
	
}
