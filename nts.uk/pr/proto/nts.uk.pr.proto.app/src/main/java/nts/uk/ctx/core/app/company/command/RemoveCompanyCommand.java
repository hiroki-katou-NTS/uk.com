package nts.uk.ctx.core.app.company.command;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RemoveCompanyCommand {

	private long version;
	
	private String companyCode;
}
