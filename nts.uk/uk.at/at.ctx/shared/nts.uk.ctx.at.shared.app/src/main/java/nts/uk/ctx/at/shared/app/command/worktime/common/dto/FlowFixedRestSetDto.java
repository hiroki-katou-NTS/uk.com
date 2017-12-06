/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.worktime.common.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.worktime.common.FlowFixedRestCalcMethod;
import nts.uk.ctx.at.shared.dom.worktime.common.FlowFixedRestSetGetMemento;

/**
 * The Class FlowFixedRestSetDto.
 */
@Getter
@Setter
public class FlowFixedRestSetDto implements FlowFixedRestSetGetMemento {

	/** The is refer rest time. */
	private boolean isReferRestTime;

	/** The use private go out rest. */
	private boolean usePrivateGoOutRest;

	/** The use asso go out rest. */
	private boolean useAssoGoOutRest;

	/** The calculate method. */
	private Integer calculateMethod;

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.FlowFixedRestSetGetMemento#getIsReferRestTime()
	 */
	@Override
	public boolean getIsReferRestTime() {
		return this.isReferRestTime;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.FlowFixedRestSetGetMemento#getUsePrivateGoOutRest()
	 */
	@Override
	public boolean getUsePrivateGoOutRest() {
		return this.usePrivateGoOutRest;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.FlowFixedRestSetGetMemento#getUseAssoGoOutRest()
	 */
	@Override
	public boolean getUseAssoGoOutRest() {
		return this.useAssoGoOutRest;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.FlowFixedRestSetGetMemento#getCalculateMethod()
	 */
	@Override
	public FlowFixedRestCalcMethod getCalculateMethod() {
		return FlowFixedRestCalcMethod.valueOf(this.calculateMethod);
	}

}
