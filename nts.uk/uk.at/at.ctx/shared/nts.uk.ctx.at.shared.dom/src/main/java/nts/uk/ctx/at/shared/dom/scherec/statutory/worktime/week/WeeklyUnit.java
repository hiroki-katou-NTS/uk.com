/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week;

import org.eclipse.persistence.internal.xr.ValueObject;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.WeeklyTime;

/** 週単位  */
@Getter
public class WeeklyUnit extends ValueObject {

	/** The Constant DEFAULT_VALUE. */
	public static final int DEFAULT_VALUE = 0;

	/** 週間時間 */
	private WeeklyTime time;
	
	public WeeklyUnit(WeeklyTime time) {
		super();
		this.time = time;
	}

}
