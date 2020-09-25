package nts.uk.ctx.at.shared.dom.vacation.setting.addsettingofworktime;

import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.CalcurationByActualTimeAtr;

/**
 * 休暇加算区分
 * @author keisuke_hoshina
 *
 */
public enum HolidayAdditionAtr {
	HolidayAddition,
	HolidayNotAddition;
	
	
	/**
	 * 実働のみで計算する区分から休暇加算区分に変換する
	 * @param calcActualTime
	 * @return 休暇加算区分
	 */
	public HolidayAdditionAtr convertFromCalcByActualTimeToHolidayAdditionAtr(CalcurationByActualTimeAtr calcActualTime){
		if(calcActualTime.isCalclationByActualTime()) {
			return HolidayNotAddition;
		}
		else {
			return HolidayAddition;
		}
	}
	
	/**
	 * 休暇加算をするか判定する
	 * @return　休暇加算をする
	 */
	public boolean isHolidayAddition() {
		return HolidayAddition.equals(this);
	}
}
