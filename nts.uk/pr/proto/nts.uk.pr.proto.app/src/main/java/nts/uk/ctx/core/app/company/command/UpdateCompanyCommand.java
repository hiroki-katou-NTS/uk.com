package nts.uk.ctx.core.app.company.command;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.core.dom.company.Company;

/**
 * UpdateCompanyCommand
 */
@Getter
@Setter
public class UpdateCompanyCommand extends CompanyCommand {

	/** version */
	private long version;
	
	/**
	 * Convert to domain object.
	 * 
	 * @return domain
	 */
	@Override
	public Company toDomain() {
		Company domain = super.toDomain();
		domain.setVersion(this.version);
		return domain;
	}
}
