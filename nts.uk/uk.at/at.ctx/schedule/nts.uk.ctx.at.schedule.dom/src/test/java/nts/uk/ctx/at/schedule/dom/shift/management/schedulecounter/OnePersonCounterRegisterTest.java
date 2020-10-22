package nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter.timescounting.TimesNumberCounterType;

@RunWith(JMockit.class)
public class OnePersonCounterRegisterTest {
	
	@Injectable
	PersonalCounterRegister.Require require;
	
	@Test
	public void test_insert () {
		
		PersonalCounter target = ScheduleCounterHelper.createPersonCounter_timeCounting_allNot();
		
		new Expectations() {
			{
				require.existsOnePersonCounter();
				result = false;
				
			}
		};
		
		NtsAssert.atomTask(
				() -> PersonalCounterRegister.register(require, target).getAtomTask(),
				any -> require.insertOnePersonCounter(any.get()));
	}
	
	@Test
	public void test_update () {
		
		PersonalCounter target = ScheduleCounterHelper.createPersonCounter_timeCounting_allNot();
		
		new Expectations() {
			{
				require.existsOnePersonCounter();
				result = true;
				
			}
		};
		
		NtsAssert.atomTask(
				() -> PersonalCounterRegister.register(require, target).getAtomTask(),
				any -> require.updateOnePersonCounter(any.get()));
	}
	
	@Test
	public void test_notDetailSettingList_empty() {
		
		PersonalCounter target = ScheduleCounterHelper.createPersonCounter_timeCounting_allNot();
		
		PersonalCounterRegisterResult result = PersonalCounterRegister.register(require, target);
		
		assertThat(result.getNotDetailSettingList()).isEmpty();
	}
	
	@Test
	public void test_notDetailSettingList_empty_2() {
		
		PersonalCounter target = ScheduleCounterHelper.createPersonCounter_timeCounting_allUse();
		
		new Expectations() {
			{
				require.existsTimesCouting( (TimesNumberCounterType) any );
				result = true;
			}
		};
		
		PersonalCounterRegisterResult result = PersonalCounterRegister.register(require, target);
		
		assertThat(result.getNotDetailSettingList()).isEmpty();		
	}
	
	@Test
	public void test_notDetailSettingList_empty_3() {
		
		PersonalCounter target = ScheduleCounterHelper.createPersonCounter_timeCounting_allUse();
		
		new Expectations() {
			{
				require.existsTimesCouting( (TimesNumberCounterType) any );
				result = false;
			}
		};
		
		PersonalCounterRegisterResult result = PersonalCounterRegister.register(require, target);
		
		assertThat(result.getNotDetailSettingList()).contains(
				PersonalCounterCategory.TIMES_COUNTING_1, 
				PersonalCounterCategory.TIMES_COUNTING_2, 
				PersonalCounterCategory.TIMES_COUNTING_3);
		
	}

}
