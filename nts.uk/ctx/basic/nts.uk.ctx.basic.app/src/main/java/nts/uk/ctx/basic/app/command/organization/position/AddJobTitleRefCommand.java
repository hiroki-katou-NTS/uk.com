package nts.uk.ctx.basic.app.command.organization.position;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.basic.dom.organization.position.AuthorizationCode;
import nts.uk.ctx.basic.dom.organization.position.JobCode;
import nts.uk.ctx.basic.dom.organization.position.ReferenceSettings;

@Getter
@Setter
public class AddJobTitleRefCommand {

	String companyCode;
	
	String historyId;
	
	JobCode jobCode;
	
	AuthorizationCode authorizationCode;
	
	ReferenceSettings referenceSettings;
}
