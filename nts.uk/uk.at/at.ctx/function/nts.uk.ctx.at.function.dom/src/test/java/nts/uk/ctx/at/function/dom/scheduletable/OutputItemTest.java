package nts.uk.ctx.at.function.dom.scheduletable;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;
import nts.uk.shr.com.enumcommon.NotUseAtr;

public class OutputItemTest {
	
	@Test
	public void testGetter() {
		
		OutputItem target = OutputItem.create(NotUseAtr.USE, NotUseAtr.USE , NotUseAtr.USE, 
				Arrays.asList(
						OneRowOutputItem.create(
								Optional.of(ScheduleTablePersonalInfoItem.EMPLOYEE_NAME), 
								Optional.empty(), 
								Optional.of(ScheduleTableAttendanceItem.WORK_TYPE))));
		
		NtsAssert.invokeGetters(target);
	}
	
	@Test
	public void testCreate_exception_details_isEmpty() {
		
		NtsAssert.businessException("Msg_1975", 
				() -> OutputItem.create( NotUseAtr.USE, NotUseAtr.USE , NotUseAtr.USE, Collections.emptyList())
		); 
	}
	
	@Test
	public void testCreate_exception_details_size_more_than_10() {
		
		// Size == 11 >> exception_Msg_1975
		NtsAssert.businessException("Msg_1975", 
				() -> OutputItem.create( NotUseAtr.USE, NotUseAtr.USE , NotUseAtr.USE, 
						Arrays.asList(
								// 0
								OneRowOutputItem.create(
										Optional.of(ScheduleTablePersonalInfoItem.EMPLOYEE_NAME), 
										Optional.empty(), 
										Optional.of(ScheduleTableAttendanceItem.SHIFT)),
								// 1
								OneRowOutputItem.create(Optional.empty(), Optional.empty(), Optional.of(ScheduleTableAttendanceItem.WORK_TYPE)),
								// 2
								OneRowOutputItem.create(Optional.empty(), Optional.empty(), Optional.of(ScheduleTableAttendanceItem.WORK_TIME)),
								// 3
								OneRowOutputItem.create(Optional.empty(), Optional.empty(), Optional.of(ScheduleTableAttendanceItem.START_TIME)),
								// 4
								OneRowOutputItem.create(Optional.empty(), Optional.empty(), Optional.of(ScheduleTableAttendanceItem.END_TIME)),
								// 5
								OneRowOutputItem.create(Optional.empty(), Optional.empty(), Optional.of(ScheduleTableAttendanceItem.START_TIME_2)),
								// 6
								OneRowOutputItem.create(Optional.empty(), Optional.empty(), Optional.of(ScheduleTableAttendanceItem.END_TIME_2)),
								// 7
								OneRowOutputItem.create(Optional.empty(), Optional.empty(), Optional.of(ScheduleTableAttendanceItem.TOTAL_WORKING_HOURS)),
								// 8
								OneRowOutputItem.create(Optional.empty(), Optional.empty(), Optional.of(ScheduleTableAttendanceItem.WORKING_HOURS)),
								// 9
								OneRowOutputItem.create(Optional.empty(), Optional.empty(), Optional.of(ScheduleTableAttendanceItem.ACTUAL_WORKING_HOURS)),
								// 10
								OneRowOutputItem.create(Optional.empty(), Optional.empty(), Optional.of(ScheduleTableAttendanceItem.LEGAL_OVERTIME))
								))
		); 
		
		// size == 10 >> OK
		OutputItem.create( NotUseAtr.USE, NotUseAtr.USE , NotUseAtr.USE, 
						Arrays.asList(
								// 0
								OneRowOutputItem.create(
										Optional.of(ScheduleTablePersonalInfoItem.EMPLOYEE_NAME), 
										Optional.empty(), 
										Optional.of(ScheduleTableAttendanceItem.SHIFT)),
								// 1
								OneRowOutputItem.create(Optional.empty(), Optional.empty(), Optional.of(ScheduleTableAttendanceItem.WORK_TYPE)),
								// 2
								OneRowOutputItem.create(Optional.empty(), Optional.empty(), Optional.of(ScheduleTableAttendanceItem.WORK_TIME)),
								// 3
								OneRowOutputItem.create(Optional.empty(), Optional.empty(), Optional.of(ScheduleTableAttendanceItem.START_TIME)),
								// 4
								OneRowOutputItem.create(Optional.empty(), Optional.empty(), Optional.of(ScheduleTableAttendanceItem.END_TIME)),
								// 5
								OneRowOutputItem.create(Optional.empty(), Optional.empty(), Optional.of(ScheduleTableAttendanceItem.START_TIME_2)),
								// 6
								OneRowOutputItem.create(Optional.empty(), Optional.empty(), Optional.of(ScheduleTableAttendanceItem.END_TIME_2)),
								// 7
								OneRowOutputItem.create(Optional.empty(), Optional.empty(), Optional.of(ScheduleTableAttendanceItem.TOTAL_WORKING_HOURS)),
								// 8
								OneRowOutputItem.create(Optional.empty(), Optional.empty(), Optional.of(ScheduleTableAttendanceItem.WORKING_HOURS)),
								// 9
								OneRowOutputItem.create(Optional.empty(), Optional.empty(), Optional.of(ScheduleTableAttendanceItem.ACTUAL_WORKING_HOURS))
								));	
	}
	
	@Test
	public void testCreate_fail_with_additionalColumnUseAtr() {
		
		NtsAssert.systemError(
				() -> OutputItem.create( 
						NotUseAtr.NOT_USE, // additionalColumnUseAtr 
						NotUseAtr.USE , 
						NotUseAtr.USE, 
						Arrays.asList(
								// 0
								OneRowOutputItem.create(
										Optional.of(ScheduleTablePersonalInfoItem.EMPLOYEE_NAME), 
										Optional.empty(), 
										Optional.of(ScheduleTableAttendanceItem.SHIFT)),
								// 1
								OneRowOutputItem.create(
										Optional.empty(),  
										Optional.of(ScheduleTablePersonalInfoItem.EMPLOYMENT), 
										Optional.empty())
								)) );
		
	}
	
	@Test
	public void testCreate_exception_perInfos_isEmpty() {
		
		NtsAssert.businessException("Msg_2006", 
				() -> OutputItem.create( NotUseAtr.USE, NotUseAtr.USE , NotUseAtr.USE, 
						Arrays.asList(
								// 0
								OneRowOutputItem.create( Optional.empty(), Optional.empty(), Optional.of(ScheduleTableAttendanceItem.SHIFT))
								))
		); 
		
	}
	
	
	@Test
	public void testCreate_exception_perInfos_duplicate_on_single_row() {
		
		NtsAssert.businessException("Msg_1972", 
				() -> OutputItem.create( NotUseAtr.USE, NotUseAtr.USE , NotUseAtr.USE, 
						Arrays.asList(
								// 0
								OneRowOutputItem.create(
										Optional.of(ScheduleTablePersonalInfoItem.EMPLOYEE_NAME), 
										Optional.of(ScheduleTablePersonalInfoItem.EMPLOYEE_NAME),  
										Optional.of(ScheduleTableAttendanceItem.SHIFT))
								))
		); 
		
	}
	
	@Test
	public void testCreate_exception_perInfos_duplicate_on_multiple_rows() {
		
		NtsAssert.businessException("Msg_1972", 
				() -> OutputItem.create( NotUseAtr.USE, NotUseAtr.USE , NotUseAtr.USE, 
						Arrays.asList(
								// 0
								OneRowOutputItem.create(
										Optional.of(ScheduleTablePersonalInfoItem.EMPLOYEE_NAME), 
										Optional.empty(),  
										Optional.of(ScheduleTableAttendanceItem.SHIFT)),
								// 1
								OneRowOutputItem.create(
										Optional.of(ScheduleTablePersonalInfoItem.EMPLOYEE_NAME), 
										Optional.empty(),  
										Optional.of(ScheduleTableAttendanceItem.WORK_TYPE))
								))
		); 
	}
	
	@Test
	public void testCreate_exception_attendanceItems_isEmpty() {
		
		NtsAssert.businessException("Msg_2007", 
				() -> OutputItem.create( NotUseAtr.USE, NotUseAtr.USE , NotUseAtr.USE, 
						Arrays.asList(
								// 0
								OneRowOutputItem.create(
										Optional.of(ScheduleTablePersonalInfoItem.EMPLOYEE_NAME), 
										Optional.empty(), 
										Optional.empty())
								))
		); 
		
	}
	
	@Test
	public void testCreate_exception_attendanceItems_duplicate() {
		
		NtsAssert.businessException("Msg_1973", 
				() -> OutputItem.create( NotUseAtr.USE, NotUseAtr.USE , NotUseAtr.USE, 
						Arrays.asList(
								// 0
								OneRowOutputItem.create(
										Optional.of(ScheduleTablePersonalInfoItem.EMPLOYEE_NAME), 
										Optional.empty(),  
										Optional.of(ScheduleTableAttendanceItem.SHIFT)),
								// 1
								OneRowOutputItem.create(
										Optional.empty(),
										Optional.empty(),
										Optional.of(ScheduleTableAttendanceItem.SHIFT))
								))
		); 
		
	}
	
	@Test
	public void testCreate_sucessfully() {
		
		OneRowOutputItem element0 = OneRowOutputItem.create(
				Optional.of(ScheduleTablePersonalInfoItem.EMPLOYEE_NAME), 
				Optional.empty(),  
				Optional.of(ScheduleTableAttendanceItem.SHIFT));
				
		OneRowOutputItem element1 = OneRowOutputItem.create(
						Optional.empty(),
						Optional.empty(),
						Optional.of(ScheduleTableAttendanceItem.WORK_TYPE));
		
		OutputItem target = OutputItem.create( NotUseAtr.USE, NotUseAtr.NOT_USE , NotUseAtr.USE, 
						Arrays.asList( element0, element1 ));
		
		assertThat(target.getAdditionalColumnUseAtr()).isEqualTo(NotUseAtr.USE);
		assertThat(target.getShiftBackgroundColorUseAtr()).isEqualTo(NotUseAtr.NOT_USE);
		assertThat(target.getDailyDataDisplayAtr()).isEqualTo(NotUseAtr.USE);
		assertThat(target.getDetails()).containsExactly(element0, element1);
	}

}
