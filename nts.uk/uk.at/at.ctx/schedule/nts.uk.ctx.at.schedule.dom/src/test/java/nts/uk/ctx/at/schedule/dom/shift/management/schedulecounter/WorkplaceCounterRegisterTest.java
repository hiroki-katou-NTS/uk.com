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
public class WorkplaceCounterRegisterTest {
	
	@Injectable
	WorkplaceCounterRegister.Require require;
	
	@Test
	public void test_insert () {
		
		WorkplaceCounter target = new WorkplaceCounter(
				Arrays.asList(WorkplaceCounterCategory.LABOR_COSTS_AND_TIME));
		
		new Expectations() {
			{
				require.existsWorkplaceCounter();
				result = false;
				
			}
		};
		
		NtsAssert.atomTask(
				() -> WorkplaceCounterRegister.register(require, target).getAtomTask(),
				any -> require.insertWorkplaceCounter(any.get()));
	}
	
	@Test
	public void test_update () {
		
		WorkplaceCounter target = new WorkplaceCounter(
				Arrays.asList(WorkplaceCounterCategory.LABOR_COSTS_AND_TIME));
		
		new Expectations() {
			{
				require.existsWorkplaceCounter();
				result = true;
				
			}
		};
		
		NtsAssert.atomTask(
				() -> WorkplaceCounterRegister.register(require, target).getAtomTask(),
				any -> require.updateWorkplaceCounter(any.get()));
	}
	
	@Test
	public void test_notDetailSettingList_empty() {
		
		// all is not use
		WorkplaceCounter target = new WorkplaceCounter();
		
		WorkplaceCounterRegisterResult result = WorkplaceCounterRegister.register(require, target);
		
		assertThat(result.getNotDetailSettingList()).isEmpty();
	}
	
	@Test
	public void test_notDetailSettingList_empty_2() {
		
		WorkplaceCounter target = new WorkplaceCounter(
				Arrays.asList( 
						WorkplaceCounterCategory.LABOR_COSTS_AND_TIME,
						WorkplaceCounterCategory.TIMEZONE_PEOPLE,
						WorkplaceCounterCategory.TIMES_COUNTING
						)
				);
		
		new Expectations() {
			{
				require.existsLaborCostAndTime();
				result = true;
				
				require.existsTimeZonePeople();
				result = true;
				
				require.existsTimesCouting(TimesNumberCounterType.WORKPLACE);
				result = true;
			}
		};
		
		WorkplaceCounterRegisterResult result = WorkplaceCounterRegister.register(require, target);
		
		assertThat(result.getNotDetailSettingList()).isEmpty();
	}
	
	@Test
	public void test_LaborCostAndTime_notDetailSetting() {
		
		WorkplaceCounter target = new WorkplaceCounter(
				Arrays.asList( WorkplaceCounterCategory.LABOR_COSTS_AND_TIME));
		
		new Expectations() {
			{
				require.existsLaborCostAndTime();
				result = false;
				
			}
		};
		
		WorkplaceCounterRegisterResult result = WorkplaceCounterRegister.register(require, target);
		
		assertThat(result.getNotDetailSettingList()).containsOnly(
				WorkplaceCounterCategory.LABOR_COSTS_AND_TIME);
		
	}
	
	@Test
	public void test_timeZonePeople_notDetailSetting() {
		
		WorkplaceCounter target = new WorkplaceCounter(
				Arrays.asList( WorkplaceCounterCategory.TIMEZONE_PEOPLE));
		
		new Expectations() {
			{
				require.existsTimeZonePeople();
				result = false;
				
			}
		};
		
		WorkplaceCounterRegisterResult result = WorkplaceCounterRegister.register(require, target);
		
		assertThat(result.getNotDetailSettingList()).containsOnly(
				WorkplaceCounterCategory.TIMEZONE_PEOPLE);
		
	}
	
	@Test
	public void test_timesCounting_notDetailSetting() {
		
		WorkplaceCounter target = new WorkplaceCounter(
				Arrays.asList( WorkplaceCounterCategory.TIMES_COUNTING));
		
		new Expectations() {
			{
				require.existsTimesCouting( TimesNumberCounterType.WORKPLACE);
				result = false;
				
			}
		};
		
		WorkplaceCounterRegisterResult result = WorkplaceCounterRegister.register(require, target);
		
		assertThat(result.getNotDetailSettingList()).containsOnly(
				WorkplaceCounterCategory.TIMES_COUNTING);
		
	}

}
