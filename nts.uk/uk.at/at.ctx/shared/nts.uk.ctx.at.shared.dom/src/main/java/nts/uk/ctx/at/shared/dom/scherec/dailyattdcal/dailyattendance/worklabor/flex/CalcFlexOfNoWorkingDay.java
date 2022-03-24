package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worklabor.flex;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;

/**
 * 非勤務日のフレックス時間計算
 * @author shuichi_ishida
 */
@Getter
@NoArgsConstructor
public class CalcFlexOfNoWorkingDay {

	/** 設定 */
	private CalcMethodOfNoWorkingDay setting = CalcMethodOfNoWorkingDay.IS_CALC_FLEX_TIME;
	
	/**
	 * ファクトリー
	 * @param setting 設定
	 * @return 非勤務日のフレックス時間計算
	 */
	public static CalcFlexOfNoWorkingDay createJavaType(int setting){
		CalcFlexOfNoWorkingDay myclass = new CalcFlexOfNoWorkingDay();
		myclass.setting = EnumAdaptor.valueOf(setting, CalcMethodOfNoWorkingDay.class);
		return myclass;
	}
}
