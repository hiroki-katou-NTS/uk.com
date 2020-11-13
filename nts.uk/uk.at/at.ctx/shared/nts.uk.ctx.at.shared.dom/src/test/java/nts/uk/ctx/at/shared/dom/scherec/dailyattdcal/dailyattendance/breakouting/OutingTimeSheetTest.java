package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting;
/**
 * UnitTest: 外出時間帯
 * @author lan_lt
 *
 */

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.Expectations;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.OvertimeDeclaration;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeActualStamp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkStamp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkStampTest;
import nts.uk.shr.com.time.TimeWithDayAttr;
/**
 * UnitTest: 外出時間帯
 * @author lan_lt
 *
 */
@RunWith(JMockit.class)
public class OutingTimeSheetTest {
	@Test
	public void getters() {
		//外出
		val goOut = Helper.createTimeAcutualStamp(new TimeWithDayAttr(2000));
		
		//戻り
		val comeBack = Helper.createTimeAcutualStamp(new TimeWithDayAttr(3000));

		//外出時間帯
		val outingTime = Helper.createOutingTime(
				Optional.of(goOut), 
				Optional.of(comeBack));
		
		NtsAssert.invokeGetters(outingTime);
	}
	
	/**
	 * 計算可能な状態か判断する(isCalcState) == false
	 */
	@Test
	public void getTimeZone_empty_1() {	
		
		//外出
		val goOut = Helper.createTimeAcutualStamp(new TimeWithDayAttr(1600));
		
		//戻り empty
		
		//外出時間帯
		val outingTime = Helper.createOutingTime(Optional.of(goOut), Optional.empty());
		
		new Expectations(outingTime) {{
			
			outingTime.isCalcState();
			result = false;
		}};
		
		// Action
		val actual = outingTime.getTimeZone();
		
		// Assert
		assertThat(actual).isEmpty();
		
	}
	
	/**
	 * 計算可能な状態か判断する(isCalcState) == true
	 * 外出(goOut) > (戻り)comeBack
	 * 
	 */
	@Test
	public void getTimeZone_empty_2() {
		
		//外出
		val goOut = Helper.createTimeAcutualStamp(new TimeWithDayAttr(2000));
		
		//戻り
		val comeBack = Helper.createTimeAcutualStamp(new TimeWithDayAttr(1000));

		//外出時間帯
		val outingTime = Helper.createOutingTime(
				Optional.of(goOut), 
				Optional.of(comeBack));
		
		new Expectations(outingTime, goOut) {{
			
			outingTime.isCalcState();
			result = true;
			
		}};
		
		val actual = outingTime.getTimeZone();
		
		assertThat(actual).isEmpty();
		
	}
	
	/**
	 * 計算可能な状態か判断する(isCalcState) == true
	 * 外出(goOut) == (戻り)comeBack
	 * 
	 */
	@Test
	public void getTimeZone_empty_3() {
		
		//外出
		val goOut = Helper.createTimeAcutualStamp(new TimeWithDayAttr(2000));
		
		//戻り
		val comeBack = Helper.createTimeAcutualStamp(new TimeWithDayAttr(2000));

		//外出時間帯
		val outingTime = Helper.createOutingTime(
				Optional.of(goOut), 
				Optional.of(comeBack));
		
		new Expectations(outingTime) {{
			
			outingTime.isCalcState();
			result = true;
		}};
		
		val actual = outingTime.getTimeZone();
		
		assertThat(actual).isEmpty();
	}
	
	/**
	 * 計算可能な状態か判断する(isCalcState) == true
	 * 外出(goOut) < (戻り)comeBack
	 * 
	 */
	@Test
	public void getTimeZone_successfully() {
		
		//外出
		val goOut = Helper.createTimeAcutualStamp(new TimeWithDayAttr(2000));
		
		//戻り
		val comeBack = Helper.createTimeAcutualStamp(new TimeWithDayAttr(3000));

		//外出時間帯
		val outingTime = Helper.createOutingTime(
				Optional.of(goOut), 
				Optional.of(comeBack));
		
		new Expectations(outingTime) {{
			
			outingTime.isCalcState();
			result = true;
		}};
		
		val actual = outingTime.getTimeZone();
		
		assertThat(actual.get().getStart().v()).isEqualTo(2000);
		assertThat(actual.get().getEnd().v()).isEqualTo(3000);
	}
	
	static class Helper{
		
		/**
		 * 時刻（日区分付き）を指定して勤怠打刻を作る
		 * 時刻以外はdummy
		 * @param timeWithDay
		 * @return
		 */
		public static WorkStamp createStamp(TimeWithDayAttr timeWithDay) {
			
			return WorkStampTest.WorkStampHelper.createWorkStampWithTimeWithDay(timeWithDay.v());
		}
		
		/**
		 * 時刻（日区分付き）を指定して勤怠打刻(実打刻付き)を作る
		 * 時刻以外はdummy
		 * @param stamp
		 * @return
		 */
		public static TimeActualStamp createTimeAcutualStamp (TimeWithDayAttr stamp) {
			return new TimeActualStamp(
					Helper.createStamp(new TimeWithDayAttr(1000))
					, Helper.createStamp(stamp)
					, 1
	                , new OvertimeDeclaration(new AttendanceTime(100), new AttendanceTime(0))
	                , null);
		}
		
		/**
		 * 外出・戻りの勤怠打刻を指定して外出時間帯を作る
		 * 外出・戻り以外はdummy
		 * @param goOut
		 * @param comeBack
		 * @return
		 */
		public static OutingTimeSheet createOutingTime(Optional<TimeActualStamp> goOut, Optional<TimeActualStamp> comeBack) {
			return new OutingTimeSheet(
					new OutingFrameNo(1)
					, goOut
					, new AttendanceTime(1600)
					, new AttendanceTime(1700)
					, GoingOutReason.PRIVATE
					, comeBack);
		}
	}
}
