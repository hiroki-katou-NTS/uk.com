package nts.uk.ctx.at.schedule.dom.schedule.workschedule;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
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
		val workSchedule = WorkScheduleHelper.createWithConfirmAtr(ConfirmedATR.UNSETTLED );
		
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
		val workSchedule = WorkScheduleHelper.createWithConfirmAtr(ConfirmedATR.CONFIRMED );
		
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
	
}
