/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.monthly.roundingset;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.common.timerounding.Rounding;
import nts.uk.ctx.at.shared.dom.worktime.common.RoundingTimeUnit;

/**
 * The Class RoundingMonth.
 */
@AllArgsConstructor
@Getter
public class RoundingMonth extends AggregateRoot {
	/** 会社ID */
	private String companyId;

	/** 勤怠項目ID */
	private Integer timeItemId;

	/** 丸め単位 */
	public RoundingTimeUnit unit;

	/** 端数処理 */
	public Rounding rounding;

	/**
	 * Create from Java Type of Rounding Month
	 * 
	 * @param companyId
	 * @param timeItemId
	 * @param unit
	 * @param rounding
	 * @return
	 */
	public static RoundingMonth createFromJavaType(String companyId, Integer timeItemId, int unit, int rounding) {
		return new RoundingMonth(companyId, timeItemId,
				EnumAdaptor.valueOf(unit, RoundingTimeUnit.class), EnumAdaptor.valueOf(rounding, Rounding.class));
	}
}
