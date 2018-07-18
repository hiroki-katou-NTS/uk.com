package nts.uk.ctx.sys.auth.app.find.registration.user;

import lombok.Value;
import nts.uk.ctx.sys.auth.dom.adapter.company.CompanyImport;

@Value
public class CompanyImportDto {
	
	private String companyCode;
	
	private String companyName;
	
	private String companyId;
	
	 public static CompanyImportDto fromDomain(CompanyImport domain){
		 return new  CompanyImportDto (
				 domain.getCompanyCode().toString(),
				 domain.getCompanyName().toString(),
				 domain.getCompanyId().toString());
	 }

}
