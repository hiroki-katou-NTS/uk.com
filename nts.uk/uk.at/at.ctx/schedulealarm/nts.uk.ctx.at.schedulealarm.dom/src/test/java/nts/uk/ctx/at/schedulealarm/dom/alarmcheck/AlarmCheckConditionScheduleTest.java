package nts.uk.ctx.at.schedulealarm.dom.alarmcheck;

import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
		val alarmCheckCond =  AlarmCheckConditionSchedule.create(
				new AlarmCheckConditionScheduleCode("alarmCheckCode"), "alarmCheckName", true, Collections.emptyList()
			);
		NtsAssert.invokeGetters(alarmCheckCond);
	}


	/**
	 * [private] サブ条件をソートする
	 */
	@Test
	public void testSortSubConditions() {

		// ソート対象のサブ条件
		val subConds = Arrays.asList(
						new SubCondition( new SubCode("03"), Helper.createAlmChkMsgCnt("規定C", "任意C"), "説明C" )
					,	new SubCondition( new SubCode("01"), Helper.createAlmChkMsgCnt("規定A", "任意A"), "説明A" )
					,	new SubCondition( new SubCode("05"), Helper.createAlmChkMsgCnt("規定E", "任意E"), "説明E" )
					,	new SubCondition( new SubCode("11"), Helper.createAlmChkMsgCnt("規定K", "任意K"), "説明K" )
				);

		// Execute
		NtsAssert.Invoke.privateMethod(
					AlarmCheckConditionSchedule.create(new AlarmCheckConditionScheduleCode("01"), "name", false
							, Arrays.asList(new SubCondition( new SubCode("02"), Helper.createAlmChkMsgCnt("規定B", "任意B"), "説明B" )))
				,	"sortSubConditions", subConds
			);

		// Assertion
		assertThat( subConds )
			.extracting( e -> e.getSubCode().v() )
			.containsExactly( "01", "03", "05", "11" );

	}

	/**
	 * 作成する
	 */
	@Test
	public void create_alarmCheckConditionSchedule_success() {
		val subCondLst = Helper.createSubCondition(2);
		val alarmCond = AlarmCheckConditionSchedule.create(new AlarmCheckConditionScheduleCode("01"), "name", true, subCondLst);

		assertThat(alarmCond.getCode().v()).isEqualTo("01");
		assertThat(alarmCond.getConditionName()).isEqualTo("name");
		assertThat(alarmCond.isMedicalOpt()).isTrue();
		assertThat(alarmCond.getSubConditions()).containsExactlyElementsOf(subCondLst);
	}

	/**
	 * メッセージを変更する success
	 */
	@Test
	public void update_success() {
		val subCondLst = Helper.createSubCondition(5);
		val updSubCd = new SubCode("1");
		val updMessage = new AlarmCheckMessage( "new message" );

		val instance  = AlarmCheckConditionSchedule.create(new AlarmCheckConditionScheduleCode("01"), "name", true, subCondLst);
		instance.updateMessage(updSubCd, updMessage);

		// 更新対象
		assertThat(instance.getSubConditions()).filteredOn(e -> e.getSubCode().equals(updSubCd))
				.extracting( e -> e.getMessage().getMessage() ).containsOnly( updMessage );

		// 更新対象以外
		assertThat(instance.getSubConditions()).filteredOn(e -> !e.getSubCode().equals(updSubCd))
				.containsExactlyInAnyOrderElementsOf(
						subCondLst.stream().filter(c -> !c.getSubCode().equals(updSubCd)).collect(Collectors.toList())
				);
	}

	protected static class Helper {
		/**
		 * メッセージ内容を作成する
		 * @param defMsg 既定のメッセージ
		 * @param arbMsg 任意のメッセージ
		 * @return
		 */
		public static AlarmCheckMsgContent createAlmChkMsgCnt(String defMsg, String arbMsg) {
			return new AlarmCheckMsgContent(new AlarmCheckMessage(defMsg), new AlarmCheckMessage(arbMsg));
		}

		/**
		 * 指定件数のサブ条件リスト(DUMMY)を作成する
		 * @param size 件数
		 * @return サブ条件リスト(DUMMY)
		 */
		public static List<SubCondition> createSubCondition(int size) {
			List<SubCondition> result = new ArrayList<>();
			for (int i = 0; i < size; i++) {
				result.add(new SubCondition(
							new SubCode(String.valueOf(i))
						,	createAlmChkMsgCnt( String.valueOf(i), String.valueOf(i))
						,	"explanation" + String.valueOf(i))
					);
			}

			return result;
		}

	}
}
