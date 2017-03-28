package nts.uk.ctx.basic.app.command.organization.position;

import java.util.List;

import lombok.Getter;
import nts.uk.ctx.basic.app.find.organization.position.JobHistDto;

@Getter

public class AddPositionCommand {

	private String jobName;

	private String jobCode;

	private String historyId;

	private String memo;
		
	
	// private String hiterarchyOrderCode;

	// private int presenceCheckScopeSet;

	// private String jobOutCode;

	//private String companyCode;


}
