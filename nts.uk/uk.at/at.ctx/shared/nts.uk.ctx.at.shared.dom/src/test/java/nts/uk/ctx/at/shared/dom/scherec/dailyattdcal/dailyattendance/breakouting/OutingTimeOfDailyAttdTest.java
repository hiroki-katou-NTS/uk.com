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
						, Optional.of(OutingTimeOfDailyAttdHelper.create_ComeBack()))
				));
		NtsAssert.invokeGetters(outTime);
	}
	
	/**
	 * workDay of goOut not empty
	 * timeDay of goOut empty
	 * workDay of comeback not empty
	 * timeDay of comeback  empty
	 * 
	 */
	@Test
	public void getTimeZoneByGoOutReason_case_1() {	
		val outTime = new OutingTimeOfDailyAttd(Arrays.asList(
				new OutingTimeSheet(new OutingFrameNo(1)
						, Optional.of(OutingTimeOfDailyAttdHelper.create_GoOut_Time_Day_Empty())
						, new AttendanceTime(new Integer(100))
						, new AttendanceTime(new Integer(200))
						, GoingOutReason.PRIVATE
						, Optional.of(OutingTimeOfDailyAttdHelper.create_ComeBack_Time_Day_Empty()))
				));
		assertThat(outTime.getTimeZoneByGoOutReason()).isEmpty();
	}
	
	/**
	 * workDay of goOut not empty
	 * timeDay of goOut empty
	 * workDay of comeback not empty
	 * timeDay of comeback not empty
	 * 
	 */
	@Test
	public void getTimeZoneByGoOutReason_case_2() {	
		val outTime = new OutingTimeOfDailyAttd(Arrays.asList(
				new OutingTimeSheet(new OutingFrameNo(1)
						, Optional.of(OutingTimeOfDailyAttdHelper.create_GoOut_Time_Day_Empty())
						, new AttendanceTime(new Integer(100))
						, new AttendanceTime(new Integer(200))
						, GoingOutReason.PRIVATE
						, Optional.of(OutingTimeOfDailyAttdHelper.create_ComeBack()))
				));
		assertThat(outTime.getTimeZoneByGoOutReason()).isEmpty();
	}
	
	/**
	 * workDay of goOut not empty
	 * timeDay of goOut empty
	 * workDay of comeback empty
	 * 
	 */
	@Test
	public void getTimeZoneByGoOutReason_case_3() {	
		val outTime = new OutingTimeOfDailyAttd(Arrays.asList(
				new OutingTimeSheet(new OutingFrameNo(1)
						, Optional.of(OutingTimeOfDailyAttdHelper.create_GoOut_Time_Day_Empty())
						, new AttendanceTime(new Integer(100))
						, new AttendanceTime(new Integer(200))
						, GoingOutReason.PRIVATE
						, Optional.of(OutingTimeOfDailyAttdHelper.create_ComeBack_Empty()))
				));
		assertThat(outTime.getTimeZoneByGoOutReason()).isEmpty();
	}
	
	/**
	 * workDay of goOut not empty
	 * timeDay of goOut not empty
	 * workDay of comeback not empty
	 * timeDay of comeback empty
	 */
	@Test
	public void getTimeZoneByGoOutReason_case_4() {	
		val outTime = new OutingTimeOfDailyAttd(Arrays.asList(
				new OutingTimeSheet(new OutingFrameNo(1)
						, Optional.of(OutingTimeOfDailyAttdHelper.create_GoOut())
						, new AttendanceTime(new Integer(100))
						, new AttendanceTime(new Integer(200))
						, GoingOutReason.PRIVATE
						, Optional.of(OutingTimeOfDailyAttdHelper.create_ComeBack_Time_Day_Empty()))
				));
		assertThat(outTime.getTimeZoneByGoOutReason()).isEmpty();
	}
	
	/**
	 * workDay of goOut not empty
	 * timeDay of goOut not empty
	 * workDay of comeback not empty
	 * timeDay of comeback not empty
	 */
	@Test
	public void getTimeZoneByGoOutReason_case_5() {	
		/** 外出を作る _1 */
		val actStamp_go_out_1 = new WorkStamp(  new TimeWithDayAttr(15)
				                               , new TimeWithDayAttr(1600)
				                               , new WorkLocationCD("001")
				                               , TimeChangeMeans.REAL_STAMP);
		val stamp_go_out_1 = new WorkStamp( new TimeWithDayAttr(15)
				                           , new TimeWithDayAttr(1600)
				                           , new WorkLocationCD("001")
				                           , TimeChangeMeans.REAL_STAMP);

		val goOut_1 = new TimeActualStamp( actStamp_go_out_1, stamp_go_out_1, 1);
		
		/** 戻りを作る 1 */
		val actStamp_come_back_1 = new WorkStamp(new TimeWithDayAttr(15)
				                               , new TimeWithDayAttr(1700)
				                               , new WorkLocationCD("001")
				                               , TimeChangeMeans.REAL_STAMP);
		val stamp_come_back_1 = new WorkStamp( new TimeWithDayAttr(15)
				                           , new TimeWithDayAttr(1700)
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
		
		/** 外出を作る _2 */
		val actStamp_go_out_2 = new WorkStamp(  new TimeWithDayAttr(15)
                , new TimeWithDayAttr(1600)
                , new WorkLocationCD("001")
                , TimeChangeMeans.REAL_STAMP);
		
		val stamp_go_out_2 = new WorkStamp( new TimeWithDayAttr(15)
		            , new TimeWithDayAttr(1600)
		            , new WorkLocationCD("001")
		            , TimeChangeMeans.REAL_STAMP);
		
		val goOut_2 = new TimeActualStamp( actStamp_go_out_2, stamp_go_out_2, 1);
		
		/** 戻りを作る 2 */
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
	 * workDay of goOut not empty
	 * timeDay of goOut not empty
	 * workDay of comeback  empty
	 * timeDay of comeback  empty
	 */
	@Test
	public void getTimeZoneByGoOutReason_case_6() {	
		val outTime = new OutingTimeOfDailyAttd(Arrays.asList(
				new OutingTimeSheet(new OutingFrameNo(1)
						, Optional.of(OutingTimeOfDailyAttdHelper.create_GoOut())
						, new AttendanceTime(new Integer(100))
						, new AttendanceTime(new Integer(200))
						, GoingOutReason.PRIVATE
						, Optional.of(OutingTimeOfDailyAttdHelper.create_ComeBack_Empty()))
				));
		assertThat(outTime.getTimeZoneByGoOutReason()).isEmpty();
	}
	
	/**
	 * workDay of goOut  empty
	 * timeDay of goOut  empty
	 * workDay of comeback  not empty
	 * timeDay of comeback  empty
	 */
	@Test
	public void getTimeZoneByGoOutReason_case_7() {	
		val outTime = new OutingTimeOfDailyAttd(Arrays.asList(
				new OutingTimeSheet(new OutingFrameNo(1)
						, Optional.of(OutingTimeOfDailyAttdHelper.create_GoOut_Empty())
						, new AttendanceTime(new Integer(100))
						, new AttendanceTime(new Integer(200))
						, GoingOutReason.PRIVATE
						, Optional.of(OutingTimeOfDailyAttdHelper.create_ComeBack_Time_Day_Empty()))
				));
		assertThat(outTime.getTimeZoneByGoOutReason()).isEmpty();
	}
	
	/**
	 * workDay of goOut  empty
	 * timeDay of goOut  empty
	 * workDay of comeback  not empty
	 * timeDay of comeback  not empty
	 */
	@Test
	public void getTimeZoneByGoOutReason_case_8() {	
		val outTime = new OutingTimeOfDailyAttd(Arrays.asList(
				new OutingTimeSheet(new OutingFrameNo(1)
						, Optional.of(OutingTimeOfDailyAttdHelper.create_GoOut_Empty())
						, new AttendanceTime(new Integer(100))
						, new AttendanceTime(new Integer(200))
						, GoingOutReason.PRIVATE
						, Optional.of(OutingTimeOfDailyAttdHelper.create_ComeBack()))
				));
		assertThat(outTime.getTimeZoneByGoOutReason()).isEmpty();
	}
	
	/**
	 * workDay of goOut  empty
	 * timeDay of goOut  empty
	 * workDay of comeback   empty
	 * timeDay of comeback   empty
	 */
	@Test
	public void getTimeZoneByGoOutReason_case_9() {	
		val outTime = new OutingTimeOfDailyAttd(Arrays.asList(
				new OutingTimeSheet(new OutingFrameNo(1)
						, Optional.of(OutingTimeOfDailyAttdHelper.create_GoOut_Empty())
						, new AttendanceTime(new Integer(100))
						, new AttendanceTime(new Integer(200))
						, GoingOutReason.PRIVATE
						, Optional.of(OutingTimeOfDailyAttdHelper.create_ComeBack_Empty()))
				));
		assertThat(outTime.getTimeZoneByGoOutReason()).isEmpty();
	}
	
	
	
}
