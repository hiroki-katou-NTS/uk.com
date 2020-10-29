package nts.uk.ctx.at.function.dom.scheduletable;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;

public class OneRowOutputItemTest {
	
	@Test
	public void testGetter() {
		OneRowOutputItem target = new OneRowOutputItem(
				Optional.of(ScheduleTablePersonalInfoItem.CLASSIFICATION), 
				Optional.of(ScheduleTablePersonalInfoItem.EMPLOYMENT), 
				Optional.of(ScheduleTableAttendanceItem.WORK_TYPE));
		
		NtsAssert.invokeGetters(target);
	}
	
	@Test
	public void testCreate_Exception() {
		
		NtsAssert.businessException("Msg_1971", 
				() -> OneRowOutputItem.create( 
						Optional.empty(), 
						Optional.empty(), 
						Optional.empty())
				);
	} 
	
	@Test
	public void testCreate_sucessfully() {
		
		OneRowOutputItem target = 
				OneRowOutputItem.create( 
						Optional.of(ScheduleTablePersonalInfoItem.EMPLOYEE_NAME), 
						Optional.empty(), 
						Optional.of(ScheduleTableAttendanceItem.WORK_TYPE));
		
		assertThat(target.getPersonalInfo().get()).isEqualTo(ScheduleTablePersonalInfoItem.EMPLOYEE_NAME);
		assertThat(target.getAdditionalInfo()).isEmpty();
		assertThat(target.getAttendanceItem().get()).isEqualTo(ScheduleTableAttendanceItem.WORK_TYPE);
		
	}
	
	@Test
	public void tesHasThisPersonalItem_false() {
		
		OneRowOutputItem target = 
				OneRowOutputItem.create( 
						Optional.of(ScheduleTablePersonalInfoItem.EMPLOYEE_NAME), 
						Optional.empty(), 
						Optional.of(ScheduleTableAttendanceItem.WORK_TYPE));
		
		boolean result = target.hasThisPersonalItem(ScheduleTablePersonalInfoItem.CLASSIFICATION);
		assertThat(result).isFalse();
	}
	
	/**
	 * input == @personalInfo
	 */
	@Test
	public void tesHasThisPersonalItem_true_case1() {
		
		OneRowOutputItem target = 
				OneRowOutputItem.create( 
						Optional.of(ScheduleTablePersonalInfoItem.EMPLOYEE_NAME), 
						Optional.empty(), 
						Optional.of(ScheduleTableAttendanceItem.WORK_TYPE));
		
		boolean result = target.hasThisPersonalItem(ScheduleTablePersonalInfoItem.EMPLOYEE_NAME);
		assertThat(result).isTrue();
	}
	
	/**
	 * input == @additionalInfo
	 */
	@Test
	public void tesHasThisPersonalItem_true_case2() {
		
		OneRowOutputItem target = 
				OneRowOutputItem.create( 
						Optional.empty(), 
						Optional.of(ScheduleTablePersonalInfoItem.CLASSIFICATION), 
						Optional.of(ScheduleTableAttendanceItem.WORK_TYPE));
		
		boolean result = target.hasThisPersonalItem(ScheduleTablePersonalInfoItem.CLASSIFICATION);
		assertThat(result).isTrue();
	}
	
	@Test
	public void testHasThisAttendanceItem_false() {
		
		OneRowOutputItem target = 
				OneRowOutputItem.create( 
						Optional.of(ScheduleTablePersonalInfoItem.EMPLOYEE_NAME), 
						Optional.empty(), 
						Optional.of(ScheduleTableAttendanceItem.WORK_TYPE));
		
		boolean result = target.hasThisAttendanceItem( ScheduleTableAttendanceItem.WORK_TIME );
		assertThat(result).isFalse();
	}
	
	@Test
	public void testHasThisAttendanceItem_true() {
		
		OneRowOutputItem target = 
				OneRowOutputItem.create( 
						Optional.of(ScheduleTablePersonalInfoItem.EMPLOYEE_NAME), 
						Optional.empty(), 
						Optional.of(ScheduleTableAttendanceItem.WORK_TYPE));
		
		boolean result = target.hasThisAttendanceItem( ScheduleTableAttendanceItem.WORK_TYPE );
		assertThat(result).isTrue();
	}
	

}
