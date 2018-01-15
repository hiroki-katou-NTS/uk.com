package nts.uk.ctx.workflow.dom.service.output;

import lombok.Value;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@Value
public class RepresenterInforOutput {
	public static final String None_Information = "EMPTY";
	
	public static final String Path_Information = "PASS";
	
	private String value;
	
	public RepresenterInforOutput(String value){
		this.value = value;
	}
	
	public static RepresenterInforOutput noneInformation(){
		return new RepresenterInforOutput(None_Information);
	}
	
	public static RepresenterInforOutput pathInformation(){
		return new RepresenterInforOutput(Path_Information);
	}
	
	public static RepresenterInforOutput representerInformation(String representer){
		return new RepresenterInforOutput(representer);
	}	
}
