package nts.uk.ctx.core.app.company.command;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.core.dom.company.Company;

/**
 * Base class of add/update company commands.
 */
@Getter
@Setter
public abstract class CompanyCommand {
	
	/** code */
	private String companyCode;
	
	/** name */
	private String companyName;
	
	/**
	 * Convert to domain object.
	 * 
	 * @return domain
	 */
	public Company toDomain() {
		return Company.createFromJavaType(this.companyCode, this.companyName);
	}
}
