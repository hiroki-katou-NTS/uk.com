package nts.uk.ctx.at.shared.dom.worktype.algorithm;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeClassification;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeUnit;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class JudgmentWorkTypeService {
	@Inject
	private WorkTypeRepository repository;

	/**
	 * 勤務種類が１日年休特休かの判断
	 * @param workTypeCode
	 * @return
	 */
	public boolean checkWorkTypeIsHD(String workTypeCode) {
		boolean isFlagRed = false;
		String companyId = AppContexts.user().companyId();
		Optional<WorkType> checkIsHD = repository.findByPK(companyId, workTypeCode);
		if (!checkIsHD.isPresent()) {
			return false;
		}

		WorkType data = checkIsHD.get();

		if (data.getDailyWork().getWorkTypeUnit() == WorkTypeUnit.OneDay
				&& (data.getDailyWork().getOneDay() == WorkTypeClassification.AnnualHoliday
						|| data.getDailyWork().getOneDay() == WorkTypeClassification.SpecialHoliday)) {
			isFlagRed = true;
		} else {
			isFlagRed = false;
		}
		return isFlagRed;
	}
}
