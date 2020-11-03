package nts.uk.ctx.at.schedule.dom.shift.management.workavailability;

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
public class WorkAvailabilityByShiftMasterTest {
	
	@Injectable
    private WorkAvailability.Require require;
	
	@Test
	public void getters() {
		
		WorkAvailabilityByShiftMaster shiftExp = WorkAvailabilityByShiftMasterTestHelper.defaultCreate();
		NtsAssert.invokeGetters(shiftExp);
	}
	
	@Test
	public void testGetAssignmentMethod() {
		
		WorkAvailabilityByShiftMaster shiftExp = WorkAvailabilityByShiftMasterTestHelper.defaultCreate();
		
		assertThat(shiftExp.getAssignmentMethod()).isEqualTo(AssignmentMethod.SHIFT);
	}
	
	@Test
	public void testIsHolidayAvailability() {
		
		WorkAvailabilityByShiftMaster shiftExp = WorkAvailabilityByShiftMasterTestHelper.defaultCreate();
		
		assertThat(shiftExp.isHolidayAvailability()).isFalse();
	}
	
	@Test
	public void testIsMatchingWorkAvailability_getShiftMaster_empty() {
		
		WorkAvailabilityByShiftMaster shiftExp = WorkAvailabilityByShiftMasterTestHelper.defaultCreate();
		
		WorkTypeCode workTypeCode = new WorkTypeCode("001");
		WorkTimeCode workTimeCode = new WorkTimeCode("001");
		WorkInformation workInformation = new WorkInformation(workTypeCode, workTimeCode);
		new Expectations() {
            {
            	require.getShiftMasterByWorkInformation(workTypeCode, workTimeCode);
            	// result = empty
            }
        };
        
        boolean result = shiftExp.isMatchingWorkAvailability(require, workInformation, new ArrayList<>());
        
        assertThat(result).isFalse();
		
	}
	
	@Test
	public void testIsMatchingWorkAvailability_getShiftMaster_notMatch() {
		
		WorkTypeCode workTypeCode = new WorkTypeCode("001");
		WorkTimeCode workTimeCode = new WorkTimeCode("001");
		WorkInformation workInformation = new WorkInformation(workTypeCode, workTimeCode);
		
		WorkAvailabilityByShiftMaster shiftExp = WorkAvailabilityByShiftMasterTestHelper.createWithShiftCodes("S01", "S02");
		ShiftMaster shiftMaster = WorkAvailabilityByShiftMasterTestHelper.createShiftMasterWithCode("S03");
		
		new Expectations() {
            {
            	require.getShiftMasterByWorkInformation(workTypeCode, workTimeCode);
            	result = Optional.of(shiftMaster);
            }
        };
        
        boolean result = shiftExp.isMatchingWorkAvailability(require, workInformation, new ArrayList<>());
        
        assertThat(result).isFalse();
	}

	@Test
	public void testIsMatchingWorkAvailability_getShiftMaster_Match() {
		
		WorkTypeCode workTypeCode = new WorkTypeCode("001");
		WorkTimeCode workTimeCode = new WorkTimeCode("001");
		WorkInformation workInformation = new WorkInformation(workTypeCode, workTimeCode);
		
		WorkAvailabilityByShiftMaster shiftAvailability = WorkAvailabilityByShiftMasterTestHelper.createWithShiftCodes("S01", "S02");
		ShiftMaster shiftMaster = WorkAvailabilityByShiftMasterTestHelper.createShiftMasterWithCode("S02");
		
		new Expectations() {
            {
            	require.getShiftMasterByWorkInformation(workTypeCode, workTimeCode);
            	result = Optional.of(shiftMaster);
            }
        };
        
        boolean result = shiftAvailability.isMatchingWorkAvailability(require, workInformation, new ArrayList<>());
        
        assertThat(result).isTrue();
	}
	
	@Test
	public void testGetDisplayInformation() {
		
		 WorkAvailabilityByShiftMaster shiftExp = WorkAvailabilityByShiftMasterTestHelper.createWithShiftCodes("S01", "S02", "S03");
		
		ShiftMaster shiftMaster1 = WorkAvailabilityByShiftMasterTestHelper.createShiftMasterWithCodeName("S01", "S01-name");
		ShiftMaster shiftMaster2 = WorkAvailabilityByShiftMasterTestHelper.createShiftMasterWithCodeName("S02", "S02-name");
		
		new Expectations() {
            {
            	require.getShiftMaster(shiftExp.getWorkableShiftCodeList());
            	result = Arrays.asList(shiftMaster1, shiftMaster2);
            }
        };
		
		WorkAvailabilityDisplayInfo displayInfo = shiftExp.getDisplayInformation(require);
		
		assertThat(displayInfo.getMethod()).isEqualTo(AssignmentMethod.SHIFT);
		assertThat(displayInfo.getNameList())
				.containsOnly("S01-name", "S02-name");
		assertThat(displayInfo.getTimeZoneList()).isEmpty();
	}
	
}
