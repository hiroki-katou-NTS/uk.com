package nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter.laborcostandtime;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import lombok.val;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.shr.com.enumcommon.NotUseAtr;


public class LaborCostAndTimeTest {
	
	@Test
	public void test_createTotal_with_Error() {
		
		NtsAssert.businessException("", () -> {
			
			LaborCostAndTime.createTotal(NotUseAtr.USE, NotUseAtr.NOT_USE, NotUseAtr.NOT_USE, NotUseAtr.NOT_USE);
		}); 
		
	}
	
	@Test
	public void test_createTotal_successfully() {
		
		val result = LaborCostAndTime.createTotal(NotUseAtr.USE, NotUseAtr.USE, NotUseAtr.NOT_USE, NotUseAtr.USE);
		
		assertThat(result.getUseClassification()).isEqualTo(NotUseAtr.USE);
		assertThat(result.getTime()).isEqualTo(NotUseAtr.USE);
		assertThat(result.getLaborCost()).isEqualTo(NotUseAtr.NOT_USE);
		assertThat(result.getBudget().get()).isEqualTo(NotUseAtr.USE);
		
	}
	
	@Test
	public void test_create_with_Error() {
		
		NtsAssert.businessException("", () -> {
			
			LaborCostAndTime.create(NotUseAtr.USE, NotUseAtr.NOT_USE, NotUseAtr.NOT_USE);
		}); 
		
	}
	

	@Test
	public void test_create_successfully() {
		
		val result = LaborCostAndTime.create(NotUseAtr.USE, NotUseAtr.NOT_USE, NotUseAtr.USE);
		
		assertThat(result.getUseClassification()).isEqualTo(NotUseAtr.USE);
		assertThat(result.getTime()).isEqualTo(NotUseAtr.NOT_USE);
		assertThat(result.getLaborCost()).isEqualTo(NotUseAtr.USE);
	}
	
	@Test
	public void getters() {
		
		val target = LaborCostAndTime.create(NotUseAtr.USE, NotUseAtr.NOT_USE, NotUseAtr.USE);
		NtsAssert.invokeGetters(target);  
	}

}
