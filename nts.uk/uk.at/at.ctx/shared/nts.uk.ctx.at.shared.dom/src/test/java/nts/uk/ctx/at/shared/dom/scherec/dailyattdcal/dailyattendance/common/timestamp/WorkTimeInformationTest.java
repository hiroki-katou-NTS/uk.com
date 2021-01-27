package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.Test;

import mockit.Injectable;
import mockit.Mock;
import mockit.MockUp;
import nts.uk.shr.com.time.TimeWithDayAttr;

public class WorkTimeInformationTest {
	
	@Test
	public void testCreateByAutomaticSet(@Injectable TimeWithDayAttr time) {
		
		// Mock
		ReasonTimeChange reason = new ReasonTimeChange(TimeChangeMeans.AUTOMATIC_SET, Optional.empty());
		new MockUp<ReasonTimeChange>() {
	        @Mock
	        public ReasonTimeChange createByAutomaticSet() {
	            return reason;
	        }
	    };
		
	    // Act
		WorkTimeInformation target = WorkTimeInformation.createByAutomaticSet(time);
		
		// Assert
		assertThat(target.getTimeWithDay().get()).isEqualTo(time);
		assertThat(target.getReasonTimeChange()).isEqualTo(reason);
		
	}

}
