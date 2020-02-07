package nts.uk.ctx.hr.develop.app.guidance.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ParamFindScreen {
	
	public String categoryCode;
	
	public String eventName;
	
	public String programName;
	
	public String screenName;
	
	public Boolean useSet;
	
}
