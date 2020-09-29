package nts.uk.ctx.at.schedule.dom.shift.management.workexpect;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMaster;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;

@RunWith(JMockit.class)
public class ShiftExpectationTest {
	
	@Injectable
    private WorkExpectation.Require require;
	
	@Test
	public void getters() {
		
		ShiftExpectation shiftExp = ShiftExpectationTestHelper.defaultCreate();
		NtsAssert.invokeGetters(shiftExp);
	}
	
	@Test
	public void testGetAssignmentMethod() {
		
		ShiftExpectation shiftExp = ShiftExpectationTestHelper.defaultCreate();
		
		assertThat(shiftExp.getAssignmentMethod()).isEqualTo(AssignmentMethod.SHIFT);
	}
	
	@Test
	public void testIsHolidayExpectation() {
		
		ShiftExpectation shiftExp = ShiftExpectationTestHelper.defaultCreate();
		
		assertThat(shiftExp.isHolidayExpectation()).isFalse();
	}
	
	@Test
	public void testIsMatchingExpectation_getShiftMaster_empty() {
		
		ShiftExpectation shiftExp = ShiftExpectationTestHelper.defaultCreate();
		
		WorkTypeCode workTypeCode = new WorkTypeCode("001");
		WorkTimeCode workTimeCode = new WorkTimeCode("001");
		WorkInformation workInformation = new WorkInformation(workTypeCode, workTimeCode);
		new Expectations() {
            {
            	require.getShiftMasterByWorkInformation(workTypeCode, workTimeCode);
            	// result = empty
            }
        };
        
        boolean result = shiftExp.isMatchingExpectation(require, workInformation, new ArrayList<>());
        
        assertThat(result).isFalse();
		
	}
	
	@Test
	public void testIsMatchingExpectation_getShiftMaster_notMatch() {
		
		WorkTypeCode workTypeCode = new WorkTypeCode("001");
		WorkTimeCode workTimeCode = new WorkTimeCode("001");
		WorkInformation workInformation = new WorkInformation(workTypeCode, workTimeCode);
		
		ShiftExpectation shiftExp = ShiftExpectationTestHelper.createWithShiftCodes("S01", "S02");
		ShiftMaster shiftMaster = ShiftExpectationTestHelper.createShiftMasterWithCode("S03");
		
		new Expectations() {
            {
            	require.getShiftMasterByWorkInformation(workTypeCode, workTimeCode);
            	result = Optional.of(shiftMaster);
            }
        };
        
        boolean result = shiftExp.isMatchingExpectation(require, workInformation, new ArrayList<>());
        
        assertThat(result).isFalse();
	}

	@Test
	public void testIsMatchingExpectation_getShiftMaster_Match() {
		
		WorkTypeCode workTypeCode = new WorkTypeCode("001");
		WorkTimeCode workTimeCode = new WorkTimeCode("001");
		WorkInformation workInformation = new WorkInformation(workTypeCode, workTimeCode);
		
		ShiftExpectation shiftExpectation = ShiftExpectationTestHelper.createWithShiftCodes("S01", "S02");
		ShiftMaster shiftMaster = ShiftExpectationTestHelper.createShiftMasterWithCode("S02");
		
		new Expectations() {
            {
            	require.getShiftMasterByWorkInformation(workTypeCode, workTimeCode);
            	result = Optional.of(shiftMaster);
            }
        };
        
        boolean result = shiftExpectation.isMatchingExpectation(require, workInformation, new ArrayList<>());
        
        assertThat(result).isTrue();
	}
	
	@Test
	public void testGetDisplayInformation() {
		
		 ShiftExpectation shiftExp = ShiftExpectationTestHelper.createWithShiftCodes("S01", "S02", "S03");
		
		ShiftMaster shiftMaster1 = ShiftExpectationTestHelper.createShiftMasterWithCodeName("S01", "S01-name");
		ShiftMaster shiftMaster2 = ShiftExpectationTestHelper.createShiftMasterWithCodeName("S02", "S02-name");
		
		new Expectations() {
            {
            	require.getShiftMaster(shiftExp.getWorkableShiftCodeList());
            	result = Arrays.asList(shiftMaster1, shiftMaster2);
            }
        };
		
		WorkExpectDisplayInfo displayInfo = shiftExp.getDisplayInformation(require);
		
		assertThat(displayInfo.getMethod()).isEqualTo(AssignmentMethod.SHIFT);
		assertThat(displayInfo.getNameList())
				.containsOnly("S01-name", "S02-name");
		assertThat(displayInfo.getTimeZoneList()).isEmpty();
	}
	
}
