package nts.uk.ctx.at.shared.dom.worktype.service;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.worktype.HolidayAtr;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeClassification;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeUnit;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class JudgmentOneDayHolidayImpl implements JudgmentOneDayHoliday {

	@Inject
	private WorkTypeRepository workTypeRepository;
	
	@Override
	public boolean judgmentOneDayHoliday(String companyID, String workTypeCD) {
		// 休日フラグ=false(初期化)
		boolean result = false;
		// ドメイン「勤務種類」を取得する
		Optional<WorkType> opWorkType = workTypeRepository.findByPK(companyID, workTypeCD);
		if(!opWorkType.isPresent()){
			return result;
		}
		WorkType workType = opWorkType.get();
		// 勤務区分チェック
		if(workType.getDailyWork().getWorkTypeUnit()!=WorkTypeUnit.OneDay){
			return result;
		}
		// 1日をチェックする
		if(workType.getDailyWork().getOneDay()==WorkTypeClassification.Holiday){
			// 休日フラグ=true
			result = true;
			return result;
		}
		return result;
	}

	@Override
	public HolidayAtrOutput checkHolidayAtr(String companyID, String actualWorkTypeCD, String appWorkTypeCD) {
		// 勤務種類の法定区分を取得
		Optional<HolidayAtr> opActualHolidayAtr = this.getHolidayAtr(companyID, actualWorkTypeCD);
		// 勤務種類の法定区分を取得
		Optional<HolidayAtr> opAppHolidayAtr = this.getHolidayAtr(companyID, appWorkTypeCD);
		// OUTPUT．実績の休日区分とOUTPUT．申請の休日区分をチェックする
		if(!opActualHolidayAtr.isPresent() || !opAppHolidayAtr.isPresent()) {
			// チェック結果：一致
			return new HolidayAtrOutput(true, opActualHolidayAtr);
		}
		// OUTPUT．実績の休日区分とOUTPUT．申請の休日区分をチェックする
		if(opActualHolidayAtr.get() == opAppHolidayAtr.get()) {
			// チェック結果：一致
			return new HolidayAtrOutput(true, opActualHolidayAtr);
		}
		// チェック結果：不一致
		return new HolidayAtrOutput(false, opActualHolidayAtr);
	}

	@Override
	public Optional<HolidayAtr> getHolidayAtr(String companyID, String workTypeCD) {
		// 勤務種類を取得(get type công việc)
		Optional<WorkType> opWorkType = workTypeRepository.findByPK(companyID, workTypeCD);
		if(!opWorkType.isPresent()) {
			return Optional.empty();
		}
		// 休日区分を取得(get phân khu holiday)
		Optional<HolidayAtr> opHolidayAtr = opWorkType.get().getHolidayAtr();
		// 休日区分を返す(trả về phân khu holiday)
		return opHolidayAtr;
	}

}
