/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.function.dom.dailyworkschedule;

import lombok.Getter;

/**
 * The Class FormatPerformanceImport.
 */
// @author HoangDD
@Getter
public class FormatPerformanceImport {
	/**
    * 会社ID
    */
    private String cid;
    
    /**
    * フォーマット種類 
    * 権限 : 0 - authority
    * 勤務種別 : 1 - businessType
    */
    private int settingUnitType;

	/**
	 * Instantiates a new format performance import.
	 *
	 * @param cid the cid
	 * @param settingUnitType the setting unit type
	 */
	public FormatPerformanceImport(String cid, int settingUnitType) {
		super();
		this.cid = cid;
		this.settingUnitType = settingUnitType;
	}
}
