/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime;

import java.io.Serializable;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.DomainObject;

/**
 * The Class TimeHolidayAdditionSet.
 */
@Getter
@Setter
@Builder
// 時間休暇加算設定
public class TimeHolidayAdditionSet extends DomainObject implements Serializable{

	/** Serializable */
	private static final long serialVersionUID = 1L;
	
	/** The adding method. */
	// 加算方法
	private TimeHolidayAddingMethod addingMethod;
	
	/** The work class. */
	// 勤務区分
	private WorkClassOfTimeHolidaySet workClass;
}
