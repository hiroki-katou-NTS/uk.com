/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * The Class GraceTimeSet.
 */
//猶予時間設定

/**
 * Gets the grace time.
 *
 * @return the grace time
 */
@Getter
public class GraceTimeSet extends DomainObject {

	/** The include working hour. */
	//就業時間に含める
	private boolean includeWorkingHour;
	
	/** The grace time. */
	//猶予時間
	private LateEarlyGraceTime graceTime;
}
