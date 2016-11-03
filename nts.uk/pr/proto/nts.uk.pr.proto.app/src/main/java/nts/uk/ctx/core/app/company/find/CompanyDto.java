package nts.uk.ctx.core.app.company.find;

import lombok.Value;
import nts.uk.ctx.core.dom.company.Company;

@Value
public class CompanyDto {
	
	String code;
	String name;

	public static CompanyDto fromDomain(Company domain) {
		return new CompanyDto(domain.getCode().v(), domain.getName().v());
	}
}
