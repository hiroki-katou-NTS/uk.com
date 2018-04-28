/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.worktime.common.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.worktime.common.CommonRestSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.FixedRestCalculateMethod;
import nts.uk.ctx.at.shared.dom.worktime.common.FixedWorkRestSetGetMemento;

/**
 * The Class FixedWorkRestSetDto.
 */
@Getter
@Setter
public class FixedWorkRestSetDto implements FixedWorkRestSetGetMemento {

	/** The common rest set. */
	private CommonRestSettingDto commonRestSet;

	/** The is plan actual not match master refer. */
	private Boolean isPlanActualNotMatchMasterRefer;

	/** The fixed rest calculate method. */
	private Integer fixedRestCalculateMethod;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.FixedWorkRestSetGetMemento#
	 * getCommonRestSet()
	 */
	@Override
	public CommonRestSetting getCommonRestSet() {
		return new CommonRestSetting(this.commonRestSet);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.FixedWorkRestSetGetMemento#
	 * getIsPlanActualNotMatchMasterRefer()
	 */
	@Override
	public boolean getIsPlanActualNotMatchMasterRefer() {
		return this.isPlanActualNotMatchMasterRefer;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.FixedWorkRestSetGetMemento#
	 * getCalculateMethod()
	 */
	@Override
	public FixedRestCalculateMethod getCalculateMethod() {
		return FixedRestCalculateMethod.valueOf(this.fixedRestCalculateMethod);
	}

}
