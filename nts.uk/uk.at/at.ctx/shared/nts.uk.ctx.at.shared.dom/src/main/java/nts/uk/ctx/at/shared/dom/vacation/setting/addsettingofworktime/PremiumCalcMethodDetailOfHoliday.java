package nts.uk.ctx.at.shared.dom.vacation.setting.addsettingofworktime;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.workrule.addsettingofworktime.NotUseAtr;

/**
 * 休暇の割増計算方法詳細
 * @author ken_takasu
 *
 */
public class PremiumCalcMethodDetailOfHoliday {

	@Getter
	private IncludeHolidaysPremiumCalcDetailSet includeHolidaysSet;

	@Getter
	private NotUseAtr deductLateLeaveEarly;//遅刻・早退を控除する

	public PremiumCalcMethodDetailOfHoliday(IncludeHolidaysPremiumCalcDetailSet includeHolidaysSet,NotUseAtr deductLateLeaveEarly) {
		this.includeHolidaysSet = includeHolidaysSet;
		this.deductLateLeaveEarly = deductLateLeaveEarly;
	}//休暇分を含める設定
	
}
