package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.function.algorithm.aftercorrectatt.checkspr;

import java.util.Optional;

import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeActualStamp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.TimeChangeMeans;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeClassification;

/**
 * @author thanh_nx
 *
 *         SPR打刻の場合の判断
 */
public class JudgmentSprStamp {

	public static boolean checkRemove(Require require, String workTypeCode, TimeActualStamp timeActualStamp) {

		// 時刻変更理由を確認
		if (timeActualStamp.getStamp().isPresent() && timeActualStamp.getStamp().get().getTimeDay()
				.getReasonTimeChange().getTimeChangeMeans() == TimeChangeMeans.SPR_COOPERATION) {

			// SPR連携打刻
			Optional<WorkType> wTypeOpt = require.findByPK(workTypeCode);
			if (wTypeOpt.isPresent() && (wTypeOpt.get().getDailyWork()
					.getClassification() == WorkTypeClassification.AnnualHoliday
					|| wTypeOpt.get().getDailyWork().getClassification() == WorkTypeClassification.SpecialHoliday)) {
				return false;
			}
		}
		return true;
	}

	public static interface Require {

		Optional<WorkType> findByPK(String workTypeCd);

	}
}
