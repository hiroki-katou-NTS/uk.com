package nts.uk.ctx.at.shared.dom.vacation.setting.addsettingofworktime;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.workrule.addsettingofworktime.NotUseAtr;

/**
 * 変形労働勤務の加算設定
 * @author ken_takasu
 *
 */
public class AddSettingOfIrregularWork extends AggregateRoot implements AddSetting {
	
	 CompanyId companyId;
	@Getter
	private HolidayCalcMethodSet holidayCalcMethodSet;
	
	/**
	 * Constructor 
	 */
	public AddSettingOfIrregularWork(CompanyId companyId, HolidayCalcMethodSet holidayCalcMethodSet) {
		super();
		this.companyId = companyId;
		this.holidayCalcMethodSet = holidayCalcMethodSet;
	}
	
	//「休暇分を含める割増計算詳細設定」or「休暇分を含める就業計算詳細設定」の属性「加算する」を取得する
	public NotUseAtr getNotUseAtr(StatutoryDivision statutoryDivision) {
		NotUseAtr notUseAtr;
		if(statutoryDivision.isNomal()) {
			notUseAtr = this.holidayCalcMethodSet.getWorkTimeCalcMethodOfHoliday().getDetailSet().get().getIncludeHolidaysWorkCalcDetailSet().getToAdd();
		}else {
			notUseAtr = this.holidayCalcMethodSet.getPremiumCalcMethodOfHoliday().getDetailSet().get().getIncludeHolidaysSet().getToAdd();
		}
		return notUseAtr;
	}
	
	/**
	 * 実働のみで計算するしない区分を取得する
	 * @param statutoryDivision
	 * @return
	 */
	public CalculationByActualTimeAtr getCalculationByActualTimeAtr(StatutoryDivision statutoryDivision) {
		
		CalculationByActualTimeAtr calculationByActualTimeAtr;
		if(statutoryDivision.isNomal()) {
			calculationByActualTimeAtr = this.holidayCalcMethodSet.getWorkTimeCalcMethodOfHoliday().getCalculationByActualTime();
		}else {
			calculationByActualTimeAtr = this.holidayCalcMethodSet.getPremiumCalcMethodOfHoliday().getCalculationByActualTime();
		}
		return calculationByActualTimeAtr;	
	}
}
