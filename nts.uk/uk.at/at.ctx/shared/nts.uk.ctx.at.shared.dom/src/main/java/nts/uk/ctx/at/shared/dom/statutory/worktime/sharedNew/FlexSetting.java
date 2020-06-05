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
public class FlexSetting extends AggregateRoot {

	/** The year. */
	// 年
	protected Year year;

	/** The statutory setting. */
	// 法定時間
	protected List<MonthlyUnit> statutorySetting;

	/** The specified setting. */
	// 所定時間
	protected List<MonthlyUnit> specifiedSetting;
	
	/** The week average setting. */
	// 週平均時間
	protected List<MonthlyUnit> weekAveSetting;
}
