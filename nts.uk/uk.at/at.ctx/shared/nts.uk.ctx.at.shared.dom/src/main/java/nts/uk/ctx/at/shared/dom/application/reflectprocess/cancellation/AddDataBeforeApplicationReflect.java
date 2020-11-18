package nts.uk.ctx.at.shared.dom.application.reflectprocess.cancellation;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.DailyRecordToAttendanceItemConverter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.editstate.EditStateOfDailyAttd;

/**
 * @author thanh_nx
 *
 *         申請反映前のデータを取得
 */
public class AddDataBeforeApplicationReflect {

	public static void process(Require require, List<AttendanceBeforeApplicationReflect> attendanceBeforeReflect,
			IntegrationOfDaily domainBefore) {

		DailyRecordToAttendanceItemConverter converter = require.createDailyConverter();
		converter.setData(domainBefore);
		Map<Integer, ItemValue> itemValue = converter
				.convert(attendanceBeforeReflect.stream().map(x -> x.getAttendanceId()).collect(Collectors.toList()))
				.stream().collect(Collectors.toMap(x -> x.getItemId(), x -> x, (x, y) -> x));

		// 反映前の勤怠（List）でループする
		for (AttendanceBeforeApplicationReflect data : attendanceBeforeReflect) {

			// [反映前の日別勤怠(Work）]から、該当する値を取得
			String value = itemValue.get(data.getAttendanceId()).getValue();

			// 取得した値を該当する[反映前の勤怠（List）]に追加する
			data.setValue(value);

			// [反映前の日別勤怠(Work）]から、該当する編集状態を取得
			EditStateOfDailyAttd state = domainBefore.getEditState().stream()
					.filter(x -> x.getAttendanceItemId() == data.getAttendanceId()).findFirst().orElse(null);
			data.setEditState(Optional.ofNullable(state));

		}
	}

	public static interface Require {

		public DailyRecordToAttendanceItemConverter createDailyConverter();

	}
}
