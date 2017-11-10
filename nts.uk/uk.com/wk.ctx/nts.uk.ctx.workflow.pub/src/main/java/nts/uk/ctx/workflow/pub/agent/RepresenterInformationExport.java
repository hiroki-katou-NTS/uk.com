package nts.uk.ctx.workflow.pub.agent;

import lombok.Value;

@Value
public class RepresenterInformationExport {
	
	private String value;
	
	public RepresenterInformationExport(String value){
		this.value = value;
	}
}
