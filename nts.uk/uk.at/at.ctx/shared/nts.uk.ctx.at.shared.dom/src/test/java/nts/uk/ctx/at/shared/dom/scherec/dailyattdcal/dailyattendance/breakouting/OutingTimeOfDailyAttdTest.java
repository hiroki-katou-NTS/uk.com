package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeActualStamp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.EngravingMethod;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.TimeChangeMeans;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkLocationCD;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkStamp;
import nts.uk.shr.com.time.TimeWithDayAttr;
@RunWith(JMockit.class)	
public class OutingTimeOfDailyAttdTest {
	@Test
	public void getters() {
		val outTime = new OutingTimeOfDailyAttd(Arrays.asList(
				new OutingTimeSheet(new OutingFrameNo(1)
						, Optional.of(OutingTimeOfDailyAttdHelper.create_GoOut())
						, new AttendanceTime(new Integer(100))
						, new AttendanceTime(new Integer(200))
						, GoingOutReason.PRIVATE
						, Optional.of(OutingTimeOfDailyAttdHelper.create_ComeBack(new TimeWithDayAttr(120))))
				));
		NtsAssert.invokeGetters(outTime);
	}
	
	/**
	 * 外出の勤怠打刻の時刻　= empty
	 * 戻りの勤怠打刻の時刻　= empty
	 * 
	 */
	@Test
	public void getTimeZoneByGoOutReason_case_1() {	
		TimeWithDayAttr timeDay = null;
		val outTime = new OutingTimeOfDailyAttd(Arrays.asList(
				new OutingTimeSheet(new OutingFrameNo(1)
						, Optional.of(OutingTimeOfDailyAttdHelper.create_GoOut(timeDay))
						, new AttendanceTime(new Integer(100))
						, new AttendanceTime(new Integer(200))
						, GoingOutReason.PRIVATE
						, Optional.of(OutingTimeOfDailyAttdHelper.create_ComeBack(timeDay)))
				));
		assertThat(outTime.getTimeZoneByGoOutReason()).isEmpty();
	}
	
	/**
	 * 外出の勤怠打刻の時刻　= empty
	 * 勤怠打刻(実打刻付き)の打刻　= empty
	 * 
	 */
	@Test
	public void getTimeZoneByGoOutReason_case_3() {	
		TimeWithDayAttr goOutTime = null;
		Optional<WorkStamp> stamp = Optional.empty();
		val outTime = new OutingTimeOfDailyAttd(Arrays.asList(
				new OutingTimeSheet(new OutingFrameNo(1)
						, Optional.of(OutingTimeOfDailyAttdHelper.create_GoOut(goOutTime))
						, new AttendanceTime(new Integer(100))
						, new AttendanceTime(new Integer(200))
						, GoingOutReason.PRIVATE
						, Optional.of(OutingTimeOfDailyAttdHelper.create_ComeBack(stamp)))
				));
		assertThat(outTime.getTimeZoneByGoOutReason()).isEmpty();
	}
	
	/**
	 * 外出の勤怠打刻の時刻　not empty
	 * 戻りの勤怠打刻の時刻　 empty
	 */
	@Test
	public void getTimeZoneByGoOutReason_case_4() {	
		TimeWithDayAttr combackTime = null;
		val outTime = new OutingTimeOfDailyAttd(Arrays.asList(
				new OutingTimeSheet(new OutingFrameNo(1)
						, Optional.of(OutingTimeOfDailyAttdHelper.create_GoOut())
						, new AttendanceTime(new Integer(100))
						, new AttendanceTime(new Integer(200))
						, GoingOutReason.PRIVATE
						, Optional.of(OutingTimeOfDailyAttdHelper.create_ComeBack(combackTime)))
				));
		assertThat(outTime.getTimeZoneByGoOutReason()).isEmpty();
	}
	
	/**
	 * 外出1:　9:00, 戻り1: 10:00
	 * 外出2:　16:00, 戻り1: 17:00
	 */
	@Test
	public void getTimeZoneByGoOutReason_case_5() {	
		/** 外出を作る _1  **/
		val actStamp_go_out_1 = new WorkStamp(  new TimeWithDayAttr(15)
				                               , new TimeWithDayAttr(900)
				                               , new WorkLocationCD("001")
				                               , TimeChangeMeans.REAL_STAMP);
		val stamp_go_out_1 = new WorkStamp( new TimeWithDayAttr(15)
				                           , new TimeWithDayAttr(900)
				                           , new WorkLocationCD("001")
				                           , TimeChangeMeans.REAL_STAMP);

		val goOut_1 = new TimeActualStamp( actStamp_go_out_1, stamp_go_out_1, 1);
		
		/** 戻りを作る 1 **/
		val actStamp_come_back_1 = new WorkStamp(new TimeWithDayAttr(15)
				                               , new TimeWithDayAttr(1000)
				                               , new WorkLocationCD("001")
				                               , TimeChangeMeans.REAL_STAMP);
		val stamp_come_back_1 = new WorkStamp( new TimeWithDayAttr(15)
				                           , new TimeWithDayAttr(1000)
				                           , new WorkLocationCD("001")
				                           , TimeChangeMeans.REAL_STAMP);

		val comeBack_1 =  new TimeActualStamp(actStamp_come_back_1, stamp_come_back_1, 1);
		
		/** 外出時間帯 1 */
		val outingTime_1 = new OutingTimeSheet(new OutingFrameNo(1)
				, Optional.of(goOut_1)
				, new AttendanceTime(1600)
				, new AttendanceTime(1700)
				, GoingOutReason.PRIVATE
				, Optional.of(comeBack_1));
		
		/** 外出を作る _2 **/
		val actStamp_go_out_2 = new WorkStamp(  new TimeWithDayAttr(15)
                , new TimeWithDayAttr(1600)
                , new WorkLocationCD("001")
                , TimeChangeMeans.REAL_STAMP);
		
		val stamp_go_out_2 = new WorkStamp( new TimeWithDayAttr(15)
		            , new TimeWithDayAttr(1600)
		            , new WorkLocationCD("001")
		            , TimeChangeMeans.REAL_STAMP);
		
		val goOut_2 = new TimeActualStamp( actStamp_go_out_2, stamp_go_out_2, 1);
		
		/** 戻りを作る 2 **/
		val actStamp_come_back_2 = new WorkStamp(  new TimeWithDayAttr(15)
                , new TimeWithDayAttr(1700)
                , new WorkLocationCD("001")
                , TimeChangeMeans.REAL_STAMP);
		val stamp_come_back_2 = new WorkStamp( new TimeWithDayAttr(15)
		            , new TimeWithDayAttr(1700)
		            , new WorkLocationCD("001")
		            , TimeChangeMeans.REAL_STAMP);
		val comeBack_2 =  new TimeActualStamp(actStamp_come_back_2, stamp_come_back_2, 1);
		
		/** 外出時間帯 2 */
		val outingTime_2 = new OutingTimeSheet(new OutingFrameNo(1)
				, Optional.of(goOut_2)
				, new AttendanceTime(1600)
				, new AttendanceTime(1700)
				, GoingOutReason.PRIVATE
				, Optional.of(comeBack_2));
		
		val outTime = new OutingTimeOfDailyAttd(Arrays.asList(outingTime_1, outingTime_2));
		
		val excected = Arrays.asList(new TimeSpanForCalc(goOut_1.getStamp().get().getTimeDay().getTimeWithDay().get()
				                         , comeBack_1.getStamp().get().getTimeDay().getTimeWithDay().get())
				                   , new TimeSpanForCalc(goOut_2.getStamp().get().getTimeDay().getTimeWithDay().get()
                                         , comeBack_2.getStamp().get().getTimeDay().getTimeWithDay().get()));
		assertThat(outTime.getTimeZoneByGoOutReason()).containsExactlyInAnyOrderElementsOf(excected);
	}
	
	/**
	 * 外出の勤怠打刻(実打刻付き)の打刻　＝ empty
	 * 戻りの勤怠打刻の時刻　= empty
	 */
	@Test
	public void getTimeZoneByGoOutReason_case_7() {	
		Optional<WorkStamp> stamp = Optional.empty();
		TimeWithDayAttr combackTime = null;
		val outTime = new OutingTimeOfDailyAttd(Arrays.asList(
				new OutingTimeSheet(new OutingFrameNo(1)
						, Optional.of(OutingTimeOfDailyAttdHelper.create_GoOut(stamp))
						, new AttendanceTime(new Integer(100))
						, new AttendanceTime(new Integer(200))
						, GoingOutReason.PRIVATE
						, Optional.of(OutingTimeOfDailyAttdHelper.create_ComeBack(combackTime)))
				));
		assertThat(outTime.getTimeZoneByGoOutReason()).isEmpty();
	}
	
	/**
	 * 外出の勤怠打刻(実打刻付き)の打刻　＝ empty
	 * 戻りの勤怠打刻の時刻　not empty
	 */
	@Test
	public void getTimeZoneByGoOutReason_case_8() {	
		TimeWithDayAttr goOutTime = null;
		TimeWithDayAttr comeBackTime = new TimeWithDayAttr(1100);
		Optional<WorkStamp> stamp = Optional.of(new WorkStamp( new TimeWithDayAttr(60)
		        , new TimeWithDayAttr(3600)
		        , new WorkLocationCD("004")
		        , TimeChangeMeans.REAL_STAMP
		        , EngravingMethod.DIRECT_BOUNCE_BUTTON));
		val outTime = new OutingTimeOfDailyAttd(Arrays.asList(
				new OutingTimeSheet(new OutingFrameNo(1)
						, Optional.of(OutingTimeOfDailyAttdHelper.create_GoOut(goOutTime))
						, new AttendanceTime(new Integer(100))
						, new AttendanceTime(new Integer(200))
						, GoingOutReason.PRIVATE
						, Optional.of(OutingTimeOfDailyAttdHelper.create_ComeBack(comeBackTime, stamp)))
				));
		assertThat(outTime.getTimeZoneByGoOutReason()).isEmpty();
	}
	
	/**
	 * 外出の勤怠打刻(実打刻付き)の打刻　＝ empty
	 * 戻りの勤怠打刻(実打刻付き)の打刻　＝ empty
	 */
	@Test
	public void getTimeZoneByGoOutReason_case_9() {	
		Optional<WorkStamp> stampGoOut = Optional.empty();
		Optional<WorkStamp> stampComeBack = Optional.empty();
		val outTime = new OutingTimeOfDailyAttd(Arrays.asList(
				new OutingTimeSheet(new OutingFrameNo(1)
						, Optional.of(OutingTimeOfDailyAttdHelper.create_GoOut(stampGoOut))
						, new AttendanceTime(new Integer(100))
						, new AttendanceTime(new Integer(200))
						, GoingOutReason.PRIVATE
						, Optional.of(OutingTimeOfDailyAttdHelper.create_ComeBack(stampComeBack)))
				));
		assertThat(outTime.getTimeZoneByGoOutReason()).isEmpty();
	}
	
	
	
}
