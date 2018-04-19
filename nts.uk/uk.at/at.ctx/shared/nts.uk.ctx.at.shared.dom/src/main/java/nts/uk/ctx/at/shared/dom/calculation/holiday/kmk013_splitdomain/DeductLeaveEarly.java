/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.calculation.holiday.kmk013_splitdomain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.DomainObject;

/**
 * The Class DeductLeaveEarly.
 */
// 遅刻早退を控除する
@NoArgsConstructor
@Getter
public class DeductLeaveEarly extends DomainObject{
	
	/** The deduct. */
	// 控除する
	private boolean deduct;
	
	/** The enable set per work hour. */
	// 就業時間帯毎の設定を可能とする
	private boolean enableSetPerWorkHour;

	/**
	 * @param deduct
	 * @param enableSetPerWorkHour
	 */
	public DeductLeaveEarly(int deduct, int enableSetPerWorkHour) {
		super();
		this.deduct = deduct == TRUE_CONST ? true : false;
		this.enableSetPerWorkHour = enableSetPerWorkHour == TRUE_CONST ? true : false;
	}
	
	private static final int TRUE_CONST = 1;
}

