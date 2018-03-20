/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew;

import java.util.List;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.common.Year;

@Getter
public class DeforLaborSetting extends AggregateRoot {
	// 年
	/** The year. */
	protected Year year;

	// 法定時間
	/** The statutory setting. */
	protected List<MonthlyUnit> statutorySetting;

}
