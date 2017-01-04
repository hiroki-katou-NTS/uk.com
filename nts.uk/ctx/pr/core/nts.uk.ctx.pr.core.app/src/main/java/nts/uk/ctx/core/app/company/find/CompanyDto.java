package nts.uk.ctx.core.app.company.find;

import lombok.Value;
import nts.uk.ctx.core.dom.company.Company;

/**
 * Finder DTO of Company
 */
@Value
public class CompanyDto {
	
	/** code */
	String code;
	
	/** name */
	String name;

	/**
	 * Create DTO from domain object.
	 * 
	 * @param domain domain
	 * @return DTO
	 */
	public static CompanyDto fromDomain(Company domain) {
		return new CompanyDto(domain.getCode().v(), domain.getName().v());
	}
}
