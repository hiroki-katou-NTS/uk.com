package nts.uk.ctx.at.request.dom.application.common.service.application.output;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class DetailApproverOutput {
	public String approverID;
	
	public String approverName;
	
	public String representerID;
	
	public String representerName;
	
	public String jobtitle;
	
	public String jobtitleAgent;
}	
