package nts.uk.ctx.at.schedulealarm.dom.alarmcheck;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;

import org.assertj.core.groups.Tuple;
import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;

@RunWith(JMockit.class)
public class AlarmCheckConditionScheduleTest {
	@Test
	public void getters() {
		val alarmCheckCond =  new AlarmCheckConditionSchedule(new AlarmCheckConditionCode("alarmCheckCode"),
				"alarmCheckName",
				true,
				new ArrayList<>());

		NtsAssert.invokeGetters(alarmCheckCond);

	}

	@Test
	public void create_alarmCheckConditionSchedule_success() {

		val subCondLst = AlarmCheckConditionScheduleHelper.createSubCondition(2);
		val alarmCond = new AlarmCheckConditionSchedule(new AlarmCheckConditionCode("01"),
				"alarmCheck1", true, subCondLst);

		assertThat(alarmCond.getCode().v()).isEqualTo("01");
		assertThat(alarmCond.getConditionName()).isEqualTo("alarmCheck1");
		assertThat(alarmCond.isMedicalOpt()).isTrue();
		assertThat(alarmCond.getSubConditionLst())
				.extracting(d -> d.getSubCode().v(),
							d -> d.getMessage().getDefaultMsg().v(),
							d -> d.getMessage().getMessage().v(), 
							d -> d.getExplanation())
				.containsExactly(
						Tuple.tuple(subCondLst.get(0).getSubCode().v(),
									subCondLst.get(0).getMessage().getDefaultMsg().v(),
									subCondLst.get(0).getMessage().getMessage().v(), 
									subCondLst.get(0).getExplanation()),
						Tuple.tuple(subCondLst.get(1).getSubCode().v(),
									subCondLst.get(1).getMessage().getDefaultMsg().v(),
									subCondLst.get(1).getMessage().getMessage().v(),
									subCondLst.get(1).getExplanation()));
	}
	/**
	 * メッセージを変更する success
	 */
	@Test
	public void update_success() {

		val subCondLst = AlarmCheckConditionScheduleHelper.createSubCondition(5);
		val alarmCond = new AlarmCheckConditionSchedule(new AlarmCheckConditionCode("01"), "alarmCheck1", true, subCondLst);
		alarmCond.updateMessage(new SubCode("1"), new AlarmCheckMessage("updated"));
		
		assertThat(alarmCond.getSubConditionLst())
				.extracting(d -> d.getSubCode().v(), d -> d.getMessage().getMessage().v())
				.containsExactly(Tuple.tuple("0", "0"),
								 Tuple.tuple("1", "updated"), 
								 Tuple.tuple("2", "2"),
						         Tuple.tuple("3", "3"), 
						         Tuple.tuple("4", "4"));
	}
}
