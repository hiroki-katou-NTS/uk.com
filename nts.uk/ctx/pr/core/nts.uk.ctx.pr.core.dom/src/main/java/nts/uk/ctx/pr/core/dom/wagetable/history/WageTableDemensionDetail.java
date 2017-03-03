/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.wagetable.history;

import java.util.List;

import lombok.Getter;
import nts.uk.ctx.pr.core.dom.wagetable.DemensionNo;
import nts.uk.ctx.pr.core.dom.wagetable.element.BaseItem;
import nts.uk.ctx.pr.core.dom.wagetable.element.ElementMode;

/**
 * The Class WageTableDemension.
 */
@Getter
public class WageTableDemensionDetail {

	/** The demension no. */
	private DemensionNo demensionNo;

	/** The element mode setting. */
	private ElementMode elementModeSetting;

	/** The items. */
	private List<BaseItem> items;

}
