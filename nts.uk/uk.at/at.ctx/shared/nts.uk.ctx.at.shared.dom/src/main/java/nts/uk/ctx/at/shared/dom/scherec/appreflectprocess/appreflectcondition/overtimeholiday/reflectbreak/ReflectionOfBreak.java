package nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.overtimeholiday.reflectbreak;

import java.util.ArrayList;
import java.util.List;

import nts.uk.ctx.at.shared.dom.common.TimeZoneWithWorkNo;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.condition.UpdateEditSttCreateBeforeAppReflect;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.stampapplication.algorithm.CancelAppStamp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.breaking.BreakTimeSheet;

/**
 * @author thanh_nx
 *
 *         休憩の反映
 */
public class ReflectionOfBreak {

	public static void process(List<TimeZoneWithWorkNo> breakTimeOp, DailyRecordOfApplication dailyApp) {

		List<Integer> lstId = new ArrayList<Integer>();

		// [input.休憩時間帯(List）]でループ
		breakTimeOp.stream().forEach(breakTime -> {

			// 日別勤怠(work）の該当する[休憩時間帯]をチェック
			BreakTimeSheet breakSheet = dailyApp.getBreakTime().getBreakTimeSheets().stream()
					.filter(x -> x.getBreakFrameNo().v() == breakTime.getWorkNo().v()).findFirst().orElse(null);
			if(breakSheet == null) {
				breakSheet = BreakTimeSheet.createDefaultWithNo(breakTime.getWorkNo().v());
				dailyApp.getBreakTime().getBreakTimeSheets().add(breakSheet);
			}

			///該当の休憩枠NOをキーにした[休憩時間帯]を作成する
			breakSheet.setStartTime(breakTime.getTimeZone().getStartTime());
			breakSheet.setEndTime(breakTime.getTimeZone().getEndTime());
			
			// 休憩時間帯を日別勤怠(work）にセットする
			lstId.add(CancelAppStamp.createItemId(157, breakTime.getWorkNo().v(), 6));
			lstId.add(CancelAppStamp.createItemId(159, breakTime.getWorkNo().v(), 6));
			

		});

		// 申請反映状態にする
		UpdateEditSttCreateBeforeAppReflect.update(dailyApp, lstId);
	}

}
