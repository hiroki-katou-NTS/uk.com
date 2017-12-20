package nts.uk.ctx.at.shared.dom.worktime.fixedworkset;

/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.worktime_old.DeductionTime;
/**
 * The Class FixRestTimezoneSet.
 */
//固定勤務の休憩時間帯
@Getter
@AllArgsConstructor
public class FixRestTimezoneSet extends DomainObject {

	/** The timezone. */
	// 時間帯
	private List<DeductionTime> timezone;
}
