package nts.uk.shr.com.company;

public class CompanyId {
	
	public static String create(String tenantCode, String companyCode) {
		return tenantCode + "-" + companyCode;
	}

	public static String zeroCompanyInTenant(String tenantCode) {
		return create(tenantCode, "0000");
	}
}
