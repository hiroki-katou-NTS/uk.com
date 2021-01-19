package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.Test;

import lombok.val;
import mockit.Injectable;
import mockit.Mock;
import mockit.MockUp;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.shr.com.time.TimeWithDayAttr;
/**
 * UnitTest: 勤怠打刻
 * @author lan_lt
 *
 */
public class WorkStampTest {
	@Test
	public void getters() {
		val workStamp = WorkStampHelper.createWorkStampWithTimeWithDay(1);
		NtsAssert.invokeGetters(workStamp);
	}
	
	@Test
	public void testCreateByAutomaticSet(@Injectable TimeWithDayAttr time) {
		
		// Mock
		WorkTimeInformation workTimeInformation = new WorkTimeInformation(
				ReasonTimeChange.createByAutomaticSet(), 
				time);
		
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
		assertThat(target.getTimeDay().getTimeWithDay().get()).isEqualTo(time);
		assertThat(target.getLocationCode()).isEmpty();
	}
	
	/**
	 * less_than = false
	 * workStamp = 830
	 * compareWorkStamp = 829
	 */
	@Test
	public void check_less_than_false() {

		val workStamp = WorkStampHelper.createWorkStampWithTimeWithDay(830);
		val compareWorkStamp = WorkStampHelper.createWorkStampWithTimeWithDay(829);
		
		assertThat(workStamp.lessThan(compareWorkStamp)).isFalse();
		
	}
	
	/**
	 * less_than = false
	 * workStamp = 830
	 * compareWorkStamp = 830
	 */
	@Test
	public void check_less_than_false_1() {
		
		val workStamp = WorkStampHelper.createWorkStampWithTimeWithDay(830);
		val compareWorkStamp = WorkStampHelper.createWorkStampWithTimeWithDay(830);
		
		assertThat(workStamp.lessThan(compareWorkStamp)).isFalse();
	}

	/**
	 * less_than = true
	 * workStamp = 830
	 * compareWorkStamp = 831
	 */
	@Test
	public void check_less_than_true() {
		
		val workStamp = WorkStampHelper.createWorkStampWithTimeWithDay(830);
		val compareWorkStamp = WorkStampHelper.createWorkStampWithTimeWithDay(831);
		
		assertThat(workStamp.lessThan(compareWorkStamp)).isTrue();
	}
	
	public static class WorkStampHelper{
		
		public static WorkStamp createWorkStampWithTimeWithDay(int timeWithDay) {
			return new WorkStamp(
					new WorkTimeInformation(
							new ReasonTimeChange(TimeChangeMeans.REAL_STAMP, null ),
							new TimeWithDayAttr(timeWithDay)),
					Optional.of(new WorkLocationCD("001")));
		}
		
	}

}
