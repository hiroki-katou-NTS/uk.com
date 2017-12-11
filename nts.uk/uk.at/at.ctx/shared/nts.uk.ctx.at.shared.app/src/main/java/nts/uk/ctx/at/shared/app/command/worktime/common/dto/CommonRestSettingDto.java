/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.worktime.common.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.worktime.common.CommonRestSettingGetmemento;
import nts.uk.ctx.at.shared.dom.worktime.common.RestTimeOfficeWorkCalcMethod;

/**
 * The Class CommonRestSettingDto.
 */
@Getter
@Setter
public class CommonRestSettingDto implements CommonRestSettingGetmemento{
	
	/** The calculate method. */
	private Integer calculateMethod;


	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.CommonRestSettingGetmemento#
	 * getCalculateMethod()
	 */
	@Override
	public RestTimeOfficeWorkCalcMethod getCalculateMethod() {
		return RestTimeOfficeWorkCalcMethod.valueOf(this.calculateMethod);
	}

}
