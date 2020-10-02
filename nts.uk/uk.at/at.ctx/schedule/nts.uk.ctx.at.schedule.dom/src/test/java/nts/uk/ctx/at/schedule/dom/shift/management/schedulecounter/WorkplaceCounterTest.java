package nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;
import nts.uk.shr.com.enumcommon.NotUseAtr;

public class WorkplaceCounterTest {
	
private WorkplaceCounter target;
	
	@Before
	public void createMap() {
		
		Map<WorkplaceCounterCategory, NotUseAtr> map = new HashMap<>();
		map.put(WorkplaceCounterCategory.LABOR_COSTS_AND_TIME, NotUseAtr.USE);
		map.put(WorkplaceCounterCategory.EXTERNAL_BUDGET, NotUseAtr.NOT_USE);
		map.put(WorkplaceCounterCategory.TIMES_COUNTING, NotUseAtr.USE);
		map.put(WorkplaceCounterCategory.WORKTIME_PEOPLE, NotUseAtr.NOT_USE);
		map.put(WorkplaceCounterCategory.TIMEZONE_PEOPLE, NotUseAtr.USE);
		map.put(WorkplaceCounterCategory.EMPLOYMENT_PEOPLE, NotUseAtr.NOT_USE);
		map.put(WorkplaceCounterCategory.CLASSIFICATION_PEOPLE, NotUseAtr.USE);
		map.put(WorkplaceCounterCategory.POSITION_PEOPLE, NotUseAtr.NOT_USE);
		
		target = new WorkplaceCounter(map);
	}
	
	@Test
	public void getters() {
		
		NtsAssert.invokeGetters(target); 
	}
	
	@Test
	public void testIsUsed_true() {
		
		boolean result = target.isUsed(WorkplaceCounterCategory.LABOR_COSTS_AND_TIME);
		
		assertThat(result).isTrue();
	}
	
	@Test
	public void testIsUsed_false() {
		
		boolean result = target.isUsed(WorkplaceCounterCategory.EXTERNAL_BUDGET);
		
		assertThat(result).isFalse();
	}

}
