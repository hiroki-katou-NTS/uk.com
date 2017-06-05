/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.employment.statutory.worktime;

import nts.arc.layer.dom.AggregateRoot;

/**
 * The Class CompanySetting.
 */
public class CompanySetting extends AggregateRoot {

	/** The flex setting. */
	private FlexSetting flexSetting;

	/** The deformation labor setting. */
	private DeformationLaborSetting deformationLaborSetting;

	/** The year. */
	private Year year;

	/** The company id. */
	private String companyId;
}
