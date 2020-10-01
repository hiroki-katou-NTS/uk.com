package nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.editstate.algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.editstate.EditStateOfDailyAttd;

/**
 * @author thanh_nx
 *
 *         編集状態リストにAddする
 */
public class AddEditStatusList {

	public static List<EditStateOfDailyAttd> addStatus(List<EditStateOfDailyAttd> lstEdit,
			List<EditStateOfDailyAttd> lstBefore) {

		List<EditStateOfDailyAttd> result = new ArrayList<>(lstBefore);
		for (EditStateOfDailyAttd edit : lstEdit) {

			Optional<EditStateOfDailyAttd> itemEdit = lstBefore.stream()
					.filter(x -> x.getAttendanceItemId() == edit.getAttendanceItemId()).findFirst();

			if (!itemEdit.isPresent()) {
				result.add(edit);
			}
		}
		return result;
	}
}
