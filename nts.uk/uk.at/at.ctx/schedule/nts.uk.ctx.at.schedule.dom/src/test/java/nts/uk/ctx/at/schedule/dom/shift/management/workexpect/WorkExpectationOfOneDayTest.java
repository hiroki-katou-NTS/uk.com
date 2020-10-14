package nts.uk.ctx.at.schedule.dom.shift.management.workexpect;

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
public class WorkExpectationOfOneDayTest {
	
	@Injectable
	WorkExpectationOfOneDay.Require require;
	
	@Test
	public void getters() {
		
		WorkExpectationOfOneDay expectation = WorkExpectationOfOneDayTestHelper.createExpectationOfOneDay();
		NtsAssert.invokeGetters(expectation);  
	}
	
	@Test
	public void testCreate_holiday() {
		
		// Prepare
		String employeeId = "001";
		GeneralDate expectingDate = GeneralDate.ymd(2020, 9, 18);
		WorkExpectationMemo memo = new WorkExpectationMemo("memo");
		AssignmentMethod assignmentMethod = AssignmentMethod.HOLIDAY;
		
		// Run
		WorkExpectationOfOneDay expectation = WorkExpectationOfOneDay
				.create(employeeId, expectingDate, memo, assignmentMethod, Collections.emptyList(), Collections.emptyList());
		
		// assert
		assertThat(expectation.getEmployeeId()).isEqualTo("001");
		assertThat(expectation.getExpectingDate()).isEqualTo(GeneralDate.ymd(2020, 9, 18));
		assertThat(expectation.getMemo().v()).isEqualTo("memo");
		
		assertThat(expectation.getWorkExpectation().getAssignmentMethod()).isEqualTo(AssignmentMethod.HOLIDAY);
		assertThat(expectation.getWorkExpectation()).isInstanceOf(HolidayExpectation.class);
	}
	
	@Test
	public void testCreate_shift() {
		
		String employeeId = "001";
		GeneralDate expectingDate = GeneralDate.ymd(2020, 9, 18);
		WorkExpectationMemo memo = new WorkExpectationMemo("memo");
		AssignmentMethod assignmentMethod = AssignmentMethod.SHIFT;
		List<ShiftMasterCode> shiftCodeList = Arrays.asList(
				new ShiftMasterCode("S01"),
				new ShiftMasterCode("S02"));
		
		WorkExpectationOfOneDay expectation = WorkExpectationOfOneDay
				.create(employeeId, expectingDate, memo, assignmentMethod, shiftCodeList, Collections.emptyList());
		
		assertThat(expectation.getWorkExpectation().getAssignmentMethod()).isEqualTo(AssignmentMethod.SHIFT);
		assertThat(expectation.getWorkExpectation()).isInstanceOf(ShiftExpectation.class);
	}

	@Test
	public void testCreate_timeZone() {
		
		String employeeId = "001";
		GeneralDate expectingDate = GeneralDate.ymd(2020, 9, 18);
		WorkExpectationMemo memo = new WorkExpectationMemo("memo");
		AssignmentMethod assignmentMethod = AssignmentMethod.TIME_ZONE;
		List<TimeSpanForCalc> timeZoneList = Arrays.asList(
				new TimeSpanForCalc(new TimeWithDayAttr(100), new TimeWithDayAttr(200)),
				new TimeSpanForCalc(new TimeWithDayAttr(300), new TimeWithDayAttr(400)));
		
		WorkExpectationOfOneDay expectation = WorkExpectationOfOneDay
				.create(employeeId, expectingDate, memo, assignmentMethod, Collections.emptyList(), timeZoneList);
		
		assertThat(expectation.getWorkExpectation().getAssignmentMethod()).isEqualTo(AssignmentMethod.TIME_ZONE);
		assertThat(expectation.getWorkExpectation()).isInstanceOf(TimeZoneExpectation.class);
	}
	
	@Test
	public void testIsHolidayExpectation_true() {
		
		WorkExpectationOfOneDay expectation = WorkExpectationOfOneDayTestHelper.createExpectationOfOneDay();
		
		new Expectations(expectation.getWorkExpectation()) {
            {
            	expectation.getWorkExpectation().isHolidayExpectation();
            	result = true;
            }
        };
        
        boolean result = expectation.isHolidayExpectation();
        
        assertThat(result).isTrue();
	}
	
	@Test
	public void testIsHolidayExpectation_false() {
		
		WorkExpectationOfOneDay expectation = WorkExpectationOfOneDayTestHelper.createExpectationOfOneDay();
		
		new Expectations(expectation.getWorkExpectation()) {
            {
            	expectation.getWorkExpectation().isHolidayExpectation();
            	result = false;
            }
        };
        
        boolean result = expectation.isHolidayExpectation();
        
        assertThat(result).isFalse();
	}
	
	@Test
	public void testIsMatchingExpectation_true() {
		
		WorkExpectationOfOneDay expectation = WorkExpectationOfOneDayTestHelper.createExpectationOfOneDay();
		
		WorkInformation workInformation = new WorkInformation( 
				
				new WorkTypeCode("001"), new WorkTimeCode("001") 
				);
		
		new Expectations(expectation.getWorkExpectation()) {
            {
            	expectation.getWorkExpectation().isMatchingExpectation(require, workInformation, new ArrayList<TimeSpanForCalc>());
            	result = true;
            }
        };
        
        boolean result = expectation.isMatchingExpectation(require, workInformation, Collections.emptyList());
        
        assertThat(result).isTrue();
	}
	
	@Test
	public void testIsMatchingExpectation_false() {
		
		WorkExpectationOfOneDay expectation = WorkExpectationOfOneDayTestHelper.createExpectationOfOneDay();
		
		WorkInformation workInformation = new WorkInformation( 
				new WorkTypeCode("001"),
				new WorkTimeCode("001") 
				);
		
		new Expectations(expectation.getWorkExpectation()) {
            {
            	expectation.getWorkExpectation()
            		.isMatchingExpectation(require, workInformation, new ArrayList<TimeSpanForCalc>());
            	result = false;
            }
        };
        
        boolean result = expectation.isMatchingExpectation(require, workInformation, Collections.emptyList());
        
        assertThat(result).isFalse();
	}
	
	@Test
	public void testGetDisplayInformation() {
		
		WorkExpectationOfOneDay expectation = WorkExpectationOfOneDay
				.create("001", 
						GeneralDate.ymd(2020, 9, 18), 
						new WorkExpectationMemo("memo"), 
						AssignmentMethod.SHIFT, 
						Arrays.asList(new ShiftMasterCode("S01")), 
						Collections.emptyList());
		
		WorkExpectDisplayInfo displayInfo = new WorkExpectDisplayInfo(
				AssignmentMethod.SHIFT, 
				Arrays.asList("S01"), 
				new ArrayList<TimeSpanForCalc>());
		
		new Expectations(expectation.getWorkExpectation()) {
            {
            	expectation.getWorkExpectation().getDisplayInformation(require);
            	result = displayInfo;
            }
        };
        
        WorkExpectDisplayInfoOfOneDay result = expectation.getDisplayInformation(require);
        
        assertThat(result.getEmployeeId()).isEqualTo("001");
        assertThat(result.getExpectingDate()).isEqualTo(GeneralDate.ymd(2020, 9, 18));
        assertThat(result.getMemo().v()).isEqualTo("memo");
        assertThat(result.getDisplayInfo()).isEqualTo(displayInfo);
	}

}
