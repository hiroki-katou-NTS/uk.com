/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.dom.adapter;

import lombok.Getter;

/**
 * The Class CompanyInformationDto.
 */
@Getter
public class CompanyInformationDto {
	
	//会社ID
	/** The company id. */
	private String companyId;
	
	//会社コード
	/** The company code. */
	private String companyCode;
	
	//会社名
	/** The company name. */
	private String companyName;

	/**
	 * @param companyId
	 * @param companyCode
	 * @param companyName
	 */
	public CompanyInformationDto(String companyId, String companyCode, String companyName) {
		this.companyId = companyId;
		this.companyCode = companyCode;
		this.companyName = companyName;
	}
	
	
}
