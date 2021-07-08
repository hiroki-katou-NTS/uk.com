package nts.uk.ctx.at.record.dom.workrecord.stampmanagement;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.record.dom.daily.dailyperformance.classification.DoWork;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.stampinputfunctionsettings.notificationmessagesettings.ColorSetting;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.stampinputfunctionsettings.notificationmessagesettings.MessageTitle;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.stampinputfunctionsettings.notificationmessagesettings.NoticeSet;
import nts.uk.ctx.at.shared.dom.common.color.ColorCode;

public class NoticeSetTest {

	@Test
	public void getters() {
	
		NoticeSet noticeSet = new NoticeSet(
				new ColorSetting(
						new ColorCode("fff"), new ColorCode("fff")), 
				new MessageTitle("aaa"), 
				new ColorSetting(
						new ColorCode("fff"), new ColorCode("fff")),
				new ColorSetting(
						new ColorCode("fff"), new ColorCode("fff")),
				new MessageTitle("aaa"),  
				DoWork.USE);
		
		NtsAssert.invokeGetters(noticeSet);
	
	}

}
