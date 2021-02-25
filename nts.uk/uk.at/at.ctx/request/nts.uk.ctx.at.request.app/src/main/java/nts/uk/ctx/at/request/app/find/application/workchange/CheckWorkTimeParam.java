package nts.uk.ctx.at.request.app.find.application.workchange;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CheckWorkTimeParam {
	
	private String companyId;
	
	private String workType;
	
	private String workTime;
	
	private AppWorkChangeSetDto appWorkChangeSetDto;
}
