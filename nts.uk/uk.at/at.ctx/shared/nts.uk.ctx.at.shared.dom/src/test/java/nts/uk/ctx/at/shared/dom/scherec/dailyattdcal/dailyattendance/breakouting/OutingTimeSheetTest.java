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
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.OvertimeDeclaration;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeActualStamp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.TimeChangeMeans;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkLocationCD;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkStamp;
import nts.uk.ctx.at.shared.dom.worktime.common.TimeZone;
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
		val outingTime = Helper.createOutingTimeSheet();
		NtsAssert.invokeGetters(outingTime);
	}
	
	/**
	 * 外出 = empty
	 * 時間帯を返す = empty
	 * 
	 */
	@Test
	public void getTimeZone_goOut_empty() {	
		/** 戻りを作る */
		//実打刻
		val actStamp_come_back = new WorkStamp(  new TimeWithDayAttr(15)
				                               , new TimeWithDayAttr(1700)
				                               , new WorkLocationCD("001")
				                               , TimeChangeMeans.REAL_STAMP);
		//打刻
		val stamp_come_back = new WorkStamp( new TimeWithDayAttr(15)
				                           , new TimeWithDayAttr(1700)
				                           , new WorkLocationCD("001")
				                           , TimeChangeMeans.REAL_STAMP);

		val comeBack =  new TimeActualStamp(actStamp_come_back, stamp_come_back, 1
                , new OvertimeDeclaration(new AttendanceTime(100), new AttendanceTime(0))
                , null);
		
		val outingTime = new OutingTimeSheet(new OutingFrameNo(1)
				, Optional.empty() //外出 = empty
				, new AttendanceTime(1600)
				, new AttendanceTime(1700)
				, GoingOutReason.PRIVATE
				, Optional.of(comeBack));
		val actual = outingTime.getTimeZone();
		
		assertThat(actual).isEmpty();
		
	}
	
	/**
	 * 外出の打刻　= empty
	 * 時間帯を返す = empty
	 * 
	 */
	@Test
	public void getTimeZone_goOut_stamp_empty() {
		val vacations  = new TimeZone(new TimeWithDayAttr(1200), new TimeWithDayAttr(1300));
		//実打刻
		val actStamp_go_out = new WorkStamp(  new TimeWithDayAttr(15)
				                               , new TimeWithDayAttr(1600)
				                               , new WorkLocationCD("001")
				                               , TimeChangeMeans.REAL_STAMP);
		//打刻
		WorkStamp stamp_go_out = null;

		val goOut = new TimeActualStamp( actStamp_go_out, stamp_go_out, 1
                , new OvertimeDeclaration(new AttendanceTime(100), new AttendanceTime(0))
                , vacations);
		
		/** 戻りを作る */
		//実打刻
		val actStamp_come_back = new WorkStamp(  new TimeWithDayAttr(15)
				                               , new TimeWithDayAttr(1700)
				                               , new WorkLocationCD("001")
				                               , TimeChangeMeans.REAL_STAMP);
		//打刻
		val stamp_come_back = new WorkStamp( new TimeWithDayAttr(15)
				                           , new TimeWithDayAttr(1700)
				                           , new WorkLocationCD("001")
				                           , TimeChangeMeans.REAL_STAMP);

		val comeBack =  new TimeActualStamp(actStamp_come_back, stamp_come_back, 1
                , new OvertimeDeclaration(new AttendanceTime(100), new AttendanceTime(0))
                , null);
		
		val outingTime = new OutingTimeSheet(new OutingFrameNo(1)
				, Optional.of(goOut)
				, new AttendanceTime(1600)
				, new AttendanceTime(1700)
				, GoingOutReason.PRIVATE
				, Optional.of(comeBack));
		val actual = outingTime.getTimeZone();
		
		assertThat(actual).isEmpty();
		
	}
	
	/**
	 * 戻り = empty
	 * 時間帯を返す = empty
	 * 
	 */
	@Test
	public void getTimeZone_comeback_empty() {	
		/** 外出を作る */
		//実打刻
		val actStamp_go_out = new WorkStamp(  new TimeWithDayAttr(15)
				                               , new TimeWithDayAttr(1700)
				                               , new WorkLocationCD("001")
				                               , TimeChangeMeans.REAL_STAMP);
		//打刻
		val stamp_go_out = new WorkStamp( new TimeWithDayAttr(15)
				                           , new TimeWithDayAttr(1700)
				                           , new WorkLocationCD("001")
				                           , TimeChangeMeans.REAL_STAMP);

		val goOut =  new TimeActualStamp(actStamp_go_out, stamp_go_out, 1
                , new OvertimeDeclaration(new AttendanceTime(100), new AttendanceTime(0))
                , null);
		
		val outingTime = new OutingTimeSheet(new OutingFrameNo(1)
				, Optional.of(goOut) 
				, new AttendanceTime(1600)
				, new AttendanceTime(1700)
				, GoingOutReason.PRIVATE
				, Optional.empty());     //戻り  = empty
		val actual = outingTime.getTimeZone();
		
		assertThat(actual).isEmpty();
		
	}
	
	/**
	 * 戻りの打刻　= empty
	 * 時間帯を返す = empty
	 * 
	 */
	@Test
	public void getTimeZone_comeback_stamp_empty() {	
		/**　外出を作る　*/
		//実打刻
		val actStamp_go_out = new WorkStamp(  new TimeWithDayAttr(15)
				                               , new TimeWithDayAttr(1600)
				                               , new WorkLocationCD("001")
				                               , TimeChangeMeans.REAL_STAMP);
		//打刻
		val stamp_go_out = new WorkStamp( new TimeWithDayAttr(15)
				                           , new TimeWithDayAttr(1600)
				                           , new WorkLocationCD("001")
				                           , TimeChangeMeans.REAL_STAMP);

		val goOut = new TimeActualStamp( actStamp_go_out, stamp_go_out, 1
                , new OvertimeDeclaration(new AttendanceTime(100), new AttendanceTime(0))
                , null);
		
		/** 戻りを作る */
		//実打刻 
		val actStamp_come_back = new WorkStamp(  new TimeWithDayAttr(15)
				                               , new TimeWithDayAttr(1700)
				                               , new WorkLocationCD("001")
				                               , TimeChangeMeans.REAL_STAMP);
		//打刻 = null
		WorkStamp stamp_come_back = null;

		val comeBack =  new TimeActualStamp(actStamp_come_back, stamp_come_back, 1
                , new OvertimeDeclaration(new AttendanceTime(100), new AttendanceTime(0))
                , null);
		
		val outingTime = new OutingTimeSheet(new OutingFrameNo(1)
				, Optional.of(goOut)
				, new AttendanceTime(1600)
				, new AttendanceTime(1700)
				, GoingOutReason.PRIVATE
				, Optional.of(comeBack));
		val actual = outingTime.getTimeZone();
		
		assertThat(actual).isEmpty();
		
	}
	
	/**
	 * 時間帯を返す not empty
	 * 
	 */
	@Test
	public void getTimeZone_not_empty() {	
		TimeZone vacations  = null;
		/** 外出を作る */
		//実打刻
		val actStamp_go_out = new WorkStamp(  new TimeWithDayAttr(15)
				                               , new TimeWithDayAttr(1600)
				                               , new WorkLocationCD("001")
				                               , TimeChangeMeans.REAL_STAMP);
		//打刻
		val stamp_go_out = new WorkStamp( new TimeWithDayAttr(15)
				                           , new TimeWithDayAttr(1600)
				                           , new WorkLocationCD("001")
				                           , TimeChangeMeans.REAL_STAMP);

		val goOut = new TimeActualStamp( actStamp_go_out, stamp_go_out, 1
                , new OvertimeDeclaration(new AttendanceTime(100), new AttendanceTime(0))
                , vacations);
		
		
		/** 戻りを作る */
		//実打刻
		val actStamp_come_back = new WorkStamp(  new TimeWithDayAttr(15)
				                               , new TimeWithDayAttr(1700)
				                               , new WorkLocationCD("001")
				                               , TimeChangeMeans.REAL_STAMP);
		//打刻
		val stamp_come_back = new WorkStamp( new TimeWithDayAttr(15)
				                           , new TimeWithDayAttr(1700)
				                           , new WorkLocationCD("001")
				                           , TimeChangeMeans.REAL_STAMP);

		val comeBack =  new TimeActualStamp(actStamp_come_back, stamp_come_back, 1
                , new OvertimeDeclaration(new AttendanceTime(100), new AttendanceTime(0))
                , vacations);
		
		val outingTime = new OutingTimeSheet(new OutingFrameNo(1)
				, Optional.of(goOut)
				, new AttendanceTime(1600)
				, new AttendanceTime(1700)
				, GoingOutReason.PRIVATE
				, Optional.of(comeBack));
		val actual = outingTime.getTimeZone();
		
		assertThat(actual).isNotEmpty();
		assertThat(actual.get().getStart()).isEqualTo(goOut.getStamp().get().getTimeDay().getTimeWithDay().get());
		assertThat(actual.get().getEnd()).isEqualTo(comeBack.getStamp().get().getTimeDay().getTimeWithDay().get());
	}
	
	
	
	static class Helper{
	    /**
	     * 外出時間帯を作る
	     * @return
	     */
		public static OutingTimeSheet createOutingTimeSheet() {
			return new OutingTimeSheet(new OutingFrameNo(1)
					, Optional.of(createGoOut())
					, new AttendanceTime(1600)
					, new AttendanceTime(1700)
					, GoingOutReason.PRIVATE
					, Optional.of(createComeBack()));
		}
		
		/**
		 * 戻りを作る
		 * @return
		 */
		public static TimeActualStamp createComeBack() {
			val vacations  = new TimeZone(new TimeWithDayAttr(1200), new TimeWithDayAttr(1300));
			//実打刻
			val actStamp_come_back = new WorkStamp(  new TimeWithDayAttr(15)
					                               , new TimeWithDayAttr(1700)
					                               , new WorkLocationCD("001")
					                               , TimeChangeMeans.REAL_STAMP);
			//打刻
			val stamp_come_back = new WorkStamp( new TimeWithDayAttr(15)
					                           , new TimeWithDayAttr(1700)
					                           , new WorkLocationCD("001")
					                           , TimeChangeMeans.REAL_STAMP);

			return  new TimeActualStamp(actStamp_come_back, stamp_come_back, 1
                    , new OvertimeDeclaration(new AttendanceTime(100), new AttendanceTime(0))
                    , vacations);
		}
		
		/**
		 * 外出を作る
		 * @return
		 */
		public static TimeActualStamp createGoOut() {
			val vacations  = new TimeZone(new TimeWithDayAttr(1200), new TimeWithDayAttr(1300));
			//実打刻
			val actStamp_go_out = new WorkStamp(  new TimeWithDayAttr(15)
					                               , new TimeWithDayAttr(1600)
					                               , new WorkLocationCD("001")
					                               , TimeChangeMeans.REAL_STAMP);
			//打刻
			val stamp_go_out = new WorkStamp( new TimeWithDayAttr(15)
					                           , new TimeWithDayAttr(1600)
					                           , new WorkLocationCD("001")
					                           , TimeChangeMeans.REAL_STAMP);

			return  new TimeActualStamp( actStamp_go_out, stamp_go_out, 1
                    , new OvertimeDeclaration(new AttendanceTime(100), new AttendanceTime(0))
                    , vacations);
		}
	}
}
