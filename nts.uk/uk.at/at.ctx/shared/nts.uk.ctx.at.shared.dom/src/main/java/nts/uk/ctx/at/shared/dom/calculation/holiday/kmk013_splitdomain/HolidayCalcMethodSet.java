/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.calculation.holiday.kmk013_splitdomain;

import java.util.Optional;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.PremiumAtr;
import nts.uk.ctx.at.shared.dom.calculation.holiday.kmk013_splitdomain.ENUM.CalcurationByActualTimeAtr;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * The Class HolidayCalcMethodSet.
 */

@NoArgsConstructor
@Getter
// 休暇の計算方法の設定
public class HolidayCalcMethodSet extends DomainObject{
	
	/** The premium calc method detail of holiday. */
	// 休暇の割増計算方法
	private PremiumHolidayCalcMethod premiumCalcMethodOfHoliday;
	
	/** The work time calc method detail of holiday. */
	// 休暇の就業時間計算方法
	private WorkTimeHolidayCalcMethod workTimeCalcMethodOfHoliday;

	/**
	 * @param premiumCalcMethodOfHoliday
	 * @param workTimeCalcMethodOfHoliday
	 */
	public HolidayCalcMethodSet(PremiumHolidayCalcMethod premiumCalcMethodOfHoliday,
			WorkTimeHolidayCalcMethod workTimeCalcMethodOfHoliday) {
		super();
		this.premiumCalcMethodOfHoliday = premiumCalcMethodOfHoliday;
		this.workTimeCalcMethodOfHoliday = workTimeCalcMethodOfHoliday;
	}
	
	/**
	 * aggregateが取得できなかった場合の仮作成
	 * 就業、割増共に中身が「実働のみで計算する=実働時間のみで計算する　、詳細設定=null」で作成
	 * @return
	 */
	public static HolidayCalcMethodSet emptyHolidayCalcMethodSet() {
		PremiumHolidayCalcMethod premiumHolidayCalcMethod = new PremiumHolidayCalcMethod(0,Optional.empty());
		WorkTimeHolidayCalcMethod workTimeHolidayCalcMethod = new WorkTimeHolidayCalcMethod(0,Optional.empty());
		return new HolidayCalcMethodSet(premiumHolidayCalcMethod,workTimeHolidayCalcMethod);
	}
	
	/**
	 * 休暇加算するかどうか判断
	 * @return
	 */
	public NotUseAtr getNotUseAtr(PremiumAtr premiumAtr) {
		if(premiumAtr.isRegularWork()) {
			return this.workTimeCalcMethodOfHoliday.getAdvancedSet().isPresent()?this.workTimeCalcMethodOfHoliday.getAdvancedSet().get().getIncludeVacationSet().getAddition():NotUseAtr.NOT_USE;
		}else {
			return this.premiumCalcMethodOfHoliday.getAdvanceSet().isPresent()?this.workTimeCalcMethodOfHoliday.getAdvancedSet().get().getIncludeVacationSet().getAddition():NotUseAtr.NOT_USE;
		}
	}
	
	/**
	 * 割増区分を基に実働のみで計算するしない区分を取得する
	 * @param premiumAtr
	 * @return
	 */
	public CalcurationByActualTimeAtr getCalcurationByActualTimeAtr(PremiumAtr premiumAtr) {
		if(premiumAtr.isRegularWork()) {
			return this.workTimeCalcMethodOfHoliday.getCalculateActualOperation();
		}else {
			return this.premiumCalcMethodOfHoliday.getCalculateActualOperation();
		}
		
	}
	
	
	public HolidayCalcMethodSet changeDeduction() {
		return new HolidayCalcMethodSet(this.premiumCalcMethodOfHoliday,this.workTimeCalcMethodOfHoliday.changeDeduct());
	}
	
}

