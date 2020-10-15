package nts.uk.ctx.at.record.dom.breakorgoout;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import nts.uk.ctx.at.record.dom.editstate.EditStateOfDailyPerformance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.breaking.BreakTimeSheet;

/**
 * @author ThanhNX
 *
 */
public class MergeBreaksTime {

	public static List<Integer> BREAKTIME_START_ID = Arrays.asList(159, 165, 171, 177, 183, 189, 195, 201, 207, 217);

	public static List<Integer> BREAKTIME_END_ID = Arrays.asList(161, 167, 173, 179, 185, 191, 197, 203, 209, 215);

	public static Integer BREAKTIME_NO = 10;

	// 休憩時間帯をマージする
	public static void merge(List<EditStateOfDailyPerformance> state, BreakTimeOfDailyPerformance breakTime) {
		// 時間帯を取得する
		List<BreakTimeSheet> breakTimeSheet = breakTime.getTimeZone().getBreakTimeSheets();

		List<Integer> lstIdEdit = state.stream().map(x -> x.getEditState().getAttendanceItemId()).collect(Collectors.toList());

		// 該当する編集状態が存在するかどうか確認する
		List<Integer> lstEditInBreakTime = Stream.concat(BREAKTIME_START_ID.stream(), BREAKTIME_END_ID.stream())
				.filter(x -> lstIdEdit.contains(x)).collect(Collectors.toList());

		if (!lstEditInBreakTime.isEmpty()) {
			return;
		}

		IntStream.range(1, BREAKTIME_NO).boxed().forEach(no -> {
			// TODO: マージ処理
			MergeProcessBreakTime.merge(no, breakTimeSheet.size() >= no ? breakTimeSheet.get(no - 1) : null);
		});
	}

}
