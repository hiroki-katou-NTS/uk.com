/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.shared.dom.company;

import lombok.Getter;
import lombok.val;

/**
 * The Class CompanyInformationDto.
 */
@Getter
public class CompanyInforImport {
	
	//会社ID
	/** The company id. */
	private String companyId;
	
	//会社コード
	/** The company code. */
	private String companyCode;
	
	//会社名
	/** The company name. */
	private String companyName;
	
	/** The Abolition */
	// 廃止区分
	private int isAbolition;

	/**
	 * @param companyId
	 * @param companyCode
	 * @param companyName
	 */
	public CompanyInforImport(String companyId, String companyCode, String companyName, int isAbolition) {
		this.companyId = companyId;
		this.companyCode = companyCode;
		this.companyName = companyName;
		this.isAbolition = isAbolition;
	}
	
	public boolean isAbolished() {
		return isAbolition == 1;
	}

	public static String createCompanyId(String contractCd, String companyCode) {
		return contractCd + "-" + companyCode;
	}
	
	public static String extractTenantCode(String CompanyId) {
		val Parts = CompanyId.split("-");
		return Parts[0];
	}

	public static String extractCompanyCode(String CompanyId) {
		val Parts = CompanyId.split("-");
		return Parts[1];
	}
	
}
