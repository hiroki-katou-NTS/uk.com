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
	OnePersonCounterRegister.Require require;
	
	@Test
	public void test_insert () {
		
		OnePersonCounter target = ScheduleCounterHelper.createPersonCounter_timeCounting_allNot();
		
		new Expectations() {
			{
				require.existsOnePersonCounter();
				result = false;
				
			}
		};
		
		NtsAssert.atomTask(
				() -> OnePersonCounterRegister.register(require, target).getAtomTask(),
				any -> require.insertOnePersonCounter(any.get()));
	}
	
	@Test
	public void test_update () {
		
		OnePersonCounter target = ScheduleCounterHelper.createPersonCounter_timeCounting_allNot();
		
		new Expectations() {
			{
				require.existsOnePersonCounter();
				result = true;
				
			}
		};
		
		NtsAssert.atomTask(
				() -> OnePersonCounterRegister.register(require, target).getAtomTask(),
				any -> require.updateOnePersonCounter(any.get()));
	}
	
	@Test
	public void test_notDetailSettingList_empty() {
		
		OnePersonCounter target = ScheduleCounterHelper.createPersonCounter_timeCounting_allNot();
		
		OnePersonCounterRegisterResult result = OnePersonCounterRegister.register(require, target);
		
		assertThat(result.getNotDetailSettingList()).isEmpty();
	}
	
	@Test
	public void test_notDetailSettingList_empty_2() {
		
		OnePersonCounter target = ScheduleCounterHelper.createPersonCounter_timeCounting_allUse();
		
		new Expectations() {
			{
				require.existsTimesCouting( (TimesNumberCounterType) any );
				result = true;
			}
		};
		
		OnePersonCounterRegisterResult result = OnePersonCounterRegister.register(require, target);
		
		assertThat(result.getNotDetailSettingList()).isEmpty();		
	}
	
	@Test
	public void test_notDetailSettingList_empty_3() {
		
		OnePersonCounter target = ScheduleCounterHelper.createPersonCounter_timeCounting_allUse();
		
		new Expectations() {
			{
				require.existsTimesCouting( (TimesNumberCounterType) any );
				result = false;
			}
		};
		
		OnePersonCounterRegisterResult result = OnePersonCounterRegister.register(require, target);
		
		assertThat(result.getNotDetailSettingList()).contains(
				OnePersonCounterCategory.TIMES_COUNTING_1, 
				OnePersonCounterCategory.TIMES_COUNTING_2, 
				OnePersonCounterCategory.TIMES_COUNTING_3);
		
	}

}
