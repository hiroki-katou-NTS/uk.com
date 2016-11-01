package nts.uk.ctx.core.app.company.command;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.core.dom.company.Company;

@Getter
@Setter
public abstract class CompanyCommand {
	
	private String companyCode;
	
	private String companyName;
	
	public Company toDomain() {
		return Company.createToAddOrUpdate(this.companyCode, this.companyName);
	}
}
