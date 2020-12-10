package nts.uk.ctx.at.schedule.dom.shift.management.workavailability;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMasterCode;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.shr.com.time.TimeWithDayAttr;

@RunWith(JMockit.class)
public class WorkAvailabilityOfOneDayTest {
	
	@Injectable
	WorkAvailabilityOfOneDay.Require require;
	
	@Test
	public void getters() {
		
		WorkAvailabilityOfOneDay workAvailability = WorkAvailabilityOfOneDayTestHelper.createWorkAvailabilityOfOneDay();
		NtsAssert.invokeGetters(workAvailability);  
	}
	
	@Test
	public void testCreate_holiday() {
		
		// Prepare
		String employeeId = "001";
		GeneralDate availabilityDate = GeneralDate.ymd(2020, 9, 18);
		WorkAvailabilityMemo memo = new WorkAvailabilityMemo("memo");
		AssignmentMethod assignmentMethod = AssignmentMethod.HOLIDAY;
		
		// Run
		WorkAvailabilityOfOneDay workAvailability = WorkAvailabilityOfOneDay
				.create(employeeId, availabilityDate, memo, assignmentMethod, Collections.emptyList(), Collections.emptyList());
		
		// assert
		assertThat(workAvailability.getEmployeeId()).isEqualTo("001");
		assertThat(workAvailability.getWorkAvailabilityDate()).isEqualTo(GeneralDate.ymd(2020, 9, 18));
		assertThat(workAvailability.getMemo().v()).isEqualTo("memo");
		
		assertThat(workAvailability.getWorkAvailability().getAssignmentMethod()).isEqualTo(AssignmentMethod.HOLIDAY);
		assertThat(workAvailability.getWorkAvailability()).isInstanceOf(WorkAvailabilityByHoliday.class);
	}
	
	@Test
	public void testCreate_shift() {
		
		String employeeId = "001";
		GeneralDate availabilityDate = GeneralDate.ymd(2020, 9, 18);
		WorkAvailabilityMemo memo = new WorkAvailabilityMemo("memo");
		AssignmentMethod assignmentMethod = AssignmentMethod.SHIFT;
		List<ShiftMasterCode> shiftCodeList = Arrays.asList(
				new ShiftMasterCode("S01"),
				new ShiftMasterCode("S02"));
		
		WorkAvailabilityOfOneDay workAvailability = WorkAvailabilityOfOneDay
				.create(employeeId, availabilityDate, memo, assignmentMethod, shiftCodeList, Collections.emptyList());
		
		assertThat(workAvailability.getWorkAvailability().getAssignmentMethod()).isEqualTo(AssignmentMethod.SHIFT);
		assertThat(workAvailability.getWorkAvailability()).isInstanceOf(WorkAvailabilityByShiftMaster.class);
	}

	@Test
	public void testCreate_timeZone() {
		
		String employeeId = "001";
		GeneralDate availabilityDate = GeneralDate.ymd(2020, 9, 18);
		WorkAvailabilityMemo memo = new WorkAvailabilityMemo("memo");
		AssignmentMethod assignmentMethod = AssignmentMethod.TIME_ZONE;
		List<TimeSpanForCalc> timeZoneList = Arrays.asList(
				new TimeSpanForCalc(new TimeWithDayAttr(100), new TimeWithDayAttr(200)),
				new TimeSpanForCalc(new TimeWithDayAttr(300), new TimeWithDayAttr(400)));
		
		WorkAvailabilityOfOneDay workAvailability = WorkAvailabilityOfOneDay
				.create(employeeId, availabilityDate, memo, assignmentMethod, Collections.emptyList(), timeZoneList);
		
		assertThat(workAvailability.getWorkAvailability().getAssignmentMethod()).isEqualTo(AssignmentMethod.TIME_ZONE);
		assertThat(workAvailability.getWorkAvailability()).isInstanceOf(WorkAvailabilityByTimeZone.class);
	}
	
	@Test
	public void testIsHolidayAvailability_true() {
		
		WorkAvailabilityOfOneDay workAvailability = WorkAvailabilityOfOneDayTestHelper.createWorkAvailabilityOfOneDay();
		
		new Expectations(workAvailability.getWorkAvailability()) {
            {
            	workAvailability.getWorkAvailability().isHolidayAvailability();
            	result = true;
            }
        };
        
        boolean result = workAvailability.isHolidayAvailability();
        
        assertThat(result).isTrue();
	}
	
	@Test
	public void testIsHolidayAvailability_false() {
		
		WorkAvailabilityOfOneDay workAvailability = WorkAvailabilityOfOneDayTestHelper.createWorkAvailabilityOfOneDay();
		
		new Expectations(workAvailability.getWorkAvailability()) {
            {
            	workAvailability.getWorkAvailability().isHolidayAvailability();
            	result = false;
            }
        };
        
        boolean result = workAvailability.isHolidayAvailability();
        
        assertThat(result).isFalse();
	}
	
	@Test
	public void testIsMatchingAvailability_true() {
		
		WorkAvailabilityOfOneDay workAvailability = WorkAvailabilityOfOneDayTestHelper.createWorkAvailabilityOfOneDay();
		
		WorkInformation workInformation = new WorkInformation( 
				
				new WorkTypeCode("001"), new WorkTimeCode("001") 
				);
		
		new Expectations(workAvailability.getWorkAvailability()) {
            {
            	workAvailability.getWorkAvailability().isMatchingWorkAvailability(require, workInformation, new ArrayList<TimeSpanForCalc>());
            	result = true;
            }
        };
        
        boolean result = workAvailability.isMatchingAvailability(require, workInformation, Collections.emptyList());
        
        assertThat(result).isTrue();
	}
	
	@Test
	public void testIsMatchingAvailability_false() {
		
		WorkAvailabilityOfOneDay workAvailability = WorkAvailabilityOfOneDayTestHelper.createWorkAvailabilityOfOneDay();
		
		WorkInformation workInformation = new WorkInformation( 
				new WorkTypeCode("001"),
				new WorkTimeCode("001") 
				);
		
		new Expectations(workAvailability.getWorkAvailability()) {
            {
            	workAvailability.getWorkAvailability()
            		.isMatchingWorkAvailability(require, workInformation, new ArrayList<TimeSpanForCalc>());
            	result = false;
            }
        };
        
        boolean result = workAvailability.isMatchingAvailability(require, workInformation, Collections.emptyList());
        
        assertThat(result).isFalse();
	}
	
	@Test
	public void testGetDisplayInformation() {
		
		WorkAvailabilityOfOneDay workAvailability = WorkAvailabilityOfOneDay
				.create("001", 
						GeneralDate.ymd(2020, 9, 18), 
						new WorkAvailabilityMemo("memo"), 
						AssignmentMethod.SHIFT, 
						Arrays.asList(new ShiftMasterCode("S01")), 
						Collections.emptyList());
		
		WorkAvailabilityDisplayInfo displayInfo = new WorkAvailabilityDisplayInfo(
				AssignmentMethod.SHIFT, 
				Arrays.asList("S01"), 
				new ArrayList<TimeSpanForCalc>());
		
		new Expectations(workAvailability.getWorkAvailability()) {
            {
            	workAvailability.getWorkAvailability().getDisplayInformation(require);
            	result = displayInfo;
            }
        };
        
        WorkAvailabilityDisplayInfoOfOneDay result = workAvailability.getDisplayInformation(require);
        
        assertThat(result.getEmployeeId()).isEqualTo("001");
        assertThat(result.getAvailabilityDate()).isEqualTo(GeneralDate.ymd(2020, 9, 18));
        assertThat(result.getMemo().v()).isEqualTo("memo");
        assertThat(result.getDisplayInfo()).isEqualTo(displayInfo);
	}

}
