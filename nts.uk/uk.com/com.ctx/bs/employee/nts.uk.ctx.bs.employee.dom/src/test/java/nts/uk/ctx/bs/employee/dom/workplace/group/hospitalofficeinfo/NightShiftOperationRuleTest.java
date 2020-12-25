package nts.uk.ctx.bs.employee.dom.workplace.group.hospitalofficeinfo;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.clock.ClockHourMinute;
import nts.arc.time.clock.ClockHourMinuteSpan;
import nts.uk.shr.com.enumcommon.NotUseAtr;

@RunWith(JMockit.class)
public class NightShiftOperationRuleTest {
	
	/**
	 * input: 夜勤時間帯は 22時～翌5時 を含まない16時間　
	 * output:　Msg_2090
	 */
	@Test
	public void createByNightShiftUse_throw_Msg_2090() {
		val shiftTime = new ClockHourMinuteSpan(new ClockHourMinute(1330), new ClockHourMinute(1740));
		NtsAssert.businessException("Msg_2090", () ->{
			NightShiftOperationRule.createByNightShiftUse(shiftTime);
		});
	}

	/**
	 * input: 夜勤時間帯は 22時～翌5時 を含む16時間　
	 * output:　create success
	 */
	@Test
	public void createByNightShiftUse_sucess() {
		val shiftTime = new ClockHourMinuteSpan(new ClockHourMinute(1200), new ClockHourMinute(1800));
		val nightShiftOpeRule = NightShiftOperationRule.createByNightShiftUse(shiftTime);
		
		assertThat(nightShiftOpeRule.getNightShiftOperationAtr()).isEqualTo(NotUseAtr.USE);
		assertThat(nightShiftOpeRule.getShiftTime().get().start().v()).isEqualTo(shiftTime.start().v());
		assertThat(nightShiftOpeRule.getShiftTime().get().start().v()).isEqualTo(shiftTime.end().v());
		
	}
	
	@Test
	public void createByNightShiftNotUse_sucess() {
		val nightShiftOpeRule = NightShiftOperationRule.createByNightShiftNotUse();
		
		assertThat(nightShiftOpeRule.getNightShiftOperationAtr()).isEqualTo(NotUseAtr.NOT_USE);
		assertThat(nightShiftOpeRule.getShiftTime()).isEmpty();		
	}
}
