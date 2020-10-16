package nts.uk.ctx.at.schedulealarm.dom.alarmcheck;

import static org.assertj.core.api.Assertions.*;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;

@RunWith(JMockit.class)
public class AlarmCheckConditionScheduleOrderTest {
	@Test
	public void getters() {
		val alarmOrder = AlarmCheckConditionScheduleOrder
				.create(Arrays.asList(new AlarmCheckConditionScheduleCode("01")));

		NtsAssert.invokeGetters(alarmOrder);

	}

	/**
	 * inv-1: 優先順リストの勤務予定のアラームチェック条件コードが重複しないこと
	 * 優先順リストの勤務予定のアラームチェック条件コード == 02
	 */
	@Test
	public void check_inv1_duplicate() {
		NtsAssert.businessException("Msg_1621", ()-> {
			AlarmCheckConditionScheduleOrder.create(
					Arrays.asList(
							new AlarmCheckConditionScheduleCode("01")
						,	new AlarmCheckConditionScheduleCode("02")
						,	new AlarmCheckConditionScheduleCode("02")
					));
		});
	}

	/**
	 * inv-2: 優先順リスト.size() > 0
	 * 優先順リスト.size() == 0
	 */
	@Test
	public void check_inv2_emptyList() {
		NtsAssert.businessException("Msg_1622", ()-> {
			AlarmCheckConditionScheduleOrder.create(Collections.emptyList());
		});
	}

	/**
	 * 作る：成功
	 */
	@Test
	public void create_alarmCheckConditionScheduleOrder_success() {

		val codes = Arrays.asList(
							new AlarmCheckConditionScheduleCode("13")
						,	new AlarmCheckConditionScheduleCode("05")
						,	new AlarmCheckConditionScheduleCode("02")
					);
		val alarmOrder = AlarmCheckConditionScheduleOrder.create(codes);

		assertThat(alarmOrder.getCodes()).containsExactlyElementsOf(codes);

	}
}
