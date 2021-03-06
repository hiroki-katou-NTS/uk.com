package nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.CheckCareResult;
import nts.uk.ctx.at.shared.dom.remainingnumber.work.CareType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeSet;

/**
 * 
 * @author sonnlb
 *
 */
@Stateless
public class CheckCareServiceImpl implements CheckCareService {

	@Inject
	private NursingLeaveSettingRepository nursingRepo;

	@Override
	public CheckCareResult checkCare(WorkTypeSet wkSet, String cId) {
		// 介護看護休暇設定を取得する
		NursingLeaveSetting childNursing = this.nursingRepo.findByCompanyIdAndNursingCategory(cId,
				NursingCategory.ChildNursing.value);

		// 特別休暇枠NOを取得する
		// vì kiểu dữ liệu của 欠勤枠 và 特別休暇枠 là int (kiểu gì cũng có data nên không cần
		// check gì cả, cứ lấy ra thôi)
		int holidayNo = wkSet.getSumSpHodidayNo();
		int absenseNo = wkSet.getSumAbsenseNo();
		// {特別休暇枠NO ＝ 看護介護休暇設定．特別休暇枠 and 看護介護休暇設定．特別休暇枠 != Optional．empty}

		if (childNursing != null && (
		// 特別休暇枠NO ＝ 看護介護休暇設定．特別休暇枠 and 看護介護休暇設定．特別休暇枠 != Optional．empty
		childNursing.getWorkAbsence().map(x -> (x.equals(absenseNo) && x != 0)).orElse(false) ||
		// 欠勤枠NO ＝ 看護介護休暇設定．欠勤枠 and 看護介護休暇設定．欠勤枠 !=Optional．empty}
				childNursing.getSpecialHolidayFrame().map(x -> (x.equals(holidayNo)&& x != 0)).orElse(false))) {
			return new CheckCareResult(true, CareType.ChildNursing);
		} else {

			// 介護看護休暇設定を取得する
			NursingLeaveSetting nursing = this.nursingRepo.findByCompanyIdAndNursingCategory(cId,
					NursingCategory.Nursing.value);
			
			if (nursing != null && (
					nursing.getWorkAbsence().map(x -> (x.equals(absenseNo)&& x != 0)).orElse(false) ||
					nursing.getSpecialHolidayFrame().map(x -> (x.equals(holidayNo) && x != 0)).orElse(false))) {
				return new CheckCareResult(true, CareType.Nursing);
				
			}

			return new CheckCareResult(false, null);
		}
	}

}
