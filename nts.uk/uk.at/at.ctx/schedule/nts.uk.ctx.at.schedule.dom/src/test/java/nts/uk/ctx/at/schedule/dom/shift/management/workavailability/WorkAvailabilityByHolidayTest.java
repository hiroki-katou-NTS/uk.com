package nts.uk.ctx.at.schedule.dom.shift.management.workavailability;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.WorkStyle;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;

@RunWith(JMockit.class)
public class WorkAvailabilityByHolidayTest {
	
	@Injectable
    private WorkAvailability.Require require;
	
	@Test
	public void getters() {
		
		WorkAvailabilityByHoliday holidayexp = new WorkAvailabilityByHoliday();
		
		NtsAssert.invokeGetters(holidayexp);
	}
	
	@Test
	public void testGetAssignmentMethod() {
		assertThat(new WorkAvailabilityByHoliday().getAssignmentMethod())
			.isEqualTo(AssignmentMethod.HOLIDAY);
	}
	
	@Test
	public void testIsHolidayAvailability() {
		assertThat(new WorkAvailabilityByHoliday().isHolidayAvailability()).isTrue();
	}
	
	@Test
	public void testIsMatchingWorkAvailability_getWorkStyle_empty() {
		
		WorkInformation workInformation = new WorkInformation(new WorkTypeCode("001"), new WorkTimeCode("001"));
		
		new Expectations(workInformation) {
            {
            	workInformation.getWorkStyle(require);
            	result = Optional.empty();
            }
        };
        
        boolean result = new WorkAvailabilityByHoliday().isMatchingWorkAvailability(require, workInformation, new ArrayList<>());
        assertThat(result).isFalse();
		
	}
	
	@Test
	public void testIsMatchingWorkAvailability_getWorkStyle_dif_OneDayRest() {
		
		WorkInformation workInformation = new WorkInformation(new WorkTypeCode("001"), new WorkTimeCode("001"));
		
		new Expectations(workInformation) {
            {
            	workInformation.getWorkStyle(require);
            	result = Optional.of(WorkStyle.ONE_DAY_WORK);
            }
        };
        
        boolean result = new WorkAvailabilityByHoliday().isMatchingWorkAvailability(require, workInformation, new ArrayList<>());
        assertThat(result).isFalse();
		
	}
	
	@Test
	public void testIsMatchingWorkAvailability_getWorkStyle_OneDayRest() {
		
		WorkInformation workInformation = new WorkInformation(new WorkTypeCode("001"), new WorkTimeCode("001"));
		
		new Expectations(workInformation) {
            {
            	workInformation.getWorkStyle(require);
            	result = Optional.of(WorkStyle.ONE_DAY_REST);
            }
        };
        
        boolean result = new WorkAvailabilityByHoliday().isMatchingWorkAvailability(require, workInformation, new ArrayList<>());
        assertThat(result).isTrue();
		
	}
	
	
	@Test
	public void testGetDisplayInformation() {
		WorkAvailabilityDisplayInfo displayInfo = new WorkAvailabilityByHoliday().getDisplayInformation(require);
		
		assertThat(displayInfo.getMethod()).isEqualTo(AssignmentMethod.HOLIDAY);
		assertThat(displayInfo.getNameList()).isEmpty();
		assertThat(displayInfo.getTimeZoneList()).isEmpty();
	}

}
