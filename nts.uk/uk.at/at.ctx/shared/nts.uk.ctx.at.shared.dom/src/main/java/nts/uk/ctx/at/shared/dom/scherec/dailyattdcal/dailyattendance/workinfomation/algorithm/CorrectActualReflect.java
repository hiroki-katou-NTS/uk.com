package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.algorithm;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.tuple.Pair;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.workinformation.enums.ClassificationReflect;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.editstate.EditStateOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.function.workinfo.algorithm.CopyActualToScheduleWorkInfo;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.Reflection;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.WorkInfoOfDailyAttendance;

/**
 * @author ThanhNX
 *
 *         予実反映処理の補正
 */
@Stateless
public class CorrectActualReflect {

	@Inject
	private DetermineReflectActual determineReflectActual;

	// 予実反映
	public Pair<WorkInfoOfDailyAttendance, List<EditStateOfDailyAttd>> process(String companyId, String employeeId, GeneralDate date, 
			WorkInfoOfDailyAttendance workInfo, List<EditStateOfDailyAttd> editState, Reflection reflection) {

		// 予実反映を行うかどうか判断
		ClassificationReflect classifiReflect = determineReflectActual.determine(companyId, reflection, workInfo);

		if (classifiReflect == ClassificationReflect.REFLECT) {
			// 実績の勤務情報を予定の勤務情報へコピー
			CopyActualToScheduleWorkInfo.copy(workInfo, editState);
			// 変更後の勤務予定の勤務情報、編集状態を返す
			return Pair.of(workInfo, editState);
		} else {
			// 勤務予定の勤務情報をそのまま返す
			return Pair.of(workInfo, editState);
		}

	}

}
