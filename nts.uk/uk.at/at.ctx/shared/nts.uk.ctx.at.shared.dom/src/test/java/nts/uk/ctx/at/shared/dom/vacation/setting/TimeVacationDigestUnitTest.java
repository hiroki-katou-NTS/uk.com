package nts.uk.ctx.at.shared.dom.vacation.setting;

import org.junit.Test;

import mockit.Expectations;
import mockit.Injectable;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeVacationDigestUnit.Require;
import nts.uk.shr.com.context.AppContexts;

public class TimeVacationDigestUnitTest {

	@Injectable
	private Require require;
	
	@Test
	public void getters() {
		TimeVacationDigestUnit domain = TimeVacationDigestUnitHelper.createDefault();
		NtsAssert.invokeGetters(domain);
	}
	
	/**
	 * [1] 消化単位をチェックする
	 * Test case 1
	 */
	public void checkDigestUnitTest1() {
		new Expectations() {
			{
				require.getOptionLicense();
				result = AppContexts.optionLicense();
			}
		};
		
	}
}
