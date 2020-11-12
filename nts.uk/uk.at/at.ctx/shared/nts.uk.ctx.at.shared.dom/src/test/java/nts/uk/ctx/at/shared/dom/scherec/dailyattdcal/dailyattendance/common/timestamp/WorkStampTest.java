package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import mockit.Mock;
import mockit.MockUp;
import nts.uk.shr.com.time.TimeWithDayAttr;

public class WorkStampTest {
	
	@Test
	public void testCreateByAutomaticSet() {
		
		// Arrange, 
		TimeWithDayAttr time = new TimeWithDayAttr(60);
		
		WorkTimeInformation workTimeInformation = new WorkTimeInformation(
				ReasonTimeChange.createByAutomaticSet(), 
				time);
		
		// Mock
		new MockUp<WorkTimeInformation>() {
	        @Mock
	        public WorkTimeInformation createByAutomaticSet(TimeWithDayAttr time) {
	            return workTimeInformation;
	        }
	    };
		
	    // Action
		WorkStamp target = WorkStamp.createByAutomaticSet(time);
		
		// Assert
		assertThat(target.getTimeDay()).isEqualTo(workTimeInformation);
		assertThat(target.getLocationCode()).isEmpty();
	}

}
