package nts.uk.ctx.at.shared.dom.application.reflectprocess.condition;

import java.util.List;

import nts.uk.ctx.at.shared.dom.application.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.editstate.algorithm.ApplyApplicationProcess;

/**
 * @author thanh_nx
 *
 *         編集状態の更新と申請反映前リストの作成
 *         
 */
public class UpdateEditSttCreateBeforeAppReflect {

	public static void update(DailyRecordOfApplication dailyApp, List<Integer> lstItemId) {

		// 申請反映状態にする
		dailyApp.setEditState(ApplyApplicationProcess.apply(lstItemId, dailyApp.getEditState()));

		// 勤怠項目IDを反映前の勤怠一覧に追加する
		AddTimeItemIDToTimeBeforeReflect.addTime(dailyApp, lstItemId);
	}

}
