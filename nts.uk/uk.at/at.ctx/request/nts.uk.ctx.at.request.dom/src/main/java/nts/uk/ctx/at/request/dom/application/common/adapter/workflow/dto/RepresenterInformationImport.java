package nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class RepresenterInformationImport {
	public static final String None_Information = "EMPTY";
	
	public static final String Path_Information = "PASS";
	
	private String value;
}
