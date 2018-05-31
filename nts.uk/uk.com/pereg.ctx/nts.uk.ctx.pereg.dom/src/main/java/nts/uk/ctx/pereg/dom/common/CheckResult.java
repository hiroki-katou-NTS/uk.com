package nts.uk.ctx.pereg.dom.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CheckResult {
	
	private String workTimeCode;
	
	private boolean startEnd;
	
	private boolean multiTime;

}
