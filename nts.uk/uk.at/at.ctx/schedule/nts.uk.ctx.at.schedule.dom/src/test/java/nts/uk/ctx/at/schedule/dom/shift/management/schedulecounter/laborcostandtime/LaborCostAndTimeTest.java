package nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter.laborcostandtime;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import lombok.val;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.shr.com.enumcommon.NotUseAtr;


public class LaborCostAndTimeTest {
	
	@Test
	public void test_createTotal_with_Error() {
		
		NtsAssert.businessException("Msg_1953", () -> {
			
			LaborCostAndTime.createWithBudget(NotUseAtr.USE, NotUseAtr.NOT_USE, NotUseAtr.NOT_USE, NotUseAtr.NOT_USE);
		}); 
		
	}
	
	@Test
	public void test_createTotal_successfully() {
		
		val result = LaborCostAndTime.createWithBudget(NotUseAtr.USE, NotUseAtr.USE, NotUseAtr.NOT_USE, NotUseAtr.USE);
		
		assertThat(result.getUseClassification()).isEqualTo(NotUseAtr.USE);
		assertThat(result.getTime()).isEqualTo(NotUseAtr.USE);
		assertThat(result.getLaborCost()).isEqualTo(NotUseAtr.NOT_USE);
		assertThat(result.getBudget().get()).isEqualTo(NotUseAtr.USE);
		
	}
	
	@Test
	public void test_create_with_Error() {
		
		NtsAssert.businessException("Msg_1953", () -> {
			
			LaborCostAndTime.createWithoutBudget(NotUseAtr.USE, NotUseAtr.NOT_USE, NotUseAtr.NOT_USE);
		}); 
		
	}
	

	@Test
	public void test_create_successfully() {
		
		val result = LaborCostAndTime.createWithoutBudget(NotUseAtr.USE, NotUseAtr.NOT_USE, NotUseAtr.USE);
		
		assertThat(result.getUseClassification()).isEqualTo(NotUseAtr.USE);
		assertThat(result.getTime()).isEqualTo(NotUseAtr.NOT_USE);
		assertThat(result.getLaborCost()).isEqualTo(NotUseAtr.USE);
	}
	
	@Test
	public void getters() {
		
		val target = LaborCostAndTime.createWithoutBudget(NotUseAtr.USE, NotUseAtr.NOT_USE, NotUseAtr.USE);
		NtsAssert.invokeGetters(target);  
	}

}
