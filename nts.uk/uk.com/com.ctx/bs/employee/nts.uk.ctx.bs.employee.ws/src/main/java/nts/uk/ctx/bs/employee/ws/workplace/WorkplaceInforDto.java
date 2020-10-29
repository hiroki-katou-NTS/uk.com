package nts.uk.ctx.bs.employee.ws.workplace;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter	
@AllArgsConstructor
public class WorkplaceInforDto {
	
	private String workplaceGroupID;
	
	private String workplaceGroupCode;
	
	private String workplaceGroupName;
	
	private boolean isPresent;
}
