package nts.uk.ctx.at.schedule.dom.shift.management.workexpect;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
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
	WorkExpectation.Require require;
	
	@Test
	public void testCreate_holiday() {
		
		String employeeId = "001";
		GeneralDate expectingDate = GeneralDate.ymd(2020, 9, 18);
		WorkExpectationMemo memo = new WorkExpectationMemo("memo");
		AssignmentMethod assignmentMethod = AssignmentMethod.HOLIDAY;
		
		WorkExpectationOfOneDay expectation = WorkExpectationOfOneDay
				.create(employeeId, expectingDate, memo, assignmentMethod, new ArrayList<>(), new ArrayList<>());
		
		assertThat(expectation.getEmployeeId()).isEqualTo("001");
		assertThat(expectation.getExpectingDate()).isEqualTo(GeneralDate.ymd(2020, 9, 18));
		assertThat(expectation.getMemo().v()).isEqualTo("memo");
		assertThat(expectation.getWorkExpectation().getAssignmentMethod()).isEqualTo(AssignmentMethod.HOLIDAY);
		// ??
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
				.create(employeeId, expectingDate, memo, assignmentMethod, shiftCodeList, new ArrayList<>());
		
		assertThat(expectation.getEmployeeId()).isEqualTo("001");
		assertThat(expectation.getExpectingDate()).isEqualTo(GeneralDate.ymd(2020, 9, 18));
		assertThat(expectation.getMemo().v()).isEqualTo("memo");
		assertThat(expectation.getWorkExpectation().getAssignmentMethod()).isEqualTo(AssignmentMethod.SHIFT);
		// ??
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
				.create(employeeId, expectingDate, memo, assignmentMethod, new ArrayList<>(), timeZoneList);
		
		assertThat(expectation.getEmployeeId()).isEqualTo("001");
		assertThat(expectation.getExpectingDate()).isEqualTo(GeneralDate.ymd(2020, 9, 18));
		assertThat(expectation.getMemo().v()).isEqualTo("memo");
		assertThat(expectation.getWorkExpectation().getAssignmentMethod()).isEqualTo(AssignmentMethod.TIME_ZONE);
		// ??
	}
	
	@Test
	public void testIsHolidayExpectation_true() {
		
		WorkExpectationOfOneDay expectation = WorkExpectationOfOneDay
				.create("001", 
						GeneralDate.ymd(2020, 9, 18), 
						new WorkExpectationMemo("memo"), 
						AssignmentMethod.HOLIDAY, 
						new ArrayList<>(), 
						new ArrayList<>());
		
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
		
		WorkExpectationOfOneDay expectation = WorkExpectationOfOneDay
				.create("001", 
						GeneralDate.ymd(2020, 9, 18), 
						new WorkExpectationMemo("memo"), 
						AssignmentMethod.SHIFT, 
						 Arrays.asList(new ShiftMasterCode("S01")), 
						new ArrayList<>());
		
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
		
		WorkExpectationOfOneDay expectation = WorkExpectationOfOneDay
				.create("001", 
						GeneralDate.ymd(2020, 9, 18), 
						new WorkExpectationMemo("memo"), 
						AssignmentMethod.HOLIDAY, 
						new ArrayList<>(), 
						new ArrayList<>());
		
		WorkInformation workInformation = new WorkInformation( 
				
				new WorkTypeCode("001"), new WorkTimeCode("001") 
				);
		
		new Expectations(expectation.getWorkExpectation()) {
            {
            	expectation.getWorkExpectation().isMatchingExpectation(require, workInformation, new ArrayList<TimeSpanForCalc>());
            	result = true;
            }
        };
        
        boolean result = expectation.isMatchingExpectation(require, workInformation, new ArrayList<TimeSpanForCalc>());
        
        assertThat(result).isTrue();
	}
	
	@Test
	public void testIsMatchingExpectation_false() {
		
		WorkExpectationOfOneDay expectation = WorkExpectationOfOneDay
				.create("001", 
						GeneralDate.ymd(2020, 9, 18), 
						new WorkExpectationMemo("memo"), 
						AssignmentMethod.HOLIDAY, 
						new ArrayList<>(), 
						new ArrayList<>());
		
		WorkInformation workInformation = new WorkInformation( 
				new WorkTypeCode("001"),
				new WorkTimeCode("001") 
				);
		
		new Expectations(expectation.getWorkExpectation()) {
            {
            	expectation.getWorkExpectation().isMatchingExpectation(require, workInformation, new ArrayList<TimeSpanForCalc>());
            	result = false;
            }
        };
        
        boolean result = expectation.isMatchingExpectation(require, workInformation, new ArrayList<TimeSpanForCalc>());
        
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
						new ArrayList<>());
		
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
