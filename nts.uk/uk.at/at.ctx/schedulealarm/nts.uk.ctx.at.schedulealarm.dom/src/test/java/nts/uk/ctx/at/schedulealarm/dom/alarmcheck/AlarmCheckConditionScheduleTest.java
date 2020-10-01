package nts.uk.ctx.at.schedulealarm.dom.alarmcheck;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;

@RunWith(JMockit.class)
public class AlarmCheckConditionScheduleTest {
	@Test
	public void getters() {
		val alarmCheckCond =  new AlarmCheckConditionSchedule(new AlarmCheckConditionScheduleCode("alarmCheckCode"),
				"alarmCheckName",
				true,
				new ArrayList<>());

		NtsAssert.invokeGetters(alarmCheckCond);

	}

	@Test
	public void create_alarmCheckConditionSchedule_success() {

		val subCondLst = createSubCondition(2);
		val alarmCond = new AlarmCheckConditionSchedule(new AlarmCheckConditionScheduleCode("01"),
				"alarmCheck1", true, subCondLst);

		assertThat(alarmCond.getCode().v()).isEqualTo("01");
		assertThat(alarmCond.getConditionName()).isEqualTo("alarmCheck1");
		assertThat(alarmCond.isMedicalOpt()).isTrue();
		assertThat(alarmCond.getSubConditions()).containsExactlyElementsOf(subCondLst);
	}
	/**
	 * メッセージを変更する success
	 */
	@Test
	public void update_success() {
		val subCondLst = createSubCondition(5);
		val updSubCd = new SubCode("1");															
		val updMessage = new AlarmCheckMessage( "new message" );	
		
		val instance  = new AlarmCheckConditionSchedule(new AlarmCheckConditionScheduleCode("01"), "name", true, subCondLst);
		instance .updateMessage(updSubCd, updMessage);
		
		assertThat(instance.getSubConditions()).filteredOn(e -> e.getSubCode().equals(updSubCd))				
				.extracting( e -> e.getMessage().getMessage() ).containsOnly( updMessage );		
		assertThat(instance.getSubConditions()).filteredOn(e -> !e.getSubCode().equals(updSubCd))				
				.containsExactlyInAnyOrderElementsOf(
						subCondLst.stream().filter(c -> c.getSubCode().equals(updSubCd)).collect(Collectors.toList())
				);	

	}
	
	public static List<SubCondition> createSubCondition(int size) {
		List<SubCondition> result = new ArrayList<>();
		for (int i = 0; i < size; i++) {
			val subCond = new SubCondition(new SubCode(String.valueOf(i)),
					new AlarmCheckMsgContent(new AlarmCheckMessage(String.valueOf(i)), new AlarmCheckMessage(String.valueOf(i))),
					"explanation" + String.valueOf(i));

			result.add(subCond);

		}
		
		return result;
	}
}
