package nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;
import nts.uk.shr.com.enumcommon.NotUseAtr;

public class OnePersonCounterTest {
	
	private OnePersonCounter target;
	
	@Before
	public void createMap() {
		
		Map<OnePersonCounterCategory, NotUseAtr> map = new HashMap<>();
		map.put(OnePersonCounterCategory.MONTHLY_EXPECTED_SALARY, NotUseAtr.USE);
		map.put(OnePersonCounterCategory.CUMULATIVE_ESTIMATED_SALARY, NotUseAtr.NOT_USE);
		map.put(OnePersonCounterCategory.STANDARD_WORKING_HOURS_COMPARISON, NotUseAtr.USE);
		map.put(OnePersonCounterCategory.WORKING_HOURS, NotUseAtr.NOT_USE);
		map.put(OnePersonCounterCategory.NIGHT_SHIFT_HOURS, NotUseAtr.USE);
		map.put(OnePersonCounterCategory.WEEKS_HOLIDAY_DAYS, NotUseAtr.NOT_USE);
		map.put(OnePersonCounterCategory.ATTENDANCE_HOLIDAY_DAYS, NotUseAtr.USE);
		map.put(OnePersonCounterCategory.TIMES_COUNTING_1, NotUseAtr.NOT_USE);
		map.put(OnePersonCounterCategory.TIMES_COUNTING_2, NotUseAtr.USE);
		map.put(OnePersonCounterCategory.TIMES_COUNTING_3, NotUseAtr.NOT_USE);
		
		target = new OnePersonCounter(map);
	}
	
	@Test
	public void getters() {
		
		NtsAssert.invokeGetters(target); 
	}
	
	@Test
	public void testIsUsed_true() {
		
		boolean result = target.isUsed(OnePersonCounterCategory.MONTHLY_EXPECTED_SALARY);
		
		assertThat(result).isTrue();
	}
	
	@Test
	public void testIsUsed_false() {
		
		boolean result = target.isUsed(OnePersonCounterCategory.CUMULATIVE_ESTIMATED_SALARY);
		
		assertThat(result).isFalse();
	}

}
