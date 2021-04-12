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
	
	/**
	 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.shared.就業規則.勤務種類.アルゴリズム.公休消化するか判断(引数：勤務種類)
	 * @param workType 勤務種類
	 * @return boolean(true:公休消化する / false:公休消化しない)
	 */
	public boolean hasUsePublicHoliday(WorkType workType) {
		if (workType == null) {
			return false;
		}
		
		if (workType.getDailyWork().getWorkTypeUnit() == WorkTypeUnit.OneDay) {
			// 	公休消化するか判断（１日）
			// TODO UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.shared.就業規則.勤務種類.アルゴリズム.公休消化するか判断(引数：勤務種類,workAtr)
			return false;
		}
		
		// 公休消化するか判断（午前）
		// TODO UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.shared.就業規則.勤務種類.アルゴリズム.公休消化するか判断(引数：勤務種類,workAtr)
		boolean isMorning = false;
		
		// 公休消化するか判断（午後）
		// TODO UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.shared.就業規則.勤務種類.アルゴリズム.公休消化するか判断(引数：勤務種類,workAtr)
		boolean isAfternoon = false;
		
		// 午前 or 午後 = trueの場合は、trueとする
		if (isMorning || isAfternoon) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.shared.就業規則.勤務種類.アルゴリズム.勤務種類が休暇系か判断
	 * @param workType 勤務種類
	 * @return
	 */
	public boolean isVacationType(WorkType workType) {
		WorkTypeClassification workTypeAtr = workType.getDailyWork().getClassification();
		
		// 勤務種類が休暇系か
		// 休暇系：年休、積立年休、特別休暇、代休、休日、欠勤
		if (workTypeAtr == WorkTypeClassification.AnnualHoliday
				|| workTypeAtr == WorkTypeClassification.YearlyReserved
				|| workTypeAtr == WorkTypeClassification.SpecialHoliday
				|| workTypeAtr == WorkTypeClassification.SubstituteHoliday
				|| workTypeAtr == WorkTypeClassification.Holiday
				|| workTypeAtr == WorkTypeClassification.Absence) {
			// 勤務種類が休日か
			if (workTypeAtr == WorkTypeClassification.Holiday) {
				// 公休消化するか判断
				return hasUsePublicHoliday(workType);
			}
			
			return true;
		}
		
		return false;
	}
}
