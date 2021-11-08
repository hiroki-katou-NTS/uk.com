package nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.condition;

import java.util.List;

import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.editstate.algorithm.ApplyApplicationProcess;

/**
 * @author thanh_nx
 *
 *         編集状態の更新と申請反映前リストの作成
 *         
 */
public class UpdateEditSttCreateBeforeAppReflect {

	//作成する
	public static void update(DailyRecordOfApplication dailyApp, List<Integer> lstItemId) {

		// 申請反映状態にする
		dailyApp.setEditState(ApplyApplicationProcess.apply(lstItemId, dailyApp.getEditState()));

		// 勤怠項目IDを反映前の勤怠一覧に追加する
		AddTimeItemIDToTimeBeforeReflect.addTime(dailyApp, lstItemId);
	}

}
