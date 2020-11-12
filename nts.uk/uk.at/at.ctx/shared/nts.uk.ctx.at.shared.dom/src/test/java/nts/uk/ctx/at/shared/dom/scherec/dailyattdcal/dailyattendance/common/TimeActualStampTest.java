package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.Test;

import mockit.Mock;
import mockit.MockUp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkStamp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkTimeInformation;
import nts.uk.shr.com.time.TimeWithDayAttr;

public class TimeActualStampTest {
	
	@Test
	public void testCreateByAutomaticSet() {
		
		// Arrange
		TimeWithDayAttr time = new TimeWithDayAttr(60);
		WorkStamp workStamp = new WorkStamp(
				WorkTimeInformation.createByAutomaticSet(time), 
				Optional.empty());
		
		// Mock
		new MockUp<WorkStamp>() {
	        @Mock
	        public WorkStamp createByAutomaticSet(TimeWithDayAttr time) {
	            return workStamp;
	        }
	    };
		
		// Action
		TimeActualStamp target = TimeActualStamp.createByAutomaticSet(time);
		
		// Assert
		assertThat(target.getActualStamp()).isEmpty();
		assertThat(target.getStamp().get()).isEqualTo(workStamp);
		assertThat(target.getNumberOfReflectionStamp()).isEqualTo(1);
		assertThat(target.getOvertimeDeclaration()).isEmpty();
		assertThat(target.getTimeVacation()).isEmpty();
		
	}

}
