/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktimeset.flow;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * The Class FlowOTSet.
 */
//流動残業設定
@Getter
public class FlowOTSet extends DomainObject {

	/** The fixed change atr. */
	//所定変動区分
	private FixedChangeAtr fixedChangeAtr;
}
