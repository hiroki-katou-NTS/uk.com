package nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.optional;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.assertj.core.groups.Tuple;
import org.junit.Test;

import nts.uk.ctx.at.shared.dom.scherec.anyitem.AnyItemNo;
import nts.uk.ctx.at.shared.dom.scherec.application.reflectprocess.common.ReflectApplicationHelper;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.ScheduleRecordClassifi;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.editstate.EditStateSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.optionalitemvalue.AnyItemAmount;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.optionalitemvalue.AnyItemTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.optionalitemvalue.AnyItemTimes;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.optionalitemvalue.AnyItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.optionalitemvalue.AnyItemValueOfDailyAttd;

/**
 * @author thanh_nx
 *
 *         任意項目の反映
 */
public class ReflectOptionalTest {

	/*
	 * テストしたい内容
	 * 
	 * 任意項目の反映
	 * 
	 * → 回数、金額、時間 を反映する
	 * 
	 * → 日別実績の中の任意項目Noを保存する場合更新する、保存しない場合作成する
	 * 
	 * 準備するデータ
	 * 
	 * 
	 */
	@Test
	public void test() {
		DailyRecordOfApplication dailyApp = ReflectApplicationHelper.createRCWithTimeLeav(ScheduleRecordClassifi.RECORD,
				1);
		List<AnyItemValue> optionalIntegration = new ArrayList<AnyItemValue>();
		optionalIntegration.addAll(Arrays.asList(createAnyItemApp(1, 1.0, 0, 0), //
				createAnyItemApp(2, 0, 2, 0)));
		dailyApp.setAnyItemValue(Optional.of(new AnyItemValueOfDailyAttd(optionalIntegration)));

		// 申請の任意項目
		List<AnyItemValue> optionalItems = Arrays.asList(createAnyItemApp(1, 98.0, 0, 0), //
				createAnyItemApp(2, 0.0, 201, 0), //
				createAnyItemApp(3, 0.0, 0, 302)//
		);

		ReflectOptional.reflect(optionalItems, dailyApp);

		assertThat(dailyApp.getAnyItemValue().get().getItems()).extracting(x -> x.getItemNo().v(),
				x -> x.getRowTimes().doubleValue(), x -> x.getRowAmount(), x -> x.getRowTime())
				.containsExactly(Tuple.tuple(1, 98.0, 0, 0), // 回数の更新
						Tuple.tuple(2, 0.0, 201, 0), // 金額の更新
						Tuple.tuple(3, 0.0, 0, 302)// 時間の作成
				);
		
		assertThat(dailyApp.getEditState()).extracting(x -> x.getAttendanceItemId(), x -> x.getEditStateSetting())
				.containsExactly(Tuple.tuple(641, EditStateSetting.REFLECT_APPLICATION),
						Tuple.tuple(642, EditStateSetting.REFLECT_APPLICATION),
						Tuple.tuple(643, EditStateSetting.REFLECT_APPLICATION));

	}

	private AnyItemValue createAnyItemApp(int no, double times, int amount, int time) {
		return new AnyItemValue(new AnyItemNo(no), // No
				Optional.of(new AnyItemTimes(new BigDecimal(times))), // 回数
				Optional.of(new AnyItemAmount(amount)), // 金額
				Optional.of(new AnyItemTime(time)));// 時間
	}
}
