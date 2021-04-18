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
 * 確定状態を変更する UTコード
 * @author lan_lt
 *
 */
@RunWith(JMockit.class)
public class ChangeConfirmedServiceTest {
	@Injectable
	private ChangeConfirmedService.Require require;
	
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
		
		NtsAssert.businessException("Msg_1541", () -> ChangeConfirmedService.change(require, sid, ymd, true));
	}
	
	/**
	 * ケース:	勤務予定がある、確定か = true
	 * 期待：		勤務予定の確定状態 = 確定済み
	 */
	@Test
	public void change_workSchedule_confirmed() {
		val workSchedule = Helper.create_WorkSchedule(ConfirmedATR.UNSETTLED );
		
		new Expectations() {
			{
				require.getWorkSchedule((String) any, (GeneralDate) any);
				result = Optional.of(workSchedule);
			}
		};
		
		NtsAssert.atomTask(
				() -> ChangeConfirmedService.change(require,  "sid", GeneralDate.ymd(2021, 03, 29), true));
		
		assertThat(workSchedule.getConfirmedATR()).isEqualTo(ConfirmedATR.CONFIRMED);
	}
	
	/**
	 * ケース:	勤務予定がある、確定か = false
	 * 期待：		勤務予定の確定状態 = 未確定
	 */
	@Test
	public void change_workSchedule_not_confirmed() {
		val workSchedule = Helper.create_WorkSchedule(ConfirmedATR.CONFIRMED );
		
		new Expectations() {
			{
				require.getWorkSchedule((String) any, (GeneralDate) any);
				result = Optional.of(workSchedule);
			}
		};
		
		NtsAssert.atomTask(
				() -> ChangeConfirmedService.change(require, "sid", GeneralDate.ymd(2021, 03, 29), false));
		
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
		
		/**
		 * 勤務予定を作る
		 * @param confirmedATR 確定区分
		 * @return
		 */
		public static WorkSchedule create_WorkSchedule(ConfirmedATR confirmedATR) {
			return new WorkSchedule("sid", GeneralDate.ymd(2021, 03, 29), confirmedATR
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
