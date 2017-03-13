package nts.uk.ctx.basic.app.command.organization.position;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
