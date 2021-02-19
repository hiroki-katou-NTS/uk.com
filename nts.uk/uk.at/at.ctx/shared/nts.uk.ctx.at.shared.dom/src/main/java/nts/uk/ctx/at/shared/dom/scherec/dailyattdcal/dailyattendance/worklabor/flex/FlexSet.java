/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worklabor.flex;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.CalcMethodOfNoWorkingDay;

/**
 * フレックス勤務の日別計算設定
 * The Class FlexSet.
 */
@AllArgsConstructor
@Getter
public class FlexSet extends AggregateRoot {

	/** 会社ID */
	private String companyId;

	/** 不足計算 */
	public FlexCalcAtr missCalcHd;

	/** 割増計算 */
	public FlexCalcAtr premiumCalcHd;

	/** 不足計算 */
	public FlexCalcAtr missCalcSubhd;

	/** 割増計算 */
	public FlexCalcAtr premiumCalcSubhd;
	
	/** 法定労働控除時間計算 */
	public TimeHolidayCalcSet flexDeductTimeCalc;
	
	/** 非勤務日計算 */
	public CalcMethodOfNoWorkingDay flexNonworkingDayCalc;

	/**
	 * Create from Java Type of Flex Set
	 * 
	 * @param companyId
	 * @param missCalcHd
	 * @param premiumCalcHd
	 * @param missCalcSubhd
	 * @param premiumCalcSubhd
	 * @return
	 */
	public static FlexSet createFromJavaType(String companyId, int missCalcHd, int premiumCalcHd, int missCalcSubhd,
			int premiumCalcSubhd, int flexDeductTimeCalc, int flexNonworkingDayCalc) {
		return new FlexSet(companyId, EnumAdaptor.valueOf(missCalcHd, FlexCalcAtr.class), EnumAdaptor.valueOf(premiumCalcHd, FlexCalcAtr.class),EnumAdaptor.valueOf(missCalcSubhd, FlexCalcAtr.class),EnumAdaptor.valueOf(premiumCalcSubhd, FlexCalcAtr.class),
				EnumAdaptor.valueOf(flexDeductTimeCalc, TimeHolidayCalcSet.class), EnumAdaptor.valueOf(flexNonworkingDayCalc, CalcMethodOfNoWorkingDay.class));
	}
}
