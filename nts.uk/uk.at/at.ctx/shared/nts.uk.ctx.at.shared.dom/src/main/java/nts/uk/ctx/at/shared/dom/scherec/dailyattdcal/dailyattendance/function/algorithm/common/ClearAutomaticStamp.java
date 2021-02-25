package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.function.algorithm.common;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeActualStamp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.TimeChangeMeans;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author ThanhNX
 *
 *         自動打刻をクリアする
 */
@Stateless
public class ClearAutomaticStamp {

	@Inject
	private WorkTypeRepository workTypeRepo;

	// 自動打刻をクリアする
	public TimeActualStamp clear(String workTypeCode, TimeActualStamp timeActualStamp) {

		// 打刻の時刻変更手段を確認
		if (!timeActualStamp.getStamp().isPresent() || (timeActualStamp.getStamp().get().getTimeDay()
				.getReasonTimeChange().getTimeChangeMeans() != TimeChangeMeans.SPR_COOPERATION
				&& timeActualStamp.getStamp().get().getTimeDay().getReasonTimeChange()
						.getTimeChangeMeans() != TimeChangeMeans.DIRECT_BOUNCE)) {
			return timeActualStamp;
		}

		if (timeActualStamp.getStamp().get().getTimeDay().getReasonTimeChange()
				.getTimeChangeMeans() == TimeChangeMeans.SPR_COOPERATION) {
			Optional<WorkType> workTypeOpt = workTypeRepo.findByPK(AppContexts.user().companyId(), workTypeCode);
			// 勤務種類に年休or特別休暇が含まっている場合はSPR連携打刻は消さない
			if (!workTypeOpt.isPresent() || !workTypeOpt.get().getDailyWork().isAnnualOrSpecialHoliday())
				return timeActualStamp;

		}
		// 不要な打刻をクリア
		return new TimeActualStamp(timeActualStamp.getActualStamp().orElse(null), null,
				timeActualStamp.getNumberOfReflectionStamp());
	}

}
