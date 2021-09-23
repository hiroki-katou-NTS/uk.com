package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.function.algorithm.common;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeActualStamp;
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
		if (!timeActualStamp.getStamp().isPresent()) {
			return new TimeActualStamp(timeActualStamp.getActualStamp().orElse(null), null,
					timeActualStamp.getNumberOfReflectionStamp());
		}
		val changeMean = timeActualStamp.getStamp().get().getTimeDay().getReasonTimeChange().getTimeChangeMeans();
		
		if (null == changeMean) {
			return timeActualStamp;
		}
		
		switch (changeMean) {
		case APPLICATION:
		case DIRECT_BOUNCE_APPLICATION:
		case DIRECT_BOUNCE:
		case AUTOMATIC_SET:
			// 不要な打刻をクリア
			return new TimeActualStamp(timeActualStamp.getActualStamp().orElse(null), null,
					timeActualStamp.getNumberOfReflectionStamp());
		case HAND_CORRECTION_OTHERS:
		case HAND_CORRECTION_PERSON:
		case REAL_STAMP:
			return timeActualStamp;
		case SPR_COOPERATION:
			Optional<WorkType> workTypeOpt = workTypeRepo.findByPK(AppContexts.user().companyId(), workTypeCode);
			// 勤務種類に年休or特別休暇が含まっている場合はSPR連携打刻は消さない
			if (!workTypeOpt.isPresent() || !workTypeOpt.get().getDailyWork().isAnnualOrSpecialHoliday())
				return timeActualStamp;
		default:
			break;
		}
		return timeActualStamp;
		
	}
}
