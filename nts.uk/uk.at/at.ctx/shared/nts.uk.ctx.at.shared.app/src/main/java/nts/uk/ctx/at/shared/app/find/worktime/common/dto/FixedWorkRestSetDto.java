/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.common.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.worktime.common.CommonRestSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.FixedRestCalculateMethod;
import nts.uk.ctx.at.shared.dom.worktime.common.FixedWorkRestSetSetMemento;

/**
 * The Class FixedWorkRestSetDto.
 */
@Getter
@Setter
public class FixedWorkRestSetDto implements FixedWorkRestSetSetMemento {

	/** The common rest set. */
	private CommonRestSettingDto commonRestSet;

	/** The is plan actual not match master refer. */
	private Boolean isPlanActualNotMatchMasterRefer;

	/** The fixed rest calculate method. */
	private Integer fixedRestCalculateMethod;

	/**
	 * Instantiates a new fixed work rest set dto.
	 */
	public FixedWorkRestSetDto() {
		this.commonRestSet = new CommonRestSettingDto();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.FixedWorkRestSetSetMemento#
	 * setCommonRestSet(nts.uk.ctx.at.shared.dom.worktime.common.
	 * CommonRestSetting)
	 */
	@Override
	public void setCommonRestSet(CommonRestSetting set) {
		if (set != null) {
			this.commonRestSet = new CommonRestSettingDto();
			set.saveToMemento(this.commonRestSet);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.FixedWorkRestSetSetMemento#
	 * setIsPlanActualNotMatchMasterRefer(boolean)
	 */
	@Override
	public void setIsPlanActualNotMatchMasterRefer(boolean isPlanActualNotMatchMasterRefer) {
		this.isPlanActualNotMatchMasterRefer = isPlanActualNotMatchMasterRefer;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.FixedWorkRestSetSetMemento#
	 * setCalculateMethod(nts.uk.ctx.at.shared.dom.worktime.common.
	 * FixedRestCalculateMethod)
	 */
	@Override
	public void setCalculateMethod(FixedRestCalculateMethod method) {
		this.fixedRestCalculateMethod = method.value;
	}

}
