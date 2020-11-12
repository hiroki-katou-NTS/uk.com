package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import mockit.Mock;
import mockit.MockUp;
import nts.uk.shr.com.time.TimeWithDayAttr;

public class WorkTimeInformationTest {
	
	@Test
	public void testCreateByAutomaticSet() {
		
		// Arrange, 
		TimeWithDayAttr time = new TimeWithDayAttr(60);
		ReasonTimeChange reason = new ReasonTimeChange(TimeChangeMeans.AUTOMATIC_SET, null);
		
		// Mock
		new MockUp<ReasonTimeChange>() {
	        @Mock
	        public ReasonTimeChange createByAutomaticSet() {
	            return reason;
	        }
	    };
		
	    // Act
		WorkTimeInformation target = WorkTimeInformation.createByAutomaticSet(time);
		
		// Assert
		assertThat(target.getReasonTimeChange()).isEqualTo(reason);
		assertThat(target.getTimeWithDay().get()).isEqualTo(time);
		
	}

}
