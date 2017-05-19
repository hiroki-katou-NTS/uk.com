/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.vacationsetting.yearly;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * The Class CumulationYearlySetting.
 */
@Getter
public class RetentionYearlySetting extends DomainObject{
	
	/** The company id. */
	private String companyId;
	
	/** The upper limit setting. */
	private UpperLimitSetting upperLimitSetting;
	
	/** The can add to cumulation yearly as normal work day. 積立年休を出勤日数として加算する*/
	// TODO: can't find this field in screen.
	private Boolean canAddToCumulationYearlyAsNormalWorkDay;
	
}
