/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktimeset.flex;

import java.util.List;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.worktime_old.DeductionTime;

/**
 * The Class TimezoneOfFixedRestTimeSet.
 */
//固定休憩時間の時間帯設定

/**
 * Gets the timezone.
 *
 * @return the timezone
 */
@Getter
public class TimezoneOfFixedRestTimeSet extends DomainObject{

	/** The timezone. */
	//時間帯
	private List<DeductionTime> timezone;
}
