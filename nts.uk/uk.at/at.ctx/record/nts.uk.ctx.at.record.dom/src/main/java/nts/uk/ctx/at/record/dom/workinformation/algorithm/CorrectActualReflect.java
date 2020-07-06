package nts.uk.ctx.at.record.dom.workinformation.algorithm;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.tuple.Pair;

import dailyattdcal.dailywork.workinfo.algorithm.CopyActualToScheduleWorkInfo;
import nts.uk.ctx.at.record.dom.editstate.EditStateOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workinformation.Reflection;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workinformation.enums.ClassificationReflect;

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
	public Pair<WorkInfoOfDailyPerformance, List<EditStateOfDailyPerformance>> process(String companyId,
			WorkInfoOfDailyPerformance workInfo, List<EditStateOfDailyPerformance> editState, Reflection reflection) {

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
