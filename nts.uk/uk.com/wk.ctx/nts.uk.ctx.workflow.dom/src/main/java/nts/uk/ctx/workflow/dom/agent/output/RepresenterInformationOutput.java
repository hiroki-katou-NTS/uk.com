package nts.uk.ctx.workflow.dom.agent.output;

import lombok.Value;

@Value
public class RepresenterInformationOutput {
	public static final String None_Information = "EMPTY";
	
	public static final String Path_Information = "PASS";
	
	private String value;
	
	public RepresenterInformationOutput(String value){
		this.value = value;
	}
	
	public static RepresenterInformationOutput noneInformation(){
		return new RepresenterInformationOutput(None_Information);
	}
	
	public static RepresenterInformationOutput pathInformation(){
		return new RepresenterInformationOutput(Path_Information);
	}
	
	public static RepresenterInformationOutput representerInformation(String representer){
		return new RepresenterInformationOutput(representer);
	}
}
