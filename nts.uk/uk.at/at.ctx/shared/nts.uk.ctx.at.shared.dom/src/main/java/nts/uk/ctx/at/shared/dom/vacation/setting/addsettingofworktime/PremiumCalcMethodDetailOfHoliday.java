package nts.uk.ctx.at.shared.dom.vacation.setting.addsettingofworktime;

import lombok.Getter;

/**
 * 休暇の割増計算方法詳細
 * @author ken_takasu
 *
 */
public class PremiumCalcMethodDetailOfHoliday {
	
	public PremiumCalcMethodDetailOfHoliday(IncludeHolidaysPremiumCalcDetailSet includeHolidaysSet) {
		super();
		this.includeHolidaysSet = includeHolidaysSet;
	}//休暇分を含める設定
	
	@Getter
	private IncludeHolidaysPremiumCalcDetailSet includeHolidaysSet;

}
