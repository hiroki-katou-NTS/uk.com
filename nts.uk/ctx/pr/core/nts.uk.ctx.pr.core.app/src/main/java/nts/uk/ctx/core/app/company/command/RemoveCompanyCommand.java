package nts.uk.ctx.core.app.company.command;

import lombok.Getter;
import lombok.Setter;

/**
 * RemoveCompanyCommand
 */
@Getter
@Setter
public class RemoveCompanyCommand {

	/** version */
	private long version;
	
	/** code */
	private String companyCode;
}
