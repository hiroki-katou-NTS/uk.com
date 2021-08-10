package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;
/**
 * 
 * @author tutk
 *
 */
public class StampRecordTest {

	@Test
	public void getters() {
		StampRecord stampRecord = StampRecordHelper.getStampRecord();
		NtsAssert.invokeGetters(stampRecord);
	}

}
