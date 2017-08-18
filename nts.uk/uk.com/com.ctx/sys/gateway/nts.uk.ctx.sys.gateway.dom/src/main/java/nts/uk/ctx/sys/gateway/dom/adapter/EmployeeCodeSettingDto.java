/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.dom.adapter;

import lombok.Getter;
import nts.uk.ctx.sys.gateway.dom.login.EmployCodeEditType;

/**
 * The Class EmployeeCodeSettingDto.
 */
@Getter
public class EmployeeCodeSettingDto {
	
	//会社ID
	/** The company id. */
	private String companyId;

	//桁数
	/** The number digit. */
	private Integer numberDigit;

	//編集方法
	/** The edit type. */
	private EmployCodeEditType editType;

	/**
	 * @param companyId
	 * @param numberDigit
	 * @param editType
	 */
	//TODO add contruct for mock data
	public EmployeeCodeSettingDto(String companyId, Integer numberDigit, Integer editType) {
		this.companyId = companyId;
		this.numberDigit = numberDigit;
		this.editType = EmployCodeEditType.valueOf(editType);
	}
	
	
}
