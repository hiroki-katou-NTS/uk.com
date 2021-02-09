package nts.uk.ctx.sys.gateway.dom.adapter.company;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.val;

/**
 * 
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class CompanyBsImport {
	
	/** 会社コード*/
	private String companyCode;
	
	/** 会社名*/
	private String companyName;
	
	/** 会社ID*/
	private String companyId;
	
	/** 廃止区分*/
	private int isAbolition;
	
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
