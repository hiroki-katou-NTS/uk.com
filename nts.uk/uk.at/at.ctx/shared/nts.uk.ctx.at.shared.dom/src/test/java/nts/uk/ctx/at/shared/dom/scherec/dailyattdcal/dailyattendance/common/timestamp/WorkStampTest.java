package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.shr.com.time.TimeWithDayAttr;
/**
 * UnitTest: 勤怠打刻
 * @author lan_lt
 *
 */
@RunWith(JMockit.class)
public class WorkStampTest {
	@Test
	public void getters() {
		val workStamp = WorkStampHelper.createWorkStamp();
		NtsAssert.invokeGetters(workStamp);
	}
	
	/**
	 * less_than = false
	 * workStamp = 1730 
	 * compareWorkStamp = 830
	 */
	@Test
	public void check_less_than_false() {		
		val workStamp = new WorkStamp(new TimeWithDayAttr(100)
				, new TimeWithDayAttr(1730)
				, new WorkLocationCD("001")
				, TimeChangeMeans.REAL_STAMP
				, EngravingMethod.TIME_RECORD_FINGERPRINT_ENGRAVING
				);
		
		val compareWorkStamp = new WorkStamp(new TimeWithDayAttr(100)
				, new TimeWithDayAttr(830)
				, new WorkLocationCD("001")
				, TimeChangeMeans.REAL_STAMP
				, EngravingMethod.TIME_RECORD_FINGERPRINT_ENGRAVING
				);
		assertThat(workStamp.lessThan(compareWorkStamp)).isFalse();
		
	}
	
	/**
	 * less_than = false
	 * workStamp = 830 
	 * compareWorkStamp = 830
	 */
	@Test
	public void check_less_than_false_1() {		
		val workStamp = new WorkStamp(new TimeWithDayAttr(100)
				, new TimeWithDayAttr(830)
				, new WorkLocationCD("001")
				, TimeChangeMeans.REAL_STAMP
				, EngravingMethod.TIME_RECORD_FINGERPRINT_ENGRAVING
				);
		
		val compareWorkStamp = new WorkStamp(new TimeWithDayAttr(100)
				, new TimeWithDayAttr(830)
				, new WorkLocationCD("001")
				, TimeChangeMeans.REAL_STAMP
				, EngravingMethod.TIME_RECORD_FINGERPRINT_ENGRAVING
				);
		assertThat(workStamp.lessThan(compareWorkStamp)).isFalse();
	}

	/**
	 * less_than = false
	 * workStamp = 830 
	 * compareWorkStamp = 1730
	 */
	@Test
	public void check_less_than_true() {		
		val workStamp = new WorkStamp(new TimeWithDayAttr(100)
				, new TimeWithDayAttr(830)
				, new WorkLocationCD("001")
				, TimeChangeMeans.REAL_STAMP
				, EngravingMethod.TIME_RECORD_FINGERPRINT_ENGRAVING
				);
		
		val compareWorkStamp = new WorkStamp(new TimeWithDayAttr(100)
				, new TimeWithDayAttr(1730)
				, new WorkLocationCD("001")
				, TimeChangeMeans.REAL_STAMP
				, EngravingMethod.TIME_RECORD_FINGERPRINT_ENGRAVING
				);
		assertThat(workStamp.lessThan(compareWorkStamp)).isTrue();
	}
	
	static class WorkStampHelper{
		public static WorkStamp createWorkStamp() {
			return new WorkStamp(new TimeWithDayAttr(100)
					, new TimeWithDayAttr(830)
					, new WorkLocationCD("001")
					, TimeChangeMeans.REAL_STAMP
					, EngravingMethod.TIME_RECORD_FINGERPRINT_ENGRAVING
					);
		}
	}

}
