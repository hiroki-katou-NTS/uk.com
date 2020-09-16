package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.toppagealarm;

import java.util.ArrayList;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDateTime;

public class TopPageArmTest {

	@Test
	public void getters() {
		TopPageAlarmMgrStamp topPageAlarm = new TopPageAlarmMgrStamp("DUMMY",GeneralDateTime.now(), ExistenceError.HAVE_ERROR,IsCancelled.NOT_CANCELLED, new ArrayList<>());
		NtsAssert.invokeGetters(topPageAlarm);
	}
}
