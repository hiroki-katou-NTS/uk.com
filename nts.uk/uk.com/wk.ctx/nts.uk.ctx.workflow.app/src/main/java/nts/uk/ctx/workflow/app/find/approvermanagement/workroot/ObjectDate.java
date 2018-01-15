package nts.uk.ctx.workflow.app.find.approvermanagement.workroot;

import lombok.Value;

@Value
public class ObjectDate {
	private String approvalId;
	private String startDate;
	private String endDate;
	private boolean overlap;
}
