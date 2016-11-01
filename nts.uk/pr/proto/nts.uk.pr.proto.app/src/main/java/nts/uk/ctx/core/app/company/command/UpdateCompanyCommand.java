package nts.uk.ctx.core.app.company.command;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.core.dom.company.Company;

@Getter
@Setter
public class UpdateCompanyCommand extends CompanyCommand {

	private long version;
	
	@Override
	public Company toDomain() {
		Company domain = super.toDomain();
		domain.setVersion(this.version);
		return domain;
	}
}
