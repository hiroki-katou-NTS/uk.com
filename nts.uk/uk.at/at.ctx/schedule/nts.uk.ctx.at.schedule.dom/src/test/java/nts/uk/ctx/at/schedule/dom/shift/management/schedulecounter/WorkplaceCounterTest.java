package nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;

public class WorkplaceCounterTest {
	
	@Test
	public void getters() {
		
		WorkplaceCounter target = new WorkplaceCounter();
		
		NtsAssert.invokeGetters(target); 
	}
	
	@Test
	public void testCreateWithDuplicate() {
		
		NtsAssert.systemError( 
				() ->  WorkplaceCounter.create( 
							Arrays.asList( 
									WorkplaceCounterCategory.LABOR_COSTS_AND_TIME,
									WorkplaceCounterCategory.LABOR_COSTS_AND_TIME
						)));
		
	}
	
	@Test
	public void testCreateSucessfully() {
		
		WorkplaceCounter target = WorkplaceCounter.create( 
				Arrays.asList( 
						WorkplaceCounterCategory.LABOR_COSTS_AND_TIME,
						WorkplaceCounterCategory.EXTERNAL_BUDGET
						));
		
		assertThat(target.getUseCategories()).containsOnly(
				WorkplaceCounterCategory.LABOR_COSTS_AND_TIME,
				WorkplaceCounterCategory.EXTERNAL_BUDGET);
		
	}
	
	@Test
	public void testIsUsed_true() {
		
		WorkplaceCounter target = new WorkplaceCounter(
				Arrays.asList(WorkplaceCounterCategory.LABOR_COSTS_AND_TIME)
				);
		
		boolean result = target.isUsed(WorkplaceCounterCategory.LABOR_COSTS_AND_TIME);
		
		assertThat(result).isTrue();
	}
	
	@Test
	public void testIsUsed_false() {
		
		WorkplaceCounter target = new WorkplaceCounter(
				Arrays.asList(WorkplaceCounterCategory.LABOR_COSTS_AND_TIME)
				);
		
		
		boolean result = target.isUsed(WorkplaceCounterCategory.EXTERNAL_BUDGET);
		
		assertThat(result).isFalse();
	}
	
}
