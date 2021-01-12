package nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.editstate.algorithm;

import java.util.ArrayList;
import java.util.List;

import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.editstate.EditStateOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.editstate.EditStateSetting;

/**
 * @author thanh_nx
 *
 *         申請反映状態にする
 */
public class ApplyApplicationProcess {

	public static List<EditStateOfDailyAttd> apply(List<Integer> itemId, List<EditStateOfDailyAttd> lstEditBefore) {

		List<EditStateOfDailyAttd> lstStatusNew = new ArrayList<>();
		// メンバ変数[追加する一覧]を作成して、すべての勤怠項目IDをセットする
		itemId.stream().forEach(id -> {
			lstStatusNew.add(new EditStateOfDailyAttd(id, EditStateSetting.REFLECT_APPLICATION));
		});

		// 編集状態リストにAddする
		return AddEditStatusList.addStatus(lstStatusNew, lstEditBefore);
	}
}
