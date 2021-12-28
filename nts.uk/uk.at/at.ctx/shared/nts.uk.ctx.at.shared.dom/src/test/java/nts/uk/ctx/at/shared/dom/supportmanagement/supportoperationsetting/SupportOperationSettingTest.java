package nts.uk.ctx.at.shared.dom.supportmanagement.supportoperationsetting;

import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
/**
 * 応援の運用設定のUTコード
 * @author lan_lt
 *
 */
@RunWith(JMockit.class)
public class SupportOperationSettingTest {
	
	@Test
	public void getters() {

		val supportOperationSetting = new SupportOperationSetting(true, true, new MaximumNumberOfSupport(20));

		NtsAssert.invokeGetters(supportOperationSetting);

	}
}
