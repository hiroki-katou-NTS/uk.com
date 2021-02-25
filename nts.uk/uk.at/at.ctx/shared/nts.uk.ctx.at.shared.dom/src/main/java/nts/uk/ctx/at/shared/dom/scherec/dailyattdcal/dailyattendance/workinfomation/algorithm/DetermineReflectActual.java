package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.algorithm;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.workinformation.enums.ClassificationReflect;
import nts.uk.ctx.at.shared.dom.employmentrules.worktime.CheckWorkDivision;
import nts.uk.ctx.at.shared.dom.employmentrules.worktime.WorkDivision;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.Reflection;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.WorkInfoOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;

/**
 * @author ThanhNX
 *
 *         予実反映を行うかどうか判断
 */
@Stateless
public class DetermineReflectActual {

	@Inject
	private WorkTimeSettingRepository workTimeSettingRepository;

	// 予実反映を行うかどうか判断
	public ClassificationReflect determine(String companyId, Reflection reflection,
			WorkInfoOfDailyAttendance workInfo) {

		if (reflection.getClassifiReflect() != ClassificationReflect.REFLECT_MOBI) {
			return reflection.getClassifiReflect();
		}

		// 流動勤務のみ反映する

		// 就業時間帯の設定を取得
		Optional<WorkTimeSetting> wTimeSettingOpt = workTimeSettingRepository.findByCode(companyId,
				workInfo.getRecordInfo().getWorkTimeCode().v());
		// 勤務区分をチェックする
		WorkDivision wDiv = CheckWorkDivision.check(wTimeSettingOpt.get());
		if (wDiv != WorkDivision.SYSTEM)
			return ClassificationReflect.NO_REFLECT;
		
		return ClassificationReflect.REFLECT;
	}

}
