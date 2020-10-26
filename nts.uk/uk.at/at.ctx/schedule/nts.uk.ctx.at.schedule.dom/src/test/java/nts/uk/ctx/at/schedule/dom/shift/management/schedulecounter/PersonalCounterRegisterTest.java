package nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter.timescounting.TimesNumberCounterType;

@RunWith(JMockit.class)
public class PersonalCounterRegisterTest {
	
	@Injectable
	PersonalCounterRegister.Require require;
	
	@Test
	public void test_insert () {
		
		PersonalCounter target = new PersonalCounter();
		
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
		
		PersonalCounter target = new PersonalCounter();
		
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
		
		PersonalCounter target = new PersonalCounter();
		
		PersonalCounterRegisterResult result = PersonalCounterRegister.register(require, target);
		
		assertThat(result.getNotDetailSettingList()).isEmpty();
	}
	
	@Test
	public void test_notDetailSettingList_empty_2() {
		
		PersonalCounter target = new PersonalCounter(
				Arrays.asList( 
						PersonalCounterCategory.TIMES_COUNTING_1,
						PersonalCounterCategory.TIMES_COUNTING_2,
						PersonalCounterCategory.TIMES_COUNTING_3
						));
		
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
	public void test_timesCounting1_notDetailSetting() {
		
		PersonalCounter target = new PersonalCounter(
				Arrays.asList( PersonalCounterCategory.TIMES_COUNTING_1));
		
		new Expectations() {
			{
				require.existsTimesCouting( TimesNumberCounterType.PERSON_1 );
				result = false;
			}
		};
		
		PersonalCounterRegisterResult result = PersonalCounterRegister.register(require, target);
		
		assertThat(result.getNotDetailSettingList()).containsOnly(
				PersonalCounterCategory.TIMES_COUNTING_1 );
		
	}
	
	@Test
	public void test_timesCounting2_notDetailSetting() {
		
		PersonalCounter target = new PersonalCounter(
				Arrays.asList( PersonalCounterCategory.TIMES_COUNTING_2 ));
		
		new Expectations() {
			{
				require.existsTimesCouting( TimesNumberCounterType.PERSON_2 );
				result = false;
			}
		};
		
		PersonalCounterRegisterResult result = PersonalCounterRegister.register(require, target);
		
		assertThat(result.getNotDetailSettingList()).containsOnly(
				PersonalCounterCategory.TIMES_COUNTING_2 );
		
	}
	
	@Test
	public void test_timesCounting3_notDetailSetting() {
		
		PersonalCounter target = new PersonalCounter(
				Arrays.asList( PersonalCounterCategory.TIMES_COUNTING_3 ));
		
		new Expectations() {
			{
				require.existsTimesCouting( TimesNumberCounterType.PERSON_3 );
				result = false;
			}
		};
		
		PersonalCounterRegisterResult result = PersonalCounterRegister.register(require, target);
		
		assertThat(result.getNotDetailSettingList()).containsOnly(
				PersonalCounterCategory.TIMES_COUNTING_3);
		
	}

}
