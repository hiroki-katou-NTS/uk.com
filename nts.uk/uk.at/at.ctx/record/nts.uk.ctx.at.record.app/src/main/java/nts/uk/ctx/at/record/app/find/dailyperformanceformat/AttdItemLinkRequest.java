/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.find.dailyperformanceformat;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class AttdItemLinkRequest.
 * 
 * @author anhnm
 */
@Getter
@Setter
public class AttdItemLinkRequest {
	
	/** The any item nos. */
	private List<Integer> anyItemNos; 
	
	/** The formula atr. */
	private int formulaAtr;
	
	/** The performance atr. */
	private int performanceAtr;
}
