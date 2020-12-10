package nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;

public class PersonalCounterTest {
	
	@Test
	public void getters() {
		
		PersonalCounter target = new PersonalCounter();
		
		NtsAssert.invokeGetters(target); 
	}
	
	@Test
	public void testCreateWithDuplicate() {
		
		NtsAssert.systemError( 
				() ->  PersonalCounter.create( 
							Arrays.asList( 
									PersonalCounterCategory.MONTHLY_EXPECTED_SALARY,
									PersonalCounterCategory.MONTHLY_EXPECTED_SALARY
						)));
		
	}
	
	@Test
	public void testCreateSucessfully() {
		
		PersonalCounter target = PersonalCounter.create( 
							Arrays.asList( 
									PersonalCounterCategory.MONTHLY_EXPECTED_SALARY,
									PersonalCounterCategory.CUMULATIVE_ESTIMATED_SALARY
						));
		
		assertThat(target.getUseCategories()).containsOnly(
				PersonalCounterCategory.MONTHLY_EXPECTED_SALARY,
				PersonalCounterCategory.CUMULATIVE_ESTIMATED_SALARY);
		
	}
	
	@Test
	public void testIsUsed_true() {
		
		PersonalCounter target = new PersonalCounter( 
				Arrays.asList( PersonalCounterCategory.MONTHLY_EXPECTED_SALARY));
		
		boolean result = target.isUsed(PersonalCounterCategory.MONTHLY_EXPECTED_SALARY);
		
		assertThat(result).isTrue();
	}
	
	@Test
	public void testIsUsed_false() {
		
		PersonalCounter target = new PersonalCounter( 
				Arrays.asList( PersonalCounterCategory.MONTHLY_EXPECTED_SALARY));
		
		boolean result = target.isUsed(PersonalCounterCategory.CUMULATIVE_ESTIMATED_SALARY);
		
		assertThat(result).isFalse();
	}
}
