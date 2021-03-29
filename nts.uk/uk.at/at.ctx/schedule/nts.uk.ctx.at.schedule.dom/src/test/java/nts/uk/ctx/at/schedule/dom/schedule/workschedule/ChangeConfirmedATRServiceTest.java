package nts.uk.ctx.at.schedule.dom.schedule.workschedule;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.task.tran.AtomTask;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.schedule.task.taskschedule.TaskSchedule;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.affiliationinfor.AffiliationInforOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.OutingTimeOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.breaking.BreakTimeOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.editstate.EditStateOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.shortworktime.ShortTimeOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.WorkInfoOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worktime.AttendanceTimeOfDailyAttendance;
/**
 * 確定区分を変更する UTコード
 * @author lan_lt
 *
 */
@RunWith(JMockit.class)
public class ChangeConfirmedATRServiceTest {
	@Injectable
	private ChangeConfirmedATRService.Require require;
	
	/**
	 * ケース:	勤務予定がないです
	 * 期待：		Msg_1541
	 */
	@Test
	public void change_workSchedule_empty() {
		String sid = "sid";
		GeneralDate ymd = GeneralDate.ymd(2021,  03, 29);
		
		new Expectations() {
			{
				require.getWorkSchedule(sid, ymd);
			}
		};
		
		NtsAssert.businessException("Msg_1541", () ->{
			
			AtomTask persit = ChangeConfirmedATRService.change(require, sid, ymd, true);
			persit.run();
			
		});
	}
	
	/**
	 * ケース:	勤務予定がある、確定か = true
	 * 期待：		勤務予定の確定状態 = 確定済み
	 */
	@Test
	public void change_workSchedule_confirmed() {
		String sid = "sid";
		GeneralDate ymd = GeneralDate.ymd(2021, 03, 29);
		val workSchedule = Helper.create_WorkSchedule(sid, ymd, ConfirmedATR.UNSETTLED );
		
		new Expectations() {
			{
				require.getWorkSchedule(sid, ymd);
				result = Optional.of(workSchedule);
			}
		};
		
		NtsAssert.atomTask(
				() -> ChangeConfirmedATRService.change(require, sid, ymd, true));
		
		assertThat(workSchedule.getConfirmedATR()).isEqualTo(ConfirmedATR.CONFIRMED);
	}
	
	/**
	 * ケース:	勤務予定がある、確定か = false
	 * 期待：		勤務予定の確定状態 = 未確定
	 */
	@Test
	public void change_workSchedule_not_confirmed() {
		val sid = "sid";
		val ymd = GeneralDate.ymd(2021, 03, 29);
		val workSchedule = Helper.create_WorkSchedule(sid, ymd, ConfirmedATR.CONFIRMED );
		
		new Expectations() {
			{
				require.getWorkSchedule(sid, ymd);
				result = Optional.of(workSchedule);
			}
		};
		
		NtsAssert.atomTask(
				() -> ChangeConfirmedATRService.change(require, sid, ymd, false));
		
		assertThat(workSchedule.getConfirmedATR()).isEqualTo(ConfirmedATR.UNSETTLED);
	}
	
	
	public static class Helper{
		@Injectable
		private static WorkInfoOfDailyAttendance workInfo;
		
		@Injectable
		private static AffiliationInforOfDailyAttd affInfo;
		
		@Injectable
		private static BreakTimeOfDailyAttd lstBreakTime;
		
		@Injectable
		private static List<EditStateOfDailyAttd> lstEditState;
		
		@Injectable
		private static TaskSchedule taskSchedule;
		
		@Injectable
		private static Optional<TimeLeavingOfDailyAttd> optTimeLeaving;
		
		@Injectable
		private static Optional<AttendanceTimeOfDailyAttendance> optAttendanceTime;
		
		@Injectable
		private static Optional<ShortTimeOfDailyAttd> optSortTimeWork;
		
		@Injectable
		private static Optional<OutingTimeOfDailyAttd> outingTime;
		
		public static WorkSchedule create_WorkSchedule(String sid, GeneralDate ymd, ConfirmedATR confirmedATR) {
			return new WorkSchedule(sid, ymd, confirmedATR
					,	workInfo
					,	affInfo
					,	lstBreakTime
					,	lstEditState
					,	taskSchedule
					,	optTimeLeaving
					,	optAttendanceTime
					,	optSortTimeWork
					,	outingTime);
		}
		
	}
	
}
