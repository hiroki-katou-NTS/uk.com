package nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;

/**
 * 
 * @author dungbn
 *
 */

@RunWith(JMockit.class)
public class TimeRecordSetFormatBakTest {

	@Test
	public void getters() {
		TimeRecordSetFormatBak timeRecordSetFormatBak = TimeRecordSetFormatBakHelper.createTimeRecordSetFormatBak();
		NtsAssert.invokeGetters(timeRecordSetFormatBak);
	}
}
