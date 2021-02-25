package nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.editstate.algorithm;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.assertj.core.groups.Tuple;
import org.junit.Test;

import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.editstate.EditStateOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.editstate.EditStateSetting;

public class ApplyApplicationProcessTest {

	/*
	 * テストしたい内容
	 * 
	 * →日別勤怠の編集状態のデータ
	 * 
	 * 準備するデータ
	 * 
	 * →勤怠項目ID
	 * 
	 */
	@Test
	public void test() {

		List<Integer> itemId = Arrays.asList(1, 1292, 1293, 2);

		List<EditStateOfDailyAttd> resultActual = ApplyApplicationProcess.apply(itemId, new ArrayList<>());

		assertThat(resultActual).extracting(x -> x.getAttendanceItemId(), x -> x.getEditStateSetting()).containsExactly(
				Tuple.tuple(1, EditStateSetting.REFLECT_APPLICATION),
				Tuple.tuple(1292, EditStateSetting.REFLECT_APPLICATION),
				Tuple.tuple(1293, EditStateSetting.REFLECT_APPLICATION),
				Tuple.tuple(2, EditStateSetting.REFLECT_APPLICATION));
	}

}
