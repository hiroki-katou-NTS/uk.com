package nts.uk.shr.com.company;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CompanyId {
	
	private final String value;
	
	public static String create(String tenantCode, String companyCode) {
		return tenantCode + "-" + companyCode;
	}

	public static String zeroCompanyInTenant(String tenantCode) {
		return create(tenantCode, "0000");
	}
	
	public String tenantCode() {
		return value.split("-")[0];
	}
	
	public String companyCode() {
		return value.split("-")[1];
	}
}
