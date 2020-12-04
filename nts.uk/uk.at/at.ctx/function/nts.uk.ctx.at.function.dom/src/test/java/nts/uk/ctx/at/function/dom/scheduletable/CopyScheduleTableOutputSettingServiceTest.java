package nts.uk.ctx.at.function.dom.scheduletable;

import org.junit.Test;

import mockit.Expectations;
import mockit.Injectable;
import nts.arc.testing.assertion.NtsAssert;

public class CopyScheduleTableOutputSettingServiceTest {
	
	@Injectable
	CopyScheduleTableOutputSettingService.Require require;
	
	@Test
	public void test_exception_object_exists() {
		
		ScheduleTableOutputSetting copySource = ScheduleTableOutputSettingHelper.createWithCodeName("code1", "name1");
		
		 new Expectations() {{
			 
			 require.isScheduleTableOutputSettingRegistered( (OutputSettingCode) any);
			 result = true;
		 }};
		
		NtsAssert.businessException("Msg_212", () ->  
			CopyScheduleTableOutputSettingService.copy(require, copySource, 
					new OutputSettingCode("code2"), new OutputSettingName("name2"))
		); 
	}
	
	
	@Test
	public void test_ok() {
		
		ScheduleTableOutputSetting copySource = ScheduleTableOutputSettingHelper.createWithCodeName("code1", "name1");
		
		 new Expectations() {{
			 
			 require.isScheduleTableOutputSettingRegistered( (OutputSettingCode) any);
			 result = false;
		 }};
		
		NtsAssert.atomTask(
				() -> CopyScheduleTableOutputSettingService.copy( require, copySource, 
						new OutputSettingCode("code2"), new OutputSettingName("name2")),
				any -> require.insertScheduleTableOutputSetting( any.get() ));
	}
	
}
