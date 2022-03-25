package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.function.algorithm.aftercorrectwork.startendwork;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.DailyRecordToAttendanceItemConverter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.editstate.EditStateOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.WorkInfoOfDailyAttendance;

/**
 * @author thanh_nx
 *
 *  始業終業時刻の補正
 */
public class CorrectStartEndWorkForWorkInfo {
	
	/** 始業終業時刻の補正 */
	public static WorkInfoOfDailyAttendance correctStartEndWork(Require require, String companyId,
			WorkInfoOfDailyAttendance workInfo, List<EditStateOfDailyAttd> editState) {
		
		/** input.日別勤怠の勤務情報を取得 */
		DailyRecordToAttendanceItemConverter converter = require.createDailyConverter().withWorkInfo(workInfo)
				.completed();
		List<Integer> atendanceId = editState.stream().map(x -> x.getAttendanceItemId()).distinct()
				.collect(Collectors.toList());
		List<ItemValue> beforeItems = atendanceId.isEmpty() ? new ArrayList<>() : converter.convert(atendanceId);
		
		/** 勤務情報と始業終業を変更する */
		workInfo.changeWorkSchedule(require, companyId, workInfo.getRecordInfo(), true, true);
		
		/**手修正を基に戻す*/
		DailyRecordToAttendanceItemConverter afterConverter = require.createDailyConverter().withWorkInfo(workInfo)
				.completed();
		if(!beforeItems.isEmpty()) afterConverter.merge(beforeItems);
		
		return afterConverter.workInfo();
	}
	
	public static interface Require extends WorkInfoOfDailyAttendance.Require {
		DailyRecordToAttendanceItemConverter createDailyConverter();
	}
}
