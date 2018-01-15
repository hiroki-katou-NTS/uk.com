/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.common.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.worktime.common.CommonRestSettingSetmemento;
import nts.uk.ctx.at.shared.dom.worktime.common.RestTimeOfficeWorkCalcMethod;

/**
 * The Class CommonRestSettingDto.
 */
@Getter
@Setter
public class CommonRestSettingDto implements CommonRestSettingSetmemento{
	
	/** The calculate method. */
	private Integer calculateMethod;

	/**
	 * Instantiates a new common rest setting dto.
	 */
	public CommonRestSettingDto() {}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.CommonRestSettingSetmemento#
	 * setCalculateMethod(nts.uk.ctx.at.shared.dom.worktime.common.
	 * RestTimeOfficeWorkCalcMethod)
	 */
	@Override
	public void setCalculateMethod(RestTimeOfficeWorkCalcMethod method) {
		this.calculateMethod = method.value;		
	}

}
